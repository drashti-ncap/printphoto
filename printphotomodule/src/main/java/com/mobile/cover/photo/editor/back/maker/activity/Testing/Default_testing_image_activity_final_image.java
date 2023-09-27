package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Testing_adapter.test_new_default_image_adapter;
import com.mobile.cover.photo.editor.back.maker.model.get_images;

import java.util.ArrayList;
import java.util.List;

public class Default_testing_image_activity_final_image extends AppCompatActivity {

    ProgressDialog pd;
    Activity activity;
    List<get_images> sqlist = new ArrayList<>();
    RecyclerView rv_list;
    test_new_default_image_adapter default_image_adapter;
    Button btn_1, btn_2, btn_3;
    LinearLayout ll_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_testing_image_activity);
        activity = Default_testing_image_activity_final_image.this;
        rv_list = findViewById(R.id.rv_list);
        ll_test = findViewById(R.id.ll_test);
        ll_test.setVisibility(View.VISIBLE);
        btn_1 = findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.mobile_type = 1;
//                getMainData();
            }
        });
        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.mobile_type = 2;
//                getMainData();
            }
        });
        btn_3 = findViewById(R.id.btn_3);
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.mobile_type = 3;
//                getMainData();
            }
        });
//        default_image_adapter = new test_new_default_image_adapter(activity, sqlist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(default_image_adapter);
    }

//    private void getMainData() {
//        sqlist.clear();
//        pd = ProgressDialog.show(activity, "", "Processing...", true, false);
//        pd.show();
//        APIService api = RetrofitClient.getApiService();
//
//
//        Call<customimage_response> call = api.getcaseimages();
//
//
//        call.enqueue(new Callback<customimage_response>() {
//            @Override
//            public void onResponse(Call<customimage_response> call, retrofit2.Response<customimage_response> response) {
//                customimage_response customimage_response = response.body();
//                if (customimage_response.getResponseCode().equalsIgnoreCase("1")) {
//                    pd.dismiss();
//
//                    List<custom_data_response> datumList = customimage_response.getData();
//                    for (custom_data_response datum : datumList) {
//                        List<case_images_data> imagedata = datum.getCases();
//                        for (case_images_data data:imagedata){
//                                get_images get_images=new get_images(""+data.getCategoryId(),""+data.getName(),""+data.getImage1(),
//                                        ""+data.getImage2(),""+data.getImage3(),""+data.getSelling());
//                                sqlist.add(get_images);
//                        }
//                    }
//                    default_image_adapter.notifyDataSetChanged();
//                    Log.e("SIZE", "onResponse: "+sqlist.size());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<customimage_response> call, Throwable t) {
//                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
//                    alertDialog.setTitle(getString(R.string.time_out));
//                    alertDialog.setCancelable(false);
//                    alertDialog.setMessage(getString(R.string.connect_time_out));
//                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            pd.dismiss();
//                            getMainData();
//
//                        }
//                    });
//                    alertDialog.show();
//                } else {
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
//                    alertDialog.setTitle(getString(R.string.internet_connection));
//                    alertDialog.setCancelable(false);
//                    alertDialog.setMessage(getString(R.string.slow_connect));
//                    alertDialog.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            pd.dismiss();
//                            getMainData();
//                        }
//                    });
//                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            pd.dismiss();
//                        }
//                    });
//                    alertDialog.show();
//                }
//
//
//            }
//        });
//    }

}
