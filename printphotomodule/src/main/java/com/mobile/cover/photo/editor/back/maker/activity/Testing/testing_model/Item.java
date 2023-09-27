package com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("shipping_level")
    @Expose
    private String shippingLevel;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("files")
    @Expose
    private List<File> files = null;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getShippingLevel() {
        return shippingLevel;
    }

    public void setShippingLevel(String shippingLevel) {
        this.shippingLevel = shippingLevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

}
