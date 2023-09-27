package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra {
    @SerializedName("keychain_exist")
    @Expose
    private Integer keychainExist;
    @SerializedName("best_offer")
    @Expose
    private String bestOffer;
    @SerializedName("best_offer_description")
    @Expose
    private String bestOfferDescription;

    public Integer getKeychainExist() {
        return keychainExist;
    }

    public void setKeychainExist(Integer keychainExist) {
        this.keychainExist = keychainExist;
    }

    public String getBestOffer() {
        return bestOffer;
    }

    public void setBestOffer(String bestOffer) {
        this.bestOffer = bestOffer;
    }

    public String getBestOfferDescription() {
        return bestOfferDescription;
    }

    public void setBestOfferDescription(String bestOfferDescription) {
        this.bestOfferDescription = bestOfferDescription;
    }


}
