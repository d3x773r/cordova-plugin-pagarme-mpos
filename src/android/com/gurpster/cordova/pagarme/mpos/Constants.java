package com.gurpster.cordova.pagarme.mpos;

public class Constants {

    public static final int REQUEST_ENABLE_BT = 0x123;
    public static final String PREF_DEFAULT_PIN_PAD = "connected_pin_pad";

    public static final int DEVICE_CONNECTED = 20;
    public static final int DEVICE_NOT_CONNECTED = 21;
    public static final int BLUETOOTH_CONNECTED = 22;
    public static final int BLUETOOTH_CONNECTING = 27;
    public static final int BLUETOOTH_NOT_CONNECTED = 23;
    public static final int PAYMENT_ERROR = 24;
    public static final int PAYMENT_ERROR_PARAMETERS = 24;
    public static final int UNKNOWN_ERROR = 404;
    public static final int PAYMENT_SUCCESSFUL = 25;
    public static final int PAYMENT_PROCESSING = 26;
    public static final int PAYMENT_METHOD_CREDIT_CARD = 1;
    public static final int PAYMENT_METHOD_DEBIT_CARD = 2;
    public static final int INITIALIZED_SUCCESSFULLY = 10;
    public static final int INITIALIZED_ERROR = 11;
    public static final int PIN_PAD_TABLES_UPDATED = 28;
    public static final int PIN_PAD_CANCELED = 29;

    public static final int ERROR_INITIALIZE = 100;
    public static final int ERROR_CONNECTION = 50;
    public static final int ERROR_LOW_BATTERY = 50;
}