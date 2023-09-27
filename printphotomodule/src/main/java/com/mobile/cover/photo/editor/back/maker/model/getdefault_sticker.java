package com.mobile.cover.photo.editor.back.maker.model;

public class getdefault_sticker {
    private String category_id, img;

    public getdefault_sticker(String category_id, String img) {
        this.category_id = category_id;
        this.img = img;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
