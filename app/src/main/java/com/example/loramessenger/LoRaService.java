package com.example.loramessenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;

public class LoRaService extends Service {
    public static boolean IS_RUNNING;
    private final LoRaServiceBinder loRaServiceBinder = new LoRaServiceBinder(this);
    private SocketRunnable socketRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        IS_RUNNING = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        socketRunnable = new SocketRunnable(this.getApplicationContext(), "192.168.1.1", 9597, 1000, 5000);
        startForeground(LoRaNotification.SERVICE_NOTIFICATION_ID, LoRaNotification.getForegroundServiceNotification(this.getApplicationContext(), 0, 0));
        new Thread(socketRunnable).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return loRaServiceBinder;
    }

    @Override
    public void onDestroy() {
        IS_RUNNING = false;
        socketRunnable.stop();
        super.onDestroy();
    }

    public void setMessenger(Messenger messenger) {
        socketRunnable.setMessenger(messenger);
    }

    public void send(LoRaTextMessage loRaTextMessage) {
        socketRunnable.write(loRaTextMessage);
    }
}
