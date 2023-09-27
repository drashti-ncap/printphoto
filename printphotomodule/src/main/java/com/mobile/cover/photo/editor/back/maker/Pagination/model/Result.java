package com.mobile.cover.photo.editor.back.maker.Pagination.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Pagination
 * Created by Suleiman19 on 10/27/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image1")
    @Expose
    private String image1;
    @SerializedName("n_image1")
    @Expose
    private String nImage1;
    @SerializedName("n_thumb_image1")
    @Expose
    private String nThumbImage1;
    @SerializedName("image2")
    @Expose
    private String image2;
    @SerializedName("n_image2")
    @Expose
    private String nImage2;
    @SerializedName("n_thumb_image2")
    @Expose
    private String nThumbImage2;
    @SerializedName("image3")
    @Expose
    private String image3;
    @SerializedName("n_image3")
    @Expose
    private String nImage3;
    @SerializedName("n_thumb_image3")
    @Expose
    private String nThumbImage3;
    @SerializedName("selling")
    @Expose
    private Integer selling;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getNImage1() {
        return nImage1;
    }

    public void setNImage1(String nImage1) {
        this.nImage1 = nImage1;
    }

    public String getNThumbImage1() {
        return nThumbImage1;
    }

    public void setNThumbImage1(String nThumbImage1) {
        this.nThumbImage1 = nThumbImage1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getNImage2() {
        return nImage2;
    }

    public void setNImage2(String nImage2) {
        this.nImage2 = nImage2;
    }

    public String getNThumbImage2() {
        return nThumbImage2;
    }

    public void setNThumbImage2(String nThumbImage2) {
        this.nThumbImage2 = nThumbImage2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getNImage3() {
        return nImage3;
    }

    public void setNImage3(String nImage3) {
        this.nImage3 = nImage3;
    }

    public String getNThumbImage3() {
        return nThumbImage3;
    }

    public void setNThumbImage3(String nThumbImage3) {
        this.nThumbImage3 = nThumbImage3;
    }

    public Integer getSelling() {
        return selling;
    }

    public void setSelling(Integer selling) {
        this.selling = selling;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}



