package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class addressListResponse {
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("office")
    @Expose
    private String office;
    @SerializedName("address_3")
    @Expose
    private String address3;
    @SerializedName("address_4")
    @Expose
    private String address4;
    @SerializedName("address_5")
    @Expose
    private String address5;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }
}
