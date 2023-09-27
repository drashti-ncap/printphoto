package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductType {
    @SerializedName("Customisable")
    @Expose
    private Integer customisable;


    @SerializedName("CoupleTshirt")
    @Expose
    private Integer CoupleTshirt;



    @SerializedName("Mall")
    @Expose
    private Integer mall;
    @SerializedName("Phonecase")
    @Expose
    private Integer phonecase;
    @SerializedName("Mug")
    @Expose
    private Integer mug;
    @SerializedName("Keychain")
    @Expose
    private Integer keychain;
    @SerializedName("Popsocket")
    @Expose
    private Integer popsocket;
    @SerializedName("Tshirt")
    @Expose
    private Integer tshirt;

    @SerializedName("WallWoodenCanvasFrame")
    @Expose
    private Integer WallWoodenCanvasFrame;

    @SerializedName("MultiImagePhotoFrames")
    @Expose
    private Integer MultiImagePhotoFrames;
    @SerializedName("CustomizeLocket")
    @Expose
    private Integer CustomizeLocket;

    public Integer getCustomizeLocket() {
        return CustomizeLocket;
    }

    public void setCustomizeLocket(Integer customizeLocket) {
        CustomizeLocket = customizeLocket;
    }

    public Integer getCustomisable() {
        return customisable;
    }

    public void setCustomisable(Integer customisable) {
        this.customisable = customisable;
    }

    public Integer getMall() {
        return mall;
    }

    public void setMall(Integer mall) {
        this.mall = mall;
    }

    public Integer getPhonecase() {
        return phonecase;
    }

    public void setPhonecase(Integer phonecase) {
        this.phonecase = phonecase;
    }

    public Integer getMug() {
        return mug;
    }

    public void setMug(Integer mug) {
        this.mug = mug;
    }

    public Integer getKeychain() {
        return keychain;
    }

    public void setKeychain(Integer keychain) {
        this.keychain = keychain;
    }

    public Integer getPopsocket() {
        return popsocket;
    }

    public void setPopsocket(Integer popsocket) {
        this.popsocket = popsocket;
    }

    public Integer getTshirt() {
        return tshirt;
    }

    public void setTshirt(Integer tshirt) {
        this.tshirt = tshirt;
    }

    public Integer getWallWoodenCanvasFrame() {
        return WallWoodenCanvasFrame;
    }

    public void setWallWoodenCanvasFrame(Integer wallWoodenCanvasFrame) {
        WallWoodenCanvasFrame = wallWoodenCanvasFrame;
    }

    public Integer getMultiImagePhotoFrames() {
        return MultiImagePhotoFrames;
    }

    public void setMultiImagePhotoFrames(Integer multiImagePhotoFrames) {
        MultiImagePhotoFrames = multiImagePhotoFrames;
    }

    public Integer getCoupleTshirt() {
        return CoupleTshirt;
    }

    public void setCoupleTshirt(Integer coupleTshirt) {
        CoupleTshirt = coupleTshirt;
    }
}
