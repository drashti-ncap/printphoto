package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Select_region;
import com.mobile.cover.photo.editor.back.maker.model.region_model_data;

import java.util.ArrayList;
import java.util.List;


public class region_adapter extends RecyclerView.Adapter<region_adapter.MyViewHolder> {

    TextView tv_no_found;
    private List<region_model_data> sqlitemodelList;
    private Context mContext;

    public region_adapter(Context mContext, ArrayList<region_model_data> sqlitemodelList, TextView textView) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
        this.tv_no_found = textView;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_name.setText(sqlitemodelList.get(position).getRegion_name());
        if (sqlitemodelList.get(position).getSelect()) {
            holder.iv_select.setVisibility(View.VISIBLE);
        } else {
            holder.iv_select.setVisibility(View.GONE);
        }
        holder.main_card.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Log.e("CHECKCLICK", "onSingleClick: check click" );
                for (int i = 0; i < Share.region_search_array.size(); i++) {
                    Share.region_search_array.get(i).setSelect(false);
                    if (Share.region_search_array.get(i).getRegion_code().equalsIgnoreCase(sqlitemodelList.get(position).getRegion_code())) {
                        Share.region_search_array.get(i).setSelect(true);
                    }
                }
                for (int i = 0; i < sqlitemodelList.size(); i++) {
                    sqlitemodelList.get(i).setSelect(false);
                    if (i == position) {
                        Share.select_position = position;
                        sqlitemodelList.get(i).setSelect(true);
                    }
                }

                holder.iv_select.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.region_name_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public ArrayList<region_model_data> filter(String s) {
        ArrayList<region_model_data> subData = new ArrayList<>();
        if (s.length() == 0) {
            subData.addAll(Share.region_search_array);
            tv_no_found.setVisibility(View.GONE);

        } else {
            for (region_model_data wp : Share.region_search_array) {

                if (wp.getRegion_name().toLowerCase().trim().contains(s.toLowerCase())) {

                    subData.add(wp);
                    tv_no_found.setVisibility(View.GONE);

                }
                if (subData.size() <= 0) {
                    tv_no_found.setVisibility(View.VISIBLE);
                }
            }
        }
        noti(subData);
        return subData;
    }

    public void noti(ArrayList<region_model_data> subData) {
        this.sqlitemodelList = subData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_select;
        public TextView tv_name;
        public CardView main_card;

        public MyViewHolder(View view) {
            super(view);
            iv_select = view.findViewById(R.id.iv_select);
            tv_name = view.findViewById(R.id.name);
            main_card = view.findViewById(R.id.main_card);
        }
    }

//    public  void m6676a(ArrayList<msg_listmodel> list) {
//        this.sqlitemodelList = list;
//    }

}
