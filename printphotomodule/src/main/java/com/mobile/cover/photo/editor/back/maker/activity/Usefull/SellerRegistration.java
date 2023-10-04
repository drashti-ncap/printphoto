package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.SellerData;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.Responsedata;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;

import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerRegistration extends PrintPhotoBaseActivity implements View.OnClickListener {

    ArrayList<String> spinnerArray = new ArrayList<String>();

    Spinner sp_type;
    EditText ed_adhaar, ed_mob_no, ed_email, ed_name;
    TextView btn_register;
    String name, email, mobile, adhaar, type;
    LinearLayout id_ll_register;
    ImageView id_back, iv_reset;
    //  ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        initviews();
        initlistener();
    }

    private void initviews() {
        ed_adhaar = findViewById(R.id.ed_adhaar);
        ed_mob_no = findViewById(R.id.ed_mob_no);
        ed_email = findViewById(R.id.ed_email);
        ed_name = findViewById(R.id.ed_name);

        id_back = findViewById(R.id.id_back);
        iv_reset = findViewById(R.id.iv_reset);
        iv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_adhaar.setText("");
                ed_email.setText("");
            }
        });
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sp_type = findViewById(R.id.sp_type);
        spinnerArray.add(getString(R.string.merchant));
        spinnerArray.add(getString(R.string.business));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, spinnerArray);
        sp_type.setAdapter(adapter);

        btn_register = findViewById(R.id.btn_register);
    }

    private void initlistener() {
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_register) {
            name = ed_name.getText().toString();
            email = ed_email.getText().toString();
            mobile = ed_mob_no.getText().toString();
            adhaar = ed_adhaar.getText().toString();
            type = sp_type.getSelectedItem().toString();
            if (ed_email.getText().toString().equalsIgnoreCase("")) {
                ed_email.setError(getString(R.string.pls_paytm_num));
                ed_email.requestFocus();
            } else if (ed_email.getText().toString().contains(" ")) {
                ed_email.setError(getString(R.string.pls_paytm_num));
                ed_email.requestFocus();
            } else if (ed_adhaar.getText().toString().equalsIgnoreCase("")) {
                ed_adhaar.setError(getString(R.string.enter_adhar));
                ed_adhaar.requestFocus();
            } else if (ed_adhaar.getText().toString().contains(" ")) {
                ed_adhaar.setError(getString(R.string.enter_adhar));
                ed_adhaar.requestFocus();
            } else {


                //pd = ProgressDialog.show(SellerRegistration.this, "", getString(R.string.loading), true, false);
                showProgressDialog(this);
                if (adhaar.length() < 12) {
                    Toast.makeText(this, getString(R.string.provide_correct_adhar), Toast.LENGTH_SHORT).show();
                   // pd.dismiss();
                    hideProgressDialog();
                } else {
                    postdata();
                }

            }
        }
    }

    private void postdata() {

        APIService api = new MainApiClient(SellerRegistration.this).getApiInterface();

        RequestBody rqname = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPrefs.getString(getApplicationContext(), Share.key_ + RegReq.name));
        RequestBody rqemail = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody rqmobile = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPrefs.getString(getApplicationContext(), Share.key_ + RegReq.mobile_1));
        RequestBody rqadhaar = RequestBody.create(MediaType.parse("multipart/form-data"), adhaar);
        RequestBody rqtype = RequestBody.create(MediaType.parse("multipart/form-data"), type);
        RequestBody uid = RequestBody.create(MediaType.parse("multipart/form-data"), SharedPrefs.getString(getApplicationContext(), Share.key_ + RegReq.id));
        RequestBody ln = RequestBody.create(MediaType.parse("multipart/form-data"), Locale.getDefault().getLanguage());

//        Log.e("RQ", "postdata:==> "+name);
        Log.e("RQ", "postdata:==> " + email);
//        Log.e("RQ", "postdata:==> "+mobile);
        Log.e("RQ", "postdata:==> " + adhaar);
        Log.e("RQ", "postdata:==> " + type);

        Call<Responsedata> call = api.getResponse(rqname, rqemail, rqmobile, rqadhaar, rqtype, uid, ln);

        call.enqueue(new Callback<Responsedata>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<Responsedata> call, Response<Responsedata> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    Responsedata responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        Log.e("SUCCESS", "onSUCCESS: ");
                        Toast.makeText(SellerRegistration.this, getString(R.string.registration_successfull), Toast.LENGTH_SHORT).show();
                        SellerData seller_data = responseData.getSeller_data();
                        SharedPrefs.save(getApplicationContext(), SharedPrefs.isapproved, seller_data.getis_approve());
                        SharedPrefs.save(getApplicationContext(), SharedPrefs.Sellerid, String.valueOf(seller_data.getId()));
                        Log.e(TAG, "onResponse:===> " + seller_data.getis_approve());
                        Log.e("SELLER_ID", "onResponse:===> " + seller_data.getId());
                        hideProgressDialog();
                        //pd.dismiss();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SellerRegistration.this);
                        alertDialog.setTitle(getString(R.string.seller_registration_succes));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(getString(R.string.seller_account_created_success));
                        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });

                        alertDialog.create().show();


                    } else if (responseData.getResponseCode().equalsIgnoreCase("0")) {
                        //pd.dismiss();
                        hideProgressDialog();
                        Toast.makeText(SellerRegistration.this, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SellerRegistration.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        //pd.dismiss();
                        hideProgressDialog();
                    }

                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(SellerRegistration.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Responsedata> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(SellerRegistration.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            postdata();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SellerRegistration.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            postdata();
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
