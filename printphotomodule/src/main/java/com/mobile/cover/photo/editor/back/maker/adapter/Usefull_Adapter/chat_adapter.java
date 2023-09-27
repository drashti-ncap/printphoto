package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Chat.models.ChatMessage;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;

import java.util.List;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;


public class chat_adapter extends RecyclerView.Adapter<chat_adapter.MyViewHolder> {

    private List<ChatMessage> sqlitemodelList;
    private Context mContext;

    public chat_adapter(Context mContext, List<ChatMessage> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {

        if (sqlitemodelList.get(position).getType() == ChatMessage.Type.SENT) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        chat_adapter.MyViewHolder vh = null;
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complain_row_send, parent, false);
            vh = new MyViewHolder(view);
        } else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complain_row_recieve, parent, false);
            vh = new MyViewHolder(view);
        }
        return vh;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ChatMessage sqlitemodel = sqlitemodelList.get(position);

        holder.tv_timestamp.setText(sqlitemodel.getTimestamp());
        holder.tv_msg.setText(sqlitemodel.getMessage());
        if (!sqlitemodel.getImageurl().equalsIgnoreCase("")) {
            holder.iv_url.setVisibility(View.VISIBLE);
            Glide.with(mContext).asBitmap()
                    .load(sqlitemodel.getImageurl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            holder.iv_url.setImageBitmap(resource);
                            System.gc();
                        }


                        @Override
                        public void onLoadFailed(Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                        }
                    });
        } else {
            holder.iv_url.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size() + Share.size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_msg, tv_timestamp;
        public ImageViewZoom iv_url;
        public LinearLayout ll_row;


        public MyViewHolder(View view) {
            super(view);
            tv_msg = view.findViewById(R.id.tv_msg);
            tv_timestamp = view.findViewById(R.id.tv_timestamp);
            iv_url = view.findViewById(R.id.iv_url);
            ll_row = view.findViewById(R.id.ll_row);
        }
    }


}
