package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mall_main_category_response_click_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.mall_filter_adapter;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.mainapplication;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.mall_category_activity;

import java.util.ArrayList;

public class mall_filter_activity extends AppCompatActivity implements View.OnClickListener {
    public static Activity mactivity;
    RecyclerView rv_filter_list;
    mall_filter_adapter mall_filter_adapter;
    ImageView id_back, id_cart_display;
    ProgressDialog pd;
    Button btn_count, btn_clear, btn_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_activity);
        mactivity = mall_filter_activity.this;
        findviews();
        initlistener();
    }

    private void findviews() {
        rv_filter_list = findViewById(R.id.rv_filter_list);
        rv_filter_list.setHasFixedSize(true);
        id_back = findViewById(R.id.id_back);
        id_cart_display = findViewById(R.id.id_cart_display);
        btn_count = findViewById(R.id.btn_count);
        btn_clear = findViewById(R.id.btn_clear);
        btn_apply = findViewById(R.id.btn_apply);
        mall_filter_adapter = new mall_filter_adapter(mall_filter_activity.this, Share.available_filters);
        rv_filter_list.setLayoutManager(new LinearLayoutManager(mall_filter_activity.this));
        rv_filter_list.setAdapter(mall_filter_adapter);

        if (SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT) == 0) {
            btn_count.setVisibility(View.GONE);
        } else {
            btn_count.setVisibility(View.VISIBLE);
            btn_count.setText("" + SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT));
        }
    }

    private void initlistener() {
        id_back.setOnClickListener(this);
        id_cart_display.setOnClickListener(this);
        btn_count.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_back) {
            filter(0);
        } else if (id == R.id.btn_clear) {
            filter(0);
        } else if (id == R.id.btn_apply) {
            filter(1);
        } else if (id == R.id.btn_count || id == R.id.id_cart_display) {
            finish();
            Share.iscart_frommall = 1;
            HomeMainActivity.cart_select = true;
        }
    }

    @Override
    public void onBackPressed() {
        filter(0);
    }

    private void filter(final int filter) {
        pd = ProgressDialog.show(this, "", this.getString(R.string.loading), true, false);
        int user_id;
        if (!SharedPrefs.getBoolean(mall_filter_activity.this, Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(mall_filter_activity.this, Share.key_ + RegReq.id));
        }

        StringBuilder filter_url = new StringBuilder();
        if (filter != 1) {
            Share.checked_arraylist.clear();
            filter_url.append(UtilsKt.getBaseUrl(mactivity) + "mall_products?user_id=" + user_id + "&category_id=" + getIntent().getStringExtra("category_id") + "&country_code=" + Share.countryCodeValue);
        } else {
            filter_url.append(UtilsKt.getBaseUrl(mactivity) + "mall_products?user_id=" + user_id + "&category_id=" + getIntent().getStringExtra("category_id") + "&max_price=" + Share.max_price + "&min_price=" + Share.min_price + "&country_code=" + Share.countryCodeValue);

            ArrayList<String> categoryList = new ArrayList<>();
            for (int i = 0; i < Share.checked_arraylist.size(); i++) {
                String[] catname = Share.checked_arraylist.get(i).getValue().split("//");
                if (!categoryList.contains(catname[0])) {
                    categoryList.add(catname[0]);
                }
            }

            if (categoryList.size() > 0) {
                for (int i = 0; i < categoryList.size(); i++) {
                    filter_url.append("&Filter_" + categoryList.get(i) + "=");
                    int m = 0;
                    for (int k = 0; k < Share.checked_arraylist.size(); k++) {
                        String[] name = Share.checked_arraylist.get(k).getValue().split("//");
                        if (name[0].equalsIgnoreCase(categoryList.get(i))) {
                            if (m == 0) {
                                if (name[1].contains("&")) {
                                    filter_url.append(name[1].replace("&", "%26"));
                                } else if (name[1].contains("^")) {
                                    filter_url.append(name[1].replace("^", "%5E%0A"));
                                } else {
                                    filter_url.append(name[1]);
                                }
                                m++;
                            } else {
                                if (name[1].contains("&")) {
                                    filter_url.append("," + name[1].replace("&", "%26"));
                                } else if (name[1].contains("^")) {
                                    filter_url.append(name[1].replace("^", "%5E%0A"));
                                } else {
                                    filter_url.append("," + name[1]);
                                }
                            }
                        }
                    }
                }

            }
        }
        String url = filter_url.toString().replace(" ", "%20");

        Share.base_filter_url = url + "&sort=" + Share.sort_value;
        Log.e("FILTERURL", "filter: " + url + "&sort=" + Share.sort_value);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url + "&sort=" + Share.sort_value,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        mall_main_category_response_click_data mall_main_category_response_click_data = gson.fromJson(response, mall_main_category_response_click_data.class);
                        if (mall_main_category_response_click_data.getResponseCode().equalsIgnoreCase("1")) {
                            pd.dismiss();
                            Share.subresponse_data = mall_main_category_response_click_data.getData();
                            if (filter == 0) {
                                finish();
                                mactivity = null;
                                Intent intent = new Intent(mall_filter_activity.this, mall_category_activity.class);
                                intent.putExtra("category_id", "" + getIntent().getStringExtra("category_id"));
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(mall_filter_activity.this, mall_category_activity.class);
                                intent.putExtra("category_id", "" + getIntent().getStringExtra("category_id"));
                                startActivity(intent);
                            }
                        } else {
                            pd.dismiss();
                            Toast.makeText(mall_filter_activity.this, mall_main_category_response_click_data.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError t) {
                        if (t != null) {
                            pd.dismiss();
                            Log.e("Error", "Message=====>" + t.getLocalizedMessage());
                            Log.e("Error", "Message=====>" + t.getMessage());
                            Log.e("Error", "Message=====>" + t.getNetworkTimeMs());
                            Log.e("Error", "Message=====>" + t.getCause());
                            Log.e("Error", "Message=====>" + t.getStackTrace());
                            Log.e("Error", "Message=====>" + t.networkResponse);
                            if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                                AlertDialog alertDialog = new AlertDialog.Builder(mall_filter_activity.this).create();
                                alertDialog.setTitle(getString(R.string.time_out));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.connect_time_out));
                                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        filter(1);
                                    }
                                });
                                alertDialog.show();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(mall_filter_activity.this).create();
                                alertDialog.setTitle(getString(R.string.internet_connection));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.slow_connect));
                                alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        filter(1);
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


}
