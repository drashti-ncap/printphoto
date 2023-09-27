package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mall_main_category_response_click_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.mall_filter_activity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.mall_AllChild;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class mall_main_category_data extends RecyclerView.Adapter<mall_main_category_data.MyViewHolder> {

    private static final long MIN_CLICK_INTERVAL = 1500;
    ProgressDialog pd;
    AlertDialog alertDialog;
    FirebaseAnalytics firebaseAnalytics;
    AppEventsLogger logger;
    OnItemClickListener onItemClickListener;
    private List<mall_AllChild> sqlitemodelList;
    private Context mContext;
    private long mLastClickTime;
    private DisplayImageOptions options;

    public mall_main_category_data(Context mContext, List<mall_AllChild> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        Glide.with(mContext).load(sqlitemodelList.get(position).getAppImage()).asBitmap().placeholder(R.drawable.place_holder).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                holder.iv_pro_image.setPadding(0, 0, 0, 0);
//                holder.iv_pro_image.setImageBitmap(resource);
//            }
//        });

        ImageLoader.getInstance().displayImage(sqlitemodelList.get(position).getAppImage(), holder.iv_pro_image, options);

        holder.tv_category_name.setText(sqlitemodelList.get(position).getName());
        holder.tv_category_desp.setText(sqlitemodelList.get(position).getDescription());
        holder.ll_tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickLister(view, position);
            }

//            @Override
//            public void onSingleClick(View v) {
//                long currentClickTime = SystemClock.uptimeMillis();
//                long elapsedTime = currentClickTime - mLastClickTime;
//                mLastClickTime = currentClickTime;
//                if (elapsedTime <= MIN_CLICK_INTERVAL)
//                    return;
//                get_other_category(sqlitemodelList.get(position).getId(), position, holder);
//            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_mall, parent, false);

        return new MyViewHolder(itemView);
    }

    public void get_other_category(final Integer id, final Integer position, final MyViewHolder holder) {
        pd = ProgressDialog.show(mContext, "", mContext.getResources().getString(R.string.loading), true, false);
        APIService apiService = new MainApiClient(mContext).getApiInterface();
        int user_id;
        if (!SharedPrefs.getBoolean(mContext, Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
        }
        Call<mall_main_category_response_click_data> call = apiService.call_main_categoru_subdata(id, user_id, Share.countryCodeValue, "whats_new", Locale.getDefault().getLanguage());

        call.enqueue(new Callback<mall_main_category_response_click_data>() {
            @Override
            public void onResponse(Call<mall_main_category_response_click_data> call, Response<mall_main_category_response_click_data> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        Share.isinternational = response.body().getIs_international();

                        firebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
                        logger = AppEventsLogger.newLogger(mContext);
                        Bundle params = new Bundle();
                        params.putInt("mall_category_open", 1);
                        firebaseAnalytics.logEvent("mall_category_open", params);

                        Share.symbol = response.body().getCurrency_symbol();
                        Share.category_header_name = sqlitemodelList.get(position).getName();
                        Share.filtered_response.clear();
                        Share.subresponse_data = response.body().getData();
                        Share.checked_arraylist.clear();
                        Share.available_filters = Share.mall_main_category_data.get(position).getAvailableFilter();
                        Log.e("SIZE", "onResponse: " + Share.subresponse_data.size());
                        Log.e("SIZE", "onResponse: " + Share.mall_main_category_data.get(position).getAvailableFilter().size());
                        mall_filter_activity.mactivity = null;
                        Share.base_filter_url = "";
                        Intent intent = new Intent(mContext, mall_category_activity.class);
                        intent.putExtra("category_id", "" + id);
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<mall_main_category_response_click_data> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                        alertDialog = null;
                    }
                    alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(mContext.getResources().getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(mContext.getResources().getString(R.string.connect_time_out));
                    alertDialog.setButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_other_category(sqlitemodelList.get(position).getId(), position, holder);
                        }
                    });
                    alertDialog.show();
                } else {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                        alertDialog = null;
                    }
                    alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(mContext.getResources().getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(mContext.getResources().getString(R.string.slow_connect));
                    alertDialog.setButton(mContext.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_other_category(sqlitemodelList.get(position).getId(), position, holder);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_tshirt;
        ImageView iv_pro_image;
        TextView tv_category_name, tv_category_desp;

        public MyViewHolder(View view) {
            super(view);
            ll_tshirt = view.findViewById(R.id.ll_tshirt);
            iv_pro_image = view.findViewById(R.id.iv_pro_image);
            tv_category_name = view.findViewById(R.id.tv_category_name);
            tv_category_desp = view.findViewById(R.id.tv_category_desp);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }
    }
}
