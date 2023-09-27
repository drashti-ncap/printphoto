package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegData implements Serializable {

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("mobile_1")
    String mobile_1;

    @SerializedName("user_id")
    String id;

    @SerializedName("seller_id")
    String seller_id;

    @SerializedName("is_approve")
    String is_approve;

    @SerializedName("is_international")
    Integer is_international;
    @SerializedName("token")
    String token;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("country_name")
    @Expose
    private String country_name;
    @SerializedName("country_code")
    @Expose
    private String country_code;
    @SerializedName("currency_id")
    @Expose
    private Integer currencyId;
    @SerializedName("country_id")
    @Expose
    private Integer country_id;

    public Integer getIs_international() {
        return is_international;
    }

    public void setIs_international(Integer is_international) {
        this.is_international = is_international;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getseller_id() {
        return seller_id;
    }

    public void setseller_id(String seller_id) {
        this.seller_id = seller_id;
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

    public String getMobile_1() {
        return mobile_1;
    }

    public void setMobile_1(String mobile_1) {
        this.mobile_1 = mobile_1;
    }

    public String getis_approve() {
        return is_approve;
    }

    public void setis_approve(String is_approve) {
        this.is_approve = is_approve;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
