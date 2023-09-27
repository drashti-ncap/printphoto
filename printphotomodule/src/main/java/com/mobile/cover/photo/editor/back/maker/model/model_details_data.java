package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class model_details_data {
    @SerializedName("model_id")
    @Expose
    private Integer modelId;
    @SerializedName("modalName")
    @Expose
    private String modalName;
    @SerializedName("modal_code")
    @Expose
    private String modalCode;
    @SerializedName("n_modal_image")
    @Expose
    private String nModalImage;
    @SerializedName("n_mask_image")
    @Expose
    private String nMaskImage;
    @SerializedName("n_new_modal_image")
    @Expose
    private String nNewModalImage;
    @SerializedName("n_new_mask_image")
    @Expose
    private String nNewMaskImage;
    @SerializedName("n_mug_image")
    @Expose
    private String n_mug_image;
    @SerializedName("international_n_mug_image")
    @Expose
    private String international_n_mug_image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("display_width")
    @Expose
    private String displayWidth;
    @SerializedName("display_height")
    @Expose
    private String displayHeight;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("dummy_price")
    @Expose
    private String dummyPrice;
    @SerializedName("image_type")
    @Expose
    private Integer imageType;
    @SerializedName("video_url")
    @Expose
    private String video_url;
    @SerializedName("international_video_url")
    @Expose
    private String international_video_url;
    @SerializedName("available_stock")
    @Expose
    private String available_stock;
    @SerializedName("frame_details")
    @Expose
    private List<FrameDetail> frameDetails = null;

    @SerializedName("locket_details")
    @Expose
    private List<LocketDetail> locket_details = null;

    public List<LocketDetail> getLocket_details() {
        return locket_details;
    }

    public void setLocket_details(List<LocketDetail> locket_details) {
        this.locket_details = locket_details;
    }

    public String getInternational_n_mug_image() {
        return international_n_mug_image;
    }

    public void setInternational_n_mug_image(String international_n_mug_image) {
        this.international_n_mug_image = international_n_mug_image;
    }

    public String getInternational_video_url() {
        return international_video_url;
    }

    public void setInternational_video_url(String international_video_url) {
        this.international_video_url = international_video_url;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModalName() {
        return modalName;
    }

    public void setModalName(String modalName) {
        this.modalName = modalName;
    }

    public String getModalCode() {
        return modalCode;
    }

    public void setModalCode(String modalCode) {
        this.modalCode = modalCode;
    }

    public String getNModalImage() {
        return nModalImage;
    }

    public void setNModalImage(String nModalImage) {
        this.nModalImage = nModalImage;
    }

    public String getNMaskImage() {
        return nMaskImage;
    }

    public void setNMaskImage(String nMaskImage) {
        this.nMaskImage = nMaskImage;
    }

    public String getNNewModalImage() {
        return nNewModalImage;
    }

    public void setNNewModalImage(String nNewModalImage) {
        this.nNewModalImage = nNewModalImage;
    }

    public String getNNewMaskImage() {
        return nNewMaskImage;
    }

    public void setNNewMaskImage(String nNewMaskImage) {
        this.nNewMaskImage = nNewMaskImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(String displayWidth) {
        this.displayWidth = displayWidth;
    }

    public String getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(String displayHeight) {
        this.displayHeight = displayHeight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDummyPrice() {
        return dummyPrice;
    }

    public void setDummyPrice(String dummyPrice) {
        this.dummyPrice = dummyPrice;
    }

    public Integer getImageType() {
        return imageType;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
    }

    public String getN_mug_image() {
        return n_mug_image;
    }

    public void setN_mug_image(String n_mug_image) {
        this.n_mug_image = n_mug_image;
    }

    public String getvideo_url() {
        return video_url;
    }

    public void setvideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getAvailable_stock() {
        return available_stock;
    }

    public void setAvailable_stock(String available_stock) {
        this.available_stock = available_stock;
    }

    public List<FrameDetail> getFrameDetails() {
        return frameDetails;
    }

    public void setFrameDetails(List<FrameDetail> frameDetails) {
        this.frameDetails = frameDetails;
    }
}
