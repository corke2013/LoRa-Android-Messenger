package com.example.loramessenger.handlers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.loramessenger.messages.LoRaTextMessage;
import com.example.loramessenger.MainActivity;
import com.example.loramessenger.protos.compiled.LoRaAckMessageProto;

import java.lang.ref.WeakReference;

public class MainActivityHandler extends Handler {
    public static final int UPDATE_NEW_MESSAGE = 1;
    public static final int ACK_EXISTING_MESSAGE = 2;
    private final WeakReference<MainActivity> mainActivityWeakReference;

    public MainActivityHandler(MainActivity mainActivity) {
        super(Looper.getMainLooper());
        mainActivityWeakReference = new WeakReference<>(mainActivity);
    }

    @Override
    public void handleMessage(Message msg) {
        MainActivity mainActivity = mainActivityWeakReference.get();
        if (mainActivity != null) {
            if (msg.what == UPDATE_NEW_MESSAGE) {
                mainActivity.stuff((LoRaTextMessage) msg.obj);
            }
            if (msg.what == ACK_EXISTING_MESSAGE) {
                mainActivity.stuff2((LoRaAckMessageProto.AckMessage) msg.obj);
            }
            super.handleMessage(msg);
        }
    }
}
