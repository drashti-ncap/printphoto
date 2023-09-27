package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.mobile.cover.photo.editor.back.maker.R;

import java.util.List;


public class mall_filter_value_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> sqlitemodelList;
    private Context mContext;

    public mall_filter_value_adapter(Context mContext, List<String> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {

        if (sqlitemodelList.get(0).length() == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_price, parent, false);
            vh = new MyViewHolder(view);
        } else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_list, parent, false);
            vh = new new_listview_holder(view);
        }
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)) {
            case 1:
                MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
                myViewHolder.rangeSeekbar.setMinValue(0);
                myViewHolder.rangeSeekbar.setMaxValue(Float.parseFloat(sqlitemodelList.get(0)));
                break;
            case 2:
                new_listview_holder new_listview_holder = (new_listview_holder) viewHolder;
                new_listview_holder.tv_value.setText(sqlitemodelList.get(i));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_row;
        public CrystalRangeSeekbar rangeSeekbar;


        public MyViewHolder(View view) {
            super(view);
            ll_row = view.findViewById(R.id.ll_row);
            rangeSeekbar = view.findViewById(R.id.rangeSeekbar);
        }
    }

    private class new_listview_holder extends RecyclerView.ViewHolder {
        public LinearLayout ll_list_row;
        public CheckBox check_box;
        public TextView tv_value;

        public new_listview_holder(View view) {
            super(view);
            ll_list_row = view.findViewById(R.id.ll_list_row);
            check_box = view.findViewById(R.id.check_box);
            tv_value = view.findViewById(R.id.tv_value);
        }
    }


}
