package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelReason {

    public CancelReason(int id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("reason")
    @Expose
    public String reason;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
