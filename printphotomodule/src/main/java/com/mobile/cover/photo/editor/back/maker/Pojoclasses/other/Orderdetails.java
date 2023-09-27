package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Orderdetails {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("seller_order")
    @Expose
    private List<SellerOrder> sellerOrder = null;
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
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;
    @SerializedName("shipping")
    @Expose
    private Double shipping;
    @SerializedName("cod_charge")
    @Expose
    private Double codCharge;

    @SerializedName("gift_charge")
    @Expose
    private Double giftCharge;


   @SerializedName("gst_charges")
    @Expose
    private Float gst_charges;



    @SerializedName("is_gift")
    @Expose
    private Integer isGift;
    @SerializedName("order_total")
    @Expose
    private Double orderTotal;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("paid_amount")
    @Expose
    private Double paidAmount;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("is_cancellable")
    @Expose
    private Integer isCancellable;
    @SerializedName("cancellation_message")
    @Expose
    private String cancellationMessage;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<SellerOrder> getSellerOrder() {
        return sellerOrder;
    }

    public void setSellerOrder(List<SellerOrder> sellerOrder) {
        this.sellerOrder = sellerOrder;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getShipping() {
        return shipping;
    }

    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    public Double getCodCharge() {
        return codCharge;
    }

    public void setCodCharge(Double codCharge) {
        this.codCharge = codCharge;
    }

    public Double getGiftCharge() {
        return giftCharge;
    }

    public void setGiftCharge(Double giftCharge) {
        this.giftCharge = giftCharge;
    }

    public Integer getIsGift() {
        return isGift;
    }

    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getIsCancellable() {
        return isCancellable;
    }

    public void setIsCancellable(Integer isCancellable) {
        this.isCancellable = isCancellable;
    }

    public String getCancellationMessage() {
        return cancellationMessage;
    }

    public void setCancellationMessage(String cancellationMessage) {
        this.cancellationMessage = cancellationMessage;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Float getGst_charges() {
        return gst_charges;
    }

    public void setGst_charges(Float gst_charges) {
        this.gst_charges = gst_charges;
    }
}
