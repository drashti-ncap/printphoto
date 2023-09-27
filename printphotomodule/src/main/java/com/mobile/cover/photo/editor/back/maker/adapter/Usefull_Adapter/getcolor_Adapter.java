package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;

import java.util.List;


public class getcolor_Adapter extends RecyclerView.Adapter<getcolor_Adapter.MyViewHolder> {

    OnItemClickListener onItemClickListener;
    private List<String> sqlitemodelList;
    private Context mContext;

    public getcolor_Adapter(Context mContext, List<String> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_row_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Drawable mDrawable = mContext.getResources().getDrawable(R.drawable.round_rect_blue);
        if (sqlitemodelList.get(position).equalsIgnoreCase(String.valueOf(Color.BLACK))) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN));
        } else if (sqlitemodelList.get(position).equalsIgnoreCase(String.valueOf(Color.BLUE))) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN));
        } else if (sqlitemodelList.get(position).equalsIgnoreCase(String.valueOf(Color.YELLOW))) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN));
        } else if (sqlitemodelList.get(position).equalsIgnoreCase(String.valueOf(Color.RED))) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN));
        } else if (sqlitemodelList.get(position).equalsIgnoreCase(String.valueOf(Color.GREEN))) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN));
        } else if (sqlitemodelList.get(position).equalsIgnoreCase(String.valueOf(Color.LTGRAY))) {
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN));
        } else {
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));
        }
        holder.btn_color.setBackground(mDrawable);
        holder.btn_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickLister(v, position);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn_color;

        public MyViewHolder(View view) {
            super(view);

            btn_color = view.findViewById(R.id.btn_color);
        }
    }
}
