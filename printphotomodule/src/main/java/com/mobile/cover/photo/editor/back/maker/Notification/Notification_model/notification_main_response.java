package com.mobile.cover.photo.editor.back.maker.Notification.Notification_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class notification_main_response {
    @SerializedName("action")
    @Expose
    private Action action;
    @SerializedName("notification")
    @Expose
    private Notification notification;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

}
