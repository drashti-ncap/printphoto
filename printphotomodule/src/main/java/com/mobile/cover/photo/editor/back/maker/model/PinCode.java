package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PinCode implements Serializable {

    @SerializedName("ResponseCode")
    String ResponseCode;

    @SerializedName("ResponseMessage")
    String ResponseMessage;

    @SerializedName("data")
    PinCodeData pinCodeData;

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        ResponseMessage = responseMessage;
    }

    public PinCodeData getPinCodeData() {
        return pinCodeData;
    }

    public void setPinCodeData(PinCodeData pinCodeData) {
        this.pinCodeData = pinCodeData;
    }


}
