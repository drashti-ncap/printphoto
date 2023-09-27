package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtraFields {

    @SerializedName("refrence_number")
    @Expose
    private String refrenceNumber;
    @SerializedName("reciever")
    @Expose
    private String reciever;
    @SerializedName("sign_stamp")
    @Expose
    private String signStamp;
    @SerializedName("drs_no")
    @Expose
    private String drsNo;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("relationship")
    @Expose
    private String relationship;

    public String getRefrenceNumber() {
        return refrenceNumber;
    }

    public void setRefrenceNumber(String refrenceNumber) {
        this.refrenceNumber = refrenceNumber;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getSignStamp() {
        return signStamp;
    }

    public void setSignStamp(String signStamp) {
        this.signStamp = signStamp;
    }

    public String getDrsNo() {
        return drsNo;
    }

    public void setDrsNo(String drsNo) {
        this.drsNo = drsNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

}
