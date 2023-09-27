package com.mobile.cover.photo.editor.back.maker.adapter.Testing_adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.model.background_rv_images;

import java.util.List;


public class test_frame_image_adapter extends RecyclerView.Adapter<test_frame_image_adapter.MyViewHolder> {

    Bitmap bitmap;
    ProgressDialog progressDialog;
    Bitmap bitmap_resource;
    private int[] sqlitemodelList;
    private Context mContext;
    //    private DisplayImageOptions options;
    private List<background_rv_images> sqlitemodelListFiltered_images;

    public test_frame_image_adapter(Context mContext, int[] sqlitemodelList_images) {
        this.sqlitemodelList = sqlitemodelList_images;
        this.mContext = mContext;
    }

    @Override
    public test_frame_image_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_main_row_default_image_item, parent, false);

        return new test_frame_image_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final test_frame_image_adapter.MyViewHolder holder, final int position) {

//        ImageLoader.getInstance().displayImage(sqlitemodelList[position], holder.background, options);

        Glide.with(mContext).load(sqlitemodelList[position]).into(holder.background);
//        holder.background.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog = new ProgressDialog(mContext);
//                progressDialog.setMessage("Generating Image...");
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//
//                Glide.with(mContext).load("https://printphoto.in/Photo_case/public/background_images/" + sqlitemodel.getImages()).asBitmap().into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//
//                        int width = 0, height = 0;
//                        final int IMAGE_MAX_SIZE = (int) 1200000;
//                        width = (int) resource.getWidth();
//                        height = (int) resource.getHeight();
//
//                        Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========1122=>" + Share.maskheight);
//
//                        double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
//                        double x = (y / height) * width;
//                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(resource, (int) x, (int) y, true);
//                        progressDialog.dismiss();
//                        Log.e(TAG, "onResourceReady: " + CaseEditActivity.stickerView);
//                        Log.e(TAG, "onResourceReady: " + Dynamic_EditActivity.stickerView);
//                        Log.e(TAG, "onResourceReady: " + CoffeeMugEditActivity.stickerView);
//                        Log.e(TAG, "onResourceReady: " + Custom_CaseEditActivity.stickerView);
//                        if (CaseEditActivity.stickerView != null) {
//                            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap));
//                            drawableSticker.setTag("bg_image");
//                            CaseEditActivity.stickerView.addStickerMain(drawableSticker);
//                            ((Activity) mContext).finish();
//                            Log.e(TAG, "onResourceReady: ====>1");
//                        } else if (Dynamic_EditActivity.stickerView != null) {
//                            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap));
//                            drawableSticker.setTag("bg_image");
//                            Dynamic_EditActivity.stickerView.addStickerMain(drawableSticker);
//                            ((Activity) mContext).finish();
//                            Log.e(TAG, "onResourceReady: ====>2");
//                        } else if (CoffeeMugEditActivity.stickerView != null) {
//                            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap));
//                            drawableSticker.setTag("bg_image");
//                            CoffeeMugEditActivity.stickerView.addStickerMain(drawableSticker);
//                            ((Activity) mContext).finish();
//                        } else if (Custom_CaseEditActivity.stickerView != null) {
//                            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap));
//                            drawableSticker.setTag("bg_image");
//                            Custom_CaseEditActivity.stickerView.addStickerMain(drawableSticker);
//                            ((Activity) mContext).finish();
//                        }
//
//                    }
//                });
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView background;
        public TextView tv_name;


        public MyViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.up);
//            tv_name = view.findViewById(R.id.tv_name);

//            options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.progress_animation)
//                    .cacheInMemory(true)
//                    .cacheOnDisk(true)
//                    .bitmapConfig(Bitmap.Config.RGB_565)
//                    .build();
        }
    }

    public class generate_resource extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (bitmap_resource != null) {

            }
            return null;
        }

    }

}
