package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Version {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("forcefully")
    @Expose
    private Integer forcefully;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getForcefully() {
        return forcefully;
    }

    public void setForcefully(Integer forcefully) {
        this.forcefully = forcefully;
    }

}