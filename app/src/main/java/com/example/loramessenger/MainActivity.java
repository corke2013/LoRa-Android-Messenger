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
import com.example.loramessenger.messages.LoRaMessageType;
import com.example.loramessenger.messages.LoRaTextMessage;
import com.example.loramessenger.protos.compiled.LoRaAckMessageProto;
import com.example.loramessenger.protos.compiled.LoRaMetadataProto;
import com.example.loramessenger.protos.compiled.LoRaTextMessageProto;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.List;

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
            sendTextMessage(dataBaseHelper.getUsername(), messageEditText.getText().toString());
            messageEditText.setText("");
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

    //    Calls from handler
    public void stuff(LoRaTextMessage loRaTextMessage) {
        loRaTextMessageList.add(loRaTextMessage);
        myAdapter.notifyItemInserted(myAdapter.getItemCount());
        sendAckMessage(loRaTextMessage.getUuid());
    }

    public void stuff2(LoRaAckMessageProto.AckMessage ackMessage) {
        loRaTextMessageList.get(ackMessage.getMetadata().getUuid()).setDelivered(true);
        myAdapter.notifyItemChanged(ackMessage.getMetadata().getUuid());
    }

    private void sendTextMessage(String username, String strMessage) {
        LoRaTextMessage loRaTextMessage = new LoRaTextMessage(username, strMessage, myAdapter.getItemCount());
        loRaTextMessage.setMessageType(LoRaMessageType.SENT);
        loRaTextMessageList.add(loRaTextMessage);
        dataBaseHelper.addMessage(loRaTextMessage);
        myAdapter.notifyItemInserted(myAdapter.getItemCount());
        LoRaMetadataProto.Metadata metadata = LoRaMetadataProto.Metadata.newBuilder()
                .setSender(loRaTextMessage.getSender())
                .setUuid(loRaTextMessage.getUuid())
                .build();
        LoRaTextMessageProto.TextMessage textMessage = LoRaTextMessageProto.TextMessage.newBuilder()
                .setMetadata(metadata)
                .setMessage(strMessage)
                .build();
        loRaServiceConnection.getLoRaService().write(Any.pack(textMessage));
    }

    private void sendAckMessage(int uuid) {
        LoRaMetadataProto.Metadata metadata = LoRaMetadataProto.Metadata.newBuilder()
                .setUuid(uuid)
                .build();
        LoRaAckMessageProto.AckMessage ackMessage = LoRaAckMessageProto.AckMessage.newBuilder()
                .setMetadata(metadata)
                .setReceived(true)
                .build();
        loRaServiceConnection.getLoRaService().write(Any.pack(ackMessage));
    }

    private void exit() {
        Intent intent = new Intent(this, LoRaService.class);
        stopService(intent);
        this.finishAndRemoveTask();
    }
}