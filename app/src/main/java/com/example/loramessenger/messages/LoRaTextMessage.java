package com.example.loramessenger.messages;

import java.io.Serializable;

public class LoRaTextMessage implements Serializable {
    private final String recipient;
    private final String sender;
    private final String uuid;
    private final String message;
    private final long timeStamp;
    private final LoRaMessageDirection loRaMessageDirection;
    private boolean delivered = false;


    public LoRaTextMessage(String recipient, String sender, String uuid, String message, long timeStamp, LoRaMessageDirection loRaMessageDirection) {
        this.recipient = recipient;
        this.sender = sender;
        this.uuid = uuid;
        this.message = message;
        this.timeStamp = timeStamp;
        this.loRaMessageDirection = loRaMessageDirection;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getUuid() {
        return uuid;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public LoRaMessageDirection getLoRaMessageDirection() {
        return loRaMessageDirection;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
