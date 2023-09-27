package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.model.filter;

import java.util.List;


public class mall_filter_list_adapter extends RecyclerView.Adapter<mall_filter_list_adapter.MyViewHolder> {

    private List<String> sqlitemodelList;
    private String catName;
    private Context mContext;

    public mall_filter_list_adapter(Context mContext, List<String> sqlitemodelList, String name) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
        this.catName = name;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_filter_list, parent, false);

        return new MyViewHolder(itemView);
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        holder.tv_value.setText(sqlitemodelList.get(position));
        holder.check_box.setTag(catName);
        Log.e("SIZE", "onBindViewHolder: " + Share.checked_arraylist.size());
//        if (Share.checked_list.size() > 0) {
//            for (int i = 0; i < Share.checked_list.size(); i++) {
//                Log.e("CHECKED", "onBindViewHolder: "+Share.checked_list.get(i));
//                if (Share.checked_list.get(i))
//                {
//                    holder.check_box.setChecked(true);
//                }else {
//                    holder.check_box.setChecked(false);
//                }
//            }
//

        holder.check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("HOLDER", "onSingleClick: " + holder.check_box.isChecked());
                if (holder.check_box.isChecked()) {
                    Log.e("ADDED", "onSingleClick:======>" + Share.checked_arraylist.size());
                    for (int i = 0; i < Share.checked_arraylist.size(); i++) {
                        if (Share.checked_arraylist.get(i).getValue().contains(holder.check_box.getTag() + "//" + holder.tv_value.getText().toString())) {
                            Share.checked_arraylist.remove(i);
//                            Share.checked_list.remove(i);
                            Log.e("HERE", "onSingleClick: REMOVED");
                        }
                    }
                    Share.checked_arraylist.add(new filter(holder.check_box.getTag() + "//" + holder.tv_value.getText().toString(), true));

                    Log.e("ADDED", "onSingleClick:======>ADDED" + Share.checked_arraylist.size());

//                    Share.checked_list.add(true);
                } else {
                    for (int i = 0; i < Share.checked_arraylist.size(); i++) {
                        Log.e("VALUE", "onSingleClick:======>1 " + Share.checked_arraylist.get(i).getValue());
                        Log.e("VALUE", "onSingleClick:======>2 " + holder.check_box.getTag() + "//" + holder.tv_value.getText().toString());
                        if (Share.checked_arraylist.get(i).getValue().contains(holder.check_box.getTag() + "//" + holder.tv_value.getText().toString())) {
                            Share.checked_arraylist.remove(i);
//                            Share.checked_list.remove(i);
                            Log.e("HERE", "onSingleClick: REMOVED");
                        } else {
                            Log.e("ELSE HERE", "onSingleClick: REMOVED");
                        }
//                        else {
//                            Share.checked_list.add(false);
//                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout ll_list_row;
        public CheckBox check_box;
        public TextView tv_value;

        public MyViewHolder(View view) {
            super(view);
            ll_list_row = view.findViewById(R.id.ll_list_row);
            check_box = view.findViewById(R.id.check_box);
            tv_value = view.findViewById(R.id.tv_value);
        }
    }
}
