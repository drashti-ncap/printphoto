package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class sub_category_model_details {
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("currency_symbol")
    @Expose
    private String currency_symbol;
    @SerializedName("is_international")
    @Expose
    private Integer is_international;
    @SerializedName("data")
    @Expose
    private List<model_details_data> data = null;

    public Integer getIs_international() {
        return is_international;
    }

    public void setIs_international(Integer is_international) {
        this.is_international = is_international;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

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

    public List<model_details_data> getData() {
        return data;
    }

    public void setData(List<model_details_data> data) {
        this.data = data;
    }
}
