package me.pagar.mposandroidexample.listeners;

import me.pagar.mposandroid.MposListener;
import me.pagar.mposandroid.MposPaymentResult;

/**
 * Created by Williaan Souza (dextter) on 15/10/2020
 * Contact williaanlopes@gmail.com
 */
public abstract class MposCallback implements MposListener {

    @Override
    public void bluetoothConnected() {

    }

    @Override
    public void bluetoothDisconnected() {

    }

    @Override
    public void bluetoothErrored(int i) {

    }

    @Override
    public void receiveInitialization() {

    }

    @Override
    public void receiveClose() {

    }

    @Override
    public void receiveNotification(String s) {

    }

    @Override
    public void receiveOperationCompleted() {

    }

    @Override
    public void receiveOperationCancelled() {

    }

    @Override
    public void receiveCardHash(String s, MposPaymentResult mposPaymentResult) {

    }

    @Override
    public void receiveTableUpdated(boolean b) {

    }

    @Override
    public void receiveFinishTransaction() {

    }

    @Override
    public void receiveError(int i) {

    }
}
