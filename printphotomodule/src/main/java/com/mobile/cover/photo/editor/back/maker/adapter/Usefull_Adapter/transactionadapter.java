package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.model.transaction;

import java.util.List;


public class transactionadapter extends RecyclerView.Adapter<transactionadapter.MyViewHolder> {

    private List<transaction> sqlitemodelList;
    private Context mContext;
    private List<transaction> sqlitemodelListFiltered;

    public transactionadapter(Context mContext, List<transaction> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final transaction sqlitemodel = sqlitemodelList.get(position);
        holder.tv_order_id.setText(mContext.getString(R.string.order_id) + sqlitemodel.getOrder_id().replace("ORDER", ""));
        holder.tv_date.setText(mContext.getString(R.string.date) + sqlitemodel.getDate_time());
        if (sqlitemodel.getStatus().equalsIgnoreCase("cash")) {
            holder.tv_rs.setText("+ " + mContext.getString(R.string.rs_icon) + sqlitemodel.getPrice());
        } else {
            holder.tv_rs.setText("- " + mContext.getString(R.string.rs_icon) + sqlitemodel.getPrice());
        }
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size() + Share.size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_order_id, tv_rs;


        public MyViewHolder(View view) {
            super(view);
            tv_order_id = view.findViewById(R.id.tv_order_id);
            tv_date = view.findViewById(R.id.tv_date);
            tv_rs = view.findViewById(R.id.tv_rs);
        }
    }
}
