package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.username_update_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.response_main_username_update;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ChangePasswordActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.PinCode;
import com.mobile.cover.photo.editor.back.maker.model.PinCodeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRegDataActivity extends PrintPhotoBaseActivity {

    private static final String TAG = "EditRegDataActivity";
    int height = 0;
    int width = 0;
    ImageView id_back, id_done, id_ll_reset;
    LinearLayout personal_detail, id_ll_1, id_ll_10, id_ll_2, id_ll_3, id_ll_4, id_ll_5, id_ll_6, id_ll_7, id_ll_8, id_ll_9, address_detail, user_data, address_data, id_ll_save;
   // ProgressDialog progressDialog;
    TextView title, title1, update;
    EditText id_ed_mobile2, id_ed_fname, id_ed_lname, id_ed_h_pincode, id_ed_h_homeno, id_ed_h_street, id_ed_h_landmark, id_ed_h_city, id_ed_h_state, id_ed_h_country, id_ed_email_id;
    Spinner sp_currency;
    String currency_id = "0";
    PackageInfo pInfo = null;
    private boolean isOtpSent = false;
    private boolean isValid = false;
    private LinearLayout moLlChangePassword, ll_sp_currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reg_data);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getDisplaySize();
        findVies();
        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        update = findViewById(R.id.update);
        if (getIntent().hasExtra("userdata")) {
            title.setText(getString(R.string.edit));
            title1.setText(getString(R.string.account));
            user_data.setVisibility(View.VISIBLE);
            address_data.setVisibility(View.GONE);
        } else {
            title.setText("Edit");
            title1.setText("Address");
            update.setText(" Save Address");
            update.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_update), null, null, null);
            address_data.setVisibility(View.VISIBLE);
            user_data.setVisibility(View.GONE);
        }

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        intViews();
    }


    private void getDisplaySize() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void intViews() {
        height = Share.screenHeight / 11;
//        setdimensH(id_ll_1, id_ll_2, id_ll_10, id_ll_3, id_ll_4, id_ll_5, id_ll_6, id_ll_7, id_ll_8, id_ll_9);
        id_ed_h_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (id_ed_h_pincode.getText().length() == 6) {
                    getPincodeDetail(id_ed_h_pincode.getText().toString());
                } else {
                    id_ed_h_pincode.setError(getString(R.string.provide_pincode));
                }
            }
        });

        String fname = getIntent().getStringExtra("fname");
        String lname = getIntent().getStringExtra("lname");
        final String mobile2 = getIntent().getStringExtra("mobile_1");

        id_ed_fname.setText(fname);
        id_ed_lname.setText(lname);
        id_ed_mobile2.setText(mobile2);
        id_ed_email_id.setText(getIntent().getStringExtra("email"));


        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        moLlChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditRegDataActivity.this, ChangePasswordActivity.class));
            }
        });

        id_ll_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleardara(id_ed_fname, id_ed_lname, id_ed_h_pincode, id_ed_h_homeno, id_ed_h_street, id_ed_h_landmark, id_ed_h_city, id_ed_h_state, id_ed_h_country);
                id_ed_fname.setError(null);
                id_ed_lname.setError(null);
