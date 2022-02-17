package com.example.loramessenger;

import com.google.protobuf.Any;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LoRaSocket {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private LoRaSocket() {
    }

    private static class LoRaSocketHolder {
        private static final LoRaSocket INSTANCE = new LoRaSocket();
    }

    public static LoRaSocket getInstance() {
        return LoRaSocketHolder.INSTANCE;
    }

    public void connect() throws IOException {
        disconnect();
        socket = new Socket();
        socket.connect(new InetSocketAddress("192.168.1.1", 9597), 5000);
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void disconnect() throws IOException {
        if ((socket != null) && (!socket.isClosed())) socket.close();
    }

    public Any read() throws IOException {
        return Any.parseDelimitedFrom(dataInputStream);
    }

    public void write(Any any) throws IOException {
        any.writeDelimitedTo(dataOutputStream);
        dataOutputStream.flush();
    }
}
