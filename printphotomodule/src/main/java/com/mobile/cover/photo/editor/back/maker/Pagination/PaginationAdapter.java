package com.mobile.cover.photo.editor.back.maker.Pagination;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.jdrodi.BaseActivity;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pagination.model.Result;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.GlideUtilKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.THUMB_TYPE;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Custom_CaseEditActivity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;
import com.mobile.cover.photo.editor.back.maker.testing_modules.PhotoPickupImageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * Created by Suleiman on 19/10/16.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";
    public Bitmap msk = null, bground = null;
    public Bitmap up_image = null;
    OnItemClickListener onItemClickListner;
    Bitmap new_result, final_result;
    private List<Result> movieResults;
    private Context context;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private boolean isEditable = false;
    private PaginationAdapterCallback mCallback;
    private DisplayImageOptions options;

    private String errorMsg;

    private setOnSubItemClickListener viewItemInterface;


    public PaginationAdapter(Context context, boolean isEditable, setOnSubItemClickListener viewItemInterface) {
        this.context = context;
        this.isEditable = isEditable;
        this.mCallback = (PaginationAdapterCallback) context;
        movieResults = new ArrayList<>();
        this.viewItemInterface = viewItemInterface;

    }

    public List<Result> getMovies() {
        return movieResults;
    }

    public void setMovies(List<Result> movieResults) {
        this.movieResults = movieResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.row_default_image_item, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        holder.setIsRecyclable(false);

        final Result result = movieResults.get(position); // Movie

        switch (getItemViewType(position)) {

            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;


                Log.e("MOBILE_TYPE", "onBindViewHolder: " + Share.mobile_type);
                Log.e("MASKHEIGT", "onBindViewHolder: " + Share.maskheight);


                if (Share.case_category_id == 76) {
//                    Glide.with(context).load(result.getNImage1()).asBitmap().into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            movieVH.background.setImageBitmap(resource);
//                        }
//
//                        @Override
//                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                            super.onLoadFailed(e, errorDrawable);
//                        }
//                    });
                    ImageLoader.getInstance().displayImage(result.getNImage1(), movieVH.background, options);

                } else {
                    movieVH.background.setImageBitmap(bground);
                    movieVH.up.setImageBitmap(up_image);

                    String imageUrl = result.getNThumbImage1();
                    if (Share.mobile_type == 1) {
                        imageUrl = result.getNImage1();
                    } else if (Share.mobile_type == 2) {
                        imageUrl = result.getNImage2();
                    } else {
                        imageUrl = result.getNImage3();
                    }

                    try {
                        GlideUtilKt.loadImageCenterCrop(context, imageUrl, movieVH.background, movieVH.progressBar, THUMB_TYPE.PORTRAIT, up_image.getWidth(), up_image.getHeight());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    /*if (Share.mobile_type == 1) {

                        Glide.with(context).asBitmap().load(result.getNImage1()).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                int width = 0, height = 0;
                                final int IMAGE_MAX_SIZE = 550000;
                                width = resource.getWidth();
                                height = resource.getHeight();

                                double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                                double x = (y / height) * width;
                                Bitmap scaledBitmap;
                                Log.e("MASKHEIGHT", "onResourceReady: " + Share.maskheight + "=======>" + y + "=======>" + x);
                                if (String.valueOf(Share.maskheight).equalsIgnoreCase("8.48")) {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.3), (int) (y * 1.14), true);
                                } else {
                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.3), (int) (y * 1.26), true);
                                }

                                Share.bitmap = resource;
                                Bitmap original = bground;
                                Bitmap mask = scaledBitmap;

                                new_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                Canvas mCanvas = new Canvas(new_result);
                                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                mCanvas.drawBitmap(original, 0, 0, null);
                                mCanvas.drawBitmap(mask, 0, 0, paint);
                                paint.setXfermode(null);
                                movieVH.background.setImageBitmap(new_result);


                            }


                        });
                    } else if (Share.mobile_type == 2) {
                        Glide.with(context).asBitmap()
                                .load(result.getNImage2())
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                        int width = 0, height = 0;
                                        final int IMAGE_MAX_SIZE = 550000;
                                        width = resource.getWidth();
                                        height = resource.getHeight();

                                        Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========>" + Share.maskheight);

                                        double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                                        double x = (y / height) * width;
                                        Log.e("MASK", "onResourceReady: " + "========>" + x + "========>" + y);
                                        Bitmap scaledBitmap;
                                        if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.02")) {
                                            Log.e("YES", "onResourceReady: =========++>");
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.8), (int) (y * 1.8), true);
                                        } else {
                                            scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.3), (int) (y * 1.275), true);
                                        }
                                        Bitmap original = bground;
                                        Bitmap mask = scaledBitmap;
                                        new_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                        Canvas mCanvas = new Canvas(new_result);
                                        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                        mCanvas.drawBitmap(original, 0, 0, null);
                                        mCanvas.drawBitmap(mask, 0, 0, paint);
                                        paint.setXfermode(null);
                                        movieVH.background.setImageBitmap(new_result);

                                    }


                                });
                    } else {
                        Glide.with(context).asBitmap()
                                .load(result.getNImage3())
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                        int width = 0, height = 0;
                                        final int IMAGE_MAX_SIZE = 550000;
                                        width = resource.getWidth();
                                        height = resource.getHeight();

                                        Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========11=>" + Share.maskheight);

                                        double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                                        double x = (y / height) * width;
                                        Bitmap scaledBitmap;
                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.3), (int) (y * 1.28), true);
                                        Bitmap original = bground;
                                        Bitmap mask = scaledBitmap;
                                        new_result = Bitmap.createBitmap(up_image.getWidth(), up_image.getHeight(), Bitmap.Config.ARGB_8888);
                                        Canvas mCanvas = new Canvas(new_result);
                                        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                        mCanvas.drawBitmap(original, 0, 0, null);
                                        mCanvas.drawBitmap(mask, 0, 0, paint);
                                        paint.setXfermode(null);
                                        movieVH.background.setImageBitmap(new_result);

                                    }

                                });
                    }*/
                }


                movieVH.background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkAndRequestPermissionsStorage(2)) {

                            if (Share.case_category_id == 76) {

                                DataHelperKt.saveCaseId(context, result.getId());

                                Log.e("CASEID", "onClick: " + result.getId());
                                if (result.getId() == 1261) {
                                    Share.itemnum = 5;
                                } else if (result.getId() == 1263) {
                                    Share.itemnum = 8;
                                } else if (result.getId() == 1260) {
                                    Share.itemnum = 7;
                                } else if (result.getId() == 1262) {
                                    Share.itemnum = 10;
                                } else if (result.getId() == 1265) {
                                    Share.itemnum = 5;
                                } else if (result.getId() == 1264) {
                                    Share.itemnum = 14;
                                } else if (result.getId() == 1259) {
                                    Share.itemnum = 4;
                                } else if (result.getId() == 1521) {
                                    Share.itemnum = 3;
                                } else if (result.getId() == 1522) {
                                    Share.itemnum = 3;
                                } else if (result.getId() == 1256) {
                                    Share.itemnum = 4;
                                } else if (result.getId() == 1523) {
                                    Share.itemnum = 2;
                                } else if (result.getId() == 1254) {
                                    Share.itemnum = 6;
                                } else if (result.getId() == 1526) {
                                    Share.itemnum = 4;
                                } else if (result.getId() == 1525) {
                                    Share.itemnum = 3;
                                } else if (result.getId() == 1524) {
                                    Share.itemnum = 3;
                                } else if (result.getId() == 1527) {
                                    Share.itemnum = 3;
                                } else if (result.getId() == 1528) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1529) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1530) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1520) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1519) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1531) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1532) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1533) {
                                    Share.itemnum = 2;
                                } else if (result.getId() == 1534) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1535) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1536) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1537) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1538) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1539) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1540) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1541) {
                                    Share.itemnum = 1;
                                } else if (result.getId() == 1542) {
                                    Share.itemnum = 2;
                                } else if (result.getId() == 1543) {
                                    Share.itemnum = 2;
                                }


                                final ProgressDialog pd = ProgressDialog.show(context, "", context.getResources().getString(R.string.loading), true, false);
                                Glide.with(context).asBitmap().load(result.getNImage1()).into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        pd.dismiss();
                                        movieVH.background.setImageBitmap(resource);
                                        Share.case_fancy_image = resource;
                                        Share.case_fancy_image_heigth = resource.getHeight();
                                        Share.case_fancy_image_width = resource.getWidth();
                                        Share.case_id = String.valueOf(result.getId());
                                        if (Share.case_fancy_image != null) {
                                            model_details_data modelData = DataHelperKt.getModelData(context);
                                            Log.e("CHECKGALLERY", "onResourceReady: gallery--1");
                                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                            intent.setType("image/*");
                                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

//                                            Intent intent = new Intent(context, PhotoPickupImageActivity.class);
                                            intent.putExtra("from", "" + 1);
                                            intent.putExtra("position", "" + position);
                                            intent.putExtra("model_name", modelData.getModalName());
                                            intent.putExtra("model_id", "" + modelData.getModelId());
                                            intent.putExtra("user_id", SharedPrefs.getString(context, Share.key_ + RegReq.id));
                                            intent.putExtra("quantity", "1");
                                            intent.putExtra("total_amount", modelData.getPrice());
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
                                            if (viewItemInterface != null) {
                                                Toast.makeText(context, "Select " + Share.itemnum + " image/images", Toast.LENGTH_LONG).show();
                                                viewItemInterface.itemClick(intent, Share.itemnum);
                                            }
//                                             context.startActivity(intent);
                                        }
                                    }


                                });
                            } else {
                                if (Share.mobile_type == 1) {
                                    Share.imagetype = result.getNImage1();
                                    Share.case_id = String.valueOf(result.getId());
                                    Glide.with(context).asBitmap()
                                            .load(Share.imagetype)
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                                    int width = 0, height = 0;
                                                    final int IMAGE_MAX_SIZE = 550000;
                                                    width = resource.getWidth();
                                                    height = resource.getHeight();

                                                    Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========1122=>" + Share.maskheight);

                                                    double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                                                    double x = (y / height) * width;
                                                    Bitmap scaledBitmap;
                                                    if (String.valueOf(Share.maskheight).equalsIgnoreCase("8.48")) {
                                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.3), (int) (y * 1.14), true);
                                                    } else {
                                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.3), (int) (y * 1.26), true);
                                                    }

                                                    Log.e("MASKHEIGHT", "onResourceReady: " + Share.maskheight + "=======>" + y + "=======>" + x);
                                                    Bitmap original = bground;
                                                    Bitmap mask = scaledBitmap;
                                                    new_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                                    final_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                                    Canvas mCanvas = new Canvas(new_result);
                                                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                                                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                                    mCanvas.drawBitmap(original, 0, 0, null);
                                                    mCanvas.drawBitmap(mask, 0, 0, paint);
                                                    paint.setXfermode(null);
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
                                                        Share.resultbitmap = new_result;
                                                        Share.final_result_bitmap = final_result;
                                                    } else {

                                                        Share.resultbitmap = new_result;
                                                        Share.final_result_bitmap = final_result;
                                                    }
