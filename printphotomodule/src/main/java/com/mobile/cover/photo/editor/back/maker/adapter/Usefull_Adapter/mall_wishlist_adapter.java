package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.mall_main_sub_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.main_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Mall_wishlist;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.mall_detail_activity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class mall_wishlist_adapter extends RecyclerView.Adapter<mall_wishlist_adapter.MyViewHolder> {

    ProgressDialog pd;
    private List<mall_main_sub_data> sqlitemodelList;
    private Context mContext;
    private DisplayImageOptions options;

    public mall_wishlist_adapter(Context mContext, List<mall_main_sub_data> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product_wishlist_disp, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (sqlitemodelList.get(position).getProductImages().size() != 0) {

            ImageLoader.getInstance().displayImage(sqlitemodelList.get(position).getProductImages().get(0).getThumbImage(), holder.iv_pro_image, options);
//            Glide.with(mContext).load(sqlitemodelList.get(position).getProductImages().get(0).getImage()).asBitmap().placeholder(R.drawable.place_holder).into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    holder.iv_pro_image.setPadding(0, 0, 0, 0);
//                    holder.iv_pro_image.setImageBitmap(resource);
//                }
//            });
        }
        holder.tv_pro_name.setText(sqlitemodelList.get(position).getName());
        if (sqlitemodelList.get(position).getDummyPrice().equalsIgnoreCase("")) {
            holder.tv_dummy_price.setText("");
            holder.tv_dummy_price.setBackground(null);
            holder.tv_pro_percentage.setText("");
        } else {
            holder.tv_dummy_price.setVisibility(View.VISIBLE);
            holder.tv_pro_percentage.setVisibility(View.VISIBLE);
            holder.tv_pro_real_price.setText(Share.symbol + sqlitemodelList.get(position).getPrice());
            holder.tv_dummy_price.setText(Share.symbol + sqlitemodelList.get(position).getDummyPrice());
        }

        holder.tv_pro_percentage.setText(sqlitemodelList.get(position).getDiscount());
        holder.ll_main.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mall_detail_activity.mActivity != null) {
                    mall_detail_activity.mActivity.finish();
                }
                Share.variant_list_value.clear();
                Share.variant_list_value_women.clear();
                Share.detail_header_name = sqlitemodelList.get(position).getName();
                Share.product_info_details = sqlitemodelList.get(position).getmProductDetails1();
                Share.product_old_details = sqlitemodelList.get(position).getProductDetails();
                Share.product_images_list = sqlitemodelList.get(position).getProductImages();
                Share.mall_seller_details = sqlitemodelList.get(position).getSellerDetails();
                Log.e("PRO_ID", "onSingleClick: " + sqlitemodelList.get(position).getId());
                Share.in_wishlist = sqlitemodelList.get(position).getIn_wishlist();
                Intent intent = new Intent(mContext, mall_detail_activity.class);
                intent.putExtra("position", "" + position);
                intent.putExtra("product_id", "" + sqlitemodelList.get(position).getId());
                intent.putExtra("category_id", "" + sqlitemodelList.get(position).getGeneral_category_id());
                intent.putExtra("you_save", sqlitemodelList.get(position).getYou_save());
                intent.putExtra("descrip", sqlitemodelList.get(position).getDescription());
                intent.putExtra("pro_name", sqlitemodelList.get(position).getName());
                intent.putExtra("pro_id", "" + sqlitemodelList.get(position).getId());
                intent.putExtra("pro_price", sqlitemodelList.get(position).getPrice());
                intent.putExtra("pro_dummy_price", sqlitemodelList.get(position).getDummyPrice());
                intent.putExtra("pro_discount", sqlitemodelList.get(position).getDiscount());
                ((Activity)mContext).startActivityForResult(intent,100);
            }
        });
    }

    private void wishlist(final String pro_id, final MyViewHolder holder, final int position) {

        pd = ProgressDialog.show(mContext, "", mContext.getResources().getString(R.string.loading), true, false);
        APIService apiService = new MainApiClient(mContext).getApiInterface();
        Log.e("WISH", "wishlist: " + pro_id);
        Log.e("WISH", "wishlist: " + SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
        Call<main_response> call = apiService.check_uncheck_wishlist(SharedPrefs.getString(mContext, Share.key_ + RegReq.id), pro_id, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<main_response>() {
            @Override
            public void onResponse(Call<main_response> call, Response<main_response> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
//                        holder.iv_favourite.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_favorites));
                    } else {
//                        holder.iv_favourite.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_unfavorites));
                        sqlitemodelList.remove(position);
                        notifyDataSetChanged();
                        Mall_wishlist.title.setText("Wishlist(" + sqlitemodelList.size() + ")");
                    }
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<main_response> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(mContext.getResources().getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(mContext.getResources().getString(R.string.connect_time_out));
                    alertDialog.setButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            wishlist(pro_id, holder, position);
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(mContext.getResources().getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(mContext.getResources().getString(R.string.slow_connect));
                    alertDialog.setButton(mContext.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            wishlist(pro_id, holder, position);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_main;
        ImageView iv_pro_image;
        TextView tv_pro_name, tv_pro_real_price, tv_dummy_price, tv_pro_percentage;

        public MyViewHolder(View view) {
            super(view);
            ll_main = view.findViewById(R.id.ll_main);
            iv_pro_image = view.findViewById(R.id.iv_pro_image);
            tv_pro_name = view.findViewById(R.id.tv_pro_name);
            tv_pro_real_price = view.findViewById(R.id.tv_pro_real_price);
            tv_dummy_price = view.findViewById(R.id.tv_dummy_price);
            tv_pro_percentage = view.findViewById(R.id.tv_pro_percentage);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }
    }
}
