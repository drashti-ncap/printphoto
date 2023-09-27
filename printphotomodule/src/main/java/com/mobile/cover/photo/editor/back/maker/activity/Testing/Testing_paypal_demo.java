package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Testing_paypal_demo extends AppCompatActivity {

    private static final int CHROME_CUSTOM_TAB_REQUEST_CODE = 100;
    Button btn_drop_in;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_paypal_demo);
        btn_drop_in = findViewById(R.id.btn_drop_in);
        btn_drop_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypal();
            }
        });
    }


    private void paypal() {

            pd = ProgressDialog.show(Testing_paypal_demo.this, "", getString(R.string.loading), true, false);

            Integer userid;

//        APIService api = RetrofitClient.getApiService();
            Log.e("PARAMTERS", "check_cod: ======>" + Share.address_id);
            Log.e("PARAMTERS", "check_cod: ======>" + getIntent().getStringExtra("cart_id"));

        APIService apiService = new MainApiClient(Testing_paypal_demo.this).getApiInterface();
            Call<paypal_model> call = apiService.paypal("ORDER1560312177454");

            call.enqueue(new Callback<paypal_model>() {
                public static final String TAG = "test";

                @Override
                public void onResponse(Call<paypal_model> call, Response<paypal_model> response) {
                    Log.e(TAG, "onResponse: " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        pd.dismiss();
                        if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.intent.setData(Uri.parse(response.body().getData().getRedirectUrl()));
                            startActivityForResult(customTabsIntent.intent, CHROME_CUSTOM_TAB_REQUEST_CODE);
                        } else {
                            Toast.makeText(Testing_paypal_demo.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        pd.dismiss();
                        Toast.makeText(Testing_paypal_demo.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<paypal_model> call, Throwable t) {
                    pd.dismiss();
                    Log.e(TAG, "onFailure: ======>" + t);
                    Log.e(TAG, "onFailure: ======>" + t.getMessage());
                    Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                    if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(Testing_paypal_demo.this).create();
                        alertDialog.setTitle(getString(R.string.time_out));
                        alertDialog.setMessage(getString(R.string.connect_time_out));
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                paypal();

                            }
                        });
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Testing_paypal_demo.this).create();
                        alertDialog.setTitle(getString(R.string.internet_connection));
                        alertDialog.setMessage(getString(R.string.slow_connect));
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                paypal();
                            }
                        });
                        alertDialog.show();
                    }
                }
            });


    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
