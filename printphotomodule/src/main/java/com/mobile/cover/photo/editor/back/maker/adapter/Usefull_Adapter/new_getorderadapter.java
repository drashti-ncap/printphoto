package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.SellerOrder;
import com.mobile.cover.photo.editor.back.maker.R;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;
import java.util.Locale;


public class new_getorderadapter extends RecyclerView.Adapter<new_getorderadapter.MyViewHolder> {

    String Date, order_total_amount,
            discount_amount, transaction_type_value, order_id_value, value_paid_amount,
            value_shipping_charge, is_cancel, cancel_msg_value, currency_symbol, is_gift, gift_charge, gstCharge;
    new_orderitem_getorderadapter mAdapter;

    private Context mContext;
    private List<SellerOrder> sqlitemodelList;

//    public new_getorderadapter(Context mContext, List<orderitem> sqlitemodelList, String date, String courier_link, String track_status, String order_status,
//                               String total_amount, String discount, String transaction_type, String order_id, String paid_amount, String shipping, String is_cancelable, String cancel_msg, String currency_symbol, String is_gift, String gift_charge) {
//        this.sqlitemodelList = sqlitemodelList;
//        this.mContext = mContext;
//        Date = date;
//        show_track = track_status;
//        trackinglink = courier_link;
//        status = order_status;
//        order_total_amount = total_amount;
//        discount_amount = discount;
//        transaction_type_value = transaction_type;
//        order_id_value = order_id;
//        value_paid_amount = paid_amount;
//        value_shipping_charge = shipping;
//        is_cancel = is_cancelable;
//        cancel_msg_value = cancel_msg;
//        this.currency_symbol = currency_symbol;
//        this.is_gift = is_gift;
//        this.gift_charge = gift_charge;
//    }

    public new_getorderadapter(Context mContext, List<SellerOrder> sellerOrder, String date, Double totalAmount, Double discountAmount,
                               String transactionType, String orderId, Double paidAmount, Double shipping, Integer isCancellable,
                               String cancellationMessage, String currencySymbol, Integer isGift, Double giftCharge, float gstCharge) {
        this.sqlitemodelList = sellerOrder;
        this.mContext = mContext;
        this.currency_symbol = currencySymbol;
        this.is_cancel = String.valueOf(isCancellable);
        cancel_msg_value = cancellationMessage;
        Date = date;
        is_gift = String.valueOf(isGift);
        gift_charge = String.valueOf(giftCharge);
        order_id_value = orderId;
        value_shipping_charge = String.valueOf(shipping);
        value_paid_amount = String.valueOf(paidAmount);
        transaction_type_value = transactionType;
        discount_amount = String.valueOf(discountAmount);
        order_total_amount = String.valueOf(totalAmount);
        this.gstCharge = String.valueOf(gstCharge);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_row_seller_order, parent, false);

        return new MyViewHolder(itemView);
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SellerOrder sqlitemodel = sqlitemodelList.get(position);

        if (sqlitemodel.getShowTracking() == 1) {
            holder.btn_track.setVisibility(View.VISIBLE);
            holder.tv_status.setVisibility(View.GONE);
        } else {
            holder.btn_track.setVisibility(View.GONE);
            holder.tv_status.setVisibility(View.VISIBLE);

        }
        holder.btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, Track_web_view_activity.class);
//                intent.putExtra("trackinglink", ""+trackinglink);
//                mContext.startActivity(intent);
                new FinestWebView.Builder(mContext).show(sqlitemodel.getTrackingLink());
            }
        });

        holder.tv_status.setText(sqlitemodel.getOrderStatus());
        if (sqlitemodel.getOrderStatus().equalsIgnoreCase("Order Received")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.tv_status.setTextColor(mContext.getColor(R.color.print_colorPrimary));
            } else {
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.print_colorPrimary));
            }
        } else if (sqlitemodel.getOrderStatus().equalsIgnoreCase("Order Confirmed")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.tv_status.setTextColor(mContext.getColor(R.color.print_colorPrimary));
            } else {
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.print_colorPrimary));
            }
        } else if (sqlitemodel.getOrderStatus().equalsIgnoreCase("Order Cancelled")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.tv_status.setTextColor(mContext.getColor(R.color.color_red));
            } else {
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.color_red));
            }
        } else if (sqlitemodel.getOrderStatus().equalsIgnoreCase("Dispatched")) {
            if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar")) {
                holder.tv_seller_orderid.setText(mContext.getString(R.string.order_id) + sqlitemodel.getOrderId().replace("ORDER", "") + "(Dispatch Order)");
            } else {
                holder.tv_seller_orderid.setText(sqlitemodel.getOrderId().replace("ORDER", ""));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.tv_status.setTextColor(mContext.getColor(R.color.color_green));
            } else {
                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.color_green));
            }
        }

        if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar")) {
            holder.tv_seller_orderid.setText(sqlitemodel.getOrderId());
        } else {
            holder.tv_seller_orderid.setText(mContext.getString(R.string.seller_id) + sqlitemodel.getOrderId());
        }


        mAdapter = new new_orderitem_getorderadapter(mContext, sqlitemodel.getOrderItems(), sqlitemodel.getDate(), currency_symbol, is_cancel, cancel_msg_value,
                Date,
                is_gift,
                gift_charge,
                order_id_value,
                value_shipping_charge,
                value_paid_amount,
                transaction_type_value,
                discount_amount,
                order_total_amount, sqlitemodelList, gstCharge);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.rv_order_items.setLayoutManager(mLayoutManager);
        holder.rv_order_items.setItemAnimator(new DefaultItemAnimator());
        holder.rv_order_items.setAdapter(mAdapter);
    }


    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_seller_orderid, tv_status;
        public ImageView btn_track;
        public LinearLayout main_card;
        public RecyclerView rv_order_items;


        public MyViewHolder(View view) {
            super(view);
            tv_seller_orderid = view.findViewById(R.id.tv_seller_orderid);
            tv_status = view.findViewById(R.id.tv_status);
            btn_track = view.findViewById(R.id.btn_track);
            main_card = view.findViewById(R.id.main_card);
            rv_order_items = view.findViewById(R.id.rv_order_items);
        }
    }


}
