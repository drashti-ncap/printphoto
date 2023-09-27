package com.mobile.cover.photo.editor.back.maker.model;

public class filter {
    String value;
    Boolean check;

    public filter(String value, Boolean check) {
        this.value = value;
        this.check = check;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
