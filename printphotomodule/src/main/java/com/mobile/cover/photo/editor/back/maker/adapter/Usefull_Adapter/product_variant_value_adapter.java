package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.variant.ProductDetails1;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.customView.sharp.Sharp;
import com.mobile.cover.photo.editor.back.maker.model.variant_value;

import java.util.List;


public class product_variant_value_adapter extends RecyclerView.Adapter<product_variant_value_adapter.MyViewHolder> {

    private List<variant_value> sqlitemodelList;
    private Context mContext;
    private String catname;
    private boolean isWomen = false;

    public product_variant_value_adapter(Context mContext, List<variant_value> sqlitemodelList, String name,boolean isWomen) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
        this.catname = name;
        this.isWomen = isWomen;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_variant_name, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.tv_variant_name.setTag(catname);
        holder.tv_variant_name.setText(sqlitemodelList.get(position).getVariant_value());


        if (isWomen){
            if (sqlitemodelList.get(position).getSelect()) {
                holder.tv_variant_name.setEnabled(false);
                for (int j = 0; j < Share.variant_list_value_women.size(); j++) {
                    String[] value = Share.variant_list_value_women.get(j).split("=");
                    Log.e("VALUE", "onClick: " + Share.variant_list_value_women.get(j));
                    Log.e("VALUE_TAG", "onClick: " + value[0]);
                    if (holder.tv_variant_name.getTag().equals(value[0])) {
                        Share.variant_list_value_women.remove(j);
                    }
                }
                Share.variant_list_value_women.add(holder.tv_variant_name.getTag()  + "=" + sqlitemodelList.get(position).getVariant_value());
                holder.tv_variant_name.setBackground(mContext.getResources().getDrawable(R.drawable.mall_variant_select));
                holder.tv_variant_name.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                holder.tv_variant_name.setEnabled(true);
                holder.tv_variant_name.setBackground(mContext.getResources().getDrawable(R.drawable.mall_variant_deselect));
                holder.tv_variant_name.setTextColor(mContext.getResources().getColor(R.color.black));
            }

            holder.tv_variant_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ISSELECT", "onClick: " + sqlitemodelList.get(position).getSelect());

                    for (int i = 0; i < Share.variant_list_value_women.size(); i++) {
                        String[] value = Share.variant_list_value_women.get(i).split("=");
                        Log.e("VALUE", "onClick: " + Share.variant_list_value_women.get(i));
                        Log.e("VALUE_TAG", "onClick: " + value[0]);
                    }

                    for (int i = 0; i < sqlitemodelList.size(); i++) {
                        if (sqlitemodelList.get(i).getVariant_value().equalsIgnoreCase(sqlitemodelList.get(position).getVariant_value())) {
                            if (Share.variant_list_value_women.size() != 0) {
                                for (int j = 0; j < Share.variant_list_value_women.size(); j++) {
                                    String[] value = Share.variant_list_value_women.get(j).split("=");
                                    Log.e("VALUE", "onClick: " + Share.variant_list_value_women.get(j));
                                    Log.e("VALUE_TAG", "onClick: " + value[0]);
                                    if (holder.tv_variant_name.getTag().equals(value[0])) {
                                        Share.variant_list_value_women.remove(j);
                                    }
                                }

                                Share.variant_list_value_women.add(catname + "=" + sqlitemodelList.get(position).getVariant_value());
                                sqlitemodelList.get(i).setSelect(true);
                            } else {
                                Share.variant_list_value_women.add(catname + "=" + sqlitemodelList.get(position).getVariant_value());
                                sqlitemodelList.get(i).setSelect(true);
                            }
                        } else {
                            sqlitemodelList.get(i).setSelect(false);
                        }


                        notifyDataSetChanged();
                    }
                }
            });
        }else {
            if (sqlitemodelList.get(position).getSelect()) {
                holder.tv_variant_name.setEnabled(false);
                for (int j = 0; j < Share.variant_list_value.size(); j++) {
                    String[] value = Share.variant_list_value.get(j).split("=");
                    Log.e("VALUE", "onClick: " + Share.variant_list_value.get(j));
                    Log.e("VALUE_TAG", "onClick: " + value[0]);
                    if (holder.tv_variant_name.getTag().equals(value[0])) {
                        Share.variant_list_value.remove(j);
                    }
                }
                Share.variant_list_value.add(holder.tv_variant_name.getTag() + "=" + sqlitemodelList.get(position).getVariant_value());
                holder.tv_variant_name.setBackground(mContext.getResources().getDrawable(R.drawable.mall_variant_select));
                holder.tv_variant_name.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                holder.tv_variant_name.setEnabled(true);
                holder.tv_variant_name.setBackground(mContext.getResources().getDrawable(R.drawable.mall_variant_deselect));
                holder.tv_variant_name.setTextColor(mContext.getResources().getColor(R.color.black));
            }

            holder.tv_variant_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("ISSELECT", "onClick: " + sqlitemodelList.get(position).getSelect());

                    for (int i = 0; i < Share.variant_list_value.size(); i++) {
                        String[] value = Share.variant_list_value.get(i).split("=");
                        Log.e("VALUE", "onClick: " + Share.variant_list_value.get(i));
                        Log.e("VALUE_TAG", "onClick: " + value[0]);
                    }

                    for (int i = 0; i < sqlitemodelList.size(); i++) {
                        if (sqlitemodelList.get(i).getVariant_value().equalsIgnoreCase(sqlitemodelList.get(position).getVariant_value())) {
                            if (Share.variant_list_value.size() != 0) {
                                for (int j = 0; j < Share.variant_list_value.size(); j++) {
                                    String[] value = Share.variant_list_value.get(j).split("=");
                                    Log.e("VALUE", "onClick: " + Share.variant_list_value.get(j));
                                    Log.e("VALUE_TAG", "onClick: " + value[0]);
                                    if (holder.tv_variant_name.getTag().equals(value[0])) {
                                        Share.variant_list_value.remove(j);
                                    }
                                }

                                Share.variant_list_value.add(catname + "=" + sqlitemodelList.get(position).getVariant_value());
                                sqlitemodelList.get(i).setSelect(true);
                            } else {
                                Share.variant_list_value.add(catname + "=" + sqlitemodelList.get(position).getVariant_value());
                                sqlitemodelList.get(i).setSelect(true);
                            }
                        } else {
                            sqlitemodelList.get(i).setSelect(false);
                        }


                        notifyDataSetChanged();
                    }
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_variant_name;

        public MyViewHolder(View view) {
            super(view);
            tv_variant_name = view.findViewById(R.id.tv_variant_name);
        }
    }
}
