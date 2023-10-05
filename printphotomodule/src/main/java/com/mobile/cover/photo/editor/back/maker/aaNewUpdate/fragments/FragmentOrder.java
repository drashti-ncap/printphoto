package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Orderdetails;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.getorderresponse;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseFragment;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter.OrderAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Contactus_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.RegistrationActivity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.RegData;
import com.mobile.cover.photo.editor.back.maker.model.RegResponse;
import com.mobile.cover.photo.editor.back.maker.model.getorderdetails;
import com.mobile.cover.photo.editor.back.maker.rateandfeedback.library_feedback.FeedbackUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_offer;

public class FragmentOrder extends PrintPhotoBaseFragment {
  //  ProgressDialog progressDialog;
    LinearLayout id_text_view, id_ll_sign_in, ll_sign_in_text, id_ll;
    TextView id_text_view_messess, tv_login1, tv_login;
    RecyclerView recyclerview;
    SwipeRefreshLayout mySwipeRefreshLayout;
    //ProgressDialog pd;
    OrderAdapter mAdapter;
    private List<Orderdetails> sqlist = new ArrayList<Orderdetails>();
    private List<getorderdetails> sqlist1 = new ArrayList<>();

    private Activity mContext;

    // Define a method to set the context
    public void setContext(Activity context) {
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_order, container, false);
        HomeMainActivity.id_back.setVisibility(View.VISIBLE);
        HomeMainActivity.toolbar.setVisibility(View.VISIBLE);
        HomeMainActivity.btn_count.setVisibility(View.GONE);
        HomeMainActivity.iv_logout.setVisibility(View.VISIBLE);
        HomeMainActivity.iv_logout.setImageDrawable(getResources().getDrawable(R.drawable.ic_contact_us));
        HomeMainActivity.iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Contactus_activity.class);
                startActivity(intent);
            }
        });
        setHeader();
        getDisplaySize();
        findViews(v);
        setDimens();
        intView();
        Share.upload_success = false;
        return v;

    }


    private void setDimens() {

        id_ll_sign_in.getLayoutParams().height = (int) (Share.screenHeight / 9.8);

    }

    private void intView() {

        ImageView id_home = mContext.findViewById(R.id.id_home);
        ImageView id_order = mContext.findViewById(R.id.id_order);
        ImageView id_cart = mContext.findViewById(R.id.id_cart);
        ImageView id_account = mContext.findViewById(R.id.id_account);
        ImageView id_offer = mContext.findViewById(R.id.id_offer);


        HomeMainActivity.selected = 1;
        id_home.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_account.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_cart.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_order.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
        id_offer.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);

        Share.isorder = false;
        if (SharedPrefs.getBoolean(mContext, Share.key_reg_suc)) {
            id_ll_sign_in.setVisibility(View.GONE);
            ll_sign_in_text.setVisibility(View.GONE);
//            if (Share.order_simplerespons != null) {
//                id_ll_sign_in.setVisibility(View.GONE);
//                id_text_view.setVisibility(View.VISIBLE);
//                tv_login.setVisibility(View.GONE);
//                tv_login1.setVisibility(View.GONE);
//                id_text_view_messess.setText(Share.order_simplerespons.getResponseMessage());
//            } else {
            getorder();
//            }
        } else {
            id_ll_sign_in.setVisibility(View.VISIBLE);
            ll_sign_in_text.setVisibility(View.VISIBLE);
        }

        id_ll_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share.isorder = true;
                HomeMainActivity.selected = 3;
                Intent intent = new Intent(mContext, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Share.order_cancel) {
            Share.order_cancel = false;
            getorder();
        }

        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (SharedPrefs.getString(mContext, SharedPrefs.country_code).equalsIgnoreCase("")) {
            Share.countryCodeValue = tm.getNetworkCountryIso().toUpperCase();
        } else {
            Share.countryCodeValue = SharedPrefs.getString(mContext, SharedPrefs.country_code);
        }

        if (SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT) == 0) {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);
        } else {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
            HomeMainActivity.tv_nudge_cart_count.setText("" + SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT));
        }

        if (Share.isRegistrationSuccess) {

            Share.isRegistrationSuccess = false;

            String mobile = SharedPrefs.getString(mContext, Share.key_ + RegReq.mobile_1);
            String email = SharedPrefs.getString(mContext, Share.key_ + RegReq.email);

            String cred;
            if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                cred = mobile;
            } else {
                cred = email;
                for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                    if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                        if (SharedPrefs.getString(mContext, SharedPrefs.country_code).equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
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

    private void getorder() {

        sqlist.clear();
        sqlist1.clear();
        mAdapter.notifyDataSetChanged();

        id_ll_sign_in.setVisibility(View.GONE);
        ll_sign_in_text.setVisibility(View.GONE);
        id_text_view.setVisibility(View.VISIBLE);
//        progressDialog = ProgressDialog.show(mContext, "", getString(R.string.loading), true, false);
//        progressDialog.show();
        showProgressDialog(mContext);


        APIService apiService = new MainApiClient(mContext).getApiInterface();

        Log.e("UID", "getorder:=======>" + SharedPrefs.getString(mContext, Share.key_ + RegReq.id));

        Call<getorderresponse> call = apiService.getorderdetiails(Integer.valueOf(SharedPrefs.getString(mContext, Share.key_ + RegReq.id)), Locale.getDefault().getLanguage());
        call.enqueue(new Callback<getorderresponse>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<getorderresponse> call, Response<getorderresponse> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {

                    getorderresponse responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
//                        if (progressDialog != null && progressDialog.isShowing())
//                            progressDialog.dismiss();
                        hideProgressDialog();
                        id_ll_sign_in.setVisibility(View.GONE);
                        ll_sign_in_text.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        Log.e("SUCCESS", "onSUCCESS: ");
                        List<Orderdetails> datumList = responseData.getData();
                        if (datumList.size() == 0) {
                            id_text_view_messess.setVisibility(View.VISIBLE);
                            mySwipeRefreshLayout.setRefreshing(false);
                        } else {
                            id_text_view_messess.setVisibility(View.GONE);
//                            for (Orderdetails datum : datumList) {
                            Log.e(TAG, "onResponse:=============>FINAL======>1 ");
//                                if (datum.getOrderStatus().equalsIgnoreCase("Delivered")) {
//                                    exitdialog();
//                                }
//                                List<orderitem> datumitemlist = datum.getOrderItems();
//                                getorderdata getorderdata = new getorderdata("" + datum.getOrderId(), "" + datum.getDate(), "" + datum.getTransactionType(), "" + datum.getTransactionId(),
//                                        "" + datum.getTotalAmount(), "" + datum.getDiscountAmount(), "" + datum.getShipping(), "" + datum.getPaidAmount(), "" + datum.getShowTracking(),
//                                        "" + datum.getOrderStatus(), "" + datum.getTrackingLink(), "" + datum.getIsCancellable(), "" + datum.getCancellationMessage(), "" + datum.getCurrency_symbol(),
//                                        "" + datum.getIs_gift(), "" + datum.getGift_charge(), datum.getOrderItems());
                            sqlist.addAll(responseData.getData());
                            mySwipeRefreshLayout.setRefreshing(false);
                            mAdapter.notifyDataSetChanged();
                        }
//                        }
                    } else {
//                        if (progressDialog != null && progressDialog.isShowing())
//                            progressDialog.dismiss();
                        hideProgressDialog();
                        mySwipeRefreshLayout.setRefreshing(false);
                        id_ll_sign_in.setVisibility(View.GONE);
                        ll_sign_in_text.setVisibility(View.GONE);
                        id_text_view.setVisibility(View.VISIBLE);
                        tv_login.setVisibility(View.GONE);
                        tv_login1.setVisibility(View.GONE);
                        id_text_view_messess.setText(response.body().getResponseMessage());
                    }
                } else {
                    //progressDialog.dismiss();
                    hideProgressDialog();
                    mySwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(mContext, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<getorderresponse> call, Throwable t) {

                //recyclerview.setNestedScrollingEnabled(true);


                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                Log.e(TAG, "onFailure: " + t.getMessage());
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
                hideProgressDialog();
                    mySwipeRefreshLayout.setRefreshing(false);
//                }

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getorder();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getorder();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }


    public void exitdialog() {
        if (SharedPrefs.getInt(mContext, SharedPrefs.REVIEW) == 0) {
            displayalert();
        }
    }


    private void displayalert() {
        final Dialog dialog = new Dialog(mContext);
        if (dialog != null)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.order_dialog_finish_alert); //get layout from ExitDialog folder
        TextView textView = dialog.findViewById(R.id.tv_dialog_text);
        TextView tv_rate_appp = dialog.findViewById(R.id.tv_rate_appp);
        textView.setText(getString(R.string.order_is_delivered));
        SmileRating smileRating = dialog.findViewById(R.id.smile_rating);
        Button btn_yes = dialog.findViewById(R.id.btn_yes);
        Button btn_no = dialog.findViewById(R.id.btn_no);
        btn_no.setText(getString(R.string.dismiss));
        btn_yes.setVisibility(View.GONE);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                    case SmileRating.OKAY:
                    case SmileRating.TERRIBLE:
                        dialog.dismiss();
                        SharedPrefs.save(mContext, SharedPrefs.REVIEW, 1);
                        FeedbackUtils.FeedbackDialog(mContext);
                        break;
                    case SmileRating.GOOD:
                    case SmileRating.GREAT:
                        dialog.dismiss();
                        SharedPrefs.save(mContext, SharedPrefs.REVIEW, 1);
                        rate_app();
                        break;
                }
            }
        });
        dialog.show();
    }

    private void rate_app() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mContext.getPackageName())));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
        }
    }

    private void findViews(View v) {
        id_text_view = v.findViewById(R.id.id_text_view);
        tv_login = v.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView id_home = mContext.findViewById(R.id.id_home);
                ImageView id_order = mContext.findViewById(R.id.id_order);
                ImageView id_cart = mContext.findViewById(R.id.id_cart);
                ImageView id_account = mContext.findViewById(R.id.id_account);
                id_home.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_account.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                id_cart.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_order.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_offer.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                Share.display_isorder = true;
                HomeMainActivity.selected = 3;
                FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frg_main, new FragmentAccount());
                fragmentTransaction.commit();
            }
        });
        tv_login1 = v.findViewById(R.id.tv_login1);
        id_text_view_messess = v.findViewById(R.id.id_text_view_messess);
        id_ll_sign_in = v.findViewById(R.id.id_ll_sign_in);
        ll_sign_in_text = v.findViewById(R.id.ll_sign_in_text);
        id_ll = v.findViewById(R.id.id_ll);
        recyclerview = v.findViewById(R.id.recyclerview);
        mySwipeRefreshLayout = v.findViewById(R.id.mySwipeRefreshLayout);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (SharedPrefs.getString(mContext, Share.key_ + RegReq.id).equalsIgnoreCase("")) {
                            Log.e("NOTHING", "onRefresh: ");
                            mySwipeRefreshLayout.setRefreshing(false);
                        } else {
                            getorder();
                        }
                    }
                }
        );
        sqlist = new ArrayList<Orderdetails>();
        sqlist1 = new ArrayList<>();
        mAdapter = new OrderAdapter(mContext, sqlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);

    }

    private void getDisplaySize() {
        Display display = mContext.getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void setHeader() {
        TextView title = mContext.findViewById(R.id.title);

        title.setText(getString(R.string.order));
        ImageView imageView = mContext.findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeMainActivity.selected = 0;
                HomeMainActivity.id_home.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_account.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_cart.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_order.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_offer.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


    private void signin(String cred, String password) {
        String androidId = Share.firebaseToken;
        Log.e("androidId", "==>" + androidId);
//        progressDialog = ProgressDialog.show(mContext, "", getString(R.string.loading), true, false);
//        progressDialog.show();
        showProgressDialog(mContext);

        APIService apiService = new MainApiClient(mContext).getApiInterface();
        Call<RegResponse> regResponseCall = apiService.getRegResponseLogin(cred, password, androidId, "android", TimeZone.getDefault().getID(), Locale.getDefault().getLanguage());

        regResponseCall.enqueue(new Callback<RegResponse>() {
            @Override
            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
                hideProgressDialog();

                if (response.body() != null) {
                    Log.e("androidId", "==>" + response.body().getResponseCode());


                    //progressDialog.dismiss();
                    hideProgressDialog();
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        SharedPrefs.save(mContext, SharedPrefs.CART_COUNT, response.body().getCart_count());
                        HomeMainActivity.tv_nudge_cart_count.setText("" + SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT));
                        if (response.body().getData() == null) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
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
                            SharedPrefs.savePref(mContext, Share.key_reg_suc, true);
                            RegData regData = response.body().getData();
                            Share.isinternational = regData.getIs_international();
                            if (regData != null) {
                                SharedPrefs.save(mContext, Share.key_ + RegReq.name, regData.getName());
                                SharedPrefs.save(mContext, Share.key_ + RegReq.email, regData.getEmail());
                                SharedPrefs.save(mContext, Share.key_ + RegReq.mobile_1, regData.getMobile_1());
                                SharedPrefs.save(mContext, Share.key_ + RegReq.id, regData.getId());
                                SharedPrefs.save(mContext, SharedPrefs.currency_id, "" + regData.getCurrencyId());
                                SharedPrefs.save(mContext, SharedPrefs.country_id, regData.getCountry_id());
                                SharedPrefs.save(mContext, SharedPrefs.country_name, regData.getCountry_name());
//                                SharedPrefs.save(mContext, SharedPrefs.last_country_code, SharedPrefs.getString(mContext, SharedPrefs.country_code));
                                SharedPrefs.save(mContext, SharedPrefs.country_code, regData.getCountry_code());
                                Share.countryCodeValue = regData.getCountry_code();

                                Log.e("UID", "onResponse: " + regData.getId());
                                SharedPrefs.save(mContext, SharedPrefs.uid, regData.getId());
                                SharedPrefs.save(mContext, SharedPrefs.Sellerid, regData.getseller_id());
                                SharedPrefs.save(mContext, SharedPrefs.SELLER, regData.getis_approve());
                                SharedPrefs.save(mContext, SharedPrefs.TOKEN, regData.getToken());

                                Log.e("LOGINDATA", "onResponse: " + regData.getseller_id());
                                Log.e("TOKEN", "onResponse: ========>" + regData.getToken());
                                Log.e("LOGINDATA", "onResponse: " + regData.getis_approve());
                                Log.e("LOGINDATA", "onResponse: " + SharedPrefs.getString(mContext, SharedPrefs.uid));
                                Log.e("LOGINDATA", "onResponse: " + SharedPrefs.getString(mContext, Share.key_ + RegReq.mobile_1));


                                SharedPrefs.save(mContext, SharedPrefs.isapproved, regData.getis_approve());

                                Log.e("ISAPPROVED", "onResponse:==> " + regData.getis_approve());
                            }

                            intView();


                        }


                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
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
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
                hideProgressDialog();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {

                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
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

                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
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

}
