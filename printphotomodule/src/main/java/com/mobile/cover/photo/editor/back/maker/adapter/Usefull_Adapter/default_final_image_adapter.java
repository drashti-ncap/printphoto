package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.CaseEditActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Custom_CaseEditActivity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;
import com.mobile.cover.photo.editor.back.maker.model.temp_get_images;

import java.util.List;


public class default_final_image_adapter extends RecyclerView.Adapter<default_final_image_adapter.MyViewHolder> {

    public Bitmap msk = null, bground = null, up_image = null;
    Bitmap result, final_result;
    private List<temp_get_images> sqlitemodelList;
    private Activity mContext;
    private List<temp_get_images> sqlitemodelListFiltered_images;


    public default_final_image_adapter(Activity mContext, List<temp_get_images> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
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
        final temp_get_images sqlitemodel = sqlitemodelList.get(position);

        holder.background.setImageBitmap(bground);
        holder.up.setImageBitmap(up_image);
        Log.e("MOBILE_TYPE", "onBindViewHolder: " + Share.mobile_type);
        Log.e("MASKHEIGT", "onBindViewHolder: " + Share.maskheight);
        if (Share.mobile_type == 1) {
            Glide.with(mContext).asBitmap()
                    .load("https://printphoto.in/Photo_case/public/special_case/" + sqlitemodel.getImage1())
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
                            Bitmap original = bground;
                            Bitmap mask = scaledBitmap;
                            result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                            Canvas mCanvas = new Canvas(result);
                            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                            mCanvas.drawBitmap(original, 0, 0, null);
                            mCanvas.drawBitmap(mask, 0, 0, paint);
                            paint.setXfermode(null);
                            holder.background.setImageBitmap(result);
                        }


                    });
        } else if (Share.mobile_type == 2) {
            Glide.with(mContext).asBitmap()
                    .load("https://printphoto.in/Photo_case/public/special_case/" + sqlitemodel.getImage2())
                    .into(new SimpleTarget<Bitmap>() {

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                            int width = 0, height = 0;
                            final int IMAGE_MAX_SIZE = 1200000;
                            width = resource.getWidth();
                            height = resource.getHeight();

                            Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========>" + Share.maskheight);

                            double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                            double x = (y / height) * width;
                            Log.e("MASK", "onResourceReady: " + "========>" + x + "========>" + y);
                            Bitmap scaledBitmap;
                            if (Share.maskheight <= 6) {
                                Log.e("MASK", "onResourceReady: ==>1");
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.2), true);
                            } else if (Share.maskheight <= 6.5) {
                                Log.e("MASK", "onResourceReady: ==>2");
                                if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.45")) {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.12), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.27")) {
                                    Log.e("HTC", "onResourceReady: ");
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.41), (int) (y * 1.45), true);
                                } else {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.190), (int) (y / 1.12), true);
                                }
                            } else if (Share.maskheight <= 6.69) {
                                if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.6")) {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.19), (int) (y / 1.12), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.59")) {
                                    Log.e("NOTE3", "onResourceReady: ");
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.125), true);
                                } else {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.12), true);
                                }
                            } else if (Share.maskheight <= 6.81) {
                                Log.e("MASK", "onResourceReady: ==>3");
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                            } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.92")) {
                                Log.e("MASK", "onResourceReady: ==>3");
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                            } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("7.07")) {
                                Log.e("MASK", "onResourceReady: ==>5");
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.1), true);
                            } else if (Share.maskheight == 6.98) {
                                Log.e("MASK", "onResourceReady: ==>5");
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.2), true);
                            } else {
                                Log.e("MASK", "onResourceReady: ==>6");
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.175), (int) (y / 1.2), true);
                            }

