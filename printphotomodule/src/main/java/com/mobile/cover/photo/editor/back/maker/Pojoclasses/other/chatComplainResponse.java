package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class chatComplainResponse {
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
    @SerializedName("replay")
    @Expose
    private String replay;
    @SerializedName("n_image")
    @Expose
    private String nImage;
    @SerializedName("n_thumb_image")
    @Expose
    private String nThumbImage;

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

    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    public String getNImage() {
        return nImage;
    }

    public void setNImage(String nImage) {
        this.nImage = nImage;
    }

    public String getNThumbImage() {
        return nThumbImage;
    }

    public void setNThumbImage(String nThumbImage) {
        this.nThumbImage = nThumbImage;
    }


}
