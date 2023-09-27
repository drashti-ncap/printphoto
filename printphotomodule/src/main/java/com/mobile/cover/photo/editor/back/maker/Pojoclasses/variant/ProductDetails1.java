
package com.mobile.cover.photo.editor.back.maker.Pojoclasses.variant;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ProductDetails1 {

    @SerializedName("name")
    private String mName;
    @SerializedName("value")
    private List<String> mValue;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<String> getValue() {
        return mValue;
    }

    public void setValue(List<String> value) {
        mValue = value;
    }

}
