package com.mobile.cover.photo.editor.back.maker.Pojoclasses.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class address_country_data {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sortname")
    @Expose
    private String sortname;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phonecode")
    @Expose
    private Integer phonecode;
    @SerializedName("zone_id")
    @Expose
    private Integer zoneId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }
}
