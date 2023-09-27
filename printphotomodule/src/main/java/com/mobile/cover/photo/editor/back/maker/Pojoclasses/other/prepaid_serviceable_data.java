package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class prepaid_serviceable_data {
    @SerializedName("prepaid_serviceable")
    @Expose
    private Boolean prepaidServiceable;
    @SerializedName("prepaid_message")
    @Expose
    private String prepaidMessage;
    @SerializedName("cod_servicable")
    @Expose
    private Boolean codServicable;
    @SerializedName("cod_message")
    @Expose
    private String codMessage;

    public Boolean getPrepaidServiceable() {
        return prepaidServiceable;
    }

    public void setPrepaidServiceable(Boolean prepaidServiceable) {
        this.prepaidServiceable = prepaidServiceable;
    }

    public String getPrepaidMessage() {
        return prepaidMessage;
    }

    public void setPrepaidMessage(String prepaidMessage) {
        this.prepaidMessage = prepaidMessage;
    }

    public Boolean getCodServicable() {
        return codServicable;
    }

    public void setCodServicable(Boolean codServicable) {
        this.codServicable = codServicable;
    }

    public String getCodMessage() {
        return codMessage;
    }

    public void setCodMessage(String codMessage) {
        this.codMessage = codMessage;
    }
}
