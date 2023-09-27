package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class category_bg_images {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("width")
    @Expose
    private Double width;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

}
