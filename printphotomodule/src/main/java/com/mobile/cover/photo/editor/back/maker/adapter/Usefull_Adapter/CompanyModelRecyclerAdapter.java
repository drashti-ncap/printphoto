package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.phonecase.SelectCompanyMobel;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;

import java.util.ArrayList;
import java.util.List;

public class CompanyModelRecyclerAdapter extends RecyclerView.Adapter<CompanyModelRecyclerAdapter.MyViewHolder> {
    Context context;
    List<model_details_data> subDataArrayList;

    OnItemClickListener onItemClickListener;

    public CompanyModelRecyclerAdapter(Context context, List<model_details_data> subDataArrayList) {
        this.context = context;
        this.subDataArrayList = subDataArrayList;
    }

    @Override
    public CompanyModelRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_name_item, parent, false);
        return new CompanyModelRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CompanyModelRecyclerAdapter.MyViewHolder holder, final int position) {


        holder.cardView.getLayoutParams().height = Share.screenHeight / 6;

        holder.name.setText(subDataArrayList.get(position).getModalName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickLister(view, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return subDataArrayList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArrayList<model_details_data> filter(String s) {
        ArrayList<model_details_data> subData = new ArrayList<>();
        if (s.length() == 0) {
            subData.addAll(Share.subDataModelDetailsArrayList_search);
            SelectCompanyMobel.tv_no_fnd.setVisibility(View.GONE);
        } else {
            for (model_details_data wp : Share.subDataModelDetailsArrayList_search) {
                if (wp.getModalName().toLowerCase().contains(s.toLowerCase())) {

                    subData.add(wp);
                    SelectCompanyMobel.tv_no_fnd.setVisibility(View.GONE);
                }
                if (subData.size() <= 0) {
                    SelectCompanyMobel.tv_no_fnd.setVisibility(View.VISIBLE);
                }
            }
        }
        noti(subData);
        return subData;
    }

    public void noti(ArrayList<model_details_data> subData) {
        this.subDataArrayList = subData;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;


        TextView name;

        MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            cardView = view.findViewById(R.id.main_card);


        }
    }

}