//                    Share.bitmap=resource;
                            Bitmap original = bground;
                            Bitmap mask = scaledBitmap;
                            result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                            Canvas mCanvas = new Canvas(result);
                            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                            mCanvas.drawBitmap(original, 0, 0, null);
                            mCanvas.drawBitmap(mask, 0, 0, paint);
                            paint.setXfermode(null);
                            holder.background.setImageBitmap(result);

                        }

                    });
        } else {
            Glide.with(mContext).asBitmap()
                    .load("https://printphoto.in/Photo_case/public/special_case/" + sqlitemodel.getImage3())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                            int width = 0, height = 0;
                            final int IMAGE_MAX_SIZE = 1200000;
                            width = resource.getWidth();
                            height = resource.getHeight();

                            Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========11=>" + Share.maskheight);


                            double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                            double x = (y / height) * width;
                            Bitmap scaledBitmap;
                            if (Share.maskheight <= 6) {
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.2), true);
                            } else if (Share.maskheight <= 6.5) {
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.12), true);
                            } else if (Share.maskheight <= 6.8) {
                                if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.52")) {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.175), (int) (y / 1.185), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.74")) {
                                    Log.e("XL", "onResourceReady: ===>6.74");
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.18), (int) (y / 1.17), true);
                                } else {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                                }
                            } else if (Share.maskheight <= 6.82) {
                                scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.18), (int) (y / 1.17), true);
                            } else if (Share.maskheight >= 6.85) {
                                if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.92")) {
                                    Log.e("XL", "onResourceReady: ");
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.185), (int) (y / 1.05), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.91")) {
                                    Log.e("XL", "onResourceReady: ");
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.185), (int) (y / 1.15), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.93")) {
                                    Log.e("XXL", "onResourceReady: ");
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.97")) {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.1), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.88")) {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.15), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.86")) {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.15), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("7.02")) {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.22), true);
                                } else {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1), true);
                                }
                            } else {
                                if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.92")) {
                                    Log.e("XL", "onResourceReady: ");
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.19), (int) (y / 1.19), true);
                                } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.93")) {
                                    Log.e("XXL", "onResourceReady: ");
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.35), true);
                                } else {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                                }
                            }

                            Bitmap original = bground;
                            Bitmap mask = scaledBitmap;
                            result = Bitmap.createBitmap(up_image.getWidth(), up_image.getHeight(), Bitmap.Config.ARGB_8888);
                            Canvas mCanvas = new Canvas(result);
                            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                            mCanvas.drawBitmap(original, 0, 0, null);
                            mCanvas.drawBitmap(mask, 0, 0, paint);
                            paint.setXfermode(null);
                            holder.background.setImageBitmap(result);

                        }

                    });
        }
        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Share.mobile_type == 1) {
                    Share.imagetype = "https://printphoto.in/Photo_case/public/special_case/" + sqlitemodel.getImage1();
                    Glide.with(mContext).asBitmap()
                            .load(Share.imagetype)
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

                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.165), (int) (y / 1.05), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.22")) {
                                            Log.e("TAG", "onResourceReady: 211111+++");

                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.185), (int) (y), true);
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
                                    Bitmap original = bground;
                                    Bitmap mask = scaledBitmap;
                                    result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                    final_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                    Canvas mCanvas = new Canvas(result);
                                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                    mCanvas.drawBitmap(original, 0, 0, null);
                                    mCanvas.drawBitmap(mask, 0, 0, paint);
                                    paint.setXfermode(null);


//                            final_result = Bitmap.createBitmap((int) (Float.parseFloat(subDataModelDetails.getWidth())*198), (int) (Float.parseFloat(subDataModelDetails.getHeight())*198), Bitmap.Config.ARGB_8888);
                                    Canvas mCanvas1 = new Canvas(final_result);
                                    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
                                    paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                                    mCanvas1.drawBitmap(final_result, 0, 0, null);
                                    mCanvas1.drawBitmap(mask, 0, 0, paint);
                                    paint1.setXfermode(null);
//                            holder.background.setImageBitmap(result);
                                    if (Share.resultbitmap != null && !Share.resultbitmap.isRecycled()) {
//                                Share.resultbitmap.recycle();
                                        Share.resultbitmap = null;
                                        Share.final_result_bitmap = null;
                                        Share.resultbitmap = result;
                                        Share.final_result_bitmap = final_result;
                                    } else {

                                        Share.resultbitmap = result;
                                        Share.final_result_bitmap = final_result;
                                    }
                                    if (position == 0) {
                                        customcasediting();
                                    } else {
                                        nextactivity();
                                    }
                                }

                            });

                } else if (Share.mobile_type == 2) {
                    Share.imagetype = "https://printphoto.in/Photo_case/public/special_case/" + sqlitemodel.getImage2();

                    Glide.with(mContext).asBitmap()
                            .load(Share.imagetype)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                                    int width = 0, height = 0;
                                    final int IMAGE_MAX_SIZE = 1200000;
                                    width = resource.getWidth();
                                    height = resource.getHeight();

                                    Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========>" + Share.maskheight);

                                    double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                                    double x = (y / height) * width;
                                    Bitmap scaledBitmap;
                                    if (Share.maskheight <= 6) {
                                        Log.e("MASK", "onResourceReady: ==>1");
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.2), true);
                                    } else if (Share.maskheight <= 6.5) {
                                        Log.e("MASK", "onResourceReady: ==>2");
                                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.45")) {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.12), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.27")) {
                                            Log.e("HTC", "onResourceReady: ");
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.41), (int) (y * 1.45), true);
                                        } else {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.190), (int) (y / 1.12), true);
                                        }
                                    } else if (Share.maskheight <= 6.69) {
                                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.6")) {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.19), (int) (y / 1.12), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.59")) {
                                            Log.e("NOTE3", "onResourceReady: ");
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.125), true);
                                        } else {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.12), true);
                                        }
                                    } else if (Share.maskheight <= 6.81) {
                                        Log.e("MASK", "onResourceReady: ==>3");
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                                    } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.92")) {
                                        Log.e("MASK", "onResourceReady: ==>3");
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                                    } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("7.07")) {
                                        Log.e("MASK", "onResourceReady: ==>5");
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.1), true);
                                    } else if (Share.maskheight == 6.98) {
                                        Log.e("MASK", "onResourceReady: ==>5");
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.2), true);
                                    } else {
                                        Log.e("MASK", "onResourceReady: ==>6");
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.175), (int) (y / 1.2), true);
                                    }

