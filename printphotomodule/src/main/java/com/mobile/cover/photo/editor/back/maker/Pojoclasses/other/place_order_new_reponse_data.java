package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class place_order_new_reponse_data {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("transaction_status_checked_at")
    @Expose
    private String transactionStatusCheckedAt;
    @SerializedName("shipping")
    @Expose
    private String shipping;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("coupon")
    @Expose
    private String coupon;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("cart")
    @Expose
    private Integer cart;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("seller_id")
    @Expose
    private Object sellerId;
    @SerializedName("offer_id")
    @Expose
    private Object offerId;
    @SerializedName("gift")
    @Expose
    private String gift;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("item_quantity")
    @Expose
    private String itemQuantity;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionStatusCheckedAt() {
        return transactionStatusCheckedAt;
    }

    public void setTransactionStatusCheckedAt(String transactionStatusCheckedAt) {
        this.transactionStatusCheckedAt = transactionStatusCheckedAt;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getCart() {
        return cart;
    }

    public void setCart(Integer cart) {
        this.cart = cart;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Object getSellerId() {
        return sellerId;
    }

    public void setSellerId(Object sellerId) {
        this.sellerId = sellerId;
    }

    public Object getOfferId() {
        return offerId;
    }

    public void setOfferId(Object offerId) {
        this.offerId = offerId;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
