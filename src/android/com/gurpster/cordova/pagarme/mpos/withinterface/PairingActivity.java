package com.gurpster.cordova.pagarme.mpos.withinterface;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.gurpster.cordova.pagarme.mpos.withinterface.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.pagar.mposandroid.Mpos;

public class PairingActivity extends AppCompatActivity {

    public static final int PAIRING_REQUEST_CODE = 0x985;
    public static final int PAIRING_REQUEST_RESULT_CODE = 0x475;
    private long mLastClickTime = 0;

    private AppCompatTextView title;
    private AppCompatTextView description;
    private AppCompatButton action;
    private AppCompatImageView icon;

    private BluetoothDevice device;
    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> devices = new ArrayList<>();
    private MposService service;

    private String encryptionKey;

    private View.OnClickListener connect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            if (!bluetoothAdapter.isEnabled()) {
                enableBluetooth();
            } else {
                showDevicesDialog();
            }
        }
    };

    private View.OnClickListener disconnect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            if (bluetoothAdapter.isEnabled() && App.getInstance().getMposService() != null) {
                service.close();
            }
        }
    };

    private final Handler waitingHandler = new Handler();

    private AlertDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (getIntent().hasExtra("encryption_key")) {
            this.encryptionKey = getIntent().getStringExtra("encryption_key");
        }

        service = App.getInstance().getMposService();

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        action = findViewById(R.id.action);
        icon = findViewById(R.id.icon);

        bindView();
    }

    private void enableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, PAIRING_REQUEST_CODE);
    }

    private void bindView() {
        if (bluetoothAdapter.isEnabled() && service.isMposConnected()) {
            title.setText(getString(R.string.device_paired_title));
            description.setText(Html.fromHtml(getString(R.string.device_paired_description, device.getName())));
            action.setText(getString(R.string.device_paired_action));
            action.setOnClickListener(disconnect);

            Glide.with(getApplicationContext()).load(R.mipmap.frame_2).into(icon);

            if (getCallingActivity() != null) {
                waitingHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent result = new Intent();
                        result.putExtra("pin_pad", device.getName());
                        setResult(RESULT_OK, result);
                        finish();
                    }
                }, 1000);

            }

        } else {
            title.setText(getString(R.string.not_device_paired_title));
            description.setText(getString(R.string.not_device_paired_description));
            action.setText(getString(R.string.not_device_paired_action));
            action.setOnClickListener(connect);
            Glide.with(getApplicationContext()).load(R.mipmap.frame_1).into(icon);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == PAIRING_REQUEST_CODE) {
            showDevicesDialog();
        } else {
            ModalUtils.showTextDialog(
                    PairingActivity.this,
                    R.layout.dialog_text,
                    getString(R.string.error),
                    getString(R.string.not_device_paired_error_default),
                    false,
                    true
            ).show();
        }
    }

    @SuppressLint("RestrictedApi")
    private void showDevicesDialog() {

        devices.clear();

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().toUpperCase().startsWith("PAX-")) {
                    devices.add(device);
                }
            }
        }

        final LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.dialog_radio_list, null);

        LayoutInflater inflater = getLayoutInflater();

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setView(deleteDialogView);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        AppCompatTextView title = deleteDialogView.findViewById(R.id.title);
        title.setText(getString(R.string.discovered_devices_title));

        RadioGroup group = deleteDialogView.findViewById(R.id.list);
        for (BluetoothDevice device : devices) {
            AppCompatRadioButton choice = (AppCompatRadioButton) inflater.inflate(
                    R.layout.item_radio,
                    null
            );
            choice.setLayoutParams(new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            choice.setId(View.generateViewId());
            choice.setText(device.getName());
            choice.setTag(device.getAddress());
            group.addView(choice);

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
            choice.setSupportButtonTintList(colorStateList);
        }

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                device = bluetoothAdapter.getRemoteDevice((String) radioButton.getTag());
            }
        });

        View actionCancel = deleteDialogView.findViewById(R.id.action_cancel);
        actionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        View actionConfirm = deleteDialogView.findViewById(R.id.action_confirm);
        actionConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                waitingDialog = ModalUtils.showAnimatedDialog(
                        PairingActivity.this,
                        R.layout.dialog_lottie_animated,
                        getString(R.string.connect_devices_title),
                        "https://assets2.lottiefiles.com/datafiles/hxzaM4QqYNp5duh/data.json"
                );
                try {
                    final Mpos mpos = new Mpos(
                            device,
                            encryptionKey,
                            PairingActivity.this
                    );
                    service.setMpos(mpos);
                    waitingHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            waitingDialog.dismiss();
                            waitingHandler.removeCallbacks(this);
                            ModalUtils.showTextDialog(
                                    PairingActivity.this,
                                    R.layout.dialog_text,
                                    getString(R.string.error),
                                    getString(R.string.not_device_paired_error_default),
                                    false,
                                    true
                            ).show();
                        }
                    }, 50000);
                    service.connect();
                } catch (IOException e) {
                    waitingDialog.dismiss();
                    ModalUtils.showTextDialog(
                            PairingActivity.this,
                            R.layout.dialog_text,
                            getString(R.string.error),
                            getString(R.string.not_device_paired_error_default),
                            false,
                            true
                    ).show();
                }
                waitingDialog.show();
            }
        });
        dialog.show();
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
    public void onMessageEvent(MposService.ConnectedEvent event) {
        bindView();
        waitingDialog.dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MposService.NotConnectedEvent event) {
        waitingDialog.dismiss();
        ModalUtils.showTextDialog(
                PairingActivity.this,
                R.layout.dialog_text,
                getString(R.string.error),
                getString(R.string.not_device_paired_error_default),
                false,
                true
        ).show();
    }
}