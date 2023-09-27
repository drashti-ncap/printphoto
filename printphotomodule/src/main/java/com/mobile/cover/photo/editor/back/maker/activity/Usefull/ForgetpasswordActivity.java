package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.numberverify;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetpasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText ed_new_pass, ed_cn_new_pass;
    Button btn_cn;
    ProgressDialog pd;
    ImageView id_back, id_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword_activity);
        initviews();
        initlistener();
    }

    private void initviews() {
        ed_new_pass = findViewById(R.id.ed_new_pass);
        ed_cn_new_pass = findViewById(R.id.ed_cn_new_pass);
        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_cn = findViewById(R.id.btn_cn);
        id_reset = findViewById(R.id.id_reset);
    }

    private void initlistener() {
        btn_cn.setOnClickListener(this);
        id_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_cn) {
            if (ed_new_pass.getText().toString().length() < 6) {
                Toast.makeText(this, "Must be more than 6 digit", Toast.LENGTH_SHORT).show();
            } else {
                if (ed_new_pass.getText().toString().equalsIgnoreCase(ed_cn_new_pass.getText().toString())) {
                    if (ed_new_pass.getText().toString().equalsIgnoreCase("") || ed_new_pass.getText().toString().equalsIgnoreCase(" ") || ed_new_pass.getText().toString().startsWith(" ")) {
                        Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                    } else {
                        verify(ed_new_pass.getText().toString(), getIntent().getStringExtra("mobile"));
                    }
                } else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (v == id_reset) {
            ed_cn_new_pass.setText("");
            ed_new_pass.setText("");
        }
    }

    private void verify(final String password, String number) {


        pd = ProgressDialog.show(ForgetpasswordActivity.this, "", getString(R.string.loading), true, false);

        APIService api = new MainApiClient(ForgetpasswordActivity.this).getApiInterface();


        Log.e("PASSWORD", "verify: ==========?" + password + "///=======>///" + number);
        Call<numberverify> call = api.changepswd(password, number, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<numberverify>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<numberverify> call, Response<numberverify> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    numberverify responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        Toast.makeText(ForgetpasswordActivity.this, getString(R.string.pass_change_successfully), Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (responseData.getResponseCode().equalsIgnoreCase("0")) {
                        Toast.makeText(ForgetpasswordActivity.this, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    pd.dismiss();
                } else {
                    pd.dismiss();
                    Toast.makeText(ForgetpasswordActivity.this, "Error==>2", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<numberverify> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ForgetpasswordActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            verify(ed_new_pass.getText().toString(), getIntent().getStringExtra("mobile"));
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ForgetpasswordActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            verify(ed_new_pass.getText().toString(), getIntent().getStringExtra("mobile"));
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


}
