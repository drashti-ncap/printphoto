package com.mobile.cover.photo.editor.back.maker.adapter.Testing_adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.model.get_images;
import com.mobile.cover.photo.editor.back.maker.model.testing_model_class;

import java.util.List;


public class test_default_image_adapter extends RecyclerView.Adapter<test_default_image_adapter.MyViewHolder> {

    Bitmap result, final_result;
    private List<testing_model_class> sqlitemodelList;
    private Context mContext;
    private List<get_images> sqlitemodelListFiltered_images;


    public test_default_image_adapter(Context mContext, List<testing_model_class> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_default_image_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final testing_model_class sqlitemodel = sqlitemodelList.get(position);
        Log.e("TAG", "onBindViewHolder: " + Share.Mask_file_path + sqlitemodel.getMask_image());

        Glide.with(mContext).asBitmap().load(Share.Mask_file_path + sqlitemodel.getMask_image()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (Share.mobile_type == 1) {
                    holder.tv_model_name.setText(sqlitemodel.getModalName());

                    int width = 0, height = 0;
                    final int IMAGE_MAX_SIZE = 1200000;
                    width = Share.test_bitmap.getWidth();
                    height = Share.test_bitmap.getHeight();

                    Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========1122=>" + Share.maskheight);

                    double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                    double x = (y / height) * width;
                    Bitmap scaledBitmap;
                    Log.e("MASKHEIGHT", "onResourceReady: " + Share.maskheight + "=======>" + y + "=======>" + x);
                    if (Share.maskheight <= 6) {
                        Log.e("TAG", "onResourceReady: 1");

                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.19), (int) (y / 1.2), true);
                    } else if (Share.maskheight <= 6.5) {
                        Log.e("CHECK", "onResourceReady: " + Share.maskheight + "====>" + (String.valueOf(Share.maskheight).equalsIgnoreCase("6.38")));
                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.38")) {
                            Log.e("TAG", "onResourceReady: 2==GIO");

                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.165), (int) (y / 1.12), true);
                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.25")) {
                            Log.e("TAG", "onResourceReady: 2==GIO");

                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.165), (int) (y / 1.12), true);
                        } else {
                            Log.e("TAG", "onResourceReady: 2+++");

                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.185), (int) (y / 1.2), true);
                        }
                    } else if (Share.maskheight <= 6.69) {
                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.68")) {
                            Log.e("Coolpad", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.12), true);
                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.65")) {
                            Log.e("Oneplus 5", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.19), (int) (y / 1.12), true);
                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.62")) {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.195), (int) (y / 1.12), true);
                        } else {
                            Log.e("OTHER", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.2), (int) (y / 1.12), true);
                        }
                    } else if (Share.maskheight <= 6.81) {
                        Log.e("TAG", "onResourceReady: 3");
                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.79")) {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.18), (int) (y / 1.17), true);
                        } else {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.17), true);
                        }
                    } else if (Share.maskheight == 6.84) {
                        Log.e("TAG", "onResourceReady: 4");
                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.5), (int) (y / 1.15), true);
                    } else {
                        Log.e("TAG", "onResourceReady: 5");

                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.2), (int) (y / 1.15), true);
                    }

                    Bitmap original = resource;
                    Bitmap mask = scaledBitmap;
                    result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas mCanvas = new Canvas(result);
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    mCanvas.drawBitmap(original, 0, 0, null);
                    mCanvas.drawBitmap(mask, 0, 0, paint);
                    paint.setXfermode(null);
                    holder.background.setImageBitmap(result);

                } else if (Share.mobile_type == 2) {
                    holder.tv_model_name.setText(sqlitemodel.getModalName());

                    int width = 0, height = 0;
                    final int IMAGE_MAX_SIZE = 1200000;
                    width = Share.test_bitmap.getWidth();
                    height = Share.test_bitmap.getHeight();

                    Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========>" + Share.maskheight);

                    double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                    double x = (y / height) * width;
                    Log.e("MASK", "onResourceReady: " + "========>" + x + "========>" + y);
                    Bitmap scaledBitmap;
                    if (Share.maskheight <= 6) {
                        Log.e("MASK", "onResourceReady: ==>1");
                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.2), (int) (y / 1.2), true);
                    } else if (Share.maskheight <= 6.5) {
                        Log.e("MASK", "onResourceReady: ==>2");
                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.45")) {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.12), true);
                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.27")) {
                            Log.e("HTC", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x * 1.41), (int) (y * 1.45), true);
                        } else {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.190), (int) (y / 1.12), true);
                        }
                    } else if (Share.maskheight <= 6.69) {
                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.6")) {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.19), (int) (y / 1.12), true);
                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.59")) {
                            Log.e("NOTE3", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.125), true);
                        } else {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.2), (int) (y / 1.12), true);
                        }
                    } else if (Share.maskheight <= 6.81) {
                        Log.e("MASK", "onResourceReady: ==>3");
                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.17), true);
                    } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.92")) {
                        Log.e("MASK", "onResourceReady: ==>3");
                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.17), true);
                    } else if (Share.maskheight == 6.98) {
                        Log.e("MASK", "onResourceReady: ==>5");
                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.2), true);
                    } else {
                        Log.e("MASK", "onResourceReady: ==>6");
                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.175), (int) (y / 1.2), true);
                    }
                    Bitmap original = resource;
                    Bitmap mask = scaledBitmap;
                    result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas mCanvas = new Canvas(result);
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    mCanvas.drawBitmap(original, 0, 0, null);
                    mCanvas.drawBitmap(mask, 0, 0, paint);
                    paint.setXfermode(null);
                    holder.background.setImageBitmap(result);


                } else {
                    holder.tv_model_name.setText(sqlitemodel.getModalName());

                    int width = 0, height = 0;
                    final int IMAGE_MAX_SIZE = 1200000;
                    width = Share.test_bitmap.getWidth();
                    height = Share.test_bitmap.getHeight();

                    Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========11=>" + Share.maskheight);


                    double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                    double x = (y / height) * width;
                    Bitmap scaledBitmap;
                    if (Share.maskheight <= 6) {
                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.2), (int) (y / 1.2), true);
                    } else if (Share.maskheight <= 6.5) {
                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.2), (int) (y / 1.12), true);
                    } else if (Share.maskheight <= 6.8) {
                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.52")) {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.175), (int) (y / 1.185), true);
                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.74")) {
                            Log.e("XL", "onResourceReady: ===>6.74");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.18), (int) (y / 1.17), true);
                        } else {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.17), true);
                        }
                    } else if (Share.maskheight <= 6.82) {
                        scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.18), (int) (y / 1.17), true);
                    } else if (Share.maskheight >= 6.85) {
                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.92")) {
                            Log.e("XL", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.185), (int) (y / 1.05), true);
                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.91")) {
                            Log.e("XL", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.185), (int) (y / 1.15), true);
                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.93")) {
                            Log.e("XXL", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.185), true);
                        } else {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1), true);
                        }
                    } else {
                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.92")) {
                            Log.e("XL", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.19), (int) (y / 1.19), true);
                        } else if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.93")) {
                            Log.e("XXL", "onResourceReady: ");
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.35), true);
                        } else {
                            scaledBitmap = Bitmap.createScaledBitmap(Share.test_bitmap, (int) (x / 1.17), (int) (y / 1.17), true);
                        }
                    }

                    Bitmap original = resource;
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
                System.gc();

            }

        });

        Glide.with(mContext).asBitmap().load(Share.Model_file_path + sqlitemodel.getModal_image()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                holder.up.setImageBitmap(resource);
                System.gc();
            }
        });

    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView background, up;
        public TextView tv_model_name;
        public RelativeLayout rl_layout;


        public MyViewHolder(View view) {
            super(view);
            up = view.findViewById(R.id.up);
            background = view.findViewById(R.id.background);
            tv_model_name = view.findViewById(R.id.tv_model_name);
            rl_layout = view.findViewById(R.id.rl_layout);
        }
    }

//    public  void m6676a(ArrayList<msg_listmodel> list) {
//        this.sqlitemodelList = list;
//    }

}
