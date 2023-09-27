package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mall_main_category_response_click_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.mall_wishlist_adapter;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mall_wishlist extends AppCompatActivity implements View.OnClickListener {

    public static TextView title, tv_no_fnd;
    public static Activity activity;
    RecyclerView rv_wish_list;
    ImageView id_back;
    mall_wishlist_adapter mAdapter;
    ProgressDialog pd;

    AlertDialog alertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_wishlist);
        activity = Mall_wishlist.this;
        findviews();
        initlistener();
        get_wishlist();
    }

    private void findviews() {
        rv_wish_list = findViewById(R.id.rv_wish_list);
        title = findViewById(R.id.title);
        tv_no_fnd = findViewById(R.id.tv_no_fnd);
        title.setText("Wishlist(" + Share.wish_list.size() + ")");
        mAdapter = new mall_wishlist_adapter(Mall_wishlist.this, Share.wish_list);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(Mall_wishlist.this, 2);
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(2, 12, true);
        rv_wish_list.setLayoutManager(mLayoutManager);
        rv_wish_list.setItemAnimator(new DefaultItemAnimator());
        rv_wish_list.addItemDecoration(gridSpacingItemDecoration);
        rv_wish_list.setAdapter(mAdapter);
        id_back = findViewById(R.id.id_back);
    }

    private void initlistener() {
        id_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_back) {
            finish();
        }
    }

    private void get_wishlist() {


        int user_id;
        if (!SharedPrefs.getBoolean(Mall_wishlist.this, Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(Mall_wishlist.this, Share.key_ + RegReq.id));
        }

        pd = ProgressDialog.show(Mall_wishlist.this, "", getString(R.string.loading), true, false);

        APIService apiService = new MainApiClient(Mall_wishlist.this).getApiInterface();
        Call<mall_main_category_response_click_data> call = apiService.wishlist(user_id, Share.countryCodeValue, "1", Locale.getDefault().getLanguage());

        call.enqueue(new Callback<mall_main_category_response_click_data>() {
            @Override
            public void onResponse(Call<mall_main_category_response_click_data> call, Response<mall_main_category_response_click_data> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        Share.isinternational = response.body().getIs_international();

                        if (response.body().getData().size() == 0) {
                            tv_no_fnd.setVisibility(View.VISIBLE);
                            rv_wish_list.setVisibility(View.GONE);
                        } else {
                            tv_no_fnd.setVisibility(View.GONE);
                            rv_wish_list.setVisibility(View.VISIBLE);
                        }
                        Share.wish_list.clear();
                        Share.symbol = response.body().getCurrency_symbol();
                        Share.wish_list.addAll(response.body().getData());
                        title.setText("Wishlist(" + Share.wish_list.size() + ")");
                        mAdapter.notifyDataSetChanged();
                    } else {
                        title.setText("Wishlist(" + "0" + ")");
                        Share.wish_list.clear();
                        mAdapter.notifyDataSetChanged();

                        tv_no_fnd.setVisibility(View.VISIBLE);
                        rv_wish_list.setVisibility(View.GONE);
                        Toast.makeText(Mall_wishlist.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Share.wish_list.clear();
                    mAdapter.notifyDataSetChanged();
                    title.setText("Wishlist(" + "0" + ")");
                    tv_no_fnd.setVisibility(View.VISIBLE);
                    rv_wish_list.setVisibility(View.GONE);
                    Toast.makeText(Mall_wishlist.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<mall_main_category_response_click_data> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Mall_wishlist.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_wishlist();
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Mall_wishlist.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_wishlist();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            rv_wish_list.setVisibility(View.GONE);
            get_wishlist();
        }
    }
}
