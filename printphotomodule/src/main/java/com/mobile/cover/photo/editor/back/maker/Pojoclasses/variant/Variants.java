
package com.mobile.cover.photo.editor.back.maker.Pojoclasses.variant;

import com.google.gson.annotations.SerializedName;

public class Variants {

    @SerializedName("dummy_price")
    private String mDummyPrice;
    @SerializedName("id")
    private Long mId;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("quantity")
    private Long mQuantity;

    public String getDummyPrice() {
        return mDummyPrice;
    }

    public void setDummyPrice(String dummyPrice) {
        mDummyPrice = dummyPrice;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public Long getQuantity() {
        return mQuantity;
    }

    public void setQuantity(Long quantity) {
        mQuantity = quantity;
    }

}
