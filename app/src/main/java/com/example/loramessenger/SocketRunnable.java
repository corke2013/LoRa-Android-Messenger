package com.example.loramessenger;

import android.content.Context;
import android.os.Messenger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketRunnable implements Runnable {
    private final Context context;
    private final String host;
    private final int port;
    private final int timeout;
    private final int retryInterval;
    private boolean run;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private SocketReadThread socketReadThread;
    private SocketWriteThread socketWriteThread;

    public SocketRunnable(Context context, String host, int port, int timeout, int retryInterval) {
        this.context = context;
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.retryInterval = retryInterval;
    }

    @Override
    public void run() {
        run = true;
        socketReadThread = new SocketReadThread(context);
        socketWriteThread = new SocketWriteThread();
        while (run) {
            try {
                if (!socketReadThread.isAlive() || !socketWriteThread.isAlive()) {
                    connect();
                    startSocketReadWriteThreads();
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        run = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMessenger(Messenger messenger) {
        socketReadThread.setMessenger(messenger);
    }

    public void write(LoRaMessage loRaMessage) {
        socketWriteThread.write(loRaMessage);
    }

    private void connect() throws IOException {
        if ((socket != null) && (!socket.isClosed())) socket.close();
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), timeout);
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    private void startSocketReadWriteThreads() {
        socketReadThread = new SocketReadThread(context);
        socketWriteThread = new SocketWriteThread();
        socketReadThread.startThread(dataInputStream);
        socketWriteThread.startThread(dataOutputStream);
    }
}