//                    Share.bitmap=resource;
                                    Bitmap original = bground;
                                    Bitmap mask = scaledBitmap;
                                    result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                    final_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);

                                    Canvas mCanvas = new Canvas(result);
                                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                    mCanvas.drawBitmap(original, 0, 0, null);
                                    mCanvas.drawBitmap(mask, 0, 0, paint);
                                    paint.setXfermode(null);

//                            final_result = Bitmap.createBitmap((int) (Float.parseFloat(subDataModelDetails.getWidth())*198), (int) (Float.parseFloat(subDataModelDetails.getHeight())*198), Bitmap.Config.ARGB_8888);
                                    Canvas mCanvas1 = new Canvas(final_result);
                                    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
                                    paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                                    mCanvas1.drawBitmap(final_result, 0, 0, null);
                                    mCanvas1.drawBitmap(mask, 0, 0, paint);
                                    paint1.setXfermode(null);
//                            holder.background.setImageBitmap(result);
                                    if (Share.resultbitmap != null && !Share.resultbitmap.isRecycled()) {
//                                Share.resultbitmap.recycle();
                                        Share.resultbitmap = null;
                                        Share.final_result_bitmap = null;
                                        Share.resultbitmap = result;
                                        Share.final_result_bitmap = final_result;
                                    } else {

                                        Share.resultbitmap = result;
                                        Share.final_result_bitmap = final_result;
                                    }
                                    if (position == 0) {
                                        customcasediting();
                                    } else {
                                        nextactivity();
                                    }
                                }


                            });

                } else {
                    Share.imagetype = "https://printphoto.in/Photo_case/public/special_case/" + sqlitemodel.getImage3();

                    Glide.with(mContext).asBitmap()
                            .load(Share.imagetype)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                                    int width = 0, height = 0;
                                    final int IMAGE_MAX_SIZE = 1200000;
                                    width = resource.getWidth();
                                    height = resource.getHeight();

                                    Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========11=>" + Share.maskheight);


                                    double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                                    double x = (y / height) * width;
                                    Bitmap scaledBitmap;
                                    if (Share.maskheight <= 6) {
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.2), true);
                                    } else if (Share.maskheight <= 6.5) {
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.12), true);
                                    } else if (Share.maskheight <= 6.8) {
                                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.52")) {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.175), (int) (y / 1.185), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.74")) {
                                            Log.e("XL", "onResourceReady: ===>6.74");
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.18), (int) (y / 1.17), true);
                                        } else {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                                        }
                                    } else if (Share.maskheight <= 6.82) {
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.18), (int) (y / 1.17), true);
                                    } else if (Share.maskheight >= 6.85) {
                                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.92")) {
                                            Log.e("XL", "onResourceReady: ");
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.185), (int) (y / 1.05), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.91")) {
                                            Log.e("XL", "onResourceReady: ");
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.185), (int) (y / 1.15), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.93")) {
                                            Log.e("XXL", "onResourceReady: ");
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.97")) {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.1), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.88")) {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.15), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.86")) {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.15), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("7.02")) {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.2), (int) (y / 1.22), true);
                                        } else {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1), true);
                                        }
                                    } else {
                                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.92")) {
                                            Log.e("XL", "onResourceReady: ");
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.19), (int) (y / 1.19), true);
                                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.93")) {
                                            Log.e("XXL", "onResourceReady: ");
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.35), true);
                                        } else {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x / 1.17), (int) (y / 1.17), true);
                                        }
                                    }

                                    Bitmap original = bground;
                                    Bitmap mask = scaledBitmap;
                                    result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                    final_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);

                                    // TODO: 26/12/18 Hathi key dant dikhaney key alag
                                    Canvas mCanvas = new Canvas(result);
                                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                    mCanvas.drawBitmap(original, 0, 0, null);
                                    mCanvas.drawBitmap(mask, 0, 0, paint);
                                    paint.setXfermode(null);


                                    // TODO: 26/12/18 Aur khaney key alag
                                    Canvas mCanvas1 = new Canvas(final_result);
                                    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
                                    paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                                    mCanvas1.drawBitmap(final_result, 0, 0, null);
                                    mCanvas1.drawBitmap(mask, 0, 0, paint);
                                    paint1.setXfermode(null);

                                    if (Share.resultbitmap != null && !Share.resultbitmap.isRecycled()) {
//                                Share.resultbitmap.recycle();
                                        Share.resultbitmap = null;
                                        Share.final_result_bitmap = null;
                                        Share.resultbitmap = result;
                                        Share.final_result_bitmap = final_result;
                                    } else {

                                        Share.resultbitmap = result;
                                        Share.final_result_bitmap = final_result;
                                    }
                                    if (position == 0) {
                                        customcasediting();
                                    } else {
                                        nextactivity();
                                    }
                                }


                            });

                }


            }


        });
    }

    private void customcasediting() {

        model_details_data modelData = DataHelperKt.getModelData(mContext);

        Intent intent = new Intent(mContext, CaseEditActivity.class);
        Log.e("DETAILS", "customcasediting: " + modelData.getModalName());
        Log.e("DETAILS", "customcasediting: " + modelData.getModelId());
        Log.e("DETAILS", "customcasediting: " + modelData.getPrice());
        Log.e("DETAILS", "customcasediting: " + modelData.getModelId());
        Log.e("DETAILS", "customcasediting: " + modelData.getWidth());
        Log.e("DETAILS", "customcasediting: " + modelData.getHeight());
        intent.putExtra("model_name", modelData.getModalName());
        intent.putExtra("model_id", modelData.getModelId());
        intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
        intent.putExtra("quantity", "1");
        intent.putExtra("total_amount", modelData.getPrice());
        intent.putExtra("model_id", modelData.getModelId());
        intent.putExtra("paid_amount", modelData.getPrice());
        Share.mobile_type = modelData.getImageType();
        Log.e("TAG", "onPostExecute: " + Share.mobile_type);
        if (!modelData.getWidth().equalsIgnoreCase("")) {
            intent.putExtra("width", modelData.getWidth());
            intent.putExtra("height", modelData.getHeight());
            Share.maskheight = Float.parseFloat(modelData.getHeight());
            Share.maskwidth = Float.parseFloat(modelData.getWidth());
            Log.e("width", "==>" + modelData.getWidth());
            Log.e("height", "==>" + modelData.getHeight());
        }

        intent.putExtra("shipping", "0");
        mContext.startActivity(intent);
    }

    private void nextactivity() {

        model_details_data modelData = DataHelperKt.getModelData(mContext);

        Log.e("DETAILS", "customcasediting: " + modelData.getModalName());
        Log.e("DETAILS", "customcasediting: " + modelData.getModelId());
        Log.e("DETAILS", "customcasediting: " + modelData.getPrice());
        Log.e("DETAILS", "customcasediting: " + modelData.getModelId());
        Log.e("DETAILS", "customcasediting: " + modelData.getWidth());
        Log.e("DETAILS", "customcasediting: " + modelData.getHeight());
        Intent intent = new Intent(mContext, Custom_CaseEditActivity.class);

        intent.putExtra("model_name", modelData.getModalName());
        intent.putExtra("model_id", modelData.getModelId());
        intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
        intent.putExtra("quantity", "1");
        intent.putExtra("total_amount", modelData.getPrice());
        intent.putExtra("model_id", modelData.getModelId());
        intent.putExtra("paid_amount", modelData.getPrice());
        Share.mobile_type = modelData.getImageType();
        Log.e("TAG", "onPostExecute: " + Share.mobile_type);
        if (!modelData.getWidth().equalsIgnoreCase("")) {
            intent.putExtra("width", modelData.getWidth());
            intent.putExtra("height", modelData.getHeight());
            Share.maskheight = Float.parseFloat(modelData.getHeight());
            Share.maskwidth = Float.parseFloat(modelData.getWidth());
            Log.e("width", "==>" + modelData.getWidth());
            Log.e("height", "==>" + modelData.getHeight());
        }

        intent.putExtra("shipping", "0");
        intent.putExtra("isEditable", true);
        mContext.startActivity(intent);
        System.gc();
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView background, up;
        public TextView tv_name;
        public RelativeLayout rl_layout;


        public MyViewHolder(View view) {
            super(view);
            up = view.findViewById(R.id.up);
            background = view.findViewById(R.id.background);
            tv_name = view.findViewById(R.id.tv_name);
            rl_layout = view.findViewById(R.id.rl_layout);
            if (Share.bitmapHashMap != null) {
                bground = Share.bitmapHashMap.get(Share.key_msk_imge);
                Log.e("bground", "onCreate: " + Share.bitmapHashMap.get(Share.key_msk_imge));
                Log.e("bground", "onCreate: " + Share.bitmapHashMap.get(Share.key_normal_image));
                up_image = Share.bitmapHashMap.get(Share.key_normal_image);
            }
        }
    }

}
