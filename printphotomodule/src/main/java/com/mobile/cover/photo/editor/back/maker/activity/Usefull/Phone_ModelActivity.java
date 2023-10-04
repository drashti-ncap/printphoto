package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.CompanyModelRecyclerAdapter;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;
import com.mobile.cover.photo.editor.back.maker.model.sub_category_model_details;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Phone_ModelActivity extends PrintPhotoBaseActivity {
    public static Activity activity;
    public static TextView title, tv_no_fnd;
  //  ProgressDialog pd;
    RecyclerView rv_mug_model;
    CompanyModelRecyclerAdapter mAdapter;
    ImageView id_back, id_cart_display;
    EditText ed_search;
    Button btn_count;
    private List<model_details_data> sqlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mug_model_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        activity = Phone_ModelActivity.this;
        initviews();
        load_data();
    }

    private void initviews() {
        rv_mug_model = findViewById(R.id.rv_mug_model);
        tv_no_fnd = findViewById(R.id.tv_no_fnd);
        ed_search = findViewById(R.id.ed_search);


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
        title.setText(getIntent().getStringExtra("name"));
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                HomeMainActivity.selected = 0;
            }
        });
        sqlist = new ArrayList<>();
        mAdapter = new CompanyModelRecyclerAdapter(Phone_ModelActivity.this, sqlist);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rv_mug_model.setLayoutManager(mLayoutManager);
        rv_mug_model.setItemAnimator(new DefaultItemAnimator());
        rv_mug_model.setAdapter(mAdapter);

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sqlist = mAdapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ed_search.setText("");
    }

//    private void getdata() {
//        sqlist.clear();
//        Share.subDataArrayList_category_multiple_category.clear();
//        pd = ProgressDialog.show(Model_List_Activity.this, "", getString(R.string.loading), true, false);
//        pd.show();
//
//        APIService api = RetrofitClient.getApiService();
//
//        Call<getmugresponse> call = api.getmugdetails(Share.category_id);
//
//        call.enqueue(new Callback<getmugresponse>() {
//            public static final String TAG = "test";
//
//            @Override
//            public void onResponse(Call<getmugresponse> call, retrofit2.Response<getmugresponse> response) {
//                getmugresponse responseData = response.body();
//                if (responseData.getResponseCode().equalsIgnoreCase("1")) {
//                    List<mugdetail> datumList = responseData.getData();
//                    for (mugdetail datum : datumList) {
//                        getmugModeldata getdata = new getmugModeldata("" + datum.getId(), "" + datum.getGeneralCategoryId(), "" + datum.getModalName(), "" + datum.getCompanyId(), "" + datum.getNModalImage(),
//                                "" + datum.getNMaskImage(), "" + datum.getAvailableStock(), "" + datum.getPrice(), "" + datum.getWidth(), "" + datum.getHeight(), "" + datum.getDisplayWidth(), "" + datum.getDisplayHeight(),
//                                "" + datum.getDummyPrice(), "" + datum.getType(), "" + datum.getDescription(), "" + datum.getNMugImage());
//                        sqlist.add(getdata);
//                        mAdapter.notifyDataSetChanged();
//                    }
//
//                    if (sqlist.size() == 0) {
//                        tv_no_fnd.setVisibility(View.VISIBLE);
//                    } else {
//                        tv_no_fnd.setVisibility(View.GONE);
//                    }
//
//
//                    Share.sub_category_data_array_list = sqlist;
//                    Share.subDataArrayList_category_multiple_category.addAll(sqlist);
//                    pd.dismiss();
//                } else {
//                    pd.dismiss();
//                    Toast.makeText(getApplicationContext(), responseData.getResponseMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<getmugresponse> call, Throwable t) {
//                pd.dismiss();
//                Log.e(TAG, "onFailure: ======>" + t);
//                Log.e(TAG, "onFailure: ======>" + t.getMessage());
//                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
//                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
//                    alertDialog.setTitle(getString(R.string.time_out));
//                    alertDialog.setMessage(getString(R.string.connect_time_out));
//                    alertDialog.setCancelable(false);
//                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            getdata();
//
//                        }
//                    });
//                    alertDialog.show();
//                } else {
//                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
//                    alertDialog.setTitle(getString(R.string.internet_connection));
//                    alertDialog.setMessage(getString(R.string.slow_connect));
//                    alertDialog.setCancelable(false);
//                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            getdata();
//
//                        }
//                    });
//                    alertDialog.show();
//                }
//            }
//        });
//
//    }

    private void load_data() {

        sqlist.clear();
        Share.subDataArrayList_category_multiple_category.clear();
//        pd = ProgressDialog.show(Phone_ModelActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(Phone_ModelActivity.this);
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

                    sqlist.addAll(response.body().getData());
                    mAdapter.notifyDataSetChanged();

                    if (sqlist.size() == 0) {
                        tv_no_fnd.setVisibility(View.VISIBLE);
                    } else {
                        tv_no_fnd.setVisibility(View.GONE);
                    }


                    Share.isinternational = response.body().getIs_international();
                    Share.sub_category_data_array_list = sqlist;
                    Share.subDataArrayList_category_multiple_category.addAll(sqlist);
                    //pd.dismiss();
                    hideProgressDialog();
                } else {
                    //pd.dismiss();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(Phone_ModelActivity.this).create();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(Phone_ModelActivity.this).create();
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
