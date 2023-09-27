package com.mobile.cover.photo.editor.back.maker.model;

public class transaction {
    private String id, order_id, model_id, user_id, seller_id, price, date_time, status;

    public transaction(String id, String order_id, String model_id, String user_id, String seller_id, String price, String date_time, String status) {
        this.id = id;
        this.order_id = order_id;
        this.model_id = model_id;
        this.user_id = user_id;
        this.seller_id = seller_id;
        this.price = price;
        this.date_time = date_time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
