package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferCodeData {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("offer_code")
    @Expose
    private String offerCode;

    @SerializedName("amount")
    @Expose
    private String amount;

    public int getId() {
        return id;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public String getAmount() {
        return amount;
    }
}
