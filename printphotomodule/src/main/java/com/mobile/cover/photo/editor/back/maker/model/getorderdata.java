package com.mobile.cover.photo.editor.back.maker.model;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.orderitem;

import java.util.List;

public class getorderdata {
    private String order_id, date, transaction_type, transaction_id, total_amount, discount, shipping, paid_amount, track_status, order_status, courier_link, is_cancelable, cancel_msg, currency_symbol, is_gift, gift_charge;
    private List<orderitem> sqlist1;


    public getorderdata(String order_id, String date, String transaction_type, String transaction_id, String total_amount, String discount,
                        String shipping, String paid_amount, String track_status, String order_status, String courier_link, String is_cancelable,
                        String cancel_msg, String currency_symbol, String is_gift, String gift_charge, List<orderitem> sqlist1) {
        this.order_id = order_id;
        this.date = date;
        this.transaction_type = transaction_type;
        this.transaction_id = transaction_id;
        this.total_amount = total_amount;
        this.discount = discount;
        this.shipping = shipping;
        this.paid_amount = paid_amount;
        this.track_status = track_status;
        this.order_status = order_status;
        this.courier_link = courier_link;
        this.is_cancelable = is_cancelable;
        this.cancel_msg = cancel_msg;
        this.sqlist1 = sqlist1;
        this.currency_symbol = currency_symbol;
        this.is_gift = is_gift;
        this.gift_charge = gift_charge;
    }

    public String getIs_gift() {
        return is_gift;
    }

    public void setIs_gift(String is_gift) {
        this.is_gift = is_gift;
    }

    public String getGift_charge() {
        return gift_charge;
    }

    public void setGift_charge(String gift_charge) {
        this.gift_charge = gift_charge;
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

    public String getTrack_status() {
        return track_status;
    }

    public void setTrack_status(String track_status) {
        this.track_status = track_status;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getCourier_link() {
        return courier_link;
    }

    public void setCourier_link(String courier_link) {
        this.courier_link = courier_link;
    }

    public String getIs_cancelable() {
        return is_cancelable;
    }

    public void setIs_cancelable(String is_cancelable) {
        this.is_cancelable = is_cancelable;
    }

    public String getCancel_msg() {
        return cancel_msg;
    }

    public void setCancel_msg(String cancel_msg) {
        this.cancel_msg = cancel_msg;
    }

    public List<orderitem> getSqlist1() {
        return sqlist1;
    }

    public void setSqlist1(List<orderitem> sqlist1) {
        this.sqlist1 = sqlist1;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

}
