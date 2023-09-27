package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.address_city_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.address_country_data;

public class save_address_response_data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    //    @SerializedName("city_id")
//    @Expose
//    private Integer cityId;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("receiver_name")
    @Expose
    private String receiverName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("address_1")
    @Expose
    private String address1;
    @SerializedName("alternate_mobile")
    @Expose
    private String alternateMobile;
    @SerializedName("city")
    @Expose
    private address_city_data city;
    @SerializedName("country")
    @Expose
    private address_country_data country;
    @SerializedName("is_select")
    @Expose
    private Boolean isSelect;

    public address_country_data getCountry() {
        return country;
    }

    public void setCountry(address_country_data country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

//    public Integer getCityId() {
//        return cityId;
//    }
//
//    public void setCityId(Integer cityId) {
//        this.cityId = cityId;
//    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public address_city_data getCity() {
        return city;
    }

    public void setCity(address_city_data city) {
        this.city = city;
    }

    public Boolean getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Boolean isSelect) {
        this.isSelect = isSelect;
    }

}
