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

import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.gurpster.cordova.pagarme.mpos.withinterface.App;
import com.gurpster.cordova.pagarme.mpos.withinterface.ChargeActivity;
import com.gurpster.cordova.pagarme.mpos.withinterface.MposService;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.BIND_AUTO_CREATE;
import static org.apache.cordova.media.AudioHandler.permissions;

public class MposPlugin extends CordovaPlugin {

    private static final String TAG = MposPlugin.class.getSimpleName();

    private MposPluginService mposPluginService;
    private JSONArray requestArgs;
    private CallbackContext callbackContext;
    private String firedAction;
    private PluginResult pluginResult;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MposPluginService.MposPluginServiceBinder binder = (MposPluginService.MposPluginServiceBinder) service;
            MposPlugin.this.mposPluginService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private final ServiceConnection connectionWithInterface = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MposService.ServiceBinder binder = (MposService.ServiceBinder) service;
            MposService mposService = binder.getService();
            App.getInstance().setMposService(mposService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void pluginInitialize() {
        if (!isMyServiceRunning(MposPluginService.class) || mposPluginService == null) {
            startService();
            startServiceInterface();
        }
        super.pluginInitialize();
    }

    public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {

        this.callbackContext = callbackContext;
        this.requestArgs = args;
        this.firedAction = action;

        if (action.equals("payWithInterface")) {
            Intent chargeIntent = new Intent(
                    cordova.getActivity(),
                    ChargeActivity.class
            );
//            Charge charge = JSON.parseObject(
//                    getIntent().getStringExtra("data"),
//                    Charge.class
//            );

            chargeIntent.putExtra("data", args.getJSONObject(0).toString());
            cordova.startActivityForResult(this, chargeIntent, 847);
            return true;
        }

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            callbackContext.error("error.toString()");
            return false;
        }

        if (args != null) {

            if (!bluetoothAdapter.isEnabled()) {
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("status", 41);
                    jsonObject.put("message", "Error bluetooth not enabled");
                    callbackContext.error(jsonObject);
                } catch (Exception e) {
                }

//                this.pluginResult = PluginResultHelper.makePluginResult(PluginResult.Status.OK, jsonObject);
//                pluginResult.setKeepCallback(true);
//                callbackContext.sendPluginResult(pluginResult);

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                cordova.getActivity().startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT);
            } else {
                if (!isMyServiceRunning(MposPluginService.class) || mposPluginService == null) {
                    startService();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            invokeMethod(action, args, callbackContext);
                        }
                    }, 500);
                } else {
                    invokeMethod(action, args, callbackContext);
                }
            }

        } else {
            callbackContext.error("Invalid parameters");
        }
        return true;
    }

    /**
     * check application's permissions
     */
    public boolean hasPermission() {
        for (String p : permissions) {
            if (!PermissionHelper.hasPermission(this, p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * We override this so that we can access the permissions variable, which no longer exists in
     * the parent class, since we can't initialize it reliably in the constructor!
     *
     * @param requestCode The code to get request action
     */
    public void requestPermissions(int requestCode) {
        PermissionHelper.requestPermissions(this, requestCode, permissions);
    }

    /**
     * processes the result of permission request
     *
     * @param requestCode  The code to get request action
     * @param permissions  The collection of permissions
     * @param grantResults The result of grant
     */
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        PluginResult result;
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                Log.d(TAG, "Permission Denied!");
                result = new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
                this.callbackContext.sendPluginResult(result);
                return;
            }
        }

        if (requestCode == 0) {
            initialize(this.requestArgs);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_ENABLE_BT) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    invokeMethod(firedAction, requestArgs, callbackContext);
                }
            }, 500);
        } else if (resultCode == RESULT_OK && requestCode == 847) {
            this.callbackContext.success();
        } else if (resultCode == RESULT_CANCELED && requestCode == 847) {
            this.callbackContext.error(Constants.PAYMENT_ERROR);
        } else {
            this.callbackContext.error("Bluetooth not enabled");
        }
    }

    private void initialize(final JSONArray jsonArray) {
        invokeMethod("initialize", jsonArray, callbackContext);
    }

    private void invokeMethod(String methodName, JSONArray jsonArray, CallbackContext callbackContext) {
        if (isMyServiceRunning(MposPluginService.class) && mposPluginService != null) {
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Method method = mposPluginService.getClass().getDeclaredMethod(
                                methodName,
                                jsonArray.getClass(),
                                callbackContext.getClass()
                        );
                        method.setAccessible(true);
                        method.invoke(
                                mposPluginService,
                                jsonArray,
                                callbackContext
                        );
                    } catch (Exception e) {
                        callbackContext.error(e.getMessage());
                    }
                }
            });
        }
    }

    private void startService() {
        Activity context = cordova.getActivity();
        Intent intent = new Intent(context, MposPluginService.class);
        context.bindService(intent, connection, BIND_AUTO_CREATE);
        context.startService(intent);
    }

    private void stopService() {
        Activity context = cordova.getActivity();
        Intent intent = new Intent(context, MposPluginService.class);
        context.stopService(intent);
    }

    private void startServiceInterface() {
        Activity context = cordova.getActivity();
        Intent intent = new Intent(context, MposService.class);
        context.bindService(intent, connection, BIND_AUTO_CREATE);
        context.startService(intent);
    }

    private void stopServiceInterface() {
        Activity context = cordova.getActivity();
        Intent intent = new Intent(context, MposService.class);
        context.stopService(intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {

        if (mposPluginService == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mposPluginService.isRunning;
        } else {
            ActivityManager manager = (ActivityManager) cordova.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void onStop() {
        stopService();
        stopServiceInterface();
        super.onStop();
    }
}