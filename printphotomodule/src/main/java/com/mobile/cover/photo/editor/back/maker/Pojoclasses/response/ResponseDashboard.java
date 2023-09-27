package com.mobile.cover.photo.editor.back.maker.Pojoclasses.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.prepaid_serviceable_data;

public class ResponseDashboard {

    @SerializedName("ResponseStatus")
    @Expose
    private String responseStatus;
    @SerializedName("ORDER_ID")
    @Expose
    private String oRDERID;
    @SerializedName("CHECKSUMHASH")
    @Expose
    private String cHECKSUMHASH;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getORDERID() {
        return oRDERID;
    }

    public void setORDERID(String oRDERID) {
        this.oRDERID = oRDERID;
    }

    public String getCHECKSUMHASH() {
        return cHECKSUMHASH;
    }

    public void setCHECKSUMHASH(String cHECKSUMHASH) {
        this.cHECKSUMHASH = cHECKSUMHASH;
    }
}
