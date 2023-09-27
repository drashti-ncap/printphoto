package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocketDetail {

    @SerializedName("locket_detail_id")
    @Expose
    private Integer locket_detail_id;
    @SerializedName("locket_text_max_length")
    @Expose
    private String locket_text_max_length;

    public Integer getLocket_detail_id() {
        return locket_detail_id;
    }

    public void setLocket_detail_id(Integer locket_detail_id) {
        this.locket_detail_id = locket_detail_id;
    }

    public String getLocket_text_max_length() {
        return locket_text_max_length;
    }

    public void setLocket_text_max_length(String locket_text_max_length) {
        this.locket_text_max_length = locket_text_max_length;
    }
}