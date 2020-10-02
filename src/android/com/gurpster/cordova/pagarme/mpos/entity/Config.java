package com.gurpster.cordova.pagarme.mpos.entity;

public class Config {

    private String apiKey;
    private String encryptionKey;
    private String remoteApi;
    private String macAddress;
    private boolean enableSafeTransactionConditions = true;
    private boolean enableAnimations = false;
    private boolean alwaysUpdateTables = true;
    private boolean forceConnectToPinPad = false;
    private boolean autoConnectPinPad = false;

    public Config() {
    }

    public Config(String apiKey, String encryptionKey, String remoteApi, String macAddress, boolean enableSafeTransactionConditions, boolean enableAnimations, boolean alwaysUpdateTables, boolean forceConnectToPinPad, boolean autoConnectPinPad) {
        this.apiKey = apiKey;
        this.encryptionKey = encryptionKey;
        this.remoteApi = remoteApi;
        this.macAddress = macAddress;
        this.enableSafeTransactionConditions = enableSafeTransactionConditions;
        this.enableAnimations = enableAnimations;
        this.alwaysUpdateTables = alwaysUpdateTables;
        this.forceConnectToPinPad = forceConnectToPinPad;
        this.autoConnectPinPad = autoConnectPinPad;
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

    public String getRemoteApi() {
        return remoteApi;
    }

    public void setRemoteApi(String remoteApi) {
        this.remoteApi = remoteApi;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public boolean isEnableSafeTransactionConditions() {
        return enableSafeTransactionConditions;
    }

    public void setEnableSafeTransactionConditions(boolean enableSafeTransactionConditions) {
        this.enableSafeTransactionConditions = enableSafeTransactionConditions;
    }

    public boolean isEnableAnimations() {
        return enableAnimations;
    }

    public void setEnableAnimations(boolean enableAnimations) {
        this.enableAnimations = enableAnimations;
    }

    public boolean isAlwaysUpdateTables() {
        return alwaysUpdateTables;
    }

    public void setAlwaysUpdateTables(boolean alwaysUpdateTables) {
        this.alwaysUpdateTables = alwaysUpdateTables;
    }

    public boolean isForceConnectToPinPad() {
        return forceConnectToPinPad;
    }

    public void setForceConnectToPinPad(boolean forceConnectToPinPad) {
        this.forceConnectToPinPad = forceConnectToPinPad;
    }

    public boolean isAutoConnectPinPad() {
        return autoConnectPinPad;
    }

    public void setAutoConnectPinPad(boolean autoConnectPinPad) {
        this.autoConnectPinPad = autoConnectPinPad;
    }
}