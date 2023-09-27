package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.mall_main_sub_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.mall_detail_activity;

import java.util.List;


public class search_adapter extends RecyclerView.Adapter<search_adapter.MyViewHolder> {

    private List<mall_main_sub_data> sqlitemodelList;
    private Context mContext;

    public search_adapter(Context mContext, List<mall_main_sub_data> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_category_name_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final mall_main_sub_data sqlitemodel = sqlitemodelList.get(position);

        holder.tv_name.setText(sqlitemodel.getName());
        holder.main_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mall_detail_activity.mActivity != null) {
                    mall_detail_activity.mActivity.finish();
                }
                Share.variant_list_value.clear();
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
                intent.putExtra("you_save", "" + sqlitemodel.getYou_save());
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
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public CardView main_card;

        public MyViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.name);
            main_card = view.findViewById(R.id.main_card);
        }
    }
}
