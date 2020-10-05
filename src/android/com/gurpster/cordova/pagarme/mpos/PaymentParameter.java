package com.gurpster.cordova.pagarme.mpos;

import com.google.gson.annotations.SerializedName;
import me.pagar.mposandroid.EmvApplication;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class PaymentParameter {

//    @SerializedName("amount")
//    protected int amount;
    @SerializedName("paymentMethod")
    private int paymentMethod;
    @SerializedName("emvApplications")
    private List<EmvApplication> emvApplications;
//    private Map<String, Integer> stripeMethod = new HashMap<String, Integer>() {{
//        put("CreditCard", PaymentMethod.CreditCard);
//        put("DebitCard", PaymentMethod.DebitCard);
//    }};

    public PaymentParameter() {
        emvApplications = new ArrayList<>();
    }

    public PaymentParameter(JSONArray jsonArray) {
//        this.getProperties(jsonArray);
        emvApplications = new ArrayList<>();
    }

    public PaymentParameter(int amount, String stripeMethod) {
//        this.amount = amount;
//        if (this.stripeMethod.containsKey(stripeMethod)) {
//            this.paymentMethod = this.stripeMethod.get(stripeMethod);
//        }
    }

//    public int getAmount() {
//        return amount;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<me.pagar.mposandroid.EmvApplication> getEmvApplications() {
        List<me.pagar.mposandroid.EmvApplication> emvApplications = new ArrayList<>();
        for (EmvApplication applications: this.emvApplications) {
            me.pagar.mposandroid.EmvApplication credit = new me.pagar.mposandroid.EmvApplication(
                    applications.paymentMethod,
                    applications.cardBrand
            );
            emvApplications.add(credit);
        }
        return emvApplications;
    }

    public void setEmvApplications(List<EmvApplication> emvApplications) {
        this.emvApplications = emvApplications;
    }
    //    public Map<String, Integer> getStripeMethod() {
//        return stripeMethod;
//    }

//    public Integer getStripMethodByName(String method) {
//        if (this.stripeMethod.containsKey(method)) {
//            return  this.stripeMethod.get(method);
//        }
//        return null;
//    }

//    public PaymentParameter getProperties(JSONArray jsonArray) {
//        try {
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//            if (jsonObject.has("stripeMethod")) {
//                String stripeMethod = jsonObject.getString("stripeMethod");
//                if (this.stripeMethod.containsKey(stripeMethod)) {
//                    this.paymentMethod = this.stripeMethod.get(stripeMethod);
//                }
//            }
//            if (jsonObject.has("paymentMethod")) {
//                this.paymentMethod = jsonObject.getInt("paymentMethod");
//            }
//            if (jsonObject.has("amount")) {
//                this.amount = jsonObject.getInt("amount");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return this;
//    }

    public static class EmvApplication {

        private int paymentMethod;
        private String cardBrand;

        public EmvApplication() {
        }

        public EmvApplication(int paymentMethod, String cardBrand) {
            this.paymentMethod = paymentMethod;
            this.cardBrand = cardBrand;
        }

        public int getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(int paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getCardBrand() {
            return cardBrand;
        }

        public void setCardBrand(String cardBrand) {
            this.cardBrand = cardBrand;
        }
    }
}