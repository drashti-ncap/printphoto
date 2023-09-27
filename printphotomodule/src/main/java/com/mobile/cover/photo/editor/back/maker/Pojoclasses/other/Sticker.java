package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sticker {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sticker_catid")
    @Expose
    private Integer stickerCatid;
    @SerializedName("n_image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStickerCatid() {
        return stickerCatid;
    }

    public void setStickerCatid(Integer stickerCatid) {
        this.stickerCatid = stickerCatid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
