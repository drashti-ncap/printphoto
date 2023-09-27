package com.mobile.cover.photo.editor.back.maker.model;

import android.view.Display;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.rotate;

public class getorderdetails {
    com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.rotate rotate;
    private String model_id, model_name, case_image, qty, price, order_id, date;

    public getorderdetails(String model_id, String model_name, String case_image, String qty, String price, String order_id, String date, com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.rotate rotate) {
        this.model_id = model_id;
        this.model_name = model_name;
        this.case_image = case_image;
        this.qty = qty;
        this.price = price;
        this.order_id = order_id;
        this.date = date;
        this.rotate = rotate;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getCase_image() {
        return case_image;
    }

    public void setCase_image(String case_image) {
        this.case_image = case_image;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.rotate getRotate() {
        return rotate;
    }

    public void setRotate(com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.rotate rotate) {
        this.rotate = rotate;
    }
}
