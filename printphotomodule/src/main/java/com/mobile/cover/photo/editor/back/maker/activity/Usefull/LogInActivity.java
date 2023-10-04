package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.new_numberverify;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.auth.PhoneAuthActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.MallSearchActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.mall_category_activity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.mall_detail_activity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.RegData;
import com.mobile.cover.photo.editor.back.maker.model.RegResponse;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends PrintPhotoBaseActivity {
    public static LogInActivity logInActivity;
    LinearLayout id_ll_register, id_ll_sign_in, id_ll_password, id_ll_mobile_no, id_signIn, id_signOut, id_ll_email_id;
    RelativeLayout id_ll_print_photo;
    ImageView im_printphoto, im_user, im_key, id_iv_forget_password;
//    ProgressDialog pd;
    EditText id_mobile_number, id_password, id_email_id;
    AlertDialog alertDialog;
    ArrayList<String> spinnercodeArray = new ArrayList<String>();
    Integer country_code, lenght, code_position;
    boolean send = false;
    ArrayList<String> spinnercountryArray = new ArrayList<String>();
    ArrayList<String> temp_spinnercodeArray = new ArrayList<String>();
    Spinner sp_country;
    int selected_pos;

    int REQ_REGISTER = 101;

    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);
        logInActivity = this;
        spinnercodeArray.clear();
        spinnercountryArray.clear();
        temp_spinnercodeArray.clear();
        for (int country = 0; country < Share.country_mobile_code.size(); country++) {
            if (country == 0) {
                spinnercodeArray.add("+" + Share.country_mobile_code.get(0).getPhonecode() + " (" + Share.country_mobile_code.get(0).getSortname() + ")" + " " + Share.country_mobile_code.get(0).getName());
            }
            if (Share.country_mobile_code.get(country).getIs_branch() == 1) {
                spinnercodeArray.add("+" + Share.country_mobile_code.get(country).getPhonecode() + " (" + Share.country_mobile_code.get(country).getSortname() + ")" + " " + Share.country_mobile_code.get(country).getName());
            }
            temp_spinnercodeArray.add(Share.country_mobile_code.get(country).getSortname());
            spinnercountryArray.add(" (" + Share.country_mobile_code.get(country).getSortname() + ")" + " " + Share.country_mobile_code.get(country).getName());
        }
        setHeader();
        getDisplaySize();
        findViews();
        setDimens();
        intView();
    }

    private void intView() {

        id_ll_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, RegistrationActivity.class);
                startActivityForResult(intent, REQ_REGISTER);
            }
        });

        id_ll_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_mobile_number.setError(null);
                id_password.setError(null);
                id_email_id.setError(null);


                if (Share.countryCodeValue != null && Share.countryCodeValue.equalsIgnoreCase("IN")) {

                    if (id_mobile_number.getText().toString().trim().length() < 10 || id_mobile_number.getText().toString().trim().equalsIgnoreCase("")) {
                        id_mobile_number.setError(getString(R.string.enter_correct_number));
                        id_mobile_number.requestFocus();
                    } else if (id_password.getText().toString().trim().length() == 0 || id_password.getText().toString().trim().equalsIgnoreCase("")) {
                        id_password.setError(getString(R.string.enter_pass));
                        id_password.requestFocus();
                    } else {
                        signin();
                    }
                } else {
                    for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                        if (Share.countryCodeValue.equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
                            if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                                if (id_mobile_number.getText().toString().trim().length() == 0 || id_mobile_number.getText().toString().trim().equalsIgnoreCase("")) {
                                    id_mobile_number.setError(getString(R.string.enter_correct_number));
                                    id_mobile_number.requestFocus();
                                } else if (id_password.getText().toString().trim().length() == 0 || id_password.getText().toString().trim().equalsIgnoreCase("")) {
                                    id_password.setError(getString(R.string.enter_pass));
                                    id_password.requestFocus();
                                } else {
                                    signin();
                                }
                            } else {
                                if (id_email_id.getText().toString().trim().length() == 0 || id_email_id.getText().toString().trim().equalsIgnoreCase("") || !id_email_id.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                                    id_email_id.setError(getString(R.string.enter_correct_email));
                                    id_email_id.requestFocus();
                                } else if (id_password.getText().toString().trim().length() == 0 || id_password.getText().toString().trim().equalsIgnoreCase("")) {
                                    id_password.setError(getString(R.string.enter_pass));
                                    id_password.requestFocus();
                                } else {
                                    signin();
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    private void signin() {


        String cred;
        if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
            cred = id_mobile_number.getText().toString();
        } else {
            cred = id_email_id.getText().toString();
            for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                    if (SharedPrefs.getString(LogInActivity.this, SharedPrefs.country_code).equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
                        cred = id_mobile_number.getText().toString();
                    }
                }
            }
        }


        callSignInAPI(cred, id_password.getText().toString());
        // new login().execute();


    }

    private void callSignInAPI(String cred, String password) {

//        pd = ProgressDialog.show(LogInActivity.this, "", getString(R.string.loading), true, false);
//        pd.show();
        showProgressDialog(LogInActivity.this);

        String androidId = Share.firebaseToken;
        Log.e("androidId", "==>" + androidId);

        APIService apiService = new MainApiClient(LogInActivity.this).getApiInterface();
        Call<RegResponse> regResponseCall = apiService.getRegResponseLogin(cred, password, androidId, "android", TimeZone.getDefault().getID(), Locale.getDefault().getLanguage());
        regResponseCall.enqueue(new Callback<RegResponse>() {
            @Override
            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
                dismissDialog();
                if (response.body() != null) {
                    Log.e("androidId", "==>" + response.body().getResponseCode());

                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        SharedPrefs.save(getApplicationContext(), SharedPrefs.CART_COUNT, response.body().getCart_count());
                        HomeMainActivity.tv_nudge_cart_count.setText("" + SharedPrefs.getInt(LogInActivity.this, SharedPrefs.CART_COUNT));


                        if (response.body().getData() == null) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogInActivity.this);
                            alertDialog.setTitle(getString(R.string.log_in));
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(response.body().getResponseMessage());
                            alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            alertDialog.create().show();
                        } else {
                            SharedPrefs.savePref(LogInActivity.this, Share.key_reg_suc, true);
                            RegData regData = response.body().getData();
                            Share.currency_id = response.body().getData().getCurrencyId();
                            Share.isinternational = regData.getIs_international();
                            if (regData != null) {
                                SharedPrefs.save(LogInActivity.this, Share.key_ + RegReq.name, regData.getName());
                                SharedPrefs.save(LogInActivity.this, Share.key_ + RegReq.email, regData.getEmail());
                                SharedPrefs.save(LogInActivity.this, Share.key_ + RegReq.mobile_1, regData.getMobile_1());
                                SharedPrefs.save(LogInActivity.this, Share.key_ + RegReq.id, regData.getId());
                                SharedPrefs.save(LogInActivity.this, SharedPrefs.currency_id, "" + regData.getCurrencyId());
                                SharedPrefs.save(LogInActivity.this, SharedPrefs.country_id, regData.getCountry_id());
                                SharedPrefs.save(LogInActivity.this, SharedPrefs.country_name, regData.getCountry_name());
//                                SharedPrefs.save(LogInActivity.this, SharedPrefs.last_country_code, SharedPrefs.getString(LogInActivity.this, SharedPrefs.country_code));
                                SharedPrefs.save(LogInActivity.this, SharedPrefs.country_code, regData.getCountry_code());
                                Share.countryCodeValue = regData.getCountry_code();
                                Log.e("userId", "LogIn==>" + regData.getId());
                                SharedPrefs.save(LogInActivity.this, SharedPrefs.uid, regData.getId());
                                SharedPrefs.save(LogInActivity.this, SharedPrefs.Sellerid, regData.getseller_id());
                                SharedPrefs.save(LogInActivity.this, SharedPrefs.isapproved, regData.getis_approve());
                                SharedPrefs.save(LogInActivity.this, SharedPrefs.SELLER, regData.getis_approve());
                                SharedPrefs.save(LogInActivity.this, SharedPrefs.TOKEN, regData.getToken());
                            }
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }


                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LogInActivity.this);
                        alertDialog.setTitle(getString(R.string.log_in));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(response.body().getResponseMessage());
                        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        alertDialog.create().show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
                    alertDialog.setTitle(getString(R.string.log_in));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.something_went_wrong_try_agaian));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    alertDialog.show();
                }


            }

            @Override
            public void onFailure(Call<RegResponse> call, Throwable t) {
                dismissDialog();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            signin();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            signin();

                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    private void setDimens() {
        int height = Share.screenHeight / 10;
        id_ll_register.getLayoutParams().height = height;
        id_ll_sign_in.getLayoutParams().height = height;
    }

    private void findViews() {
        id_ll_print_photo = findViewById(R.id.id_ll_print_photo);
        sp_country = findViewById(R.id.sp_country);
        ArrayAdapter<String> code_adapter = new ArrayAdapter<String>(LogInActivity.this, R.layout.simple_spinner_print_item, spinnercountryArray);
        sp_country.setAdapter(code_adapter);
        for (int i = 0; i < temp_spinnercodeArray.size(); i++) {
            if (temp_spinnercodeArray.get(i).equalsIgnoreCase(SharedPrefs.getString(LogInActivity.this, SharedPrefs.country_code))) {
                sp_country.setSelection(i);
            }
        }
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_pos = position;
                for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                    if (i == selected_pos) {
                        Share.countryCodeValue = Share.country_mobile_code.get(i).getSortname();
                        SharedPrefs.save(LogInActivity.this, SharedPrefs.last_country_code, SharedPrefs.getString(LogInActivity.this, SharedPrefs.country_code));
                        Share.isregistration = true;
                        SharedPrefs.save(LogInActivity.this, SharedPrefs.country_code, Share.country_mobile_code.get(i).getSortname());
                    }

                    id_email_id.setError(null);
                    id_mobile_number.setError(null);
                    id_password.setError(null);

                    if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                        id_ll_mobile_no.setVisibility(View.VISIBLE);
                        id_ll_email_id.setVisibility(View.GONE);
                        UtilsKt.setEditTextMaxLength(id_mobile_number, 10);
                    } else {

                        for (int j = 0; j < Share.country_mobile_code.size(); j++) {
                            if (Share.countryCodeValue.equalsIgnoreCase(Share.country_mobile_code.get(j).getSortname())) {
                                if (Share.country_mobile_code.get(j).getIs_branch() == 1) {
                                    id_ll_mobile_no.setVisibility(View.VISIBLE);
                                    id_ll_email_id.setVisibility(View.GONE);
                                    UtilsKt.setEditTextMaxLength(id_mobile_number, 9);
                                } else {
                                    id_ll_mobile_no.setVisibility(View.GONE);
                                    id_ll_email_id.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        id_ll_register = findViewById(R.id.id_ll_register);
        id_ll_sign_in = findViewById(R.id.id_ll_sign_in);
        id_iv_forget_password = findViewById(R.id.id_iv_forget_password);
        id_iv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LogInActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_getmobile);
                Window window = dialog.getWindow();
                window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
                dialog.show();
                final EditText tvdone = dialog.findViewById(R.id.btn_push);
                final EditText ed_email = dialog.findViewById(R.id.ed_email);
                final LinearLayout below_mail = dialog.findViewById(R.id.below_mail);
                final LinearLayout above = dialog.findViewById(R.id.above);
                final Spinner sp_code = dialog.findViewById(R.id.sp_code);

                ArrayAdapter<String> code_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, spinnercodeArray);
                sp_code.setAdapter(code_adapter);


                if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                    send = false;
                    above.setVisibility(View.VISIBLE);
                    below_mail.setVisibility(View.GONE);
                    sp_code.setSelection(code_adapter.getPosition("+91 (IN) India"));

                } else {

                    for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                        if (Share.countryCodeValue.equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
                            if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                                send = false;
                                above.setVisibility(View.VISIBLE);
                                below_mail.setVisibility(View.GONE);
                                sp_code.setSelection(code_adapter.getPosition("+" + Share.country_mobile_code.get(i).getPhonecode() + " (" + Share.country_mobile_code.get(i).getSortname() + ")" + " " + Share.country_mobile_code.get(i).getName()));
                            } else {
                                above.setVisibility(View.GONE);
                                below_mail.setVisibility(View.VISIBLE);
                                send = true;
                            }
                        }
                    }


                }

                sp_code.setEnabled(false);

                sp_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        code_position = position;
                        country_code = Integer.parseInt(sp_code.getSelectedItem().toString().replaceAll("[^0-9]", ""));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                // Changes Made By Jignesh Patel
                Button btnSendOtp = dialog.findViewById(R.id.btn_send_otp);
                btnSendOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (send) {
                            final String email = ed_email.getText().toString().trim();

                            final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                            if (email.equalsIgnoreCase("") || email.length() == 0) {
                                ed_email.requestFocus();
                                ed_email.setError(getString(R.string.enter_correct_email));
                            } else {
                                if (email.matches(emailPattern)) {
                                    verify("", email, dialog, "");
                                } else {
                                    ed_email.requestFocus();
                                    ed_email.setError(getString(R.string.invalid_email_id));
                                }
                            }
                        } else {
                            if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                                Log.e("COUNTRY", "onClick: IN");
                                lenght = Share.country_mobile_code.get(0).getPhone_length();
                            } else {
                                for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                                    if (Share.countryCodeValue.equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
                                        if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                                            lenght = Share.country_mobile_code.get(i).getPhone_length();
                                            Log.e("HERE", "onClick: ======>" + lenght);
                                        }
                                    }
                                }
                            }

                            if (lenght != null) {

                                String number = tvdone.getText().toString().trim();
                                if (Share.countryCodeValue.equalsIgnoreCase("IN") && number.startsWith("+91")) {
                                    number = number.substring(3);
                                }

                                if (lenght == 0) {
                                    if (number.length() == 0) {
                                        tvdone.setError(getString(R.string.enter_mobile));
                                        tvdone.requestFocus();
                                    } else {
                                        verify(number, "", dialog, "+" + sp_code.getSelectedItem().toString().replaceAll("[^0-9]", ""));
                                    }
                                } else {
                                    if (number.contains(" ") || number.length() < lenght) {
                                        tvdone.setError(getString(R.string.pls_enter_valid_mobile_no));
                                        tvdone.requestFocus();
                                    } else {
                                        verify(number, "", dialog, "+" + sp_code.getSelectedItem().toString().replaceAll("[^0-9]", ""));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        id_ll_password =

                findViewById(R.id.id_ll_password);

        id_ll_mobile_no =

                findViewById(R.id.id_ll_mobile_no);

        im_printphoto =

                findViewById(R.id.im_printphoto);

        im_user =

                findViewById(R.id.im_user);

        im_key =

                findViewById(R.id.im_key);

        id_signIn =

                findViewById(R.id.id_signIn);

        id_mobile_number =

                findViewById(R.id.id_mobile_number);

        id_password =

                findViewById(R.id.id_password);

        id_ll_email_id = findViewById(R.id.id_ll_email_id);
        id_email_id = findViewById(R.id.id_email_id);


        Log.e("COUNTRYCODEVALUE", "findViews: " + Share.countryCodeValue);

    }

    private void verify(final String number, final String email, final Dialog dialog, final String code) {


        //pd = ProgressDialog.show(LogInActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(LogInActivity.this);

        APIService api = new MainApiClient(LogInActivity.this).getApiInterface();

        Call<new_numberverify> call = api.forget_verify(number, email, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<new_numberverify>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<new_numberverify> call, Response<new_numberverify> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    new_numberverify responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        dialog.dismiss();
                        dismissDialog();
                        String verify;
                        if (send) {
                            verify = "1";
                        } else {
                            verify = "0";
                        }
                        Intent intent = new Intent(LogInActivity.this, PhoneAuthActivity.class);
                        intent.putExtra("country_code", code);
                        intent.putExtra("mobno", number);
                        intent.putExtra("emailid", email);
                        intent.putExtra("verify", verify);
                        Share.forgetpassword = 1;
                        startActivity(intent);
                    } else {
                        dismissDialog();
                        Toast.makeText(LogInActivity.this, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dismissDialog();
                } else {
                    dismissDialog();
                    Toast.makeText(LogInActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<new_numberverify> call, Throwable t) {
                dismissDialog();
                Toast.makeText(LogInActivity.this, getString(R.string.something_went_wrong) + t, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }

                    alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {
                            dialog1.dismiss();
                            verify(number, email, dialog, code);
                        }
                    });
                    alertDialog.show();
                } else {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }

                    alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {
                            dialog1.dismiss();
                            verify(number, email, dialog, code);
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
        try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            for (int i = 0; i < temp_spinnercodeArray.size(); i++) {
                if (temp_spinnercodeArray.get(i).equalsIgnoreCase(SharedPrefs.getString(LogInActivity.this, SharedPrefs.country_code))) {
                    sp_country.setSelection(i);
                }
            }
            if (SharedPrefs.getInt(LogInActivity.this, SharedPrefs.CART_COUNT) == 0) {
                HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);
            } else {
                HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }

        if (Share.isRegistrationSuccess) {

            Share.isRegistrationSuccess = false;

            String mobile = SharedPrefs.getString(LogInActivity.this, Share.key_ + RegReq.mobile_1);
            String email = SharedPrefs.getString(LogInActivity.this, Share.key_ + RegReq.email);

            if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                id_mobile_number.setText(mobile);
            } else {
                id_email_id.setText(email);
                for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                    if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                        if (SharedPrefs.getString(LogInActivity.this, SharedPrefs.country_code).equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
                            id_mobile_number.setText(mobile);
                        }
                    }
                }
            }

            if (Share.tempPassword != null) {
                id_password.setText(Share.tempPassword);
                Share.tempPassword = null;
                signin();
            }


        }

    }

    private void getDisplaySize() {
        Display display = LogInActivity.this.getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void setHeader() {
        TextView title = findViewById(R.id.title);
        title.setText("Sign");
        TextView title1 = findViewById(R.id.title1);
        title1.setText("In");
        ImageView imageView = findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPrefs.getString(LogInActivity.this, SharedPrefs.last_country_code).equalsIgnoreCase(SharedPrefs.getString(LogInActivity.this, SharedPrefs.country_code))) {
                    Log.e("HERE", "onClick: ");
                    finish();
                } else {
                    Log.e("NOTHERE", "onClick: ");
                    if (mall_detail_activity.mActivity != null) {
                        mall_detail_activity.mActivity.finish();
                    }
                    if (mall_category_activity.mActivity != null) {
                        mall_category_activity.mActivity.finish();
                    }
                    if (mall_description.mActivity != null) {
                        mall_description.mActivity.finish();
                    }
                    if (mall_filter_activity.mactivity != null) {
                        mall_filter_activity.mactivity.finish();
                    }
                    if (mall_seller_activity.activity != null) {
                        mall_seller_activity.activity.finish();
                    }
                    if (MallSearchActivity.activity != null) {
                        MallSearchActivity.activity.finish();
                    }

//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.frg_main, new mall());
//                    fragmentTransaction.commitAllowingStateLoss();
                    Share.login_back = true;
                    finish();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (SharedPrefs.getString(LogInActivity.this, SharedPrefs.last_country_code).equalsIgnoreCase(SharedPrefs.getString(LogInActivity.this, SharedPrefs.country_code))) {
            Log.e("HERE", "onClick: ");
            finish();
        } else {
            Log.e("NOTHERE", "onClick: ");
            if (mall_detail_activity.mActivity != null) {
                mall_detail_activity.mActivity.finish();
            }
            if (mall_category_activity.mActivity != null) {
                mall_category_activity.mActivity.finish();
            }
            if (mall_description.mActivity != null) {
                mall_description.mActivity.finish();
            }
            if (mall_filter_activity.mactivity != null) {
                mall_filter_activity.mactivity.finish();
            }
            if (mall_seller_activity.activity != null) {
                mall_seller_activity.activity.finish();
            }
            if (MallSearchActivity.activity != null) {
                MallSearchActivity.activity.finish();
            }


//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.frg_main, new mall());
//            fragmentTransaction.commitAllowingStateLoss();
            Share.login_back = true;

            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_REGISTER && resultCode == RESULT_OK) {

        }
    }

    public void dismissDialog() {

        try {
            if (isDestroyed()) {
                return;
            }
//            if (pd != null && pd.isShowing()) {
//                pd.dismiss();
//            }
            hideProgressDialog();
        } catch (Exception e) {
            Log.e("Dismiss Dialog", e.toString());
        }

    }
}
