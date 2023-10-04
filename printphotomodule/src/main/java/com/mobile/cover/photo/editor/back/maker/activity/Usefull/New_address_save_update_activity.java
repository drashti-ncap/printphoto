package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Country_state_city_code_response;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.save_address_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class New_address_save_update_activity extends PrintPhotoBaseActivity implements View.OnClickListener {
    private static final long MIN_CLICK_INTERVAL = 2500;
    LinearLayout id_ll_save, ll_city, ll_state;
    Spinner sp_country, sp_states, sp_district, sp_code;
    EditText id_ed_address, id_ed_address_1, id_ed_h_pincode, id_ed_h_deliver_name, id_ed_mobile_no;
    ImageView id_back, id_ll_reset;
    TextView title, title1, tv_reg_mobile_no;
    ArrayList<String> spinnerstateArray = new ArrayList<String>();
    ArrayList<String> spinnercodeArray = new ArrayList<String>();
    ArrayList<String> spinnercountryArray = new ArrayList<String>();
    ArrayList<String> spinnercitiesArray = new ArrayList<String>();
    Activity activity;
    int countrypos, statepos, mobilecode;
//    ProgressDialog pd;
    boolean country_selection = false, code_selection = false;
    String country_code, country_sort_num, country_name, state_name, city_name, Country_reg = "", State_reg = "", City_reg = "";
    private long mLastClickTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        activity = New_address_save_update_activity.this;
        if (Share.Country_state_city.size() == 0) {
            getcountry_states_cities_code();
        } else {
            initviews();
            initlistener();
//            if (SharedPrefs.getString(New_address_save_update_activity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
            ll_city.setVisibility(View.VISIBLE);
            ll_state.setVisibility(View.VISIBLE);
//            } else {
//                ll_city.setVisibility(View.GONE);
//                ll_state.setVisibility(View.GONE);
//            }
        }

    }

    private void getcountry_states_cities_code() {

        //pd = ProgressDialog.show(New_address_save_update_activity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(New_address_save_update_activity.this);

        APIService api = new MainApiClient(New_address_save_update_activity.this).getApiInterface();


        Call<Country_state_city_code_response> call = api.getcountry_states_city_code();


        call.enqueue(new Callback<Country_state_city_code_response>() {
            @Override
            public void onResponse(Call<Country_state_city_code_response> call, Response<Country_state_city_code_response> response) {
                if (response.isSuccessful()) {
                    //pd.dismiss();
                    hideProgressDialog();
                    Country_state_city_code_response customimage_response = response.body();
                    Share.Country_state_city.addAll(customimage_response.getData());
                    initviews();
                    initlistener();
//                    if (SharedPrefs.getString(New_address_save_update_activity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                    ll_city.setVisibility(View.VISIBLE);
                    ll_state.setVisibility(View.VISIBLE);
//                    } else {
//                        ll_city.setVisibility(View.GONE);
//                        ll_state.setVisibility(View.GONE);
//                    }
                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Country_state_city_code_response> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //pd.dismiss();
                            hideProgressDialog();
                            getcountry_states_cities_code();
                        }
                    });
                    alertDialog.show();
                } else if (t.toString().contains("Handshake failed") || t.toString().contains("Failed to connect to printphoto")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle(getString(R.string.server_error));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.server_under_maintenance));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //pd.dismiss();
                            hideProgressDialog();
                            finish();
                        }
                    });
                    alertDialog.show();
                } else {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //pd.dismiss();
                            hideProgressDialog();
                            getcountry_states_cities_code();

                        }
                    });
                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //pd.dismiss();
                            hideProgressDialog();
                            finish();
                        }
                    });
                    alertDialog.show();
                }

            }
        });

    }


    private void initviews() {
        id_ll_save = findViewById(R.id.id_ll_save);

        ll_state = findViewById(R.id.ll_state);
        ll_city = findViewById(R.id.ll_city);

        sp_country = findViewById(R.id.sp_country);
        sp_country.setEnabled(false);
        sp_states = findViewById(R.id.sp_states);
        sp_district = findViewById(R.id.sp_district);
        sp_code = findViewById(R.id.sp_code);
        sp_code.setEnabled(false);

//        id_ed_h_landmark = findViewById(R.id.id_ed_h_landmark);
        id_ed_address = findViewById(R.id.id_ed_address);
        id_ed_address_1 = findViewById(R.id.id_ed_address_1);
        id_ed_h_pincode = findViewById(R.id.id_ed_h_pincode);
        if (SharedPrefs.getString(New_address_save_update_activity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
            id_ed_h_pincode.setHint(getString(R.string.pincode));
        } else {
            id_ed_h_pincode.setHint(getString(R.string.zipcode));
        }
        id_ed_h_deliver_name = findViewById(R.id.id_ed_h_deliver_name);
        id_ed_mobile_no = findViewById(R.id.id_ed_mobile_no);

        title = findViewById(R.id.title);
        title1 = findViewById(R.id.title1);
        tv_reg_mobile_no = findViewById(R.id.tv_reg_mobile_no);
        tv_reg_mobile_no.setText(SharedPrefs.getString(activity, Share.key_ + RegReq.mobile_1));

        id_back = findViewById(R.id.id_back);
        id_ll_reset = findViewById(R.id.id_ll_reset);

        fill_data_spinner_method();

        if (getIntent().getStringExtra("addresstype").equalsIgnoreCase("edit")) {
            id_ed_address.setText(Share.saved_address_list.get(Share.addressposition).getAddress());
            id_ed_address_1.setText(Share.saved_address_list.get(Share.addressposition).getAddress1());
            id_ed_h_pincode.setText(Share.saved_address_list.get(Share.addressposition).getPincode());
            id_ed_h_deliver_name.setText(Share.saved_address_list.get(Share.addressposition).getReceiverName());
            id_ed_mobile_no.setText(Share.saved_address_list.get(Share.addressposition).getAlternateMobile());
        }


        if (Share.countryCodeValue != null && Share.countryCodeValue.equalsIgnoreCase("IN")) {
            Log.e("COUNTRY", "onClick: IN");
            UtilsKt.setEditTextMaxLength(id_ed_mobile_no, 10);
        } else if (Share.countryCodeValue != null && Share.countryCodeValue.equalsIgnoreCase("SA")) {
            Log.e("COUNTRY", "onClick: SA");
            UtilsKt.setEditTextMaxLength(id_ed_mobile_no, 9);
        } else {
            UtilsKt.setEditTextMaxLength(id_ed_mobile_no, 20);
        }


    }

    private void fill_data_spinner_method() {
        spinnercodeArray.clear();
        spinnercountryArray.clear();
        spinnercountryArray.add(getString(R.string.select_country));
        for (int country = 0; country < Share.Country_state_city.size(); country++) {
            spinnercodeArray.add("+" + Share.Country_state_city.get(country).getPhonecode() + " (" + Share.Country_state_city.get(country).getSortname() + ")" + " " + Share.Country_state_city.get(country).getName());
            if (Share.Country_state_city.get(country).getName().equalsIgnoreCase(SharedPrefs.getString(New_address_save_update_activity.this, SharedPrefs.country_name))) {
//                sp_country.setEnabled(false);
            }
            spinnercountryArray.add(Share.Country_state_city.get(country).getName());
            if (getIntent().getStringExtra("addresstype").equalsIgnoreCase("edit")) {
//                if (!SharedPrefs.getString(activity, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
//                    country_name = "" + Share.saved_address_list.get(Share.addressposition).getCountry().getName();
//                    for (int i = 0; i < Share.Country_state_city.size(); i++) {
//                        if (Share.Country_state_city.get(i).getName().equalsIgnoreCase(country_name)) {
//                            country_sort_num = "+" + Share.Country_state_city.get(i).getPhonecode() + " (" + Share.Country_state_city.get(i).getSortname() + ")" + " " + Share.Country_state_city.get(i).getName();
//                        }
//                    }
//                } else {
                country_name = "" + Share.saved_address_list.get(Share.addressposition).getCity().getState().getCountry().getName();
                state_name = "" + Share.saved_address_list.get(Share.addressposition).getCity().getState().getName();
                city_name = "" + Share.saved_address_list.get(Share.addressposition).getCity().getName();
                Log.e("NAME", "fill_data_spinner_method: " + country_name + "=====>" + state_name + "=====>" + city_name);
                for (int i = 0; i < Share.Country_state_city.size(); i++) {
                    if (Share.Country_state_city.get(i).getName().equalsIgnoreCase(country_name)) {
                        country_sort_num = "+" + Share.Country_state_city.get(i).getPhonecode() + " (" + Share.Country_state_city.get(i).getSortname() + ")" + " " + Share.Country_state_city.get(i).getName();
                    }
                }
//                }

            } else {
                if (SharedPrefs.getString(New_address_save_update_activity.this, SharedPrefs.country_code).equalsIgnoreCase(Share.Country_state_city.get(country).getSortname())) {
                    country_code = Share.Country_state_city.get(country).getName();
                    country_sort_num = "+" + Share.Country_state_city.get(country).getPhonecode() + " (" + Share.Country_state_city.get(country).getSortname() + ")" + " " + Share.Country_state_city.get(country).getName();
                }
            }
        }

        ArrayAdapter<String> country_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, spinnercountryArray);
        sp_country.setAdapter(country_adapter);
        if (getIntent().getStringExtra("addresstype").equalsIgnoreCase("edit")) {
            int spinnerpos1 = country_adapter.getPosition(country_name);
            sp_country.setSelection(spinnerpos1);
            Log.e("SPINNERPOS", "onItemSelected: " + spinnerpos1);
        } else {
            int spinnerpos1 = country_adapter.getPosition(country_code);
            sp_country.setSelection(spinnerpos1);
        }


        ArrayAdapter<String> code_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, spinnercodeArray);
        sp_code.setAdapter(code_adapter);
        int spinnerpos = code_adapter.getPosition(country_sort_num);
        sp_code.setSelection(spinnerpos);


        sp_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mobilecode = Integer.parseInt(sp_code.getSelectedItem().toString().replaceAll("[^0-9]", ""));
                if (code_selection) {
                    sp_country.setSelection(position + 1);
                    country_selection = false;
                } else {
                    code_selection = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("POSITION", "onItemSelected:=======>Country " + position);
                spinnerstateArray.clear();

                spinnerstateArray.add(getString(R.string.select_state));
                if (country_selection) {
                    if (position != 0) {
                        sp_code.setSelection(position - 1);
                    }
                    code_selection = false;
                } else {
                    country_selection = true;
                }

                if (position != 0) {
                    position = position - 1;
                    countrypos = position;
                    for (int states = 0; states < Share.Country_state_city.get(position).getStates().size(); states++) {
                        spinnerstateArray.add(Share.Country_state_city.get(position).getStates().get(states).getName());
                    }
                }

                ArrayAdapter<String> state_adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_print_item, spinnerstateArray);
                sp_states.setAdapter(state_adapter);
                if (getIntent().getStringExtra("addresstype").equalsIgnoreCase("edit")) {
                    int spinnerpos = state_adapter.getPosition(state_name);
                    Log.e("SPINNERPOS", "onItemSelected: " + spinnerpos);
                    sp_states.setSelection(spinnerpos);
                    Country_reg = Share.Country_state_city.get(position).getId().toString();
                    State_reg = "";
                    City_reg = "";
                } else {
                    Country_reg = Share.Country_state_city.get(position).getId().toString();
                    State_reg = "";
                    City_reg = "";
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

                if (getIntent().getStringExtra("addresstype").equalsIgnoreCase("edit")) {
                    int spinnerpos = city_adapter.getPosition(city_name);
                    sp_district.setSelection(spinnerpos);
                    Log.e("SPINNERPOS", "onItemSelected: " + spinnerpos);
                    State_reg = Share.Country_state_city.get(countrypos).getStates().get(position).getId().toString();
                    City_reg = "";
                } else {
                    State_reg = Share.Country_state_city.get(countrypos).getStates().get(position).getId().toString();
                    City_reg = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("POSITION", "onItemSelected:=======>City " + position);
                if (position != 0) {
                    position = position - 1;
                    City_reg = Share.Country_state_city.get(countrypos).getStates().get(statepos).getCities().get(position).getId().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initlistener() {
        id_ll_reset.setOnClickListener(this);
        id_ll_save.setOnClickListener(this);
        id_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == id_ll_reset) {
            id_ed_h_deliver_name.setError(null);
            id_ed_mobile_no.setError(null);
            id_ed_h_pincode.setError(null);
            id_ed_address.setError(null);
            id_ed_address_1.setError(null);

            id_ed_address.setText("");
            id_ed_address_1.setText("");
            id_ed_h_pincode.setText("");
            id_ed_h_deliver_name.setText("");
            id_ed_mobile_no.setText("");
//            Country_reg = "";
            State_reg = "";
            City_reg = "";
//            sp_country.setSelection(0);
            sp_district.setSelection(0);
            sp_states.setSelection(0);
        }

        if (v == id_ll_save) {
            long currentClickTime = SystemClock.uptimeMillis();
            long elapsedTime = currentClickTime - mLastClickTime;
            mLastClickTime = currentClickTime;
            if (elapsedTime <= MIN_CLICK_INTERVAL)
                return;

            Log.e("DATA", "onClick: " + Country_reg + "======>" + State_reg + "======>" + City_reg);
            id_ed_h_deliver_name.setError(null);
            id_ed_mobile_no.setError(null);
            id_ed_h_pincode.setError(null);
            id_ed_address.setError(null);
            id_ed_address_1.setError(null);

            if (SharedPrefs.getString(New_address_save_update_activity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
                if (id_ed_h_deliver_name.getText().toString().trim().length() == 0 || id_ed_h_deliver_name.getText().toString().trim().equalsIgnoreCase("")) {
                    id_ed_h_deliver_name.setError(getString(R.string.please_enter_name));
                    id_ed_h_deliver_name.requestFocus();
                } else if (id_ed_mobile_no.getText().toString().trim().length() != 10 || id_ed_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                    id_ed_mobile_no.setError(getString(R.string.valid_mobile));
                    id_ed_mobile_no.requestFocus();
                } else if (id_ed_h_pincode.getText().toString().trim().length() != 6 || id_ed_h_pincode.getText().toString().trim().equalsIgnoreCase("") || !id_ed_h_pincode.getText().toString().matches("\\d+")) {
                    id_ed_h_pincode.setError(getString(R.string.valid_pincode));
                    id_ed_h_pincode.requestFocus();
                } else if (id_ed_address.getText().toString().length() == 0 || id_ed_address.getText().toString().trim().equalsIgnoreCase("")) {
                    id_ed_address.setError(getString(R.string.pls_enter_address));
                    id_ed_address.requestFocus();
                } else if (id_ed_address_1.getText().toString().length() == 0 || id_ed_address_1.getText().toString().trim().equalsIgnoreCase("")) {
                    id_ed_address_1.setError(getString(R.string.pls_enter_address));
                    id_ed_address_1.requestFocus();
                } else if (Country_reg.equalsIgnoreCase("")) {
                    Toast.makeText(activity, getString(R.string.pls_select_country), Toast.LENGTH_SHORT).show();
                } else if (State_reg.equalsIgnoreCase("") || State_reg.equalsIgnoreCase("1")) {
                    Toast.makeText(activity, getString(R.string.pls_select_state), Toast.LENGTH_SHORT).show();
                } else if (City_reg.equalsIgnoreCase("")) {
                    Toast.makeText(activity, getString(R.string.pls_select_city), Toast.LENGTH_SHORT).show();
                } else {
                    Save_address();
                }
            } else {
                if (id_ed_h_deliver_name.getText().toString().trim().length() == 0 || id_ed_h_deliver_name.getText().toString().trim().equalsIgnoreCase("")) {
                    id_ed_h_deliver_name.setError(getString(R.string.please_enter_name));
                    id_ed_h_deliver_name.requestFocus();
                } else if (id_ed_mobile_no.getText().toString().trim().length() == 0 || id_ed_mobile_no.getText().toString().trim().equalsIgnoreCase("")) {
                    id_ed_mobile_no.setError(getString(R.string.valid_mobile));
                    id_ed_mobile_no.requestFocus();
                } else if (id_ed_h_pincode.getText().toString().trim().length() == 0 || id_ed_h_pincode.getText().toString().trim().equalsIgnoreCase("")) {
                    id_ed_h_pincode.setError(getString(R.string.valid_zipcode));
                    id_ed_h_pincode.requestFocus();
                } else if (id_ed_address.getText().toString().length() == 0 || id_ed_address.getText().toString().trim().equalsIgnoreCase("")) {
                    id_ed_address.setError(getString(R.string.pls_enter_address));
                    id_ed_address.requestFocus();
                } else if (id_ed_address_1.getText().toString().length() == 0 || id_ed_address_1.getText().toString().trim().equalsIgnoreCase("")) {
                    id_ed_address_1.setError(getString(R.string.pls_enter_address));
                    id_ed_address_1.requestFocus();
                } else if (Country_reg.equalsIgnoreCase("")) {
                    Toast.makeText(activity, getString(R.string.pls_select_country), Toast.LENGTH_SHORT).show();
                } else if (State_reg.equalsIgnoreCase("") || State_reg.equalsIgnoreCase("1")) {
                    Toast.makeText(activity, getString(R.string.pls_select_state), Toast.LENGTH_SHORT).show();
                } else if (City_reg.equalsIgnoreCase("")) {
                    Toast.makeText(activity, getString(R.string.pls_select_city), Toast.LENGTH_SHORT).show();
                } else {
                    Save_address();
                }
            }

        }
        if (v == id_back) {
            finish();
        }
    }

    private void Save_address() {


        Share.hideKeyboard(New_address_save_update_activity.this);
//        if (pd != null) {
//            pd.dismiss();
//        }
        hideProgressDialog();
//        pd = ProgressDialog.show(New_address_save_update_activity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(New_address_save_update_activity.this);
        String addressid;
        if (getIntent().getStringExtra("addresstype").equalsIgnoreCase("edit")) {
            addressid = String.valueOf(Share.address_id);
        } else {
            addressid = "";
        }
        String mobile;
        if (id_ed_mobile_no.getText().toString().equalsIgnoreCase("")) {
            mobile = "";
        } else {
            mobile = id_ed_mobile_no.getText().toString();
        }
        APIService api = new MainApiClient(New_address_save_update_activity.this).getApiInterface();

        Call<save_address_response> call;
//        if (SharedPrefs.getString(New_address_save_update_activity.this, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
        call = api.save_address(addressid,
                id_ed_h_deliver_name.getText().toString(),
                mobile,
                SharedPrefs.getString(activity, SharedPrefs.uid),
                id_ed_address.getText().toString(), id_ed_address_1.getText().toString()
                , City_reg, "", id_ed_h_pincode.getText().toString(), Locale.getDefault().getLanguage());
//        } else {
//
//            call = api.save_address(addressid,
//                    id_ed_h_deliver_name.getText().toString(),
//                    mobile,
//                    SharedPrefs.getString(activity, SharedPrefs.uid),
//                    id_ed_address.getText().toString(), id_ed_address_1.getText().toString()
//                    , "", Country_reg, id_ed_h_pincode.getText().toString());
//        }


        call.enqueue(new Callback<save_address_response>() {
            @Override
            public void onResponse(Call<save_address_response> call, retrofit2.Response<save_address_response> response) {
                save_address_response address_response = response.body();
//                pd.dismiss();
                hideProgressDialog();
                if (response.body().getSuccess()) {
                    address_response.getData().get(0).setIsSelect(true);
                    Log.e("RESPONSE_SIZE", "onResponse: " + address_response.getData().size());
                    Share.saved_address_list.clear();
                    Share.saved_address_list = address_response.getData();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                    Toast.makeText(New_address_save_update_activity.this, address_response.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (!address_response.getSuccess()) {
                    Toast.makeText(New_address_save_update_activity.this, address_response.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(New_address_save_update_activity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<save_address_response> call, Throwable t) {
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //pd.dismiss();
                            hideProgressDialog();
                            Save_address();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //pd.dismiss();
                            hideProgressDialog();
                            Save_address();
                        }
                    });
                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
//                            pd.dismiss();
                            hideProgressDialog();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
