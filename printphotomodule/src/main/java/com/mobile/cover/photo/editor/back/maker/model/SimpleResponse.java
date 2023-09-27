package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.SerializedName;

public class SimpleResponse {

    @SerializedName("ResponseCode")
    String ResponseCode;

    @SerializedName("ResponseMessage")
    String ResponseMessage;


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
}
