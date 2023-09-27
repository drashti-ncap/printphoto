package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.SellerOrder;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.orderitem;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.OrderDetailActivity;

import java.util.List;


public class new_orderitem_getorderadapter extends RecyclerView.Adapter<new_orderitem_getorderadapter.MyViewHolder> {

    String Date, status, order_total,
            discount_amount_val, transaction_type, order_id, paid_amount,
            shipping_charge, is_cancel, cancel_msg, currency_symbol, is_gift_val, gift_charge_val,gstCharge;
    private List<orderitem> sqlitemodelList;
    private List<SellerOrder> sqlitemodelList_Seller;
    private Context mContext;


    public new_orderitem_getorderadapter(Context mContext, List<orderitem> orderItems, String date, String currency_symbol, String is_cancel, String cancel_msg_value, String s, String is_gift, String gift_charge, String order_id_value, String value_shipping_charge, String value_paid_amount, String transaction_type_value, String discount_amount, String order_total_amount, List<SellerOrder> sqlitemodelList,String gstCharge) {
        this.sqlitemodelList = orderItems;
        this.mContext = mContext;
        this.Date = date;
        this.currency_symbol = currency_symbol;
        this.is_cancel = String.valueOf(is_cancel);
        cancel_msg = cancel_msg_value;
        Date = date;
        is_gift_val = String.valueOf(is_gift);
        gift_charge_val = String.valueOf(gift_charge);
        order_id = order_id_value;
        shipping_charge = String.valueOf(value_shipping_charge);
        paid_amount = String.valueOf(value_paid_amount);
        transaction_type = transaction_type_value;
        discount_amount_val = String.valueOf(discount_amount);
        order_total = String.valueOf(order_total_amount);
        sqlitemodelList_Seller = sqlitemodelList;
        this.gstCharge = gstCharge;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_details, parent, false);

        return new MyViewHolder(itemView);
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final orderitem sqlitemodel = sqlitemodelList.get(position);

        holder.tv_place_on_date.setText(mContext.getString(R.string.placed_on) + Date);
        holder.tv_item_amount.setText(mContext.getString(R.string.amount_val) + currency_symbol + sqlitemodel.getSubtotal());
        holder.tv_model_name.setText(sqlitemodel.getModelName());

        holder.main_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailscreen(sqlitemodel);
            }
        });

        Glide.with(mContext).asBitmap()
                .load(sqlitemodel.getDisplayImages().get(0).getImage())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (sqlitemodel.getDisplay().getRotate() == 1) {
                            holder.iv_pro_image.setImageBitmap(RotateBitmap(resource, 90));
                        } else {
                            holder.iv_pro_image.setImageBitmap(resource);
                        }
                        holder.progressBar.setVisibility(View.GONE);
                        System.gc();
                    }

                    @Override
                    public void onLoadFailed(Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });

    }


    private void detailscreen(orderitem sqlitemodel) {
        Share.order_item_list = sqlitemodelList_Seller;
        Share.symbol = currency_symbol;
        if (OrderDetailActivity.activity != null) {
            OrderDetailActivity.activity.finish();
        }
        Intent detail = new Intent(mContext, OrderDetailActivity.class);
        detail.putExtra("is_cancelable", is_cancel);
        detail.putExtra("cancel_msg", cancel_msg);
        detail.putExtra("date", Date);
        detail.putExtra("status", status);
        detail.putExtra("is_gift", is_gift_val);
        detail.putExtra("gift_charge", gift_charge_val);
        detail.putExtra("tr_id", order_id);
        detail.putExtra("total_amount", order_total);
        detail.putExtra("pd_amount", paid_amount);
        detail.putExtra("shipping", shipping_charge);
        detail.putExtra("transaction_type", transaction_type);
        detail.putExtra("code_discount", discount_amount_val);
        detail.putExtra("gstCharge", gstCharge);
        mContext.startActivity(detail);
    }


    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_model_name, tv_place_on_date, tv_item_amount;
        public ImageView iv_pro_image;
        public LinearLayout main_card;
        public ProgressBar progressBar;


        public MyViewHolder(View view) {
            super(view);
            tv_model_name = view.findViewById(R.id.tv_model_name);
            tv_place_on_date = view.findViewById(R.id.tv_place_on_date);
            tv_item_amount = view.findViewById(R.id.tv_item_amount);
            main_card = view.findViewById(R.id.main_card);
            iv_pro_image = view.findViewById(R.id.iv_pro_image);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }


}
