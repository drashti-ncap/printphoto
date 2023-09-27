package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class model_response_data {

    @SerializedName("model_id")
    @Expose
    private Integer model_id;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("Model_Name")
    @Expose
    private String modelName;
    @SerializedName("Form_Factor")
    @Expose
    private String formFactor;

    public Integer getmodel_id() {
        return model_id;
    }

    public void setmodel_id(Integer model_id) {
        this.model_id = model_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

}
