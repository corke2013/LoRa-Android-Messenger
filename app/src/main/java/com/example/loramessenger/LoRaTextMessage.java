package com.example.loramessenger;
import java.io.Serializable;

public class LoRaTextMessage implements Serializable {
    private LoRaMessageType loRaMessageType;
    private final String sender;
    private final String message;

    public LoRaTextMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
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

    public void setMessageType(LoRaMessageType loRaMessageType) {
        this.loRaMessageType = loRaMessageType;
    }
}
