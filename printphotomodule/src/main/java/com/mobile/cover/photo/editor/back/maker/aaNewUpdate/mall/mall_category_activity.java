package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mall_main_category_response_click_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.LogInActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Mall_wishlist;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.mall_filter_activity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.mainapplication;

public class mall_category_activity extends AppCompatActivity implements View.OnClickListener {

    public static Activity mActivity;
    public static String sort_value = "whats_new";
    ImageView id_back, iv_wish_list_disp;
    RecyclerView rv_sub_category_data;
    mall_sub_category_data_adapter mAdapter;
    CardView cd_sort, cd_filter;
    ProgressDialog pd;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_category_activity);
        mActivity = mall_category_activity.this;
        if (!Share.sort_value.equalsIgnoreCase("")) {
            sort_value = Share.sort_value;
        } else {
            sort_value = "whats_new";
        }
        findviews();
        initlistener();
    }

    private void findviews() {
        id_back = findViewById(R.id.id_back);
        cd_sort = findViewById(R.id.cd_sort);
        title = findViewById(R.id.title);
        title.setText(Share.category_header_name);
        cd_filter = findViewById(R.id.cd_filter);
        iv_wish_list_disp = findViewById(R.id.iv_wish_list_disp);
        rv_sub_category_data = findViewById(R.id.rv_sub_category_data);
        mAdapter = new mall_sub_category_data_adapter(mActivity, Share.subresponse_data);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mActivity, 2);
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(2, 12, true);
        rv_sub_category_data.setLayoutManager(mLayoutManager);
        rv_sub_category_data.setItemAnimator(new DefaultItemAnimator());
        rv_sub_category_data.addItemDecoration(gridSpacingItemDecoration);
        rv_sub_category_data.setAdapter(mAdapter);
    }

    private void initlistener() {
        id_back.setOnClickListener(this);
        iv_wish_list_disp.setOnClickListener(this);
        cd_sort.setOnClickListener(this);
        cd_filter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.id_back) {
            Share.sort_value = "whats_new";

            if (mall_filter_activity.mactivity != null) {
                mall_filter_activity.mactivity.finish();
                mall_filter_activity.mactivity = null;
                finish();
            } else {
                mall_filter_activity.mactivity = null;
                finish();
            }
        } else if (id == R.id.iv_wish_list_disp) {
            if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mall_category_activity.this);
                alertDialog.setTitle(getString(R.string.log_in));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.need_login));
                alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent intent = new Intent(mall_category_activity.this, LogInActivity.class);
                        startActivity(intent);

                    }
                });
                alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialog.create().show();
            } else {
                if (Mall_wishlist.activity != null) {
                    Mall_wishlist.activity.finish();
                }
                Intent wish_list_intent = new Intent(mall_category_activity.this, Mall_wishlist.class);
                startActivity(wish_list_intent);
            }
        } else if (id == R.id.cd_sort) {
            final Dialog dialog = new Dialog(mall_category_activity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_sort);
            Window window = dialog.getWindow();
            window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.show();
            final TextView tv_clear = dialog.findViewById(R.id.tv_clear);
            final TextView tv_apply = dialog.findViewById(R.id.tv_apply);

            final LinearLayout ll_price_low_high = dialog.findViewById(R.id.ll_price_low_high);
            final LinearLayout ll_price_high_low = dialog.findViewById(R.id.ll_price_high_low);
            final LinearLayout ll_popularity = dialog.findViewById(R.id.ll_popularity);
            final LinearLayout ll_whats_new = dialog.findViewById(R.id.ll_whats_new);
            final LinearLayout ll_best_seller = dialog.findViewById(R.id.ll_best_seller);
            final LinearLayout ll_rating = dialog.findViewById(R.id.ll_rating);

            final RadioButton rd_btn_price_low_high = dialog.findViewById(R.id.rd_btn_price_low_high);
            final RadioButton rd_btn_price_high_low = dialog.findViewById(R.id.rd_btn_price_high_low);
            final RadioButton rd_btn_popularity = dialog.findViewById(R.id.rd_btn_popularity);
            final RadioButton rd_btn_whats_new = dialog.findViewById(R.id.rd_btn_whats_new);
            final RadioButton rd_btn_best_seller = dialog.findViewById(R.id.rd_btn_best_seller);
            final RadioButton rd_btn_rating = dialog.findViewById(R.id.rd_btn_rating);

            switch (sort_value) {
                case "price_low_high":
                    rd_btn_price_low_high.setChecked(true);
                    rd_btn_best_seller.setChecked(false);
                    rd_btn_popularity.setChecked(false);
                    rd_btn_price_high_low.setChecked(false);
                    rd_btn_rating.setChecked(false);
                    rd_btn_whats_new.setChecked(false);
                    break;
                case "price_high_low":
                    rd_btn_price_low_high.setChecked(false);
                    rd_btn_best_seller.setChecked(false);
                    rd_btn_popularity.setChecked(false);
                    rd_btn_price_high_low.setChecked(true);
                    rd_btn_rating.setChecked(false);
                    rd_btn_whats_new.setChecked(false);
                    break;
                case "popularity":
                    rd_btn_price_low_high.setChecked(false);
                    rd_btn_best_seller.setChecked(false);
                    rd_btn_popularity.setChecked(true);
                    rd_btn_price_high_low.setChecked(false);
                    rd_btn_rating.setChecked(false);
                    rd_btn_whats_new.setChecked(false);
                    break;
                case "whats_new":
                    rd_btn_price_low_high.setChecked(false);
                    rd_btn_best_seller.setChecked(false);
                    rd_btn_popularity.setChecked(false);
                    rd_btn_price_high_low.setChecked(false);
                    rd_btn_rating.setChecked(false);
                    rd_btn_whats_new.setChecked(true);
                    break;
                case "best_seller":
                    rd_btn_price_low_high.setChecked(false);
                    rd_btn_best_seller.setChecked(true);
                    rd_btn_popularity.setChecked(false);
                    rd_btn_price_high_low.setChecked(false);
                    rd_btn_rating.setChecked(false);
                    rd_btn_whats_new.setChecked(false);
                    break;
                case "rating":
                    rd_btn_price_low_high.setChecked(false);
                    rd_btn_best_seller.setChecked(false);
                    rd_btn_popularity.setChecked(false);
                    rd_btn_price_high_low.setChecked(false);
                    rd_btn_rating.setChecked(true);
                    rd_btn_whats_new.setChecked(false);
                    break;
            }


            radiobutton_ll_dialog_click(ll_price_low_high, ll_price_high_low, ll_popularity, ll_whats_new, ll_best_seller, ll_rating, rd_btn_price_low_high, rd_btn_price_high_low, rd_btn_popularity,
                    rd_btn_whats_new, rd_btn_best_seller, rd_btn_rating);
            ImageView iv_dismiss_dialog = dialog.findViewById(R.id.iv_dismiss_dialog);
            iv_dismiss_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!Share.sort_value.equalsIgnoreCase("")) {
                        sort_value = Share.sort_value;
                    }
                    dialog.dismiss();
                }
            });

            tv_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Share.sort_value = sort_value;
                    get_other_category(sort_value);
                }
            });

            tv_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    sort_value = "whats_new";
                    Share.sort_value = sort_value;
                    get_other_category("whats_new");
                }
            });
        } else if (id == R.id.cd_filter) {
            if (mall_filter_activity.mactivity != null) {
                finish();
            } else {
                finish();
                Intent intent = new Intent(mall_category_activity.this, mall_filter_activity.class);
                intent.putExtra("category_id", "" + getIntent().getStringExtra("category_id"));
                startActivity(intent);
            }
        }
    }

    private void radiobutton_ll_dialog_click(LinearLayout ll_price_low_high, LinearLayout ll_price_high_low, LinearLayout ll_popularity, LinearLayout ll_whats_new, LinearLayout ll_best_seller, LinearLayout ll_rating, final RadioButton rd_btn_price_low_high, final RadioButton rd_btn_price_high_low, final RadioButton rd_btn_popularity, final RadioButton rd_btn_whats_new, final RadioButton rd_btn_best_seller, final RadioButton rd_btn_rating) {
        ll_price_low_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "price_low_high";
                rd_btn_price_low_high.setChecked(true);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(false);
            }
        });

        rd_btn_price_low_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "price_low_high";
                rd_btn_price_low_high.setChecked(true);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(false);
            }
        });

        ll_price_high_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "price_high_low";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(true);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(false);
            }
        });

        rd_btn_price_high_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "price_high_low";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(true);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(false);
            }
        });

        ll_popularity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "popularity";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(true);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(false);
            }
        });

        rd_btn_popularity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "popularity";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(true);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(false);
            }
        });

        ll_whats_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "whats_new";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(true);
            }
        });

        rd_btn_whats_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "whats_new";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(true);
            }
        });

        ll_best_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "best_seller";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(true);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(false);
            }
        });

        rd_btn_best_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "best_seller";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(true);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(false);
                rd_btn_whats_new.setChecked(false);
            }
        });

        ll_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "rating";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(true);
                rd_btn_whats_new.setChecked(false);
            }
        });

        rd_btn_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_value = "rating";
                rd_btn_price_low_high.setChecked(false);
                rd_btn_best_seller.setChecked(false);
                rd_btn_popularity.setChecked(false);
                rd_btn_price_high_low.setChecked(false);
                rd_btn_rating.setChecked(true);
                rd_btn_whats_new.setChecked(false);
            }
        });
    }

    private void get_other_category(final String sort_value) {
        pd = ProgressDialog.show(this, "", this.getString(R.string.loading), true, false);
        int user_id;
        if (!SharedPrefs.getBoolean(mall_category_activity.this, Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(mall_category_activity.this, Share.key_ + RegReq.id));
        }
        String url;
        if (Share.base_filter_url.equalsIgnoreCase("")) {
            url = UtilsKt.getBaseUrl(mall_category_activity.this) + "mall_products?user_id=" + user_id + "&category_id=" + getIntent().getStringExtra("category_id") + "&sort=" + sort_value + "&country_code=" + Share.countryCodeValue + "&variants=1";
        } else {
            if (!Share.base_filter_url.contains("&sort=" + Share.sort_value)) {
                url = Share.base_filter_url + "&sort=" + sort_value;
                Log.e("WITHOUTSORTVALUE", "get_other_category: " + url);
            } else {
                url = Share.base_filter_url.replace(Share.sort_value, sort_value);
                Log.e("WITH", "get_other_category: " + url);
            }
        }
        Log.e("FILTERURL", "filter: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        mall_main_category_response_click_data mall_main_category_response_click_data = gson.fromJson(response, mall_main_category_response_click_data.class);
                        if (mall_main_category_response_click_data.getResponseCode().equalsIgnoreCase("1")) {
                            pd.dismiss();
                            Share.subresponse_data.clear();
                            Share.subresponse_data.addAll(mall_main_category_response_click_data.getData());
                            mAdapter.notifyDataSetChanged();

                        } else {
                            pd.dismiss();
                            Toast.makeText(mall_category_activity.this, mall_main_category_response_click_data.getResponseMessage(), Toast.LENGTH_SHORT).show();
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
                                AlertDialog alertDialog = new AlertDialog.Builder(mall_category_activity.this).create();
                                alertDialog.setTitle(getString(R.string.time_out));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.connect_time_out));
                                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        get_other_category(sort_value);
                                    }
                                });
                                alertDialog.show();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(mall_category_activity.this).create();
                                alertDialog.setTitle(getString(R.string.internet_connection));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.slow_connect));
                                alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        get_other_category(sort_value);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (Share.filtered_response.size() > 0) {
            Share.subresponse_data.clear();
            Share.subresponse_data.addAll(Share.filtered_response);
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Share.sort_value = "whats_new";
        if (mall_filter_activity.mactivity != null) {
            mall_filter_activity.mactivity.finish();
            mall_filter_activity.mactivity = null;
            finish();
        } else {
            mall_filter_activity.mactivity = null;
            finish();
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f / spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
