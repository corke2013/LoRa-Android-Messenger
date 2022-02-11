package com.example.loramessenger;
import android.content.Context;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import org.apache.commons.lang3.SerializationUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        int payloadSize = dataInputStream.readInt();
        if (251 >= payloadSize && payloadSize >= 0) {
            byte[] buff = new byte[payloadSize];
            dataInputStream.read(buff, 0, payloadSize);
            LoRaMessage loRaMessage = new LoRaMessage(((String) SerializationUtils.deserialize(buff)).split(":")[0],
                    ((String) SerializationUtils.deserialize(buff)).split(":")[1]);
            loRaMessage.setMessageType(LoRaMessageType.RECEIVED);
            dataBaseHelper.addMessage(loRaMessage);
            if (messenger != null) {
                try {
                    messenger.send(Message.obtain(null, MainActivityHandler.UPDATE_NEW_MESSAGE, loRaMessage));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                LoRaNotification.sendNewMessageNotification(context, loRaMessage, 0, 0);
            }
        }
    }
}
