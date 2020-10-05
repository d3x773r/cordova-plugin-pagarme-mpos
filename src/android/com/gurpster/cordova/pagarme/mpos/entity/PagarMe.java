package com.gurpster.cordova.pagarme.mpos.entity;

public class PagarMe {

    private String apiKey;
    private String encryptionKey;

    public PagarMe() {
    }

    public PagarMe(String apiKey, String encryptionKey) {
        this.apiKey = apiKey;
        this.encryptionKey = encryptionKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }
}
