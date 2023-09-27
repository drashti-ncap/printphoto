package com.mobile.cover.photo.editor.back.maker.adapter.Testing_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Testing.Default_testing_image_activity;
import com.mobile.cover.photo.editor.back.maker.model.get_images;
import com.mobile.cover.photo.editor.back.maker.model.getdefault_images;

import java.util.List;


public class test_new_default_image_adapter extends RecyclerView.Adapter<test_new_default_image_adapter.MyViewHolder> {

    Bitmap bitmap;
    Bitmap new_result, final_result;
    private List<getdefault_images> sqlitemodelList;
    private Context mContext;
    private List<get_images> sqlitemodelListFiltered_images;

    public test_new_default_image_adapter(Context mContext, List<getdefault_images> sqlitemodelList_images) {
        this.sqlitemodelList = sqlitemodelList_images;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_default_image_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final getdefault_images sqlitemodel = sqlitemodelList.get(position);

//        Log.e(TAG, "onBindViewHolder: " + sqlitemodel.getImage1());
        final String image = "https://printphoto.in/Photo_case/public/test_images/" + sqlitemodel.getImg();


        Glide.with(mContext).asBitmap()
                .load(image)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        int width = 0, height = 0;
                        final int IMAGE_MAX_SIZE = 1200000;
                        width = resource.getWidth();
                        height = resource.getHeight();

                        Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========1122=>" + Share.maskheight);

                        double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                        double x = (y / height) * width;
                        Bitmap scaledBitmap;
                        Log.e("MASKHEIGHT", "onResourceReady: " + Share.maskheight + "=======>" + y + "=======>" + x);
                        if (Share.maskheight <= 6) {
                            Log.e("TAG", "onResourceReady: 1");

                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.19), (int) (y / 1.2), true);
                        } else if (Share.maskheight <= 6.5) {
                            Log.e("CHECK", "onResourceReady: " + Share.maskheight + "====>" + (String.valueOf(Share.maskheight).equalsIgnoreCase("6.38")));
                            if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.38")) {
                                Log.e("TAG", "onResourceReady: 2==GIO");

                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.165), (int) (y / 1.12), true);
                            } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.22")) {
                                Log.e("TAG", "onResourceReady: 211111+++");

                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.185), (int) (y / 1), true);
                            } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.25")) {
                                Log.e("TAG", "onResourceReady: 2==GIO");

                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.165), (int) (y / 1.12), true);
                            } else {
                                Log.e("TAG", "onResourceReady: 2+++");

                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.185), (int) (y / 1.2), true);
                            }
                        } else if (Share.maskheight <= 6.69) {
                            if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.68")) {
                                Log.e("Coolpad", "onResourceReady: ");
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.12), true);
                            } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.65")) {
                                Log.e("Oneplus 5", "onResourceReady: ");
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.19), (int) (y / 1.12), true);
                            } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.62")) {
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.195), (int) (y / 1.12), true);
                            } else {
                                Log.e("OTHER", "onResourceReady: ");
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.12), true);
                            }
                        } else if (Share.maskheight <= 6.81) {
                            Log.e("TAG", "onResourceReady: 3");
                            if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.79")) {
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.18), (int) (y / 1.17), true);
                            } else {
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                            }
                        } else if (Share.maskheight == 6.84) {
                            Log.e("TAG", "onResourceReady: 4");
                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.5), (int) (y / 1.15), true);
                        } else {
                            Log.e("TAG", "onResourceReady: 5");

                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.15), true);
                        }


//                    Share.bitmap=resource;
                        Bitmap original = ((BitmapDrawable) mContext.getResources().getDrawable(R.drawable.iphone_mask)).getBitmap();
                        Bitmap mask = scaledBitmap;
                        new_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas mCanvas = new Canvas(new_result);
                        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        mCanvas.drawBitmap(original, 0, 0, null);
                        mCanvas.drawBitmap(mask, 0, 0, paint);
                        paint.setXfermode(null);
                        holder.background.setImageBitmap(new_result);

                        System.gc();

                    }
                });

        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.test_image = image;
                Intent intent = new Intent(mContext, Default_testing_image_activity.class);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }


//        holder.iv_sticker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView background, up;
        public TextView tv_name;


        public MyViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.background);
//            tv_name = view.findViewById(R.id.tv_name);
        }
    }

//    public  void m6676a(ArrayList<msg_listmodel> list) {
//        this.sqlitemodelList = list;
//    }

}
