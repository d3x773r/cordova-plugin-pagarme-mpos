package com.gurpster.cordova.pagarme.mpos.withinterface;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;

import me.pagar.mposandroid.Mpos;

/**
 * Created by Williaan Souza (dextter) on 15/10/2020
 * Contact williaanlopes@gmail.com
 */
public class App extends Application {

    private static App sInstance;

    public static App getInstance() {
        return sInstance;
    }

    private MposService mposService = null;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MposService.ServiceBinder binder = (MposService.ServiceBinder) service;
            App.this.mposService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        if (sInstance == null) {
            sInstance = App.this;
        }

        if (!isMyServiceRunning(MposService.class) || mposService == null) {
            startService();
        }
    }

    public MposService getMposService() {
        return mposService;
    }

    public void setMposService(MposService mposService) {
        this.mposService = mposService;
    }

    private void startService() {
        Intent intent = new Intent(App.this, MposService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        startService(intent);
    }

    private void stopService() {
        Intent intent = new Intent(App.this, MposService.class);
        stopService(intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {

        if (mposService == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mposService.isRunning;
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
    public void onTerminate() {
        stopService();
        super.onTerminate();
    }
}