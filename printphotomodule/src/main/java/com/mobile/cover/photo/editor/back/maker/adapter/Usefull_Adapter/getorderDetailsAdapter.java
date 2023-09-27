package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.SellerOrder;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.complaints.Complain_ticket_system;
import com.mobile.cover.photo.editor.back.maker.model.getorderdetails;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;


public class getorderDetailsAdapter extends RecyclerView.Adapter<getorderDetailsAdapter.MyViewHolder> {

    ProgressDialog pd;
    private List<SellerOrder> sqlitemodelList;
    private Context mContext;
    private List<getorderdetails> sqlitemodelListFiltered;

    public getorderDetailsAdapter(Context mContext, List<SellerOrder> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SellerOrder sqlitemodel = sqlitemodelList.get(position);

        holder.tv_order_id.setText(mContext.getString(R.string.seller_id_new) + sqlitemodel.getOrderId());
        holder.tv_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FinestWebView.Builder(mContext).show(sqlitemodel.getTrackingLink());
            }
        });
        holder.tv_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Complain_ticket_system.class);
                intent.putExtra("order_id", sqlitemodel.getOrderId());
                mContext.startActivity(intent);
            }
        });

        new_Sub_getorderDetailsAdapter new_sub_getorderDetailsAdapter = new new_Sub_getorderDetailsAdapter(mContext, sqlitemodel.getOrderItems());
        holder.rv_order_item.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv_order_item.setItemAnimator(new DefaultItemAnimator());
        holder.rv_order_item.setAdapter(new_sub_getorderDetailsAdapter);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_extremely_new_row_order_details, parent, false);

        return new MyViewHolder(itemView);
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_order_id, tv_complain, tv_track;
        public RecyclerView rv_order_item;


        public MyViewHolder(View view) {
            super(view);

            tv_order_id = view.findViewById(R.id.tv_order_id);
            tv_complain = view.findViewById(R.id.tv_complain);
            tv_track = view.findViewById(R.id.tv_track);
            rv_order_item = view.findViewById(R.id.rv_order_item);

        }
    }

}
