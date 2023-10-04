package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.setting_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.cart_address_check_model;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.save_address_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout.adapter.SelectAddressAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.New_address_save_update_activity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemDeleteListener;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnaddressItemClickListener;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddressActivity extends PrintPhotoBaseActivity implements View.OnClickListener {

    public static int REQ_ADD_UPDATE = 101;

    private static final long MIN_CLICK_INTERVAL = 1500;
    public static Activity activity;
    public static TextView title, tv_no_fnd;
    ImageView iv_add_address, id_back;
    RecyclerView rv_address;
    LinearLayout ll_address_availablity;
    SelectAddressAdapter addressRecyclerAdapter;
    //ProgressDialog pd;
    EditText ed_search;
    TextView tv_continue;
    private long mLastClickTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_select_activity_select_address_activity);
        activity = SelectAddressActivity.this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        findviews();
        initlistener();

    }

    private void findviews() {
        iv_add_address = findViewById(R.id.iv_add_address);
        tv_continue = findViewById(R.id.tv_continue);
        id_back = findViewById(R.id.id_back);
        ed_search = findViewById(R.id.ed_search);
        tv_no_fnd = findViewById(R.id.tv_no_fnd);


        rv_address = findViewById(R.id.rv_address);
        Share.saved_address_list.clear();
        if (Share.saved_address_list.size() > 0) {
            notifyaddressdata();
        } else {
            getaddress_data();
        }
        ll_address_availablity = findViewById(R.id.ll_address_availablity);
    }

    private void initlistener() {
        id_back.setOnClickListener(this);
        iv_add_address.setOnClickListener(this);
        tv_continue.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == id_back) {
            Share.saved_address_list.clear();
            Share.saved_address_list.addAll(Share.search_saved_address_list);
            finish();
        }
        if (v == tv_continue) {
            long currentClickTime = SystemClock.uptimeMillis();
            long elapsedTime = currentClickTime - mLastClickTime;
            mLastClickTime = currentClickTime;
            if (elapsedTime <= MIN_CLICK_INTERVAL)
                return;

            if (Share.saved_address_list.size() == 1) {
                if (Share.saved_address_list.get(0).getIsSelect()) {
                    final StringBuilder stringBuilder = new StringBuilder();
//                    if (Share.isinternational == 1) {
                    stringBuilder.append(Share.saved_address_list.get(0).getAddress());
                    stringBuilder.append(",");
                    stringBuilder.append(Share.saved_address_list.get(0).getAddress1());
                    stringBuilder.append(",");
                    stringBuilder.append(Share.saved_address_list.get(0).getCity().getName());
                    stringBuilder.append(",\n");
                    stringBuilder.append(Share.saved_address_list.get(0).getCity().getState().getName());
                    stringBuilder.append(",\n");
                    stringBuilder.append(Share.saved_address_list.get(0).getCountry().getName());
                    stringBuilder.append(" - " + Share.saved_address_list.get(0).getPincode() + ".");
                    stringBuilder.append("\n");
                    stringBuilder.append("Mobile No. : " + SharedPrefs.getString(activity, Share.key_ + RegReq.mobile_1));
                    stringBuilder.append("\n");
                    stringBuilder.append("Alternate Mobile No. : " + Share.saved_address_list.get(0).getAlternateMobile());

                    Share.address_value = stringBuilder.toString();
                    Share.deliver_name = Share.saved_address_list.get(0).getReceiverName();
                    Share.address_id = Share.saved_address_list.get(0).getId();
                    check_cod();
                } else {
                    Toast.makeText(activity, "Please select any address", Toast.LENGTH_SHORT).show();
                }

            } else if (Share.saved_address_list.size() == 0) {
                if (Share.search_saved_address_list.size() != 0) {
                    Toast.makeText(activity, "Please select one address!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Please add address!!", Toast.LENGTH_SHORT).show();
                }
            } else {
                for (int i = 0; i < Share.saved_address_list.size(); i++) {
                    if (Share.saved_address_list.get(i).getIsSelect()) {
                        final StringBuilder stringBuilder = new StringBuilder();
//                        if (Share.isinternational == 1) {
                        stringBuilder.append(Share.saved_address_list.get(i).getAddress());
                        stringBuilder.append(",");
                        stringBuilder.append(Share.saved_address_list.get(i).getAddress1());
                        stringBuilder.append(",");
                        stringBuilder.append(Share.saved_address_list.get(i).getCity().getName());
                        stringBuilder.append(",\n");
                        stringBuilder.append(Share.saved_address_list.get(i).getCity().getState().getName());
                        stringBuilder.append(",\n");
                        stringBuilder.append(Share.saved_address_list.get(i).getCountry().getName());
                        stringBuilder.append(" - " + Share.saved_address_list.get(i).getPincode() + ".");
                        stringBuilder.append("\n");
                        stringBuilder.append("Mobile No. : " + SharedPrefs.getString(activity, Share.key_ + RegReq.mobile_1));
                        stringBuilder.append("\n");
                        stringBuilder.append("Alternate Mobile No. : " + Share.saved_address_list.get(i).getAlternateMobile());

                        Share.address_value = stringBuilder.toString();
                        Share.deliver_name = Share.saved_address_list.get(i).getReceiverName();
                        Share.address_id = Share.saved_address_list.get(i).getId();
                        check_cod();
                    }
                }
            }
        }
        if (v == iv_add_address) {
            ed_search.setText("");
            Intent intent = new Intent(activity, New_address_save_update_activity.class);
            intent.putExtra("addresstype", "new");
            startActivityForResult(intent, REQ_ADD_UPDATE);
        }

    }

    private void getaddress_data() {


        //pd = ProgressDialog.show(SelectAddressActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(SelectAddressActivity.this);

        APIService api = new MainApiClient(SelectAddressActivity.this).getApiInterface();
        Log.e("USERID", "getaddress_data: " + SharedPrefs.getString(activity, SharedPrefs.uid));
        Call<save_address_response> call = api.get_saved_address(SharedPrefs.getString(activity, SharedPrefs.uid), Locale.getDefault().getLanguage());


        call.enqueue(new Callback<save_address_response>() {
            @Override
            public void onResponse(Call<save_address_response> call, retrofit2.Response<save_address_response> response) {
                save_address_response address_response = response.body();
                Log.e("RESPONSE", "onResponse: " + response.isSuccessful());
                Log.e("RESPONSE", "onResponse: " + response.message());
                if (response.body().getSuccess()) {
                    //pd.dismiss();
                    hideProgressDialog();
                    if (address_response.getData().size() != 0) {
                        Share.saved_address_list = address_response.getData();
                        Share.saved_address_list.get(0).setIsSelect(true);
                        notifyaddressdata();

                    } else {
                        ll_address_availablity.setVisibility(View.VISIBLE);
                        tv_continue.setVisibility(View.GONE);
                        Intent intent = new Intent(activity, New_address_save_update_activity.class);
                        intent.putExtra("addresstype", "new");
                        startActivityForResult(intent, REQ_ADD_UPDATE);
                        Toast.makeText(SelectAddressActivity.this, getString(R.string.no_address_available), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<save_address_response> call, Throwable t) {
                Log.e("FAILURE", "onFailure: " + t.getLocalizedMessage());
                Log.e("FAILURE", "onFailure: " + t.getMessage());
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
                            getaddress_data();

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
                            getaddress_data();
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


    private void deleteaddress(int type) {


//        if (pd != null) {
//            pd.dismiss();
//        }
        hideProgressDialog();
//        pd = ProgressDialog.show(SelectAddressActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(SelectAddressActivity.this);


        APIService api = new MainApiClient(SelectAddressActivity.this).getApiInterface();

        Call<save_address_response> call = api.delete_saved_address(String.valueOf(type), Locale.getDefault().getLanguage());


        call.enqueue(new Callback<save_address_response>() {
            @Override
            public void onResponse(Call<save_address_response> call, retrofit2.Response<save_address_response> response) {
                if (response.body().getSuccess()) {
                    save_address_response address_response = response.body();
                    //pd.dismiss();
                    hideProgressDialog();
                    Share.saved_address_list.clear();
                    Share.search_saved_address_list.clear();
                    Share.saved_address_list = address_response.getData();
                    Share.search_saved_address_list.addAll(Share.saved_address_list);
                    address_response.getData().get(0).setIsSelect(true);

                    notifyaddressdata();

                } else if (!response.body().getSuccess()) {
                    Toast.makeText(SelectAddressActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SelectAddressActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
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
                            getaddress_data();

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
                            getaddress_data();
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

    private void check_cod() {


        //pd = ProgressDialog.show(SelectAddressActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(SelectAddressActivity.this);

        Log.e("PARAMTERS", "check_cod: ======>" + Share.address_id);
        Log.e("PARAMTERS", "check_cod: ======>" + getIntent().getStringExtra("cart_id"));
        Log.e("PARAMTERS", "check_cod: ======>" + Locale.getDefault().getLanguage());
        APIService apiService = new MainApiClient(SelectAddressActivity.this).getApiInterface();
        Call<cart_address_check_model> call = apiService.check_address_cart(String.valueOf(Share.address_id), getIntent().getStringExtra("cart_id"), Locale.getDefault().getLanguage());

        call.enqueue(new Callback<cart_address_check_model>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<cart_address_check_model> call, Response<cart_address_check_model> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    //pd.dismiss();
                    hideProgressDialog();
                    cart_address_check_model responseData = response.body();
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        if (responseData.getData().getStatus() == 1) {

                            Log.e(TAG, "onResponse_data: " + new Gson().toJson(responseData.getData()));

                            Share.orderdetails = responseData.getData().getOrderDetails();
                            Log.e(TAG, "onResponse:=======>keychain " + responseData.getData().getExtra().getKeychainExist());

                            Log.e(TAG, "onResponse: " + responseData.getData().getCodAvailable());
                            Log.e(TAG, "onResponse: " + responseData.getData().getCodMessage());
                            Log.e(TAG, "onResponse: " + responseData.getData().getOrderId());

                            setting_response_data configurationData = DataHelperKt.getConfiguration(activity);
                            if (configurationData != null) {
                                configurationData.setGift_charge(responseData.getData().getDisplay_gift_charge());
                                DataHelperKt.saveConfiguration(activity, configurationData);
                            }


//                            Share.symbol = responseData.getData().getCurrency_symbol();
                            Share.iskeychainexsist = responseData.getData().getExtra().getKeychainExist();
                            boolean isCODAvail = responseData.getData().getDisplayCodButton() == 1;
                            Log.e("CHECKCLCIK", "onResponse: isCODAvail---"+isCODAvail );
                            Intent intent = new Intent(activity, PlaceOrderActivity.class);
                            Share.enabledpayment_gateway = responseData.getData().getEnabledPaymentGateways();
                            intent.putExtra("currency_code", responseData.getData().getCurrency_code());
                            intent.putExtra("COD", isCODAvail);
                            intent.putExtra("total_amount", getIntent().getStringExtra("total_amount"));
                            intent.putExtra("best_offer_code", responseData.getData().getExtra().getBestOffer());
                            intent.putExtra("best_offer_description", responseData.getData().getExtra().getBestOfferDescription());
                            intent.putExtra("user_id", SharedPrefs.getString(activity, Share.key_ + RegReq.id));
                            intent.putExtra("shipping", "0");
                            intent.putExtra("order_id", responseData.getData().getOrderId());
                            intent.putExtra("item_id", getIntent().getStringExtra("item_id"));
                            intent.putExtra("type", getIntent().getStringExtra("type"));
                            intent.putExtra("modelid", getIntent().getStringExtra("modelid"));
                            intent.putExtra("item_quantity", getIntent().getStringExtra("item_quantity"));
                            intent.putExtra("quantity", getIntent().getIntExtra("quantity", 1));
                            intent.putExtra("additional_shipping_charge", responseData.getData().getAdditional_shipping_charge());
                            intent.putExtra("gst_charges", responseData.getData().getOrderDetails().getGst_charges());
                            Log.e(TAG, "onResponse: " + responseData.getData().getOrderDetails().getGst_charges());
                            if (isCODAvail) {
                                Log.e("CHECKCLCIK", "onResponse: if---"+responseData.getData().getCodAvailable() );
                                intent.putExtra("COD_dialog", "" + responseData.getData().getCodAvailable());
                                intent.putExtra("COD_message", responseData.getData().getCodMessage());
                            } else {
                                Log.e("CHECKCLCIK", "onResponse: else---"+getIntent().getStringExtra("paid_amount"));
                                intent.putExtra("paid_amount", getIntent().getStringExtra("paid_amount"));
                            }
                            startActivity(intent);


                        } else {
                            Toast.makeText(SelectAddressActivity.this, responseData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(activity, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(activity, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<cart_address_check_model> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            check_cod();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            check_cod();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Share.saved_address_list.clear();
        Share.saved_address_list.addAll(Share.search_saved_address_list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ed_search != null) {
            ed_search.setText("");
        }
        Log.e("RESUME", "onResume: ");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (Share.saved_address_list.size() >= Share.search_saved_address_list.size()) {
            Log.e("SIZE", "onResume: " + Share.saved_address_list.size());
            ll_address_availablity.setVisibility(View.GONE);
            notifyaddressdata();
        }
    }

    private void notifyaddressdata() {
        Share.search_saved_address_list.clear();
        Share.search_saved_address_list.addAll(Share.saved_address_list);
        for (int i = 0; i < Share.saved_address_list.size(); i++) {
            if (Share.saved_address_list.size() == 1) {
                Share.saved_address_list.get(0).setIsSelect(true);
            } else {
                if (Share.saved_address_list.get(i).getIsSelect()) {
                    if (i != 0) {
                        Share.saved_address_list.get(0).setIsSelect(false);
                        Share.saved_address_list.get(i).setIsSelect(true);
                    }
                } else {
                    Share.saved_address_list.get(i).setIsSelect(false);
                }
            }
        }

        tv_continue.setVisibility(View.VISIBLE);

        addressRecyclerAdapter = new SelectAddressAdapter(activity, Share.saved_address_list);
        rv_address.setLayoutManager(new LinearLayoutManager(activity));
        rv_address.setAdapter(addressRecyclerAdapter);
        addressRecyclerAdapter.notifyDataSetChanged();

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Share.saved_address_list = addressRecyclerAdapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        addressRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickLister(View v, int position) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;
                Intent intent = new Intent(activity, New_address_save_update_activity.class);
                intent.putExtra("addresstype", "edit");
                Share.addressposition = position;
                Share.address_id = Share.saved_address_list.get(position).getId();
                startActivityForResult(intent, REQ_ADD_UPDATE);

            }
        });

        addressRecyclerAdapter.setOnItemDeletekLister(new OnItemDeleteListener() {
            @Override
            public void onItemDeletekLister(View v, final int position) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle(getString(R.string.delete));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.delete_address_sentence));
                alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Log.e("DELETE", "onClick: =====>" + position);
                        deleteaddress(Share.saved_address_list.get(position).getId());

                    }
                });
                alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                alertDialog.create().show();
            }
        });

        addressRecyclerAdapter.setOnaddressItemClickListener(new OnaddressItemClickListener() {
            @Override
            public void onaddressItemClickLister(View v, int position) {
                final StringBuilder stringBuilder = new StringBuilder();
//                if (Share.isinternational == 1) {
                stringBuilder.append(Share.saved_address_list.get(position).getAddress());
                stringBuilder.append(",");
                stringBuilder.append(Share.saved_address_list.get(position).getAddress1());
                stringBuilder.append(",");
                stringBuilder.append(Share.saved_address_list.get(position).getCity().getName());
                stringBuilder.append(",\n");
                stringBuilder.append(Share.saved_address_list.get(position).getCity().getState().getName());
                stringBuilder.append(",\n");
                stringBuilder.append(Share.saved_address_list.get(position).getCountry().getName());
                stringBuilder.append(" - " + Share.saved_address_list.get(position).getPincode() + ".");
                stringBuilder.append("\n");
                stringBuilder.append("Mobile No. : " + Share.saved_address_list.get(position).getAlternateMobile());

                Share.address_value = stringBuilder.toString();
                Share.deliver_name = Share.saved_address_list.get(position).getReceiverName();
                Share.address_id = Share.saved_address_list.get(position).getId();

                Log.e("SIZE", "onaddressItemClickLister: " + Share.saved_address_list.size());
                Log.e("SIZE", "onaddressItemClickLister: " + Share.search_saved_address_list.size());

                for (int i = 0; i < Share.search_saved_address_list.size(); i++) {
                    Share.search_saved_address_list.get(i).setIsSelect(false);
                    if (Share.saved_address_list.get(position).getId() == Share.search_saved_address_list.get(i).getId()) {
                        Share.search_saved_address_list.get(i).setIsSelect(true);
                    }
                }
                for (int j = 0; j < Share.saved_address_list.size(); j++) {
                    Share.saved_address_list.get(j).setIsSelect(false);
                    if (j == position) {
                        Share.saved_address_list.get(j).setIsSelect(true);
                    }
                }
                addressRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_ADD_UPDATE && resultCode == RESULT_OK) {
            getaddress_data();
        }
    }
}
