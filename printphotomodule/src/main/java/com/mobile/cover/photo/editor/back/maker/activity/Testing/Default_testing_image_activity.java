package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Testing_adapter.test_default_image_adapter;
import com.mobile.cover.photo.editor.back.maker.model.testing_model_class;

import java.util.ArrayList;
import java.util.List;

public class Default_testing_image_activity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Activity activity;
    List<testing_model_class> sqlist = new ArrayList<>();
    RecyclerView rv_list;
    test_default_image_adapter default_image_adapter;
    Button btn_1, btn_2, btn_3;
    LinearLayout ll_test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_testing_image_activity);
        activity = Default_testing_image_activity.this;
        rv_list = findViewById(R.id.rv_list);
        ll_test = findViewById(R.id.ll_test);
        ll_test.setVisibility(View.GONE);
//        btn_1 = findViewById(R.id.btn_1);
//        btn_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Share.mobile_type = 1;
//                getData();
//            }
//        });
//        btn_2 = findViewById(R.id.btn_2);
//        btn_2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Share.mobile_type = 2;
//                getData();
//            }
//        });
//        btn_3 = findViewById(R.id.btn_3);
//        btn_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Share.mobile_type = 3;
//                getData();
//            }
//        });
        Glide.with(activity).asBitmap().load(Share.test_image).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Share.test_bitmap = resource;

            }
        });
//        getData();

        default_image_adapter = new test_default_image_adapter(activity, sqlist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(default_image_adapter);
    }

//    private void getData() {
//        progressDialog = new ProgressDialog(activity);
//        progressDialog.setMessage(activity.getResources().getString(R.string.pleasewait));
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        sqlist.clear();
//        MainApiClient mainApiClient = new MainApiClient();
//        MainApiInterface apiService = mainApiClient.getClient().create(MainApiInterface.class);
//
//        Call<SubMainModel> subMainModelCall = apiService.getSubMainCatagaries("1");
//        subMainModelCall.enqueue(new Callback<SubMainModel>() {
//            @Override
//            public void onResponse(Call<SubMainModel> call, Response<SubMainModel> response) {
//                List<SubData> datumList = response.body().getSubDataArrayList();
//                for (int i = 0; i < datumList.get(0).getSubDataModelDetailsArrayList().size(); i++) {
//                    if (datumList.get(0).getSubDataModelDetailsArrayList().get(i).getImageType() == 1) {
//                        Log.e("TYPEEEE", "onResponse: " + Share.mobile_type + "=====>" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getImageType());
//                        testing_model_class testing_model_class = new testing_model_class("" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).
//                                getModelId(), "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getModalName(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getCompanyId(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getNModalImage(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getWidth(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getHeight()
//                                , "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getNMaskImage(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getBannerImage(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getShipping(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getDiscount(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getDescription(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getSize(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getPrice(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getImageType(),
//                                "" + datumList.get(0).getSubDataModelDetailsArrayList().get(i).getDummyPrice());
//                        sqlist.add(testing_model_class);
//                    }
//                }
//                default_image_adapter.notifyDataSetChanged();
//                Log.e("SIZE", "onResponse: " + sqlist.size());
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<SubMainModel> call, Throwable t) {
//                Log.d("succus", "Fail==>" + t.toString());
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//
//                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
//                    alertDialog.setTitle(getString(R.string.time_out));
//                    alertDialog.setMessage(getString(R.string.connect_time_out));
//                    alertDialog.setCancelable(false);
//                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            getData();
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
//                            getData();
//
//                        }
//                    });
//                    alertDialog.show();
//                }
//            }
//        });
//    }

}
