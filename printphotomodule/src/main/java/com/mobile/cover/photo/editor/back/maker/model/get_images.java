package com.mobile.cover.photo.editor.back.maker.model;

public class get_images {
    private String id, category_id, name, image1, image2, image3, selling;

    public get_images(String id, String category_id, String name, String image1, String image2, String image3, String selling) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.selling = selling;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getSelling() {
        return selling;
    }

    public void setSelling(String selling) {
        this.selling = selling;
    }
}
