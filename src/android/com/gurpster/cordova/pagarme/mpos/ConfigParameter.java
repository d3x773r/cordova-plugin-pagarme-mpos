package com.gurpster.cordova.pagarme.mpos;

import com.alibaba.fastjson.JSON;
import com.gurpster.cordova.pagarme.mpos.entity.Message;
import com.gurpster.cordova.pagarme.mpos.entity.PagarMe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigParameter {

    private PagarMe pagarMe;
//    private String apiKey;
    private String encryptionKey;
    private String remoteApi;
    private String macAddress;
    private boolean enableSafeTransactionConditions = true;
    private boolean enableAnimations = false;
    private boolean alwaysUpdateTables = true;
    private boolean forceConnectToPinPad = false;
    private boolean autoConnectPinPad = false;

    private Message messages;

    public ConfigParameter() {
        messages = new Message();
    }

    public ConfigParameter(JSONArray jsonArray) {
        this.getProperties(jsonArray);
    }

    public ConfigParameter(String apiKey, String encryptionKey, String remoteApi, String macAddress, boolean safeTransactionConditions, boolean useAnimations, boolean alwaysUpdateTables) {
//        this.apiKey = apiKey;
        this.encryptionKey = encryptionKey;
        this.remoteApi = remoteApi;
        this.macAddress = macAddress;
        this.enableSafeTransactionConditions = safeTransactionConditions;
        this.enableAnimations = useAnimations;
        this.alwaysUpdateTables = alwaysUpdateTables;
    }

//    public String getApiKey() {
//        return apiKey;
//    }

//    public void setApiKey(String apiKey) {
//        this.apiKey = apiKey;
//    }

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

    public boolean enableSafeTransactionConditions() {
        return enableSafeTransactionConditions;
    }

    public void setSafeTransactionConditions(boolean safeTransactionConditions) {
        this.enableSafeTransactionConditions = safeTransactionConditions;
    }

    public boolean enableUseAnimations() {
        return enableAnimations;
    }

    public void setUseAnimations(boolean useAnimations) {
        this.enableAnimations = useAnimations;
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

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Message getMessages() {
        return messages;
    }

    public void setMessages(Message messages) {
        this.messages = messages;
    }

    public ConfigParameter getProperties(JSONArray jsonArray) {
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
//            if (jsonObject.has("apiKey")) {
//                this.apiKey = jsonObject.getString("apiKey");
//            }
            if (jsonObject.has("remoteApi")) {
                this.remoteApi = jsonObject.getString("remoteApi");
            }
            if (jsonObject.has("macAddress")) {
                this.macAddress = jsonObject.getString("macAddress");
            }
            if (jsonObject.has("alwaysUpdateTables")) {
                this.alwaysUpdateTables = jsonObject.getBoolean("alwaysUpdateTables");
            }
            if (jsonObject.has("encryptionKey")) {
                this.encryptionKey = jsonObject.getString("encryptionKey");
            }
            if (jsonObject.has("enableSafeTransactionConditions")) {
                this.enableSafeTransactionConditions = jsonObject.getBoolean("enableSafeTransactionConditions");
            }

            if (jsonObject.has("autoConnectPinPad")) {
                this.autoConnectPinPad = jsonObject.getBoolean("autoConnectPinPad");
            }
            if (jsonObject.has("forceConnectToPinPad")) {
                this.forceConnectToPinPad = jsonObject.getBoolean("forceConnectToPinPad");
            }
            if (jsonObject.has("enableAnimations")) {
                this.enableAnimations = jsonObject.getBoolean("enableAnimations");
            }
            if (jsonObject.has("messages")) {
                this.messages = JSON.parseObject(jsonObject.get("message").toString(), Message.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
}