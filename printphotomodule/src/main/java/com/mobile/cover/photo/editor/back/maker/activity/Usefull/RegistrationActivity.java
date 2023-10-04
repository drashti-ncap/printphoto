package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Country_state_city_code_response;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.new_numberverify;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.auth.PhoneAuthActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.model.Address;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends PrintPhotoBaseActivity {
    LinearLayout id_ll_print_photo, id_ll_register, id_ll_firstname, id_ll_lastname, id_ll_mobile_no, id_ll_passwoerd, ll_city, ll_state;
    ImageView im_printphoto, im_user, im_key, id_reset;
    EditText id_ed_fname, id_ed_lname, id_ed_mobile_no, id_ed_password, id_ed_email_id, id_ed_re_password;
    //    TextView tv_code;
    ArrayList<String> spinnerstateArray = new ArrayList<String>();
    ArrayList<String> spinnercodeArray = new ArrayList<String>();
    ArrayList<String> spinnercountryArray = new ArrayList<String>();
    ArrayList<String> spinnercitiesArray = new ArrayList<String>();
    ArrayList<String> temp_spinnercodeArray = new ArrayList<String>();
    Spinner sp_country, sp_states, sp_district, sp_code;
    int countrypos, statepos, mobilecode, country_code_position;
   // ProgressDialog pd;
    String country_code, country_sort_num, Country_reg = "", State_reg = "", City_reg = "";
    ArrayList<Address> addressArrayList = new ArrayList<>();
    Runnable runnable;
    boolean country_selection = false, code_selection = false;
    private Handler timeoutHandler = new Handler();

    String countryCode = "IN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        setHeader();
        getDisplaySize();
        getcountry_states_cities_code();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    private void intView() {


        id_ll_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                id_ed_email_id.setError(null);
                id_ed_fname.setError(null);
                id_ed_lname.setError(null);
                id_ed_mobile_no.setError(null);
                id_ed_password.setError(null);
                id_ed_re_password.setError(null);


                if (SharedPrefs.getString(RegistrationActivity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                    if (id_ed_fname.getText().toString().trim().length() == 0 || id_ed_fname.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_fname.setError(getString(R.string.pls_enter_first_name));
                        id_ed_fname.requestFocus();
                    } else if (id_ed_lname.getText().toString().trim().length() == 0 || id_ed_lname.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_lname.setError(getString(R.string.pls_enter_last_name));
                        id_ed_lname.requestFocus();
                    } else if (id_ed_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_mobile_no.setError(getString(R.string.pls_enter_valid_mobile_no));
                        id_ed_mobile_no.requestFocus();
                    } else if (id_ed_mobile_no.getText().toString().trim().length() > 10) {
                        id_ed_mobile_no.setError(getString(R.string.pls_enter_valid_mobile_no_10));
                        id_ed_mobile_no.requestFocus();
                    } else if (id_ed_email_id.getText().toString().trim().length() == 0 || id_ed_email_id.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_email_id.setError(getString(R.string.please_enter_email));
                        id_ed_email_id.requestFocus();
                    } else if (!id_ed_email_id.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                        id_ed_email_id.setError(getString(R.string.valid_email));
                        id_ed_email_id.requestFocus();
                    } else if (id_ed_password.getText().toString().trim().length() == 0 || id_ed_password.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_password.setError(getString(R.string.pls_enter_pass));
                        id_ed_password.requestFocus();
                    } else if (id_ed_password.getText().toString().trim().length() < 6 || id_ed_password.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_password.setError(getString(R.string.must_six_char));
                        id_ed_password.requestFocus();
                    } else if (id_ed_re_password.getText().toString().trim().length() == 0 || id_ed_re_password.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_re_password.setError(getString(R.string.pls_re_enter));
                        id_ed_re_password.requestFocus();
                    } else if (id_ed_re_password.getText().toString().trim().length() < 6 || id_ed_re_password.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_re_password.setError(getString(R.string.must_six_char));
                        id_ed_re_password.requestFocus();
                    } else if (!id_ed_password.getText().toString().trim().equalsIgnoreCase(id_ed_re_password.getText().toString())) {
                        id_ed_re_password.setError(getString(R.string.pass_does_not_match));
                        id_ed_re_password.requestFocus();
                    } else if (Country_reg.trim().equalsIgnoreCase("") || Country_reg.trim().equalsIgnoreCase("0")) {
                        Toast.makeText(RegistrationActivity.this, getString(R.string.pls_select_country), Toast.LENGTH_SHORT).show();
                    } else if (State_reg.trim().equalsIgnoreCase("") || State_reg.equalsIgnoreCase("1")) {
                        Toast.makeText(RegistrationActivity.this, getString(R.string.pls_select_state), Toast.LENGTH_SHORT).show();
                    } else if (City_reg.trim().equalsIgnoreCase("")) {
                        Toast.makeText(RegistrationActivity.this, getString(R.string.pls_select_city), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("COUNTRYRED", "onClick: " + Country_reg);
                        verify(id_ed_email_id.getText().toString().trim());
                    }
                } else {
                    if (id_ed_fname.getText().toString().trim().length() == 0 || id_ed_fname.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_fname.setError(getString(R.string.pls_enter_first_name));
                        id_ed_fname.requestFocus();
                    } else if (id_ed_lname.getText().toString().trim().length() == 0 || id_ed_lname.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_lname.setError(getString(R.string.pls_enter_last_name));
                        id_ed_lname.requestFocus();
                    } else if (id_ed_mobile_no.getText().toString().trim().length() == 0 || id_ed_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_mobile_no.setError(getString(R.string.pls_enter_valid_mobile_no));
                        id_ed_mobile_no.requestFocus();
                    } else if ((SharedPrefs.getString(RegistrationActivity.this, SharedPrefs.country_code)).equalsIgnoreCase("SA") && id_ed_mobile_no.getText().toString().trim().length() > 9) {
                        id_ed_mobile_no.setError(getString(R.string.pls_enter_valid_mobile_no_9));
                        id_ed_mobile_no.requestFocus();
                    } else if (id_ed_email_id.getText().toString().trim().length() == 0 || id_ed_email_id.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_email_id.setError(getString(R.string.please_enter_email));
                        id_ed_email_id.requestFocus();
                    } else if (!id_ed_email_id.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                        id_ed_email_id.setError(getString(R.string.valid_email));
                        id_ed_email_id.requestFocus();
                    } else if (id_ed_password.getText().toString().trim().length() == 0 || id_ed_password.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_password.setError(getString(R.string.pls_enter_pass));
                        id_ed_password.requestFocus();
                    } else if (id_ed_password.getText().toString().trim().length() < 6 || id_ed_password.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_password.setError(getString(R.string.must_six_char));
                        id_ed_password.requestFocus();
                    } else if (id_ed_re_password.getText().toString().trim().length() == 0 || id_ed_re_password.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_re_password.setError(getString(R.string.pls_re_enter));
                        id_ed_re_password.requestFocus();
                    } else if (id_ed_re_password.getText().toString().trim().length() < 6 || id_ed_re_password.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_re_password.setError(getString(R.string.must_six_char));
                        id_ed_re_password.requestFocus();
                    } else if (!id_ed_password.getText().toString().trim().equalsIgnoreCase(id_ed_re_password.getText().toString())) {
                        id_ed_re_password.setError(getString(R.string.pass_does_not_match));
                        id_ed_re_password.requestFocus();
                    } else if (Country_reg.trim().equalsIgnoreCase("") || Country_reg.trim().equalsIgnoreCase("0")) {
                        Toast.makeText(RegistrationActivity.this, getString(R.string.pls_select_country), Toast.LENGTH_SHORT).show();
                    } else if (State_reg.trim().equalsIgnoreCase("") || State_reg.equalsIgnoreCase("1")) {
                        Toast.makeText(RegistrationActivity.this, getString(R.string.pls_select_state), Toast.LENGTH_SHORT).show();
                    } else if (City_reg.trim().equalsIgnoreCase("")) {
                        Toast.makeText(RegistrationActivity.this, getString(R.string.pls_select_city), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("COUNTRYRED", "onClick: " + Country_reg);

                        verify(id_ed_email_id.getText().toString().trim());
                    }
                }

            }
        });
    }

    private void verify(final String email) {


        //pd = ProgressDialog.show(RegistrationActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(this);

        APIService api = new MainApiClient(RegistrationActivity.this).getApiInterface();

        Log.e("CHECKAPI", "verify: ln--->" + Locale.getDefault().getLanguage());
        Log.e("CHECKAPI", "verify: email--->" + email);
        Log.e("CHECKAPI", "verify: ph--->" + id_ed_mobile_no.getText().toString());
        Call<new_numberverify> call = api.verify(id_ed_mobile_no.getText().toString(), email, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<new_numberverify>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<new_numberverify> call, retrofit2.Response<new_numberverify> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    new_numberverify responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());

                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        dismissDialog();
                        finish();

                        boolean sendmobile = false;
                        for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                            if (SharedPrefs.getString(RegistrationActivity.this, SharedPrefs.country_code).equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
                                if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                                    sendmobile = true;
                                }
                            }

                        }

                        if (SharedPrefs.getString(RegistrationActivity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                            Intent intent = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                            Share.forgetpassword = 0;
                            Log.e("NAME", "onResponse: " + wordFirstCap(id_ed_fname.getText().toString()));
                            Log.e("NAME", "onResponse: " + wordFirstCap(id_ed_lname.getText().toString()));
                            intent.putExtra("fname", wordFirstCap(id_ed_fname.getText().toString()));
                            intent.putExtra("verify", "0");
                            intent.putExtra("emailid", email);
                            intent.putExtra("country", Country_reg);
                            intent.putExtra("state", State_reg);
                            intent.putExtra("city", City_reg);
                            intent.putExtra("lname", wordFirstCap(id_ed_lname.getText().toString()));
                            intent.putExtra("mobno", id_ed_mobile_no.getText().toString());
                            intent.putExtra("country_code", "+91");
                            intent.putExtra("pswd", id_ed_password.getText().toString());
                            intent.putExtra("android_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                            startActivity(intent);
                        } else if (sendmobile) {
                            Intent intent = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                            Share.forgetpassword = 0;
                            Log.e("NAME", "onResponse: " + wordFirstCap(id_ed_fname.getText().toString()));
                            Log.e("NAME", "onResponse: " + wordFirstCap(id_ed_lname.getText().toString()));
                            intent.putExtra("fname", wordFirstCap(id_ed_fname.getText().toString()));
                            intent.putExtra("verify", "0");
                            intent.putExtra("emailid", email);
                            intent.putExtra("country", Country_reg);
                            intent.putExtra("state", State_reg);
                            intent.putExtra("city", City_reg);
                            intent.putExtra("lname", wordFirstCap(id_ed_lname.getText().toString()));
                            intent.putExtra("mobno", id_ed_mobile_no.getText().toString());
                            intent.putExtra("country_code", "+" + sp_code.getSelectedItem().toString().replaceAll("[^0-9]", ""));
                            intent.putExtra("pswd", id_ed_password.getText().toString());
                            intent.putExtra("android_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), PhoneAuthActivity.class);
                            Share.forgetpassword = 0;
                            Log.e("NAME", "onResponse: " + wordFirstCap(id_ed_fname.getText().toString()));
                            Log.e("NAME", "onResponse: " + wordFirstCap(id_ed_lname.getText().toString()));
                            intent.putExtra("fname", wordFirstCap(id_ed_fname.getText().toString()));
                            intent.putExtra("verify", "1");
                            intent.putExtra("emailid", email);
                            intent.putExtra("country", Country_reg);
                            intent.putExtra("state", State_reg);
                            intent.putExtra("city", City_reg);
                            intent.putExtra("lname", wordFirstCap(id_ed_lname.getText().toString()));
                            intent.putExtra("mobno", id_ed_mobile_no.getText().toString());
                            intent.putExtra("country_code", "");
                            intent.putExtra("pswd", id_ed_password.getText().toString());
                            intent.putExtra("android_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                            startActivity(intent);
                        }

                    } else {
                        dismissDialog();
                        Toast.makeText(RegistrationActivity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    dismissDialog();
                    Toast.makeText(RegistrationActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<new_numberverify> call, Throwable t) {
                dismissDialog();
//                Toast.makeText(RegistrationActivity.this, getString(R.string.something_went_wrong) + t, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            verify(email);

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            verify(email);

                        }
                    });
                    alertDialog.show();
                }

            }
        });

    }

    public String wordFirstCap(String str) {
        String[] words = str.trim().split(" ");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (words[i].trim().length() > 0) {
                Log.e("words[i].trim", "" + words[i].trim().charAt(0));
                ret.append(Character.toUpperCase(words[i].trim().charAt(0)));
                ret.append(words[i].trim().substring(1));
                if (i < words.length - 1) {
                    ret.append(' ');
                }
            }
        }

        return ret.toString();
    }

    private void setDimens() {
        int height = Share.screenHeight / 10;
        id_ll_register.getLayoutParams().height = height;
        id_ll_mobile_no.getLayoutParams().height = height;
        id_ll_passwoerd.getLayoutParams().height = height;
        id_ll_print_photo.getLayoutParams().height = (int) (Share.screenHeight / 3.5);
        im_printphoto.getLayoutParams().height = Share.screenHeight / 4;
        im_printphoto.getLayoutParams().width = Share.screenWidth / 2;
    }

    private void getcountry_states_cities_code() {


        Share.Country_state_city.clear();
        //pd = ProgressDialog.show(RegistrationActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(this);
        APIService api = new MainApiClient(RegistrationActivity.this).getApiInterface();


        Call<Country_state_city_code_response> call = api.getcountry_states_city_code();


        call.enqueue(new Callback<Country_state_city_code_response>() {
            @Override
            public void onResponse(Call<Country_state_city_code_response> call, Response<Country_state_city_code_response> response) {
                if (response.isSuccessful()) {
                    dismissDialog();
                    Country_state_city_code_response customimage_response = response.body();
                    Share.Country_state_city.addAll(customimage_response.getData());
                    findViews();
                    intView();
//                    if (SharedPrefs.getString(RegistrationActivity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                    ll_city.setVisibility(View.VISIBLE);
                    ll_state.setVisibility(View.VISIBLE);
//                    } else {
//                        ll_city.setVisibility(View.GONE);
//                        ll_state.setVisibility(View.GONE);
//                    }
//                    change_position();
                } else {
                    dismissDialog();
                    Toast.makeText(RegistrationActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Country_state_city_code_response> call, Throwable t) {
                dismissDialog();

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dismissDialog();
                            getcountry_states_cities_code();
                        }
                    });
                    alertDialog.show();
                } else {

                    AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            dismissDialog();
                            getcountry_states_cities_code();

                        }
                    });
                    alertDialog.show();
                }

            }
        });

    }

