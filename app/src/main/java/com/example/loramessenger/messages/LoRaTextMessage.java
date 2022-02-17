package com.example.loramessenger.messages;

import java.io.Serializable;

public class LoRaTextMessage implements Serializable {
    private LoRaMessageType loRaMessageType;
    private final String sender;
    private final String message;
    private final int uuid;
    private boolean delivered = false;


    public LoRaTextMessage(String sender, String message, int uuid) {
        this.sender = sender;
        this.message = message;
        this.uuid = uuid;
    }

    public LoRaMessageType getMessageType() {
        return loRaMessageType;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public int getUuid() {
        return uuid;
    }

    public void setMessageType(LoRaMessageType loRaMessageType) {
        this.loRaMessageType = loRaMessageType;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
