package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.MultipleVariant;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.main_response;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mall_main_category_response_click_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Contactus_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FinalScreenActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.LogInActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Mall_wishlist;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.mall_description;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.mall_filter_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.mall_seller_activity;
import com.mobile.cover.photo.editor.back.maker.adapter.Display_slide_CustomPagerAdapter;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.mall_detail_product_detail_adapter;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.product_detail_adapter;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.product_variant_adapter;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.mainapplication;
import com.mobile.cover.photo.editor.back.maker.model.Cart;
import com.mobile.cover.photo.editor.back.maker.model.RegData;
import com.mobile.cover.photo.editor.back.maker.model.RegResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.mobile.cover.photo.editor.back.maker.BuildConfig.DEBUG;
import static com.mobile.cover.photo.editor.back.maker.Commen.Share.upload;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.mall_category_activity.sort_value;

public class mall_detail_activity extends AppCompatActivity implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL = 1500;
    private static final int REQ_SIGNIN = 110;
    public static Activity mActivity;
    ImageView iv_wish_list, iv_un_wish_list, id_back, iv_share;
    LinearLayout ll_seller, ll_enter_pincode, ll_pincode_main, ll_pincode_status;
    ImageView iv_contact_us, iv_zoom;
    RecyclerView rv_pro_details;
    product_detail_adapter product_detail_adapter;
    mall_detail_product_detail_adapter mall_detail_product_detail_adapter;
    EditText ed_pincode_search;
    TextView tv_pro_name, tv_original_price, tv_dummy_price, tv_discount, tv_pincode_status, tv_seller_name, title, tv_product_description, tv_you_save, tv_check_pincode_text;
    Button btn_search, btn_change, btn_add_to_cart;
    ProgressDialog pd, progress;
    FirebaseAnalytics firebaseAnalytics;
    AppEventsLogger logger;
    ArrayList<Uri> image_list_uri = new ArrayList<>();
    LinearLayout ll_bottom_bar;
    int variant_id = 0;
    private ViewPager viewPager;
    private Display_slide_CustomPagerAdapter customPagerAdapter;
    private long mLastClickTime;

    private int categoryId = 0;
    private List<MultipleVariant> multiVariants = new ArrayList<>();

    private boolean isUpdate = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_detail_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mActivity = mall_detail_activity.this;
        findviews();
        listener();

        firebaseAnalytics = FirebaseAnalytics.getInstance(mActivity);
        logger = AppEventsLogger.newLogger(mActivity);
        Bundle params = new Bundle();
        params.putInt("mall_category_product_open", 1);
        firebaseAnalytics.logEvent("mall_category_product_open", params);


        Share.description = getIntent().getStringExtra("descrip");
        Share.product_name = getIntent().getStringExtra("pro_name");
    }

    private void listener() {
        iv_wish_list.setOnClickListener(this);
        iv_zoom.setOnClickListener(this);
        iv_un_wish_list.setOnClickListener(this);
        ll_seller.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_change.setOnClickListener(this);
        iv_contact_us.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        id_back.setOnClickListener(this);
        btn_add_to_cart.setOnClickListener(this);
    }

    private void findviews() {
        ll_bottom_bar = findViewById(R.id.ll_bottom_bar);
        iv_wish_list = findViewById(R.id.iv_wish_list);
        iv_zoom = findViewById(R.id.iv_zoom);

        iv_un_wish_list = findViewById(R.id.iv_un_wish_list);
        iv_share = findViewById(R.id.iv_share);
        id_back = findViewById(R.id.id_back);
        tv_seller_name = findViewById(R.id.tv_seller_name);
        tv_you_save = findViewById(R.id.tv_you_save);
        tv_check_pincode_text = findViewById(R.id.tv_check_pincode_text);
        tv_you_save.setText(getIntent().getStringExtra("you_save"));
        tv_product_description = findViewById(R.id.tv_product_description);
        tv_product_description.setText(getIntent().getStringExtra("descrip"));
        tv_product_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mall_detail_activity.this, mall_description.class);
                intent.putExtra("descrip", getIntent().getStringExtra("descrip"));
                intent.putExtra("pro_name", getIntent().getStringExtra("pro_name"));
                startActivity(intent);
            }
        });
        tv_seller_name.setText(Share.mall_seller_details.getName());
        title = findViewById(R.id.title);


        categoryId = Integer.parseInt(getIntent().getStringExtra("category_id"));
        title.setText(Share.detail_header_name);


        iv_contact_us = findViewById(R.id.iv_contact_us);
        Log.e("WISHLIST", "findviews: " + Share.in_wishlist);
        if (Share.in_wishlist) {
            iv_wish_list.setVisibility(View.VISIBLE);
            iv_un_wish_list.setVisibility(View.GONE);
        } else {
            iv_wish_list.setVisibility(View.GONE);
            iv_un_wish_list.setVisibility(View.VISIBLE);
        }
        ll_seller = findViewById(R.id.ll_seller);
        ll_enter_pincode = findViewById(R.id.ll_enter_pincode);
        ll_pincode_main = findViewById(R.id.ll_pincode_main);
        if (SharedPrefs.getString(mall_detail_activity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
            ll_pincode_main.setVisibility(View.VISIBLE);
            tv_check_pincode_text.setVisibility(View.VISIBLE);
        } else {
            ll_pincode_main.setVisibility(View.GONE);
            tv_check_pincode_text.setVisibility(View.GONE);
        }
        ll_pincode_status = findViewById(R.id.ll_pincode_status);

        tv_pro_name = findViewById(R.id.tv_pro_name);
        tv_original_price = findViewById(R.id.tv_original_price);
        tv_dummy_price = findViewById(R.id.tv_dummy_price);
        tv_discount = findViewById(R.id.tv_discount);
        tv_pincode_status = findViewById(R.id.tv_pincode_status);


        // ToDo: Changes mae by Jignesh Patel
        String proId = getIntent().getStringExtra("pro_id");
        String proName = getIntent().getStringExtra("pro_name");
        String proPrice = getIntent().getStringExtra("pro_price");
        FBEventsKt.logViewedContentEvent(this, proName, proId, proPrice);


        tv_pro_name.setText(proName);
        tv_original_price.setText(Share.symbol + proPrice);
        if (getIntent().getStringExtra("pro_dummy_price").equalsIgnoreCase("")) {
            tv_dummy_price.setVisibility(View.GONE);
            tv_discount.setVisibility(View.GONE);
        } else {
            tv_dummy_price.setVisibility(View.VISIBLE);
            tv_discount.setVisibility(View.VISIBLE);
        }
        tv_dummy_price.setText(Share.symbol + getIntent().getStringExtra("pro_dummy_price"));
        tv_discount.setText(getIntent().getStringExtra("pro_discount"));

        btn_search = findViewById(R.id.btn_search);
        btn_change = findViewById(R.id.btn_change);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);

        ed_pincode_search = findViewById(R.id.ed_pincode_search);

        rv_pro_details = findViewById(R.id.rv_pro_details);
        mall_detail_product_detail_adapter = new mall_detail_product_detail_adapter(mall_detail_activity.this, Share.product_old_details);
        rv_pro_details.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_pro_details.setAdapter(mall_detail_product_detail_adapter);

        viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
        customPagerAdapter = new Display_slide_CustomPagerAdapter(this, Share.product_images_list);
        viewPager.setAdapter(customPagerAdapter);
        viewPager.setCurrentItem(0);

