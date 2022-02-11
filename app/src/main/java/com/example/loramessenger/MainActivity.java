package com.example.loramessenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final Messenger messenger = new Messenger(new MainActivityHandler(this));
    private final List<LoRaMessage> loRaMessageList = new ArrayList<>();
    private final LoRaMessageAdapter myAdapter = new LoRaMessageAdapter(loRaMessageList);
    private DatabaseHelper dataBaseHelper;
    private LoRaService loRaService;
    boolean mBound = false;

    private final ServiceConnection connection = new ServiceConnection() {
        //        Don't like this
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            LoRaServiceBinder loRaServiceBinder = (LoRaServiceBinder) service;
            loRaService = loRaServiceBinder.getService();
            loRaService.setMessenger(messenger);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
//    Don't like this

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LoRaService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loRaMessageList.clear();
        loRaMessageList.addAll(dataBaseHelper.getMessages());
        myAdapter.notifyItemInserted(myAdapter.getItemCount());
    }

    @Override
    protected void onStop() {
        super.onStop();
        loRaService.setMessenger(null);
        unbindService(connection);
        mBound = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(dataBaseHelper.getUsername(), messageEditText.getText().toString());
                messageEditText.setText("");
            }
        });
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
            loRaMessageList.clear();
        }
        if (id == R.id.quit) {
            exit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void stuff(LoRaMessage loRaMessage) {
        loRaMessageList.add(loRaMessage);
        myAdapter.notifyItemInserted(myAdapter.getItemCount());
    }


    public void sendMessage(String username, String strMessage) {
        LoRaMessage loRaMessage = new LoRaMessage(username, strMessage);
        loRaMessage.setMessageType(LoRaMessageType.SENT);
        loRaMessageList.add(loRaMessage);
        dataBaseHelper.addMessage(loRaMessage);
        myAdapter.notifyItemInserted(myAdapter.getItemCount());
        loRaService.send(loRaMessage);
    }

    public void exit() {
        Intent serviceIntent = new Intent(this, LoRaService.class);
        stopService(serviceIntent);
        this.finishAndRemoveTask();
    }
}