package com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDashboard {
    @SerializedName("apikey")
    @Expose
    private String apikey;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
