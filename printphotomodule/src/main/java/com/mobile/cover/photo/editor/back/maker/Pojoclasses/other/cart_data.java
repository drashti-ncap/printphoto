package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class cart_data {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cart")
    @Expose
    private Cart_total_value Cart_total_value;

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

    public Cart_total_value getCart_total_value() {
        return Cart_total_value;
    }

    public void setCart_total_value(Cart_total_value Cart_total_value) {
        this.Cart_total_value = Cart_total_value;
    }
}
