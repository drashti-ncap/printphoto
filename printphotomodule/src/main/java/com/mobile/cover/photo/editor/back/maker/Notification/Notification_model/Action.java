package com.mobile.cover.photo.editor.back.maker.Notification.Notification_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Action {

    @SerializedName("type")
    @Expose
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}