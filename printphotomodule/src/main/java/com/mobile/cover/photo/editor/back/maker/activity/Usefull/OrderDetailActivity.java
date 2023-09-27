package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.order_cancel;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.complaints.Complain_ticket_system;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.getorderDetailsAdapter;
import com.mobile.cover.photo.editor.back.maker.model.getorderdetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DialogHelperKt.cancelOrderConfirmation;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity activity;
    RecyclerView recyclerview;
    getorderDetailsAdapter mAdapter;
    ImageView id_back, iv_send_complain;
    LinearLayout ll_gift, ll_lower, linear_gst;
    TextView tv_gst_charge, tv_date, tv_amount, tv_method, tv_order_id, tv_discount, tv_paid_amount, tv_shipping_amount, tv_gift;
    Button btn_complain, btn_cancel;
    RelativeLayout rl_track;
    ProgressDialog pd;
    int total, final_total, Shipping_charge;
    boolean cancelable = false;
    AlertDialog alertDialog;
    private List<getorderdetails> sqlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_orderdetail_screen_activity);
        activity = OrderDetailActivity.this;
        initviews();
        listener();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void listener() {
        id_back.setOnClickListener(this);
        iv_send_complain.setOnClickListener(this);
        btn_complain.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
//        btn_track.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.id_back).callOnClick();
    }

    private void initviews() {

        id_back = findViewById(R.id.id_back);
        ll_lower = findViewById(R.id.ll_lower);
        iv_send_complain = findViewById(R.id.iv_send_complain);
        ll_gift = findViewById(R.id.ll_gift);
        tv_gift = findViewById(R.id.tv_gift);
        linear_gst = findViewById(R.id.linear_gst);
        tv_gst_charge = findViewById(R.id.tv_gst_charge);
        if (getIntent().getStringExtra("is_gift").equalsIgnoreCase("1")) {
            ll_gift.setVisibility(View.VISIBLE);
        } else {
            ll_gift.setVisibility(View.GONE);
        }
        tv_gift.setText("+ " + Share.symbol + getIntent().getStringExtra("gift_charge"));

        btn_complain = findViewById(R.id.btn_complain);
        btn_cancel = findViewById(R.id.btn_cancel);
//        btn_track = findViewById(R.id.btn_track);
//        if (getIntent().getStringExtra("show_track").equalsIgnoreCase("1")) {
//            btn_track.setVisibility(View.VISIBLE);
//        } else {
//            btn_track.setVisibility(View.GONE);
//        }
        tv_date = findViewById(R.id.tv_date);
        tv_amount = findViewById(R.id.tv_amount);
        tv_paid_amount = findViewById(R.id.tv_paid_amount);
        tv_discount = findViewById(R.id.tv_discount);
        tv_method = findViewById(R.id.tv_method);
        tv_order_id = findViewById(R.id.tv_order_id);
        tv_shipping_amount = findViewById(R.id.tv_shipping_amount);
        tv_method.setText(getIntent().getStringExtra("transaction_type"));
        tv_order_id.setText(getIntent().getStringExtra("tr_id"));
        tv_shipping_amount.setText("+ " + Share.symbol + getIntent().getStringExtra("shipping"));

        String gstCharge = getIntent().getStringExtra("gstCharge");

        if (gstCharge.equalsIgnoreCase("0.0") || gstCharge.equalsIgnoreCase("0")) {
            linear_gst.setVisibility(View.GONE);
        } else {
            tv_gst_charge.setText("+ " + Share.symbol + gstCharge);
            linear_gst.setVisibility(View.VISIBLE);
        }

        recyclerview = findViewById(R.id.rv_details);
        mAdapter = new getorderDetailsAdapter(OrderDetailActivity.this, Share.order_item_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        passdata(getIntent().getStringExtra("pd_amount"), getIntent().getStringExtra("total_amount"),
                getIntent().getStringExtra("date"), getIntent().getStringExtra("code_discount"));
//        getorder();
        if (getIntent().getStringExtra("is_cancelable").equalsIgnoreCase("1")) {
            btn_cancel.setVisibility(View.VISIBLE);
            ll_lower.setVisibility(View.VISIBLE);
        } else {
            btn_cancel.setVisibility(View.GONE);
            ll_lower.setVisibility(View.GONE);
        }
    }

    private void cancel_order(int reasonId, String reasonDesc) {


        pd = ProgressDialog.show(OrderDetailActivity.this, "", getString(R.string.loading), true, false);

        APIService api = new MainApiClient(OrderDetailActivity.this).getApiInterface();

        String transactionId = getIntent().getStringExtra("tr_id");

        // Call<order_cancel> call = api.cancel_order(transactionId, Locale.getDefault().getLanguage());
        Call<order_cancel> call = api.cancel_order(transactionId, reasonId + "", reasonDesc, Locale.getDefault().getLanguage());


        call.enqueue(new Callback<order_cancel>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<order_cancel> call, retrofit2.Response<order_cancel> response) {
                if (response.isSuccessful()) {
                    order_cancel responseData = response.body();
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        pd.dismiss();
                        alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
                        alertDialog.setTitle(getString(R.string.order_cancelation));
                        alertDialog.setMessage(getString(R.string.order_cancelled));
                        alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                                Share.order_cancel = true;
                            }
                        });
                        alertDialog.show();
                    } else {
                        pd.dismiss();
                        alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
                        alertDialog.setTitle(getString(R.string.order_cancelation));
                        alertDialog.setMessage(responseData.getResponseMessage());
                        alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(OrderDetailActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<order_cancel> call, Throwable t) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            cancel_order(reasonId, reasonDesc);
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            cancel_order(reasonId, reasonDesc);
                        }
                    });
                    alertDialog.show();
                }

            }
        });

    }

    public void passdata(String paidamount, String Order_total, String date, String discount) {
        if (pd != null && pd.isShowing())
            pd.dismiss();
        tv_date.setText(getString(R.string.order_date) + date);
        tv_amount.setText(Share.symbol + Order_total);
        tv_paid_amount.setText(Share.symbol + paidamount);
        if (discount.equalsIgnoreCase("0")) {
            tv_discount.setText(Share.symbol + discount);
        } else {
            tv_discount.setText("- " + Share.symbol + discount);
        }
    }


    @Override
    public void onClick(View v) {
        if (v == id_back) {
            this.finish();
        }
        if (v == btn_complain) {
            Intent intent = new Intent(OrderDetailActivity.this, Complain_ticket_system.class);
            intent.putExtra("order_id", "" + tv_order_id.getText().toString());
            startActivity(intent);
        }
//        if (v == btn_track) {
//
////            new FinestWebView.Builder(OrderDetailActivity.this).show(getIntent().getStringExtra("trackinglink"));
////            Intent intent = new Intent(OrderDetailActivity.this, Track_web_view_activity.class);
////            intent.putExtra("trackinglink", ""+getIntent().getStringExtra("trackinglink"));
////            intent.putExtra("Orderid", getIntent().getStringExtra("tr_id"));
////            intent.putExtra("trackingcompany", getIntent().getStringExtra("trackingcompany"));
////            intent.putExtra("trackingid", getIntent().getStringExtra("trackingid"));
////            startActivity(intent);
//        }

        if (v == iv_send_complain) {
            Intent intent = new Intent(OrderDetailActivity.this, Complain_ticket_system.class);
            intent.putExtra("order_id", getIntent().getStringExtra("tr_id"));
            startActivity(intent);
        }
        if (v == btn_cancel) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderDetailActivity.this);
            alertDialog.setTitle(getString(R.string.order_cancelation));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.are_you_to_cancel_order));
            alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //  cancel_order();
                    dialog.dismiss();
                }
            });
            alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            // alertDialog.show();

            cancelOrderConfirmation(OrderDetailActivity.this, reason -> {

                cancel_order(reason.id, reason.reason);
            });


        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


}
