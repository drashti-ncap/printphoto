package com.mobile.cover.photo.editor.back.maker.model;

public class getpromodetail {
    private String id, seller_id, model_id, seller_price_value, user_price_value;

    public getpromodetail(String id, String seller_id, String model_id, String seller_price_value, String user_price_value) {
        this.id = id;
        this.seller_id = seller_id;
        this.model_id = model_id;
        this.seller_price_value = seller_price_value;
        this.user_price_value = user_price_value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getSeller_price_value() {
        return seller_price_value;
    }

    public void setSeller_price_value(String seller_price_value) {
        this.seller_price_value = seller_price_value;
    }

    public String getUser_price_value() {
        return user_price_value;
    }

    public void setUser_price_value(String user_price_value) {
        this.user_price_value = user_price_value;
    }
}
