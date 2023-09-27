package com.mobile.cover.photo.editor.back.maker.model;

public class Address {

    String type;
    String pincode;
    String home_no;
    String street;
    String landmark;
    String city;
    String state;
    String country;
    boolean select = false;

    public static Address convertToAdddress(String string) {

        Address address = new Address();

        String[] add = string.split("$");

        return address;

    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHome_no() {
        return home_no;
    }

    public void setHome_no(String home_no) {
        this.home_no = home_no;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
