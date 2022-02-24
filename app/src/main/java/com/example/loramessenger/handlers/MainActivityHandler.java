package com.example.loramessenger.handlers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.loramessenger.MainActivity;
import com.example.loramessenger.messages.LoRaTextMessage;

import java.lang.ref.WeakReference;

public class MainActivityHandler extends Handler {
    public static final int RECEIVE_TEXT_MESSAGE = 1;
    public static final int RECEIVE_ACK_MESSAGE = 2;
    private final WeakReference<MainActivity> mainActivityWeakReference;

    public MainActivityHandler(MainActivity mainActivity) {
        super(Looper.getMainLooper());
        mainActivityWeakReference = new WeakReference<>(mainActivity);
    }

    @Override
    public void handleMessage(Message msg) {
        MainActivity mainActivity = mainActivityWeakReference.get();
        if (mainActivity != null) {
            if (msg.what == RECEIVE_TEXT_MESSAGE) {
                mainActivity.onReceiveTextMessage((LoRaTextMessage) msg.obj);
            }
            if (msg.what == RECEIVE_ACK_MESSAGE) {
                mainActivity.onReceiveAckMessage((LoRaTextMessage) msg.obj);
            }
            super.handleMessage(msg);
        }
    }
}
