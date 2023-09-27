package com.mobile.cover.photo.editor.back.maker.model;

public class test_getdefault_images {
    private String category_id, name, img, maskheight, maskwidth;

    public test_getdefault_images(String category_id, String name, String img, String maskheight, String maskwidth) {
        this.category_id = category_id;
        this.name = name;
        this.img = img;
        this.maskheight = maskheight;
        this.maskwidth = maskwidth;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMaskheight() {
        return maskheight;
    }

    public void setMaskheight(String maskheight) {
        this.maskheight = maskheight;
    }

    public String getMaskwidth() {
        return maskwidth;
    }

    public void setMaskwidth(String maskwidth) {
        this.maskwidth = maskwidth;
    }
}
