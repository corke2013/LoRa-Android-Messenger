package com.example.loramessenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.loramessenger.handlers.LoRaServiceHandler;
import com.example.loramessenger.handlers.MainActivityHandler;
import com.example.loramessenger.messages.LoRaMessageDirection;
import com.example.loramessenger.messages.LoRaTextMessage;
import com.example.loramessenger.protos.compiled.LoRaAckMessageProto;
import com.example.loramessenger.protos.compiled.LoRaMetadataProto;
import com.example.loramessenger.protos.compiled.LoRaTextMessageProto;
import com.example.loramessenger.threads.SocketThreadManagerThread;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

public class LoRaService extends Service {
    public static boolean IS_RUNNING;
    private Messenger mainActivityMessenger;
    private final Messenger loRaServiceMessenger = new Messenger(new LoRaServiceHandler(this));
    private final LoRaServiceBinder loRaServiceBinder = new LoRaServiceBinder(this);
    private final SocketThreadManagerThread socketThreadManagerThread = new SocketThreadManagerThread(loRaServiceMessenger, 5000);
    private DatabaseHelper dataBaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        IS_RUNNING = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(LoRaNotification.SERVICE_NOTIFICATION_ID, LoRaNotification.getForegroundServiceNotification(this.getApplicationContext(), 0, 0));
        dataBaseHelper = new DatabaseHelper(this.getApplicationContext(), "Message_History.db", null, 1);
        socketThreadManagerThread.startThread();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return loRaServiceBinder;
    }

    @Override
    public void onDestroy() {
        IS_RUNNING = false;
        socketThreadManagerThread.stopThread();
        super.onDestroy();
    }

    public void setMessenger(Messenger mainActivityMessenger) {
        this.mainActivityMessenger = mainActivityMessenger;
    }

    public void write(LoRaTextMessage loRaTextMessage) {
        LoRaMetadataProto.Metadata metadata = LoRaMetadataProto.Metadata.newBuilder()
                .setRecipient(loRaTextMessage.getRecipient())
                .setSender(loRaTextMessage.getSender())
                .setUuid(loRaTextMessage.getUuid())
                .build();
        LoRaTextMessageProto.TextMessage textMessage = LoRaTextMessageProto.TextMessage.newBuilder()
                .setMetadata(metadata)
                .setMessage(loRaTextMessage.getMessage())
                .build();

        dataBaseHelper.createMessage(loRaTextMessage);
        socketThreadManagerThread.write(Any.pack(textMessage));
    }

    //    Call from handler
    public void onReadData(Any any) {
        try {
            if (any.is(LoRaTextMessageProto.TextMessage.class)) {
                onReceiveTextMessage(any.unpack(LoRaTextMessageProto.TextMessage.class));
            }
            if (any.is(LoRaAckMessageProto.AckMessage.class)) {
                onReceiveAckMessage(any.unpack(LoRaAckMessageProto.AckMessage.class));
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    //Call from handler
    public void onWriteData(boolean sent) {
        System.out.println(sent);
    }

    private void onReceiveTextMessage(LoRaTextMessageProto.TextMessage textMessage) {
        LoRaTextMessage loRaTextMessage = new LoRaTextMessage(
                textMessage.getMetadata().getRecipient(),
                textMessage.getMetadata().getSender(),
                textMessage.getMetadata().getUuid(),
                textMessage.getMessage(),
                System.currentTimeMillis(),
                LoRaMessageDirection.IN);
        LoRaMetadataProto.Metadata metadata = LoRaMetadataProto.Metadata.newBuilder()
                .setRecipient(loRaTextMessage.getRecipient())
                .setSender(loRaTextMessage.getSender())
                .setUuid(loRaTextMessage.getUuid())
                .build();
        LoRaAckMessageProto.AckMessage ackMessage = LoRaAckMessageProto.AckMessage.newBuilder()
                .setMetadata(metadata)
                .build();
        dataBaseHelper.createMessage(loRaTextMessage);
        socketThreadManagerThread.write(Any.pack(ackMessage));
        sendMessage(MainActivityHandler.RECEIVE_TEXT_MESSAGE, loRaTextMessage);
    }

    private void onReceiveAckMessage(LoRaAckMessageProto.AckMessage ackMessage) {
        LoRaTextMessage loRaTextMessage = dataBaseHelper.getMessage(ackMessage.getMetadata().getUuid());
        if (loRaTextMessage == null) return;
        loRaTextMessage.setDelivered(true);
        dataBaseHelper.updateMessage(loRaTextMessage);
        sendMessage(MainActivityHandler.RECEIVE_ACK_MESSAGE, loRaTextMessage);
    }

    private void sendMessage(int what, Object obj) {
        if (mainActivityMessenger != null) {
            try {
                mainActivityMessenger.send(Message.obtain(null, what, obj));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            LoRaNotification.sendNewMessageNotification(this.getApplicationContext(), (LoRaTextMessage) obj, 0, 0);
        }

    }
}
