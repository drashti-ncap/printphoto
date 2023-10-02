package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.mobile.cover.photo.editor.back.maker.Alarm_notification.CAlarmReceiver;
import com.mobile.cover.photo.editor.back.maker.BuildConfig;
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.ProductType;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mall_main_category_response_click_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ModelListActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter.HomeAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter.HomeMallAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.mall_category_activity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.newoffers.OffersActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.GlideUtilKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.THUMB_TYPE;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.finished_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.mall_filter_activity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.fragment.Mall_FragmentOne;
import com.mobile.cover.photo.editor.back.maker.fragment.SelectCompany;
import com.mobile.cover.photo.editor.back.maker.mainapplication;
import com.mobile.cover.photo.editor.back.maker.model.AllChild;
import com.mobile.cover.photo.editor.back.maker.model.OfferMain;
import com.mobile.cover.photo.editor.back.maker.model.mall_AllChild;
import com.mobile.cover.photo.editor.back.maker.model.mall_new_main_model;
import com.mobile.cover.photo.editor.back.maker.model.new_main_model;
import com.mobile.cover.photo.editor.back.maker.phonecase.FragmentPhoneCover;
import com.mobile.cover.photo.editor.back.maker.rateandfeedback.library_feedback.FeedbackUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.selected;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt.sendWhatsappDirectMessageHome;

public class FragmentHome extends Fragment {
    private static final String TAG = "HOME";
    HomeAdapter mHomeAdapter;
    RecyclerView mHomeRecyclerview;
    OfferMain[] offer;
    LinearLayout ll_main;
    SelectCompany selectCompany;
    ProgressDialog pd;
    AlarmManager alarmManager;
    AlertDialog alertDialog;
    private PendingIntent pendingIntent;
    ImageView iv_help_wa;

