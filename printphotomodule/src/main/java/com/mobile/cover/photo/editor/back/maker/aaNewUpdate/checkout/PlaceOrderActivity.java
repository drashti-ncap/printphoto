package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Paymentintegration.StatusActivity;
import com.mobile.cover.photo.editor.back.maker.Paymentintegration.WebViewActivity;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.setting_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.ResponseDashboard;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.apply_code_response;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.response_order_initate;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.RetrofitClient2;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DialogHelperKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.OfferActivity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.utility.AvenuesParams;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderActivity extends PrintPhotoBaseActivity implements PaymentResultWithDataListener {
    private static final int CHROME_CUSTOM_TAB_REQUEST_CODE = 100;
    private static final long MIN_CLICK_INTERVAL = 1000;
    public static Activity activity;
    public static int gift = Share.gift, shipping = 0, promo_code = 0, gift_price = 0, dummy_total = 0;

    public static LinearLayout linear_gst, linear_prepaid_offer, ll_applied, ll_address_layout, ll_cod, id_iv_pay_now, iv_pay_by_card, ll_paypal;
    public static TextView tv_gst_charge, tv_prepaid_offer_amount, tvTotalAmount, id_dummy_price, tvDeliveryCharge, tvPayableAmount, tvOffer, tv_promo, tvOfferDiscount, tv_delivery_user_name, address_detail, tv_gift, tv_gift_price;
    public static ImageView btn_cancel, id_no_address, iv_promocode;
    public static EditText ed_promo;
    public static LinearLayout rl_promo, rl_gift;
    public static boolean cancel = false;
    public static boolean ispromo = false;
    public static boolean isOfferCode = true;
    public static String fsCouponCode = "";
    public static boolean codeNotApplied = true;
    public String temp_offer_code = "PREPAIDOFFER";
    public static CheckBox ch_gift;
    //  ProgressDialog pd;
    LinearLayout id_ll_gift;
    RecyclerView recyclerview;
    Button btn_apply;
    boolean isback = false;
    String Orderid;
    ImageView id_paypal;
    boolean ccavenue;
    boolean alert_show = false;
    boolean apply_disp_offer = true;
    PackageInfo pInfo = null;
    NumberFormat nf = new DecimalFormat("#.####");
    private final String offerId = "";
    private long mLastClickTime;

    private final int CLICK_PAYTM = 0;
    private final int CLICK_PAYPAL = 1;
    private final int CLICK_OTHER = 2;
    private int CLICK_CURRENT = CLICK_PAYTM;

    private int quantity = 1;
    private int additionalShippingChargeRate = 0;
    private double additionalCharges = 0.0;
    private float gstCharges = 0.0f;
    public static double net_payable_amount = 0;
    public static double order_total = 0;
    public static double total_charge = 0;
    public static double discount_amount = 0;
    public static double delivery_charge = 0;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.new_activity_place_order);
        mContext = PlaceOrderActivity.this;
        activity = PlaceOrderActivity.this;
        codeNotApplied = true;
        Checkout.preload(getApplicationContext());
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        quantity = getIntent().getIntExtra("quantity", 1);
        additionalShippingChargeRate = getIntent().getIntExtra("additional_shipping_charge", 0);
        gstCharges = getIntent().getFloatExtra("gst_charges", 0);
        discount_amount = 0;
        shipping = Share.shipping;
        findViews();
        Log.e("SHARE>GFIT", "onCreate:===========> " + Share.gift);
        Share.addressposition = 0;
        intiViews();
        initCLick();
        alert_show = Share.iskeychainexsist == 1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cancel) {
        } else {
            if (Share.isback == true) {
                ll_applied.setVisibility(View.GONE);
            } else {
                if (!Share.applyoffer.equalsIgnoreCase("mall")) {
                }
            }
        }

        if (!Share.address_value.equalsIgnoreCase("")) {
            address_detail.setText(Share.address_value);
            tv_delivery_user_name.setText(getString(R.string.delivery_to) + Share.deliver_name);
        } else {
        }
    }

    private void applycode(final String code, boolean doCheckout) {


        //pd = ProgressDialog.show(PlaceOrderActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(PlaceOrderActivity.this);

        APIService api = new MainApiClient(PlaceOrderActivity.this).getApiInterface();

        String gift;
        if (ch_gift.isChecked()) {
            gift = "1";
        } else {
            gift = "";
        }
        Log.e("CHECKOFFER", "applycode: order_id-->" + getIntent().getStringExtra("order_id"));
        Log.e("CHECKOFFER", "applycode: code-->" + code);
        Log.e("CHECKOFFER", "applycode: gift-->" + gift);
        Call<apply_code_response> couponCodeResCall = api.applyCouponCode_new(getIntent().getStringExtra("order_id"), code, gift, Locale.getDefault().getLanguage());
        couponCodeResCall.enqueue(new Callback<apply_code_response>() {

            public static final String TAG = "test";

            @Override
            public void onResponse(Call<apply_code_response> call, Response<apply_code_response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        //pd.dismiss();
                        hideProgressDialog();

                        final apply_code_response loCouponCodeRes = response.body();

                        if (response.body().getData().getdiscount_applied().equalsIgnoreCase("1")) {
                            Share.promocode = ed_promo.getText().toString();
                            cancel = true;
                            tv_promo.setText(code);
                            ll_applied.setVisibility(View.VISIBLE);
                            rl_promo.setVisibility(View.GONE);
                            iv_promocode.setVisibility(View.GONE);

                            ispromo = true;
                            Share.isback = false;
                            Share.isapproved = 0;
                            codeNotApplied = false;

                            order_total = loCouponCodeRes.getData().getOrderTotal();
                            discount_amount = loCouponCodeRes.getData().getDiscountAmount();
                            delivery_charge = loCouponCodeRes.getData().getShippingCharge();
                            net_payable_amount = loCouponCodeRes.getData().getNetPayable();
                            gstCharges = loCouponCodeRes.getData().getGst_charges();
                            additionalCharges = 0.0;
                            setShippingAndTotalCharge();
                            if (doCheckout) {
                                if (CLICK_CURRENT == CLICK_PAYTM) {
                                    doPaytm();
                                } else if (CLICK_CURRENT == CLICK_PAYPAL) {
                                    doPaypal();
                                } else {
                                    doOther();
                                }
                            }
                        } else {
                            if (response.body().getData().getDiscountMessage().equalsIgnoreCase("")) {
                                codeNotApplied = true;
                                cancel = true;
                                ll_applied.setVisibility(View.GONE);
                                if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                                    rl_promo.setVisibility(View.VISIBLE);
                                    iv_promocode.setVisibility(View.VISIBLE);
                                } else {
                                    rl_promo.setVisibility(View.VISIBLE);
                                    iv_promocode.setVisibility(View.VISIBLE);
                                }
                                ed_promo.setText("");
                                tv_promo.setText("");
                                if (loCouponCodeRes.getData().getDiscountAmount() != 0) {
                                    tvOfferDiscount.setText(Share.symbol + loCouponCodeRes.getData().getDiscountAmount());
                                } else {
                                    tvOfferDiscount.setText("- " + Share.symbol + "00");
                                }

                                order_total = loCouponCodeRes.getData().getOrderTotal();
                                discount_amount = loCouponCodeRes.getData().getDiscountAmount();
                                delivery_charge = loCouponCodeRes.getData().getShippingCharge();
                                net_payable_amount = loCouponCodeRes.getData().getNetPayable();
                                additionalCharges = 0.0;
                                setShippingAndTotalCharge();


                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                                alertDialog.setTitle(getString(R.string.alert));
                                alertDialog.setMessage(response.body().getData().getDiscountMessage());
                                alertDialog.setCancelable(false);
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        codeNotApplied = true;
                                        cancel = true;
                                        net_payable_amount = loCouponCodeRes.getData().getNetPayable();
                                        tvPayableAmount.setText(Share.symbol + (net_payable_amount));
                                        ll_applied.setVisibility(View.GONE);
                                        rl_promo.setVisibility(View.VISIBLE);
                                        iv_promocode.setVisibility(View.VISIBLE);
                                        ed_promo.setText("");
                                        if (loCouponCodeRes.getData().getDiscountAmount() != 0) {
                                            tvOfferDiscount.setText(Share.symbol + loCouponCodeRes.getData().getDiscountAmount());
                                        } else {
                                            tvOfferDiscount.setText("- " + Share.symbol + "00");
                                        }
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();
//                            Toast.makeText(PlaceOrderActivity.this, response.body().getData().getDiscountMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        //pd.dismiss();
                        hideProgressDialog();
                        AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                        alertDialog.setTitle(getString(R.string.alert));
                        alertDialog.setMessage(response.body().getResponseMessage());
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    }

                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(PlaceOrderActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<apply_code_response> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            applycode(code, doCheckout);

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            applycode(code, doCheckout);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    private void intiViews() {
        ImageView id_back = findViewById(R.id.id_back);
        tv_promo = findViewById(R.id.tv_promo);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        Log.e("mall", "intiViews: " + Share.applyoffer);

        String giftValue = "0";
        if (Share.orderdetails != null) {
            order_total = Share.orderdetails.getOrderTotal();
            delivery_charge = Share.orderdetails.getShippingCharge();
            net_payable_amount = Share.orderdetails.getNetPayable();
            discount_amount = Share.orderdetails.getDiscountAmount();
        }
        additionalCharges = 0;


        setShippingAndTotalCharge();

        //// OFFER DIALOG
        if (Share.countryCodeValue.equalsIgnoreCase("IN") && DataHelperKt.getOfferAmount(mContext) > 0) {
            linear_prepaid_offer.setVisibility(View.VISIBLE);
            tv_prepaid_offer_amount.setText(Share.symbol + DataHelperKt.getOfferAmount(mContext));
            DialogHelperKt.offerDialog(PlaceOrderActivity.this, total_charge, getIntent().getBooleanExtra("COD", false) && !getIntent().getStringExtra("COD_dialog").equalsIgnoreCase("0"),isPositive -> {
                if (isPositive) {
                    CLICK_CURRENT = CLICK_OTHER;
                    applycode(temp_offer_code, true);
                } else {
                    doCOD();
                }
            });
        } else {
            linear_prepaid_offer.setVisibility(View.GONE);
        }

        if (gstCharges > 0) {
            linear_gst.setVisibility(View.VISIBLE);
        } else {
            linear_gst.setVisibility(View.GONE);
        }

    }

    private void setShippingAndTotalCharge() {

        Log.i("CALCULATION", "Order Total:" + order_total);
        Log.i("CALCULATION", "Shipping Charge:" + delivery_charge);
        Log.i("CALCULATION", "Discount:" + discount_amount);
        Log.i("CALCULATION", "Net Payable:" + net_payable_amount);
        Log.i("CALCULATION", "GST Charge:" + gstCharges);

        tvTotalAmount.setText(Share.symbol + (String.format("%.2f", order_total)));

        if (discount_amount > 0) {
            tvOfferDiscount.setText("- " + Share.symbol + String.format("%.2f", discount_amount));
        } else {
            tvOfferDiscount.setText("- " + Share.symbol + "00");
        }


        String formattedShippingCharge = "+ " + Share.symbol + delivery_charge;
        if (Share.countryCodeValue != null) {
            if (Share.countryCodeValue.equalsIgnoreCase("SA") && quantity > 1) {
                additionalCharges = (additionalShippingChargeRate * (quantity - 1));
                Log.i("CALCULATION", "Additional Charges:" + additionalCharges);
                delivery_charge = delivery_charge + additionalCharges;

                Log.i("CALCULATION", "New Shipping Charge:" + delivery_charge);
                formattedShippingCharge = "+" + Share.symbol + String.format("%.2f", delivery_charge);
            }
        }
        tvDeliveryCharge.setText(formattedShippingCharge);

        String formattedGSTCharge = "+ " + Share.symbol + gstCharges;
        tv_gst_charge.setText(formattedGSTCharge);

        total_charge = net_payable_amount + additionalCharges + gstCharges;
        Log.i("CALCULATION", "Total Charge:" + total_charge);

        tvPayableAmount.setText(Share.symbol + (String.format("%.2f", total_charge)));


        tvOffer.setText("- " + Share.symbol + String.format("%.2f", discount_amount));


    }

    private void initCLick() {

        // COD
        ll_cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;

                doCOD();
            }
        });

        // PAYTM
        id_iv_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;
                CLICK_CURRENT = CLICK_PAYTM;
                if (Share.countryCodeValue.equalsIgnoreCase("IN") && codeNotApplied && DataHelperKt.getOfferAmount(mContext) > 0) {
                    applycode(temp_offer_code, true);
                } else {
                    doPaytm();
                }


            }
        });

        //PROMO
        iv_promocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getoffer = new Intent(getApplicationContext(), OfferActivity.class);
                // Share.order_id_apply = getIntent().getStringExtra("order_id");
                startActivityForResult(getoffer, 101);
            }
        });

        // PAYPAL
        id_paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;
                CLICK_CURRENT = CLICK_PAYPAL;
                if (Share.countryCodeValue.equalsIgnoreCase("IN") && codeNotApplied && DataHelperKt.getOfferAmount(mContext) > 0) {
                    applycode(temp_offer_code, true);
                } else {
                    doPaypal();
                }


            }
        });

        //OTHER
        iv_pay_by_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;


                CLICK_CURRENT = CLICK_OTHER;
                if (Share.countryCodeValue.equalsIgnoreCase("IN") && codeNotApplied && DataHelperKt.getOfferAmount(mContext) > 0) {
                    applycode(temp_offer_code, true);
                } else {
                    doOther();
                }


            }
        });

    }

    private void doCOD() {
        Log.e("CHECKCLCIK", "setShippingAndTotalCharge: intent===>" + getIntent().getStringExtra("COD_dialog"));
        if (getIntent().getStringExtra("COD_dialog") != null) {
            if (getIntent().getStringExtra("COD_dialog").equalsIgnoreCase("0")) {
                AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                alertDialog.setTitle(getString(R.string.alert));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getIntent().getStringExtra("COD_message"));
                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            } else {
                if (!getIntent().getStringExtra("best_offer_code").equalsIgnoreCase("")) {
                    if (apply_disp_offer) {
                        apply_disp_offer = false;
                        AlertDialog.Builder alertDialog_show = new AlertDialog.Builder(PlaceOrderActivity.this);
                        alertDialog_show.setTitle(getString(R.string.prepaid_best_offer));
                        alertDialog_show.setCancelable(false);
                        alertDialog_show.setMessage(getIntent().getStringExtra("best_offer_description"));
                        alertDialog_show.setPositiveButton(getString(R.string.apply), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                applycode(getIntent().getStringExtra("best_offer_code"), false);
                                dialog.dismiss();
                            }
                        });
                        alertDialog_show.setNegativeButton(getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog_show.show();
                    } else {
                        if (codeNotApplied) {
                            if (alert_show) {
                                alert_show = false;
                                AlertDialog alertDialog_show = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                                alertDialog_show.setTitle(getString(R.string.buy_one_get_one_keychain));
                                alertDialog_show.setCancelable(false);
                                alertDialog_show.setMessage(getString(R.string.offer_sentence));
                                alertDialog_show.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog_show.show();


                            } else {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this);
                                alertDialog.setTitle(getString(R.string.place_order));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.are_you_sure_want_to_purchase_using_cash_on_delivery));
                                alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Share.paymenttype = 2;
                                        pay("COD");
                                    }
                                });
                                alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                            }
                        } else if (codeNotApplied == false) {
                            AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                            alertDialog.setTitle(getString(R.string.alert));
                            alertDialog.setMessage(getString(R.string.term_cond_sentence));
                            alertDialog.setCancelable(false);
                            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    applycode("", false);
                                }
                            });
                            alertDialog.show();
                        }
                    }
                } else {
                    if (codeNotApplied) {
                        if (alert_show) {
                            alert_show = false;
                            AlertDialog alertDialog_show = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                            alertDialog_show.setTitle(getString(R.string.buy_one_get_one_keychain));
                            alertDialog_show.setCancelable(false);
                            alertDialog_show.setMessage(getString(R.string.offer_sentence));
                            alertDialog_show.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog_show.show();


                        } else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this);
                            alertDialog.setTitle(getString(R.string.place_order));
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(getString(R.string.are_you_sure_want_to_purchase_using_cash_on_delivery));
                            alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Share.paymenttype = 2;
                                    pay("COD");
                                }
                            });
                            alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                        alertDialog.setTitle(getString(R.string.terms_cond));
                        alertDialog.setMessage(getString(R.string.term_cond_sentence));
                        alertDialog.setCancelable(false);
                        alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                applycode("", false);
                            }
                        });
                        alertDialog.show();
                    }
                }
            }
        }
    }

    private void doPaytm() {
        Log.e("ISPROMO", "onClick: " + ispromo);
        Log.e("ISPROMO", "onClick: ========>" + Share.addressposition);
        Share.paymenttype = 0;
        if (alert_show) {
            alert_show = false;
            AlertDialog alertDialog_show = new AlertDialog.Builder(PlaceOrderActivity.this).create();
            alertDialog_show.setTitle(getString(R.string.buy_one_get_one_keychain));
            alertDialog_show.setCancelable(false);
            alertDialog_show.setMessage(getString(R.string.offer_sentence));
            alertDialog_show.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog_show.show();
        } else {
            pay("Paytm");
        }
        if (Share.isapproved == 0) {
            Share.promo = tv_promo.getText().toString();
        } else {
            Share.promo = "";
        }
        Log.e("SHARE.PROMO", "onClick: " + Share.promo);
    }

    private void doPaypal() {
        Share.paymenttype = 3;
        if (alert_show) {
            alert_show = false;
            AlertDialog alertDialog_show = new AlertDialog.Builder(PlaceOrderActivity.this).create();
            alertDialog_show.setTitle(getString(R.string.buy_one_get_one_keychain));
            alertDialog_show.setCancelable(false);
            alertDialog_show.setMessage(getString(R.string.offer_sentence));
            alertDialog_show.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog_show.show();
        } else {
            pay("Paypal");
        }
        if (Share.isapproved == 0) {
            Share.promo = tv_promo.getText().toString();
        } else {
            Share.promo = "";
        }
    }

    private void doOther() {
        Log.e("AMOUNT", "onClick:======> " + total_charge);
        Log.e("ORDERID", "onClick:======> " + getIntent().getExtras().get("orderid"));
        Log.e("ISPROMO", "onClick: ========>" + Share.addressposition);
        Share.paymenttype = 1;
        if (ccavenue) {
            if (alert_show) {
                alert_show = false;
                AlertDialog alertDialog_show = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                alertDialog_show.setTitle(getString(R.string.buy_one_get_one_keychain));
                alertDialog_show.setCancelable(false);
                alertDialog_show.setMessage(getString(R.string.offer_sentence));
                alertDialog_show.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog_show.show();
            } else {
                pay("CCAvenueGateway");
            }
        } else {
            if (alert_show) {
                alert_show = false;
                AlertDialog alertDialog_show = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                alertDialog_show.setTitle(getString(R.string.buy_one_get_one_keychain));
                alertDialog_show.setCancelable(false);
                alertDialog_show.setMessage(getString(R.string.offer_sentence));
                alertDialog_show.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog_show.show();
            } else {
                pay("Razorpay");
            }
        }
    }

    private void pay(final String transactiontype) {


//        if (pd != null) {
//            pd.dismiss();
//        }
        hideProgressDialog();
//        pd = ProgressDialog.show(PlaceOrderActivity.this, "", getString(R.string.loading), true, false);

        showProgressDialog(PlaceOrderActivity.this);

        APIService api = new MainApiClient(PlaceOrderActivity.this).getApiInterface();


        Log.e("INITIATE", "Order ID: " + getIntent().getStringExtra("order_id"));
        Log.e("INITIATE", "Transaction Type: " + transactiontype);
        Log.e("INITIATE", "Order Total: " + order_total);
        Log.e("INITIATE", "Discount Amount: " + discount_amount);
        Log.e("INITIATE", "Net Payable: " + net_payable_amount);
        Log.e("INITIATE", "Delivery Charge: " + delivery_charge);
        Log.e("INITIATE", "Additional Charges: " + additionalCharges);
        Log.e("INITIATE", "GST Charges: " + gstCharges);
        Log.e("INITIATE", "Total Charges: " + total_charge);

        Call<response_order_initate> call = api.initiate_transaction(getIntent().getStringExtra("order_id"), transactiontype, "Android", additionalCharges, gstCharges, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<response_order_initate>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<response_order_initate> call, Response<response_order_initate> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    response_order_initate responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("0")) {
                        //pd.dismiss();
                        hideProgressDialog();
                        Toast.makeText(activity, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        if (responseData.getData().getStatus() == 1) {
                            Log.e("SUCCESS", "onSUCCESS: ");
//                            pd.dismiss();
                            hideProgressDialog();
                            Log.e("Payment_Type", "Payment code:" + Share.paymenttype);
                            Log.e(TAG, "onResponse: " + responseData.getData().getTransactionId());
                            Log.e(TAG, "onResponse: " + responseData.getData().getTransactionType());
                            if (Share.paymenttype == 0) {
                                Log.e("Payment_Type", "PAYTM");
                                generatechecksum(responseData.getData().getTransactionId());
                            } else if (Share.paymenttype == 1) {

                                Log.e("Payment_Type", "OTHER");
                                if (ccavenue) {
                                    Log.e("Payment_Type", "-ccavenue");

                                    Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                                    intent.putExtra(AvenuesParams.ACCESS_CODE, "AVLX80FI26AN89XLNA");
                                    intent.putExtra(AvenuesParams.MERCHANT_ID, "189816");
                                    intent.putExtra(AvenuesParams.ORDER_ID, responseData.getData().getTransactionId());
                                    intent.putExtra(AvenuesParams.CURRENCY, "INR");
                                    intent.putExtra(AvenuesParams.AMOUNT, total_charge + ".00");
                                    intent.putExtra(AvenuesParams.REDIRECT_URL, "https://printphoto.in/Photo_case/CCAvenue/ccavResponseHandler.php");
                                    intent.putExtra(AvenuesParams.CANCEL_URL, "https://printphoto.in/Photo_case/CCAvenue/ccavResponseHandler.php");
                                    intent.putExtra(AvenuesParams.RSA_KEY_URL, "https://printphoto.in/Photo_case/public/api/GetRSA");
                                    intent.putExtra(AvenuesParams.BILLING_COUNTRY, "India");
                                    intent.putExtra(AvenuesParams.BILLING_TEL, SharedPrefs.getString(PlaceOrderActivity.this, Share.key_ + RegReq.mobile_1));
                                    intent.putExtra(AvenuesParams.BILLING_EMAIL, SharedPrefs.getString(PlaceOrderActivity.this, Share.key_ + RegReq.email));
                                    Share.paymenttype = 1;
                                    Share.ccavenue_order_id = getIntent().getStringExtra("order_id");
                                    Log.e("PROMO", "onClick: " + tv_promo.getText().toString());
                                    if (Share.isapproved == 0) {
                                        Share.promo = tv_promo.getText().toString();
                                    } else {
                                        Share.promo = "";
                                    }
                                    Log.e("SHARE.PROMO", "onClick: " + Share.promo);
                                    startActivity(intent);
                                } else {
                                    startPayment(responseData.getData().getTransactionId(), (int) total_charge);
                                }
                            } else if (Share.paymenttype == 2) {
                                Log.e("Payment_Type", "COD");
                                placeorder_new();
                            } else if (Share.paymenttype == 3) {

                                Log.e("Payment_Type", "PAYPAL");

                                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                CustomTabsIntent customTabsIntent = builder.build();
                                customTabsIntent.intent.setData(Uri.parse(response.body().getData().getPaypal_redirect_url()));
                                customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivityForResult(customTabsIntent.intent, CHROME_CUSTOM_TAB_REQUEST_CODE);
                            }

                        } else {
                            Toast.makeText(PlaceOrderActivity.this, responseData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                            //pd.dismiss();
                            hideProgressDialog();
                        }
                    }
                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(PlaceOrderActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<response_order_initate> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pay(transactiontype);

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pay(transactiontype);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    // TODO: 13/03/19 Generate Checksum for paytm always it will be unique for all transaction
    private void generatechecksum(final String transaction_id) {


        //pd = ProgressDialog.show(PlaceOrderActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(PlaceOrderActivity.this);


        APIService apiService = new RetrofitClient2(PlaceOrderActivity.this).getApiInterface();

        Log.e("BEFORE", "onResponse:== " + (int) total_charge);

        String trAmount = String.format("%.2f", total_charge);

        Call<ResponseDashboard> call = apiService.generate_checksum(transaction_id, SharedPrefs.getString(PlaceOrderActivity.this, Share.key_ + RegReq.id), trAmount);
        call.enqueue(new Callback<ResponseDashboard>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<ResponseDashboard> call, Response<ResponseDashboard> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    ResponseDashboard responseData = response.body();
                    if (responseData.getResponseStatus().equalsIgnoreCase("1")) {
                        //pd.dismiss();
                        hideProgressDialog();
                        Log.e("BEFORE", "onResponse: " + (double) total_charge);

                        if (responseData.getCHECKSUMHASH() != null && !responseData.getCHECKSUMHASH().trim().isEmpty()) {
                            initializePaytmPayment(responseData.getCHECKSUMHASH(), responseData.getORDERID(), SharedPrefs.getString(PlaceOrderActivity.this, Share.key_ + RegReq.id), trAmount);

                        } else {
                            Toast.makeText(PlaceOrderActivity.this, "Checksum not generated", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        //pd.dismiss();
                        hideProgressDialog();
                        Toast.makeText(PlaceOrderActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                    }

                } else {
//                    pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(PlaceOrderActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseDashboard> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                Toast.makeText(PlaceOrderActivity.this, getString(R.string.something_went_wrong) + t, Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            generatechecksum(transaction_id);

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            generatechecksum(transaction_id);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    private void initializePaytmPayment(String checksumHash, final String transaction_id, String
            custid, String total) {


        Log.e("PaytmPayment", "checksumHash: " + checksumHash);
        Log.e("PaytmPayment", "trAmount: " + total);

        PaytmPGService Service = PaytmPGService.getProductionService();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", "RAMANI43483626857623");
        paramMap.put("ORDER_ID", transaction_id);
        paramMap.put("CUST_ID", custid);
        paramMap.put("INDUSTRY_TYPE_ID", "Retail109");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", total);
        paramMap.put("WEBSITE", "APPPROD");
        paramMap.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + transaction_id);
        paramMap.put("CHECKSUMHASH", checksumHash);

        // TODO: 13/03/19 To disable UPI method from Paytm
        paramMap.put("PAYMENT_MODE_ONLY", "YES");
        paramMap.put("AUTH_MODE", "USRPWD");
        paramMap.put("PAYMENT_TYPE_ID", "PPI");

        PaytmOrder Order = new PaytmOrder((HashMap<String, String>) paramMap);

        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Toast.makeText(PlaceOrderActivity.this, inErrorMessage, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.e("LOG", "Payment Transaction is successful " + inResponse);


                        placeorder_new();
                    }

                    @Override
                    public void networkNotAvailable() {
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        //  Toast.makeText(PlaceOrderActivity.this, inErrorMessage, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                        //  Toast.makeText(PlaceOrderActivity.this, inErrorMessage, Toast.LENGTH_LONG).show();
                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        Toast.makeText(PlaceOrderActivity.this, getString(R.string.back_pressed_transaction_cancel), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Toast.makeText(getBaseContext(), getString(R.string.payment_trans_failed), Toast.LENGTH_LONG).show();
                    }

                });
    }

    private void placeorder_new() {
        finish();
        ll_applied.setVisibility(View.GONE);
        Share.isback = true;
        Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
        intent.putExtra("paymentId", "");
        intent.putExtra("signature", "");
        intent.putExtra("orderId", "");
        intent.putExtra("order_new_Id", getIntent().getStringExtra("order_id"));
        startActivity(intent);
    }

    private void findViews() {
        address_detail = findViewById(R.id.address_detail);
        ll_paypal = findViewById(R.id.ll_paypal);
        linear_prepaid_offer = findViewById(R.id.linear_prepaid_offer);
        tv_prepaid_offer_amount = findViewById(R.id.tv_prepaid_offer_amount);
        tv_gst_charge = findViewById(R.id.tv_gst_charge);
        linear_gst = findViewById(R.id.linear_gst);


        rl_gift = findViewById(R.id.rl_gift);
        ch_gift = findViewById(R.id.ch_gift);
        ch_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ch_gift.isChecked()) {
                    if (!ed_promo.getText().toString().equalsIgnoreCase("")) {
                        applycode(tv_promo.getText().toString(), false);
                    } else {
                        applycode(tv_promo.getText().toString(), false);
                    }
                } else {
                    if (!ed_promo.getText().toString().equalsIgnoreCase("")) {
                        applycode(tv_promo.getText().toString(), false);
                    } else {
                        applycode(tv_promo.getText().toString(), false);
                    }
                }
            }
        });
        tv_gift_price = findViewById(R.id.tv_gift_price);


        setting_response_data configurationData = DataHelperKt.getConfiguration(activity);

        if (configurationData.getGift_charge().equalsIgnoreCase("0")) {
            rl_gift.setVisibility(View.GONE);
        } else {
            rl_gift.setVisibility(View.VISIBLE);
            tv_gift_price.setText(Share.symbol + configurationData.getGift_charge());
        }
        tv_delivery_user_name = findViewById(R.id.tv_delivery_user_name);
        rl_promo = findViewById(R.id.rl_promo);
        ll_cod = findViewById(R.id.ll_cod);
        Log.e("COD", "findViews: " + getIntent().getBooleanExtra("COD", false));
        if (getIntent().getBooleanExtra("COD", false) && !getIntent().getStringExtra("COD_dialog").equalsIgnoreCase("0")) {
            ll_cod.setVisibility(View.VISIBLE);
        } else {
            ll_cod.setVisibility(View.GONE);
        }
        ll_address_layout = findViewById(R.id.ll_address_layout);
        tvTotalAmount = findViewById(R.id.tv_total_amount);
        id_dummy_price = findViewById(R.id.id_dummy_price);
        tvDeliveryCharge = findViewById(R.id.tv_shipping_charge);
        tvPayableAmount = findViewById(R.id.tv_payable_amount);
        id_iv_pay_now = findViewById(R.id.id_iv_pay_now);
        id_no_address = findViewById(R.id.id_no_address);
        iv_pay_by_card = findViewById(R.id.iv_pay_by_card);
        id_paypal = findViewById(R.id.id_paypal);
        iv_promocode = findViewById(R.id.iv_promocode);
        tvOffer = findViewById(R.id.tv_offer);
        tvOfferDiscount = findViewById(R.id.tv_offer_discount);
        tvOfferDiscount.setText(Share.symbol + " 00");
        ll_applied = findViewById(R.id.ll_applied);
        ll_applied.setVisibility(View.GONE);
        ed_promo = findViewById(R.id.ed_promo);
        ed_promo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (!s1.equals(s1.toUpperCase())) {
                    s1 = s1.toUpperCase();
                    ed_promo.setText(s1);
                }
                ed_promo.setSelection(s1.length());
            }
        });
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applycode("", false);
            }
        });
        btn_apply = findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ed_promo.getWindowToken(), 0);
                if (ed_promo.getText().toString().trim().contains(" ") || ed_promo.getText().toString().trim().matches("")) {
                    Toast.makeText(PlaceOrderActivity.this, getString(R.string.promocode_validation_sentence), Toast.LENGTH_SHORT).show();
                } else {
                    applycode(ed_promo.getText().toString().trim(), false);
                }
            }
        });


        if (Share.enabledpayment_gateway.size() != 0) {
            for (int i = 0; i < Share.enabledpayment_gateway.size(); i++) {
                Log.e("ENABLED", "findViews: =======>" + Share.enabledpayment_gateway.get(i));
                if (Share.enabledpayment_gateway.get(i).equalsIgnoreCase("Paytm")) {
                    Log.e("PAYTM", "findViews: ===>ENABLED");
                    id_iv_pay_now.setVisibility(View.VISIBLE);
                }
                if (Share.enabledpayment_gateway.get(i).equalsIgnoreCase("CCAvenueGateway")) {
                    iv_pay_by_card.setVisibility(View.VISIBLE);
                    ccavenue = true;
                }
                if (Share.enabledpayment_gateway.get(i).equalsIgnoreCase("Razorpay")) {
                    iv_pay_by_card.setVisibility(View.VISIBLE);
                    ccavenue = false;
                }
                if (Share.enabledpayment_gateway.get(i).equalsIgnoreCase("Paypal")) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) id_iv_pay_now.getLayoutParams();
                    params.setMargins(0, 0, 10, 0); //substitute parameters for left, top, right, bottom
                    id_iv_pay_now.setLayoutParams(params);
                    Log.e("VISIBILTY", "findViews: ====>1" + id_iv_pay_now.getVisibility());
                    ll_paypal.setVisibility(View.VISIBLE);
                } else {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) id_iv_pay_now.getLayoutParams();
                    params.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
                    id_iv_pay_now.setLayoutParams(params);
                    Log.e("VISIBILTY", "findViews: ====>2" + id_iv_pay_now.getVisibility());
                }
            }
        }

        rl_promo.setVisibility(View.VISIBLE);
        iv_promocode.setVisibility(View.VISIBLE);

    }


    /*  private void comman_new_place_order(final String orderid, int cancel) {
          pd = ProgressDialog.show(PlaceOrderActivity.this, "", getString(R.string.loading), true, false);
          pd.show();
          APIService api = new MainApiClient().getApiInterface();

          Call<get_final_status_response> call = api.get_final_status(orderid, cancel, "", "", "", "" + "Android"
                  , pInfo.versionName, Locale.getDefault().getLanguage());
          call.enqueue(new Callback<get_final_status_response>() {
              public static final String TAG = "COMMAN_PLACE_ORDER";

              @Override
              public void onResponse(Call<get_final_status_response> call, Response<get_final_status_response> response) {
                  Log.e(TAG, "onResponse: " + response.isSuccessful());
                  final get_final_status_response responseData = response.body();
                  if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                      pd.dismiss();
                      if (responseData.getData().getStatus() == 1) {
                          finish();
                          global.appliedpromo.remove(Share.promocode);
                          ll_applied.setVisibility(View.GONE);
                          tv_promo.setText(null);
                          Share.isback = true;

  //                        finish();
  //                        Share.isback = true;
  //                        global.appliedpromo.remove(Share.promocode);
  //                        ll_applied.setVisibility(View.GONE);
  //                        iv_promocode.setVisibility(View.VISIBLE);
  //                        rl_promo.setVisibility(View.VISIBLE);
                      } else {
                          Toast.makeText(PlaceOrderActivity.this, responseData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                      }
                  } else {
                      Toast.makeText(PlaceOrderActivity.this, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                  }
              }

              @Override
              public void onFailure(Call<get_final_status_response> call, Throwable t) {
                  pd.dismiss();
                  Toast.makeText(PlaceOrderActivity.this, getString(R.string.something_went_wrong) + t, Toast.LENGTH_LONG).show();
                  Log.e(TAG, "onFailure: ======>" + t);
                  Log.e(TAG, "onFailure: ======>" + t.getMessage());
                  Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                  if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                      AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                      alertDialog.setTitle(getString(R.string.time_out));
                      alertDialog.setMessage(getString(R.string.connect_time_out));
                      alertDialog.setCancelable(false);
                      alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                              comman_new_place_order(orderid, 1);

                          }
                      });
                      alertDialog.show();
                  } else {
                      AlertDialog alertDialog = new AlertDialog.Builder(PlaceOrderActivity.this).create();
                      alertDialog.setTitle(getString(R.string.internet_connection));
                      alertDialog.setMessage(getString(R.string.slow_connect));
                      alertDialog.setCancelable(false);
                      alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                              comman_new_place_order(orderid, 1);
                          }
                      });
                      alertDialog.show();
                  }
              }
          });
      }

  */
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


    public void startPayment(String trasactionid, int total) {

        Log.e("Payment_Type", "-Razorpay");

        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.appicon);
        final Activity activity = this;  /**   * Pass your payment options to the Razorpay Checkout as a JSONObject   */
        try {
            JSONObject options = new JSONObject();    /**     * Merchant Name     * eg: ACME Corp || HasGeek etc.     */
            options.put("name", "Print Photo");    /**     * Description can be anything     * eg: Order #123123     *     Invoice Payment     *     etc.     */
            options.put("description", trasactionid);
            options.put("order_id", trasactionid);
            Log.e("CURRENCYCODE", "startPayment: " + getIntent().getStringExtra("currency_code"));
            options.put("currency", getIntent().getStringExtra("currency_code"));    /**     * Amount is always passed in PAISE     * Eg: "500" = Rs 5.00     */
            options.put("amount", total + "00");
            JSONObject preFill = new JSONObject();
            preFill.put("email", SharedPrefs.getString(PlaceOrderActivity.this, Share.key_ + RegReq.email));
            preFill.put("contact", SharedPrefs.getString(PlaceOrderActivity.this, Share.key_ + RegReq.mobile_1));
            options.put("prefill", preFill);


            Log.e("OPTIONS", "startPayment: " + options);
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("ERROR", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
//        placeorder_new();
        finish();
        ll_applied.setVisibility(View.GONE);
        Share.isback = true;
        Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
        intent.putExtra("paymentId", paymentData.getPaymentId());
        intent.putExtra("signature", paymentData.getSignature());
        intent.putExtra("orderId", paymentData.getOrderId());
        intent.putExtra("order_new_Id", getIntent().getStringExtra("order_id"));
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            String offerCode = data.getStringExtra("OFFER_CODE");
            //   Toast.makeText(this, offerCode, Toast.LENGTH_SHORT).show();
            applycode(offerCode, false);
        }

    }
}
