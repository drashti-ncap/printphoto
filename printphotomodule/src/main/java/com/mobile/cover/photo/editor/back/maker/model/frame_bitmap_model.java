package com.mobile.cover.photo.editor.back.maker.model;

import android.graphics.Bitmap;

public class frame_bitmap_model {
    String mask_bitmap;
    String height;
    String width;
    String disp_height;
    String disp_width;
    private Bitmap save_mask_bitmap, original_bitmap;
    private int id;

    public Bitmap getSave_mask_bitmap() {
        return save_mask_bitmap;
    }

    public void setSave_mask_bitmap(Bitmap save_mask_bitmap) {
        this.save_mask_bitmap = save_mask_bitmap;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDisp_height() {
        return disp_height;
    }

    public void setDisp_height(String disp_height) {
        this.disp_height = disp_height;
    }

    public String getDisp_width() {
        return disp_width;
    }

    public void setDisp_width(String disp_width) {
        this.disp_width = disp_width;
    }

    public Bitmap getOriginal_bitmap() {
        return original_bitmap;
    }

    public void setOriginal_bitmap(Bitmap original_bitmap) {
        this.original_bitmap = original_bitmap;
    }

    public String getMask_bitmap() {
        return mask_bitmap;
    }

    public void setMask_bitmap(String mask_bitmap) {
        this.mask_bitmap = mask_bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