//    private void change_position() {
//        runnable = new Runnable() {
//            public void run() {
//                if (sp_country!=null&&sp_code!=null){
//                    sp_code.setSelection(country_code_position-1);
//                }
//            }
//        };
//        timeoutHandler.postDelayed(runnable, 1000);
//        change_position();
//    }


    private void findViews() {
        id_ll_print_photo = findViewById(R.id.id_ll_print_photo);

        ll_state = findViewById(R.id.ll_state);
        ll_city = findViewById(R.id.ll_city);

        id_ed_email_id = findViewById(R.id.id_ed_email_id);
        id_ll_register = findViewById(R.id.id_ll_register);
        im_printphoto = findViewById(R.id.im_printphoto);
        id_ll_firstname = findViewById(R.id.id_ll_firstname);
        id_ll_lastname = findViewById(R.id.id_ll_lastname);
        id_ll_mobile_no = findViewById(R.id.id_ll_mobile_no);
        id_ll_passwoerd = findViewById(R.id.id_ll_passwoerd);
        id_ed_fname = findViewById(R.id.id_ed_fname);
        id_ed_re_password = findViewById(R.id.id_ed_re_password);
        id_ed_lname = findViewById(R.id.id_ed_lname);
        id_ed_mobile_no = findViewById(R.id.id_ed_mobile_no);
        id_ed_password = findViewById(R.id.id_ed_password);
//        tv_code = findViewById(R.id.tv_code);

        spinnercodeArray.clear();
        spinnercountryArray.clear();
        spinnercountryArray.add(getString(R.string.select_country));
        for (int country = 0; country < Share.Country_state_city.size(); country++) {
            spinnercodeArray.add("+" + Share.Country_state_city.get(country).getPhonecode() + " (" + Share.Country_state_city.get(country).getSortname() + ")" + " " + Share.Country_state_city.get(country).getName());
            spinnercountryArray.add(Share.Country_state_city.get(country).getName());
            if (SharedPrefs.getString(RegistrationActivity.this, SharedPrefs.country_code).equalsIgnoreCase(Share.Country_state_city.get(country).getSortname())) {
                country_code = Share.Country_state_city.get(country).getName();
                country_sort_num = "+" + Share.Country_state_city.get(country).getPhonecode() + " (" + Share.Country_state_city.get(country).getSortname() + ")" + " " + Share.Country_state_city.get(country).getName();
            }
        }


        if (Share.countryCodeValue != null && Share.countryCodeValue.equalsIgnoreCase("IN")) {
            UtilsKt.setEditTextMaxLength(id_ed_mobile_no, 10);
        } else if (Share.countryCodeValue != null && country_sort_num.equals("SA")) {
            UtilsKt.setEditTextMaxLength(id_ed_mobile_no, 9);
        } else {
            UtilsKt.setEditTextMaxLength(id_ed_mobile_no, 20);
        }

        sp_country = findViewById(R.id.sp_country);
        sp_states = findViewById(R.id.sp_states);
        sp_code = findViewById(R.id.sp_code);
        sp_district = findViewById(R.id.sp_district);

        final ArrayAdapter<String> country_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, spinnercountryArray);
        sp_country.setAdapter(country_adapter);
        int spinnerpos1 = country_adapter.getPosition(country_code);
        sp_country.setSelection(spinnerpos1);


        final ArrayAdapter<String> code_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, spinnercodeArray);
        sp_code.setAdapter(code_adapter);
        int spinnerpos = code_adapter.getPosition(country_sort_num);
        sp_code.setSelection(spinnerpos);

        sp_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    country_code_position = position;
                    mobilecode = Integer.parseInt(sp_code.getSelectedItem().toString().replaceAll("[^0-9]", ""));

                    if (code_selection) {
                        sp_country.setSelection(position + 1);
                        country_selection = false;
                    } else {
                        code_selection = true;
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    Log.e("POSITION", "onItemSelected: " + position);

                    spinnerstateArray.clear();
                    country_code_position = position;
                    if (country_selection) {
                        if (position != 0) {
                            sp_code.setSelection(position - 1);
                        }
                        code_selection = false;
                    } else {
                        country_selection = true;
                    }

//                code_adapter.notifyDataSetChanged();

                    if (position != 0) {
                        Log.e("SORTNAME", "onItemSelected: " + Share.Country_state_city.get(position - 1).getSortname());
                        Share.countryCodeValue = Share.Country_state_city.get(position - 1).getSortname();
                        SharedPrefs.save(RegistrationActivity.this, SharedPrefs.country_code, Share.Country_state_city.get(position - 1).getSortname());
                    }

                    if (Share.Country_state_city.get(position - 1).getSortname().equalsIgnoreCase("IN")) {
                        UtilsKt.setEditTextMaxLength(id_ed_mobile_no, 10);
                    } else if (country_sort_num.equals("SA")) {
                        UtilsKt.setEditTextMaxLength(id_ed_mobile_no, 9);
                    } else {
                        UtilsKt.setEditTextMaxLength(id_ed_mobile_no, 20);
                    }

//                if (SharedPrefs.getString(RegistrationActivity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                    ll_city.setVisibility(View.VISIBLE);
                    ll_state.setVisibility(View.VISIBLE);
//                } else {
//                    ll_city.setVisibility(View.GONE);
//                    ll_state.setVisibility(View.GONE);
//                }

                    spinnerstateArray.add(getString(R.string.select_state));
                    if (position != 0) {
                        position = position - 1;
                        countrypos = position;

                        for (int states = 0; states < Share.Country_state_city.get(position).getStates().size(); states++) {
                            spinnerstateArray.add(Share.Country_state_city.get(position).getStates().get(states).getName());
                        }
                        Country_reg = Share.Country_state_city.get(position).getId().toString();
                        State_reg = "";
                        City_reg = "";
                    } else {
                        Country_reg = "0";
                    }
                    ArrayAdapter<String> state_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, spinnerstateArray);
                    sp_states.setAdapter(state_adapter);
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_states.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnercitiesArray.clear();
                spinnercitiesArray.add(getString(R.string.select_city));
                Log.e("POSITION", "onItemSelected:=======>State " + position);
                if (position != 0) {
                    position = position - 1;
                    statepos = position;
                    for (int cities = 0; cities < Share.Country_state_city.get(countrypos).getStates().get(position).getCities().size(); cities++) {
                        spinnercitiesArray.add(Share.Country_state_city.get(countrypos).getStates().get(position).getCities().get(cities).getName());
                    }
                }
                ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, spinnercitiesArray);
                sp_district.setAdapter(city_adapter);
                Log.e("ERROR", "onItemSelected: " + countrypos);
                Log.e("ERROR", "onItemSelected: " + Share.Country_state_city.get(countrypos).getStates());
                State_reg = Share.Country_state_city.get(countrypos).getStates().get(position).getId().toString();
                City_reg = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    position = position - 1;
                    City_reg = Share.Country_state_city.get(countrypos).getStates().get(statepos).getCities().get(position).getId().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        id_reset = findViewById(R.id.id_reset);
        id_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_ed_fname.setError(null);
                id_ed_lname.setError(null);
                id_ed_mobile_no.setError(null);
                id_ed_password.setError(null);
                id_ed_email_id.setError(null);
                id_ed_re_password.setError(null);

                id_ed_fname.setText("");
                id_ed_lname.setText("");
                id_ed_mobile_no.setText("");
                id_ed_password.setText("");
                id_ed_email_id.setText("");
                id_ed_re_password.setText("");
                Country_reg = "";
                State_reg = "";
                City_reg = "";
                sp_country.setSelection(0);
                sp_states.setSelection(0);
                sp_district.setSelection(0);
            }
        });
    }


    private void getDisplaySize() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void setHeader() {

        ImageView imageView = findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Share.iscart == true) {
                    Share.iscart = false;
                    finish();
                } else if (Share.isorder == true) {
                    Share.isorder = false;
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Share.iscart == true) {
            Share.iscart = false;
            finish();
        } else {
            finish();
        }
        if (Share.isorder == true) {
            Share.isorder = false;
        } else {
            finish();
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
