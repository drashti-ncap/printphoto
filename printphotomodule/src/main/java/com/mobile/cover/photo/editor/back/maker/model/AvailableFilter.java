package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailableFilter {
    @SerializedName("is_price")
    @Expose
    private Integer is_price;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private List<String> value = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public Integer getIs_price() {
        return is_price;
    }

    public void setIs_price(Integer is_price) {
        this.is_price = is_price;
    }

}
