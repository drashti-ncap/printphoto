package com.mobile.cover.photo.editor.back.maker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubDataModelDetails {

    @SerializedName("model_id")
    String id;

    @SerializedName("modalName")
    String modalName;

    @SerializedName("company_id")
    String company_id;

    @SerializedName("n_modal_image")
    String modal_image;

    @SerializedName("n_new_modal_image")
    String n_modal_image;

    @SerializedName("width")
    String width;

    @SerializedName("height")
    String height;

    @SerializedName("n_new_mask_image")
    String n_mask_image;


    @SerializedName("banner_image")
    String banner;


    @SerializedName("shipping")
    String shipping;


    @SerializedName("discount")
    String discount;


    @SerializedName("description")
    String description;


    @SerializedName("size")
    String size;

    @SerializedName("price")
    String price;

    @SerializedName("image_type")
    Integer image_type;


    @SerializedName("dummy_price")
    String dummy_price;


    @SerializedName("created_at")
    String created_at;

    @SerializedName("updated_at")
    String updated_at;


    @SerializedName("deleted_at")
    String deleted_at;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModalName() {
        return modalName;
    }

    public void setModalName(String modalName) {
        this.modalName = modalName;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getModal_image() {
        return modal_image;
    }

    public void setModal_image(String modal_image) {
        this.modal_image = modal_image;
    }

    public String getn_modal_image() {
        return n_modal_image;
    }

    public void setn_modal_image(String n_modal_image) {
        this.n_modal_image = n_modal_image;
    }

    public String getn_mask_image() {
        return n_mask_image;
    }

    public void setn_mask_image(String n_mask_image) {
        this.n_mask_image = n_mask_image;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getImage_type() {
        return image_type;
    }

    public void setImage_type(Integer image_type) {
        this.image_type = image_type;
    }


    public String getDummy_price() {
        return dummy_price;
    }

    public void setDummy_price(String dummy_price) {
        this.dummy_price = dummy_price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

}
