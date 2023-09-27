package com.mobile.cover.photo.editor.back.maker.model;

public class request_final_brand {

    String brand, id, modelid;

    public request_final_brand(String brand, String id, String modelid) {
        this.brand = brand;
        this.id = id;
        this.modelid = modelid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelid() {
        return modelid;
    }

    public void setModelid(String modelid) {
        this.modelid = modelid;
    }
}
