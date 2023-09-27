package com.mobile.cover.photo.editor.back.maker.Pojoclasses.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.custom_data_response;

import java.util.List;

public class customimage_response {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("isEditable")
    @Expose
    private Boolean isEditable;


    @SerializedName("data")
    @Expose
    private List<custom_data_response> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<custom_data_response> getData() {
        return data;
    }

    public void setData(List<custom_data_response> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isEditable() {
        return isEditable;
    }

    public void setEditable(Boolean editable) {
        isEditable = editable;
    }
}
