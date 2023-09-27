package com.mobile.cover.photo.editor.back.maker.model;

public class getdefault_images {
    private String category_id, name, img, flag, thumb;

    public getdefault_images(String category_id, String name, String img, String flag) {
        this.category_id = category_id;
        this.name = name;
        this.img = img;
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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


}
