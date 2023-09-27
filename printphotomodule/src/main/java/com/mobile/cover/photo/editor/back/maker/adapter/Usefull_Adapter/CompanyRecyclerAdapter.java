package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.phonecase.FragmentPhoneCover;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.AllChild;

import java.util.ArrayList;
import java.util.List;

public class CompanyRecyclerAdapter extends RecyclerView.Adapter<CompanyRecyclerAdapter.MyViewHolder> {
    Context context;
    List<AllChild> subDataArrayList;

    OnItemClickListener onItemClickListener;

    public CompanyRecyclerAdapter(Context context, List<AllChild> subDataArrayList) {
        this.context = context;
        this.subDataArrayList = subDataArrayList;
    }

    @Override
    public CompanyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_name_item, parent, false);
        return new CompanyRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CompanyRecyclerAdapter.MyViewHolder holder, final int position) {


        holder.cardView.getLayoutParams().height = Share.screenHeight / 8;

        holder.name.setText(subDataArrayList.get(position).getName());
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

    public ArrayList<AllChild> filter(String s) {
        ArrayList<AllChild> subData = new ArrayList<>();
        if (s.length() == 0) {
            subData.addAll(Share.search_dynamic_sub_category_list);
            FragmentPhoneCover.tv_no_fnd.setVisibility(View.GONE);
        } else {
            for (AllChild wp : Share.search_dynamic_sub_category_list) {
                Log.d("size", "1==>" + Share.search_dynamic_sub_category_list.size());

                if (wp.getName().toLowerCase().contains(s.toLowerCase())) {

                    subData.add(wp);
                    FragmentPhoneCover.tv_no_fnd.setVisibility(View.GONE);
                }
                if (subData.size() <= 0) {
                    FragmentPhoneCover.tv_no_fnd.setVisibility(View.VISIBLE);
                }
            }
        }
        noti(subData);
        return subData;
    }

    public void noti(ArrayList<AllChild> subData) {
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
