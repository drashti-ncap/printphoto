package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mall_main_category_response_click_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.search_adapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt.SEARCH_SHOPPING_MALL;

public class MallSearchActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity activity;
    EditText ed_search;
    RecyclerView rv_search_list;
    ProgressDialog pd;
    search_adapter mAdapter;
    ImageView id_back, id_cart_display;
    Button btn_count;
    AlertDialog alertDialog;
    RelativeLayout ll_clear_history;


    Call<mall_main_category_response_click_data> call = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_search_activity);
        activity = MallSearchActivity.this;
        findviews();
        initlistener();
    }


    private void findviews() {
        ed_search = findViewById(R.id.ed_search);
        ll_clear_history = findViewById(R.id.ll_clear_history);
        if (Share.searched_product.size() <= 0) {
            ll_clear_history.setVisibility(View.GONE);
        } else {
            ll_clear_history.setVisibility(View.VISIBLE);
        }
        rv_search_list = findViewById(R.id.rv_search_list);
        rv_search_list.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });
        id_back = findViewById(R.id.id_back);
        id_cart_display = findViewById(R.id.id_cart_display);
        btn_count = findViewById(R.id.btn_count);
        if (SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT) == 0) {
            btn_count.setVisibility(View.GONE);
        } else {
            btn_count.setVisibility(View.VISIBLE);
            btn_count.setText("" + SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT));
        }
        mAdapter = new search_adapter(MallSearchActivity.this, Share.searched_product);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MallSearchActivity.this);
        rv_search_list.setLayoutManager(layoutManager);
        final SkeletonScreen skeletonScreen = Skeleton.bind(rv_search_list)
                .adapter(mAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.category_name_item)
                .show(); //default count is 10
        rv_search_list.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        }, 2000);

        rv_search_list.setAdapter(mAdapter);

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("CHANGES", "onTextChanged: " + s);
                String searchQuery = ed_search.getText().toString().trim();
                if (searchQuery.length() >= 3) {
                    get_searched_data(searchQuery);
                    // ToDo: Changes mae by Jignesh Patel
                    FBEventsKt.logSearchedEvent(activity, SEARCH_SHOPPING_MALL, s.toString(), true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initlistener() {
        id_back.setOnClickListener(this);
        id_cart_display.setOnClickListener(this);
        btn_count.setOnClickListener(this);
        ll_clear_history.setOnClickListener(this);
    }

    private void get_searched_data(final String searchQuery) {


        int user_id;
        if (!SharedPrefs.getBoolean(MallSearchActivity.this, Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(MallSearchActivity.this, Share.key_ + RegReq.id));
        }

        APIService apiService = new MainApiClient(MallSearchActivity.this).getApiInterface();

        if (call != null && !call.isCanceled()) {
            call.cancel();
        }


        call = apiService.search(user_id, searchQuery, Share.countryCodeValue, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<mall_main_category_response_click_data>() {
            @Override
            public void onResponse(Call<mall_main_category_response_click_data> call, Response<mall_main_category_response_click_data> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        Share.searched_product.clear();
                        Share.isinternational = response.body().getIs_international();
                        Share.symbol = response.body().getCurrency_symbol();
                        Share.searched_product.addAll(response.body().getData());
                        if (Share.searched_product.size() <= 0) {
                            ll_clear_history.setVisibility(View.GONE);
                        } else {
                            ll_clear_history.setVisibility(View.VISIBLE);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MallSearchActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MallSearchActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<mall_main_category_response_click_data> call, Throwable t) {
                t.printStackTrace();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }

                    alertDialog = new AlertDialog.Builder(MallSearchActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_searched_data(searchQuery);
                        }
                    });
                    alertDialog.show();
                } else if (t.getMessage().contains("Canceled")) {

                } else {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }

                    alertDialog = new AlertDialog.Builder(MallSearchActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_searched_data(searchQuery);
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
        if (id == R.id.id_back) {
            finish();
        } else if (id == R.id.btn_count || id == R.id.id_cart_display) {
            finish();
            HomeMainActivity.cart_select = true;
            Share.iscart_frommall = 1;
        } else if (id == R.id.ll_clear_history) {
            Share.searched_product.clear();
            mAdapter.notifyDataSetChanged();
            ed_search.setText("");
            if (Share.searched_product.size() <= 0) {
                ll_clear_history.setVisibility(View.GONE);
            } else {
                ll_clear_history.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }
}
