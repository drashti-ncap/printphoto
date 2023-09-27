package com.mobile.cover.photo.editor.back.maker.model;

import java.util.List;

public class getpromodata {
    private String id, user_id, name, email, mobile, aadhar_no, promo_code;

    public getpromodata(String id, String user_id, String name, String email, String mobile, String aadhar_no, String promo_code) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.aadhar_no = aadhar_no;
        this.promo_code = promo_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }
}
