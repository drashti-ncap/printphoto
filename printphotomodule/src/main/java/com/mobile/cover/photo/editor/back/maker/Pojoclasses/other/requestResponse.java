package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class requestResponse {
    @SerializedName("request_model_id")
    @Expose
    private String requestModelId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("request_model")
    @Expose
    private String requestModel;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("general_category_id")
    @Expose
    private String generalCategoryId;
    @SerializedName("modalName")
    @Expose
    private String modalName;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("n_modal_image")
    @Expose
    private String modalImage;
    @SerializedName("n_mask_image")
    @Expose
    private String maskImage;
    @SerializedName("available_stock")
    @Expose
    private String availableStock;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("dummy_price")
    @Expose
    private String dummyPrice;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("mug_image")
    @Expose
    private String mugImage;

    public String getRequestModelId() {
        return requestModelId;
    }

    public void setRequestModelId(String requestModelId) {
        this.requestModelId = requestModelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(String requestModel) {
        this.requestModel = requestModel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGeneralCategoryId() {
        return generalCategoryId;
    }

    public void setGeneralCategoryId(String generalCategoryId) {
        this.generalCategoryId = generalCategoryId;
    }

    public String getModalName() {
        return modalName;
    }

    public void setModalName(String modalName) {
        this.modalName = modalName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getModalImage() {
        return modalImage;
    }

    public void setModalImage(String modalImage) {
        this.modalImage = modalImage;
    }

    public String getMaskImage() {
        return maskImage;
    }

    public void setMaskImage(String maskImage) {
        this.maskImage = maskImage;
    }

    public String getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(String availableStock) {
        this.availableStock = availableStock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getDummyPrice() {
        return dummyPrice;
    }

    public void setDummyPrice(String dummyPrice) {
        this.dummyPrice = dummyPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMugImage() {
        return mugImage;
    }

    public void setMugImage(String mugImage) {
        this.mugImage = mugImage;
    }

}
