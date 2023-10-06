package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.new_numberverify;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseFragment;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.auth.PhoneAuthActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.RegistrationActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.SellerRegistration;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.RegData;
import com.mobile.cover.photo.editor.back.maker.model.RegResponse;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_offer;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.selected;

public class FragmentAccount extends PrintPhotoBaseFragment {

    LinearLayout id_ll_register, id_ll_sign_in, id_ll_password, id_ll_mobile_no, id_signIn, id_signOut, id_ll_email_id;
    RelativeLayout id_ll_print_photo;
    Spinner sp_code;
    ImageView im_printphoto, im_user, im_key, id_iv_forget_password;
   // ProgressDialog progressDialog;
   // ProgressDialog pd;
    TextView tv_forget, become_seller;
    EditText id_mobile_number, id_password, id_email_id;
    Integer mobilecode;
    AlertDialog alertDialog;
    ArrayList<String> spinnercodeArray = new ArrayList<String>();
    ArrayList<String> temp_spinnercodeArray = new ArrayList<String>();
    ArrayList<String> spinnercountryArray = new ArrayList<String>();
    Integer country_code, lenght, code_position;
    Spinner sp_country;
    int selected_pos;
    boolean send = false;

    private Activity mContext;

    // Define a method to set the context
    public void setContext(Activity context) {
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_account, container, false);


        HomeMainActivity.iv_logout.setVisibility(View.GONE);
        HomeMainActivity.toolbar.setVisibility(View.GONE);
        HomeMainActivity.id_back.setVisibility(View.GONE);
        HomeMainActivity.btn_count.setVisibility(View.GONE);
        spinnercodeArray.clear();
        spinnercountryArray.clear();
        temp_spinnercodeArray.clear();
        Share.isregistration = true;
        Log.e("HERE", "onCreateView: ");
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
        Share.countryCodeValue = SharedPrefs.getString(mContext, SharedPrefs.country_code);

        Log.e("COUNTRYCODEVALUE", "onCreateView: =======>" + Share.countryCodeValue);
        setHeader();
        getDisplaySize();
        findViews(v);
        setDimens();
        intView();
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        for (int i = 0; i < temp_spinnercodeArray.size(); i++) {
            if (temp_spinnercodeArray.get(i).equalsIgnoreCase(SharedPrefs.getString(mContext, SharedPrefs.country_code))) {
                sp_country.setSelection(i);
            }
        }

      /*  if (id_email_id != null) {
            id_email_id.setText("");
        }
        if (id_mobile_number != null) {
            id_mobile_number.setText("");
        }
        if (id_password != null) {
            id_password.setText("");
        }*/


