package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OfferMain implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("offer_code")
    @Expose
    private String offerCode;
    @SerializedName("n_offer_image")
    @Expose
    private String nOfferImage;
    @SerializedName("n_offer_thumb_image")
    @Expose
    private String nOfferThumbImage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount")
    @Expose
    private Integer amount;

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

    public String getNOfferImage() {
        return nOfferImage;
    }

    public void setNOfferImage(String nOfferImage) {
        this.nOfferImage = nOfferImage;
    }

    public String getNOfferThumbImage() {
        return nOfferThumbImage;
    }

    public void setNOfferThumbImage(String nOfferThumbImage) {
        this.nOfferThumbImage = nOfferThumbImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
