package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.mobile.cover.photo.editor.back.maker.Commen.NetworkManager;
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.ProductType;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ModelListActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.sipper.ShipperCategoryActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.GlideUtilKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.THUMB_TYPE;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Dynamic_EditActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Mug_images_category_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.jewellery_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.test_frame_editing_activity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ModelListActivity.activity;


public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.MyViewHolder> {

    private static final long MIN_CLICK_INTERVAL = 1000;
    ProgressDialog progressDialog;
    ArrayList<Uri> image_list_uri = new ArrayList<>();
    private List<model_details_data> sqlitemodelList;
    private Context mContext;
    private List<model_details_data> sqlitemodelListFiltered;
    private String[] str = new String[2];
    private long mLastClickTime;

    public ModelAdapter(Context mContext, List<model_details_data> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
        this.sqlitemodelListFiltered = sqlitemodelList;
    }

    public static void slideUpDown(final View view, Context context, final MyViewHolder holder) {
        if (view == holder.iv_arrow_mug) {
            if (isViewShown(view)) {
                view.setVisibility(View.VISIBLE);
                Animation bottomUp = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
                view.startAnimation(bottomUp);
                bottomUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        holder.iv_arrow_mug_rev.setVisibility(View.VISIBLE);
                        holder.iv_arrow_mug.setVisibility(View.INVISIBLE);
                        holder.tv_description.setVisibility(View.VISIBLE);
                        holder.rl_description.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                Animation bottomDown = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
                view.startAnimation(bottomDown);
                bottomDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        holder.iv_arrow_mug_rev.setVisibility(View.INVISIBLE);
                        holder.iv_arrow_mug.setVisibility(View.VISIBLE);
                        holder.tv_description.setVisibility(View.GONE);
                        holder.rl_description.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        } else {
            if (!isViewShown(view)) {
                view.setVisibility(View.VISIBLE);
                Animation bottomUp = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
                view.startAnimation(bottomUp);
                bottomUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        holder.iv_arrow_mug_rev.setVisibility(View.VISIBLE);
                        holder.iv_arrow_mug.setVisibility(View.INVISIBLE);
                        holder.tv_description.setVisibility(View.VISIBLE);
                        holder.rl_description.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                Animation bottomDown = AnimationUtils.loadAnimation(context, R.anim.bottom_down);
                view.startAnimation(bottomDown);
                bottomDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        holder.iv_arrow_mug_rev.setVisibility(View.INVISIBLE);
                        holder.iv_arrow_mug.setVisibility(View.VISIBLE);
                        holder.tv_description.setVisibility(View.GONE);
                        holder.rl_description.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }

    }

    public static boolean isViewShown(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final model_details_data sqlitemodel = sqlitemodelList.get(position);
        holder.setIsRecyclable(false);
        holder.name.setText(sqlitemodel.getModalName());
        Log.e("AR", "onBindViewHolder: " + Locale.getDefault().getLanguage());
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar")) {
            holder.info.setText(mContext.getString(R.string.size_val) + sqlitemodel.getWidth() + " * " + sqlitemodel.getHeight() + mContext.getString(R.string.inch_val));
        } else {
            holder.info.setText(mContext.getString(R.string.size_val) + sqlitemodel.getWidth() + " X " + sqlitemodel.getHeight() + mContext.getString(R.string.inch_val));
        }
        holder.rate.setText(Share.symbol + sqlitemodel.getPrice());
        holder.id_dummy_price.setText(Share.symbol + sqlitemodel.getDummyPrice());

        String imgPath = sqlitemodel.getInternational_n_mug_image();
        if (SharedPrefs.getString(mContext, SharedPrefs.country_code).equalsIgnoreCase("IN")) {
            imgPath = sqlitemodel.getN_mug_image();
        }

        GlideUtilKt.loadImage(mContext, imgPath, holder.iv_mug, holder.progressBar, THUMB_TYPE.LANDSCAPE);


        holder.tv_description.setText("" + sqlitemodel.getDescription());


        holder.name.setClickable(true);
        holder.rate.setClickable(true);
        holder.btn_select.setClickable(true);
        holder.iv_mug.setClickable(true);
        holder.info.setClickable(true);


        holder.iv_share_pro.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                int i = 0;
                image_list_uri.clear();
                generate_uri(position);
            }
        });


        holder.name.setOnClickListener(v -> onProductClick(sqlitemodel, holder));
        holder.iv_mug.setOnClickListener(v -> onProductClick(sqlitemodel, holder));
        holder.info.setOnClickListener(v -> onProductClick(sqlitemodel, holder));
        holder.rate.setOnClickListener(v -> onProductClick(sqlitemodel, holder));
        holder.btn_select.setOnClickListener(v -> onProductClick(sqlitemodel, holder));

        holder.iv_arrow_mug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;
                slideUpDown(holder.iv_arrow_mug, mContext, holder);
            }
        });
        holder.iv_arrow_mug_rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;
                slideUpDown(holder.iv_arrow_mug_rev, mContext, holder);
            }
        });

        if (Share.isinternational == 1) {
            if (!sqlitemodel.getInternational_video_url().equalsIgnoreCase("")) {
                holder.iv_disp_video.setVisibility(View.VISIBLE);
                holder.iv_disp_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long currentClickTime = SystemClock.uptimeMillis();
                        long elapsedTime = currentClickTime - mLastClickTime;
                        mLastClickTime = currentClickTime;
                        if (elapsedTime <= MIN_CLICK_INTERVAL)
                            return;
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sqlitemodel.getInternational_video_url())));
                    }
                });
            } else {
                holder.iv_disp_video.setVisibility(View.GONE);
            }
        } else {
            if (!sqlitemodel.getvideo_url().equalsIgnoreCase("")) {
                holder.iv_disp_video.setVisibility(View.VISIBLE);
                holder.iv_disp_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long currentClickTime = SystemClock.uptimeMillis();
                        long elapsedTime = currentClickTime - mLastClickTime;
                        mLastClickTime = currentClickTime;
                        if (elapsedTime <= MIN_CLICK_INTERVAL)
                            return;
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sqlitemodel.getvideo_url())));
                    }
                });
            } else {
                holder.iv_disp_video.setVisibility(View.GONE);
            }
        }
    }

    private void onProductClick(model_details_data sqlitemodel, MyViewHolder holder) {

        try {
            @SuppressLint("WrongConstant") ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
            final ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            final double ramTotal = memoryInfo.totalMem / (1024 * 1024);
            Log.e("RAM", "onBindViewHolder: Total RAM: " + ramTotal);
            Log.e("RAM", "onBindViewHolder: Total RAM: " + ramTotal / 1024 + " GB");
            String ram="";
            if(Locale.getDefault().getLanguage().equals("en")){
                 ram = String.format("%.1f", ramTotal / 1024);
            }else {
                 ram= String.valueOf(ramTotal / 1024);
            }
            Log.e("RAM", "onBindViewHolder: Total RAM: " + ram + " GB");



            long currentClickTime = SystemClock.uptimeMillis();
            long elapsedTime = currentClickTime - mLastClickTime;
            mLastClickTime = currentClickTime;
            if (elapsedTime <= MIN_CLICK_INTERVAL) {
                return;
            }


            holder.name.setClickable(false);
            holder.rate.setClickable(false);
            holder.btn_select.setClickable(false);
            holder.iv_mug.setClickable(false);
            holder.info.setClickable(false);


            ProductType productId = DataHelperKt.getProductIdType(mContext);

            if (productId.getMultiImagePhotoFrames() == DataHelperKt.getProductId(activity)) {
                if (sqlitemodel.getAvailable_stock().equalsIgnoreCase("0")) {
                    nostock_available_dialog(holder);
                } else {
                    custom_multiple_frame_activity(sqlitemodel, holder);
                }
            } else if (productId.getCustomizeLocket().equals(DataHelperKt.getProductId(activity))) {
                if (sqlitemodel.getAvailable_stock().equalsIgnoreCase("0")) {
                    nostock_available_dialog(holder);
                } else {
                    cusotmizelocket(sqlitemodel, holder);
                }
            } else {
                if (!Locale.getDefault().getLanguage().equalsIgnoreCase("ar")) {
                    if (Double.parseDouble(ram) <= 2.0) {
                        if (productId.getWallWoodenCanvasFrame() == DataHelperKt.getProductId(activity)) {
                            Toast.makeText(mContext, mContext.getString(R.string.not_support_device), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("HERE", "onClick:not locket " + productId.getCustomizeLocket());
                            Log.e("HERE", "onClick:not locket =-==>" + DataHelperKt.getProductId(activity));

                            if (sqlitemodel.getAvailable_stock().equalsIgnoreCase("0")) {
                                nostock_available_dialog(holder);
                            } else {
                                if (productId.getCustomizeLocket().equals(DataHelperKt.getProductId(activity))) {
                                    cusotmizelocket(sqlitemodel, holder);
                                } else {
                                    next(sqlitemodel, holder);
                                }
                            }
                        }
                    } else {
                        if (sqlitemodel.getAvailable_stock().equalsIgnoreCase("0")) {
                            nostock_available_dialog(holder);
                        } else {
                            next(sqlitemodel, holder);
                        }
                    }
                } else {
                    if (sqlitemodel.getAvailable_stock().equalsIgnoreCase("0")) {
                        nostock_available_dialog(holder);
                    } else {
                        if (productId.getCustomizeLocket().equals(DataHelperKt.getProductId(activity))) {
                            cusotmizelocket(sqlitemodel, holder);
                        } else {
                            next(sqlitemodel, holder);
                        }
                    }
                }

            }
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }


    }

    private void cusotmizelocket(model_details_data sqlitemodel, MyViewHolder holder) {
        Share.locket_model_data = sqlitemodel;
        Intent intent = new Intent(mContext, jewellery_activity.class);
        mContext.startActivity(intent);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mug_company_name_item, parent, false);

        return new MyViewHolder(itemView);
    }

    private void generate_uri(final int i) {
        if (checkAndRequestPermissionsStorage(2)) {
            Glide.with(mContext).asBitmap().load(sqlitemodelList.get(i).getN_mug_image()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    image_list_uri.add(Uri.fromFile(getFile("" + System.currentTimeMillis(), resource, ".png")));
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    intent.setType("image/*"); /* This example is sharing jpeg images. */
                    intent.putExtra(Intent.EXTRA_STREAM, image_list_uri);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "applock");
                    String sAux = "Download this amazing " + "applock" + " app from play store\n\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=" + mContext.getPackageName() + "\n\n";
                    intent.putExtra(Intent.EXTRA_TEXT, sAux);
                    mContext.startActivity(Intent.createChooser(intent, "Share notes.."));
                }
            });
        }
    }

    private boolean checkAndRequestPermissionsStorage(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, code);
                return false;
            } else {
                return true;
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, code);
                return false;
            } else {
                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {
                return true;
            }
        }
    }

    public void nostock_available_dialog(final MyViewHolder holder) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setCancelable(false);
        alertDialog.setTitle(mContext.getString(R.string.alert));
        alertDialog.setMessage(mContext.getString(R.string.out_of_stock_item_val));
        alertDialog.setButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                holder.name.setClickable(true);
                holder.rate.setClickable(true);
                holder.btn_select.setClickable(true);
                holder.iv_mug.setClickable(true);
                holder.info.setClickable(true);

                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void next(model_details_data sqlitemodel, MyViewHolder holder) {
        Share.maskheight = Float.parseFloat(sqlitemodel.getHeight());
        Share.maskwidth = Float.parseFloat(sqlitemodel.getWidth());
        Share.model_name = sqlitemodel.getModalName();
        Share.total_amount = sqlitemodel.getPrice();
        Share.model_id = sqlitemodel.getModelId();
        Share.paid_amount = sqlitemodel.getPrice();
        Share.width = sqlitemodel.getWidth();
        Share.height = sqlitemodel.getHeight();

        Share.displayheight = Float.valueOf(sqlitemodel.getDisplayHeight());
        Share.display_width = Float.valueOf(sqlitemodel.getDisplayWidth());

        if (sqlitemodel.getNMaskImage() != null && !sqlitemodel.getNMaskImage().equalsIgnoreCase("null")) {
            str[0] = sqlitemodel.getNMaskImage();
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.something_went_wrong_try_agaian), Toast.LENGTH_SHORT).show();
            return;
        }
        if (sqlitemodel.getNModalImage() != null && !sqlitemodel.getNModalImage().equalsIgnoreCase("null")) {
            str[1] = sqlitemodel.getNModalImage();
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.something_went_wrong_try_agaian), Toast.LENGTH_SHORT).show();
            return;
        }

        if (NetworkManager.isInternetConnectedDailog(mContext)) {
            GetXMLTask task = new GetXMLTask(holder);
            // Execute the task
            task.execute(str);
        }
    }

    private void custom_multiple_frame_activity(model_details_data sqlitemodel, MyViewHolder holder) {
        Share.maskheight = Float.parseFloat(sqlitemodel.getDisplayHeight());
        Share.maskwidth = Float.parseFloat(sqlitemodel.getDisplayWidth());
        Share.model_name = sqlitemodel.getModalName();
        Share.total_amount = sqlitemodel.getPrice();
        Share.model_id = sqlitemodel.getModelId();
        Share.paid_amount = sqlitemodel.getPrice();
        Share.width = sqlitemodel.getWidth();
        Share.height = sqlitemodel.getHeight();

        Share.displayheight = Float.valueOf(sqlitemodel.getDisplayHeight());
        Share.display_width = Float.valueOf(sqlitemodel.getDisplayWidth());

        Share.frame_alldetails = sqlitemodel.getFrameDetails();
        Share.model_svg = sqlitemodel.getNModalImage();
        Intent intent = new Intent(mContext, test_frame_editing_activity.class);
        intent.putExtra("model_name", Share.model_name);
        intent.putExtra("model_id", "" + Share.model_id);
        intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
        intent.putExtra("quantity", "1");
        intent.putExtra("total_amount", Share.total_amount);
        intent.putExtra("paid_amount", Share.total_amount);
        if (!Share.width.equalsIgnoreCase("")) {
            intent.putExtra("width", Share.width);
            intent.putExtra("height", Share.height);
        }
        intent.putExtra("shipping", "0");
        mContext.startActivity(intent);
    }

    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }

    public ArrayList<model_details_data> filter(String s) {
        ArrayList<model_details_data> subData = new ArrayList<>();
        if (s.length() == 0) {
            subData.addAll(Share.subDataArrayList_category_multiple_category);
            ModelListActivity.tv_no_fnd.setVisibility(View.GONE);

        } else {
            for (model_details_data wp : Share.subDataArrayList_category_multiple_category) {

                if (wp.getModalName().toLowerCase().trim().contains(s.toLowerCase())) {

                    subData.add(wp);
                    Log.d("size", "==>" + wp.getModalName());
                    ModelListActivity.tv_no_fnd.setVisibility(View.GONE);

                }
                if (subData.size() <= 0) {
                    ModelListActivity.tv_no_fnd.setVisibility(View.VISIBLE);
                }
            }
        }
        noti(subData);
        return subData;
    }

    public void noti(ArrayList<model_details_data> subData) {
        this.sqlitemodelList = subData;
//        if (subData.size() <= 0) {
//        } else {
//            Model_List_Activity.tv_no_fnd.setVisibility(View.GONE);
//        }
        notifyDataSetChanged();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (permissions.length == 0) {
            return;
        }
        boolean allPermissionsGranted = true;
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
        }
        if (!allPermissionsGranted) {
            boolean somePermissionsForeverDenied = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    //denied
                    Log.e("denied", permission);
                    if (requestCode == 1) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        } else {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
                        }
                    }

                } else {

                    if (ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED) {
                        //allowed
                        Log.e("allowed", permission);
//                        if (requestCode==2){
//                            Log.e("GRANTED", "checkAndRequestPermissionsStorage:=======> " );
//                        }

                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                    }
                }
            }
            if (somePermissionsForeverDenied) {

                if (requestCode == 1) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle(mContext.getResources().getString(R.string.permission_required))
                            .setMessage(mContext.getResources().getString(R.string.permission_sentence))
                            .setPositiveButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", mContext.getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }
                if (requestCode == 2) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle(mContext.getResources().getString(R.string.permission_required))
                            .setMessage(mContext.getResources().getString(R.string.permission_sentence_storage))
                            .setPositiveButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", mContext.getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }

            }
        } else {
            switch (requestCode) {
                case 1:
                    break;
                default:
                    break;
            }
        }
    }

    public File getFile(String filename, Bitmap yourbitmap, String formate) {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), filename + formate);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        Bitmap bitmap = yourbitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (formate.contains("jpg")) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);

        } else {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);

        }
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    private class GetXMLTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
        MyViewHolder final_holder;

        public GetXMLTask(MyViewHolder holder) {
            final_holder = holder;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(mContext, "", mContext.getResources().getString(R.string.loading), true, false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Log.e("BITMAP", "onPostExecute: " + bitmaps.size());


            // ToDo: Changes mae by Jignesh Patel
            String proId = Share.model_id + "";
            String proName = Share.model_name;
            String proPrice = Share.total_amount;
            FBEventsKt.logViewedContentEvent(activity, proName, proId, proPrice);

            ProductType productId = DataHelperKt.getProductIdType(mContext);


            if (bitmaps.size() == 2) {

                if (bitmaps.get(0) != null && bitmaps.get(1) != null) {

                    Share.bitmapHashMap.put(Share.key_msk_imge, bitmaps.get(0));
                    Share.bitmapHashMap.put(Share.key_normal_image, bitmaps.get(1));

                    Log.e("hashmap", "==>" + Share.bitmapHashMap);
                    if (Share.main_category_id == productId.getMug()) {


                        Intent intent = new Intent(mContext, Mug_images_category_activity.class);
                        intent.putExtra("model_name", Share.model_name);
                        intent.putExtra("model_id", "" + Share.model_id);
                        intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
                        intent.putExtra("quantity", "1");
                        intent.putExtra("total_amount", Share.total_amount);
                        intent.putExtra("paid_amount", Share.total_amount);
                        if (!Share.width.equalsIgnoreCase("")) {
                            intent.putExtra("width", Share.width);
                            intent.putExtra("height", Share.height);
                        }
                        intent.putExtra("shipping", "0");
                        mContext.startActivity(intent);


                    } else if (DataHelperKt.getProductId(activity) == 10) {
                        Intent intent = new Intent(mContext, ShipperCategoryActivity.class);
                        intent.putExtra("model_name", Share.model_name);
                        intent.putExtra("model_id", "" + Share.model_id);
                        intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
                        intent.putExtra("quantity", "1");
                        intent.putExtra("total_amount", Share.total_amount);
                        intent.putExtra("paid_amount", Share.total_amount);
                        if (!Share.width.equalsIgnoreCase("")) {
                            intent.putExtra("width", Share.width);
                            intent.putExtra("height", Share.height);
                        }
                        intent.putExtra("shipping", "0");
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, Dynamic_EditActivity.class);
                        intent.putExtra("model_name", Share.model_name);
                        intent.putExtra("model_id", "" + Share.model_id);
                        intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
                        intent.putExtra("quantity", "1");
                        intent.putExtra("total_amount", Share.total_amount);
                        intent.putExtra("paid_amount", Share.total_amount);
                        if (!Share.width.equalsIgnoreCase("")) {
                            intent.putExtra("width", Share.width);
                            intent.putExtra("height", Share.height);
                        }
                        intent.putExtra("shipping", "0");
                        mContext.startActivity(intent);


                    }
                }
            } else {

                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(mContext.getString(R.string.download_failed));
                alertDialog.setMessage(mContext.getResources().getString(R.string.slow_connect));
                alertDialog.setButton(mContext.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        layout5.callOnClick();
                    }
                });
                alertDialog.show();
            }
        }


        @Override
        protected ArrayList<Bitmap> doInBackground(String... strings) {


            ArrayList<Bitmap> map = new ArrayList<Bitmap>();
            try {
                //enhanced for statement, mainly used for arrays
                map.add(downloadImage(strings[0]));// I used as this for you to understand. You can use for each loop
                map.add(downloadImage(strings[1]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;


        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, info, rate, tv_description, id_dummy_price;
        public ImageView iv_mug, iv_arrow_mug, iv_arrow_mug_rev, iv_disp_video, iv_share_pro;
        public Button btn_select;
        public RelativeLayout rl_description;
        public ProgressBar progressBar;


        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            info = view.findViewById(R.id.info);
            rate = view.findViewById(R.id.rate);
            id_dummy_price = view.findViewById(R.id.id_dummy_price);
            iv_mug = view.findViewById(R.id.iv_mug);
            progressBar = view.findViewById(R.id.progressBar);
            iv_arrow_mug = view.findViewById(R.id.iv_arrow_mug);
            iv_arrow_mug_rev = view.findViewById(R.id.iv_arrow_mug_rev);
            btn_select = view.findViewById(R.id.btn_select);
            tv_description = view.findViewById(R.id.tv_description);
            rl_description = view.findViewById(R.id.rl_description);
            iv_disp_video = view.findViewById(R.id.iv_disp_video);
            iv_share_pro = view.findViewById(R.id.iv_share_pro);
        }
    }

}
