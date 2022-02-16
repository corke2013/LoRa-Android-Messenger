package com.example.loramessenger;

import android.content.Context;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.google.protobuf.Any;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReadThread extends Thread {
    private static Messenger messenger;
    private final Context context;
    private final DatabaseHelper dataBaseHelper;
    private DataInputStream dataInputStream;

    public SocketReadThread(Context context) {
        this.context = context;
        this.dataBaseHelper = new DatabaseHelper(context, "Message_History.db", null, 1);
    }

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            try {
                readData();
            } catch (IOException e) {
                e.printStackTrace();
                run = false;
            }
        }
    }

    public void startThread(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
        super.start();
    }

    public void setMessenger(Messenger messenger) {
        SocketReadThread.messenger = messenger;
    }

    private void readData() throws IOException {
        Any any = Any.parseDelimitedFrom(dataInputStream);
        com.example.loramessenger.protos.compiled.LoRaTextMessage.TextMessage textMessage = any.unpack(com.example.loramessenger.protos.compiled.LoRaTextMessage.TextMessage.class);
        LoRaTextMessage loRaTextMessage = new LoRaTextMessage(textMessage.getMetadata().getSender(), textMessage.getMessage());
        loRaTextMessage.setMessageType(LoRaMessageType.RECEIVED);
        dataBaseHelper.addMessage(loRaTextMessage);
        if (messenger != null) {
            try {
                messenger.send(Message.obtain(null, MainActivityHandler.UPDATE_NEW_MESSAGE, loRaTextMessage));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            LoRaNotification.sendNewMessageNotification(context, loRaTextMessage, 0, 0);
        }
    }
}
