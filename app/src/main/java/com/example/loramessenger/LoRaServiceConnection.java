package com.example.loramessenger;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

public class LoRaServiceConnection implements ServiceConnection {
    private final Messenger messenger;
    private LoRaService loRaService;

    public LoRaServiceConnection(Messenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        LoRaServiceBinder loRaServiceBinder = (LoRaServiceBinder) iBinder;
        loRaService = loRaServiceBinder.getService();
        loRaService.setMessenger(messenger);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
    }

    public LoRaService getLoRaService() {
        return loRaService;
    }
}
