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

package com.gurpster.cordova.pagarme.mpos.withinterface;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import org.apache.cordova.CordovaActivity;

public class MainActivity extends CordovaActivity {

    private MposService mposService = null;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MposService.ServiceBinder binder = (MposService.ServiceBinder) service;
            App.getInstance().setMposService(binder.getService());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);

        if (!isMyServiceRunning(MposService.class) || mposService == null) {
            startService();
        }
    }

    private void startService() {
        Intent intent = new Intent(MainActivity.this, MposService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(MainActivity.this, MposService.class);
        stopService(intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {

        if (mposService == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return MposService.isRunning;
        } else {
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
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
        super.onStop();
    }
}
