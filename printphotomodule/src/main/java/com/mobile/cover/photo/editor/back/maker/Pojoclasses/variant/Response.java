
package com.mobile.cover.photo.editor.back.maker.Pojoclasses.variant;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("currency_symbol")
    private String mCurrencySymbol;
    @SerializedName("data")
    private List<Datum> mData;
    @SerializedName("is_international")
    private Long mIsInternational;
    @SerializedName("ResponseCode")
    private String mResponseCode;
    @SerializedName("ResponseMessage")
    private String mResponseMessage;

    public String getCurrencySymbol() {
        return mCurrencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        mCurrencySymbol = currencySymbol;
    }

    public List<Datum> getData() {
        return mData;
    }

    public void setData(List<Datum> data) {
        mData = data;
    }

    public Long getIsInternational() {
        return mIsInternational;
    }

    public void setIsInternational(Long isInternational) {
        mIsInternational = isInternational;
    }

    public String getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(String responseCode) {
        mResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        mResponseMessage = responseMessage;
    }

}