        if (SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT) == 0) {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);
        } else {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
        }

        if (Share.isRegistrationSuccess) {

            Share.isRegistrationSuccess = false;

            String mobile = SharedPrefs.getString(mContext, Share.key_ + RegReq.mobile_1);
            String email = SharedPrefs.getString(mContext, Share.key_ + RegReq.email);

            if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                id_mobile_number.setText(mobile);
            } else {
                id_email_id.setText(email);
                for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                    if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                        if (SharedPrefs.getString(mContext, SharedPrefs.country_code).equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
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


    private void intView() {

        id_ll_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RegistrationActivity.class);
                startActivity(intent);
            }
        });


        id_ll_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_mobile_number.setError(null);
                id_password.setError(null);
                id_email_id.setError(null);

                if (Share.countryCodeValue.equalsIgnoreCase("IN")) {


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

        String androidId = Share.firebaseToken;


        Log.e("androidId", "==>" + androidId);
        //progressDialog = ProgressDialog.show(mContext, "", getString(R.string.loading), true, false);
//        progressDialog.show();
        showProgressDialog(mContext);
        String cred;
        if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
            cred = id_mobile_number.getText().toString();
        } else {
            cred = id_email_id.getText().toString();
            for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                    if (SharedPrefs.getString(mContext, SharedPrefs.country_code).equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
                        cred = id_mobile_number.getText().toString();
                    }
                }
            }
        }


        APIService apiService = new MainApiClient(mContext).getApiInterface();

        Call<RegResponse> regResponseCall = apiService.getRegResponseLogin(cred, id_password.getText().toString(), androidId, "android", TimeZone.getDefault().getID(), Locale.getDefault().getLanguage());

        regResponseCall.enqueue(new Callback<RegResponse>() {
            @Override
            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
                hideProgressDialog();

                if (response.body() != null) {
                    Log.e("androidId", "==>" + response.body().getResponseCode());


                    //progressDialog.dismiss();
                    hideProgressDialog();
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        SharedPrefs.save(mContext, SharedPrefs.CART_COUNT, response.body().getCart_count());
                        HomeMainActivity.tv_nudge_cart_count.setText("" + SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT));
                        if (response.body().getData() == null) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
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
                            SharedPrefs.savePref(mContext, Share.key_reg_suc, true);
                            id_mobile_number.setText("");
                            id_password.setText("");
                            RegData regData = response.body().getData();
                            Share.isinternational = regData.getIs_international();
                            if (regData != null) {
                                SharedPrefs.save(mContext, Share.key_ + RegReq.name, regData.getName());
                                SharedPrefs.save(mContext, Share.key_ + RegReq.email, regData.getEmail());
                                SharedPrefs.save(mContext, Share.key_ + RegReq.mobile_1, regData.getMobile_1());
                                SharedPrefs.save(mContext, Share.key_ + RegReq.id, regData.getId());
                                SharedPrefs.save(mContext, SharedPrefs.currency_id, "" + regData.getCurrencyId());
                                SharedPrefs.save(mContext, SharedPrefs.country_id, regData.getCountry_id());
                                SharedPrefs.save(mContext, SharedPrefs.country_name, regData.getCountry_name());
//                                SharedPrefs.save(mContext, SharedPrefs.last_country_code, SharedPrefs.getString(mContext, SharedPrefs.country_code));
                                SharedPrefs.save(mContext, SharedPrefs.country_code, regData.getCountry_code());
                                Share.countryCodeValue = regData.getCountry_code();

                                Log.e("UID", "onResponse: " + regData.getId());
                                SharedPrefs.save(mContext, SharedPrefs.uid, regData.getId());
                                SharedPrefs.save(mContext, SharedPrefs.Sellerid, regData.getseller_id());
                                SharedPrefs.save(mContext, SharedPrefs.SELLER, regData.getis_approve());
                                SharedPrefs.save(mContext, SharedPrefs.TOKEN, regData.getToken());

                                Log.e("LOGINDATA", "onResponse: " + regData.getseller_id());
                                Log.e("TOKEN", "onResponse: ========>" + regData.getToken());
                                Log.e("LOGINDATA", "onResponse: " + regData.getis_approve());
                                Log.e("LOGINDATA", "onResponse: " + SharedPrefs.getString(mContext, SharedPrefs.uid));
                                Log.e("LOGINDATA", "onResponse: " + SharedPrefs.getString(mContext, Share.key_ + RegReq.mobile_1));


                                SharedPrefs.save(mContext, SharedPrefs.isapproved, regData.getis_approve());

                                Log.e("ISAPPROVED", "onResponse:==> " + regData.getis_approve());
                            }

                            if (Share.iscart) {
                                ImageView id_home = mContext.findViewById(R.id.id_home);
                                ImageView id_order = mContext.findViewById(R.id.id_order);
                                ImageView id_cart = mContext.findViewById(R.id.id_cart);
                                ImageView id_account = mContext.findViewById(R.id.id_account);
                                id_home.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                                id_account.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                                id_cart.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                                id_order.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                                id_offer.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                                selected = 2;
                                FragmentCart cart = new FragmentCart();
                                cart.setContext(mContext);
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frg_main,cart);
//                                fragmentTransaction.commit();
                                fragmentTransaction.commitAllowingStateLoss();


                            } else if (Share.display_isorder) {
                                ImageView id_home = mContext.findViewById(R.id.id_home);
                                ImageView id_order = mContext.findViewById(R.id.id_order);
                                ImageView id_cart = mContext.findViewById(R.id.id_cart);
                                ImageView id_account = mContext.findViewById(R.id.id_account);
                                id_home.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                                id_account.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                                id_cart.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                                id_order.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                                id_offer.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                                Share.isorder = true;
                                HomeMainActivity.selected = 1;
                                FragmentOrder order = new FragmentOrder();
                                order.setContext(mContext);
                                FragmentTransaction fragmentTransaction = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frg_main, order);
//                                fragmentTransaction.commit();
                                fragmentTransaction.commitAllowingStateLoss();

                            } else {


                                selected = 9;
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frg_main, new UserProfileFragment());
//                                fragmentTransaction.commit();
                                fragmentTransaction.commitAllowingStateLoss();

                            }


                        }


                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
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
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
                hideProgressDialog();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            signin();

                        }
                    });
                    alertDialog.show();
                } else {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
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
        // new login().execute();

    }


    private void setDimens() {

        int height = Share.screenHeight / 10;
        id_ll_register.getLayoutParams().height = height;
        id_ll_sign_in.getLayoutParams().height = height;
    }

    private void findViews(View v) {
        id_ll_print_photo = v.findViewById(R.id.id_ll_print_photo);
        id_ll_register = v.findViewById(R.id.id_ll_register);
        id_ll_sign_in = v.findViewById(R.id.id_ll_sign_in);
        id_ll_email_id = v.findViewById(R.id.id_ll_email_id);
        id_email_id = v.findViewById(R.id.id_email_id);


        id_iv_forget_password = v.findViewById(R.id.id_iv_forget_password);
        tv_forget = v.findViewById(R.id.tv_forget);
        id_iv_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
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


                ArrayAdapter<String> code_adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_print_item, spinnercodeArray);
                sp_code.setAdapter(code_adapter);


