package com.gurpster.cordova.pagarme.mpos.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.gurpster.cordova.pagarme.mpos.PaymentParameter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

public class Charge extends PaymentParameter implements Serializable {

    @JSONField(name = "amount")
    protected String amount = "";
    @JSONField(name = "card_hash")
    private String cardHash = "";
    @JSONField(name = "is_online")
    private boolean isOnline;
    @JSONField(name = "payment_method")
    protected int paymentMethod;
    @JSONField(name = "card_brand")
    protected String cardBrand = "";
    @JSONField(name = "encryption_key")
    private String encryptionKey = "";

    @JSONField(name = "remoteApi")
    private RemoteApi remoteApi;

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
        this.isOnline = isOnline;
    }

    public String getCardHash() {
        return cardHash.trim();
    }

    public void setCardHash(String cardHash) {
        this.cardHash = cardHash;
    }

    public String getAmount() {
        return amount.trim();
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
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

    public RemoteApi getRemoteApi() {
        return remoteApi;
    }

    public void setRemoteApi(RemoteApi remoteApi) {
        this.remoteApi = remoteApi;
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

    public static class RemoteApi {
        @JSONField(name = "type")
        private String type;
        @JSONField(name = "url")
        private String url;
        @JSONField(name = "headers")
        private Map<String, String> headers;
        @JSONField(name = "params")
        private Map<String, String> params;

        public RemoteApi() {
        }

        public String getType() {
            return type.trim();
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url.trim();
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }
    }

}