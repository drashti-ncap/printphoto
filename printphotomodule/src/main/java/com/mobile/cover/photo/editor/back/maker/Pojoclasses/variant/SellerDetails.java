
package com.mobile.cover.photo.editor.back.maker.Pojoclasses.variant;

import com.google.gson.annotations.SerializedName;

public class SellerDetails {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("id")
    private Long mId;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
