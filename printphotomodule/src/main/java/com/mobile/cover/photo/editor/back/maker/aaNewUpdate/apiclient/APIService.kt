package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient

import com.google.gson.JsonObject
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Country_state_city_code_response
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.*
import com.mobile.cover.photo.editor.back.maker.activity.Testing.paypal_model
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq
import com.mobile.cover.photo.editor.back.maker.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {


    @Multipart
    @POST("seller")
    fun getResponse(@Part("name") name: RequestBody?,
                    @Part("email") email: RequestBody?,
                    @Part("mobile") mobile: RequestBody?,
                    @Part("aadhar_no") aadhar_no: RequestBody?,
                    @Part("business_type") business_type: RequestBody?,
                    @Part("user_id") user_id: RequestBody?,
                    @Part("ln") ln: RequestBody?
    ): Call<Responsedata?>?


    @FormUrlEncoded
    @POST("check_seller_status")
    fun getstatus(@Field("user_id") user_id: Int?,
                  @Field("ln") ln: String?): Call<Getstatus?>?

    @FormUrlEncoded
    @POST("checkout")
    fun getorderid(
            @Field("user_id") user_id: Int?,
            @Field("ln") ln: String?): Call<orderidresponse?>?

    @FormUrlEncoded
    @POST("get_offers")
    fun getoffer(
            @Field("for_mall") for_mall: String?,
            @Field("is_international") is_international: String?,
            @Field("user_id") user_id: String?,
            @Field("ln") ln: String?,
            @Field("country_code") country_code: String?
    ): Call<getofferresponse?>?

    @FormUrlEncoded
    @POST("get_companies")
    fun getmugdetails(@Field("category_id") category_id: Int?): Call<getmugresponse?>?

    @FormUrlEncoded
    @POST("get_order")
    fun getorderdetiails(@Field("user_id") user_id: Int?): Call<getorderresponse?>?

    @FormUrlEncoded
    @POST("add_complain")
    fun postcomplain(@Field("order_id") order_id: String?,
                     @Field("user_id") user_id: String?,
                     @Field("complain") complain: String?,
                     @Field("ln") ln: String?
    ): Call<postcomplain?>?

    @FormUrlEncoded
    @POST("TXNStatus.php")
    fun gettxnstatusresponse(@Field("ORDER_ID") order_id: String?): Call<Txnstatusresponse?>?

    @FormUrlEncoded
    @POST("save_request")
    fun postrequest(@Field("user_id") user_id: String?,
                    @Field("request_model") request_model: String?,
                    @Field("ln") ln: String?
    ): Call<responseModelRequest?>?

    @FormUrlEncoded
    @POST("saveData")
    fun post_new_request_model(@Field("modal_id") modal_id: String?, @Field("user_id") user_id: String?, @Field("request_model") request_model: String?,
                               @Field("request_model_id") request_model_id: String?,
                               @Field("ln") ln: String?
    ): Call<responseModelRequest?>?

    @FormUrlEncoded
    @POST("get_response")
    fun getcomplainresponse(@Field("user_id") user_id: String?,
                            @Field("order_id") order_id: String?,
                            @Field("ln") ln: String?
    ): Call<chatResponse?>?

    @FormUrlEncoded
    @POST("offer_details")
    fun getcodeapply(@Field("offer_code") promo_code: String?): Call<promo?>?

    @FormUrlEncoded
    @POST("user_promo_details")
    fun applyPromoCode(@Field("promo_code") promo_code: String?): Call<promo?>?

    @FormUrlEncoded
    @POST("validation")
    fun applyCouponCode(@Field("user_id") userId: String?, @Field("code") couponCode: String?, @Field("status") requestType: Int): Call<CouponCodeRes?>?

    @FormUrlEncoded
    @POST("seller_wallet")
    fun gettransaction(
            @Field("seller_id") seller_id: String?,
            @Field("ln") ln: String?
    ): Call<TransactionWithdrawResponse?>?

    @FormUrlEncoded
    @POST("seller_request")
    fun sendrequest(@Field("seller_id") seller_id: String?): Call<moneyrequest?>?

    @FormUrlEncoded
    @POST("can_register")
    fun verify(@Field("mobile") mobile: String?,
               @Field("email") email: String?,
               @Field("ln") ln: String?
    ): Call<new_numberverify?>?

    @FormUrlEncoded
    @POST("can_send_otp")
    fun forget_verify(@Field("mobile") mobile: String?,
                      @Field("email") email: String?,
                      @Field("ln") ln: String?
    ): Call<new_numberverify?>?


    @FormUrlEncoded
    @POST("change_password")
    fun changepswd(@Field("password") password: String?,
                   @Field("mobile") mobile: String?,
                   @Field("ln") ln: String?
    ): Call<numberverify?>?

    @FormUrlEncoded
    @POST("ccavenue/get_order_status")
    fun ccavenue_transaction(@Field("order_id") order_id: String?): Call<cc_avenue_transaction_status?>?

    @FormUrlEncoded
    @POST("new_password")
    fun changePassword(@Field("user_id") userId: String?,
                       @Field("old_password") oldPassword: String?,
                       @Field("new_password") newPassword: String?,
                       @Field("ln") ln: String?
    ): Call<numberverify?>?

    @FormUrlEncoded
    @POST("deleteAddress")
    fun deleteaddress(@Field("user_id") userid: String?, @Field("address_no") addno: String?): Call<addressResponse?>?

    @Multipart
    @POST("new_complains")
    fun send_new_complain(@Part("user_id") userid: RequestBody?,
                          @Part("message") message: RequestBody?,
                          @Part("ln") ln: RequestBody?,
                          @Part image: MultipartBody.Part?
    ): Call<complain_feedback_response?>?

    @FormUrlEncoded
    @POST("case_categories")
    fun getcaseimages(
            @Field("platform") platform: Int,
            @Field("country_code") country_code: String?,
            @Field("user_id") user_id: String?,
            @Field("ln") ln: String?
    ): Call<customimage_response?>?


    @POST("test_images")
    fun gettestcaseimages(): Call<test_image_response?>?

    @POST("new_stickers")
    fun getstickers(): Call<sticker_main_response?>?

    @POST("country_data")
    fun getcountry_states_city_code(): Call<Country_state_city_code_response?>?

    @FormUrlEncoded
    @POST(URL_CONFIG)
    fun get_configurations(@Field("country_code") Country_code: String?): Call<setting_model_main_response?>?

    /*   @POST(URL_CONFIG)
       fun get_configurations(@Field("country_code") Country_code: String?): Deferred<Response<setting_model_main_response>>*/

    @FormUrlEncoded
    @POST("get_mall_enable_status")
    fun get_mall_enable_status(@Field("country_code") Country_code: String?): Call<mallstatusresponse?>?

    @FormUrlEncoded
    @POST("case_images")
    fun getimages(@Field("limit") limit: Int?, @Field("offset") offset: Int?, @Field("search") search: Int?, @Field("searchFields") searchFields: String?,
                  @Field("orderBy") orderBy: String?, @Field("sortedBy") sortedBy: String?): Call<custom_case_image_response?>?

    //    @POST("getAllModelDetails")
    //    Call<main_brand_model_data_response> get_brand_model_data();
    @FormUrlEncoded
    @POST("getOrderShipmentDetails")
    fun postRawJSON(@Field("username") username: String?, @Field("password") password: String?, @Field("order_id") order_id: String?): Call<JsonObject?>?

    @FormUrlEncoded
    @POST("save_request")
    fun post_written_request(@Field("user_id") user_id: String?, @Field("request_model") request_model: String?): Call<request_response_written?>?

    @FormUrlEncoded
    @POST("update_quantity")
    fun update_quantity(@Field("item_id") item_id: String?,
                        @Field("quantity") quantity: String?,
                        @Field("ln") ln: String?
    ): Call<update_quantity?>?

    @POST("Seller-Privacy-Poilcy.html")
    fun getprivacypolicy(): Call<policy_response_dashboard?>?

    @FormUrlEncoded
    @POST("generateChecksum.php")
    fun generate_checksum(@Field("ORDER_ID") Orderid: String?,
                          @Field("CUST_ID") CUST_ID: String?,
                          @Field("TXN_AMOUNT") TXN_AMOUNT: String?): Call<ResponseDashboard?>?

    @FormUrlEncoded
    @POST("send_otp")
    fun getotp(@Field("mobile_number") mobile_number: String?,
               @Field("email") email: String?,
               @Field("ln") ln: String?,
               @Field("is_international") is_international: String?
    ): Call<otp_response_data?>?

    @FormUrlEncoded
    @POST("save_address")
    fun save_address(@Field("id") id: String?,
                     @Field("receiver_name") receiver_name: String?,
                     @Field("alternate_mobile") mobile: String?,
                     @Field("user_id") user_id: String?,
                     @Field("address") address: String?,
                     @Field("address_1") address_1: String?,
                     @Field("city_id") city_id: String?,
                     @Field("country_id") country_id: String?,
                     @Field("pincode") pincode: String?,
                     @Field("ln") ln: String?
    ): Call<save_address_response?>?

    @FormUrlEncoded
    @POST("delete_address")
    fun delete_saved_address(
            @Field("id") id: String?,
            @Field("ln") ln: String?
    ): Call<save_address_response?>?

    @FormUrlEncoded
    @POST("addresses")
    fun get_saved_address(
            @Field("user_id") user_id: String?,
            @Field("ln") ln: String?
    ): Call<save_address_response?>?

    @FormUrlEncoded
    @POST("cod_availability")
    fun checkcod(@Field("address_id") address_id: String?, @Field("response_version") response_version: String?): Call<cod_response?>?

    @FormUrlEncoded
    @POST("cancel_order")
    fun cancel_order(@Field("order_id") order_id: String?, @Field("ln") ln: String?): Call<order_cancel?>?

    @FormUrlEncoded
    @POST("cancel_order")
    fun cancel_order(@Field("order_id") order_id: String?, @Field("cancel_reason_id") reason_id: String?,
                     @Field("cancel_description") reasond_desc: String?, @Field("ln") ln: String?): Call<order_cancel?>?

    @FormUrlEncoded
    @POST("send_otp_email")
    fun send_email_otp(@Field("email") email: String?, @Field("verifyEmail") verifyEmail: String?): Call<email_response?>?

    //    @FormUrlEncoded
    //    @POST("order/create")
    //    Call<cart_address_check_model> check_address_cart(@Field("address_id") String address_id, @Field("cart_ids") String cart_ids);
    @FormUrlEncoded
    @POST("order/apply_discount")
    fun applyCouponCode_new(@Field("order_id") order_id: String?,
                            @Field("discount_code") discount_code: String?,
                            @Field("gift") gift: String?,
                            @Field("ln") ln: String?
    ): Call<apply_code_response?>?

    @FormUrlEncoded
    @POST("order/initiate_transaction")
    fun initiate_transaction(@Field("order_id") order_id: String?,
                             @Field("transaction_type") transaction_type: String?,
                             @Field("device_type") device_type: String?,
                             @Field("additional_shipping_charge") additional_shipping_charge: Double?,
                             @Field("gst_charges") gst_charges: Float,
                             @Field("ln") ln: String?
    ): Call<response_order_initate?>?

    @FormUrlEncoded
    @POST("order/set_final_status")
    fun get_final_status(@Field("order_id") order_id: String?,
                         @Field("cancel_order") cancel: Int,
                         @Field("razorpay_order_id") razorpay_order_id: String?,
                         @Field("razorpay_payment_id") razorpay_payment_id: String?,
                         @Field("razorpay_signature") razorpay_signature: String?,
                         @Field("device_type") device_type: String?,
                         @Field("app_version") app_version: String?,
            /* @Field("shipping_charge") shipping_charge: String?,*/
                         @Field("ln") ln: String?
    ): Call<get_final_status_response?>?

    @FormUrlEncoded
    @POST("user_exists")
    fun verify_user_mobile_email(@Field("mobile") mobile: String?, @Field("email") email: String?): Call<verify_user_main?>?

    @FormUrlEncoded
    @POST("check_stock")
    fun check_stock(
            @Field("cart_ids") cart_ids: String?,
            @Field("ln") ln: String?
    ): Call<check_stock_main_response?>?


    // Main Category on Home Page
    @FormUrlEncoded
    @POST("get_categories")
    fun get_new_MainCatagaries(@Field("country_code") Country_code: String?,
                               @Field("user_id") user_id: String?,
                               @Field("ln") ln: String?
    ): Call<new_main_model?>?


    @POST("mug_fancy_images")
    fun get_mug_images(): Call<mug_image_response?>?

    @POST("fancy_bottle_shipper_images")
    fun get_shipper_images(): Call<mug_image_response?>?

    @FormUrlEncoded
    @POST("mall_categories")
    fun get_mall_category(@Field("country_code") Country_code: String?,
                          @Field("user_id") user_id: String?,
                          @Field("ln") ln: String?
    ): Call<mall_new_main_model?>?

    @POST("getAllModelDetails")
    fun get_brand_model_data(): Call<main_brand_model_data_response?>?


    @FormUrlEncoded
    @POST("background_images")
    fun getbackground_images(
            @Field("category_id") category_id: String?,
            @Field("ln") ln: String?
    ): Call<background_response?>?

    @FormUrlEncoded
    @POST("get_companies")
    fun getSubMainCatagaries(@Field("category_id") category_id: String?): Call<SubMainModel?>?

    @FormUrlEncoded
    @POST("get_orders_split")
    fun getorderdetiails(
            @Field("user_id") user_id: Int?,
            @Field("ln") ln: String?
    ): Call<getorderresponse?>?

    @FormUrlEncoded
    @POST("registration")
    fun getRegResponse(@Field(RegReq.name) name: String?,
                       @Field(RegReq.email) email: String?,
                       @Field(RegReq.pincode) pincode: String?,
                       @Field(RegReq.mobile_1) mobile_1: String?,
                       @Field(RegReq.mobile_2) mobile_2: String?,
                       @Field(RegReq.city) city: String?,
                       @Field("country_id") country_id: String?,
                       @Field(RegReq.password) password: String?,
                       @Field(RegReq.device_token) device_token: String?,
                       @Field("timezone") timezone: String?,
                       @Field("ln") ln: String?
    ): Call<registration_main_response?>?


    @FormUrlEncoded
    @POST("update")
    fun getRegiUpdate(
            @Field("id") id: String?,
            @Field("name") name: String?,
            @Field("email") email: String?,
            @Field("currency_id") currency_id: String?,
            @Field("ln") ln: String?
    ): Call<response_main_username_update?>?

    @FormUrlEncoded
    @POST("login")
    fun getRegResponseLogin(
            @Field("mobile") mobile_1: String?,
            @Field("password") password: String?,
            @Field("device_token") device_token: String?,
            @Field("device_type") device_type: String?,
            @Field("timezone") timezone: String?,
            @Field("ln") ln: String?
    ): Call<RegResponse?>?


    @FormUrlEncoded
    @POST("get_cart")
    fun getCart(
            @Field("user_id") user_id: String?,
            @Field("ln") ln: String?
    ): Call<get_Cart?>?


    @FormUrlEncoded
    @POST("pin_detail")
    fun getPincodeDetails(
            @Field("pincode") pincode: String?,
            @Field("ln") ln: String?
    ): Call<PinCode?>?


    @FormUrlEncoded
    @POST("get_order")
    fun getOrder(@Field("user_id") user_id: String?): Call<SimpleResponse?>?

    @FormUrlEncoded
    @POST("delete")
    fun deleteCart(@Field("delete") delete: String?, @Field("id") id: String?,
                   @Field("user_id") user_id: String?,
                   @Field("ln") ln: String?
    ): Call<SimpleResponse?>?


    @POST("add_to_cart")
    fun sendCart(@Body files: RequestBody?): Call<Cart?>?


    @FormUrlEncoded
    @POST("order/create")
    fun check_address_cart(@Field("address_id") address_id: String?,
                           @Field("cart_ids") cart_ids: String?,
                           @Field("ln") ln: String?
    ): Call<cart_address_check_model?>?

    @FormUrlEncoded
    @POST("bulk_order")
    fun bulk_order(@Field("user_id") user_id: Int?,
                   @Field("name") name: String?,
                   @Field("email") email: String?,
                   @Field("mobile") mobile: String?,
                   @Field("category_id") category_id: Int?,
                   @Field("qty") qty: String?,
                   @Field("description") description: String?,
                   @Field("bussiness") bussiness: String?,
                   @Field("ln") ln: String?
    ): Call<main_response_bulk?>?

    @FormUrlEncoded
    @POST("cancel_order_item")
    fun cancel_order_item(
            @Field("order_item_id") order_item_id: String?,
            @Field("ln") ln: String?,
            @Field("app_version") app_version: String?
    ): Call<SimpleResponse?>?

    @FormUrlEncoded
    @POST("paypal/initiate_payment")
    fun paypal(@Field("order_id") order_id: String?): Call<paypal_model?>?

    @FormUrlEncoded
    @POST("bulk_order_status")
    fun bulk_order_status(
            @Field("user_id") user_id: String?,
            @Field("ln") ln: String?
    ): Call<bulk_order_dashboard_Response?>?

    @FormUrlEncoded
    @POST("get_products")
    fun getProducts(
            @Field("category_id") category_id: String?,
            @Field("country_code") country_code: String?,
            @Field("user_id") user_id: String?,
            @Field("ln") ln: String?
    ): Call<sub_category_model_details?>?


    @FormUrlEncoded
    @POST("logout")
    fun logout(
            @Field("token") token: String?,
            @Field("ln") ln: String?
    ): Call<logout_response?>?

    @FormUrlEncoded
    @POST("mall_products")
    fun call_main_categoru_subdata(
            @Field("category_id") category_id: Int?,
            @Field("user_id") user_id: Int?,
            @Field("country_code") country_code: String?,
            @Field("sort") sort: String?,
            @Field("ln") ln: String?
    ): Call<mall_main_category_response_click_data?>?


    @FormUrlEncoded
    @POST("mall_products")
    fun search(
            @Field("user_id") user_id: Int?,
            @Field("search") search: String?,
            @Field("country_code") country_code: String?,
            @Field("ln") ln: String?
    ): Call<mall_main_category_response_click_data?>?


    @FormUrlEncoded
    @POST("mall_products")
    fun wishlist(
            @Field("user_id") user_id: Int?,
            @Field("country_code") country_code: String?,
            @Field("wishlist") wishlist: String?,
            @Field("ln") ln: String?
    ): Call<mall_main_category_response_click_data?>?


    @FormUrlEncoded
    @POST("pincode_serviceability")
    fun check_pincode_service(
            @Field("pincode") pincode: Int?,
            @Field("ln") ln: String?
    ): Call<main_response?>?


    @FormUrlEncoded
    @POST("add_wishlist")
    fun check_uncheck_wishlist(
            @Field("user_id") user_id: String?,
            @Field("product_id") product_id: String?,
            @Field("ln") ln: String?
    ): Call<main_response?>?

/*    @FormUrlEncoded
    @POST("order/set_final_status")
    fun get_final_status(@Field("order_id") order_id: String?,
                         @Field("cancel_order") cancel: Int,
                         @Field("razorpay_order_id") razorpay_order_id: String?,
                         @Field("razorpay_payment_id") razorpay_payment_id: String?,
                         @Field("razorpay_signature") razorpay_signature: String?,
                         @Field("device_type") device_type: String?,
                         @Field("app_version") app_version: String?,
                         @Field("ln") ln: String?
    ): Call<get_final_status_response?>?*/

}