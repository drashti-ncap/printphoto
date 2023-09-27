package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class validation_Data {
    @SerializedName("pincode_serviceable")
    @Expose
    private Integer pincodeServiceable;
    @SerializedName("pincode_message")
    @Expose
    private String pincodeMessage;
    @SerializedName("display_cod_button")
    @Expose
    private Integer displayCodButton;
    @SerializedName("cod_available")
    @Expose
    private Integer codAvailable;
    @SerializedName("cod_message")
    @Expose
    private String codMessage;

    public Integer getPincodeServiceable() {
        return pincodeServiceable;
    }

    public void setPincodeServiceable(Integer pincodeServiceable) {
        this.pincodeServiceable = pincodeServiceable;
    }

    public String getPincodeMessage() {
        return pincodeMessage;
    }

    public void setPincodeMessage(String pincodeMessage) {
        this.pincodeMessage = pincodeMessage;
    }

    public Integer getDisplayCodButton() {
        return displayCodButton;
    }

    public void setDisplayCodButton(Integer displayCodButton) {
        this.displayCodButton = displayCodButton;
    }

    public Integer getCodAvailable() {
        return codAvailable;
    }

    public void setCodAvailable(Integer codAvailable) {
        this.codAvailable = codAvailable;
    }

    public String getCodMessage() {
        return codMessage;
    }

    public void setCodMessage(String codMessage) {
        this.codMessage = codMessage;
    }
}
