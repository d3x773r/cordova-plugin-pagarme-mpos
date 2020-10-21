package com.gurpster.cordova.pagarme.mpos.withinterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by Williaan Souza (dextter) on 16/10/2020
 * Contact williaanlopes@gmail.com
 */
public class FinishActivity extends AppCompatActivity {

    private LinearLayout rootView;
    private AppCompatButton action;
    private AppCompatTextView title;
    private AppCompatTextView description;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        rootView = findViewById(R.id.root);
        action = findViewById(R.id.action);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        animationView = findViewById(R.id.animation_view);

        if (getIntent().hasExtra("type")) {
            switch (getIntent().getIntExtra("type", 0)) {
                case 0:
                    error();
                    break;
                case 1:
                    success();
                    break;
            }
        }
    }

    void success() {
        int color = ContextCompat.getColor(
                getApplicationContext(),
                R.color.white
        );
        rootView.setBackgroundColor(ContextCompat.getColor(
                getApplicationContext(),
                R.color.greener
        ));
        title.setText("PAGAMENTO REALIZADO");
        title.setTextColor(color);
        description.setText(getString(R.string.payment_successfully));
        description.setTextColor(color);
        animationView.setAnimationFromUrl("https://assets1.lottiefiles.com/datafiles/OhIfcxnkLsj1Jxj/data.json");

        action.setTextColor(color);
        action.setBackgroundResource(R.drawable.rect_green_border_white);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void error() {
        int color = ContextCompat.getColor(
                getApplicationContext(),
                R.color.black_title
        );
        rootView.setBackgroundColor(ContextCompat.getColor(
                getApplicationContext(),
                R.color.colorApp
        ));
        title.setText("ERRO");
        title.setTextColor(color);
        description.setText(getIntent().getStringExtra("message"));
        description.setTextColor(color);
        animationView.setAnimationFromUrl("https://assets10.lottiefiles.com/packages/lf20_KWZHHd.json");

        action.setTextColor(ContextCompat.getColor(
                getApplicationContext(),
                R.color.colorApp
        ));
        action.setBackgroundResource(R.drawable.border_red);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chargeIntent = new Intent();
                chargeIntent.putExtra("data", getIntent().getStringExtra("data"));
                startActivity(chargeIntent);
                finish();
            }
        });
    }
}