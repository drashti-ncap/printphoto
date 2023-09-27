package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.ProductDetail;
import com.mobile.cover.photo.editor.back.maker.R;

import java.util.List;


public class product_detail_adapter extends RecyclerView.Adapter<product_detail_adapter.MyViewHolder> {

    private List<String> sqlitemodelList;
    private Context mContext;

    public product_detail_adapter(Context mContext, List<String> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pro_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_name.setText(sqlitemodelList.get(position).split("=")[0]);
        holder.tv_value.setText(sqlitemodelList.get(position).split("=")[1]);

    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_value;

        public MyViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_value = view.findViewById(R.id.tv_value);
        }
    }
}
