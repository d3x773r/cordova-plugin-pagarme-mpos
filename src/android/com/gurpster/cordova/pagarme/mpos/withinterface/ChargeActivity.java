package me.pagar.mposandroidexample;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import me.pagar.mposandroid.PaymentMethod;

import static android.bluetooth.BluetoothProfile.GATT;

public class ChargeActivity extends AppCompatActivity {

    private long mLastClickTime = 0;

    private Charge charge;

    private AppCompatButton actionPay;
    private List<PaymentParameter.EmvApplication> emvApplications;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra("data")) {
            charge = JSON.parseObject(
                    getIntent().getStringExtra("data"),
                    Charge.class
            );
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        bingView();
    }


    @SuppressLint("RestrictedApi")
    private void bingView() {

        emvApplications = new ArrayList<>();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        actionPay = findViewById(R.id.action_pay);
        actionPay.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.disable_title));
        actionPay.setBackgroundResource(R.drawable.round_rect_grey);

        actionPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (!emvApplications.isEmpty()) {
                    if (bluetoothAdapter.isEnabled() && MposService.isMposConnected) {
                        makeCharge();
                    } else {
                        Intent pairingIntent = new Intent(
                                getApplicationContext(),
                                PairingActivity.class
                        );
                        pairingIntent.putExtra("encryption_key", charge.getEncryptionKey());
                        startActivityForResult(pairingIntent, PairingActivity.PAIRING_REQUEST_RESULT_CODE);
                    }
                }
            }
        });

        RadioGroup radioGroup = findViewById(R.id.payment_methods);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                emvApplications.clear();

                if (!actionPay.isEnabled()) {
                    actionPay.setEnabled(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            actionPay.setTextColor(ContextCompat.getColor(
                                    getApplicationContext(),
                                    R.color.white
                            ));
                            actionPay.setBackgroundResource(R.drawable.round_rect_red);
                        }

                    }, 50);
                }

                if (checkedId == R.id.credit) {
                    emvApplications.add(new PaymentParameter.EmvApplication(
                            PaymentMethod.CreditCard,
                            charge.getCardBrand().toLowerCase()
                    ));
                    charge.setEmvApplications(emvApplications);
                } else if (checkedId == R.id.debit) {
                    emvApplications.add(new PaymentParameter.EmvApplication(
                            PaymentMethod.DebitCard,
                            charge.getCardBrand().toLowerCase()
                    ));
                    charge.setEmvApplications(emvApplications);
                }
            }
        });
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        ContextCompat.getColor(getApplicationContext(), R.color.disable_background),
                        ContextCompat.getColor(getApplicationContext(), R.color.colorApp),
                }
        );
        ((AppCompatRadioButton) findViewById(R.id.credit)).setSupportButtonTintList(colorStateList);
        ((AppCompatRadioButton) findViewById(R.id.debit)).setSupportButtonTintList(colorStateList);

        AppCompatTextView merchantName = findViewById(R.id.merchant_name);
        AppCompatTextView orderId = findViewById(R.id.order_id);
        AppCompatTextView orderPay = findViewById(R.id.order_pay);

        merchantName.setText(charge.getMerchantName());
        orderId.setText(String.valueOf(charge.getOrderId()));
        orderPay.setText("R$ " + charge.getAmountFormatted());
    }

    private void makeCharge() {
        if (charge != null) {
            Intent processingIntent = new Intent(
                    ChargeActivity.this,
                    ProcessActivity.class
            );
            processingIntent.putExtra("data", JSON.toJSONString(charge));
            startActivity(processingIntent);
            finish();
        }
    }

    public boolean isConnected() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        List<BluetoothDevice> connectedDevices = bluetoothManager.getConnectedDevices(GATT);
        for (BluetoothDevice device : connectedDevices) {
            if (device.getName().toUpperCase().startsWith("PAX-")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == PairingActivity.PAIRING_REQUEST_RESULT_CODE) {
            makeCharge();
        }
    }
}