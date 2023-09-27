package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bulk_order_dashboard_Response {
    @SerializedName("can_place_order")
    @Expose
    private Integer canPlaceOrder;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getCanPlaceOrder() {
        return canPlaceOrder;
    }

    public void setCanPlaceOrder(Integer canPlaceOrder) {
        this.canPlaceOrder = canPlaceOrder;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
