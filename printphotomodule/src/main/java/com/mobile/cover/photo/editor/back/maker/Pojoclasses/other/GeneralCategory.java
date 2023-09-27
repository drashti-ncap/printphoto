package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralCategory {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("app_image")
    @Expose
    private String appImage;
    @SerializedName("app_thumb_image")
    @Expose
    private String appThumbImage;
    @SerializedName("internation_app_image")
    @Expose
    private String internationAppImage;
    @SerializedName("internation_app_thumb_image")
    @Expose
    private String internationAppThumbImage;
    @SerializedName("web_image")
    @Expose
    private String webImage;
    @SerializedName("web_thumb_image")
    @Expose
    private String webThumbImage;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("cod_available")
    @Expose
    private Integer codAvailable;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppImage() {
        return appImage;
    }

    public void setAppImage(String appImage) {
        this.appImage = appImage;
    }

    public String getAppThumbImage() {
        return appThumbImage;
    }

    public void setAppThumbImage(String appThumbImage) {
        this.appThumbImage = appThumbImage;
    }

    public String getInternationAppImage() {
        return internationAppImage;
    }

    public void setInternationAppImage(String internationAppImage) {
        this.internationAppImage = internationAppImage;
    }

    public String getInternationAppThumbImage() {
        return internationAppThumbImage;
    }

    public void setInternationAppThumbImage(String internationAppThumbImage) {
        this.internationAppThumbImage = internationAppThumbImage;
    }

    public String getWebImage() {
        return webImage;
    }

    public void setWebImage(String webImage) {
        this.webImage = webImage;
    }

    public String getWebThumbImage() {
        return webThumbImage;
    }

    public void setWebThumbImage(String webThumbImage) {
        this.webThumbImage = webThumbImage;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }


    public Integer getCodAvailable() {
        return codAvailable;
    }

    public void setCodAvailable(Integer codAvailable) {
        this.codAvailable = codAvailable;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}