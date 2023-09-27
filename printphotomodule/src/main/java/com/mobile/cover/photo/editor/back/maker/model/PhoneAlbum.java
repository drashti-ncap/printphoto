package com.mobile.cover.photo.editor.back.maker.model;

import java.util.Vector;

/**
 * Created by admin on 11/18/2016.
 */
public class PhoneAlbum {

    private int id;
    private String name;
    private String coverUri;
    private Vector<PhonePhoto> albumPhotos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUri() {
        return coverUri;
    }

    public void setCoverUri(String albumCoverUri) {
        this.coverUri = albumCoverUri;
    }

    public Vector<PhonePhoto> getAlbumPhotos() {
        if (albumPhotos == null) {
            albumPhotos = new Vector<>();
        }
        return albumPhotos;
    }

    public void setAlbumPhotos(Vector<PhonePhoto> albumPhotos) {
        this.albumPhotos = albumPhotos;
    }
}