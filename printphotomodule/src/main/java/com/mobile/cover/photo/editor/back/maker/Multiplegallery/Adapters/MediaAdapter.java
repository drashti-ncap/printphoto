package com.mobile.cover.photo.editor.back.maker.Multiplegallery.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.cover.photo.editor.back.maker.R;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {
    private List<String> bitmapList;
    private List<Boolean> selected;
    private Context context;

    public MediaAdapter(List<String> bitmapList, List<Boolean> selected, Context context) {
        this.bitmapList = bitmapList;
        this.context = context;
        this.selected = selected;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load("file://" + bitmapList.get(position)).dontAnimate().skipMemoryCache(true).into(holder.thumbnail);
        if (selected.get(position).equals(true)) {
            holder.check.setVisibility(View.VISIBLE);
            holder.check.setAlpha(150);
        } else {
            holder.check.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail, check;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.image);
            check = view.findViewById(R.id.image2);
        }
    }
}

