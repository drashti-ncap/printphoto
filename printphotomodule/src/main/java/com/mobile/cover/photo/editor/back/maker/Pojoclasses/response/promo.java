package com.mobile.cover.photo.editor.back.maker.Pojoclasses.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.OfferCodeData;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.promodetaildata;

import java.util.List;

public class promo {
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;

    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("data")
    @Expose
    private List<promodetaildata> data = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<promodetaildata> getData() {
        return data;
    }

    public void setData(List<promodetaildata> data) {
        this.data = data;
    }
}
