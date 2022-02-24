package com.example.loramessenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.loramessenger.handlers.MainActivityHandler;
import com.example.loramessenger.messages.LoRaMessageDirection;
import com.example.loramessenger.messages.LoRaTextMessage;
import com.example.loramessenger.protos.compiled.LoRaAckMessageProto;
import com.example.loramessenger.protos.compiled.LoRaMetadataProto;
import com.example.loramessenger.protos.compiled.LoRaTextMessageProto;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private final List<LoRaTextMessage> loRaTextMessageList = new ArrayList<>();
    private final LoRaMessageAdapter myAdapter = new LoRaMessageAdapter(loRaTextMessageList);
    private final LoRaServiceConnection loRaServiceConnection = new LoRaServiceConnection(new Messenger(new MainActivityHandler(this)));
    private DatabaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.bleh);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        if (!LoRaService.IS_RUNNING) {
            LoRaNotification.createNotificationChannels(this.getApplicationContext());
            Intent intent = new Intent(this, LoRaService.class);
            ContextCompat.startForegroundService(this, intent);
        }
        dataBaseHelper = new DatabaseHelper(getApplicationContext(), "Message_History.db", null, 1);

        Button sendButton = findViewById(R.id.buttonSend);
        EditText messageEditText = findViewById(R.id.ediTextMessage);
        if (dataBaseHelper.getUsername().equalsIgnoreCase("")) {
            Intent intent = new Intent(this, UsernameActivity.class);
            startActivity(intent);
        }
        sendButton.setOnClickListener(view -> {

            LoRaTextMessage loRaTextMessage = new LoRaTextMessage(
                    "recipient",
                    dataBaseHelper.getUsername(),
                    UUID.randomUUID().toString(),
                    messageEditText.getText().toString(),
                    System.currentTimeMillis(),
                    LoRaMessageDirection.OUT);
            messageEditText.setText("");
            sendTextMessage(loRaTextMessage);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LoRaService.class);
        bindService(intent, loRaServiceConnection, Context.BIND_AUTO_CREATE);
        loRaTextMessageList.clear();
        loRaTextMessageList.addAll(dataBaseHelper.getMessages());
        myAdapter.notifyItemInserted(myAdapter.getItemCount());
    }

    @Override
    protected void onStop() {
        super.onStop();
        loRaServiceConnection.getLoRaService().setMessenger(null);
        unbindService(loRaServiceConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_messages) {
            myAdapter.notifyItemRangeRemoved(0, myAdapter.getItemCount());
            dataBaseHelper.truncateMessages();
            loRaTextMessageList.clear();
        }
        if (id == R.id.quit) {
            exit();
        }
        return super.onOptionsItemSelected(item);
    }

    //    Call from handler
    public void onReceiveTextMessage(LoRaTextMessage loRaTextMessage) {
        loRaTextMessageList.add(loRaTextMessage);
        myAdapter.notifyItemInserted(myAdapter.getItemCount());
    }

    //    Call from handler
    public void onReceiveAckMessage(LoRaTextMessage loRaTextMessage) {
        for (int i = 0; i < loRaTextMessageList.size(); i++) {
            if (loRaTextMessageList.get(i).getUuid().equals(loRaTextMessage.getUuid())) {
                loRaTextMessageList.set(i, loRaTextMessage);
                myAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    private void sendTextMessage(LoRaTextMessage loRaTextMessage) {
        loRaTextMessageList.add(loRaTextMessage);
        myAdapter.notifyItemInserted(myAdapter.getItemCount());
        loRaServiceConnection.getLoRaService().write(loRaTextMessage);
    }

    private void exit() {
        Intent intent = new Intent(this, LoRaService.class);
        stopService(intent);
        this.finishAndRemoveTask();
    }
}