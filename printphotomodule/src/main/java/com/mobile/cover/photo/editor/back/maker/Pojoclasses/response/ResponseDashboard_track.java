package com.mobile.cover.photo.editor.back.maker.Pojoclasses.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDashboard_track {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response")
    @Expose
    private Response_track response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Response_track getResponse() {
        return response;
    }

    public void setResponse(Response_track response) {
        this.response = response;
    }

}
