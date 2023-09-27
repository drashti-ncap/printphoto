
package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Version;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainModel {
//    @SerializedName("data")
//    private Data[] data;
//
//    @SerializedName("offer")
//    private OfferMain[] offer;
//
//    @SerializedName("ResponseCode")
//    private String ResponseCode;
//
//    @SerializedName("shipping_charge")
//    private String shipping_charge;
//
//    @SerializedName("bulk_sms")
//    private String bulk_sms;
//
//    @SerializedName("Version")
//    @Expose
//    private Version version;
//
//    @SerializedName("ResponseMessage")
//    private String ResponseMessage;
//
//    public Data[] getData() {
//        return data;
//    }
//
//    public void setData(Data[] data) {
//        this.data = data;
//    }
//
//    public String getResponseCode() {
//        return ResponseCode;
//    }
//
//    public void setResponseCode(String ResponseCode) {
//        this.ResponseCode = ResponseCode;
//    }
//
//    public String getshipping_charge() {
//        return shipping_charge;
//    }
//
//    public void setshipping_charge(String shipping_charge) {
//        this.shipping_charge = shipping_charge;
//    }
//
//    public String getbulk_sms() {
//        return bulk_sms;
//    }
//
//    public void setbulk_sms(String bulk_sms) {
//        this.bulk_sms = bulk_sms;
//    }
//
//    public Version getVersion() {
//        return version;
//    }
//
//    public void setVersion(Version version) {
//        this.version = version;
//    }
//
//    public String getResponseMessage() {
//        return ResponseMessage;
//    }
//
//    public void setResponseMessage(String ResponseMessage) {
//        this.ResponseMessage = ResponseMessage;
//    }
//
//    public OfferMain[] getOffer() {
//
//        return offer;
//    }
//
//    public void setOffer(OfferMain[] offer) {
//        this.offer = offer;
//    }
//
//    @Override
//    public String toString() {
//        return "ClassPojo [data = " + data + ", ResponseCode = " + ResponseCode + ", ResponseMessage = " + ResponseMessage + "]";
//    }

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("bulk_sms")
    @Expose
    private String bulkSms;
    @SerializedName("Version")
    @Expose
    private Version version;
    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("offer")
    @Expose
    private List<OfferMain> offer = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getBulkSms() {
        return bulkSms;
    }

    public void setBulkSms(String bulkSms) {
        this.bulkSms = bulkSms;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<OfferMain> getOffer() {
        return offer;
    }

    public void setOffer(List<OfferMain> offer) {
        this.offer = offer;
    }

}