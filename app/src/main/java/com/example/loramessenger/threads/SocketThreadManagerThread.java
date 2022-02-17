package com.example.loramessenger.threads;

import android.os.Messenger;

import com.example.loramessenger.LoRaSocket;
import com.google.protobuf.Any;

import java.io.IOException;

public class SocketThreadManagerThread extends Thread {
    private final Messenger loRaServiceMessenger;
    private final long retryInterval;
    private final LoRaSocket loRaSocket = LoRaSocket.getInstance();
    private SocketReadThread socketReadThread;
    private SocketWriteThread socketWriteThread;

    private boolean run = false;

    public SocketThreadManagerThread(Messenger loRaServiceMessenger, long retryInterval) {
        this.loRaServiceMessenger = loRaServiceMessenger;
        this.retryInterval = retryInterval;
        socketReadThread = new SocketReadThread(loRaServiceMessenger);
        socketWriteThread = new SocketWriteThread(loRaServiceMessenger);
    }

    @Override
    public void run() {
        super.run();
        run = true;
        while (run) {
            try {
                if ((!socketReadThread.isAlive()) || (!socketWriteThread.isAlive())) {
                    loRaSocket.connect();
                    startReadWriteThreads();
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if (run) //noinspection BusyWait
                        Thread.sleep(retryInterval);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
    }

    public void startThread() {
        super.start();
    }

    public void stopThread() {
        run = false;
        try {
            loRaSocket.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Any any) {
        socketWriteThread.write(any);
    }

    private void startReadWriteThreads() {
        socketReadThread = new SocketReadThread(loRaServiceMessenger);
        socketWriteThread = new SocketWriteThread(loRaServiceMessenger);
        socketReadThread.startThread();
        socketWriteThread.startThread();
    }
}
