package com.gurpster.cordova.pagarme.mpos.withinterface;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import me.pagar.mposandroid.Mpos;
import me.pagar.mposandroid.MposPaymentResult;
import me.pagar.mposandroidexample.listeners.MposCallback;
import com.gurpster.cordova.pagarme.mpos.entity.Charge;

/**
 * Created by Williaan Souza (dextter) on 16/10/2020
 * Contact williaanlopes@gmail.com
 */
public class MposService extends Service {

    private static final String TAG = MposService.class.getSimpleName();

    private final IBinder binder = new ServiceBinder();

    private Mpos mpos;
    public static boolean isRunning;
    public static boolean isMposConnected;

    @Override
    public IBinder onBind(Intent intent) {
        AndroidNetworking.initialize(getApplicationContext());
        isRunning = true;
        return binder;
    }

    public class ServiceBinder extends Binder {
        MposService getService() {
            return MposService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void setMpos(Mpos mpos) {
        this.mpos = mpos;
    }

    public void downloadTables(boolean force) {
        try {
            mpos.downloadEMVTablesToDevice(force);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void display(String message) {
        try {
            if (mpos != null && !TextUtils.isEmpty(message)) {
                mpos.displayText(message.toUpperCase());
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void display(final String message, int delay) {
        if (mpos != null && !TextUtils.isEmpty(message)) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    mpos.displayText(message.toUpperCase());
                }
            }, delay);
        }
    }

    public void close() {
        mpos.closeConnection();
    }

    public void close(String message) {
        mpos.close(message.toUpperCase());
    }

    public void connect() {
        mpos.addListener(new MposCallback() {
            @Override
            public void bluetoothConnected() {
                super.bluetoothConnected();
                mpos.initialize();
                isMposConnected = true;
            }

            @Override
            public void receiveInitialization() {
                super.receiveInitialization();
                display(getString(R.string.pin_pad_name));
                mpos.downloadEMVTablesToDevice(false);
                display("configurando...", 1000);
            }

            @Override
            public void receiveTableUpdated(boolean b) {
                super.receiveTableUpdated(b);
                EventBus.getDefault().post(new ConnectedEvent());
                display(getString(R.string.pin_pad_name));
            }

            @Override
            public void receiveError(int i) {
                super.receiveError(i);
                EventBus.getDefault().post(new NotConnectedEvent());
            }
        });
        mpos.openConnection(false);
    }

    public void pay(final Charge charge) {
        mpos.addListener(new MposCallback() {
            @Override
            public void receiveError(int i) {
                super.receiveError(i);
                String message = Message.getErrorFromCode(i);
                display(message);
                display(getString(R.string.pin_pad_name), 7000);
                EventBus.getDefault().post(new FinishEvent(true, message));
            }

            @Override
            public void receiveCardHash(String cardHash, MposPaymentResult mposPaymentResult) {
                super.receiveCardHash(cardHash, mposPaymentResult);
                charge.setCardHash(cardHash);
                charge.setOnline(mposPaymentResult.isOnline);
                callRemoteServer(charge);
            }
        });
        mpos.payAmount(
                charge.getAmountInt(),
                charge.getEmvApplications(),
                charge.getPaymentMethod()
        );
        display("processando...");
    }

    private void callRemoteServer(final Charge charge) {
        ANRequest.PostRequestBuilder<?> requestBuilder = AndroidNetworking.post(charge.getRemoteApi());

//        requestBuilder.addHeaders(charge.getParams());
        requestBuilder.addHeaders("X-Api-Key", charge.getApiKey());
        requestBuilder.addHeaders("Authorization", charge.getToken());

//        requestBuilder.addBodyParameter(charge.getParams());
        requestBuilder.addJSONObjectBody(charge.toJson());

        requestBuilder.setPriority(Priority.HIGH);
        ANRequest<?> request = requestBuilder.build();
        request.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
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

                    EventBus.getDefault().post(new FinishEvent(false));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                display("finalizado");
                display("remova o cartao", 2500);
                display(getString(R.string.pin_pad_name), 5500);
            }

            @Override
            public void onError(ANError error) {
                Log.d("", error.getErrorBody());
                EventBus.getDefault().post(new FinishEvent(true, "erro de conexao"));
                display("erro de conexao");
                display(getString(R.string.pin_pad_name), 5000);
            }
        });
    }

    @Override
    public void onDestroy() {
        stopSelf();
        super.onDestroy();
    }

    public boolean isMposConnected() {
        return isMposConnected;
    }

    public static class ConnectedEvent {
    }

    public static class NotConnectedEvent {
    }

    public static class FinishEvent {

        private boolean error;
        private String message;

        public FinishEvent() {
        }

        public FinishEvent(boolean error) {
            this.error = error;
        }

        public FinishEvent(boolean error, String message) {
            this.error = error;
            this.message = message;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}