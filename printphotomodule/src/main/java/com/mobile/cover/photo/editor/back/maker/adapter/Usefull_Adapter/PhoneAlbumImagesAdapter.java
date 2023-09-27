package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.AlbumImagesActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.CoffeeMugEditActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FaceActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FacebookAlbumPhotoActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 11/18/2016.
 */
public class PhoneAlbumImagesAdapter extends RecyclerView.Adapter<PhoneAlbumImagesAdapter.ViewHolder> {

    private static final long MIN_CLICK_INTERVAL = 1500;
    ProgressDialog pd;
    private Activity activity;
    private List<String> al_album = new ArrayList<>();
    private DisplayImageOptions options;
    private long mLastClickTime;

    public PhoneAlbumImagesAdapter(Activity activity, ArrayList<String> al_album) {
        this.activity = activity;
        this.al_album = al_album;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.progress_animation)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public void onBindViewHolder(PhoneAlbumImagesAdapter.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.tv_album_name.setVisibility(View.GONE);

        ImageLoader.getInstance().displayImage("file:///" + al_album.get(position), holder.iv_album_image, options);
        holder.iv_album_image.getLayoutParams().height = SharedPrefs.getInt(activity, SharedPrefs.SCREEN_HEIGHT) / 5;

        holder.itemView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {


                try{
                    if (pd != null) {
                        pd.dismiss();
                    }
                    pd = ProgressDialog.show(activity, "", activity.getResources().getString(R.string.loading), true, false);


                    GlobalData.imageUrl = al_album.get(position);
                    if (GlobalData.isFromHomeForChange)
                        GlobalData.isFromHomeAgain = true;
                    File file = new File(al_album.get(position));
                    int mb = 1024 * 1024;
                    float length = file.length() / mb;

//                    if (!getResolution6x6(al_album.get(position))) {
//                        pd.dismiss();
//                        Toast.makeText(activity, activity.getResources().getString(R.string.select_high_resolution_image), Toast.LENGTH_SHORT).show();
//                    } else {

                        try {
                            pd.dismiss();
                            Share.bitmap = makeBitmap(file.getAbsolutePath(), file.length());
                            if (AlbumImagesActivity.Companion.getActivity() != null)
                                AlbumImagesActivity.Companion.getActivity().finish();

                            if (FacebookAlbumPhotoActivity.activity != null)
                                FacebookAlbumPhotoActivity.activity.finish();

                            if (FaceActivity.getFaceActivity() != null)
                                FaceActivity.getFaceActivity().finish();

                            activity.finish();
                            System.gc();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Glide.with(activity).asBitmap()
                                    .load(al_album.get(position))
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                            pd.dismiss();
                                            Share.bitmap = resource;

                                            if (AlbumImagesActivity.Companion.getActivity() != null)
                                                AlbumImagesActivity.Companion.getActivity().finish();

                                            if (FacebookAlbumPhotoActivity.activity != null)
                                                FacebookAlbumPhotoActivity.activity.finish();

                                            if (FaceActivity.getFaceActivity() != null)
                                                FaceActivity.getFaceActivity().finish();

                                            activity.finish();
                                            System.gc();
                                        }

                                    });
                        }

//                    }
                }catch (Exception e){
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean getResolution6x6(String pickedImagePath) {
        boolean isImageWidthInResolution = false;
        boolean isImageHeightInResolution = false;

        BitmapFactory.Options bitMapOption = new BitmapFactory.Options();
        bitMapOption.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pickedImagePath, bitMapOption);
        int imageWidth = bitMapOption.outWidth;
        int imageHeight = bitMapOption.outHeight;

        isImageWidthInResolution = imageWidth >= 720;

        isImageHeightInResolution = imageHeight >= 720;

        return isImageWidthInResolution && isImageHeightInResolution;
    }


    @Override
    public PhoneAlbumImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone_photo, parent, false);
        return new ViewHolder(view);
    }

    private Bitmap makeBitmap(String path, long length) {

        try {
            final int IMAGE_MAX_SIZE = (int) length; // 1.2MP
//resource = getResources();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            int scale = 1;
            while ((options.outWidth * options.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.e("TAG", "scale = " + scale + ", orig-width: " + options.outWidth + ", orig-height: " + options.outHeight);

            Bitmap pic = null;
            if (scale > 1) {
                int width = 0, height = 0;
                scale--;
                options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                pic = BitmapFactory.decodeFile(path, options);

                Display display = activity.getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
//                if (Share.maskwidth>Share.maskheight){
//                    width = 3;
//                    height = (int) 2.5;
//                }
//                else {
//                     width = (int) 2.5;
//                     height = (int) 3;
//                }
                width = pic.getWidth();
                height = pic.getHeight();

//int height = imageView.getHeight();
//int width = imageView.getWidth();
                Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(pic, (int) x, (int) y, true);
                pic.recycle();
                pic = scaledBitmap;

                System.gc();
            } else {
                pic = BitmapFactory.decodeFile(path);
            }


            Log.e("TAG", "bitmap size - width: " + pic.getWidth() + ", height: " + pic.getHeight());
            return pic;

        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return al_album.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_album_name;
        ImageView iv_album_image;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_album_name = itemView.findViewById(R.id.tv_album_name);
            iv_album_image = itemView.findViewById(R.id.iv_album_image);
        }
    }
}
