package com.example.loramessenger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.loramessenger.handlers.LoRaServiceHandler;
import com.example.loramessenger.handlers.MainActivityHandler;
import com.example.loramessenger.messages.LoRaMessageType;
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

    public void write(Any any) {
        socketThreadManagerThread.write(any);
    }

    //    Calls from handler
    public void onReadData(Any any) {
        try {
            if (any.is(LoRaTextMessageProto.TextMessage.class)) {
                onTextMessage(any.unpack(LoRaTextMessageProto.TextMessage.class));
            }
            if (any.is(LoRaAckMessageProto.AckMessage.class)) {
                onAckMessage(any.unpack(LoRaAckMessageProto.AckMessage.class));
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public void onWriteData(boolean sent) {

    }

    private void onTextMessage(LoRaTextMessageProto.TextMessage textMessage) {
        LoRaMetadataProto.Metadata metadata = LoRaMetadataProto.Metadata.newBuilder()
                .setUuid(textMessage.getMetadata().getUuid())
                .build();
        LoRaAckMessageProto.AckMessage ackMessage = LoRaAckMessageProto.AckMessage.newBuilder()
                .setMetadata(metadata)
                .setReceived(true)
                .build();
        socketThreadManagerThread.write(Any.pack(ackMessage));
        LoRaTextMessage loRaTextMessage = new LoRaTextMessage(textMessage.getMetadata().getSender(), textMessage.getMessage(), textMessage.getMetadata().getUuid());
        loRaTextMessage.setMessageType(LoRaMessageType.RECEIVED);
        dataBaseHelper.addMessage(loRaTextMessage);
        sendMessage(MainActivityHandler.UPDATE_NEW_MESSAGE, loRaTextMessage);
    }

    private void onAckMessage(LoRaAckMessageProto.AckMessage ackMessage) {
        sendMessage(MainActivityHandler.ACK_EXISTING_MESSAGE, ackMessage);
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
