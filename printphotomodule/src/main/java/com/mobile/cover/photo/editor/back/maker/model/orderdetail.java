package com.mobile.cover.photo.editor.back.maker.model;

public class orderdetail {
    private String order_id, date, transaction_type, transaction_id, total_amount, discount, coupon, shipping, paid_amount, delivery_address, track_status, user_id;

    public orderdetail(String order_id, String date, String transaction_type, String transaction_id, String total_amount, String discount, String coupon, String shipping, String paid_amount, String delivery_address, String track_status, String user_id) {
        this.order_id = order_id;
        this.date = date;
        this.transaction_type = transaction_type;
        this.transaction_id = transaction_id;
        this.total_amount = total_amount;
        this.discount = discount;
        this.coupon = coupon;
        this.shipping = shipping;
        this.paid_amount = paid_amount;
        this.delivery_address = delivery_address;
        this.track_status = track_status;
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
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

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getTrack_status() {
        return track_status;
    }

    public void setTrack_status(String track_status) {
        this.track_status = track_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
