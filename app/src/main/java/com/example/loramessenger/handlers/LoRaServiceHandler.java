package com.example.loramessenger.handlers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.loramessenger.LoRaService;
import com.google.protobuf.Any;

import java.lang.ref.WeakReference;

public class LoRaServiceHandler extends Handler {
    public static final int READ_DATA = 1;
    public static final int WRITE_DATA = 2;
    private final WeakReference<LoRaService> loRaServiceWeakReference;

    public LoRaServiceHandler(LoRaService loRaService) {
        super(Looper.getMainLooper());
        loRaServiceWeakReference = new WeakReference<>(loRaService);
    }

    @Override
    public void handleMessage(Message msg) {
        LoRaService loRaService = loRaServiceWeakReference.get();
        if (loRaService == null) return;
        switch (msg.what) {
            case READ_DATA:
                loRaService.onReadData((Any) msg.obj);
                break;
            case WRITE_DATA:
                loRaService.onWriteData((boolean) msg.obj);
                break;
            default:
                super.handleMessage(msg);
                break;
        }
    }
}
