package com.mobile.cover.photo.editor.back.maker.Paymentintegration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.final_status_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.get_final_status_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout.PlaceOrderActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout.SelectAddressActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FirebaseEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.rateandfeedback.library_feedback.FeedbackUtils;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.Commen.Share.isorder;


public class StatusActivity extends PrintPhotoBaseActivity {

   // ProgressDialog pd;
    LinearLayout ll_success;
    TextView tv_amnt, tv_order_id, tv_transaction_id, tv_mob, tv_time, tv_success,tv_prepaid;
    Button btn_continue;
    FirebaseAnalytics firebaseAnalytics;
    ImageView iv_gif;
    PackageInfo pInfo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        initviews();
        firebaseAnalytics = FirebaseAnalytics.getInstance(StatusActivity.this);
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (Share.paymenttype == 3) {
            if (getIntent().getData().getPathSegments().size() != 0) {
                if (getIntent().getData().getPathSegments().get(0).equalsIgnoreCase("paypal_continue")) {
                    if (PlaceOrderActivity.activity != null) {
                        PlaceOrderActivity.activity.finish();
                    }
                    comman_new_place_order(getIntent().getData().getPathSegments().get(1), 0);
                } else {
                    finish();
                }
            }
        } else {
            comman_new_place_order(getIntent().getStringExtra("order_new_Id"), 0);
        }


    }

    private void initviews() {
        ll_success = findViewById(R.id.ll_success);
        tv_amnt = findViewById(R.id.tv_amnt);
        tv_order_id = findViewById(R.id.tv_order_id);
        tv_transaction_id = findViewById(R.id.tv_transaction_id);
        if (Share.paymenttype == 2) {
            tv_transaction_id.setVisibility(View.GONE);
        } else {
            tv_transaction_id.setVisibility(View.VISIBLE);
        }
        tv_mob = findViewById(R.id.tv_mob);
        tv_time = findViewById(R.id.tv_time);
        tv_success = findViewById(R.id.tv_success);

        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefs.save(StatusActivity.this, SharedPrefs.CART_COUNT, 0);
                if (SelectAddressActivity.activity != null) {
                    SelectAddressActivity.activity.finish();
                }
                finish();
                Share.upload_success = true;
            }
        });

        iv_gif = findViewById(R.id.iv_gif);
    }


    private void comman_new_place_order(final String orderid, int cancel) {


        //pd = ProgressDialog.show(StatusActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(StatusActivity.this);
        APIService api = new MainApiClient(StatusActivity.this).getApiInterface();
        String paymentId, signature, orderId;
        if (Share.paymenttype == 2) {
            paymentId = "";
            signature = "";
            orderId = "";
        } else {
            paymentId = getIntent().getStringExtra("paymentId");
            signature = getIntent().getStringExtra("signature");
            orderId = getIntent().getStringExtra("orderId");
        }

        APIService apiService = new MainApiClient(StatusActivity.this).getApiInterface();

        Call<get_final_status_response> call = apiService.get_final_status(orderid, cancel, orderId, paymentId, signature, "Android", pInfo.versionName, Locale.getDefault().getLanguage());
        call.enqueue(new Callback<get_final_status_response>() {
            public static final String TAG = "COMMAN_PLACE_ORDER";

            @Override
            public void onResponse(Call<get_final_status_response> call, Response<get_final_status_response> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                Log.e(TAG, "onResponse: " + response.message());
                if (response.isSuccessful()) {
                    final get_final_status_response responseData = response.body();
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        //pd.dismiss();
                        hideProgressDialog();
                        if (responseData.getData().getStatus() == 1) {


                            Share.symbol = responseData.getData().getCurrency_symbol();
                            Bundle params = new Bundle();
                            params.putInt("Payment_Completed", 1);
                            // firebaseAnalytics.logEvent("order_confirm", params);

                            logPurchaseEvent(responseData.getData());


                            AlertDialog alertDialog = new AlertDialog.Builder(StatusActivity.this).create();
                            alertDialog.setTitle(getString(R.string.order_place));
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(responseData.getData().getMessage());
                            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    isorder = true;
                                    ll_success.setVisibility(View.VISIBLE);
//                                    if (SharedPrefs.getInt(StatusActivity.this, SharedPrefs.REVIEW) == 0) {
//                                        displayalert();
//                                    }
                                    tv_success.setText(getString(R.string.order_received));
                                    tv_order_id.setText("Order ID:" + responseData.getData().getOrderId());
                                    if (responseData.getData().getTransactionType().equalsIgnoreCase("COD")) {
                                        tv_transaction_id.setVisibility(View.GONE);
                                    } else {
                                        tv_transaction_id.setVisibility(View.VISIBLE);
                                    }
                                    Glide.with(StatusActivity.this).asGif().load(R.drawable.done).into(iv_gif);
                                    tv_transaction_id.setText("Transaction ID:" + responseData.getData().getTransactionId());
                                    tv_amnt.setText(Share.symbol + responseData.getData().getPaid_amount());
                                    tv_mob.setText("Mo: " + SharedPrefs.getString(StatusActivity.this, Share.key_ + RegReq.mobile_1));
                                    tv_time.setText("" + Calendar.getInstance().getTime());
                                    btn_continue.setVisibility(View.VISIBLE);
                                }
                            });
                            alertDialog.show();
                        } else {

                            AlertDialog alertDialog = new AlertDialog.Builder(StatusActivity.this).create();
                            alertDialog.setTitle(getString(R.string.failure));
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(responseData.getData().getMessage());
                            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    isorder = true;
                                    ll_success.setVisibility(View.VISIBLE);
                                    btn_continue.setVisibility(View.GONE);
                                    Glide.with(StatusActivity.this).asGif().load(R.drawable.cancel).into(iv_gif);
//                                    iv_gif.setImageResource(R.drawable.cancel);
                                    tv_success.setText(getString(R.string.failure));
                                    tv_order_id.setText("Order ID:" + responseData.getData().getOrderId());
                                    if (responseData.getData().getTransactionType().equalsIgnoreCase("COD")) {
                                        tv_transaction_id.setVisibility(View.GONE);
                                    } else {
                                        tv_transaction_id.setVisibility(View.VISIBLE);
                                    }
                                    tv_transaction_id.setText("Transaction ID:" + responseData.getData().getTransactionId());
                                    tv_amnt.setText(Share.symbol + responseData.getData().getPaid_amount());
                                    tv_mob.setText("Mo: " + SharedPrefs.getString(StatusActivity.this, Share.key_ + RegReq.mobile_1));
                                    tv_time.setText("" + Calendar.getInstance().getTime());
                                }
                            });
                            alertDialog.show();
                        }
                    } else {
                        Log.e(TAG, "onResponse: " + responseData.getResponseCode());
                        AlertDialog alertDialog = new AlertDialog.Builder(StatusActivity.this).create();
                        alertDialog.setTitle(getString(R.string.failure));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(responseData.getResponseMessage());
                        alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                isorder = true;
                                ll_success.setVisibility(View.VISIBLE);
                                Glide.with(StatusActivity.this).asGif().load(R.drawable.cancel).into(iv_gif);
//                                iv_gif.setImageResource(R.drawable.cancel);
                                btn_continue.setVisibility(View.GONE);
                                tv_order_id.setText("Order ID:" + responseData.getData().getOrderId());
                                if (responseData.getData().getTransactionType().equalsIgnoreCase("COD")) {
                                    tv_transaction_id.setVisibility(View.GONE);
                                } else {
                                    tv_transaction_id.setVisibility(View.VISIBLE);
                                }
                                tv_transaction_id.setText("Transaction ID:" + responseData.getData().getTransactionId());
                                tv_amnt.setText(Share.symbol + responseData.getData().getPaid_amount());
                                tv_mob.setText("Mo: " + SharedPrefs.getString(StatusActivity.this, Share.key_ + RegReq.mobile_1));
                                tv_time.setText("" + Calendar.getInstance().getTime());
                            }
                        });
                        alertDialog.show();
                    }
                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(StatusActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<get_final_status_response> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                Toast.makeText(StatusActivity.this, getString(R.string.something_went_wrong) + t, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(StatusActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            comman_new_place_order(orderid, 0);

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(StatusActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            comman_new_place_order(orderid, 0);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    private void logPurchaseEvent(final_status_response_data data) {
        FBEventsKt.logPurchasedEvent(this, data.getOrderId(), String.valueOf(data.getPaid_amount()));
        FirebaseEventsKt.logPurchasedEvent(this, data);
    }


    private void displayalert() {
        final Dialog dialog = new Dialog(StatusActivity.this);
        if (dialog != null)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.order_dialog_finish_alert); //get layout from ExitDialog folder
        TextView textView = dialog.findViewById(R.id.tv_dialog_text);
        TextView tv_rate_appp = dialog.findViewById(R.id.tv_rate_appp);
        textView.setText(getString(R.string.would_like));
        SmileRating smileRating = dialog.findViewById(R.id.smile_rating);
        Button btn_yes = dialog.findViewById(R.id.btn_yes);
        Button btn_no = dialog.findViewById(R.id.btn_no);
        btn_no.setText(getString(R.string.dismiss_dialog));
        btn_yes.setVisibility(View.GONE);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                switch (smiley) {
                    case SmileRating.BAD:
                    case SmileRating.OKAY:
                    case SmileRating.TERRIBLE:
                        dialog.dismiss();
                        FeedbackUtils.FeedbackDialog(StatusActivity.this);
                        break;
                    case SmileRating.GOOD:
                    case SmileRating.GREAT:
                        dialog.dismiss();
                        SharedPrefs.save(StatusActivity.this, SharedPrefs.REVIEW, 1);
                        rate_app();
                        break;
                }
            }
        });
        dialog.show();
    }

    private void rate_app() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + StatusActivity.this.getPackageName())));
        } catch (ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + StatusActivity.this.getPackageName())));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (SelectAddressActivity.activity != null) {
            SelectAddressActivity.activity.finish();
        }
        finish();
    }


  /*  public void logpurchasedEvent(String contentData, String contentId, String contentType, BigDecimal price) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, Share.symbol);
        logger.logPurchase(price, Currency.getInstance(Share.symbol), params);
    }*/
}
