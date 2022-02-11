package com.example.loramessenger;

import org.apache.commons.lang3.SerializationUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SocketWriteThread extends Thread{
    private byte[] payload;
    DataOutputStream dataOutputStream;

    @Override
    public void run() {
        boolean run = true;
        while(run){
            try {
                writeData();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                run = false;
            }
        }
    }

    public void startThread(DataOutputStream dataOutputStream){
        this.dataOutputStream = dataOutputStream;
        super.start();
    }

    public synchronized void write(LoRaMessage loRaMessage) {
        byte[] s2 = SerializationUtils.serialize(loRaMessage.getSender() + ":" + loRaMessage.getMessage());
        byte[] s1 = ByteBuffer.allocate(4).putInt(s2.length).array();
        payload = new byte[s1.length + s2.length];
        System.arraycopy(s1, 0, payload, 0, s1.length);
        System.arraycopy(s2, 0, payload, s1.length, s2.length);
        this.notify();
    }

    private synchronized void writeData() throws IOException, InterruptedException {
        if (payload == null) this.wait();
        dataOutputStream.write(payload);
        dataOutputStream.flush();
        payload = null;
    }
}
