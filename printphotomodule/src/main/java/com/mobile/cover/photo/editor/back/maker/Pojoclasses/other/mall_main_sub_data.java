package com.mobile.cover.photo.editor.back.maker.Pojoclasses.other;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.variant.ProductDetails1;
import com.mobile.cover.photo.editor.back.maker.model.mall_seller_details;

import java.util.List;

public class mall_main_sub_data {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("general_category_id")
    @Expose
    private Integer general_category_id;

    @SerializedName("variants")
    @Expose
    private Variant variants;

    @SerializedName("multiple_variants")
    @Expose
    public List<MultipleVariant> multiple_variants;


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("available_stock")
    @Expose
    private Integer availableStock;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("dummy_price")
    @Expose
    private String dummyPrice;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("you_save")
    @Expose
    private String you_save;
    @SerializedName("in_wishlist")
    @Expose
    private Boolean in_wishlist;
    @SerializedName("product_details")
    @Expose
    private List<ProductDetail> productDetails = null;
    @SerializedName("product_details_new")
    private List<ProductDetails1> mProductDetails1;
    @SerializedName("product_images")
    @Expose
    private List<ProductImage> productImages = null;
    @SerializedName("seller_details")
    @Expose
    private mall_seller_details sellerDetails;
    @SerializedName("general_category")
    @Expose
    private GeneralCategory generalCategory;

    public Integer getGeneral_category_id() {
        return general_category_id;
    }

    public void setGeneral_category_id(Integer general_category_id) {
        this.general_category_id = general_category_id;
    }

    public List<ProductDetails1> getmProductDetails1() {
        return mProductDetails1;
    }

    public void setmProductDetails1(List<ProductDetails1> mProductDetails1) {
        this.mProductDetails1 = mProductDetails1;
    }

    public GeneralCategory getGeneralCategory() {
        return generalCategory;
    }

    public void setGeneralCategory(GeneralCategory generalCategory) {
        this.generalCategory = generalCategory;
    }

    public Variant getVariants() {
        return variants;
    }

    public void setVariants(Variant variants) {
        this.variants = variants;
    }

    public String getYou_save() {
        return you_save;
    }

    public void setYou_save(String you_save) {
        this.you_save = you_save;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Boolean getIn_wishlist() {
        return in_wishlist;
    }

    public void setIn_wishlist(Boolean in_wishlist) {
        this.in_wishlist = in_wishlist;
    }

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public mall_seller_details getSellerDetails() {
        return sellerDetails;
    }

    public void setSellerDetails(mall_seller_details sellerDetails) {
        this.sellerDetails = sellerDetails;
    }

    public List<MultipleVariant> getMultiple_variants() {
        return multiple_variants;
    }

    public void setMultiple_variants(List<MultipleVariant> multiple_variants) {
        this.multiple_variants = multiple_variants;
    }
}
