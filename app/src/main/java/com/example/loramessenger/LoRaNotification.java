package com.example.loramessenger;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

class LoRaNotification {
    public static final int SERVICE_NOTIFICATION_ID = 1;
    public static final int MESSAGE_NOTIFICATION_ID = 2;
    private static final String SERVICE_CHANNEL_ID = "LoRa Service Channel";
    private static final String NEW_MESSAGE_CHANNEL_ID = "LoRa Message Channel";

    public static void createNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "LoRa Service";
            int channelImportance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(
                    SERVICE_CHANNEL_ID,
                    channelName,
                    channelImportance);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            String channelName1 = "LoRa Messages";
            int channelImportance1 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel1 = new NotificationChannel(
                    NEW_MESSAGE_CHANNEL_ID,
                    channelName1,
                    channelImportance1);
            NotificationManager notificationManager1 = context.getSystemService(NotificationManager.class);
            notificationManager1.createNotificationChannel(channel1);
        }
    }

    public static Notification getForegroundServiceNotification(Context context, int requestCode, int flags) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, flags);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, SERVICE_CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("LoRa service is running")
                .setContentIntent(pendingIntent);
        return builder.build();
    }

    public static void sendNewMessageNotification(Context context, LoRaTextMessage loRaTextMessage, int requestCode, int flags) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, flags);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NEW_MESSAGE_CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("New Message from " + loRaTextMessage.getSender())
                .setContentText(loRaTextMessage.getMessage())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(MESSAGE_NOTIFICATION_ID, builder.build());
    }
}
