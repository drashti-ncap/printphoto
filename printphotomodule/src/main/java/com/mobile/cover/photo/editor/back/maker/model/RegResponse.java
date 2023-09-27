package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegResponse implements Serializable {
    @SerializedName("ResponseCode")
    String ResponseCode;

    @SerializedName("ResponseMessage")
    String ResponseMessage;

    @SerializedName("cart_count")
    Integer cart_count;


    @SerializedName("data")
    RegData data;

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

    public Integer getCart_count() {
        return cart_count;
    }

    public void setCart_count(Integer cart_count) {
        this.cart_count = cart_count;
    }

    public RegData getData() {
        return data;
    }

    public void setData(RegData data) {
        this.data = data;
    }
}
