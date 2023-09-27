package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Orderdetails;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.new_getorderadapter;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Orderdetails sqlitemodel;
    private List<Orderdetails> sqlitemodelList;
    private Context mContext;
    private new_getorderadapter mAdapter;


    public OrderAdapter(Context mContext, List<Orderdetails> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extremely_new_row_order_details, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                detailscreen(sqlitemodel);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        sqlitemodel = sqlitemodelList.get(position);
        holder.tv_order_id.setText(mContext.getString(R.string.order_id) + sqlitemodel.getOrderId().replace("ORDER", ""));
        holder.tv_total_amount.setText(sqlitemodel.getCurrencySymbol() + sqlitemodel.getPaidAmount());
//        holder.tv_status.setText(sqlitemodel.getOrder_status());
//        if (sqlitemodel.getOrder_status().equalsIgnoreCase("Order Received")) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                holder.tv_status.setTextColor(mContext.getColor(R.color.colorPrimary));
//            } else {
//                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
//            }
//        } else if (sqlitemodel.getOrder_status().equalsIgnoreCase("Order Confirmed")) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                holder.tv_status.setTextColor(mContext.getColor(R.color.colorPrimary));
//            } else {
//                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
//            }
//        } else if (sqlitemodel.getOrder_status().equalsIgnoreCase("Order Cancelled")) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                holder.tv_status.setTextColor(mContext.getColor(R.color.color_red));
//            } else {
//                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.color_red));
//            }
//        } else if (sqlitemodel.getOrder_status().equalsIgnoreCase("Dispatched")) {
//            ViewGroup.LayoutParams layoutParams = holder.tv_order_id.getLayoutParams();
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            holder.tv_order_id.setLayoutParams(layoutParams);
//            holder.tv_status.setVisibility(View.GONE);
//            holder.tv_order_id.setText(mContext.getString(R.string.order_id) + sqlitemodel.getOrder_id().replace("ORDER", "") + "(Dispatch Order)");
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                holder.tv_status.setTextColor(mContext.getColor(R.color.color_green));
//            } else {
//                holder.tv_status.setTextColor(mContext.getResources().getColor(R.color.color_green));
//            }
//        }
        mAdapter = new new_getorderadapter(mContext, sqlitemodel.getSellerOrder(), sqlitemodel.getDate(), sqlitemodel.getTotalAmount(), sqlitemodel.getDiscountAmount(), sqlitemodel.getTransactionType(), sqlitemodel.getOrderId(), sqlitemodel.getPaidAmount(), sqlitemodel.getShipping(), sqlitemodel.getIsCancellable(), sqlitemodel.getCancellationMessage(), sqlitemodel.getCurrencySymbol(), sqlitemodel.getIsGift(), sqlitemodel.getGiftCharge(),sqlitemodel.getGst_charges());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.rv_seller_order_details.setLayoutManager(mLayoutManager);
        holder.rv_seller_order_details.setItemAnimator(new DefaultItemAnimator());
        holder.rv_seller_order_details.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size() + Share.size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_order_id, tv_total_amount;
        public RecyclerView rv_seller_order_details;
        public CardView main_card;


        public MyViewHolder(View view) {
            super(view);
            tv_order_id = view.findViewById(R.id.tv_order_id);
            tv_total_amount = view.findViewById(R.id.tv_final_amount);
            rv_seller_order_details = view.findViewById(R.id.rv_seller_order_details);
            main_card = view.findViewById(R.id.main_card);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        }
    }


}
