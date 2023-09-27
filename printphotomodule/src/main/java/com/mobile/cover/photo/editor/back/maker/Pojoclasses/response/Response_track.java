package com.mobile.cover.photo.editor.back.maker.Pojoclasses.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.ExtraFields;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Scan;

import java.util.List;

public class Response_track {
    @SerializedName("awbno")
    @Expose
    private String awbno;
    @SerializedName("carrier")
    @Expose
    private String carrier;
    @SerializedName("pickupdate")
    @Expose
    private String pickupdate;
    @SerializedName("current_status")
    @Expose
    private String currentStatus;
    @SerializedName("current_status_code")
    @Expose
    private String currentStatusCode;
    @SerializedName("recipient")
    @Expose
    private String recipient;
    @SerializedName("extra_fields")
    @Expose
    private ExtraFields extraFields;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("customername")
    @Expose
    private String customername;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("scan")
    @Expose
    private List<Scan> scan = null;
    @SerializedName("tracking_url")
    @Expose
    private String trackingUrl;

    public String getAwbno() {
        return awbno;
    }

    public void setAwbno(String awbno) {
        this.awbno = awbno;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getCurrentStatusCode() {
        return currentStatusCode;
    }

    public void setCurrentStatusCode(String currentStatusCode) {
        this.currentStatusCode = currentStatusCode;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public ExtraFields getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(ExtraFields extraFields) {
        this.extraFields = extraFields;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Scan> getScan() {
        return scan;
    }

    public void setScan(List<Scan> scan) {
        this.scan = scan;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

}
