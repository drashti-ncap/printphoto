package com.mobile.cover.photo.editor.back.maker.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("n_image")
    @Expose
    private String nImage;
    @SerializedName("n_thumb_image")
    @Expose
    private String nThumbImage;
    @SerializedName("Gift")
    @Expose
    private Integer gift;
    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNImage() {
        return nImage;
    }

    public void setNImage(String nImage) {
        this.nImage = nImage;
    }

    public String getNThumbImage() {
        return nThumbImage;
    }

    public void setNThumbImage(String nThumbImage) {
        this.nThumbImage = nThumbImage;
    }

    public Integer getGift() {
        return gift;
    }

    public void setGift(Integer gift) {
        this.gift = gift;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }
}
