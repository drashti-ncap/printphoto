package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.cart_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.get_cart_data;

import java.io.Serializable;

public class get_Cart implements Serializable {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("data")
    @Expose
    private get_cart_data cart_data;

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

    public get_cart_data getcart_data() {
        return cart_data;
    }

    public void setcart_data(get_cart_data data) {
        this.cart_data = data;
    }
}
