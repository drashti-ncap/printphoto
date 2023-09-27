package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getoffer {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("offer_code")
    @Expose
    private String offerCode;
    @SerializedName("offer_image")
    @Expose
    private String offerImage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount")
    @Expose
    private String amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getamount() {
        return amount;
    }

    public void setamount(String amount) {
        this.amount = amount;
    }

}
