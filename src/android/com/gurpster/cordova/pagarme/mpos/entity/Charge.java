package com.gurpster.cordova.pagarme.mpos.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.gurpster.cordova.pagarme.mpos.PaymentParameter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Charge extends PaymentParameter implements Serializable {

    @JSONField(name = "card_hash")
    private String cardHash;
    @JSONField(name = "client_id")
    private int clientId;
    @JSONField(name = "merchant_id")
    private int merchantId;
    @JSONField(name = "is_online")
    private boolean isOnline;
    @JSONField(name = "amount")
    protected String amount;
    @JSONField(name = "order_id")
    protected int orderId;
    @JSONField(name = "task_id")
    protected String taskId;

    private String token;
    @JSONField(name = "apiKey")
    private String apiKey;

    public Charge() {
    }

    public Charge(String cardHash, String amount, boolean isOnline) {
        this.cardHash = cardHash;
        this.amount = amount;
        this.isOnline = isOnline;
    }

    public Charge(String cardHash, String amount, int clientId, int merchantId, boolean isOnline) {
        this.cardHash = cardHash;
        this.amount = amount;
        this.clientId = clientId;
        this.merchantId = merchantId;
        this.isOnline = isOnline;
    }

    public String getCardHash() {
        return cardHash;
    }

    public void setCardHash(String cardHash) {
        this.cardHash = cardHash;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public JSONObject toJson() {
        try {
            String string = JSON.toJSONString(this);
            return new JSONObject(string);
        } catch (JSONException e) {
            return null;
        }
    }

}