//                if (SharedPrefs.getString(mContext,SharedPrefs.country_code).equalsIgnoreCase("IN")||Share.country_mobile_code.get(country).getIs_branch()==1){
//                    spinnercodeArray.add("+" + Share.country_mobile_code.get(country).getPhonecode() + " (" + Share.country_mobile_code.get(country).getSortname() + ")" + " " + Share.country_mobile_code.get(country).getName());
//                }
                if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                    send = false;
                    above.setVisibility(View.VISIBLE);
                    below_mail.setVisibility(View.GONE);
                    sp_code.setSelection(code_adapter.getPosition("+91 (IN) India"));

                    UtilsKt.setEditTextMaxLength(tvdone, 10);

                } else {
                    UtilsKt.setEditTextMaxLength(tvdone, 9);
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


                sp_code.setEnabled(false);


                Button btnSendOtp = dialog.findViewById(R.id.btn_send_otp);
                btnSendOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Log.e("CLICKED", "onClick: " + send);

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

        sp_country = v.findViewById(R.id.sp_country);
        ArrayAdapter<String> code_adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_print_item, spinnercountryArray);
        sp_country.setAdapter(code_adapter);


        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_pos = position;
                for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                    if (i == selected_pos) {
                        Share.countryCodeValue = Share.country_mobile_code.get(i).getSortname();
                        if (!Share.setselection) {
                            Share.setselection = false;
                            Share.isregistration = true;
                            SharedPrefs.save(mContext, SharedPrefs.last_country_code, SharedPrefs.getString(mContext, SharedPrefs.country_code));
                        }
                        SharedPrefs.save(mContext, SharedPrefs.country_code, Share.country_mobile_code.get(i).getSortname());
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

        for (int i = 0; i < temp_spinnercodeArray.size(); i++) {
            if (temp_spinnercodeArray.get(i).equalsIgnoreCase(SharedPrefs.getString(mContext, SharedPrefs.country_code))) {
                sp_country.setSelection(i);
                Share.setselection = true;
            }
        }


        become_seller = v.findViewById(R.id.become_seller);
        become_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sellerresgistration = new Intent(mContext, SellerRegistration.class);
                startActivity(sellerresgistration);
            }
        });
        id_ll_password = v.findViewById(R.id.id_ll_password);
        id_ll_mobile_no = v.findViewById(R.id.id_ll_mobile_no);
        im_printphoto = v.findViewById(R.id.im_printphoto);
        im_user = v.findViewById(R.id.im_user);
        im_key = v.findViewById(R.id.im_key);
        id_signIn = v.findViewById(R.id.id_signIn);
        id_signIn = v.findViewById(R.id.id_signIn);
        id_mobile_number = v.findViewById(R.id.id_mobile_number);
        id_password = v.findViewById(R.id.id_password);
        id_mobile_number.setText("");
        id_password.setText("");
        id_mobile_number.setError(null);
        id_password.setError(null);
//        Share.countryCodeValue = "US";

//        if(Share.countryCodeValue.equalsIgnoreCase("")){
//            Share.countryCodeValue=getResources().getConfiguration().locale.getCountry();
//        }

        Log.e("COUNTRYCODEVALUE", "findViews: " + Share.countryCodeValue);

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

    private void verify(final String number, final String email, final Dialog dialog, final String code) {


        //pd = ProgressDialog.show(mContext, "", getString(R.string.loading), true, false);
        showProgressDialog(mContext);
        APIService api = new MainApiClient(mContext).getApiInterface();


        Call<new_numberverify> call = api.forget_verify(number, email, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<new_numberverify>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<new_numberverify> call, retrofit2.Response<new_numberverify> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    new_numberverify responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        dialog.dismiss();
                        //pd.dismiss();
                        hideProgressDialog();
                        String verify;
                        if (send) {
                            verify = "1";
                        } else {
                            verify = "0";
                        }
                        Intent intent = new Intent(mContext, PhoneAuthActivity.class);
                        intent.putExtra("mobno", number);
                        intent.putExtra("emailid", email);
                        intent.putExtra("verify", verify);
                        intent.putExtra("country_code", code);
                        Share.forgetpassword = 1;
                        startActivity(intent);
                    } else {
                        //pd.dismiss();
                        hideProgressDialog();
                        Toast.makeText(mContext, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //pd.dismiss();
                    hideProgressDialog();
                } else {
//                    pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(mContext, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<new_numberverify> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }

                    alertDialog = new AlertDialog.Builder(mContext).create();
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

                    alertDialog = new AlertDialog.Builder(mContext).create();
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


    private void getDisplaySize() {
        Display display = mContext.getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void setHeader() {
        TextView title = mContext.findViewById(R.id.title);
        title.setText(getString(R.string.sign_in));
        ImageView imageView = mContext.findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 0;
                HomeMainActivity.id_home.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_account.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_cart.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_order.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_offer.setColorFilter(ContextCompat.getColor(mContext, R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


    private class login extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.sign_in));
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
