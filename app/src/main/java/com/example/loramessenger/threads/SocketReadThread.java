package com.example.loramessenger.threads;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.loramessenger.LoRaSocket;
import com.example.loramessenger.handlers.LoRaServiceHandler;
import com.google.protobuf.Any;

import java.io.IOException;

public class SocketReadThread extends Thread {
    private final LoRaSocket loRaSocket = LoRaSocket.getInstance();
    private final Messenger loRaServiceMessenger;

    public SocketReadThread(Messenger loRaServiceMessenger) {
        this.loRaServiceMessenger = loRaServiceMessenger;
    }

    @Override
    public void run() {
        super.run();
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

    public void startThread() {
        super.start();
    }

    private void readData() throws IOException {
        Any any = loRaSocket.read();
        sendMessage(any);
    }

    private void sendMessage(Object obj) {
        try {
            loRaServiceMessenger.send(Message.obtain(null, LoRaServiceHandler.READ_DATA, obj));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
