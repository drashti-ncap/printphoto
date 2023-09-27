package com.mobile.cover.photo.editor.back.maker.aaNewUpdate;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;


import com.ads.narayan.admob.AppOpenManager;
import com.ads.narayan.ads.NarayanAd;
import com.ads.narayan.ads.NarayanAdCallback;
import com.ads.narayan.ads.wrapper.NarayanAdError;
import com.ads.narayan.ads.wrapper.NarayanInterstitialAd;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.mobile.cover.photo.editor.back.maker.Alarm_notification.CAlarmReceiver;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.RetrofitClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentAccount;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentCart;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentHome;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentHomeSub;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentMall;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentOrder;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.UserProfileFragment;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.finished_activity;
import com.mobile.cover.photo.editor.back.maker.fragment.Mall_FragmentOne;
import com.mobile.cover.photo.editor.back.maker.model.CityState;
import com.mobile.cover.photo.editor.back.maker.model.SubDataModelDetails;
import com.mobile.cover.photo.editor.back.maker.model.new_main_model;
import com.mobile.cover.photo.editor.back.maker.phonecase.CoverStyle;
import com.mobile.cover.photo.editor.back.maker.phonecase.FragmentPhoneCover;
import com.mobile.cover.photo.editor.back.maker.phonecase.FragmentRequestModelBrand;
import com.mobile.cover.photo.editor.back.maker.phonecase.SelectCompanyMobel;
import com.mobile.cover.photo.editor.back.maker.rateandfeedback.library_feedback.FeedbackUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.SplashScreenKt.ARG_IS_CART;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.SplashScreenKt.ARG_IS_OFFER;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "HomeMainActivity";

    private static final long MIN_CLICK_INTERVAL = 250;
    private static long mLastClickTime = 0;

    public static androidx.appcompat.widget.Toolbar toolbar;
    public static ImageView id_offer, id_home, id_order, id_cart, id_account, iv_language, iv_language_hindi;
    public static Button btn_count;
    public static TextView tv_nudge_cart_count;
    public static ImageView id_back, iv_logout;
    public static int selected;
    public static boolean cart_select = false;
    public static SubDataModelDetails current_model;
    FragmentTransaction fragmentTransaction;
    FragmentHome home;
    FragmentMall mall;
    FragmentCart cart;
    FragmentAccount account;
    FragmentOrder order;
    boolean clicked;
    AlarmManager alarmManager;
    LinearLayout ll_home_main_ac;
    private PendingIntent pendingIntent;
    private Context mContext;
    private ProgressDialog pd = null;
    public static NarayanInterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_main);
        mContext = HomeMainActivity.this;
        AppOpenManager.getInstance().setEnableScreenContentCallback(true);
        AppOpenManager.getInstance().setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
                Log.e("AppOpenManager", "onAdShowedFullScreenContent: ");

            }
        });
        findViews();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        getData();
        intiviews();


        // getMainCategories();
        intFrag();

        //configMediationProvider();

        boolean isCart = getIntent().getBooleanExtra(ARG_IS_CART, false);
        Log.i(TAG, "isCart:" + isCart);

        boolean isOffer = getIntent().getBooleanExtra(ARG_IS_OFFER, false);
        Log.i(TAG, "isOffer:" + isOffer);

        if (isCart && selected != 2) {
            selected = 2;
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            cartclick();
        } else if (isOffer && selected != 11) {
            selected = 11;
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
            offerclick();
        } else {
            fragment_oncreate_selection();
        }

    }

    private void getData() {
        /*if (Integer.parseInt(getCurrentTime())>10 && Integer.parseInt(getCurrentTime())<12){
            setLocale(HomeMainActivity.this, "en");
            configMediationProvider();
        }else {
            setLocale(HomeMainActivity.this, "es");
            configMediationProvider();
        }*/

        Call<CityState> call = RetrofitClient.getInstance().getMyApi().getData();
        call.enqueue(new Callback<CityState>() {
            @Override
            public void onResponse(Call<CityState> call, Response<CityState> response) {
                CityState myheroList = response.body();
                Share.CityState = myheroList;

            }

            @Override
            public void onFailure(Call<CityState> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
            }

        });

    }

    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = ""+mdformat.format(calendar.getTime()).split(":")[0];
        return strDate;
    }


    private void configMediationProvider() {
        Locale current = getResources().getConfiguration().locale;
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void loadAdInterstitial() {
        mInterstitialAd = NarayanAd.getInstance().getInterstitialAds(this, getString(R.string.inter_ad_unit_id));
    }

    private void handelMainData(new_main_model new_main_model) {
        if (new_main_model.getResponseCode().equals("1")) {
            Share.main_category_data = new_main_model.getAllChilds();
            Share.isinternational = new_main_model.getIs_international();
            Share.symbol = new_main_model.getCurrency_symbol();
            intFrag();
            fragment_oncreate_selection();
        } else {
            Toast.makeText(mContext, new_main_model.getResponseMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void fragment_oncreate_selection() {
        Log.e("ONSELECTED", "fragment_oncreate_selection: " + selected);
        if (selected == 11) {
            selected = 11;
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
//            id_home.settint(R.drawable.ic_home);
//            id_offer.setImageResource(R.drawable.ic_mall_select);
//            id_account.setImageResource(R.drawable.ic_account);
//            id_cart.setImageResource(R.drawable.ic_cart);
//            id_order.setImageResource(R.drawable.ic_order);
            offerclick();
        } else if (selected == 0) {
            selected = 0;
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frg_main, home);
            fragmentTransaction.commit();
        } else if (selected == 1) {
            selected = 1;
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            orderclick();
        }

        if (selected == 2) {
            selected = 0;
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            cartclick();
        } else if (selected == 0) {
            selected = 0;
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        }
    }

    public void fragment_transact(FragmentHomeSub fragmentOne) {
        String backstack = fragmentOne.getClass().getName();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, fragmentOne);
        fragmentTransaction.addToBackStack(backstack);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void mall_fragment_transact(Mall_FragmentOne mall_fragmentOne) {
        String backstack = mall_fragmentOne.getClass().getName();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, mall_fragmentOne);
        fragmentTransaction.addToBackStack(backstack);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void phone_cover_fragment_transact(FragmentPhoneCover phone_cover_fragment) {
        String backstack = phone_cover_fragment.getClass().getName();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, phone_cover_fragment);
        fragmentTransaction.addToBackStack(backstack);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void request_cover_fragment_transact(FragmentRequestModelBrand request_model_brand) {
        String backstack = request_model_brand.getClass().getName();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, request_model_brand);
        fragmentTransaction.addToBackStack(backstack);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void select_company_fragment_transact(SelectCompanyMobel selectCompanyMobel) {
        String backstack = selectCompanyMobel.getClass().getName();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, selectCompanyMobel);
        fragmentTransaction.addToBackStack(backstack);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void cover_style_fragment_transact(CoverStyle coverStyle) {
        String backstack = coverStyle.getClass().getName();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, coverStyle);
        fragmentTransaction.addToBackStack(backstack);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void intiviews() {
        id_home.setOnClickListener(this);
        id_order.setOnClickListener(this);
        id_cart.setOnClickListener(this);
        id_account.setOnClickListener(this);
        id_offer.setOnClickListener(this);
    }

    private void findViews() {
        id_home = findViewById(R.id.id_home);
        ll_home_main_ac = findViewById(R.id.ll_home_main_ac);
        iv_logout = findViewById(R.id.iv_logout);
        id_offer = findViewById(R.id.id_offer);
        btn_count = findViewById(R.id.btn_count);
        id_back = findViewById(R.id.id_back);
        id_order = findViewById(R.id.id_order);
        id_cart = findViewById(R.id.id_cart);
        id_account = findViewById(R.id.id_account);
        iv_language = findViewById(R.id.iv_language);
        iv_language_hindi = findViewById(R.id.iv_language_hindi);
        tv_nudge_cart_count = findViewById(R.id.tv_nudge_cart_count);
//        tv_nudge_cart_count.setText(""+Share.CartItem_data.length);

        if (Share.mall_enable.equalsIgnoreCase("1")) {
            id_offer.setVisibility(View.VISIBLE);
        } else {
            id_offer.setVisibility(View.GONE);
        }

        id_offer.setVisibility(View.VISIBLE);

        iv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_language.setVisibility(View.GONE);
                iv_language_hindi.setVisibility(View.VISIBLE);
                SharedPrefs.save(HomeMainActivity.this, SharedPrefs.LANGUAGE, "hindi");
                Log.e("LANGUAGE", "onClick:=========>HINDI ");
            }
        });
        iv_language_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_language_hindi.setVisibility(View.GONE);
                iv_language.setVisibility(View.VISIBLE);
                SharedPrefs.save(HomeMainActivity.this, SharedPrefs.LANGUAGE, "english");
                Log.e("LANGUAGE", "onClick:=========>ENGLISH");
            }
        });
    }

    private void intFrag() {
        home = new FragmentHome();
        mall = new FragmentMall();
        cart = new FragmentCart();
        account = new FragmentAccount();
        order = new FragmentOrder();

    }


    public void homeclick() {
        iv_logout.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, home);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void offerclick() {
        Share.iscart_frommall = 0;
        iv_logout.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, mall);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void orderclick() {
        iv_logout.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, order);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void cartclick() {
        iv_logout.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, cart);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void acountclick() {
        iv_logout.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frg_main, account);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAdInterstitial();
        fragment_onresume_selection();
        nudge_count();
        Log.e("USERPROFILE", "onResume: " + Share.upload_success);
    }

    private void nudge_count() {
        if (SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT) == 0) {
            tv_nudge_cart_count.setVisibility(View.GONE);
        } else {
            tv_nudge_cart_count.setVisibility(View.VISIBLE);
            tv_nudge_cart_count.setText("" + SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT));
        }
    }

    private void fragment_onresume_selection() {
        Log.e("upload_success", "fragment_onresume_selection: =======>" + Share.upload_success);
        Log.e("upload", "fragment_onresume_selection: =======>" + Share.upload);
        Log.e("cart_select", "fragment_onresume_selection: =======>     `AZ " + cart_select);
        if (Share.upload_success) {
            Share.upload_success = false;
            orderclick();
        }

        Log.e("CHECK", "fragment_onresume_selection: =====>1" + Share.upload);
        Log.e("CHECK", "fragment_onresume_selection: =====>2==//" + cart_select);
        Log.e("CHECK", "fragment_onresume_selection: =====>3==//" + selected);
        if (Share.upload) {
            selected = 2;
            cart_select = false;
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            cartclick();
        }
        if (cart_select) {
            selected = 2;
            cart_select = false;
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            cartclick();
        }
    }

    @Override
    public void onClick(View view) {


        // mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < MIN_CLICK_INTERVAL) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        int id = view.getId();
        if (id == R.id.id_home) {
            Log.e("SELECTED", "onClick: " + selected);

//                if (selected != 0) {
            selected = 0;
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            homeclick();
//                }
        } else if (id == R.id.id_offer) {
            if (selected != 11) {
                selected = 11;
                id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                offerclick();
            }
        } else if (id == R.id.id_order) {
            if (selected != 1) {
                selected = 1;
                id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                orderclick();

            }
        } else if (id == R.id.id_cart) {
            if (selected != 2) {
                selected = 2;
                id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                cartclick();
            }
        } else if (id == R.id.id_account) {
            Log.e("BOOLEAN", "onClick: " + SharedPrefs.getBoolean(HomeMainActivity.this, Share.key_reg_suc));
            if (SharedPrefs.getBoolean(HomeMainActivity.this, Share.key_reg_suc)) {
                if (selected != 9) {
                    selected = 9;
                    iv_logout.setVisibility(View.VISIBLE);
                    id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                    id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, new UserProfileFragment());
                    fragmentTransaction.commitAllowingStateLoss();
                }
            } else {
                Log.e("SELECTED", "onClick: " + selected);
                selected = 0;
                if (selected != 3) {
                    selected = 3;
                    id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                    id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    acountclick();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
          //  exitdialog();
            finish();
        } else {
            id_home.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
            id_account.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_cart.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_order.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_offer.setColorFilter(ContextCompat.getColor(HomeMainActivity.this, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
            id_back.callOnClick();
        }

    }

    public void exitdialog() {
        if (selected == 0) {
            if (SharedPrefs.getInt(getApplicationContext(), SharedPrefs.REVIEW) == 0) {
                displayalert();
            } else {
                display_normal_alert();
            }
        } else {
            id_back.callOnClick();
        }
    }

    private void display_normal_alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeMainActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getString(R.string.are_you_exit));
        alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT) == 0) {
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent myIntent = new Intent(getApplicationContext(), CAlarmReceiver.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getBroadcast(
                            getApplicationContext(), 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                    alarmManager.cancel(pendingIntent);
                    dialog.dismiss();
                    call_interestial_ads();
                } else if (SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT) > 0) {

                    SharedPrefs preff = new SharedPrefs();
                    SharedPrefs.save(getApplicationContext(), "noti_count", 0);

                    Calendar updateTime = Calendar.getInstance();
                    updateTime.setTimeZone(TimeZone.getDefault());
                    updateTime.set(Calendar.HOUR_OF_DAY, 12);
                    updateTime.set(Calendar.MINUTE, 30);
                    Intent myIntent = new Intent(getApplicationContext(), CAlarmReceiver.class); //get class from LocalNotification folder
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
                    dialog.dismiss();
                    call_interestial_ads();
                }
            }
        });
        alertDialog.create().show();
    }

    private void displayalert() {
        final Dialog dialog = new Dialog(HomeMainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_finish_alert); //get layout from ExitDialog folder
        Window window = dialog.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        SmileRating smileRating = dialog.findViewById(R.id.smile_rating);
        Button btn_yes = dialog.findViewById(R.id.btn_yes);
        Button btn_no = dialog.findViewById(R.id.btn_no);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT) == 0) {
                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent myIntent = new Intent(getApplicationContext(), CAlarmReceiver.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        pendingIntent = PendingIntent.getBroadcast(
                                getApplicationContext(), 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                        alarmManager.cancel(pendingIntent);
                        dialog.dismiss();

                        call_interestial_ads();

                    } else if (SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT) > 0) {

                        SharedPrefs preff = new SharedPrefs();
                        SharedPrefs.save(getApplicationContext(), "noti_count", 0);

                        Calendar updateTime = Calendar.getInstance();
                        updateTime.setTimeZone(TimeZone.getDefault());
                        updateTime.set(Calendar.HOUR_OF_DAY, 12);
                        updateTime.set(Calendar.MINUTE, 30);
                        Intent myIntent = new Intent(getApplicationContext(), CAlarmReceiver.class); //get class from LocalNotification folder
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
                        dialog.dismiss();
                        call_interestial_ads();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                    case SmileRating.OKAY:
                    case SmileRating.TERRIBLE:
                        SharedPrefs.save(HomeMainActivity.this, SharedPrefs.REVIEW, 1);
                        dialog.dismiss();
                        FeedbackUtils.FeedbackDialog(HomeMainActivity.this);
                        break;
                    case SmileRating.GOOD:
                    case SmileRating.GREAT:
                        SharedPrefs.save(HomeMainActivity.this, SharedPrefs.REVIEW, 1);
                        dialog.dismiss();
                        rate_app();
                        break;
                }
            }
        });
        dialog.show();

    }

    public void call_interestial_ads() {
        if (mInterstitialAd.isReady()) {
            NarayanAd.getInstance().forceShowInterstitial(this, mInterstitialAd, new NarayanAdCallback() {
                @Override
                public void onNextAction() {
                    finish();
                    ActivityCompat.finishAffinity(HomeMainActivity.this);
                    Intent intent = new Intent(HomeMainActivity.this, finished_activity.class);
                    startActivity(intent);
                }

                @Override
                public void onAdFailedToShow(@Nullable NarayanAdError adError) {
                    super.onAdFailedToShow(adError);
                    Log.i(TAG, "onAdFailedToShow:" + adError.getMessage());
                }

                @Override
                public void onInterstitialShow() {
                    super.onInterstitialShow();
                    Log.d(TAG, "onInterstitialShow");
                }
            }, true);
        } else {
            loadAdInterstitial();
            finish();
            ActivityCompat.finishAffinity(HomeMainActivity.this);
            Intent intent = new Intent(HomeMainActivity.this, finished_activity.class);
            startActivity(intent);

        }
    }

    private void rate_app() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


}
