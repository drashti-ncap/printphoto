package com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.model;

/**
 * Created by Sonal on 04-09-2017.
 */
public class DimensionModel {


    int X;
    int Y;
    int Width;
    int Height;

    public DimensionModel(int width, int height, int x, int y) {
        Width = width;
        Height = height;
        X = x;
        Y = y;
    }


    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }


}