//                                    if (position == 0) {
//                                        customcasediting();
//                                    } else {
                                                    nextactivity();
//                                    }
                                                }

                                            });

                                } else if (Share.mobile_type == 2) {
                                    Share.imagetype = result.getNImage2();
                                    Share.case_id = String.valueOf(result.getId());
                                    Glide.with(context).asBitmap()
                                            .load(Share.imagetype)
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                                    int width = 0, height = 0;
                                                    final int IMAGE_MAX_SIZE = 550000;
                                                    width = resource.getWidth();
                                                    height = resource.getHeight();

                                                    Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========>" + Share.maskheight);

                                                    double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                                                    double x = (y / height) * width;
                                                    Bitmap scaledBitmap;
                                                    if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.02")) {
                                                        Log.e("YES", "onResourceReady: =========++>");
                                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 2.1), (int) (y * 2.1), true);
                                                    } else {
                                                        scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.3), (int) (y * 1.275), true);
                                                    }

                                                    Bitmap original = bground;
                                                    Bitmap mask = scaledBitmap;
                                                    new_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                                    final_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);

                                                    Canvas mCanvas = new Canvas(new_result);
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
                                                        Share.resultbitmap = new_result;
                                                        Share.final_result_bitmap = final_result;
                                                    } else {

                                                        Share.resultbitmap = new_result;
                                                        Share.final_result_bitmap = final_result;
                                                    }
