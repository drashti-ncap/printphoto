package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.mall_main_sub_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class mall_sub_category_data_adapter extends RecyclerView.Adapter<mall_sub_category_data_adapter.MyViewHolder> {

    ProgressDialog pd;
    private List<mall_main_sub_data> sqlitemodelList;
    private Context mContext;
    private DisplayImageOptions options;

    public mall_sub_category_data_adapter(Context mContext, List<mall_main_sub_data> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.setIsRecyclable(false);
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
        holder.tv_pro_real_price.setText(Share.symbol + sqlitemodelList.get(position).getPrice());
        if (sqlitemodelList.get(position).getDummyPrice().equalsIgnoreCase("")) {
            holder.tv_dummy_price.setText("");
            holder.tv_dummy_price.setBackground(null);
            holder.tv_pro_percentage.setText("");
        } else {
            holder.tv_dummy_price.setVisibility(View.VISIBLE);
            holder.tv_pro_percentage.setVisibility(View.VISIBLE);
            holder.tv_dummy_price.setText(Share.symbol + sqlitemodelList.get(position).getDummyPrice());
            holder.tv_pro_percentage.setText(sqlitemodelList.get(position).getDiscount());
        }

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
                intent.putExtra("you_save", "" + sqlitemodelList.get(position).getYou_save());
                intent.putExtra("descrip", sqlitemodelList.get(position).getDescription());
                intent.putExtra("pro_name", sqlitemodelList.get(position).getName());
                intent.putExtra("pro_id", "" + sqlitemodelList.get(position).getId());
                intent.putExtra("pro_price", sqlitemodelList.get(position).getPrice());
                intent.putExtra("pro_dummy_price", sqlitemodelList.get(position).getDummyPrice());
                intent.putExtra("pro_discount", sqlitemodelList.get(position).getDiscount());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product_disp, parent, false);

        return new MyViewHolder(itemView);
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
