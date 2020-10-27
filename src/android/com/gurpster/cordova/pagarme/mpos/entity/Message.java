package com.gurpster.cordova.pagarme.mpos.entity;

import android.text.TextUtils;

public class Message {

    private String appName;
    private String successPayment = "TRANSACAO APROVADA";
    private String errorPayment = "TRANSACAO NAO APROVADA";
    private String errorBluetooth;
    private String disconnectedBluetooth;
    private String tableUpdated;
    private String errorDefault;
    private String finishedTransaction;
    private String processingTransaction;
    private String cancel = "CANCELED OPERATION";

    public Message() {
    }

    public String getSuccessPayment() {
        return successPayment;
    }

    public void setSuccessPayment(String successPayment) {
        this.successPayment = successPayment;
    }

    public String getErrorPayment() {
        return errorPayment;
    }

    public void setErrorPayment(String errorPayment) {
        this.errorPayment = errorPayment;
    }

    public String getErrorBluetooth() {
        return errorBluetooth;
    }

    public void setErrorBluetooth(String errorBluetooth) {
        this.errorBluetooth = errorBluetooth;
    }

    public String getAppName() {
        return TextUtils.isEmpty(errorPayment) ? "Pin Pad" : appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDisconnectedBluetooth() {
        return TextUtils.isEmpty(disconnectedBluetooth) ? "Bluetooth disconnected" : disconnectedBluetooth;
    }

    public void setDisconnectedBluetooth(String disconnectedBluetooth) {
        this.disconnectedBluetooth = disconnectedBluetooth;
    }

    public String getTableUpdated() {
        return TextUtils.isEmpty(tableUpdated) ? "table updated" : tableUpdated;
    }

    public void setTableUpdated(String tableUpdated) {
        this.tableUpdated = tableUpdated;
    }

    public String getErrorDefault() {
        return errorDefault;
    }

    public void setErrorDefault(String errorDefault) {
        this.errorDefault = errorDefault;
    }

    public String getFinishedTransaction() {
        return finishedTransaction;
    }

    public void setFinishedTransaction(String finishedTransaction) {
        this.finishedTransaction = finishedTransaction;
    }

    public String getProcessingTransaction() {
        return TextUtils.isEmpty(tableUpdated) ? "processing transaction" : processingTransaction;
    }

    public void setProcessingTransaction(String processingTransaction) {
        this.processingTransaction = processingTransaction;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public static String getErrorFromCode(int code) {
        switch (code) {
            case -2:
                return "Usuário inválido";
            case 10:
                return "";
            case 12:
                return "Tempo de resposta excedido";
            case 20:
                return "Pin Pad não atualizado";
            case 60:
                return "Cartão com problema";
            case -1:
            case 90:
                return "Erro inesperado, contate o suporte";
            case 70:
                return "Bandeira não suportada";
            case 42:
                return "Não foi possível se conectar";
            default:
                return "Error " + code;
        }
    }
}