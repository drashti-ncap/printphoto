package com.mobile.cover.photo.editor.back.maker.Commen;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Offer;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.OrderDetails_response;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.ProductDetail;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.ProductImage;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.SellerOrder;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Sticker;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.background_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.mall_main_sub_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.mug_image_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.save_address_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.variant.ProductDetails1;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.DrawableSticker;
import com.mobile.cover.photo.editor.back.maker.model.AllChild;
import com.mobile.cover.photo.editor.back.maker.model.AvailableFilter;
import com.mobile.cover.photo.editor.back.maker.model.CartItem;
import com.mobile.cover.photo.editor.back.maker.model.CityState;
import com.mobile.cover.photo.editor.back.maker.model.Currency;
import com.mobile.cover.photo.editor.back.maker.model.FrameDetail;
import com.mobile.cover.photo.editor.back.maker.model.bulk_category_model;
import com.mobile.cover.photo.editor.back.maker.model.filter;
import com.mobile.cover.photo.editor.back.maker.model.frame_bitmap_model;
import com.mobile.cover.photo.editor.back.maker.model.get_images;
import com.mobile.cover.photo.editor.back.maker.model.getdefault_images;
import com.mobile.cover.photo.editor.back.maker.model.mall_AllChild;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;
import com.mobile.cover.photo.editor.back.maker.model.new_getdefault_images;
import com.mobile.cover.photo.editor.back.maker.model.region_model_data;
import com.mobile.cover.photo.editor.back.maker.model.request_final_brand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Share {
    public static DrawableSticker TEXT_DRAWABLE = null;
    public static Bitmap bitmap;
    public static float maskheight;
    public static float maskwidth;
    public static String FONT_TEXT = "";
    public static boolean FONT_FLAG = false;
    public static String FONT_EFFECT = "6";
    public static String FONT_STYLE = "";
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    public static int isapproved = 0;
    public static boolean isback;
    public static int mobile_type = 0;
    public static int paymenttype = 0;
    public static String promo = "null";
    public static String type = "null";
    public static int gift = 0;
    public static int SelectStickerPosition;
    public static int image_not_found = -1;
    public static boolean end_slide_selected_or_not = false;
    public static int current_album_id = 0;
    public static int itemnum;
    public static boolean resume_flag = false;
    public static String sort_value = "whats_new";
    public static Bitmap bitmap_testing;
    public static boolean isPreviewActivity = false;
    public static List<filter> checked_arraylist = new ArrayList<>();
    public static float view_x;
    public static float view_y;
    public static String brand_name;
    public static int slider_url;
    public static Bitmap case_fancy_image;
    public static List<Offer> response_offer = new ArrayList<>();
    public static List<FrameDetail> frame_alldetails = new ArrayList<>();
    public static String model_svg;
    public static List<Currency> currency_details = new ArrayList<>();
    public static String countryCodeValue;
    public static String description;
    public static String product_name;
    public static List<mug_image_response_data> mug_image_array = new ArrayList<>();
    public static String symbol;
    public static Integer currency_id;
    public static List<com.mobile.cover.photo.editor.back.maker.model.Country> country_mobile_code = new ArrayList<>();
    public static boolean click_looking_for_other;
    public static Integer isinternational;
    public static List<region_model_data> region_search_array = new ArrayList<>();
    public static int select_position;
    public static int case_fancy_image_width;
    public static int case_fancy_image_heigth;
    public static List<Offer> offer_list = new ArrayList<>();
    public static boolean offer_display;
    public static boolean login_back = false;
    public static String discount_amount;
    public static List<Sticker> sqlist_multiple_sticker = new ArrayList<>();
    public static List<String> maincategoryname = new ArrayList<>();
    public static List<String> State = new ArrayList<>();
    public static List<frame_bitmap_model> frame_bitmap = new ArrayList<>();
    public static List<com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.Country> Country_state_city = new ArrayList<com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.Country>();
    public static int forgetpassword = 0;
    public static String cat_id = "0";
    public static String BASE_URL = "https://printphoto.in/";
    public static String key_msk_imge = "key_msk_imge";
    public static String key_normal_image = "key_normal_image";
    public static String key_normal_image_new = "key_normal_image_new";
    public static String Model_file_path = BASE_URL + "Photo_case/public/modal_image/";
    public static String Mask_file_path = BASE_URL + "Photo_case/public/mask_image/";
    public static List<DrawableSticker> drawables_sticker = new ArrayList<>();
    public static ArrayList<String> lst_album_image = new ArrayList<>();
    public static List<AllChild> subDataArrayList = new ArrayList<>();
    public static ArrayList<getdefault_images> subDataArrayList_category = new ArrayList<>();
    public static ArrayList<model_details_data> subDataArrayList_category_multiple_category = new ArrayList<>();
    public static ArrayList<request_final_brand> request_brand = new ArrayList<>();
    // public static List<model_details_data> subDataModelDetailsArrayList = new ArrayList<>();
    public static List<get_images> images;
    public static HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();
    public static String key_reg_suc = "key_reg_suc";
    public static String key_ = "key_";
    public static String name = "name";
    public static List<CartItem> CartItem_data = new ArrayList<>();
    public static boolean iscart = false;
    public static boolean isorder = false;
    public static boolean display_isorder = false;
    public static boolean upload = false;
    public static String address = "Key_addss";
    public static String order_id = "order_id";
    public static String applyoffer = "offer";
    public static String promocode = "promocode";
    public static String offerId = null;
    public static String sellerpromoId = null;
    public static String model_name = "model_name";
    public static int model_id;
    public static String user_id = "user_id";
    public static String quantity = "quantity";
    public static String total_amount = "total_amount";
    public static String paid_amount = "paid_amount";
    public static String width = "width";
    public static String height = "height";
    public static int size;
    public static int position;
    public static String categoryname;
    public static Bitmap resultbitmap;
    public static String imagetype;
    public static Bitmap final_result_bitmap;
    public static int requestfragment = 0;
    public static int addressposition;
    public static String test_image;
    public static Bitmap test_bitmap;
    public static int category_id;
    public static int shipping;
    public static int case_category_id;
    public static String case_id;
    public static boolean edit_image;
    public static String item_edit_id;
    public static Float displayheight;
    public static Float display_width;
    public static boolean click = false;
    public static boolean update;
    public static String imageuri = "";
    public static String bulksms;
    public static boolean radiobutton;
    public static String address_value = "";
    public static List<new_getdefault_images> stickerlist = new ArrayList<>();
    public static List<background_response_data> backgroundimage_list = new ArrayList<>();
    public static int back_print;
    public static Bitmap mask_bitmap;
    public static List<save_address_response_data> saved_address_list = new ArrayList<>();
    public static List<save_address_response_data> search_saved_address_list = new ArrayList<>();
    public static boolean isfromplaceorder;
    public static Integer address_id;
    public static String deliver_name;
    public static ArrayList<AllChild> disp_subdata_arraylist = new ArrayList<>();
    public static List<model_details_data> sub_category_data_array_list = new ArrayList<>();
    public static List<getdefault_images> default_image_sqlist = new ArrayList<>();
    public static String edited_image;
    public static int islogo;
    public static int frame_id;
    public static OrderDetails_response orderdetails;
    public static List<String> enabledpayment_gateway = new ArrayList<>();
    public static String ccavenue_order_id;
    public static Integer iskeychainexsist;
    public static boolean feedbackactivity = false;
    public static List<SellerOrder> order_item_list = new ArrayList<>();
    public static boolean order_cancel = false;
    public static int mask = 0;
    public static List<AllChild> main_category_data = new ArrayList<>();
    public static List<AllChild> sub_new_main_category_data = new ArrayList<>();
    public static List<AllChild> dynamic_sub_category_list = new ArrayList<>();
    public static List<mall_AllChild> mall_dynamic_sub_category_list = new ArrayList<>();
    public static List<Integer> click_positions = new ArrayList<>();
    public static String header_name;
    public static Integer main_category_id = 0;
    public static List<AllChild> search_dynamic_sub_category_list = new ArrayList<>();
    public static String complain_ticket_url;
    public static List<model_details_data> subDataModelDetailsArrayList_search = new ArrayList<>();
    public static boolean upload_success = false;
    public static List<mall_AllChild> mall_main_category_data = new ArrayList<>();
    public static List<mall_main_sub_data> subresponse_data = new ArrayList<>();
    public static List<ProductDetails1> product_info_details = new ArrayList<>();
    public static List<ProductImage> product_images_list = new ArrayList<>();
    public static Boolean in_wishlist;
    public static List<mall_main_sub_data> searched_product = new ArrayList<>();
    public static List<mall_main_sub_data> wish_list = new ArrayList<>();
    public static List<AvailableFilter> available_filters = new ArrayList<>();
    public static String max_price = "";
    public static String min_price = "";
    public static List<mall_main_sub_data> filtered_response = new ArrayList<>();
    public static String base_filter_url = "";
    public static List<bulk_category_model> bulk_category_list = new ArrayList<>();
    public static com.mobile.cover.photo.editor.back.maker.model.mall_seller_details mall_seller_details;
    public static String category_header_name;
    public static String detail_header_name;
    public static Integer notification_category_id = 0;
    public static String notification_category_name = "";
    public static List<mug_image_response_data> shipper_bottle_image_array = new ArrayList<>();
    public static List<String> variant_list_value = new ArrayList<>();
    public static List<String> variant_list_value_women = new ArrayList<>();
    public static List<ProductDetail> product_old_details = new ArrayList<>();
    public static Bitmap bg_drawable;
    public static model_details_data locket_model_data;
    public static boolean isregistration = false;
    public static boolean isRegistrationSuccess = false;
    public static String tempPassword = null;
    public static boolean setselection = false;
    public static int iscart_frommall = 0;
    public static String mall_enable = "mall_enable";
    public static String international_sms_priority = "0";
    public static String indian_sms_priority = "0";
    public static String firebaseToken = "";
    public static CityState CityState;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static Boolean RestartApp(Activity activity) {
        Log.e("CHECKDATA", "RestartAppForOnlyStorage: 4" );
        if (!Share.checkAndRequestPermissions(activity, 1)) {
            Intent i = activity.getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(i);
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkAndRequestPermissions(Activity act, int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(act, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static Boolean RestartAppForOnlyStorage(Activity activity) {
        Log.e("CHECKDATA", "RestartAppForOnlyStorage: 1" );
        if (!Share.checkAndRequestPermissionsforstorage(activity, 1)) {
            resume_flag = true;
            Intent i = activity.getBaseContext().getPackageManager().getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(i);
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkAndRequestPermissionsforstorage(Activity act, int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(act, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static Dialog showProgress(Context mContext, String text) {
        Dialog mDialog = new Dialog(mContext, R.style.MyTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View layout = mInflater.inflate(R.layout.dialog_progress, null);
        mDialog.setContentView(layout);

        TextView mTextView = layout.findViewById(R.id.text);
        if (text.equals(""))
            mTextView.setVisibility(View.GONE);
        else
            mTextView.setText(text);

        mDialog.setCancelable(false);
        // aiImage.post(new Starter(activityIndicator));
        return mDialog;
    }


    public static boolean checkInternetConenction(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            Toast.makeText(context, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(context, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return false;
        }
    }
}
