package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("offer_code")
    @Expose
    private String offerCode;
    @SerializedName("n_offer_image")
    @Expose
    private String nOfferImage;
    @SerializedName("n_offer_thumb_image")
    @Expose
    private String nOfferThumbImage;
    @SerializedName("n_offer_new_thumb_image")
    @Expose
    private String nOfferNewThumbImage;

    @SerializedName("n_offer_new_image")
    @Expose
    private String nOfferNewImage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("is_international")
    @Expose
    private Integer is_international;
    @SerializedName("expiry_text")
    @Expose
    private String expiryText;
    @SerializedName("terms_condition")
    @Expose
    private String termsCondition;
    @SerializedName("display_message")
    @Expose
    private String display_message;

    public Integer getIs_international() {
        return is_international;
    }

    public void setIs_international(Integer is_international) {
        this.is_international = is_international;
    }

    public String getDisplay_message() {
        return display_message;
    }

    public void setDisplay_message(String display_message) {
        this.display_message = display_message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    public String getNOfferNewThumbImage() {
        return nOfferNewThumbImage;
    }

    public void setNOfferNewThumbImage(String nOfferNewThumbImage) {
        this.nOfferNewThumbImage = nOfferNewThumbImage;
    }

    public String getNOfferNewImage() {
        return nOfferNewImage;
    }

    public void setNOfferNewImage(String nOfferNewImage) {
        this.nOfferNewImage = nOfferNewImage;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExpiryText() {
        return expiryText;
    }

    public void setExpiryText(String expiryText) {
        this.expiryText = expiryText;
    }

    public String getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(String termsCondition) {
        this.termsCondition = termsCondition;
    }

}
