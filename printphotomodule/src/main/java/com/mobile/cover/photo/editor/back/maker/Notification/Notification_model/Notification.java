package com.mobile.cover.photo.editor.back.maker.Notification.Notification_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("isAppInFocus")
    @Expose
    private Boolean isAppInFocus;
    @SerializedName("shown")
    @Expose
    private Boolean shown;
    @SerializedName("androidNotificationId")
    @Expose
    private Integer androidNotificationId;
    @SerializedName("displayType")
    @Expose
    private Integer displayType;
    @SerializedName("payload")
    @Expose
    private Payload payload;

    public Boolean getIsAppInFocus() {
        return isAppInFocus;
    }

    public void setIsAppInFocus(Boolean isAppInFocus) {
        this.isAppInFocus = isAppInFocus;
    }

    public Boolean getShown() {
        return shown;
    }

    public void setShown(Boolean shown) {
        this.shown = shown;
    }

    public Integer getAndroidNotificationId() {
        return androidNotificationId;
    }

    public void setAndroidNotificationId(Integer androidNotificationId) {
        this.androidNotificationId = androidNotificationId;
    }

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

}