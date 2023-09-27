package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class mall_AllChild {
    @SerializedName("id")
    @Expose
    private Integer id;


    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("app_image")
    @Expose
    private String appImage;

    @SerializedName("app_thumb_image")
    @Expose
    private String appThumbImage;

    @SerializedName("web_image")
    @Expose
    private String webImage;

    @SerializedName("web_thumb_image")
    @Expose
    private String web_thumb_image;

    @SerializedName("app_mall_image")
    @Expose
    private String app_mall_image;

    @SerializedName("app_mall_thumb_image")
    @Expose
    private String app_mall_thumb_image;

    @SerializedName("international_app_mall_image")
    @Expose
    private String international_app_mall_image;

    @SerializedName("international_app_mall_thumb_image")
    @Expose
    private String international_app_mall_thumb_image;



    @Expose
    private String webThumbImage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cod_available")
    @Expose
    private Integer codAvailable;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("level")
    @Expose
    private Integer level;
    private Integer position = 0;

    @SerializedName("all_childs")
    @Expose
    private List<mall_AllChild> allChilds = null;
    @SerializedName("available_filter")
    @Expose
    private List<AvailableFilter> availableFilter = new ArrayList<>();

    public List<mall_AllChild> getAllChilds() {
        return allChilds;
    }

    public void setAllChilds(List<mall_AllChild> allChilds) {
        this.allChilds = allChilds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCodAvailable() {
        return codAvailable;
    }

    public void setCodAvailable(Integer codAvailable) {
        this.codAvailable = codAvailable;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<AvailableFilter> getAvailableFilter() {
        return availableFilter;
    }

    public void setAvailableFilter(List<AvailableFilter> availableFilter) {
        this.availableFilter = availableFilter;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getApp_mall_image() {
        return app_mall_image;
    }

    public void setApp_mall_image(String app_mall_image) {
        this.app_mall_image = app_mall_image;
    }

    public String getApp_mall_thumb_image() {
        return app_mall_thumb_image;
    }

    public void setApp_mall_thumb_image(String app_mall_thumb_image) {
        this.app_mall_thumb_image = app_mall_thumb_image;
    }

    public String getInternational_app_mall_image() {
        return international_app_mall_image;
    }

    public void setInternational_app_mall_image(String international_app_mall_image) {
        this.international_app_mall_image = international_app_mall_image;
    }

    public String getInternational_app_mall_thumb_image() {
        return international_app_mall_thumb_image;
    }

    public void setInternational_app_mall_thumb_image(String international_app_mall_thumb_image) {
        this.international_app_mall_thumb_image = international_app_mall_thumb_image;
    }
}
