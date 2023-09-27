package com.mobile.cover.photo.editor.back.maker.testing_modules;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.mainapplication;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Vasundhara on 10-May-18.
 */

public class SelectedImageHorizontalAdapter extends RecyclerView.Adapter<SelectedImageHorizontalAdapter.MyView> {

    File[] contents;
    private ArrayList<ImageData> list = new ArrayList<>();
    private PhotoPickupImageActivity context;
    private RequestManager glide;
    private mainapplication application = mainapplication.getsInstance();
    private OnItemClickListner<ImageData> clickListner;

    public SelectedImageHorizontalAdapter(PhotoPickupImageActivity context) {
        this.context = context;
        this.glide = Glide.with(this.context);

//        FileUtils.TEMP_DIRECTORY_IMAGES.mkdirs();
//        FileUtils.TEMP_DIRECTORY_IMAGES1.mkdirs();

    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_gallery_view, parent, false);

        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, final int position) {
        final ImageData data = getItem(position);
        this.glide.load(data.temp_imagePath).into(holder.ivImage);

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (context.isFromPreview) {
                    application.min_pos = Math.min(application.min_pos, Math.max(0, position - 1));
                }


                try {


//                    if (getItem(position).getSelected_image_count() == application.getSelectedImages().get(position).getSelected_image_count()) {
//                        if (application.getSelectedImages().get(position).getImagePath().contains(".temp_images")) {
//                            Log.e("TAG", "delete image :" + application.getSelectedImages().get(position).getImagePath());
//
//
//                            application.removeSelectedImage(position);
//                         //   FileUtils.deleteFile(application.getSelectedImages().get(position).getImagePath());
//                        } else if (application.getSelectedImages().get(position).getImagePath().contains(".crop_images")) {
//                            Log.e("TAG", "delete image :" + application.getSelectedImages().get(position).getImagePath());
//                         //   FileUtils.deleteFile(application.getSelectedImages().get(position).getImagePath());
//                        }else {
//                            Log.e("TAG", "delete image :" + application.getSelectedImages().get(position).getImagePath());
//                           // FileUtils.deleteFile(application.getSelectedImages().get(position).getImagePath());
//                        }
//                    }

                    application.removeSelectedImage(position);

                    if (clickListner != null) {
                        clickListner.onItemClick(v, data);
                    }
                    notifyDataSetChanged();
                } catch (Exception e) {
                    Log.i("ERORRRRR", "onClick: " + e.toString());
                }
            }
        });
    }

    public void setOnItemClickListner(OnItemClickListner<ImageData> clickListner) {
        this.clickListner = clickListner;
    }

    @Override
    public int getItemCount() {
        return application.getOrgSelectedImages().size();
    }

    public ImageData getItem(int pos) {
        ArrayList<ImageData> list = application.getOrgSelectedImages();
        if (list.size() <= pos) {
            return new ImageData();
        }
        return list.get(pos);
    }

    public class MyView extends RecyclerView.ViewHolder {

        ImageView ivImage, ivDelete;

        public MyView(View itemView) {
            super(itemView);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivImage = itemView.findViewById(R.id.ivImage);
        }

        public void onItemClick(View view, ImageData item) {
            if (clickListner != null) {
                clickListner.onItemClick(view, item);
            }
        }
    }
}