//        banner_slider.getCurrentSlider().setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//            @Override
//            public void onSliderClick(BaseSliderView slider) {
//                Log.e("CLICKED", "onSliderClick: =====>"+slider.getUrl());
//            }
//        });
//
//        rl_click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("CLICKED", "onSliderClick: "+banner_slider.getCurrentSlider().getUrl());
//
//            }
//        });

    }

    private void wishlist(final String pro_id) {
        isUpdate = true;

        pd = ProgressDialog.show(mall_detail_activity.this, "", getString(R.string.loading), true, false);

        APIService apiService = new MainApiClient(mall_detail_activity.this).getApiInterface();
        Log.e("WISH", "wishlist: " + pro_id);
        Log.e("WISH", "wishlist: " + SharedPrefs.getString(mall_detail_activity.this, Share.key_ + RegReq.id));
        Call<main_response> call = apiService.check_uncheck_wishlist(SharedPrefs.getString(mall_detail_activity.this, Share.key_ + RegReq.id), pro_id, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<main_response>() {
            @Override
            public void onResponse(Call<main_response> call, Response<main_response> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    Log.e("PRO_ID", "onResponse: " + pro_id);
                    Log.e("PRO_ID", "onResponse: " + response.body().getResponseCode());
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        iv_wish_list.setVisibility(View.VISIBLE);
                        iv_un_wish_list.setVisibility(View.GONE);
                        for (int i = 0; i < Share.subresponse_data.size(); i++) {
                            if (getIntent().getStringExtra("pro_id").equalsIgnoreCase(String.valueOf(Share.subresponse_data.get(i).getId()))) {
                                Share.subresponse_data.get(i).setIn_wishlist(true);
                            }
                        }
                    } else {
                        iv_wish_list.setVisibility(View.GONE);
                        iv_un_wish_list.setVisibility(View.VISIBLE);
                        for (int i = 0; i < Share.subresponse_data.size(); i++) {
                            if (getIntent().getStringExtra("pro_id").equalsIgnoreCase(String.valueOf(Share.subresponse_data.get(i).getId()))) {
                                Share.subresponse_data.get(i).setIn_wishlist(false);
                            }
                        }
                    }
                } else {
                    Toast.makeText(mall_detail_activity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<main_response> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mall_detail_activity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            wishlist(pro_id);
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(mall_detail_activity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            wishlist(pro_id);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    private void check_pincode_service_availablity(final Integer pincode) {


        pd = ProgressDialog.show(mall_detail_activity.this, "", getString(R.string.loading), true, false);

        APIService apiService = new MainApiClient(mall_detail_activity.this).getApiInterface();
        Call<main_response> call = apiService.check_pincode_service(pincode, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<main_response>() {
            @Override
            public void onResponse(Call<main_response> call, Response<main_response> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        if (response.body().getData().getPrepaidServiceable()) {
                            ll_enter_pincode.setVisibility(View.GONE);
                            ed_pincode_search.setText("");
                            ll_pincode_status.setVisibility(View.VISIBLE);
                            tv_pincode_status.setText(getString(R.string.delivery_available));
                            tv_pincode_status.setTextColor(getResources().getColor(R.color.color_green));
                        } else {
                            ll_enter_pincode.setVisibility(View.GONE);
                            ed_pincode_search.setText("");
                            ll_pincode_status.setVisibility(View.VISIBLE);
                            tv_pincode_status.setText(getString(R.string.delivery_not_available));
                            tv_pincode_status.setTextColor(getResources().getColor(R.color.color_red));
                            Toast.makeText(mall_detail_activity.this, response.body().getData().getPrepaidMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mall_detail_activity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mall_detail_activity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<main_response> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mall_detail_activity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            check_pincode_service_availablity(pincode);
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(mall_detail_activity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            check_pincode_service_availablity(pincode);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_wish_list) {
            if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mall_detail_activity.this);
                alertDialog.setTitle(getString(R.string.log_in));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.need_login));
                alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent intent = new Intent(mall_detail_activity.this, LogInActivity.class);
                        startActivity(intent);

                    }
                });
                alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialog.create().show();

                return;
            }
            Log.e("WISH", "onClick: " + getIntent().getStringExtra("pro_id"));
            wishlist(getIntent().getStringExtra("pro_id"));
        } else if (id == R.id.iv_un_wish_list) {
            if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mall_detail_activity.this);
                alertDialog.setTitle(getString(R.string.log_in));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.need_login));
                alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent intent = new Intent(mall_detail_activity.this, LogInActivity.class);
                        startActivityForResult(intent, REQ_SIGNIN);

                    }
                });
                alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialog.create().show();

                return;
            }
            wishlist(getIntent().getStringExtra("pro_id"));
        } else if (id == R.id.ll_seller) {
            Intent intent = new Intent(mall_detail_activity.this, mall_seller_activity.class);
            startActivity(intent);
        } else if (id == R.id.iv_zoom) {
            Intent intent1 = new Intent(mActivity, FinalScreenActivity.class);
            startActivity(intent1);
        } else if (id == R.id.btn_search) {
            if (ed_pincode_search.getText().toString().trim().length() < 6) {
                Toast.makeText(this, getString(R.string.pls_enter_six_pin), Toast.LENGTH_SHORT).show();
            } else {
                if (ed_pincode_search.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(this, getString(R.string.pls_enter_pin), Toast.LENGTH_SHORT).show();
                } else {
                    hideKeyboard(mall_detail_activity.this);
                    check_pincode_service_availablity(Integer.parseInt(ed_pincode_search.getText().toString().trim()));
                }
            }
        } else if (id == R.id.btn_change) {
            ll_enter_pincode.setVisibility(View.VISIBLE);
            ll_pincode_status.setVisibility(View.GONE);
            ed_pincode_search.setText("");
        } else if (id == R.id.iv_share) {
            long currentClickTime = SystemClock.uptimeMillis();
            long elapsedTime = currentClickTime - mLastClickTime;
            mLastClickTime = currentClickTime;
            if (elapsedTime <= MIN_CLICK_INTERVAL)
                return;
            int i = 0;
            image_list_uri.clear();
            generate_uri(i);
        } else if (id == R.id.iv_contact_us) {//                if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mall_detail_activity.this);
//                    alertDialog.setTitle(getString(R.string.log_in));
//                    alertDialog.setCancelable(false);
//                    alertDialog.setMessage(getString(R.string.need_login));
//                    alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            dialog.dismiss();
//                            Intent intent = new Intent(mall_detail_activity.this, LogInActivity.class);
//                            startActivity(intent);
//
//                        }
//                    });
//                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//
//                        }
//                    });
//
//                    alertDialog.create().show();
//                } else {
//                    if (Mall_wishlist.activity != null) {
//                        Mall_wishlist.activity.finish();
//                    }
//                    Intent wish_list_intent = new Intent(mall_detail_activity.this, Mall_wishlist.class);
//                    startActivity(wish_list_intent);
//                }

            Intent intent_contact = new Intent(mActivity, Contactus_activity.class);
            startActivity(intent_contact);
        } else if (id == R.id.id_back) {
            onBackPressed();
        } else if (id == R.id.btn_add_to_cart) {
            final Dialog dialog = new Dialog(mall_detail_activity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_variant);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
            dialog.show();

            RecyclerView recyclerView = dialog.findViewById(R.id.rv_variant_name);
            RecyclerView rv_variant_women = dialog.findViewById(R.id.rv_variant_women);
            TextView tv_title = dialog.findViewById(R.id.tv_title);
            ImageView iv_cancel = dialog.findViewById(R.id.iv_cancel);
            Button btn_apply = dialog.findViewById(R.id.btn_apply);
            Button btn_dismiss = dialog.findViewById(R.id.btn_dismiss);
            TextView tv_for_men = dialog.findViewById(R.id.tv_for_men);
            TextView tv_for_women = dialog.findViewById(R.id.tv_for_women);

            tv_title.setText("Variants");


            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mall_detail_activity.this);
            recyclerView.setLayoutManager(layoutManager);

            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(mall_detail_activity.this);
            rv_variant_women.setLayoutManager(layoutManager1);


            product_variant_adapter product_variant_adapter = new product_variant_adapter(mall_detail_activity.this, Share.product_info_details, false);
            recyclerView.setAdapter(product_variant_adapter);


            if (categoryId == DataHelperKt.getProductIdType(mActivity).getCoupleTshirt()) {
                rv_variant_women.setVisibility(View.VISIBLE);
                tv_for_men.setVisibility(View.VISIBLE);
                tv_for_women.setVisibility(View.VISIBLE);
                product_variant_adapter product_variant_adapter_women = new product_variant_adapter(mall_detail_activity.this, Share.product_info_details, true);
                rv_variant_women.setAdapter(product_variant_adapter_women);
            } else {
                rv_variant_women.setVisibility(View.GONE);
                tv_for_men.setVisibility(View.GONE);
                tv_for_women.setVisibility(View.GONE);
            }


            btn_dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();


                }
            });

            btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // For Men
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < Share.variant_list_value.size(); j++) {
                        stringBuilder.append("&Filter_" + Share.variant_list_value.get(j));
                        Log.e("DISPLAY_VALUE", "onClick: " + Share.variant_list_value.get(j));
                    }
                    Log.e("STRING_BUILD", "onClick_GENERAL: " + stringBuilder);


                    StringBuilder stringBuilder2 = new StringBuilder();
                    if (categoryId == DataHelperKt.getProductIdType(mActivity).getCoupleTshirt()) {
                        // For Women

                        for (int j = 0; j < Share.variant_list_value_women.size(); j++) {
                            String filter = "&Women_" + Share.variant_list_value_women.get(j);
                            //   filter = filter.replace("=", "_Women=");
                            stringBuilder2.append(filter);
                            Log.e("DISPLAY_VALUE", "onClick: " + Share.variant_list_value_women.get(j));
                        }
                        Log.e("STRING_BUILD", "onClick_WOMENL: " + stringBuilder2.toString());
                    }

                    dialog.dismiss();

                    String filterData = stringBuilder.toString();

                    if (categoryId == DataHelperKt.getProductIdType(mActivity).getCoupleTshirt()) {
                        filterData = filterData + stringBuilder2.toString();
                    }
                    filter(filterData);

                }
            });

            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }


    private void filter(final String filter) {
        pd = ProgressDialog.show(this, "", this.getString(R.string.loading), true, false);
        int user_id;
        if (!SharedPrefs.getBoolean(mall_detail_activity.this, Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(mall_detail_activity.this, Share.key_ + RegReq.id));
        }

        String url = UtilsKt.getBaseUrl(mall_detail_activity.this) + "mall_products?category_id=" + getIntent().getStringExtra("category_id") +
                "&product_id=" + getIntent().getStringExtra("product_id") + "&user_id=" + user_id +
                "&country_code=" + SharedPrefs.getString(mall_detail_activity.this, SharedPrefs.country_code) +
                "&variants=1" + "&ln=" + Locale.getDefault().getLanguage() + filter.toString().replace(" ", "%20");
        if (DEBUG) {
            Log.e("URL", "filter: " + url);
        }
        final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        Gson gson = new Gson();
                        final mall_main_category_response_click_data mall_main_category_response_click_data = gson.fromJson(response, mall_main_category_response_click_data.class);
                        if (mall_main_category_response_click_data.getResponseCode().equalsIgnoreCase("1")) {
                            final Dialog dialog = new Dialog(mall_detail_activity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_display_cart);
                            dialog.setCancelable(false);
                            Window window = dialog.getWindow();
                            window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
                            dialog.show();
                            TextView tv_title = dialog.findViewById(R.id.tv_title);
                            TextView tv_pro_name = dialog.findViewById(R.id.tv_pro_name);
                            TextView tv_original_price = dialog.findViewById(R.id.tv_original_price);
                            TextView tv_dummy_price = dialog.findViewById(R.id.tv_dummy_price);
                            TextView tv_discount = dialog.findViewById(R.id.tv_discount);
                            TextView tv_you_save = dialog.findViewById(R.id.tv_you_save);
                            ImageView iv_cancel = dialog.findViewById(R.id.iv_cancel);
                            TextView tv_for_men = dialog.findViewById(R.id.tv_for_men);
                            TextView tv_for_women = dialog.findViewById(R.id.tv_for_women);
                            iv_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            LinearLayout ll_my_photo = dialog.findViewById(R.id.ll_my_photo);
                            ViewPager viewPager = dialog.findViewById(R.id.viewpager);
                            RecyclerView rv_pro_details = dialog.findViewById(R.id.rv_pro_details);
                            RecyclerView rv_pro_details_women = dialog.findViewById(R.id.rv_pro_details_women);

                            Button btn_add_to_cart = dialog.findViewById(R.id.btn_add_to_cart);

                            btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    if (!SharedPrefs.getBoolean(mActivity, Share.key_reg_suc)) {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mall_detail_activity.this);
                                        alertDialog.setTitle(getString(R.string.log_in));
                                        alertDialog.setCancelable(false);
                                        alertDialog.setMessage(getString(R.string.need_login));
                                        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                Intent intent = new Intent(mall_detail_activity.this, LogInActivity.class);
                                                startActivity(intent);

                                            }
                                        });
                                        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        });

                                        alertDialog.create().show();

                                        return;
                                    }
                                    Log.e("CHECKCART", "addtocart: 9--" );
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mall_detail_activity.this);
                                    alertDialog.setCancelable(false);
                                    alertDialog.setMessage(getString(R.string.add_to_cart));
                                    alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            try {
                                                variant_id = mall_main_category_response_click_data.getData().get(0).getVariants().getId();
                                                Share.iscart_frommall = 1;

                                                if (categoryId == DataHelperKt.getProductIdType(mActivity).getCoupleTshirt()) {
                                                    multiVariants = mall_main_category_response_click_data.getData().get(0).getMultiple_variants();
                                                }

                                                new crateReq().execute();

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.d("EXP", "sendData: " + e.getMessage());
                                            }
                                        }
                                    });
                                    alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    });

                                    alertDialog.create().show();
                                }
                            });

                            product_detail_adapter = new product_detail_adapter(mActivity, Share.variant_list_value);
                            rv_pro_details.setLayoutManager(new LinearLayoutManager(mActivity));
                            rv_pro_details.setAdapter(product_detail_adapter);


                            if (categoryId == DataHelperKt.getProductIdType(mActivity).getCoupleTshirt()) {
                                rv_pro_details_women.setVisibility(View.VISIBLE);
                                tv_for_men.setVisibility(View.VISIBLE);
                                tv_for_women.setVisibility(View.VISIBLE);


                                product_detail_adapter product_detail_adapter2 = new product_detail_adapter(mActivity, Share.variant_list_value_women);
                                rv_pro_details_women.setLayoutManager(new LinearLayoutManager(mActivity));
                                rv_pro_details_women.setAdapter(product_detail_adapter2);

                            } else {
                                rv_pro_details_women.setVisibility(View.GONE);
                                tv_for_men.setVisibility(View.GONE);
                                tv_for_women.setVisibility(View.GONE);
                            }


                            TabLayout tabLayout = dialog.findViewById(R.id.tabDots);
                            tabLayout.setupWithViewPager(viewPager, true);
                            customPagerAdapter = new Display_slide_CustomPagerAdapter(mActivity, mall_main_category_response_click_data.getData().get(0).getProductImages());
                            viewPager.setAdapter(customPagerAdapter);
                            viewPager.setCurrentItem(0);

                            tv_title.setText(mall_main_category_response_click_data.getData().get(0).getGeneralCategory().getName());
                            tv_pro_name.setText(mall_main_category_response_click_data.getData().get(0).getName());
                            tv_original_price.setText(Share.symbol + mall_main_category_response_click_data.getData().get(0).getVariants().getPrice());
                            tv_dummy_price.setText(Share.symbol + mall_main_category_response_click_data.getData().get(0).getVariants().getDummyPrice());
                            tv_discount.setText(mall_main_category_response_click_data.getData().get(0).getDiscount());
                            tv_you_save.setText(mall_main_category_response_click_data.getData().get(0).getYou_save());


                        } else {
                            Toast.makeText(mall_detail_activity.this, mall_main_category_response_click_data.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError t) {
                        if (t != null) {
                            pd.dismiss();
                            Log.e("Error", "Message=====>" + t.getLocalizedMessage());
                            Log.e("Error", "Message=====>" + t.getMessage());
                            if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                                AlertDialog alertDialog = new AlertDialog.Builder(mall_detail_activity.this).create();
                                alertDialog.setTitle(getString(R.string.time_out));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.connect_time_out));
                                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        filter(filter);
                                    }
                                });
                                alertDialog.show();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(mall_detail_activity.this).create();
                                alertDialog.setTitle(getString(R.string.internet_connection));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.slow_connect));
                                alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        filter(filter);
                                    }
                                });
                                alertDialog.show();
                            }
                        }
                    }
                }
        );
        mainapplication.getsInstance().getRequestQueue().add(stringRequest).setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    private void generate_uri(final int i) {
        Log.e("POSSSSSS", "generate_uri: " + i);


        if (checkAndRequestPermissionsStorage(2)) {
            if (i == Share.product_images_list.size()) {
//            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
//            shareIntent.setType("text/html");
//            shareIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "sdajfjasdgfjagsdfjgasdjkfgadfsadfsg" });
//            shareIntent.putExtra(Intent.EXTRA_STREAM, image_list_uri);
//            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Print Photo");
//            startActivity(shareIntent);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*"); /* This example is sharing jpeg images. */
                intent.putExtra(Intent.EXTRA_STREAM, image_list_uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, "getApplicationContext().getString(R.string.app_name)");
                String sAux = "Buy this amazing " + getIntent().getStringExtra("pro_name") + " from" + "getString(R.string.app_name).toLowerCase()" + " app from play store\n\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n\n";
                intent.putExtra(Intent.EXTRA_TEXT, sAux + Share.description);
                startActivity(Intent.createChooser(intent, "Share notes.."));
            } else {
                Glide.with(mActivity).asBitmap().load(Share.product_images_list.get(i).getThumbImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        image_list_uri.add(Uri.fromFile(getFile("" + System.currentTimeMillis(), resource, ".png")));
                        generate_uri(i + 1);
                    }
                });
            }
        }
    }

    private boolean checkAndRequestPermissionsStorage(int code) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, code);
                return false;
            } else {
                return true;
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, code);
                return false;
            } else {
                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, code);
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions.length == 0) {
            return;
        }
        boolean allPermissionsGranted = true;
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
        }
        if (!allPermissionsGranted) {
            boolean somePermissionsForeverDenied = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    //denied
                    Log.e("denied", permission);
                    if (requestCode == 1) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        }else {
                            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
                        }

                    }

                } else {

                    if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                        //allowed
                        Log.e("allowed", permission);
//                        if (requestCode==2){
//                            Log.e("GRANTED", "checkAndRequestPermissionsStorage:=======> " );
//                        }

                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                    }
                }
            }
            if (somePermissionsForeverDenied) {

                if (requestCode == 1) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(getString(R.string.permission_required))
                            .setMessage(getString(R.string.permission_sentence))
                            .setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }
                if (requestCode == 2) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(getString(R.string.permission_required))
                            .setMessage(getString(R.string.permission_sentence_storage))
                            .setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }

            }
        } else {
            switch (requestCode) {
                case 1:
                    break;
                default:
                    break;
            }
        }
    }

    public File getFile(String filename, Bitmap yourbitmap, String formate) {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), filename + formate);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        Bitmap bitmap = yourbitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (formate.contains("jpg")) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);

        } else {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);

        }
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (Share.isRegistrationSuccess) {

            Share.isRegistrationSuccess = false;

            String mobile = SharedPrefs.getString(mActivity, Share.key_ + RegReq.mobile_1);
            String email = SharedPrefs.getString(mActivity, Share.key_ + RegReq.email);

            String cred;
            if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                cred = mobile;
            } else {
                cred = email;
                for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                    if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                        if (SharedPrefs.getString(mActivity, SharedPrefs.country_code).equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
                            cred = mobile;
                        }
                    }
                }
            }

            if (Share.tempPassword != null) {
                signin(cred, Share.tempPassword);
                Share.tempPassword = null;
            }

        }

    }

    private class crateReq extends AsyncTask<Void, Void, Void> {

        MultipartBody.Builder builder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(mall_detail_activity.this, "", getString(R.string.loading), true, false);
            builder = new MultipartBody.Builder();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           /* if (progress != null && progress.isShowing())
                progress.dismiss();*/


            if (builder != null) {
                MultipartBody multipartBody = builder.build();
                APIService apiService = new MainApiClient(mall_detail_activity.this).getApiInterface();
                Call<Cart> cartCall = apiService.sendCart(multipartBody);
                cartCall.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {

                        if (progress != null && progress.isShowing())
                            progress.dismiss();

                        if (response != null) {
                            if (response.body().getcart_data().getStatus() == 1) {
                                upload = true;
                                if (mall_filter_activity.mactivity != null) {
                                    mall_filter_activity.mactivity.finish();
                                }
                                if (mall_category_activity.mActivity != null) {
                                    mall_category_activity.mActivity.finish();
                                }
                                if (MallSearchActivity.activity != null) {
                                    MallSearchActivity.activity.finish();
                                }
                                if (mall_seller_activity.activity != null) {
                                    mall_seller_activity.activity.finish();
                                }
                                if (Mall_wishlist.activity != null) {
                                    Mall_wishlist.activity.finish();
                                }
                                Share.iscart_frommall = 1;

                                sort_value = "whats_new";
                                Share.sort_value = "whats_new";
                                finish();
                                Log.e("SELECTED", "onResponse: " + HomeMainActivity.selected);
                            } else {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mall_detail_activity.this);
                                alertDialog.setTitle(getString(R.string.upload_fail));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(response.body().getcart_data().getMessage());
                                alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });

                                alertDialog.create().show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Log.d("response", "Failed==>" + t.toString());
                        if (progress != null && progress.isShowing())
                            progress.dismiss();

                        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(mall_detail_activity.this).create();
                            alertDialog.setTitle(getString(R.string.time_out));
                            alertDialog.setMessage(getString(R.string.connect_time_out));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    new crateReq().execute();
                                }
                            });
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(mall_detail_activity.this).create();
                            alertDialog.setTitle(getString(R.string.internet_connection));
                            alertDialog.setMessage(getString(R.string.slow_connect));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    new crateReq().execute();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                });
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("user_id", SharedPrefs.getString(mall_detail_activity.this, Share.key_ + RegReq.id));
            builder.addFormDataPart("quantity", "1");
            builder.addFormDataPart("model_id", "" + getIntent().getStringExtra("pro_id"));

            if (categoryId == DataHelperKt.getProductIdType(mActivity).getCoupleTshirt()) {
                builder.addFormDataPart("variant_id", "" + multiVariants.get(0).getId());
                builder.addFormDataPart("variant_id_women", "" + multiVariants.get(1).getId());
            } else {
                builder.addFormDataPart("variant_id", "" + variant_id);
            }


            builder.addFormDataPart("ln", Locale.getDefault().getLanguage());

            return null;
        }
    }


    private void get_wishlist() {


        int proId = Integer.parseInt(getIntent().getStringExtra("pro_id"));

        int user_id;
        if (!SharedPrefs.getBoolean(mall_detail_activity.this, Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(mall_detail_activity.this, Share.key_ + RegReq.id));
        }

        pd = ProgressDialog.show(mall_detail_activity.this, "", getString(R.string.loading), true, false);

        APIService apiService = new MainApiClient(mall_detail_activity.this).getApiInterface();
        Call<mall_main_category_response_click_data> call = apiService.wishlist(user_id, Share.countryCodeValue, "1", Locale.getDefault().getLanguage());

        call.enqueue(new Callback<mall_main_category_response_click_data>() {
            @Override
            public void onResponse(Call<mall_main_category_response_click_data> call, Response<mall_main_category_response_click_data> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        Share.isinternational = response.body().getIs_international();
                        Share.wish_list.clear();
                        Share.symbol = response.body().getCurrency_symbol();
                        Share.wish_list.addAll(response.body().getData());

                        for (int i = 0; i < Share.wish_list.size(); i++) {

                            if (Share.wish_list.get(i).getId() == proId && Share.wish_list.get(i).getIn_wishlist()) {
                                iv_wish_list.setVisibility(View.VISIBLE);
                                iv_un_wish_list.setVisibility(View.GONE);
                                Share.subresponse_data.get(i).setIn_wishlist(true);
                            } else {
                                Share.subresponse_data.get(i).setIn_wishlist(false);
                                iv_wish_list.setVisibility(View.GONE);
                                iv_un_wish_list.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<mall_main_category_response_click_data> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGNIN && resultCode == RESULT_OK) {
            get_wishlist();
        }
    }

    private void signin(String cred, String password) {
        String androidId = Share.firebaseToken;
        Log.e("androidId", "==>" + androidId);
        pd = ProgressDialog.show(mActivity, "", getString(R.string.loading), true, false);
        pd.show();


        APIService apiService = new MainApiClient(mActivity).getApiInterface();
        Call<RegResponse> regResponseCall = apiService.getRegResponseLogin(cred, password, androidId, "android", TimeZone.getDefault().getID(), Locale.getDefault().getLanguage());

        regResponseCall.enqueue(new Callback<RegResponse>() {
            @Override
            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }

                if (response.body() != null) {
                    Log.e("androidId", "==>" + response.body().getResponseCode());


                    pd.dismiss();
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        SharedPrefs.save(mActivity, SharedPrefs.CART_COUNT, response.body().getCart_count());
                        HomeMainActivity.tv_nudge_cart_count.setText("" + SharedPrefs.getInt(mActivity, SharedPrefs.CART_COUNT));
                        if (response.body().getData() == null) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
                            alertDialog.setTitle(getString(R.string.log_in));
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(response.body().getResponseMessage());
                            alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });

                            alertDialog.create().show();
                        } else {
                            SharedPrefs.savePref(mActivity, Share.key_reg_suc, true);
                            RegData regData = response.body().getData();
                            Share.isinternational = regData.getIs_international();
                            if (regData != null) {
                                SharedPrefs.save(mActivity, Share.key_ + RegReq.name, regData.getName());
                                SharedPrefs.save(mActivity, Share.key_ + RegReq.email, regData.getEmail());
                                SharedPrefs.save(mActivity, Share.key_ + RegReq.mobile_1, regData.getMobile_1());
                                SharedPrefs.save(mActivity, Share.key_ + RegReq.id, regData.getId());
                                SharedPrefs.save(mActivity, SharedPrefs.currency_id, "" + regData.getCurrencyId());
                                SharedPrefs.save(mActivity, SharedPrefs.country_id, regData.getCountry_id());
                                SharedPrefs.save(mActivity, SharedPrefs.country_name, regData.getCountry_name());
//                                SharedPrefs.save(mActivity, SharedPrefs.last_country_code, SharedPrefs.getString(mActivity, SharedPrefs.country_code));
                                SharedPrefs.save(mActivity, SharedPrefs.country_code, regData.getCountry_code());
                                Share.countryCodeValue = regData.getCountry_code();

                                Log.e("UID", "onResponse: " + regData.getId());
                                SharedPrefs.save(mActivity, SharedPrefs.uid, regData.getId());
                                SharedPrefs.save(mActivity, SharedPrefs.Sellerid, regData.getseller_id());
                                SharedPrefs.save(mActivity, SharedPrefs.SELLER, regData.getis_approve());
                                SharedPrefs.save(mActivity, SharedPrefs.TOKEN, regData.getToken());

                                Log.e("LOGINDATA", "onResponse: " + regData.getseller_id());
                                Log.e("TOKEN", "onResponse: ========>" + regData.getToken());
                                Log.e("LOGINDATA", "onResponse: " + regData.getis_approve());
                                Log.e("LOGINDATA", "onResponse: " + SharedPrefs.getString(mActivity, SharedPrefs.uid));
                                Log.e("LOGINDATA", "onResponse: " + SharedPrefs.getString(mActivity, Share.key_ + RegReq.mobile_1));

                                SharedPrefs.save(mActivity, SharedPrefs.isapproved, regData.getis_approve());
                                Log.e("ISAPPROVED", "onResponse:==> " + regData.getis_approve());
                            }


                        }


                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
                        alertDialog.setTitle(getString(R.string.log_in));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(response.body().getResponseMessage());
                        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        alertDialog.create().show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
                    alertDialog.setTitle(getString(R.string.log_in));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.something_went_wrong_try_agaian));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    alertDialog.show();
                }


            }

            @Override
            public void onFailure(Call<RegResponse> call, Throwable t) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {

                    AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            signin(cred, password);

                        }
                    });
                    alertDialog.show();
                } else {

                    AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            signin(cred, password);

                        }
                    });
                    alertDialog.show();
                }
            }
        });
        // new login().execute();

    }

    @Override
    public void onBackPressed() {
        if (isUpdate) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            super.onBackPressed();
        }

    }
}
