package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrameDetail {

    @SerializedName("mask_image")
    @Expose
    private String maskImage;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("display_width")
    @Expose
    private String displayWidth;
    @SerializedName("display_height")
    @Expose
    private String displayHeight;

    public String getMaskImage() {
        return maskImage;
    }

    public void setMaskImage(String maskImage) {
        this.maskImage = maskImage;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(String displayWidth) {
        this.displayWidth = displayWidth;
    }

    public String getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(String displayHeight) {
        this.displayHeight = displayHeight;
    }
}