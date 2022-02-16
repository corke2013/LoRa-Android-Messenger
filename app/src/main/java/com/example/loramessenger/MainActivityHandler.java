package com.example.loramessenger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class MainActivityHandler extends Handler {
    public static final int UPDATE_NEW_MESSAGE = 1;
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
            super.handleMessage(msg);
        }
    }
}
