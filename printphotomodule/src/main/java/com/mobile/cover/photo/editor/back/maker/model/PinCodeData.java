package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PinCodeData implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Pincode")
    @Expose
    private Integer pincode;
    @SerializedName("Courier_company_ID")
    @Expose
    private String courierCompanyID;
    @SerializedName("City_Name")
    @Expose
    private String cityName;
    @SerializedName("State_Name")
    @Expose
    private String stateName;
    @SerializedName("State_code")
    @Expose
    private String stateCode;
    @SerializedName("COD")
    @Expose
    private String cOD;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getCourierCompanyID() {
        return courierCompanyID;
    }

    public void setCourierCompanyID(String courierCompanyID) {
        this.courierCompanyID = courierCompanyID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCOD() {
        return cOD;
    }

    public void setCOD(String cOD) {
        this.cOD = cOD;
    }
}
