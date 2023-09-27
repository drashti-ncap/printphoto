package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class chatQuestion {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("complain")
    @Expose
    private String complain;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("states")
    @Expose
    private String states;
    @SerializedName("response")
    @Expose
    private List<chatComplainResponse> response = null;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public List<chatComplainResponse> getResponse() {
        return response;
    }

    public void setResponse(List<chatComplainResponse> response) {
        this.response = response;
    }
}