//                id_ed_email_id.setError(null);
                id_ed_h_pincode.setError(null);
                id_ed_h_homeno.setError(null);
                id_ed_h_street.setError(null);
                id_ed_h_landmark.setError(null);
                id_ed_h_city.setError(null);
                id_ed_h_state.setError(null);
                id_ed_h_country.setError(null);
            }
        });
        id_ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().hasExtra("userdata")) {
                    if (id_ed_fname.getText().toString().trim().length() == 0 || id_ed_fname.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_fname.setError(getString(R.string.enter_first_name));
                    } else if (id_ed_lname.getText().toString().trim().length() == 0 || id_ed_lname.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_lname.setError(getString(R.string.enter_last_name));
                    } else if (id_ed_email_id.getText().toString().trim().length() == 0 || id_ed_email_id.getText().toString().trim().equalsIgnoreCase("")) {
                        id_ed_email_id.requestFocus();
                        id_ed_email_id.setError(getString(R.string.enter_email_id));
                    } else if (id_ed_email_id.getText().toString().trim().length() != 0 || !id_ed_email_id.getText().toString().trim().equalsIgnoreCase("")) {
                        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (id_ed_email_id.getText().toString().trim().matches(emailPattern)) {
                            updateusername();
                        } else {
                            id_ed_email_id.requestFocus();
                            id_ed_email_id.setError(getString(R.string.invalid_email_id));
                        }
                    } else {
                        Log.e(TAG, "onClick: ====>Do nothing");
                    }
                } else {
                    Log.e(TAG, "onClick: ====>Do nothing");
                }
            }
        });
    }

    public void getPincodeDetail(String pincode) {


        APIService apiService = new MainApiClient(EditRegDataActivity.this).getApiInterface();
        Call<PinCode> pinCodeCall = apiService.getPincodeDetails("" + pincode, Locale.getDefault().getLanguage());
        pinCodeCall.enqueue(new Callback<PinCode>() {
            @Override
            public void onResponse(Call<PinCode> call, Response<PinCode> response) {

                PinCodeData pinCodeData = response.body().getPinCodeData();

                if (pinCodeData != null) {
                    id_ed_h_city.setText(pinCodeData.getCityName());
                    id_ed_h_state.setText(pinCodeData.getStateName());
                    id_ed_h_country.setText("india");
                    isValid = true;
                }
                if (response.body().getResponseCode().equalsIgnoreCase("0")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(EditRegDataActivity.this).create();
                    alertDialog.setTitle(getString(R.string.not_available));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(response.body().getResponseMessage());
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertDialog.show();
                }

            }

            @Override
            public void onFailure(Call<PinCode> call, Throwable t) {
                id_ed_h_city.setText("");
                id_ed_h_state.setText("");
                id_ed_h_country.setText("");
            }
        });


    }

