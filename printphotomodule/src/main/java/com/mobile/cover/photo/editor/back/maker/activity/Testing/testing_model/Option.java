package com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("count")
    @Expose
    private String count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
