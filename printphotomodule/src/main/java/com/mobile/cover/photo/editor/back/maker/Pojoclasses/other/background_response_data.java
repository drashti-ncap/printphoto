package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.model.category_bg_images;

public class background_response_data {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("n_image")
    @Expose
    private String image;

    @SerializedName("category")
    @Expose
    private category_bg_images category;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public category_bg_images getCategory() {
        return category;
    }

    public void setCategory(category_bg_images category) {
        this.category = category;
    }

}
