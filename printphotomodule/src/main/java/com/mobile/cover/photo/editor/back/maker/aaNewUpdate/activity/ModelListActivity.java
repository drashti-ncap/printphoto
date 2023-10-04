package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter.ModelAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;
import com.mobile.cover.photo.editor.back.maker.model.sub_category_model_details;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelListActivity extends PrintPhotoBaseActivity {
    public static Activity activity;
    public static TextView title, tv_no_fnd;
   // ProgressDialog pd;
    RecyclerView rv_mug_model;
    ModelAdapter mAdapter;
    ImageView id_back, id_cart_display;
    EditText ed_search;
    Button btn_count;
    private List<model_details_data> sqlist = new ArrayList<>();

    // ToDo: Changes mae by Jignesh Patel
    private String titleName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mug_model_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        activity = ModelListActivity.this;
        Share.notification_category_id = 0;
        Share.notification_category_name = "";
        Log.e("PRODUCT_ID", "onCreate: " + DataHelperKt.getProductId(activity));
        initviews();
        load_data();
    }

    private void initviews() {

        try {
            rv_mug_model = findViewById(R.id.rv_mug_model);
            tv_no_fnd = findViewById(R.id.tv_no_fnd);
            ed_search = findViewById(R.id.ed_search);
            if (Share.brand_name != null || !Share.brand_name.equalsIgnoreCase("")) {
                ed_search.setHint(getString(R.string.search_for) + " " + Share.brand_name.toLowerCase() + "..");
            }

            id_cart_display = findViewById(R.id.id_cart_display);
            id_cart_display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    HomeMainActivity.cart_select = true;
                }
            });

            btn_count = findViewById(R.id.btn_count);
            if (SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT) == 0) {
                btn_count.setVisibility(View.GONE);
            } else {
                btn_count.setVisibility(View.VISIBLE);
                btn_count.setText("" + SharedPrefs.getInt(getApplicationContext(), SharedPrefs.CART_COUNT));
            }
            id_back = findViewById(R.id.id_back);
            title = findViewById(R.id.title);

            // ToDo: Changes mae by Jignesh Patel
            titleName = getIntent().getStringExtra("name");
            if (titleName != null) {
                titleName = UtilsKt.truncate(titleName, 25);
                title.setText(titleName);
            }

            id_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    HomeMainActivity.selected = 0;
                }
            });
            sqlist = new ArrayList<>();
            mAdapter = new ModelAdapter(ModelListActivity.this, sqlist);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv_mug_model.setLayoutManager(mLayoutManager);
            rv_mug_model.setItemAnimator(new DefaultItemAnimator());
            rv_mug_model.setAdapter(mAdapter);

            rv_mug_model.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return false;
                }
            });

            ed_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    sqlist = mAdapter.filter(charSequence.toString());

                    // ToDo: Changes mae by Jignesh Patel
                    FBEventsKt.logSearchedEvent(activity, titleName, charSequence.toString(), true);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Share.login_back) {
            finish();
        } else {
            ed_search.setText("");
            if (mAdapter != null)
                mAdapter.notifyDataSetChanged();
        }

        hideKeyBoard(ed_search, activity);
    }

    public void hideKeyBoard(View view, Activity mActivity) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void load_data() {


        sqlist.clear();
        Share.subDataArrayList_category_multiple_category.clear();
//        if (pd != null) {
//            pd.dismiss();
//        }
        hideProgressDialog();
//        pd = ProgressDialog.show(ModelListActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(ModelListActivity.this);

        int user_id;
        if (!SharedPrefs.getBoolean(activity, Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(activity, Share.key_ + RegReq.id));
        }

        APIService apiService = new MainApiClient(activity).getApiInterface();

        Call<sub_category_model_details> call = apiService.getProducts("" + Share.category_id, Share.countryCodeValue, String.valueOf(user_id), Locale.getDefault().getLanguage());
        call.enqueue(new Callback<sub_category_model_details>() {
            @Override
            public void onResponse(Call<sub_category_model_details> call, Response<sub_category_model_details> response) {
                if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                    Share.isinternational = response.body().getIs_international();

                    sqlist.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();

                    if (sqlist.size() <= 0) {
                        tv_no_fnd.setVisibility(View.VISIBLE);
                    } else {
                        tv_no_fnd.setVisibility(View.GONE);
                    }

                    Share.symbol = response.body().getCurrency_symbol();
                    Share.sub_category_data_array_list = sqlist;
                    Share.subDataArrayList_category_multiple_category.addAll(sqlist);
//                    if (pd != null) {
//                        pd.dismiss();
//                    }
                    hideProgressDialog();
                } else {
//                    pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(getApplicationContext(), response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<sub_category_model_details> call, Throwable t) {
                t.printStackTrace();
                //pd.dismiss();
                hideProgressDialog();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ModelListActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            load_data();
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ModelListActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            load_data();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HomeMainActivity.selected = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
