package com.mobile.cover.photo.editor.back.maker.model;

/**
 * Created by Urvashi-vasundhara on 3/16/2017.
 */
public class new_StickerModel {

     /* Drawable drawable;

      public Drawable getDrawable() {
          return drawable;
      }

      public void setDrawable(Drawable drawable) {
          this.drawable = drawable;
      }*/


    String name;
    int image;

    public new_StickerModel(Integer a_sticker, String name) {

        this.image = a_sticker;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }
}
