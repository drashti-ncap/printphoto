package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("sortname")
    @Expose
    private String sortname;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phonecode")
    @Expose
    private Integer phonecode;
    @SerializedName("zone")
    @Expose
    private Integer zone;
    @SerializedName("currency_id")
    @Expose
    private Integer currencyId;
    @SerializedName("phone_length")
    @Expose
    private Integer phone_length;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;

    @SerializedName("is_branch")
    @Expose
    private Integer is_branch;

    public Integer getIs_branch() {
        return is_branch;
    }

    public void setIs_branch(Integer is_branch) {
        this.is_branch = is_branch;
    }

    public Integer getPhone_length() {
        return phone_length;
    }

    public void setPhone_length(Integer phone_length) {
        this.phone_length = phone_length;
    }

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

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(Integer phonecode) {
        this.phonecode = phonecode;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

}