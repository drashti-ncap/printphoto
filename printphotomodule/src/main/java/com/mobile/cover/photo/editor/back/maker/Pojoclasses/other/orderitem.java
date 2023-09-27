package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.model.DisplayImage;

import java.util.List;

public class orderitem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("model_name")
    @Expose
    private String modelName;
    @SerializedName("is_cancellable")
    @Expose
    private Integer is_cancellable;
    @SerializedName("cancellation_message")
    @Expose
    private String cancellation_message;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;
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

    public Integer getIs_cancellable() {
        return is_cancellable;
    }

    public void setIs_cancellable(Integer is_cancellable) {
        this.is_cancellable = is_cancellable;
    }

    public String getCancellation_message() {
        return cancellation_message;
    }

    public void setCancellation_message(String cancellation_message) {
        this.cancellation_message = cancellation_message;
    }

    public List<DisplayImage> getDisplayImages() {
        return displayImages;
    }

    public void setDisplayImages(List<DisplayImage> displayImages) {
        this.displayImages = displayImages;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
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

    public rotate getDisplay() {
        return display;
    }

    public void setDisplay(rotate display) {
        this.display = display;
    }
}
