package com.gurpster.cordova.pagarme.mpos;

/*
  Licensed to the Apache Software Foundation (ASF) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The ASF licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.*;
import android.os.*;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.gurpster.cordova.pagarme.mpos.entity.Charge;
import me.pagar.mposandroid.Mpos;
import me.pagar.mposandroid.MposListener;
import me.pagar.mposandroid.MposPaymentResult;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

public class MposPluginService extends Service {

    private static final String TAG = MposPluginService.class.getSimpleName();

    private final IBinder binder = new MposPluginServiceBinder();

    private SharedPreferences sharedPreferences;

    public boolean isRunning;

    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice pinPad;

    private Mpos mpos;

    private JSONArray args;
    private CallbackContext callbackContext;

    private ConfigParameter configParameter = null;
    private PaymentParameter paymentParameter = null;

    private Charge charge = null;

    private boolean hasInitialize = false;
    private boolean pinPadConnected = false;

    @Override
    public IBinder onBind(Intent intent) {
        isRunning = true;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        AndroidNetworking.initialize(getApplicationContext());
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return binder;
    }

    public class MposPluginServiceBinder extends Binder {
        MposPluginService getService() {
            return MposPluginService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private boolean hasInternetConnection() {
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setConnectTimeout(1000 * 30); // mTimeout is in seconds
            urlc.connect();
            return (urlc.getResponseCode() == 200);
        } catch (IOException e1) {
            return false;
        }
    }

    private boolean hasSafeBatteryLevel() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        if (batteryStatus == null) {
            return false;
        }

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return (level * 100 / (float) scale) >= 20;
    }

    private void initialize(JSONArray args, CallbackContext callbackContext) {
        JSONObject jsonObject = new JSONObject();
        try {

            configParameter = JSON.parseObject(
                    args.getJSONObject(0).toString(),
                    ConfigParameter.class
            );

            if (configParameter.getMacAddress() != null) {
                pinPad = bluetoothAdapter.getRemoteDevice(configParameter.getMacAddress());
            }

            jsonObject.put("code", Constants.INITIALIZED_SUCCESSFULLY);
            jsonObject.put("message", "initialized");
            callbackContext.success(jsonObject);

            hasInitialize = true;

        } catch (JSONException e) {
            try {
                jsonObject.put("code", Constants.INITIALIZED_ERROR);
                jsonObject.put("message", e.getMessage());
                callbackContext.error(jsonObject);
            } catch (JSONException ex) {
                callbackContext.error(ex.getMessage());
            }
        }
    }

    private void listDevices(JSONArray args, CallbackContext callbackContext) {

//        if (this.configParameter == null) {
//            callbackContext.error("Error. You need call 'initialize()' first");
//            return;
//        }

        this.callbackContext = callbackContext;
        JSONArray jsonArray = new JSONArray();
        try {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (device.getName().toUpperCase().startsWith("PAX-")) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", 20);
                        jsonObject.put("message", "paired devices");
                        jsonObject.put("name", device.getName());
                        jsonObject.put("macAddress", device.getAddress());
                        jsonArray.put(jsonObject);
                    }
                }
            }
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", 20);
                jsonObject.put("message", "paired devices");
                jsonObject.put("devices", jsonArray);

                PluginResult pluginResult = makePluginResult(PluginResult.Status.OK, jsonObject);
                pluginResult.setKeepCallback(false);
                callbackContext.sendPluginResult(pluginResult);
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", 21);
                jsonObject.put("message", "Not devices found");

                PluginResult pluginResult = makePluginResult(PluginResult.Status.OK, jsonObject);
                pluginResult.setKeepCallback(false);
                callbackContext.sendPluginResult(pluginResult);
            }
        } catch (JSONException e) {
            callbackContext.error("Unknown error");
        }
    }

    private void findAndConnectPinPad(JSONArray args, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, filter);
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();

    }

    private void getConnectedPinPad(JSONArray args, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;

        JSONObject jsonObject = new JSONObject();
        JSONObject pinPadObject = new JSONObject();
        try {
            if (pinPad != null) {
                jsonObject.put("code", Constants.BLUETOOTH_CONNECTED);
                pinPadObject.put("name", pinPad.getName());
                pinPadObject.put("macAddress", pinPad.getAddress());
                jsonObject.put("pinPadConnected", true);
                jsonObject.put("pinPad", pinPadObject);
                callbackContext.success(jsonObject);
            } else if (sharedPreferences.contains(Constants.PREF_DEFAULT_PIN_PAD)) {
                String pinPadString = sharedPreferences.getString(Constants.PREF_DEFAULT_PIN_PAD, null);
                JSONObject pinPadJson = new JSONObject(pinPadString);
                jsonObject.put("code", Constants.BLUETOOTH_CONNECTED);
                jsonObject.put("pinPad", pinPadJson);
                jsonObject.put("pinPadConnected", true);
                callbackContext.success(jsonObject);
            } else {
                jsonObject.put("code", Constants.BLUETOOTH_NOT_CONNECTED);
                jsonObject.put("message", "not pin pad connected");
                jsonObject.put("pinPadConnected", false);
                callbackContext.error(jsonObject);
            }
        } catch (Exception e) {
            callbackContext.error("Unknown error");
        }
    }

    private void connectPinPad(JSONArray jsonArray, CallbackContext callbackContext) {

        if (configParameter == null) {
            callbackContext.error("Error. You need call 'initialize()' first");
            return;
        }

        this.callbackContext = callbackContext;

        try {
            if (jsonArray.length() > 0 && !jsonArray.getString(0).equals("null")) {

                String pinPadMacAddress = null;
                boolean rememberPinPad = false;
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                if (jsonObject.has("macAddress")) {
                    pinPadMacAddress = jsonObject.getString("macAddress");
                }
                if (jsonObject.has("remember")) {
                    rememberPinPad = jsonObject.getBoolean("remember");
                }

                if (pinPadMacAddress == null) {
                    callbackContext.error("Invalid pin pad mac address");
                    return;
                }

                if (BluetoothAdapter.checkBluetoothAddress(pinPadMacAddress)) {
                    pinPad = bluetoothAdapter.getRemoteDevice(pinPadMacAddress);

                    JSONObject pinPadJson = new JSONObject();
                    pinPadJson.put("name", pinPad.getName());
                    pinPadJson.put("macAddress", pinPad.getAddress());

                    if (rememberPinPad) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.PREF_DEFAULT_PIN_PAD, pinPadJson.toString());
                        editor.apply();
                    }

                } else {
                    callbackContext.error("Invalid pin pad mac address");
                    return;
                }
            } else if (sharedPreferences.contains(Constants.PREF_DEFAULT_PIN_PAD)) {
                String pinPadString = sharedPreferences.getString(Constants.PREF_DEFAULT_PIN_PAD, null);
                JSONObject pinPadJson = new JSONObject(pinPadString);
                pinPad = bluetoothAdapter.getRemoteDevice(pinPadJson.getString("macAddress"));
            }

            if (pinPad != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", Constants.BLUETOOTH_CONNECTING);
                jsonObject.put("message", Constants.BLUETOOTH_CONNECTING);

                JSONObject pinPadObject = new JSONObject();
                pinPadObject.put("name", pinPad.getName());
                pinPadObject.put("macAddress", pinPad.getAddress());
                jsonObject.put("pinPad", pinPadObject);

                PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                pluginResult.setKeepCallback(false);
                callbackContext.sendPluginResult(pluginResult);
                openConnection(jsonArray, callbackContext);

                pinPadConnected = true;
            } else {
                callbackContext.error("not pin pad available");
            }
        } catch (Exception e) {
            callbackContext.error("Invalid Parameter");
        }
    }

    private void disconnectPinPad(JSONArray args, CallbackContext callbackContext) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.PREF_DEFAULT_PIN_PAD);
        editor.apply();
        if (mpos != null) {
            mpos.closeConnection();

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", Constants.BLUETOOTH_NOT_CONNECTED);
                jsonObject.put("message", "pin pad disconnected");
                callbackContext.success(jsonObject);
            } catch (JSONException e) {
                callbackContext.success();
            }
        }

        pinPadConnected = false;
    }

    private void openConnection(JSONArray jsonArray, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;

        try {

            if (pinPad == null && sharedPreferences.contains(Constants.PREF_DEFAULT_PIN_PAD)) {
                String pinPadString = sharedPreferences.getString(Constants.PREF_DEFAULT_PIN_PAD, null);
                JSONObject pinPadJson = new JSONObject(pinPadString);
                pinPad = bluetoothAdapter.getRemoteDevice(pinPadJson.getString("macAddress"));
            }

            mpos = new Mpos(
                    pinPad,
                    configParameter.getEncryptionKey(),
                    getApplicationContext()
            );

            mpos.addListener(new MposListener() {
                public void bluetoothConnected() {
                    mpos.initialize();
                }

                public void bluetoothDisconnected() {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", Constants.BLUETOOTH_NOT_CONNECTED);
                        jsonObject.put("message", "Bluetooth disconnected.");
                        callbackContext.error(jsonObject);
                    } catch (JSONException e) {
                        callbackContext.error("error");
                    }
                }

                public void bluetoothErrored(int error) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", error);
                        jsonObject.put("message", "Received bluetooth error");
                        callbackContext.error(jsonObject);
                    } catch (JSONException e) {
                        callbackContext.error("error " + error);
                    }
                }

                public void receiveInitialization() {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", Constants.INITIALIZED_SUCCESSFULLY);
                        jsonObject.put("message", "initialized");

                        PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                        pluginResult.setKeepCallback(false);
                        callbackContext.sendPluginResult(pluginResult);

                        if (configParameter.isAlwaysUpdateTables() && mpos != null) {
                            mpos.downloadEMVTablesToDevice(true);
                        }

                        showPinPadMessage(configParameter.getMessages().getAppName(), 0);

                    } catch (Exception e) {
                        Log.d(TAG, "Got error in initialization and table update " + e.getMessage());
                    }
                }

                public void receiveNotification(String notification) {
                    // notification from pin pad
                    Log.d("Abecs", "Got Notification " + notification);
                    if (TextUtils.isEmpty(notification)) {
                        showPinPadMessage(configParameter.getMessages().getCancel(), 0);
                        showPinPadMessage(configParameter.getMessages().getAppName(), 3500);

                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("code", Constants.PIN_PAD_CANCELED);
                            jsonObject.put("message", "pin pad by canceled");

                            PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                            pluginResult.setKeepCallback(false);
                            callbackContext.error(jsonObject);
                        } catch (JSONException e) {
                        }
                    }
                }

                @Override
                public void receiveOperationCompleted() {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", 20);
                        jsonObject.put("message", "done");

                        PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                        pluginResult.setKeepCallback(false);
                        callbackContext.sendPluginResult(pluginResult);
                    } catch (JSONException e) {
                    }
                }

                public void receiveTableUpdated(boolean loaded) {
                    Log.d("Abecs", "received table updated loaded = " + loaded);

                    showPinPadMessage(configParameter.getMessages().getAppName(), 0);

                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", Constants.PIN_PAD_TABLES_UPDATED);
                        jsonObject.put("message", "received table updated");
                        jsonObject.put("loaded", loaded);

                        PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                        pluginResult.setKeepCallback(false);
                        callbackContext.sendPluginResult(pluginResult);
                    } catch (JSONException e) {
                    }
                }

                public void receiveFinishTransaction() {
                    mpos.close(configParameter.getMessages().getSuccessPayment());

                    hasInitialize = false;
                    pinPadConnected = false;
                    pinPad = null;

                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", 20);
                        jsonObject.put("message", "transaction approved");

                        PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                        pluginResult.setKeepCallback(false);
                        callbackContext.sendPluginResult(pluginResult);
                    } catch (JSONException e) {
                    }

                    showPinPadMessage(configParameter.getMessages().getFinishedTransaction(), 0);
                    showPinPadMessage(configParameter.getMessages().getAppName(), 1200);
                }

                public void receiveClose() {
                    mpos.closeConnection();
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", 20);
                        jsonObject.put("message", "close connection");

                        PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                        pluginResult.setKeepCallback(false);
                        callbackContext.sendPluginResult(pluginResult);
                    } catch (JSONException e) {
                    }

                    showPinPadMessage(configParameter.getMessages().getAppName(), 2000);
                }

                public void receiveCardHash(String cardHash, MposPaymentResult result) {
                    showPinPadMessage(configParameter.getMessages().getProcessingTransaction(), 700);

                    charge.setCardHash(cardHash);
                    charge.setOnline(result.isOnline);

                    if (!TextUtils.isEmpty(configParameter.getRemoteApi())) {
                        callRemoteServer(charge);
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("code", 31);
                            jsonObject.put("message", "delegate remote api");
                            jsonObject.put("data", charge.toJson());

                            PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                            pluginResult.setKeepCallback(false);
                            callbackContext.sendPluginResult(pluginResult);
                        } catch (JSONException e) {
                        }
                    }
                }

                public void receiveError(int error) {

                    new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(Message message) {
                            String errorMessage = com.gurpster.cordova.pagarme.mpos.entity.Message.getErrorFromCode(error);

                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("code", error);
                                jsonObject.put("message", errorMessage);
                                callbackContext.error(jsonObject);
                            } catch (JSONException e) {
                                callbackContext.error("error " + error);
                            }

                            showPinPadMessage(errorMessage, 4000);
                        }
                    };

                }

                public void receiveOperationCancelled() {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("code", 40);
                        jsonObject.put("message", "Operation Cancelled");
                        callbackContext.error(jsonObject);
                    } catch (JSONException e) {
                        callbackContext.error("Operation Cancelled");
                    }
                }
            });

            mpos.openConnection(false);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 21);
            jsonObject.put("message", "prepare pin pad");
            PluginResult pluginResult = makePluginResult(PluginResult.Status.OK, jsonObject);
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);

        } catch (IOException | JSONException e) {
            callbackContext.error(e.getMessage());
        }
    }

    private void closeConnection(JSONArray args, CallbackContext callbackContext) {
        if (this.configParameter == null) {
            callbackContext.error("Error. You need call 'initialize()' first");
            return;
        }

        if (mpos != null) {
            mpos.close("closeConnection");
        }
        callbackContext.success();
    }

    private void downloadTables(JSONArray jsonArray, CallbackContext callbackContext) {
        if (this.configParameter == null) {
            callbackContext.error("Error. You need call 'initialize()' first");
            return;
        }

        this.callbackContext = callbackContext;
        try {
            boolean forceUpdate = false;
            String feedbackMessage = null;

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if (jsonObject.has("forceUpdate")) {
                forceUpdate = jsonObject.getBoolean("forceUpdate");
            }
            if (jsonObject.has("feedbackMessage")) {
                feedbackMessage = jsonObject.getString("feedbackMessage");
                mpos.displayText(feedbackMessage);
            }

            mpos.downloadEMVTablesToDevice(forceUpdate);

            jsonObject = new JSONObject();
            jsonObject.put("code", 21);
            jsonObject.put("message", "updating tables...");
            PluginResult pluginResult = makePluginResult(PluginResult.Status.OK, jsonObject);
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);

        } catch (Exception e) {
            callbackContext.error("Invalid Parameter");
        }
    }

    private void display(JSONArray args, CallbackContext callbackContext) {
        this.callbackContext = callbackContext;

        if (this.configParameter == null) {
            callbackContext.error("Error. You need call 'initialize()' first");
            return;
        }

        try {
            if (mpos != null && args.getString(0) != null) {
                mpos.displayText(args.getString(0).toUpperCase());
            }
            callbackContext.success();
        } catch (JSONException e) {
            callbackContext.error("Invalid Parameter");
        }
    }

    private void pay(JSONArray jsonArray, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;

        if (this.configParameter == null) {
            callbackContext.error("Error. You need call 'initialize()' first");
            return;
        }

        try {
            if (configParameter.enableSafeTransactionConditions()) {
                if (!hasSafeBatteryLevel()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("status", Constants.ERROR_LOW_BATTERY);
                    jsonObject.put("message", "low battery");
                    callbackContext.error(jsonObject);
                    return;
                }

                if (!hasInternetConnection()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("status", Constants.ERROR_CONNECTION);
                    jsonObject.put("message", "no internet connection");
                    callbackContext.error(jsonObject);
                    return;
                }
            }

            if (mpos != null) {
//                paymentParameter = JSON.parseObject(
//                        jsonArray.getJSONObject(0).toString(),
//                        PaymentParameter.class
//                );

                charge = JSON.parseObject(
                        jsonArray.getJSONObject(0).toString(),
                        Charge.class
                );

                int parsedAmount = ((Math.round(Float.parseFloat(charge.getAmount()) * 100) / 100) * 100);

                mpos.payAmount(
                        parsedAmount,
                        charge.getEmvApplications(),
                        charge.getPaymentMethod()
                );
                // TODO payment animations start
            } else {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", Constants.PAYMENT_ERROR_PARAMETERS);
                    jsonObject.put("message", "wrong parameters");

                    PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                    pluginResult.setKeepCallback(false);
                    callbackContext.error(jsonObject);
                } catch (JSONException ex) {
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", Constants.PAYMENT_PROCESSING);
            jsonObject.put("message", "waiting card processing...");

            PluginResult pluginResult = makePluginResult(PluginResult.Status.OK, jsonObject);
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
        } catch (Exception e) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", Constants.PAYMENT_ERROR_PARAMETERS);
                jsonObject.put("message", "wrong parameters");

                PluginResult pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
                pluginResult.setKeepCallback(false);
                callbackContext.error(jsonObject);
            } catch (JSONException ex) {
            }
        }

    }

    private void finish(JSONArray jsonArray, CallbackContext callbackContext) {
        if (this.configParameter == null) {
            callbackContext.error("Error. You need call 'initialize()' first");
            return;
        }

        try {
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            boolean isOnline = true;
            int acquirerResponseCode = 0;
            String cardEmvResponse = "";

            if (jsonObject.has("isOnline")) {
                isOnline = jsonObject.getBoolean("acquirerResponseCode");
            }
            if (jsonObject.has("acquirerResponseCode")) {
                acquirerResponseCode = jsonObject.getInt("acquirerResponseCode");
            }
            if (jsonObject.has("cardEmvResponse")) {
                cardEmvResponse = jsonObject.getString("cardEmvResponse");
            }

            mpos.finishTransaction(isOnline, acquirerResponseCode, cardEmvResponse);

            jsonObject = new JSONObject();
            jsonObject.put("code", 21);
            jsonObject.put("message", "finishing transaction...");

            PluginResult pluginResult = makePluginResult(PluginResult.Status.OK, jsonObject);
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void cancel(JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;

        if (this.configParameter == null) {
            callbackContext.error("Error. You need call 'initialize()' first");
            return;
        }

        mpos.close(args.getString(0));
        callbackContext.success();
    }

    private BluetoothDevice findPairedDevice() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().toUpperCase().startsWith("PAX-")) {
                    pinPad = device;
                    break;
                }
            }
        }
        return pinPad;
    }

    private void showPinPadMessage(String message, int delay) {
        if (mpos != null && !TextUtils.isEmpty(message)) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    mpos.displayText(message.toUpperCase());
                }
            }, delay);
        }
    }

    private void close(String message) {
        mpos.close(message.toUpperCase());
    }

    private void callRemoteServer(Charge charge) {

        AndroidNetworking.post(configParameter.getRemoteApi())
                .addHeaders("X-Api-Key", charge.getApiKey())
                .addHeaders("Authorization", charge.getToken())
                .addJSONObjectBody(charge.toJson())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
//                        Response res = JSON.parseObject(
//                                response.toString(),
//                                Response.class
//                        );
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            if (charge.isOnline()) {
                                mpos.finishTransaction(
                                        true,
//                                    Integer.parseInt(res.getData().getAcquirerResponseCode()),
                                        Integer.parseInt(jsonObject.getString("acquirer_response_code")),
//                                    res.getData().getCardEmvResponse()
                                        jsonObject.getString("card_emv_response")
                                );
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        showPinPadMessage(configParameter.getMessages().getSuccessPayment(), 0);
                        showPinPadMessage("remova o cartao", 2500);
                        showPinPadMessage(configParameter.getMessages().getAppName(), 5500);

                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("code", Constants.PAYMENT_SUCCESSFUL);
                            jsonObject.put("message", "payment successfully");

                            PluginResult pluginResult = makePluginResult(PluginResult.Status.OK, jsonObject);
                            pluginResult.setKeepCallback(false);
                            callbackContext.sendPluginResult(pluginResult);
                        } catch (JSONException e) {
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e(TAG, error.getErrorCode() + " " + error.getErrorBody());
                        showPinPadMessage(configParameter.getMessages().getErrorPayment(), 0);
                        showPinPadMessage(configParameter.getMessages().getAppName(), 5000);
//                            close("Finished transaction");
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("code", Constants.PAYMENT_ERROR);
                            jsonObject.put("message", error.getErrorBody());

                            PluginResult pluginResult = makePluginResult(PluginResult.Status.OK, jsonObject);
                            pluginResult.setKeepCallback(false);
                            callbackContext.error(jsonObject);
                        } catch (JSONException e) {
                        }
                    }
                });

        this.charge = null;
    }

    public PluginResult makePluginResult(PluginResult.Status status, Object... args) {
        if (args.length == 0) {
            return new PluginResult(status);
        }
        if (args.length == 1) {
            Object args0 = args[0];
            if (args0 instanceof JSONObject) {
                return new PluginResult(status, (JSONObject) args0);
            }
            if (args0 instanceof JSONArray) {
                return new PluginResult(status, (JSONArray) args0);
            }
            if (args0 instanceof Integer) {
                return new PluginResult(status, (Integer) args0);
            }
            if (args0 instanceof String) {
                return new PluginResult(status, (String) args0);
            }
        }

        JSONArray result = new JSONArray();
        for (Object arg : args) {
            result.put(arg);
        }
        return new PluginResult(status, result);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName().toUpperCase().startsWith("PAX-")) {
                    pinPad = device;
                    connectPinPad(null, callbackContext);
                    bluetoothAdapter.cancelDiscovery();
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        isRunning = false;
        bluetoothAdapter.cancelDiscovery();
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void getFile(JSONArray args, CallbackContext callbackContext) {
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.setType("*/*");
//        getApplicationContext().startActivityForResult(Intent.createChooser(i, "select file"), 12);
        callbackContext.success();
    }

}