package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails_response {

    @SerializedName("order_total")
    @Expose
    private Double orderTotal;
    @SerializedName("shipping_charge")
    @Expose
    private Double shippingCharge;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("net_payable")
    @Expose
    private Double netPayable;
    @SerializedName("discount_message")
    @Expose
    private String discountMessage;
    @SerializedName("discount_applied")
    @Expose
    private String discount_applied;

    @SerializedName("cod_charge")
    @Expose
    private String cod_charge;
    @SerializedName("gift_charge")
    @Expose
    private String gift_charge;

    @SerializedName("is_gift")
    @Expose
    private String is_gift;


    @SerializedName("gst_charges")
    @Expose
    private float gst_charges;

    public String getCod_charge() {
        return cod_charge;
    }

    public void setCod_charge(String cod_charge) {
        this.cod_charge = cod_charge;
    }

    public String getGift_charge() {
        return gift_charge;
    }

    public void setGift_charge(String gift_charge) {
        this.gift_charge = gift_charge;
    }

    public String getIs_gift() {
        return is_gift;
    }

    public void setIs_gift(String is_gift) {
        this.is_gift = is_gift;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Double getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(Double shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getNetPayable() {
        return netPayable;
    }

    public void setNetPayable(Double netPayable) {
        this.netPayable = netPayable;
    }

    public String getDiscountMessage() {
        return discountMessage;
    }

    public void setDiscountMessage(String discountMessage) {
        this.discountMessage = discountMessage;
    }

    public String getdiscount_applied() {
        return discount_applied;
    }

    public void setdiscount_applied(String discount_applied) {
        this.discount_applied = discount_applied;
    }



    public float getGst_charges() {
        return gst_charges;
    }

    public void setGst_charges(float gst_charges) {
        this.gst_charges = gst_charges;
    }
}
