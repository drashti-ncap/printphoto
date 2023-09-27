package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponData {

    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("discount_amount")
    @Expose
    private Integer discountAmount;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("code_id")
    @Expose
    private Integer codeId;
    @SerializedName("type")
    @Expose
    private Integer type;

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
