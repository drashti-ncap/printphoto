package com.mobile.cover.photo.editor.back.maker.Multiplegallery.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.cover.photo.editor.back.maker.R;

import java.util.List;

public class BucketsAdapter extends RecyclerView.Adapter<BucketsAdapter.MyViewHolder> {
    private List<String> bucketNames, bitmapList;
    private Context context;

    public BucketsAdapter(List<String> bucketNames, List<String> bitmapList, Context context) {
        this.bucketNames = bucketNames;
        this.bitmapList = bitmapList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        bucketNames.get(position);
        holder.title.setText(bucketNames.get(position));
        Glide.with(context).load("file://" + bitmapList.get(position)).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return bucketNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            thumbnail = view.findViewById(R.id.image);
        }
    }
}