//                                    if (position == 0) {
//                                        customcasediting();
//                                    } else {
                                                    nextactivity();
//                                    }
                                                }

                                            });

                                } else {
                                    Share.imagetype = result.getNImage3();
                                    Share.case_id = String.valueOf(result.getId());
                                    Glide.with(context).asBitmap()
                                            .load(Share.imagetype)
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                                    int width = 0, height = 0;
                                                    final int IMAGE_MAX_SIZE = 550000;
                                                    width = resource.getWidth();
                                                    height = resource.getHeight();

                                                    Log.e("TAG", "1th scale operation dimenions - width: " + width + ", height: " + height + "=========MASKHEIGHT========11=>" + Share.maskheight);


                                                    double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                                                    double x = (y / height) * width;
                                                    Bitmap scaledBitmap;
                                                    scaledBitmap = Bitmap.createScaledBitmap(resource, (int) (x * 1.3), (int) (y * 1.28), true);


                                                    Bitmap original = bground;
                                                    Bitmap mask = scaledBitmap;
                                                    new_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
                                                    final_result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);

                                                    // TODO: 26/12/18 Hathi key dant dikhaney key alag
                                                    Canvas mCanvas = new Canvas(new_result);
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
                                                        Share.resultbitmap = new_result;
                                                        Share.final_result_bitmap = final_result;
                                                    } else {

                                                        Share.resultbitmap = new_result;
                                                        Share.final_result_bitmap = final_result;
                                                    }
