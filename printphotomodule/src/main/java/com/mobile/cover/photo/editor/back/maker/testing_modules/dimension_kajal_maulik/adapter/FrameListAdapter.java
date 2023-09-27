package com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pagination.model.Result;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.testing_modules.PhotoPickupImageActivity;

import java.util.List;


public class FrameListAdapter extends RecyclerView.Adapter<FrameListAdapter.Holder> {
    Context context;
    List<Result> id;

    public FrameListAdapter(Context context, List<Result> id) {
        this.context = context;
        this.id = id;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.list_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {
        Glide.with(context).asBitmap().load(id.get(i).getNImage1()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                holder.img_frame.setImageBitmap(resource);
            }
        });

        holder.img_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CHECKGALLERY", "onResourceReady: gallery--2");
                Intent intent = new Intent(context, PhotoPickupImageActivity.class);
                Share.itemnum = 5;
                intent.putExtra("from", "" + 1);
                intent.putExtra("position", "" + i);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView img_frame;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img_frame = itemView.findViewById(R.id.img_frame);
        }
    }
}
