
package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variant {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("dummy_price")
    @Expose
    private String dummyPrice;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDummyPrice() {
        return dummyPrice;
    }

    public void setDummyPrice(String dummyPrice) {
        this.dummyPrice = dummyPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
