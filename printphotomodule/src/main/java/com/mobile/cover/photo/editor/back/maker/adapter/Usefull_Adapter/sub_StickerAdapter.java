package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Sticker;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.StickerActivity;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class sub_StickerAdapter extends RecyclerView.Adapter<sub_StickerAdapter.MyViewHolder> {

    OnItemClickListener onItemClickListener;
    private List<Sticker> list = new ArrayList<>();
    private Context context;
    private DisplayImageOptions options;

    public sub_StickerAdapter(Context context, List<Sticker> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_sticker_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.iv_sticker.getLayoutParams().width = Share.screenWidth / 4;
        holder.iv_sticker.getLayoutParams().height = Share.screenWidth / 4;

        ImageLoader.getInstance().displayImage(list.get(position).getImage(), holder.iv_sticker, options);
//        Glide.with(context).load(list.get(position).getImage()).into(holder.iv_sticker);

        holder.iv_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Share.imageuri = list.get(position).getImage();
                    StickerActivity.Companion.getActivity().finish();
                    ((Activity) context).finish();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception", "onClick: " + e.getLocalizedMessage());
                    Log.e("Exception", "onClick: " + e.getMessage());
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_sticker;
        TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_sticker = itemView.findViewById(R.id.iv_sticker);
            tv_name = itemView.findViewById(R.id.tv_name);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }
    }

}
