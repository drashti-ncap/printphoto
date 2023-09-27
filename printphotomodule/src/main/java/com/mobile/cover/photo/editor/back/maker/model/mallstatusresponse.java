package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.setting_response_data;

public class mallstatusresponse {
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("data")
    @Expose
    private setting_response_data mallEnableStatus;

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

    public setting_response_data getMallEnableStatus() {
        return mallEnableStatus;
    }

    public void setMallEnableStatus(setting_response_data mallEnableStatus) {
        this.mallEnableStatus = mallEnableStatus;
    }
}
