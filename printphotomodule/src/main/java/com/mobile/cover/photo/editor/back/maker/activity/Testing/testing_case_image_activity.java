package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.test_image_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.test_image_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.adapter.Testing_adapter.test_new_default_image_adapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.model.getdefault_images;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class testing_case_image_activity extends PrintPhotoBaseActivity {


    RecyclerView rv_recycler_data;
   // ProgressDialog pd;
    List<getdefault_images> sqlist = new ArrayList<>();
    test_new_default_image_adapter test_new_deafault_image_adapter_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_case_image_activity);
        rv_recycler_data = findViewById(R.id.rv_recycler_data);
        test_new_deafault_image_adapter_new = new test_new_default_image_adapter(testing_case_image_activity.this, sqlist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rv_recycler_data.setLayoutManager(mLayoutManager);
        rv_recycler_data.setItemAnimator(new DefaultItemAnimator());
        rv_recycler_data.setAdapter(test_new_deafault_image_adapter_new);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMainData();
    }

    private void getMainData() {

        sqlist.clear();
        //pd = ProgressDialog.show(testing_case_image_activity.this, "", "Processing...", true, false);
        showProgressDialog(testing_case_image_activity.this);
        APIService api = new MainApiClient(testing_case_image_activity.this).getApiInterface();


        Call<test_image_response> call = api.gettestcaseimages();


        call.enqueue(new Callback<test_image_response>() {
            @Override
            public void onResponse(Call<test_image_response> call, retrofit2.Response<test_image_response> response) {
                test_image_response customimage_response = response.body();
                if (customimage_response.getSuccess()) {
                    //pd.dismiss();
                    hideProgressDialog();

                    List<test_image_response_data> datumList = customimage_response.getData();
                    for (test_image_response_data datum : datumList) {
                        getdefault_images getdefault_images = new getdefault_images("" + datum.getId(), "" + datum.getCreatedAt(), "" + datum.getImage(), "" + datum.getImage());
                        sqlist.add(getdefault_images);
                    }
                    test_new_deafault_image_adapter_new.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<test_image_response> call, Throwable t) {
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(testing_case_image_activity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //pd.dismiss();
                            hideProgressDialog();
                            getMainData();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(testing_case_image_activity.this);
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //pd.dismiss();
                            hideProgressDialog();
                            getMainData();
                        }
                    });
                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //pd.dismiss();
                            hideProgressDialog();
                        }
                    });
                    alertDialog.show();
                }


            }
        });
    }

}
