package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class paypal_model {
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("data")
    @Expose
    private Paypal_data_model data;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Paypal_data_model getData() {
        return data;
    }

    public void setData(Paypal_data_model data) {
        this.data = data;
    }
}