//                                    if (position == 0) {
//                                        customcasediting();
//                                    } else {
                                                    nextactivity();
//                                    }
                                                }
                                            });

                                }
                            }
                        } else {
                            onItemClickListner.onItemClickLister(v, position);
                        }
                    }


                });
                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);
                }
                break;
        }
    }


    private boolean checkAndRequestPermissionsStorage(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, code);
                return false;
            } else {
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, code);
                return false;
            } else {
                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {
                return true;
            }
        }

    }


    private void nextactivity() {

        model_details_data modelData = DataHelperKt.getModelData(context);

        Log.e("DETAILS", "customcasediting: " + modelData.getModalName());
        Log.e("DETAILS", "customcasediting: " + modelData.getModelId());
        Log.e("DETAILS", "customcasediting: " + modelData.getPrice());
        Log.e("DETAILS", "customcasediting: " + modelData.getModelId());
        Log.e("DETAILS", "customcasediting: " + modelData.getWidth());
        Log.e("DETAILS", "customcasediting: " + modelData.getHeight());
        Intent intent = new Intent(context, Custom_CaseEditActivity.class);

        intent.putExtra("model_name", modelData.getModalName());
        intent.putExtra("model_id", "" + modelData.getModelId());
        intent.putExtra("user_id", SharedPrefs.getString(context, Share.key_ + RegReq.id));
        intent.putExtra("quantity", "1");
        intent.putExtra("total_amount", modelData.getPrice());
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
        intent.putExtra("isEditable", false);
        context.startActivity(intent);
        System.gc();
    }

    @Override
    public int getItemCount() {
        return movieResults == null ? 0 : movieResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Result r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(List<Result> moveResults) {
        for (Result result : moveResults) {
            add(result);
        }
    }

    public void remove(Result r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Result());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        Result result = getItem(position);

        if (result != null) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Result getItem(int position) {
        return movieResults.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(movieResults.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListner = onItemClickListener;
    }


    public interface setOnSubItemClickListener {
        void itemClick(Intent intent, int itemnum);
    }

    protected class MovieVH extends RecyclerView.ViewHolder {
        public ImageView background, up;
        public TextView tv_name;
        public LinearLayout rl_layout;
        public ProgressBar progressBar;

        public MovieVH(View view) {
            super(view);

            up = view.findViewById(R.id.up);
            background = view.findViewById(R.id.background);
            tv_name = view.findViewById(R.id.tv_name);
            rl_layout = view.findViewById(R.id.rl_layout);
            progressBar = view.findViewById(R.id.progressBar);
            if (Share.bitmapHashMap != null) {
                bground = Share.bitmapHashMap.get(Share.key_msk_imge);
                up_image = Share.bitmapHashMap.get(Share.key_normal_image_new);
            }

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.loadmore_retry || id == R.id.loadmore_errorlayout) {
                showRetry(false, null);
                mCallback.retryPageLoad();
            }
        }
    }

}
