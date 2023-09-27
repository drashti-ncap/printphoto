package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class verify_user_data {
    @SerializedName("user_exists")
    @Expose
    private Integer userExists;

    public Integer getUserExists() {
        return userExists;
    }

    public void setUserExists(Integer userExists) {
        this.userExists = userExists;
    }
}
