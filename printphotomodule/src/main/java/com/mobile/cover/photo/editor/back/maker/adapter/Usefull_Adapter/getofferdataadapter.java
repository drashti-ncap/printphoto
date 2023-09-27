package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.apply_code_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout.PlaceOrderActivity;
import com.mobile.cover.photo.editor.back.maker.model.getdata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout.PlaceOrderActivity.ch_gift;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout.PlaceOrderActivity.codeNotApplied;


public class getofferdataadapter extends RecyclerView.Adapter<getofferdataadapter.MyViewHolder> {

    private static final long MIN_CLICK_INTERVAL = 1500;
    ProgressDialog pd;
    private List<getdata> sqlitemodelList;
    private List<getpromodata> sqlitemodelList1;
    private List<getpromodetail> sqlitemodelList2;
    private Context mContext;
    private List<getdata> sqlitemodelListFiltered;
    private boolean isOfferCode = true;
    private String fsCouponCode = "";
    private String offerId = "";
    private DisplayImageOptions options;
    private long mLastClickTime;

    public getofferdataadapter(Context mContext, List<getdata> sqlitemodelList, List<getpromodata> sqlist2, List<getpromodetail> sqlist3) {
        this.sqlitemodelList = sqlitemodelList;
        this.sqlitemodelList1 = sqlist2;
        this.sqlitemodelList2 = sqlist3;
        this.mContext = mContext;
        this.sqlitemodelListFiltered = sqlitemodelList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position >= sqlitemodelList.size()) {
            final int POS = position - sqlitemodelList.size();
            final getpromodata sqlitemodel1 = sqlitemodelList1.get(POS);
            holder.tv_code.setText(sqlitemodel1.getPromo_code());
        } else {
            final getdata sqlitemodel = sqlitemodelList.get(position);
            holder.tv_code.setText(sqlitemodel.getDisplay_message());
            holder.tv_description.setText(sqlitemodel.getDescription());
            holder.tv_expiry.setText(sqlitemodel.getExpiry_text());

            ImageLoader.getInstance().displayImage(sqlitemodel.getOffer_new_image(), holder.id_image, options);
            holder.tv_terms_condition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle(mContext.getString(R.string.term_of_use));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(sqlitemodel.getTerms_condition());
                    alertDialog.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.create().show();
                }
            });

            holder.main_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    long currentClickTime = SystemClock.uptimeMillis();
                    long elapsedTime = currentClickTime - mLastClickTime;
                    mLastClickTime = currentClickTime;
                    if (elapsedTime <= MIN_CLICK_INTERVAL)
                        return;

                    Intent intent = new Intent();
                    intent.putExtra("OFFER_CODE", sqlitemodel.getOffer_code());
                    ((Activity) mContext).setResult(RESULT_OK, intent);
                    ((Activity) mContext).finish();



                 /*   Share.applyoffer = sqlitemodel.getAmount();
                    Share.promocode = sqlitemodel.getOffer_code();
                    offerId = sqlitemodel.getId();
                    Share.isapproved = 1;
                    applycode(Share.order_id_apply, sqlitemodel.getOffer_code(), position);
                    Share.isback = false;*/
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_frag_offer, parent, false);

        return new MyViewHolder(itemView);
    }

    private void applycode(final String orderid, final String code, final int position) {


        pd = ProgressDialog.show(mContext, "", mContext.getResources().getString(R.string.loading), true, false);

        APIService api = new MainApiClient(mContext).getApiInterface();

        Log.e("OFFER", "applycode: " + orderid);
        Log.e("OFFER", "applycode: " + code);


        String gift;
        if (ch_gift.isChecked()) {
            gift = "1";
        } else {
            gift = "";
        }

        Call<apply_code_response> couponCodeResCall = api.applyCouponCode_new(orderid, code, gift, Locale.getDefault().getLanguage());
        couponCodeResCall.enqueue(new Callback<apply_code_response>() {

            public static final String TAG = "test";

            @Override
            public void onResponse(Call<apply_code_response> call, Response<apply_code_response> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    if (pd != null) {
                        pd.dismiss();
                    }
                    pd.dismiss();
                    final apply_code_response loCouponCodeRes = response.body();
                    if (loCouponCodeRes.getResponseCode().equalsIgnoreCase("1")) {
                        if (loCouponCodeRes.getData().getdiscount_applied().equalsIgnoreCase("1")) {
                            Share.applyoffer = String.valueOf(loCouponCodeRes.getData().getDiscountAmount());
                            Share.promocode = code;
                            PlaceOrderActivity.cancel = true;
                            PlaceOrderActivity.tvOffer.setText("- " + Share.symbol + loCouponCodeRes.getData().getDiscountAmount());
                            if (loCouponCodeRes.getData().getDiscountAmount() != 0) {
                                PlaceOrderActivity.tvOfferDiscount.setText("- " + Share.symbol + loCouponCodeRes.getData().getDiscountAmount());
                            } else {
                                PlaceOrderActivity.tvOfferDiscount.setText("- " + Share.symbol + "00");
                            }
                            PlaceOrderActivity.tv_promo.setText(sqlitemodelList.get(position).getOffer_code());
                            PlaceOrderActivity.ll_applied.setVisibility(View.VISIBLE);
                            PlaceOrderActivity.rl_promo.setVisibility(View.GONE);
                            PlaceOrderActivity.iv_promocode.setVisibility(View.GONE);
                            double finaltotal = loCouponCodeRes.getData().getNetPayable();
                            PlaceOrderActivity.tvPayableAmount.setText(Share.symbol + finaltotal);
                            PlaceOrderActivity.net_payable_amount = finaltotal;
                            PlaceOrderActivity.ispromo = true;
                            Share.isback = false;
                            Share.isapproved = 0;
                            ((Activity) mContext).finish();
                            codeNotApplied = false;
                        } else {
                            AlertDialog alertDialog_show = new AlertDialog.Builder(mContext).create();
                            alertDialog_show.setTitle(mContext.getString(R.string.alert));
                            alertDialog_show.setCancelable(false);
                            alertDialog_show.setMessage(loCouponCodeRes.getData().getDiscountMessage());
                            alertDialog_show.setButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    codeNotApplied = true;
                                    Share.offerId = "";
                                    Share.sellerpromoId = "";
                                    PlaceOrderActivity.cancel = true;
                                    PlaceOrderActivity.tvPayableAmount.setText(Share.symbol + (loCouponCodeRes.getData().getNetPayable()));
                                    PlaceOrderActivity.ll_applied.setVisibility(View.GONE);
                                    PlaceOrderActivity.rl_promo.setVisibility(View.VISIBLE);
                                    PlaceOrderActivity.iv_promocode.setVisibility(View.VISIBLE);
                                    PlaceOrderActivity.ed_promo.setText("");
                                    PlaceOrderActivity.net_payable_amount = loCouponCodeRes.getData().getNetPayable();
                                    dialog.dismiss();
                                }
                            });
                            alertDialog_show.show();

//                        Toast.makeText(mContext, loCouponCodeRes.getData().getDiscountMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        pd.dismiss();
                        Toast.makeText(mContext, loCouponCodeRes.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<apply_code_response> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(mContext.getResources().getString(R.string.time_out));
                    alertDialog.setMessage(mContext.getResources().getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(mContext.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            applycode(orderid, code, position);

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(mContext.getResources().getString(R.string.internet_connection));
                    alertDialog.setMessage(mContext.getResources().getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(mContext.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            applycode(orderid, code, position);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size() + sqlitemodelList1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_code, tv_description, tv_expiry;
        public Button btn_apply;
        public ImageView id_image, tv_terms_condition;
        public ProgressBar progressBar;
        public CardView main_card;


        public MyViewHolder(View view) {
            super(view);
            tv_code = view.findViewById(R.id.tv_code);
            tv_expiry = view.findViewById(R.id.tv_expiry);
            tv_description = view.findViewById(R.id.tv_description);
//            btn_apply = view.findViewById(R.id.btn_apply);
            id_image = view.findViewById(R.id.id_image);
            tv_terms_condition = view.findViewById(R.id.tv_terms_condition);
            progressBar = view.findViewById(R.id.progressBar);
            main_card = view.findViewById(R.id.main_card);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }
    }

}
