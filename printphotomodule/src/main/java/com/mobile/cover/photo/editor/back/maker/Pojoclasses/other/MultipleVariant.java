package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultipleVariant{

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("price")
    @Expose
    public String price;

    @SerializedName("dummy_price")
    @Expose
    public String dummy_price;

    @SerializedName("quantity")
    @Expose
    public int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDummy_price() {
        return dummy_price;
    }

    public void setDummy_price(String dummy_price) {
        this.dummy_price = dummy_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}