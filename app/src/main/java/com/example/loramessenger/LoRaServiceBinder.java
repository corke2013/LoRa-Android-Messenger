package com.example.loramessenger;

import android.os.Binder;

public class LoRaServiceBinder extends Binder {
    private final LoRaService loRaService;

    public LoRaServiceBinder(LoRaService loRaService) {
        this.loRaService = loRaService;
    }

    LoRaService getService() {
        return loRaService;
    }
}
