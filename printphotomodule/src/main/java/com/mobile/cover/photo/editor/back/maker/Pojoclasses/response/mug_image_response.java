package com.mobile.cover.photo.editor.back.maker.Pojoclasses.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.mug_image_response_data;

import java.util.List;

public class mug_image_response {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<mug_image_response_data> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<mug_image_response_data> getData() {
        return data;
    }

    public void setData(List<mug_image_response_data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
