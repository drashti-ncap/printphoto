package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Offer;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.getofferresponse;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter.SliderAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.MallSearchActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.newoffers.OfferAdapter;
import com.mobile.cover.photo.editor.back.maker.model.getdata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodetail;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.btn_count;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_account;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_cart;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_home;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_offer;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_order;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.selected;

public class FragmentMall extends Fragment implements View.OnClickListener {
    ProgressDialog pd;
    RecyclerView rv_offer;
    OfferAdapter mAdapter;
    ImageView id_back;
//    SliderLayout banner_slider;
    SliderView banner_slider;
    private ArrayList<String> list= new ArrayList<>();
    TextView ed_search;
    AlertDialog alertDialog;
    FirebaseAnalytics firebaseAnalytics;
    AppEventsLogger logger;
    RelativeLayout rl_offers;

    private List<getdata> sqlist = new ArrayList<>();
    private List<getpromodata> sqlist2 = new ArrayList<>();
    private List<getpromodetail> sqlist3 = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_offeractivity, container, false);
        Share.iscart_frommall = 0;

        HomeMainActivity.id_back.setVisibility(View.VISIBLE);
        HomeMainActivity.toolbar.setVisibility(View.VISIBLE);
        HomeMainActivity.iv_logout.setVisibility(View.VISIBLE);
        btn_count.setVisibility(View.VISIBLE);
        banner_slider = v.findViewById(R.id.banner_slider);
        rl_offers = v.findViewById(R.id.rl_offers);
        Share.click_positions.clear();
        firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        logger = AppEventsLogger.newLogger(getActivity());
      //  banner_slider.setCustomIndicator(v.findViewById(R.id.custom_indicator));
        Bundle params = new Bundle();
        params.putInt("mall_visited", 1);
        firebaseAnalytics.logEvent("mall_visit", params);
        if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) == 0) {
            btn_count.setVisibility(View.GONE);
        } else {
            btn_count.setVisibility(View.VISIBLE);
            btn_count.setText("" + SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT));
        }
        HomeMainActivity.iv_logout.setImageDrawable(getResources().getDrawable(R.drawable.ic_shopping_cart));
        HomeMainActivity.iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != 2) {
                    selected = 2;
                    id_home.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_account.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_cart.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                    id_order.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_offer.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, new FragmentCart());
                    fragmentTransaction.commit();
                }
            }
        });

        setHeader();
        findViews(v);
        intView();
        initlistener();


        return v;

    }


    private void setHeader() {
        final TextView title = getActivity().findViewById(R.id.title);

        title.setText(getString(R.string.new_offers));

        ImageView imageView = getActivity().findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeMainActivity.selected = 0;
                HomeMainActivity.id_home.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_account.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_cart.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_order.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_offer.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
                fragmentTransaction.commit();
            }
        });
    }

    private void intView() {
        selected = 11;
        ImageView id_home = getActivity().findViewById(R.id.id_home);
        ImageView id_order = getActivity().findViewById(R.id.id_order);
        ImageView id_cart = getActivity().findViewById(R.id.id_cart);
        ImageView id_account = getActivity().findViewById(R.id.id_account);
        id_home.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_account.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_order.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_cart.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
        id_offer.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);


        sqlist = new ArrayList<>();
        mAdapter = new OfferAdapter(getActivity(), sqlist, sqlist2, sqlist3);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_offer.setLayoutManager(mLayoutManager);
        rv_offer.setItemAnimator(new DefaultItemAnimator());
        rv_offer.setAdapter(mAdapter);

        getoffer();

    }


    private void initlistener() {
        ed_search.setOnClickListener(this);
    }

    private void findViews(View v) {
        rv_offer = v.findViewById(R.id.rv_offer);
        ed_search = v.findViewById(R.id.ed_search);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


    @Override
    public void onClick(View v) {
        if (v == ed_search) {
            Intent intent = new Intent(getActivity(), MallSearchActivity.class);
            startActivity(intent);
        }
    }

    private void getoffer() {
        APIService api = new MainApiClient(getActivity()).getApiInterface();
        pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);
        Call<getofferresponse> call;
        try {
            if (SharedPrefs.getString(getActivity(), SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                call = api.getoffer("1", "0", SharedPrefs.getString(getActivity(), SharedPrefs.uid), Locale.getDefault().getLanguage(), SharedPrefs.getString(getActivity(), SharedPrefs.country_code));
            } else {
                call = api.getoffer("1", "1", SharedPrefs.getString(getActivity(), SharedPrefs.uid), Locale.getDefault().getLanguage(), SharedPrefs.getString(getActivity(), SharedPrefs.country_code));
            }

            call.enqueue(new Callback<getofferresponse>() {
                public static final String TAG = "test";

                @Override
                public void onResponse(Call<getofferresponse> call, Response<getofferresponse> response) {
                    Log.e(TAG, "onResponse: " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        getofferresponse responseData = response.body();
                        Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                        if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                            Log.e("SUCCESS", "onSUCCESS: ");
                            Share.response_offer = responseData.getOffer();
                            //banner_slider.removeAllSliders();
                            List<Offer> datumList = responseData.getOffer();
                            List<String> size = new ArrayList<>();
                            for (int i = 0; i < datumList.size(); i++) {
                                if (datumList.get(i).getIs_international() == 1) {
                                    size.add(datumList.get(i).getOfferCode());
                                }
                            }
                            Share.offer_list.clear();
                            Share.offer_list.addAll(datumList);

                            if (datumList.size() == 0) {
                                rl_offers.setVisibility(View.GONE);
                            } else {
                                rl_offers.setVisibility(View.VISIBLE);
                            }

                            for (int i = 0; i < datumList.size(); i++) {
                                if (SharedPrefs.getString(getActivity(), SharedPrefs.country_code,"IN").equalsIgnoreCase("IN")) {
                                    rl_offers.setVisibility(View.VISIBLE);
//                                    TextSliderView textSliderView = new TextSliderView(getContext());
//                                    textSliderView
//                                            .description("mall")
//                                            .image(datumList.get(i).getNOfferNewImage())
//                                            .setScaleType(BaseSliderView.ScaleType.Fit);
//                                    banner_slider.addSlider(textSliderView);
                                    list.add(datumList.get(i).getNOfferNewImage());
                                } else {

                                    Log.e(TAG, "onResponse: " + size.size());
                                    if (size.size() == 0) {
                                        rl_offers.setVisibility(View.GONE);
                                    }
//                                    TextSliderView textSliderView = new TextSliderView(getContext());
//                                    textSliderView
//                                            .description("mall")
//                                            .image(datumList.get(i).getNOfferNewImage())
//                                            .setScaleType(BaseSliderView.ScaleType.Fit);
//                                    if (datumList.get(i).getIs_international() == 1) {
//                                        banner_slider.addSlider(textSliderView);
//                                    }
                                    list.add(datumList.get(i).getNOfferNewImage());
                                }
                            }

                            setOfferData();

                            getoffer2();
                        } else {
                            if (pd != null) {
                                pd.dismiss();
                            }
                            Toast.makeText(getActivity(), response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if (pd != null) {
                            pd.dismiss();
                        }
                        Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<getofferresponse> call, Throwable t) {
                    pd.dismiss();
                    Log.e(TAG, "onFailure: ======>" + t);
                    Log.e(TAG, "onFailure: ======>" + t.getMessage());
                    Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                    if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle(getString(R.string.time_out));
                        alertDialog.setMessage(getString(R.string.connect_time_out));
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

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
                                    getoffer();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setOfferData() {
        SliderAdapter adapter = new SliderAdapter(list);
        banner_slider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        banner_slider.setSliderAdapter(adapter);
        banner_slider.setCurrentPagePosition(0);
        banner_slider.setScrollTimeInSec(3);
        banner_slider.setAutoCycle(true);
        banner_slider.startAutoCycle();
    }

    private void getoffer2() {


        sqlist.clear();
        sqlist2.clear();
        sqlist3.clear();


        APIService api = new MainApiClient(getActivity()).getApiInterface();

        Call<getofferresponse> call;
        if (SharedPrefs.getString(getActivity(), SharedPrefs.country_code).equalsIgnoreCase("IN")) {
            call = api.getoffer("", "0", SharedPrefs.getString(getActivity(), SharedPrefs.uid), Locale.getDefault().getLanguage(), SharedPrefs.getString(getActivity(), SharedPrefs.country_code));
        } else {
            call = api.getoffer("", "1", SharedPrefs.getString(getActivity(), SharedPrefs.uid), Locale.getDefault().getLanguage(), SharedPrefs.getString(getActivity(), SharedPrefs.country_code));
        }

        call.enqueue(new Callback<getofferresponse>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<getofferresponse> call, Response<getofferresponse> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    getofferresponse responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        Log.e("SUCCESS", "onSUCCESS: ");
                        List<Offer> datumList = responseData.getOffer();
                        for (Offer datum : datumList) {
                            Log.e(TAG, "onResponse: " + datum.getOfferCode());
                            if (SharedPrefs.getString(getActivity(), SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                                getdata getdata = new getdata("" + datum.getId(), "" + datum.getOfferCode(), "" + datum.getNOfferImage(), "" + datum.getNOfferNewImage(), "" + datum.getDescription(),
                                        "" + datum.getAmount(), "" + datum.getStatus(), "" + datum.getExpiryText(), "" + datum.getTermsCondition(), "" + datum.getDisplay_message());
                                sqlist.add(getdata);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                if (datum.getIs_international() == 1) {
                                    getdata getdata = new getdata("" + datum.getId(), "" + datum.getOfferCode(), "" + datum.getNOfferImage(), "" + datum.getNOfferNewImage(), "" + datum.getDescription(),
                                            "" + datum.getAmount(), "" + datum.getStatus(), "" + datum.getExpiryText(), "" + datum.getTermsCondition(), "" + datum.getDisplay_message());
                                    sqlist.add(getdata);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        if (sqlist.size() == 0) {
                            Toast.makeText(getActivity(), getString(R.string.no_offer_available), Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();
                    } else {
                        pd.dismiss();
                        Toast.makeText(getActivity(), responseData.getResponseMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<getofferresponse> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getoffer();
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getoffer();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

}
