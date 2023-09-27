package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Offer;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.getofferresponse;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.getofferdataadapter;
import com.mobile.cover.photo.editor.back.maker.model.getdata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferActivity extends AppCompatActivity {
    ProgressDialog pd;
    RecyclerView rv_offer;
    getofferdataadapter mAdapter;
    ImageView id_back;
    private List<getdata> sqlist = new ArrayList<>();
    private List<getpromodata> sqlist2 = new ArrayList<>();
    private List<getpromodetail> sqlist3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_offeractivity);
        initviews();
        getoffer();
    }

    private void initviews() {
        rv_offer = findViewById(R.id.rv_offer);
        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sqlist = new ArrayList<>();
        mAdapter = new getofferdataadapter(this, sqlist, sqlist2, sqlist3);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_offer.setLayoutManager(mLayoutManager);
        rv_offer.setItemAnimator(new DefaultItemAnimator());
        rv_offer.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getoffer() {


        sqlist.clear();
        sqlist2.clear();
        sqlist3.clear();
        pd = ProgressDialog.show(OfferActivity.this, "", getString(R.string.loading), true, false);

        APIService api = new MainApiClient(OfferActivity.this).getApiInterface();

        Call<getofferresponse> call;
        if (SharedPrefs.getString(OfferActivity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
            call = api.getoffer("", "0", SharedPrefs.getString(OfferActivity.this, SharedPrefs.uid), Locale.getDefault().getLanguage(), SharedPrefs.getString(OfferActivity.this, SharedPrefs.country_code));
        } else {
            call = api.getoffer("", "1", SharedPrefs.getString(OfferActivity.this, SharedPrefs.uid), Locale.getDefault().getLanguage(), SharedPrefs.getString(OfferActivity.this, SharedPrefs.country_code));
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
                            if (SharedPrefs.getString(OfferActivity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
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
                            Toast.makeText(OfferActivity.this, getString(R.string.no_offer_available), Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();
                    } else {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), responseData.getResponseMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<getofferresponse> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(OfferActivity.this).create();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(OfferActivity.this).create();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


}
