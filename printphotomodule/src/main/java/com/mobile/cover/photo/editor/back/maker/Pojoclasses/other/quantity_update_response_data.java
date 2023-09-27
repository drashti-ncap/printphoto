package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class quantity_update_response_data {

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("cart_total")
    @Expose
    private Double cart_total;

    @SerializedName("sub_total")
    @Expose
    private Double sub_total;

    public Double getSub_total() {
        return sub_total;
    }

    public void setSub_total(Double sub_total) {
        this.sub_total = sub_total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Double getcart_total() {
        return cart_total;
    }

    public void setcart_total(Double cart_total) {
        this.cart_total = cart_total;
    }

}
