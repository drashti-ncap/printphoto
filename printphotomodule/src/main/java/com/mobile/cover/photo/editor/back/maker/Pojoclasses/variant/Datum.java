
package com.mobile.cover.photo.editor.back.maker.Pojoclasses.variant;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("available_stock")
    private Long mAvailableStock;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("discount")
    private String mDiscount;
    @SerializedName("dummy_price")
    private String mDummyPrice;
    @SerializedName("general_category")
    private GeneralCategory mGeneralCategory;
    @SerializedName("general_category_id")
    private Long mGeneralCategoryId;
    @SerializedName("id")
    private Long mId;
    @SerializedName("in_wishlist")
    private Boolean mInWishlist;
    @SerializedName("international_video_url")
    private String mInternationalVideoUrl;
    @SerializedName("name")
    private String mName;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("product_details1")
    private List<ProductDetails1> mProductDetails1;
    @SerializedName("product_images")
    private List<ProductImage> mProductImages;
    @SerializedName("seller_details")
    private SellerDetails mSellerDetails;
    @SerializedName("variants")
    private Variants mVariants;
    @SerializedName("video_url")
    private String mVideoUrl;
    @SerializedName("you_save")
    private String mYouSave;

    public Long getAvailableStock() {
        return mAvailableStock;
    }

    public void setAvailableStock(Long availableStock) {
        mAvailableStock = availableStock;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDiscount() {
        return mDiscount;
    }

    public void setDiscount(String discount) {
        mDiscount = discount;
    }

    public String getDummyPrice() {
        return mDummyPrice;
    }

    public void setDummyPrice(String dummyPrice) {
        mDummyPrice = dummyPrice;
    }

    public GeneralCategory getGeneralCategory() {
        return mGeneralCategory;
    }

    public void setGeneralCategory(GeneralCategory generalCategory) {
        mGeneralCategory = generalCategory;
    }

    public Long getGeneralCategoryId() {
        return mGeneralCategoryId;
    }

    public void setGeneralCategoryId(Long generalCategoryId) {
        mGeneralCategoryId = generalCategoryId;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Boolean getInWishlist() {
        return mInWishlist;
    }

    public void setInWishlist(Boolean inWishlist) {
        mInWishlist = inWishlist;
    }

    public String getInternationalVideoUrl() {
        return mInternationalVideoUrl;
    }

    public void setInternationalVideoUrl(String internationalVideoUrl) {
        mInternationalVideoUrl = internationalVideoUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public List<ProductDetails1> getProductDetails1() {
        return mProductDetails1;
    }

    public void setProductDetails1(List<ProductDetails1> productDetails1) {
        mProductDetails1 = productDetails1;
    }

    public List<ProductImage> getProductImages() {
        return mProductImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        mProductImages = productImages;
    }

    public SellerDetails getSellerDetails() {
        return mSellerDetails;
    }

    public void setSellerDetails(SellerDetails sellerDetails) {
        mSellerDetails = sellerDetails;
    }

    public Variants getVariants() {
        return mVariants;
    }

    public void setVariants(Variants variants) {
        mVariants = variants;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public String getYouSave() {
        return mYouSave;
    }

    public void setYouSave(String youSave) {
        mYouSave = youSave;
    }

}
