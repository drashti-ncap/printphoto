package com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class response_data {
    @SerializedName("order")
    @Expose
    private String order;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
