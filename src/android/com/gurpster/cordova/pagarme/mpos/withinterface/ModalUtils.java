package me.pagar.mposandroidexample;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by Williaan Souza (dextter) on 19/10/2020
 * Contact williaanlopes@gmail.com
 */
public class ModalUtils extends AlertDialog {

    private AppCompatButton actionCancel;
    private AppCompatButton actionConfirm;

    protected ModalUtils(@NonNull Context context) {
        super(context);
    }

    protected ModalUtils(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ModalUtils(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * Add your custom layout interface
     * @param layout
     * @return ModalUtils
     */
    public ModalUtils setLayout(@LayoutRes int layout) {
        this.setContentView(layout);
        return this;
    }

    /**
     * Disable cancel button
     * @return ModalUtils
     */
    public ModalUtils hideCancel() {
        actionCancel.setVisibility(View.GONE);
        actionCancel.setEnabled(false);
        return this;
    }

    public ModalUtils showCancel() {
        actionCancel.setVisibility(View.VISIBLE);
        actionCancel.setEnabled(true);
        return this;
    }

    public ModalUtils hideConfirm() {
        actionConfirm.setVisibility(View.GONE);
        actionConfirm.setEnabled(false);
        return this;
    }

    public ModalUtils showConform() {
        actionConfirm.setVisibility(View.VISIBLE);
        actionConfirm.setEnabled(true);
        return this;
    }

    /**
     * Wait time to display modal
     * @param delay
     * @return ModalUtils
     */
    public ModalUtils setDelay(int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ModalUtils.this.show();
            }
        }, delay);
        return this;
    }

    /**
     * Wait time to dismiss modal
     * @param timeOut
     * @return ModalUtils
     */
    public ModalUtils setTimeOut(int timeOut) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ModalUtils.this.dismiss();
            }
        }, timeOut);
        return this;
    }

    public static AlertDialog showAnimatedDialog(@NonNull Context context, @LayoutRes int layout, String myTitle, String animationURL) {
        final LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setView(deleteDialogView);

        AppCompatTextView title = deleteDialogView.findViewById(R.id.title);
        title.setText(myTitle);

        LottieAnimationView lottieAnimationView = deleteDialogView.findViewById(R.id.animation_view);
        lottieAnimationView.setAnimationFromUrl(animationURL);
        lottieAnimationView.setSpeed(3);

        return dialog;
    }


    public static AlertDialog showTextDialog(@NonNull Context context, @LayoutRes int layout, String myTitle, String content, boolean canCancel, boolean canConfirm) {
        final LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setView(deleteDialogView);

        AppCompatTextView title = deleteDialogView.findViewById(R.id.title);
        title.setText(myTitle);

        AppCompatTextView description = deleteDialogView.findViewById(R.id.description);
        description.setText(content);

//        AppCompatButton actionCancel = deleteDialogView.findViewById(R.id.action_cancel);
//        if (!canCancel) {
//            actionCancel.setVisibility(View.GONE);
//        }

        AppCompatButton actionConfirm = deleteDialogView.findViewById(R.id.action_confirm);
        actionConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        if (!canConfirm) {
//            actionConfirm.setVisibility(View.GONE);
//        }

        return dialog;
    }

}