    RecyclerView rv_offer;
    //  mall_main_category_data mAdapter;
    HomeMallAdapter mAdapter;
    // top card
    RelativeLayout main_card;
    ImageView iv_thumb;
    ProgressBar progressBar;
    private String idBanner = "";
    private String idNative = "";
    private String idInter = "";
    private int layoutNativeCustom;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        HomeMainActivity.id_back.setVisibility(View.GONE);
        HomeMainActivity.toolbar.setVisibility(View.GONE);
        HomeMainActivity.btn_count.setVisibility(View.GONE);
        HomeMainActivity.iv_logout.setVisibility(View.GONE);
        findViews(v);
        getMainData();
        iv_help_wa.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                sendWhatsappDirectMessageHome(getActivity());
            }
        });
        return v;
    }


    private void getMainData() {

        pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);
        Share.mall_main_category_data.clear();

        APIService apiService = new MainApiClient(getActivity()).getApiInterface();
        Log.e(TAG, "getMainData: 1==" + SharedPrefs.getString(getActivity(), SharedPrefs.country_code));
        Log.e(TAG, "getMainData: 2==" + SharedPrefs.getString(getActivity(), Share.key_ + RegReq.id));
        Log.e(TAG, "getMainData: 3==" + Locale.getDefault().getLanguage());
        Call<new_main_model> call = apiService.get_new_MainCatagaries(SharedPrefs.getString(getActivity(), SharedPrefs.country_code), SharedPrefs.getString(getActivity(), Share.key_ + RegReq.id), Locale.getDefault().getLanguage());

        call.enqueue(new Callback<new_main_model>() {

            @Override
            public void onResponse(Call<new_main_model> call, Response<new_main_model> response) {
                if (response.code() == 200) {
                    new_main_model new_main_model = (com.mobile.cover.photo.editor.back.maker.model.new_main_model) response.body();
                    Log.e("MAINRESPONSE", "onResponse: " + new_main_model.getResponseCode());
                    if (new_main_model.getResponseCode().equalsIgnoreCase("1")) {
                        Share.main_category_data = new_main_model.getAllChilds();
                        Share.isinternational = new_main_model.getIs_international();
                        Share.symbol = new_main_model.getCurrency_symbol();

                        Log.e("datasize", "onResponse: " + Share.main_category_data.size());
                        Log.e("datasize", "onResponse: " + Share.isinternational);
                        Log.e("datasize", "onResponse: " + Share.symbol);
                        Share.maincategoryname.clear();
                        Share.click_positions.clear();
                        setHeader();
                        getDisplaySize();

                        try {
                            String cCode = SharedPrefs.getString(getActivity(), SharedPrefs.country_code);
                            if (cCode != null && cCode.equalsIgnoreCase("SA")) {
                                pd.dismiss();
                                intView();
                            } else {
                                getMallData();
                            }
                        } catch (Exception e) {
                            pd.dismiss();
                            intView();
                            Log.e("TAGGGG", e.toString());
                        }


                        //
                    } else {
                        showWhatsAppHelp();
                        pd.dismiss();
                        Toast.makeText(getActivity(), new_main_model.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    pd.dismiss();
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.server_error));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.server_under_maintenance));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getActivity().finish();

                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<new_main_model> call, Throwable t) {
                showWhatsAppHelp();
                error_dialogs(t);
            }
        });

    }

    public void error_dialogs(Throwable t) {
        pd.dismiss();
        Log.e("MESSAGE", "error_dialogs: " + t.getMessage());
        Log.e("MESSAGE", "error_dialogs: " + t.getLocalizedMessage());
        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle(getString(R.string.time_out));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.connect_time_out));
            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getMainData();

                }
            });
            alertDialog.show();
        } else {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            if (getActivity() != null) {
                alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle(getString(R.string.internet_connection));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.slow_connect));
                alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getMainData();

                    }
                });
                alertDialog.show();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Share.login_back = false;
        Log.e("LAST", "onResume: " + SharedPrefs.getString(getActivity(), SharedPrefs.last_country_code));
        Log.e("RECENT", "onResume: " + SharedPrefs.getString(getActivity(), SharedPrefs.country_code));
        Log.e("ISREGISTER", "onResume: " + Share.isregistration);

        if (Share.isregistration) {
            Share.isregistration = false;
        } else {
            if (SharedPrefs.getString(getActivity(), SharedPrefs.last_country_code).equalsIgnoreCase(SharedPrefs.getString(getActivity(), SharedPrefs.country_code))) {
                Share.maincategoryname.clear();
                Share.click_positions.clear();
                try {
                    setHeader();
                    getDisplaySize();
                    intView();
                } catch (Exception e) {
                    Log.e(TAG, "onResume: " + e.getLocalizedMessage());
                }

            }
        }


        if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) == 0) {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);
        } else {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        if (Share.notification_category_id != 0) {
            Log.e(TAG, "onItemClickLister: =======>Intent mey");
            Share.category_id = Integer.valueOf(Share.notification_category_id);
            Intent intent = new Intent(getContext(), ModelListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            DataHelperKt.saveProductId(requireActivity(), Share.notification_category_id);

            intent.putExtra("name", Share.notification_category_name);
            startActivity(intent);
        }

        if (Share.offer_display) {
            Log.e("OFFER_DISPLAY", "onResume: " + Share.offer_display);
            Share.offer_display = false;
            if (SharedPrefs.getString(getActivity(), SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                Intent intent = new Intent(getActivity(), OffersActivity.class);
                startActivity(intent);
            }
        }

        getDisplaySize();
    }

    private void setHeader() {
        try {
            final TextView title = getActivity().findViewById(R.id.title);

//            title.setText(getString(R.string.mobile_cover));

            ImageView imageView = getActivity().findViewById(R.id.id_back);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SharedPrefs.getInt(getActivity(), SharedPrefs.REVIEW) == 0) {
                        displayalert();
                    } else {
                        display_normal_alert();
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "setHeader: " + e.getLocalizedMessage());
        }

    }


    private void display_normal_alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
                if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) == 0) {
                    alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent myIntent = new Intent(getActivity(), CAlarmReceiver.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pendingIntent = PendingIntent.getBroadcast(
                            getActivity(), 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                    alarmManager.cancel(pendingIntent);
                    dialog.dismiss();
                    call_interestial_ads();
                } else if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) > 0) {

                    SharedPrefs preff = new SharedPrefs();
                    SharedPrefs.save(getActivity(), "noti_count", 0);

                    Calendar updateTime = Calendar.getInstance();
                    updateTime.setTimeZone(TimeZone.getDefault());
                    updateTime.set(Calendar.HOUR_OF_DAY, 12);
                    updateTime.set(Calendar.MINUTE, 30);
                    Intent myIntent = new Intent(getActivity(), CAlarmReceiver.class); //get class from LocalNotification folder
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                    alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
                    dialog.dismiss();
                    call_interestial_ads();
                }
            }
        });
        alertDialog.create().show();
    }

    public void call_interestial_ads() {
        Log.e(TAG, "call_interestial_ads: here_frag");
        getActivity().finish();
        ActivityCompat.finishAffinity(getActivity());
        Intent intent = new Intent(getActivity(), finished_activity.class);
        startActivity(intent);
    }
    private void displayalert() {
        final Dialog dialog = new Dialog(getActivity());
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
                    if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) == 0) {
                        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent myIntent = new Intent(getActivity(), CAlarmReceiver.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        pendingIntent = PendingIntent.getBroadcast(
                                getActivity(), 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                        alarmManager.cancel(pendingIntent);
                        dialog.dismiss();
                        call_interestial_ads();
                    } else if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) > 0) {

                        SharedPrefs preff = new SharedPrefs();
                        SharedPrefs.save(getActivity(), "noti_count", 0);

                        Calendar updateTime = Calendar.getInstance();
                        updateTime.setTimeZone(TimeZone.getDefault());
                        updateTime.set(Calendar.HOUR_OF_DAY, 12);
                        updateTime.set(Calendar.MINUTE, 30);
                        Intent myIntent = new Intent(getActivity(), CAlarmReceiver.class); //get class from LocalNotification folder
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, PendingIntent.FLAG_IMMUTABLE);

                        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
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
                        SharedPrefs.save(getActivity(), SharedPrefs.REVIEW, 1);
                        dialog.dismiss();
                        FeedbackUtils.FeedbackDialog(getActivity());
                        break;
                    case SmileRating.GOOD:
                    case SmileRating.GREAT:
                        dialog.dismiss();
                        SharedPrefs.save(getActivity(), SharedPrefs.REVIEW, 1);
                        rate_app();
                        break;
                }
            }
        });
        dialog.show();

    }

    private void rate_app() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName())));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
        }
    }


    private void intView() {
        try {
            selected = 0;
            selectCompany = new SelectCompany();
            Log.e("data", "offer==>" + offer);
            if (Share.main_category_data.size() != 0) {
                for (int i = 0; i < Share.main_category_data.size(); i++) {
                    Share.maincategoryname.add(Share.main_category_data.get(i).getName());
                }
                Log.e("data", "DATA LENGTH==>" + Share.main_category_data.size());


                mHomeAdapter = new HomeAdapter(getContext(), Share.main_category_data, (v, position) -> {
                    onItemClick(position);
                });
                mHomeRecyclerview.setAdapter(mHomeAdapter);

            }
        } catch (Exception e) {
            Log.e(TAG, "intView: " + e.getLocalizedMessage());
        }


    }

    private void onItemClick(int position) {
        Share.click_positions.clear();
        Share.click_positions.add(position);
        Share.cat_id = String.valueOf(Share.main_category_data.get(position).getId());

        ProductType productId = DataHelperKt.getProductIdType(requireActivity());


        if (Share.main_category_data.get(position).getId() == productId.getPhonecase()) {
            Share.category_id = Share.main_category_data.get(position).getId();
            Share.main_category_id = productId.getPhonecase();

            if (Share.main_category_data.get(position).getAllChilds() != null && Share.main_category_data.get(position).getAllChilds().size() > 0) {
                Log.e(TAG, "onItemClickLister: =======>Fragment mey");
                Share.header_name = Share.main_category_data.get(position).getName();
                Share.search_dynamic_sub_category_list = Share.main_category_data.get(position).getAllChilds();
                Share.dynamic_sub_category_list = Share.main_category_data.get(position).getAllChilds();

                Collections.sort(Share.dynamic_sub_category_list, new Comparator<AllChild>() {
                    @Override
                    public int compare(AllChild p1, AllChild p2) {
                        return p1.getName().compareTo(p2.getName());
                    }
                });

                for (int i = 0; i < Share.dynamic_sub_category_list.size(); i++) {
                    if (Share.dynamic_sub_category_list.get(i).getName().equalsIgnoreCase("Looking For Other Brand")) {
                        Share.dynamic_sub_category_list.remove(i);
                    }
                }
                AllChild allChild = new AllChild();
                allChild.setName("Looking For Other brand");
                if (!Share.dynamic_sub_category_list.contains("Looking For Other Brand")) {
                    Share.dynamic_sub_category_list.add(allChild);
                }
                Log.e(TAG, "onItemClickLister: =======>Fragment mey=====>" + Share.dynamic_sub_category_list.size());
                ((HomeMainActivity) getActivity()).phone_cover_fragment_transact(new FragmentPhoneCover());
            } else {
                Log.e(TAG, "onItemClickLister: =======>Intent mey");
                Share.brand_name = Share.main_category_data.get(position).getName();
                Intent intent = new Intent(getContext(), ModelListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                DataHelperKt.saveProductId(requireActivity(), Share.main_category_data.get(position).getId());
                intent.putExtra("name", Share.main_category_data.get(position).getName());
                startActivity(intent);
            }
        } else {
            Share.category_id = Share.main_category_data.get(position).getId();
            if (Share.category_id == productId.getMug()) {
                Share.main_category_id = productId.getMug();
            } else {
                Share.main_category_id = Share.main_category_data.get(position).getId();
            }
            if (Share.main_category_data.get(position).getAllChilds() != null && Share.main_category_data.get(position).getAllChilds().size() > 0) {
                Log.e(TAG, "onItemClickLister: =======>Fragment mey");
                Share.header_name = Share.main_category_data.get(position).getName();
                Share.dynamic_sub_category_list = Share.main_category_data.get(position).getAllChilds();
                Log.e(TAG, "onItemClickLister: =======>Fragment mey=====>" + Share.dynamic_sub_category_list.size());
                ((HomeMainActivity) getActivity()).fragment_transact(new FragmentHomeSub());
            } else {
                Log.e(TAG, "onItemClickLister: =======>Intent mey");
                String title;
                Share.brand_name = Share.main_category_data.get(position).getName();
                Share.header_name = Share.main_category_data.get(position).getName();
                Intent intent = new Intent(getContext(), ModelListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                DataHelperKt.saveProductId(requireActivity(), Share.main_category_data.get(position).getId());
                intent.putExtra("name", Share.main_category_data.get(position).getName());
                startActivity(intent);
            }
        }
    }


    private void findViews(View v) {
        mHomeRecyclerview = v.findViewById(R.id.main_recyclerview);
        ll_main = v.findViewById(R.id.ll_main);
        rv_offer = v.findViewById(R.id.rv_offer);
        iv_help_wa = v.findViewById(R.id.iv_help_wa);

        main_card = v.findViewById(R.id.main_card);
        iv_thumb = v.findViewById(R.id.iv_thumb);
        progressBar = v.findViewById(R.id.progressBar);
        rv_offer.setVisibility(View.GONE);
        main_card.setVisibility(View.GONE);

    }


    private void getDisplaySize() {
        try {
            Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Share.screenWidth = size.x;
            Share.screenHeight = size.y;
        } catch (Exception e) {
            Log.e(TAG, "getDisplaySize: " + e.getLocalizedMessage());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


    private void getMallData() {
        try {

            Share.mall_main_category_data.clear();
            APIService apiService = new MainApiClient(getActivity()).getApiInterface();
            Call<mall_new_main_model> call = apiService.get_mall_category(SharedPrefs.getString(getActivity(), SharedPrefs.country_code), SharedPrefs.getString(getActivity(), Share.key_ + RegReq.id), Locale.getDefault().getLanguage());

            call.enqueue(new Callback<mall_new_main_model>() {

                @Override
                public void onResponse(Call<mall_new_main_model> call, Response<mall_new_main_model> response) {
                    if (response.code() == 200) {
                        mall_new_main_model mall_new_main_model = (com.mobile.cover.photo.editor.back.maker.model.mall_new_main_model) response.body();
                        if (mall_new_main_model.getResponseCode().equalsIgnoreCase("1")) {
                            Share.isinternational = mall_new_main_model.getIs_international();
                            Share.mall_main_category_data = mall_new_main_model.getAllChilds();

                            Log.e(TAG, "onResponse: " + Share.mall_main_category_data);

                            setUpMallData();
                        } else {
                            Toast.makeText(getActivity(), mall_new_main_model.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (pd != null) {
                        pd.dismiss();
                    }

                    showWhatsAppHelp();
                }

                @Override
                public void onFailure(Call<mall_new_main_model> call, Throwable t) {
                    if (pd != null) {
                        pd.dismiss();
                    }
                    showWhatsAppHelp();
                }
            });
        } catch (Exception e) {
            if (pd != null) {
                pd.dismiss();
            }
            e.printStackTrace();
            showWhatsAppHelp();
        }
    }

    private void setUpMallData() {


        if (Share.mall_main_category_data != null && !Share.mall_main_category_data.isEmpty()) {

            mall_AllChild topPos = Share.mall_main_category_data.get(0);
            Log.e(TAG, "setUpMallData: " + topPos.getName());
            Share.mall_main_category_data.remove(0);

            if (topPos.getInternational_app_mall_image() != null) {
                String imgPath = topPos.getInternational_app_mall_image();
                if (SharedPrefs.getString(getActivity(), SharedPrefs.country_code).equals("IN")) {
                    imgPath = topPos.getApp_mall_image();
                }
                Log.i("New_thumb", imgPath);
                GlideUtilKt.loadImage(getActivity(), imgPath, iv_thumb, progressBar, THUMB_TYPE.LANDSCAPE);
            } else {
                progressBar.setVisibility(View.GONE);
                Glide.with(getActivity()).load(R.drawable.ic_thumb_logo).centerCrop().into(iv_thumb);
            }


            main_card.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (topPos.getAllChilds() != null && topPos.getAllChilds().size() > 0) {
                        Share.click_positions.clear();
                        Share.click_positions.add(1);

                        Share.header_name = topPos.getName();
                        Log.e("LEVEL", "onItemClickLister: " + topPos.getLevel());
                        Share.mall_dynamic_sub_category_list = topPos.getAllChilds();
                        ((HomeMainActivity) getActivity()).mall_fragment_transact(new Mall_FragmentOne());
                    } else {
                        get_other_category(topPos);
                    }
                }
            });

            intView();


            mAdapter = new HomeMallAdapter(getActivity(), Share.mall_main_category_data, (v1, position) -> {
                Share.click_positions.clear();
                Share.click_positions.add(position);
                Log.e(TAG, "setUpMallData: " + Share.click_positions);
                if (Share.mall_main_category_data.get(position).getAllChilds() != null && Share.mall_main_category_data.get(position).getAllChilds().size() > 0) {
                    Share.header_name = Share.mall_main_category_data.get(position).getName();
                    Log.e("LEVEL", "onItemClickLister: " + Share.mall_main_category_data.get(position).getLevel());
                    Share.mall_dynamic_sub_category_list = Share.mall_main_category_data.get(position).getAllChilds();
                    ((HomeMainActivity) getActivity()).mall_fragment_transact(new Mall_FragmentOne());
                } else {
                    get_other_category(Share.mall_main_category_data.get(position));
                }
            });
            rv_offer.setItemAnimator(new DefaultItemAnimator());
            rv_offer.setAdapter(mAdapter);


            rv_offer.setVisibility(View.VISIBLE);
            main_card.setVisibility(View.VISIBLE);
        } else {
            intView();
        }


    }

    private void get_other_category(mall_AllChild mall_allChild) {
        if (pd != null) {
            pd.dismiss();
        }
        pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);

        APIService apiService = new MainApiClient(getActivity()).getApiInterface();
        int user_id;
        if (!SharedPrefs.getBoolean(getActivity(), Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(getActivity(), Share.key_ + RegReq.id));
        }
        Call<mall_main_category_response_click_data> call = apiService.call_main_categoru_subdata(mall_allChild.getId(), user_id, Share.countryCodeValue, "whats_new", Locale.getDefault().getLanguage());

        call.enqueue(new Callback<mall_main_category_response_click_data>() {
            @Override
            public void onResponse(Call<mall_main_category_response_click_data> call, Response<mall_main_category_response_click_data> response) {
                if (pd != null) {
                    pd.dismiss();
                }
                pd.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        Share.isinternational = response.body().getIs_international();

                        Share.symbol = response.body().getCurrency_symbol();
                        Share.category_header_name = mall_allChild.getName();
                        Share.filtered_response.clear();
                        Share.subresponse_data = response.body().getData();
                        Share.checked_arraylist.clear();
                        Share.available_filters.clear();
                        Share.available_filters.addAll(mall_allChild.getAvailableFilter());
                        Log.e("SIZE", "onResponse: " + Share.subresponse_data.size());
                        Log.e("SIZE", "onResponse: " + mall_allChild.getAvailableFilter().size());
                        mall_filter_activity.mactivity = null;
                        Share.base_filter_url = "";
                        Intent intent = new Intent(getActivity(), mall_category_activity.class);
                        intent.putExtra("category_id", "" + mall_allChild.getId());
                        getActivity().startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<mall_main_category_response_click_data> call, Throwable t) {
                t.printStackTrace();
                if (pd != null) {
                    pd.dismiss();
                }
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                        alertDialog = null;
                    }
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_other_category(mall_allChild);
                        }
                    });
                    alertDialog.show();
                } else {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                        alertDialog = null;
                    }
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_other_category(mall_allChild);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    private void showWhatsAppHelp() {
        if (!SharedPrefs.getString(getActivity(), SharedPrefs.country_code).equals("SA") && !SharedPrefs.getBoolean(getActivity(), Share.key_reg_suc)) {
            iv_help_wa.setVisibility(View.VISIBLE);
        }
    }
}
