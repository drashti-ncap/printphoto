package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.background_response_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.GlideUtilKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.THUMB_TYPE;
import com.mobile.cover.photo.editor.back.maker.model.background_rv_images;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class background_image_adapter extends RecyclerView.Adapter<background_image_adapter.MyViewHolder> {

    Bitmap bitmap;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    Bitmap bitmap_resource;
    private List<background_response_data> sqlitemodelList;
    private Context mContext;
    private List<background_rv_images> sqlitemodelListFiltered_images;
    private DisplayImageOptions options;

    public background_image_adapter(Context mContext, List<background_response_data> sqlitemodelList_images) {
        this.sqlitemodelList = sqlitemodelList_images;
        this.mContext = mContext;
    }

    @Override
    public background_image_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_background, parent, false);

        return new background_image_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final background_image_adapter.MyViewHolder holder, final int position) {
        final String image;
        image = sqlitemodelList.get(position).getImage();
        if (sqlitemodelList.get(position).getCategory().getHeight() != null) {
            if (sqlitemodelList.get(position).getCategory().getHeight() == 0 || sqlitemodelList.get(position).getCategory().getWidth() == 0) {
                holder.background.getLayoutParams().height = (int) (Share.screenHeight / 3.45);
                holder.background.getLayoutParams().width = (int) (Share.screenWidth / 3.25);
            } else {
                holder.background.getLayoutParams().height = (int) (Share.screenHeight / sqlitemodelList.get(position).getCategory().getHeight());
                holder.background.getLayoutParams().width = (int) (Share.screenWidth / sqlitemodelList.get(position).getCategory().getWidth());
            }
        } else {
            holder.background.getLayoutParams().height = (int) (Share.screenHeight / 3.45);
            holder.background.getLayoutParams().width = (int) (Share.screenWidth / 3.25);
        }
        ImageLoader.getInstance().displayImage(image, holder.background, options);

        GlideUtilKt.loadImage(mContext, image, holder.background, holder.progressBar, THUMB_TYPE.PORTRAIT);


        holder.background.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                progressDialog = ProgressDialog.show(mContext, "", mContext.getResources().getString(R.string.loading), true, false);
                loadPicture(sqlitemodelList.get(position).getImage(), progressDialog);
            }
        });
    }

    private void loadPicture(final String photoUrl, final ProgressDialog pd) {
        Glide.with(mContext).asBitmap()
                .load(photoUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        pd.dismiss();
                        int width = 0, height = 0;
                        final int IMAGE_MAX_SIZE = 120000;
                        width = resource.getWidth();
                        height = resource.getHeight();

                        Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========11=>" + Share.maskheight);


                        double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                        double x = (y / height) * width;
                        Bitmap scaledBitmap1;
                        scaledBitmap1 = Bitmap.createScaledBitmap(resource, (int) (x * 2), (int) (y * 2), true);

//                if (CaseEditActivity.stickerView != null) {
//                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap1));
//                    drawableSticker.setTag("bg_image");
//                    CaseEditActivity.stickerView.addStickerMain_bg(drawableSticker, 0);
//                    ((Activity) mContext).finish();
//                    Log.e("TAG", "onResourceReady: ====>1");
//                } else if (Dynamic_EditActivity.stickerView != null) {
//                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap1));
//                    drawableSticker.setTag("bg_image");
//                    Dynamic_EditActivity.stickerView.addStickerMain_bg(drawableSticker, 0);
//                    ((Activity) mContext).finish();
//                    Log.e("TAG", "onResourceReady: ====>2");
//                } else if (CoffeeMugEditActivity.stickerView != null) {
//                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap1));
//                    drawableSticker.setTag("bg_image");
//                    CoffeeMugEditActivity.stickerView.addStickerMain_bg(drawableSticker, 0);
//                    ((Activity) mContext).finish();
//                } else if (Custom_CaseEditActivity.stickerView != null) {
//                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap1));
//                    drawableSticker.setTag("bg_image");
//                    Custom_CaseEditActivity.stickerView.addStickerMain_bg(drawableSticker, 0);
//                    ((Activity) mContext).finish();
//                } else if (Tshirt_Editing_Activity.stickerView != null) {
//                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap1));
//                    drawableSticker.setTag("bg_image");
//                    if (Share.back_print == 1) {
//                        Tshirt_Editing_Activity.id__back_stickerview.addStickerMain_bg(drawableSticker, 0);
//                    } else {
//                        Tshirt_Editing_Activity.stickerView.addStickerMain_bg(drawableSticker, 0);
//                    }
//                    ((Activity) mContext).finish();
//                } else if (frame_Dynamic_EditActivity.stickerView != null) {
////                    Log.e("STICKERVIEW", "onResourceReady: "+frame_Dynamic_EditActivity.stickerView);
////                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap1));
////                    drawableSticker.setTag("bg_image");
////                    frame_Dynamic_EditActivity.stickerView.addStickerMain_bg(drawableSticker,0);
                        Share.bg_drawable = scaledBitmap1;
                        ((Activity) mContext).finish();
//                } else if (com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Custom_CaseEditActivity.stickerView != null) {
//                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap1));
//                    drawableSticker.setTag("bg_image");
//                    com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Custom_CaseEditActivity.stickerView.addStickerMain_bg(drawableSticker, 0);
//                    ((Activity) mContext).finish();
//                } else if (testing_CaseEditActivity.stickerView != null) {
//                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(scaledBitmap1));
//                    drawableSticker.setTag("bg_image");
//                    testing_CaseEditActivity.stickerView.addStickerMain_bg(drawableSticker, 0);
//                    ((Activity) mContext).finish();
//                }

                    }

                    @Override
                    public void onLoadFailed(Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        pd.dismiss();
                        if (alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setTitle(mContext.getResources().getString(R.string.internet_connection));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(mContext.getResources().getString(R.string.slow_connect));
                        alertDialog.setButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView background;
        public ProgressBar progressBar;


        public MyViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.up);
            progressBar = view.findViewById(R.id.progressBar);
//            tv_name = view.findViewById(R.id.tv_name);

            options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
    }

}
