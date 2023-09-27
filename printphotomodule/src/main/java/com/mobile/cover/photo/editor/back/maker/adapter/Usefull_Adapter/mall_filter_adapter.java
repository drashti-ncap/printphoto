package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.model.AvailableFilter;

import java.util.List;


public class mall_filter_adapter extends RecyclerView.Adapter<mall_filter_adapter.MyViewHolder> {

    ProgressDialog pd;
    private List<AvailableFilter> sqlitemodelList;
    private Context mContext;

    public mall_filter_adapter(Context mContext, List<AvailableFilter> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    public static boolean isViewShown(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_filter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.setIsRecyclable(true);
        holder.rv_filter_sub_filter.setHasFixedSize(true);
        mall_filter_list_adapter mall_filter_value_adapter = new mall_filter_list_adapter(mContext, sqlitemodelList.get(position).getValue(), sqlitemodelList.get(position).getName());
        holder.rv_filter_sub_filter.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rv_filter_sub_filter.setAdapter(mall_filter_value_adapter);

        holder.tv_filter_title.setText(sqlitemodelList.get(position).getName());
        if (sqlitemodelList.get(position).getValue().get(0) != null) {
            holder.tv_filter_value.setText(sqlitemodelList.get(position).getValue().get(0));
        } else {
            holder.tv_filter_value.setText("");
        }
        holder.ll_filter_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!Share.dynamic_url.contains(sqlitemodelList.get(position).getName())){
//                    Share.dynamic_url=Share.dynamic_url+"&"+sqlitemodelList.get(position).getName();
//                }
                if (!isViewShown(holder.iv_arrow)) {
                    holder.rv_filter_sub_filter.setVisibility(View.GONE);
                    holder.iv_arrow.setVisibility(View.VISIBLE);
                    holder.iv_arrow_rev.setVisibility(View.GONE);
                    holder.rangeSeekbar.setVisibility(View.GONE);
                    holder.ll_seekbar_value.setVisibility(View.GONE);
                } else {
                    holder.iv_arrow.setVisibility(View.GONE);
                    if (sqlitemodelList.get(position).getIs_price() == 1) {
                        holder.rangeSeekbar.setVisibility(View.VISIBLE);
                        holder.rv_filter_sub_filter.setVisibility(View.GONE);
                        holder.ll_seekbar_value.setVisibility(View.VISIBLE);
                    } else {
                        holder.rangeSeekbar.setVisibility(View.GONE);
                        holder.ll_seekbar_value.setVisibility(View.GONE);
                        holder.rv_filter_sub_filter.setVisibility(View.VISIBLE);
                    }
                    holder.iv_arrow_rev.setVisibility(View.VISIBLE);

                }
            }
        });
        if (position == 0) {
            Log.e("VALUE", "onBindViewHolder: " + Float.parseFloat(sqlitemodelList.get(position).getValue().get(0)));
            Log.e("VALUE", "onBindViewHolder: " + sqlitemodelList.get(position).getValue().get(0));
            holder.rangeSeekbar.setMinValue(0);
            holder.rangeSeekbar.setMaxValue(Float.parseFloat(sqlitemodelList.get(position).getValue().get(0)));
            if (!Share.min_price.equalsIgnoreCase("") && !Share.max_price.equalsIgnoreCase("")) {
                holder.rangeSeekbar.setMinStartValue(Float.parseFloat(Share.min_price));
                holder.rangeSeekbar.setMaxStartValue(Float.parseFloat(Share.max_price));
            }

            holder.rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                @Override
                public void valueChanged(Number minValue, Number maxValue) {
                    Share.max_price = String.valueOf(maxValue.doubleValue());
                    Share.min_price = String.valueOf(minValue.doubleValue());
                    holder.tv_min.setText(mContext.getString(R.string.min_price) + Share.symbol + minValue.doubleValue());
                    holder.tv_max.setText(mContext.getString(R.string.max_price) + Share.symbol + maxValue.doubleValue());
                }
            });

            holder.rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
                @Override
                public void finalValue(Number minValue, Number maxValue) {
                    Share.max_price = "&max_price=" + maxValue.doubleValue();
                    Share.min_price = "&min_price=" + minValue.doubleValue();
                    holder.tv_min.setText(mContext.getString(R.string.min_price) + Share.symbol + minValue.doubleValue());
                    holder.tv_max.setText(mContext.getString(R.string.max_price) + Share.symbol + maxValue.doubleValue());
                }
            });
        } else {
            holder.rangeSeekbar.setVisibility(View.GONE);
            holder.ll_seekbar_value.setVisibility(View.GONE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_filter_main, ll_seekbar_value;
        ImageView iv_arrow, iv_arrow_rev;
        TextView tv_filter_title, tv_filter_value, tv_min, tv_max;
        RecyclerView rv_filter_sub_filter;
        CrystalRangeSeekbar rangeSeekbar;

        public MyViewHolder(View view) {
            super(view);
            ll_filter_main = view.findViewById(R.id.ll_filter_main);
            ll_seekbar_value = view.findViewById(R.id.ll_seekbar_value);
            iv_arrow = view.findViewById(R.id.iv_arrow);
            iv_arrow_rev = view.findViewById(R.id.iv_arrow_rev);
            tv_filter_title = view.findViewById(R.id.tv_filter_title);
            tv_filter_value = view.findViewById(R.id.tv_filter_value);
            rv_filter_sub_filter = view.findViewById(R.id.rv_filter_sub_filter);
            tv_min = view.findViewById(R.id.tv_min);
            tv_max = view.findViewById(R.id.tv_max);
            rangeSeekbar = view.findViewById(R.id.rangeSeekbar);
        }
    }
}
