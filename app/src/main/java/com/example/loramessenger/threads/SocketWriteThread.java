package com.example.loramessenger.threads;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.loramessenger.LoRaSocket;
import com.example.loramessenger.handlers.LoRaServiceHandler;
import com.google.protobuf.Any;

import java.io.IOException;

public class SocketWriteThread extends Thread {
    private final LoRaSocket loRaSocket = LoRaSocket.getInstance();
    private final Messenger loRaServiceMessenger;
    private Any any;

    public SocketWriteThread(Messenger loRaServiceMessenger) {
        this.loRaServiceMessenger = loRaServiceMessenger;
    }

    @Override
    public void run() {
        super.run();
        boolean run = true;
        while (run) {
            try {
                writeData();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                run = false;
            }
        }
    }

    public void startThread() {
        super.start();
    }

    public synchronized void write(Any any) {
        this.any = any;
        this.notify();
    }

    private synchronized void writeData() throws IOException, InterruptedException {
        if (any == null) this.wait();
        loRaSocket.write(any);
        any = null;
        sendMessage(true);
    }

    private void sendMessage(Object obj) {
        try {
            loRaServiceMessenger.send(Message.obtain(null, LoRaServiceHandler.WRITE_DATA, obj));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
