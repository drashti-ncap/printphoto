package com.mobile.cover.photo.editor.back.maker.Notification.Notification_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdditionalData {

    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("offer")
    @Expose
    private String offer;

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

}