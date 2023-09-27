package com.mobile.cover.photo.editor.back.maker.model;


public class getdata {
    private String id, offer_code, offer_image, offer_new_image, description, amount, status, expiry_text, terms_condition, display_message;

    public getdata(String id, String offer_code, String offer_image, String offer_new_image, String description, String amount, String status, String expiry_text, String terms_condition, String display_message) {
        this.id = id;
        this.offer_code = offer_code;
        this.offer_image = offer_image;
        this.offer_new_image = offer_new_image;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.expiry_text = expiry_text;
        this.terms_condition = terms_condition;
        this.display_message = display_message;
    }


    public String getDisplay_message() {
        return display_message;
    }

    public void setDisplay_message(String display_message) {
        this.display_message = display_message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOffer_code() {
        return offer_code;
    }

    public void setOffer_code(String offer_code) {
        this.offer_code = offer_code;
    }

    public String getOffer_image() {
        return offer_image;
    }

    public void setOffer_image(String offer_image) {
        this.offer_image = offer_image;
    }

    public String getOffer_new_image() {
        return offer_new_image;
    }

    public void setOffer_new_image(String offer_new_image) {
        this.offer_new_image = offer_new_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpiry_text() {
        return expiry_text;
    }

    public void setExpiry_text(String expiry_text) {
        this.expiry_text = expiry_text;
    }

    public String getTerms_condition() {
        return terms_condition;
    }

    public void setTerms_condition(String terms_condition) {
        this.terms_condition = terms_condition;
    }
}
