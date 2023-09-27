package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.model.CartItem;

import java.util.List;

public class Cart_total_value {
    @SerializedName("cart_total")
    @Expose
    private Double cartTotal;
    @SerializedName("cart_items")
    @Expose
    private List<CartItem> cartItems = null;

    public Double getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(Double cartTotal) {
        this.cartTotal = cartTotal;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}
