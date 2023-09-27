package com.mobile.cover.photo.editor.back.maker.Drag_recyclerview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.model.frame_bitmap_model;

import java.util.List;

public class MyAdapterRecyclerView extends RecyclerView.Adapter<MyAdapterRecyclerView.MyViewHolder> {

    private List<frame_bitmap_model> mList;
    private Context mContext;

    public MyAdapterRecyclerView(Context context, List<frame_bitmap_model> mList) {
        this.mList = mList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        frame_bitmap_model item = mList.get(position); // Object Item
        Log.e("ID", "onBindViewHolder: =>" + mList.get(position).getId());
        holder.setImage(item.getOriginal_bitmap()); // Image
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUser;
        TextView tvName, tvDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivUser = itemView.findViewById(R.id.ivUser);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void setName(String name) {
            tvName.setText(name);
        }

        public void setDescription(String description) {
            tvDescription.setText(description);
        }

        public void setImage(Bitmap idImage) {
            ivUser.setImageBitmap(idImage);
        }

    }

}
