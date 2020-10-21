package com.gurpster.cordova.pagarme.mpos.withinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Williaan Souza (dextter) on 16/10/2020
 * Contact williaanlopes@gmail.com
 */
public class ProcessActivity extends AppCompatActivity {

    private MposService service;
    private Charge charge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);
        service = App.getInstance().getMposService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("data")) {
            charge = JSON.parseObject(getIntent().getStringExtra("data"), Charge.class);
            service.pay(charge);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MposService.FinishEvent event) {
//        if (event.isError()) {
//            Intent chargeIntent = new Intent(
//                    ProcessActivity.this,
//                    ChargeActivity.class
//            );
//            chargeIntent.putExtra("data", JSON.toJSONString(charge));
//            startActivity(chargeIntent);
//        } else {
        Intent finishIntent = new Intent(
                ProcessActivity.this,
                FinishActivity.class
        );
        finishIntent.putExtra("data", JSON.toJSONString(charge));
        finishIntent.putExtra("type", event.isError() ? 0 : 1);
        finishIntent.putExtra("message", event.getMessage());
        startActivity(finishIntent);
//        }
        finish();
    }

}