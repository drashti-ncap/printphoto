package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paypal_data_model {
    @SerializedName("redirect_url")
    @Expose
    private String redirectUrl;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
