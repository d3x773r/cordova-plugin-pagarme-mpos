package com.gurpster.cordova.pagarme.mpos.withinterface;

import android.app.Application;

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

    @Override
    public void onCreate() {
        super.onCreate();

        if (sInstance == null) {
            sInstance = App.this;
        }

    }

    public MposService getMposService() {
        return mposService;
    }

    public void setMposService(MposService mposService) {
        this.mposService = mposService;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}