package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class brand_response_data {
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("modal")
    @Expose
    private List<model_response_data> modal = null;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<model_response_data> getModal() {
        return modal;
    }

    public void setModal(List<model_response_data> modal) {
        this.modal = modal;
    }

}
