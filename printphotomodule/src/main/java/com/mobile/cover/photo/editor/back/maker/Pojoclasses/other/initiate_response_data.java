package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class initiate_response_data {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("paypal_redirect_url")
    @Expose
    private String paypal_redirect_url;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaypal_redirect_url() {
        return paypal_redirect_url;
    }

    public void setPaypal_redirect_url(String paypal_redirect_url) {
        this.paypal_redirect_url = paypal_redirect_url;
    }
}
