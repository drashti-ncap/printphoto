package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AllChild {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("app_image")
    @Expose
    private String appImage;
    @SerializedName("internation_app_image")
    @Expose
    private String internation_app_image;
    @SerializedName("is_international")
    @Expose
    private Integer is_international;
    @SerializedName("app_thumb_image")
    @Expose
    private String appThumbImage;
    @SerializedName("web_image")
    @Expose
    private String webImage;
    @SerializedName("web_thumb_image")
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
    private List<AllChild> allChilds = new ArrayList<>();

    public String getInternation_app_image() {
        return internation_app_image;
    }

    public void setInternation_app_image(String internation_app_image) {
        this.internation_app_image = internation_app_image;
    }

    public Integer getIs_international() {
        return is_international;
    }

    public void setIs_international(Integer is_international) {
        this.is_international = is_international;
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

    public List<AllChild> getAllChilds() {
        return allChilds;
    }

    public void setAllChilds(List<AllChild> allChilds) {
        this.allChilds = allChilds;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

}
