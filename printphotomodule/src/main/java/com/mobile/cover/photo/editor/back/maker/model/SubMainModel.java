package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubMainModel {

    @SerializedName("ResponseCode")
    String ResponseCode;

    @SerializedName("ResponseMessage")
    String ResponseMessage;

    @SerializedName("data")
    ArrayList<SubData> subDataArrayList;

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        ResponseMessage = responseMessage;
    }

    public ArrayList<SubData> getSubDataArrayList() {
        return subDataArrayList;
    }

    public void setSubDataArrayList(ArrayList<SubData> subDataArrayList) {
        this.subDataArrayList = subDataArrayList;
    }
}
