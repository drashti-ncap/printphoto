package com.mobile.cover.photo.editor.back.maker.Pojoclasses.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.UserPrice;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.transactionDetailResponse;

import java.util.List;

public class TransactionWithdrawResponse {
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("cash")
    @Expose
    private Integer _case;
    @SerializedName("withdraw")
    @Expose
    private Integer withdraw;
    @SerializedName("promo_code")
    @Expose
    private String promo_code;
    @SerializedName("user_price")
    @Expose
    private UserPrice userPrice;
    @SerializedName("data")
    @Expose
    private List<transactionDetailResponse> date = null;

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

    public Integer getCase() {
        return _case;
    }

    public void setCase(Integer _case) {
        this._case = _case;
    }

    public Integer getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Integer withdraw) {
        this.withdraw = withdraw;
    }

    public String getpromo_code() {
        return promo_code;
    }

    public void setpromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public UserPrice getUserPrice() {
        return userPrice;
    }

    public void setUserPrice(UserPrice userPrice) {
        this.userPrice = userPrice;
    }


    public List<transactionDetailResponse> getDate() {
        return date;
    }

    public void setDate(List<transactionDetailResponse> date) {
        this.date = date;
    }
}
