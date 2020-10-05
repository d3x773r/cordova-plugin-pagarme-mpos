package com.gurpster.cordova.pagarme.mpos;

public class Payment {

    private int amount;
    private int paymentMethod;

    public Payment() {
    }

    public Payment(int amount, int paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
