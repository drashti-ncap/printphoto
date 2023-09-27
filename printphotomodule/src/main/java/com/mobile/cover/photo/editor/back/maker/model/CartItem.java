package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.rotate;

import java.io.Serializable;
import java.util.List;

public class CartItem implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("model_id")
    @Expose
    private Integer modelId;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("n_print_image")
    @Expose
    private String nPrintImage;
    @SerializedName("n_print_thumb_image")
    @Expose
    private String nPrintThumbImage;
    @SerializedName("n_case_image")
    @Expose
    private String nCaseImage;
    @SerializedName("n_case_thumb_image")
    @Expose
    private String nCaseThumbImage;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("product_price")
    @Expose
    private Double productPrice;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;


    @SerializedName("display_images")
    @Expose
    private List<DisplayImage> displayImages = null;
    @SerializedName("display")
    @Expose
    private rotate display;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getNPrintImage() {
        return nPrintImage;
    }

    public void setNPrintImage(String nPrintImage) {
        this.nPrintImage = nPrintImage;
    }

    public String getNPrintThumbImage() {
        return nPrintThumbImage;
    }

    public void setNPrintThumbImage(String nPrintThumbImage) {
        this.nPrintThumbImage = nPrintThumbImage;
    }

    public String getNCaseImage() {
        return nCaseImage;
    }

    public void setNCaseImage(String nCaseImage) {
        this.nCaseImage = nCaseImage;
    }

    public String getNCaseThumbImage() {
        return nCaseThumbImage;
    }

    public void setNCaseThumbImage(String nCaseThumbImage) {
        this.nCaseThumbImage = nCaseThumbImage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public List<DisplayImage> getDisplayImages() {
        return displayImages;
    }

    public void setDisplayImages(List<DisplayImage> displayImages) {
        this.displayImages = displayImages;
    }

    public rotate getDisplay() {
        return display;
    }

    public void setDisplay(rotate display) {
        this.display = display;
    }
}