//    private void updateAddress() {
//        String pincode_value = id_ed_h_pincode.getText().toString();
//        String home_no_value = id_ed_h_homeno.getText().toString();
//        String street_value = id_ed_h_street.getText().toString();
//        String landmark_value = id_ed_h_landmark.getText().toString();
//        String city_value = id_ed_h_city.getText().toString();
//        String state_value = id_ed_h_state.getText().toString();
//        String country_value = id_ed_h_country.getText().toString();
//
//
//        progressDialog = ProgressDialog.show(EditRegDataActivity.this,"",getString(R.string.loading),true,false);
//        progressDialog.show();
//
//        final Address address = new Address();
//        address.setCity(id_ed_h_city.getText().toString());
//        address.setPincode(id_ed_h_pincode.getText().toString());
//        address.setHome_no(id_ed_h_homeno.getText().toString());
//        if (getIntent().getBooleanExtra("isselect", false)) {
//            address.setSelect(true);
//        } else {
//            address.setSelect(false);
//        }
//        address.setLandmark(id_ed_h_landmark.getText().toString());
//        address.setCity(id_ed_h_city.getText().toString());
//        address.setState(id_ed_h_state.getText().toString());
//        address.setCountry(id_ed_h_country.getText().toString());
//        address.setStreet(id_ed_h_street.getText().toString());
//
//        final ArrayList<Address> addressArrayList = SharedPrefs.getAdListAddress(EditRegDataActivity.this, Share.address);
//
//        String androidId = FirebaseInstanceId.getInstance().getToken();
//        Log.e("TOKEN", "updateAddress: ======>2" + androidId);
//
//        String name = getIntent().getStringExtra("fname") + " " + getIntent().getStringExtra("lname");
//
//
//        Log.e(TAG, "updateAddress: ");
////
//        String home = getIntent().getStringExtra("home");
//        String office = getIntent().getStringExtra("office");
//        String address_3 = getIntent().getStringExtra("address_3");
//        String address_4 = getIntent().getStringExtra("address_4");
//        String address_5 = getIntent().getStringExtra("address_5");
//
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(home_no_value);
//        stringBuilder.append("$");
//        stringBuilder.append(street_value);
//        stringBuilder.append("$");
//        stringBuilder.append(landmark_value);
//        stringBuilder.append("$");
//        stringBuilder.append(city_value);
//        stringBuilder.append("$");
//        stringBuilder.append(state_value);
//        stringBuilder.append("$");
//        stringBuilder.append(country_value);
//        stringBuilder.append("$");
//        stringBuilder.append(pincode_value);
//
//
//        if (addressArrayList.size() == 0) {
//
//            home = stringBuilder.toString();
//            address.setType("home");
//        } else if (addressArrayList.size() == 1) {
//            office = stringBuilder.toString();
//            address.setType("office");
//
//        } else if (addressArrayList.size() == 2) {
//            address_3 = stringBuilder.toString();
//            address.setType("address_3");
//
//        } else if (addressArrayList.size() == 3) {
//            address_4 = stringBuilder.toString();
//            address.setType("address_4");
//
//        } else if (addressArrayList.size() == 4) {
//            address_5 = stringBuilder.toString();
//            address.setType("address_5");
//        }
//
//
//        MainApiClient mainApiClient = new MainApiClient();
//        MainApiInterface apiService = mainApiClient.getClient().create(MainApiInterface.class);
//
//        Call<RegResponse> simpleResponseCall = apiService.getRegiUpdate(Update.Registration, name, "",
//                pincode_value,
//                getIntent().getStringExtra("mobile_1"),
//                getIntent().getStringExtra("mobile_2"),
//                androidId, SharedPrefs.getString(EditRegDataActivity.this, Share.key_ + RegReq.id));
//
//        simpleResponseCall.enqueue(new Callback<RegResponse>() {
//            @Override
//            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
//
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//
//                Log.e("response", "==>" + response.body().toString());
//                RegData regData = response.body().getData();
//                if (regData != null) {
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.name, regData.getName());
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.email, regData.getEmail());
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.pincode, regData.getPincode());
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.mobile_1, regData.getMobile_1());
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.mobile_2, regData.getMobile_2());
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.city, regData.getCity());
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.state, regData.getState());
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.country, regData.getCountry());
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.device_token, regData.getDevice_token());
//                    SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.id, regData.getId());
//
//                    addressArrayList.add(address);
//                    SharedPrefs.putAdListAddress(EditRegDataActivity.this, Share.address, addressArrayList);
//                }
//
//                AlertDialog alertDialog = new AlertDialog.Builder(EditRegDataActivity.this).create();
//                alertDialog.setTitle("Update");
//                alertDialog.setCancelable(false);
//                alertDialog.setMessage("Update successfully");
//                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        finish();
//                    }
//                });
//
//                alertDialog.show();
//            }
//
//            @Override
//            public void onFailure(Call<RegResponse> call, Throwable t) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//                AlertDialog alertDialog = new AlertDialog.Builder(EditRegDataActivity.this).create();
//                alertDialog.setTitle("Update Failed");
//                alertDialog.setCancelable(false);
//                alertDialog.setMessage("Something went to wrong. Please try again Later");
//                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        finish();
//                    }
//                });
//
//                alertDialog.show();
//            }
//        });
//
//
//    }

    private void updateusername() {


//        progressDialog = ProgressDialog.show(EditRegDataActivity.this, "", getString(R.string.loading), true, false);
//        progressDialog.show();
        showProgressDialog(EditRegDataActivity.this);

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String name = id_ed_fname.getText() + " " + id_ed_lname.getText();


        APIService apiService = new MainApiClient(EditRegDataActivity.this).getApiInterface();

        Call<response_main_username_update> simpleResponseCall = apiService.getRegiUpdate("" + SharedPrefs.getString(EditRegDataActivity.this, Share.key_ + RegReq.id), name, id_ed_email_id.getText().toString().trim(), currency_id, Locale.getDefault().getLanguage());

        simpleResponseCall.enqueue(new Callback<response_main_username_update>() {
            @Override
            public void onResponse(Call<response_main_username_update> call, Response<response_main_username_update> response) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
                hideProgressDialog();

                username_update_response_data regData = response.body().getData();

                if (response.body().getResponseCode().equals("1")) {

                    if (regData != null) {
                        Log.e("SUCCESS", "onResponse:========> ");
                        SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.name, regData.getName());
                        SharedPrefs.save(EditRegDataActivity.this, Share.key_ + RegReq.email, id_ed_email_id.getText().toString().trim());
                        SharedPrefs.save(EditRegDataActivity.this, SharedPrefs.currency_id, "" + currency_id);
                    }

                    AlertDialog alertDialog = new AlertDialog.Builder(EditRegDataActivity.this).create();
                    alertDialog.setTitle(getString(R.string.update));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.update_success));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });

                    alertDialog.show();

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(EditRegDataActivity.this).create();
                    alertDialog.setTitle(getString(R.string.update_fail));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(response.body().getResponseMessage());
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });

                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<response_main_username_update> call, Throwable t) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
                hideProgressDialog();
                AlertDialog alertDialog = new AlertDialog.Builder(EditRegDataActivity.this).create();
                alertDialog.setTitle(getString(R.string.update_fail));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.something_went_wrong_try_agaian));
                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

                alertDialog.show();
            }
        });


    }

    private void findVies() {
        id_back = findViewById(R.id.id_back);
        id_done = findViewById(R.id.id_done);
        sp_currency = findViewById(R.id.sp_currency);
        ll_sp_currency = findViewById(R.id.ll_sp_currency);
        if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
            ll_sp_currency.setVisibility(View.GONE);
        } else {
            ll_sp_currency.setVisibility(View.VISIBLE);
        }
        List<String> currency_list = new ArrayList<>();
        for (int i = 1; i < Share.currency_details.size(); i++) {
            currency_list.add(Share.currency_details.get(i).getCode());
        }
        ArrayAdapter<String> currency_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, currency_list);
        sp_currency.setAdapter(currency_adapter);

        for (int i = 0; i < Share.currency_details.size(); i++) {
            if (!SharedPrefs.getString(EditRegDataActivity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                try {
                    if ((SharedPrefs.getString(EditRegDataActivity.this, SharedPrefs.currency_id).equalsIgnoreCase(String.valueOf(Share.currency_details.get(i).getId())))) {
                        int spinnerpos1 = currency_adapter.getPosition(Share.currency_details.get(i).getCode());
                        currency_id = String.valueOf(Share.currency_details.get(i).getId());
                        sp_currency.setSelection(spinnerpos1);
                    }
                } catch (Exception e) {
                    if (i == 0) {
                        Toast.makeText(this, getString(R.string.please_select_country), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        sp_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < Share.currency_details.size(); i++) {
                    if ((sp_currency.getSelectedItem().toString().equalsIgnoreCase(Share.currency_details.get(i).getCode()))) {
                        currency_id = String.valueOf(Share.currency_details.get(i).getId());
                    }
                }
                Log.e(TAG, "onItemSelected: =====>" + currency_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        personal_detail = findViewById(R.id.personal_detail);
        id_ll_1 = findViewById(R.id.id_ll_1);
        id_ll_2 = findViewById(R.id.id_ll_2);
        id_ll_3 = findViewById(R.id.id_ll_3);
        id_ll_4 = findViewById(R.id.id_ll_4);
        id_ll_5 = findViewById(R.id.id_ll_5);
        id_ll_6 = findViewById(R.id.id_ll_6);
        id_ll_7 = findViewById(R.id.id_ll_7);
        id_ll_7 = findViewById(R.id.id_ll_7);
        id_ll_8 = findViewById(R.id.id_ll_8);
        id_ll_9 = findViewById(R.id.id_ll_9);
        id_ll_10 = findViewById(R.id.id_ll_10);
        address_detail = findViewById(R.id.address_detail);
        id_back = findViewById(R.id.id_back);
        address_data = findViewById(R.id.address_data);
        user_data = findViewById(R.id.user_data);
        id_ll_reset = findViewById(R.id.id_ll_reset);
        id_ll_save = findViewById(R.id.id_ll_save);
        id_ed_fname = findViewById(R.id.id_ed_fname);
        id_ed_lname = findViewById(R.id.id_ed_lname);
        id_ed_h_pincode = findViewById(R.id.id_ed_h_pincode);
        id_ed_h_homeno = findViewById(R.id.id_ed_h_homeno);
        id_ed_h_street = findViewById(R.id.id_ed_h_street);
        id_ed_h_landmark = findViewById(R.id.id_ed_h_landmark);
        id_ed_h_city = findViewById(R.id.id_ed_h_city);
        id_ed_h_state = findViewById(R.id.id_ed_h_state);
        id_ed_h_country = findViewById(R.id.id_ed_h_country);
        id_ed_mobile2 = findViewById(R.id.id_ed_mobile2);
        id_ed_email_id = findViewById(R.id.id_ed_email_id);
        id_ed_mobile2.setEnabled(false);
        if (!Share.countryCodeValue.equalsIgnoreCase("IN")) {
            id_ed_email_id.setEnabled(false);
        } else {
            id_ed_email_id.setEnabled(true);
        }
        moLlChangePassword = findViewById(R.id.llChangePassword);
    }

    public void cleardara(View... views) {
        for (View view : views) {
            ((EditText) view).setText("");
        }
    }

    public void setdimensH(LinearLayout... relativeLayouts) {
        for (LinearLayout re :
                relativeLayouts) {
            re.getLayoutParams().height = height;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
