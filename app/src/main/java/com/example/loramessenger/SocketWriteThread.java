package com.example.loramessenger;

import com.example.loramessenger.protos.compiled.LoRaMetadata;
import com.google.protobuf.Any;

import java.io.DataOutputStream;
import java.io.IOException;

public class SocketWriteThread extends Thread {
    private Any any;
    DataOutputStream dataOutputStream;

    @Override
    public void run() {
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

    public void startThread(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
        super.start();
    }

    public synchronized void write(LoRaTextMessage loRaTextMessage) {
        LoRaMetadata.Metadata metadata = LoRaMetadata.Metadata.newBuilder()
                .setRecipient("")
                .setSender(loRaTextMessage.getSender())
                .setTime(0)
                .setUuid("")
                .build();
        any = Any.pack(com.example.loramessenger.protos.compiled.LoRaTextMessage.TextMessage.newBuilder()
                .setMetadata(metadata)
                .setMessage(loRaTextMessage.getMessage())
                .build());
        this.notify();
    }

    private synchronized void writeData() throws IOException, InterruptedException {
        if (any == null) this.wait();
        any.writeDelimitedTo(dataOutputStream);
        dataOutputStream.flush();
        any = null;
    }
}
