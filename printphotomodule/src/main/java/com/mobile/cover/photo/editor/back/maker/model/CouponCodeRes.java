package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponCodeRes {

    @SerializedName("ResponseCode")
    @Expose
    private int resCode;

    @SerializedName("ResponseMessage")
    @Expose
    private String resMsg;

    @SerializedName("offer")
    @Expose
    private CouponData couponData;

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public CouponData getCouponData() {
        return couponData;
    }

    public void setCouponData(CouponData couponData) {
        this.couponData = couponData;
    }
}
