package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPrice {

    @SerializedName("Magic_Mug")
    @Expose
    private Integer magicMug;
    @SerializedName("White_Mug")
    @Expose
    private Integer whiteMug;
    @SerializedName("Plastic_Mug")
    @Expose
    private Integer plasticMug;

    public Integer getMagicMug() {
        return magicMug;
    }

    public void setMagicMug(Integer magicMug) {
        this.magicMug = magicMug;
    }

    public Integer getWhiteMug() {
        return whiteMug;
    }

    public void setWhiteMug(Integer whiteMug) {
        this.whiteMug = whiteMug;
    }

    public Integer getPlasticMug() {
        return plasticMug;
    }

    public void setPlasticMug(Integer plasticMug) {
        this.plasticMug = plasticMug;
    }

}
