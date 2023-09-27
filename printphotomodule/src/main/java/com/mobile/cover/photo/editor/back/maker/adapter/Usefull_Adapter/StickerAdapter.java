package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

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
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.new_getdefault_images;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.MyViewHolder> {

    OnItemClickListener onItemClickListener;
    private List<new_getdefault_images> list = new ArrayList<>();
    private Context context;
    private DisplayImageOptions options;

    public StickerAdapter(Context context, List<new_getdefault_images> list) {
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
        Log.e("STICKER", "" + Share.screenWidth);

        holder.iv_sticker.getLayoutParams().width = Share.screenWidth / 4;
        holder.iv_sticker.getLayoutParams().height = Share.screenWidth / 4;

        ImageLoader.getInstance().displayImage(list.get(position).getImg(), holder.iv_sticker, options);
        holder.tv_name.setText(list.get(position).getName());

        holder.iv_sticker.setOnClickListener(new View.OnClickListener() {
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
