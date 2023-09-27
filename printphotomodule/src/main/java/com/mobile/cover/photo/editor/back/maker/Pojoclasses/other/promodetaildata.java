package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class promodetaildata {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("seller_id")
    @Expose
    private Integer sellerId;
    @SerializedName("model_id")
    @Expose
    private Integer modelId;
    @SerializedName("seller_price_value")
    @Expose
    private Integer sellerPriceValue;
    @SerializedName("user_price_value")
    @Expose
    private Integer userPriceValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getSellerPriceValue() {
        return sellerPriceValue;
    }

    public void setSellerPriceValue(Integer sellerPriceValue) {
        this.sellerPriceValue = sellerPriceValue;
    }

    public Integer getUserPriceValue() {
        return userPriceValue;
    }

    public void setUserPriceValue(Integer userPriceValue) {
        this.userPriceValue = userPriceValue;
    }
}
