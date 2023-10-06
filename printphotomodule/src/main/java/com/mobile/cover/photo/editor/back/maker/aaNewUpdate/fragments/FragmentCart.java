package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Alarm_notification.CAlarmReceiver;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.check_stock_main_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseFragment;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter.CartRecyclerAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout.SelectAddressActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FirebaseEventsKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Contactus_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.RegistrationActivity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemDeleteListener;
import com.mobile.cover.photo.editor.back.maker.model.RegData;
import com.mobile.cover.photo.editor.back.maker.model.RegResponse;
import com.mobile.cover.photo.editor.back.maker.model.SimpleResponse;
import com.mobile.cover.photo.editor.back.maker.model.get_Cart;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.Commen.Share.iscart;
import static com.mobile.cover.photo.editor.back.maker.Commen.Share.isorder;
import static com.mobile.cover.photo.editor.back.maker.Commen.Share.upload;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_offer;

public class FragmentCart extends PrintPhotoBaseFragment implements View.OnClickListener {
    private static final long MIN_CLICK_INTERVAL = 1000;
    public static TextView id_text_view_messess, sign_in_ll, tv_login, tv_login1, id_paid_amount;
    // ProgressDialog progressDialog, pd;
    CartRecyclerAdapter cartRecyclerAdapter;
    RecyclerView recyclerview;
    LinearLayout id_ll, id_ll_sign_in, id_text_view;
    CardView id_check_out;
    Double total_amount = Double.valueOf(0), gift = Double.valueOf(0), paid_amount = Double.valueOf(0);
    Boolean isload;
    int gift1;
    AlarmManager alarmManager;
    FirebaseAnalytics firebaseAnalytics;
    private PendingIntent pendingIntent;
    private long mLastClickTime;

    private Activity mContext;

    public void setContext(Activity context) {
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_frag_cart, container, false);
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

        firebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        // logger = AppEventsLogger.newLogger(mContext);
        setHeader();
        getDisplaySize();
        findViews(v);
        setDimens();
        intView();
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Log.e("LENGTH", "onResume: " + SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT));
        if (SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT) == 0) {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);

        } else {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
        }

        Log.e("key_reg_suc", "intView: ======>" + SharedPrefs.getBoolean(mContext, Share.key_reg_suc));
        if (SharedPrefs.getBoolean(mContext, Share.key_reg_suc)) {
            Log.e("ISCART_ISORDER", "onResume: =========>" + iscart + "======>" + isorder);
            if (iscart) {
                iscart = false;
                Share.CartItem_data = null;
                getcartData();
            }
            if (isorder) {
                isorder = false;
                Share.CartItem_data = null;
                getcartData();
            }
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

    private void intView() {
        ImageView id_home = mContext.findViewById(R.id.id_home);
        ImageView id_order = mContext.findViewById(R.id.id_order);
        ImageView id_cart = mContext.findViewById(R.id.id_cart);
        ImageView id_account = mContext.findViewById(R.id.id_account);
        ImageView id_offer = mContext.findViewById(R.id.id_offer);

        HomeMainActivity.selected = 2;
        id_home.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_account.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_cart.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
        id_order.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_offer.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);

        Share.iscart = false;


        Log.e("key_reg_suc", "intView: ======>" + SharedPrefs.getBoolean(mContext, Share.key_reg_suc));
        if (SharedPrefs.getBoolean(mContext, Share.key_reg_suc)) {
            id_ll.setVisibility(View.GONE);
            Share.CartItem_data = null;
            if (Share.CartItem_data != null) {
                if (upload) {
                    getcartData();
                    return;
                }

                id_ll.setVisibility(View.VISIBLE);
                id_text_view.setVisibility(View.GONE);

                recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
                cartRecyclerAdapter = new CartRecyclerAdapter(mContext, Share.CartItem_data);
                Log.e("LENGTH", "intView: " + Share.CartItem_data.size());
                gift1 = 0;
                total_amount = Double.valueOf(0);
                SharedPrefs.save(mContext, SharedPrefs.CART_COUNT, Share.CartItem_data.size());
                if (Share.CartItem_data.size() == 0) {
                    HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);
                    SharedPrefs.save(mContext, SharedPrefs.CART_COUNT, 0);
                    HomeMainActivity.tv_nudge_cart_count.setText("0");
                } else {
                    HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
                    HomeMainActivity.tv_nudge_cart_count.setText("" + Share.CartItem_data.size());
                }

                Log.e("AMOUNT", "intView: " + total_amount);
                Share.gift = gift1;
                id_paid_amount.setText(Share.symbol + (total_amount));

                recyclerview.setAdapter(cartRecyclerAdapter);
                cartRecyclerAdapter.setOnItemDeletekLister(new OnItemDeleteListener() {
                    @Override
                    public void onItemDeletekLister(View v, final int position) {
                        long currentClickTime = SystemClock.uptimeMillis();
                        long elapsedTime = currentClickTime - mLastClickTime;
                        mLastClickTime = currentClickTime;
                        if (elapsedTime <= MIN_CLICK_INTERVAL)
                            return;
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                        alertDialog.setTitle(getString(R.string.delete));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(getString(R.string.are_u_sure_want_delete_cart));
                        alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteitem(position);

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

                cartRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClickLister(View v, int position) {
                        long currentClickTime = SystemClock.uptimeMillis();
                        long elapsedTime = currentClickTime - mLastClickTime;
                        mLastClickTime = currentClickTime;
                        if (elapsedTime <= MIN_CLICK_INTERVAL)
                            return;
                        Share.CartItem_data = null;
                        getcartData();
                    }
                });

            } else {
                Log.e("HERE", "intView: ===>Please check");
                getcartData();
            }


        } else {
            id_text_view.setVisibility(View.VISIBLE);
            id_ll.setVisibility(View.GONE);
            id_ll_sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Share.iscart = true;
                    Intent intent = new Intent(mContext, RegistrationActivity.class);
                    startActivity(intent);
                }
            });
        }
        upload = false;
    }

    private void deleteitem(final int position) {


        //progressDialog = ProgressDialog.show(mContext, "", getString(R.string.loading), true, false);
//        progressDialog.show();
        showProgressDialog(mContext);

        APIService apiService = new MainApiClient(mContext).getApiInterface();
        Call<SimpleResponse> cartCall = apiService.deleteCart("0", String.valueOf(Share.CartItem_data.get(position).getId()), SharedPrefs.getString(mContext, Share.key_ + RegReq.id),
                Locale.getDefault().getLanguage());


        cartCall.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {

                try {

                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
//                        if (progressDialog != null && progressDialog.isShowing())
//                            progressDialog.dismiss();
                        hideProgressDialog();

                        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setTitle(getString(R.string.delete));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(getString(R.string.item_delete_successfully));
                        alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                getcartData();

                            }
                        });
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setTitle(getString(R.string.failed_to_delete));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(response.body().getResponseMessage());
                        alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();


                            }
                        });
                        alertDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
                hideProgressDialog();

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getcartData();

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
                            getcartData();

                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    private void getcartData() {

        Share.CartItem_data = null;
        isload = true;
        id_ll.setVisibility(View.GONE);
        id_text_view.setVisibility(View.GONE);
//        progressDialog = ProgressDialog.show(mContext, "", getString(R.string.loading), true, false);
//        progressDialog.show();
        showProgressDialog(mContext);


        APIService apiService = new MainApiClient(mContext).getApiInterface();
        Call<get_Cart> cartCall = apiService.getCart(SharedPrefs.getString(mContext, SharedPrefs.uid), Locale.getDefault().getLanguage());

        total_amount = Double.valueOf(0);
        gift1 = 0;
        paid_amount = Double.valueOf(0);
        cartCall.enqueue(new Callback<get_Cart>() {
            @Override
            public void onResponse(Call<get_Cart> call, Response<get_Cart> response) {

                try {
                    Log.e("Response", "onResponse: =====>" + response.isSuccessful());
                    Log.e("Response", "onResponse: =====>" + response.body().getResponseCode());
                    Log.e("Response", "onResponse: =====>" + response.body().getResponseMessage());
                    Log.e("Response", "onResponse: =====>" + response.body().getcart_data().getCartItems().size());
                    Log.e("Response", "onResponse: =====>" + response.body().getcart_data().getCartTotal());
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
//                        if (progressDialog != null && progressDialog.isShowing())
//                            progressDialog.dismiss();
                        hideProgressDialog();

                        Share.symbol = response.body().getcart_data().getCurrency_symbol();
                        isload = false;
                        if (response.body().getcart_data().getCartItems().size() != 0) {
                            id_ll.setVisibility(View.VISIBLE);
                            id_text_view.setVisibility(View.GONE);
                        } else {
                            id_ll.setVisibility(View.GONE);
                            id_text_view.setVisibility(View.VISIBLE);
                            id_text_view_messess.setText(getString(R.string.no_item_available));
                            id_ll_sign_in.setVisibility(View.GONE);
                            tv_login.setVisibility(View.GONE);
                            tv_login1.setVisibility(View.GONE);
                        }

                        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));

                        Share.CartItem_data = response.body().getcart_data().getCartItems();

                        FirebaseEventsKt.logViewCartEvent(mContext, response.body().getcart_data().getCartItems(), response.body().getcart_data().getCartTotal());


                        SharedPrefs.save(mContext, SharedPrefs.CART_COUNT, Share.CartItem_data.size());
                        if (SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT) == 0) {
                            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);
                        } else {
                            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
                            HomeMainActivity.tv_nudge_cart_count.setText("" + SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT));
                        }

                        if (SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT) == 0) {
                            alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                            Intent myIntent = new Intent(mContext, CAlarmReceiver.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            pendingIntent = PendingIntent.getBroadcast(
                                    mContext, 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                            alarmManager.cancel(pendingIntent);
                        } else if (SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT) > 0) {

                            SharedPrefs preff = new SharedPrefs();
                            SharedPrefs.save(mContext, "noti_count", 0);

                            Calendar updateTime = Calendar.getInstance();
                            updateTime.setTimeZone(TimeZone.getDefault());
                            updateTime.set(Calendar.HOUR_OF_DAY, 12);
                            updateTime.set(Calendar.MINUTE, 30);
                            Intent myIntent = new Intent(mContext, CAlarmReceiver.class); //get class from LocalNotification folder
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                            alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
                        }


                        Log.e("LENGTH", "onResponse: " + Share.CartItem_data.size());
                        HomeMainActivity.tv_nudge_cart_count.setText("" + Share.CartItem_data.size());
                        if (Share.CartItem_data.size() == 0) {
                            SharedPrefs.save(mContext, SharedPrefs.CART_COUNT, 0);
                            HomeMainActivity.tv_nudge_cart_count.setText("0");
                        }
                        cartRecyclerAdapter = new CartRecyclerAdapter(mContext, Share.CartItem_data);
                        recyclerview.setAdapter(cartRecyclerAdapter);
                        cartRecyclerAdapter.notifyDataSetChanged();
                        total_amount = response.body().getcart_data().getCartTotal();
                        id_paid_amount.setText(Share.symbol + (total_amount));
                        cartRecyclerAdapter.setOnItemDeletekLister(new OnItemDeleteListener() {
                            @Override
                            public void onItemDeletekLister(View v, final int position) {
                                long currentClickTime = SystemClock.uptimeMillis();
                                long elapsedTime = currentClickTime - mLastClickTime;
                                mLastClickTime = currentClickTime;
                                if (elapsedTime <= MIN_CLICK_INTERVAL)
                                    return;
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                                alertDialog.setTitle(getString(R.string.delete));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.are_u_sure_want_delete_cart));
                                alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        deleteitem(position);
                                        isload = false;
                                    }
                                });
                                alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        isload = false;
                                    }
                                });
                                alertDialog.create().show();

                            }
                        });
                        cartRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClickLister(View v, int position) {
                                long currentClickTime = SystemClock.uptimeMillis();
                                long elapsedTime = currentClickTime - mLastClickTime;
                                mLastClickTime = currentClickTime;
                                if (elapsedTime <= MIN_CLICK_INTERVAL)
                                    return;
                                Share.CartItem_data = null;
                                getcartData();
                            }
                        });
                    } else {
//                        if (progressDialog != null && progressDialog.isShowing())
//                            progressDialog.dismiss();
                        hideProgressDialog();
                        isload = false;
                        id_ll.setVisibility(View.GONE);
                        id_ll_sign_in.setVisibility(View.GONE);
                        id_text_view.setVisibility(View.VISIBLE);
                        tv_login.setVisibility(View.GONE);
                        tv_login1.setVisibility(View.GONE);
                        id_text_view_messess.setText(response.body().getResponseMessage());
                        SharedPrefs.save(mContext, SharedPrefs.CART_COUNT, 0);
                        HomeMainActivity.tv_nudge_cart_count.setText("0");

                        if (SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT) == 0) {
                            alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                            Intent myIntent = new Intent(mContext, CAlarmReceiver.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            pendingIntent = PendingIntent.getBroadcast(
                                    mContext, 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                            alarmManager.cancel(pendingIntent);
                        } else if (SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT) > 0) {

                            SharedPrefs preff = new SharedPrefs();
                            SharedPrefs.save(mContext, "noti_count", 0);

                            Calendar updateTime = Calendar.getInstance();
                            updateTime.setTimeZone(TimeZone.getDefault());
                            updateTime.set(Calendar.HOUR_OF_DAY, 12);
                            updateTime.set(Calendar.MINUTE, 30);
                            Intent myIntent = new Intent(mContext, CAlarmReceiver.class); //get class from LocalNotification folder
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                            alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    progressDialog.dismiss();
                    isload = false;
                    hideProgressDialog();
                }

            }

            @Override
            public void onFailure(Call<get_Cart> call, Throwable t) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
                hideProgressDialog();

                Log.e("FAILURE", "onFailure: " + t.getLocalizedMessage());
                Log.e("FAILURE", "onFailure: " + t.getMessage());
                Log.e("FAILURE", "onFailure: " + t.getStackTrace());

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getcartData();

                        }
                    });
                    alertDialog.show();
                } else {
                    if (mContext != null) {
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setTitle(getString(R.string.internet_connection));
                        alertDialog.setMessage(getString(R.string.slow_connect));
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                getcartData();

                            }
                        });
                        alertDialog.show();
                    }
                }
            }
        });

    }

    private void setDimens() {
        int height = Share.screenHeight / 14;
        id_ll_sign_in.getLayoutParams().height = (int) (Share.screenHeight / 9.8);

    }

    private void findViews(View v) {
        recyclerview = v.findViewById(R.id.recyclerview);
//        recyclerview.setHasFixedSize(true);
//        recyclerview.setItemViewCacheSize(25);
//        recyclerview.setDrawingCacheEnabled(true);
//        recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        id_check_out = v.findViewById(R.id.id_check_out);
        sign_in_ll = v.findViewById(R.id.sign_in_ll);
        sign_in_ll.setOnClickListener(this);
        id_paid_amount = v.findViewById(R.id.id_paid_amount);
        id_text_view = v.findViewById(R.id.id_text_view);
        id_text_view_messess = v.findViewById(R.id.id_text_view_messess);
        tv_login = v.findViewById(R.id.tv_login);
        tv_login1 = v.findViewById(R.id.tv_login1);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_text_view.setVisibility(View.VISIBLE);
                id_ll.setVisibility(View.GONE);
                ImageView id_home = mContext.findViewById(R.id.id_home);
                ImageView id_order = mContext.findViewById(R.id.id_order);
                ImageView id_cart = mContext.findViewById(R.id.id_cart);
                ImageView id_account = mContext.findViewById(R.id.id_account);
                id_home.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_cart.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_account.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                id_order.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_offer.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                Share.iscart = true;
                HomeMainActivity.selected = 3;
                FragmentAccount account = new FragmentAccount();
                account.setContext(mContext);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frg_main, account);
                fragmentTransaction.commit();
            }
        });
        id_ll_sign_in = v.findViewById(R.id.id_ll_sign_in);
        id_ll = v.findViewById(R.id.id_ll);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
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
        title.setText(getString(R.string.cart));
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
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == sign_in_ll) {

            long currentClickTime = SystemClock.uptimeMillis();
            long elapsedTime = currentClickTime - mLastClickTime;
            mLastClickTime = currentClickTime;
            if (elapsedTime <= MIN_CLICK_INTERVAL)
                return;

            Bundle params = new Bundle();
            params.putInt("Cart", Share.CartItem_data.size());
            //   firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, params);
            Share.gift = 0;
            Log.e("ADDRESS_POS", "onClick: " + Share.addressposition);
            if (Share.CartItem_data != null) {

                StringBuilder item_id = new StringBuilder();
                StringBuilder item_qnty = new StringBuilder();
                StringBuilder modelid = new StringBuilder();
                StringBuilder type = new StringBuilder();
                StringBuilder cart_id = new StringBuilder();


                for (int i = 0; i < Share.CartItem_data.size(); i++) {
                    item_id.append(Share.CartItem_data.get(i).getModelId());
                    item_qnty.append(Share.CartItem_data.get(i).getQuantity());
                    modelid.append(Share.CartItem_data.get(i).getModelId());
                    cart_id.append(Share.CartItem_data.get(i).getId());


                    if (Share.CartItem_data.size() - 1 != i) {
                        item_id.append(",");
                        item_qnty.append(",");
                        modelid.append(",");
                        type.append(",");
                        cart_id.append(",");
                    }
                }
                Check_stock(item_id.toString(), cart_id.toString(), type.toString(), modelid.toString(), item_qnty.toString(), getTotalQuantity());
            }
        }
    }

    private void Check_stock(final String item_id, final String cart_id, final String type, final String modelid, final String item_qnty, int quantity) {

        //pd = ProgressDialog.show(mContext, "", getString(R.string.loading), true, false);
        showProgressDialog(mContext);
        APIService api = new MainApiClient(mContext).getApiInterface();
        Call<check_stock_main_response> call = api.check_stock(cart_id, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<check_stock_main_response>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<check_stock_main_response> call, Response<check_stock_main_response> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    //pd.dismiss();
                    hideProgressDialog();
                    check_stock_main_response responseData = response.body();
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        if (responseData.getData().getStatus() == 1) {
                            Share.isfromplaceorder = true;
                            Intent intent = new Intent(mContext, SelectAddressActivity.class);
                            intent.putExtra("addresstype", "select");
                            intent.putExtra("total_amount", "" + total_amount);
                            intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
                            intent.putExtra("shipping", "0");

                            intent.putExtra("item_id", item_id);
                            intent.putExtra("cart_id", cart_id);
                            intent.putExtra("type", type);
                            intent.putExtra("modelid", modelid);
                            intent.putExtra("item_quantity", item_qnty);
                            intent.putExtra("quantity", quantity);

                            startActivity(intent);

                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(responseData.getData().getMessage());
                            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
//                            Toast.makeText(mContext, responseData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(mContext, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<check_stock_main_response> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Check_stock(item_id, cart_id, type, modelid, item_qnty, getTotalQuantity());

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
                            Check_stock(item_id, cart_id, type, modelid, item_qnty, getTotalQuantity());
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (int i = 0; i < Share.CartItem_data.size(); i++) {
            totalQuantity = totalQuantity + Share.CartItem_data.get(i).getQuantity();
        }
        return totalQuantity;
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
                showProgressDialog(mContext);

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
                            alertDialog.setPositiveButton(getString(R.string.ok), (dialog, which) -> dialog.dismiss());

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


