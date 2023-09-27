package com.mobile.cover.photo.editor.back.maker.model;

public class background_rv_images {
    private String id, images;

    public background_rv_images(String id, String images) {
        this.id = id;
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
