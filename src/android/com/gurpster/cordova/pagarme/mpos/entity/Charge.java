package com.gurpster.cordova.pagarme.mpos.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

public class Charge extends PaymentParameter implements Serializable {

    @JSONField(name = "card_hash")
    private String cardHash;
    @JSONField(name = "is_online")
    private boolean isOnline;
    @JSONField(name = "amount")
    protected String amount;
    @JSONField(name = "payment_method")
    protected int paymentMethod;
    @JSONField(name = "card_brand")
    protected String cardBrand;
    @JSONField(name = "encryption_key")
    private String encryptionKey;

    @JSONField(name = "client_id")
    private int clientId;
    @JSONField(name = "merchant_id")
    private int merchantId;
    @JSONField(name = "merchant_name")
    private String merchantName;
    @JSONField(name = "order_id")
    protected int orderId;
    @JSONField(name = "task_id")
    protected String taskId;
    @JSONField(name = "token")
    private String token;
    @JSONField(name = "apiKey")
    private String apiKey;

    private Map<String, String> params;

    @JSONField(name = "remote_api")
    private String remoteApi;

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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    @Override
    public int getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
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

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public JSONObject toJson() {
        try {
            String string = JSON.toJSONString(this);
            return new JSONObject(string);
        } catch (JSONException e) {
            return null;
        }
    }

    public int getAmountInt() {
        return (int) (Float.parseFloat(amount) * 100);
    }

    public String getAmountFormatted() {
        return String.format(
                Locale.getDefault(),
                "%.2f",
                Float.parseFloat(amount)
        );
    }

}