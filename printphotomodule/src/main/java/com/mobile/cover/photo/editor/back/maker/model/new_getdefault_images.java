package com.mobile.cover.photo.editor.back.maker.model;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Sticker;

import java.util.ArrayList;
import java.util.List;

public class new_getdefault_images {
    private String category_id, name, img;
    private List<Sticker> sqlist = new ArrayList<>();

    public new_getdefault_images(String category_id, String name, String img, List<Sticker> sqlist) {
        this.category_id = category_id;
        this.name = name;
        this.img = img;
        this.sqlist = sqlist;
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

    public List<Sticker> getSqlist() {
        return sqlist;
    }

    public void setSqlist(List<Sticker> sqlist) {
        this.sqlist = sqlist;
    }
}
