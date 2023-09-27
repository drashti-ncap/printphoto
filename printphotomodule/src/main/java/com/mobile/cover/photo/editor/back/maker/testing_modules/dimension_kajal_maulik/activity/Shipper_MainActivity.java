package com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Dynamic_EditActivity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.mainapplication;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.model.DimensionModel;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout1;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout10;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout11;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout12;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout13;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout2;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout3;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout4;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout5;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout6;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout7;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout8;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.widgets.MaskableFrameLayout9;

import java.util.ArrayList;
import java.util.List;

public class Shipper_MainActivity extends AppCompatActivity {

    private static final String TAG = "ShipperMainActivity";
    public static float frame1_x, frame1_y, frame1_width, frame1_height, frame2_x, frame2_y, frame2_width, frame2_height, frame3_x,
            frame3_y, frame3_width, frame3_height, frame4_x, frame4_y, frame4_width, frame4_height, frame5_x, frame5_y, frame5_width, frame5_height, frame6_x, frame6_y, frame6_width, frame6_height,
            frame7_x, frame7_y, frame7_width, frame7_height,
            frame8_x, frame8_y, frame8_width, frame8_height,
            frame9_x, frame9_y, frame9_width, frame9_height,
            frame10_x, frame10_y, frame10_width, frame10_height,
            frame11_x, frame11_y, frame11_width, frame11_height,
            frame12_x, frame12_y, frame12_width, frame12_height,
            frame13_x, frame13_y, frame13_width, frame13_height,
            frame14_x, frame14_y, frame14_width, frame14_height;
    public static Activity activity;
    ArrayList<DimensionModel> dimen_frame_2 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_3 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_4 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_5 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_6 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_7 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_8 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_9 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_10 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_11 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_12 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_13 = new ArrayList<>();
    ArrayList<DimensionModel> dimen_frame_14 = new ArrayList<>();
    //    int position = 0;
    ArrayList<DimensionModel> dimen_frame_1 = new ArrayList<>();
    Button button;
    List<Boolean> temp = new ArrayList<>();
    ImageView id_back, btn_add;
    private ImageView iv_frame;
    private FrameLayout fl_1;
    private MaskableFrameLayout maskable_fl_1;
    private MaskableFrameLayout1 maskable_fl_2;
    private MaskableFrameLayout2 maskable_fl_3;
    private MaskableFrameLayout3 maskable_fl_4;
    private MaskableFrameLayout4 maskable_fl_5;
    private MaskableFrameLayout5 maskable_fl_6;
    private MaskableFrameLayout6 maskable_fl_7;
    private MaskableFrameLayout7 maskable_fl_8;
    private MaskableFrameLayout8 maskable_fl_9;
    private MaskableFrameLayout9 maskable_fl_10;
    private MaskableFrameLayout10 maskable_fl_11;
    private MaskableFrameLayout11 maskable_fl_12;
    private MaskableFrameLayout12 maskable_fl_13;
    private MaskableFrameLayout13 maskable_fl_14;
    private ImageView iv_subframe_1, iv_subframe_2, iv_subframe_3, iv_subframe_4, iv_subframe_5, iv_subframe_6, iv_subframe_7, iv_subframe_8, iv_subframe_9, iv_subframe_10, iv_subframe_11, iv_subframe_12, iv_subframe_13, iv_subframe_14;
    private float device_width;
    private float device_height;
    private RelativeLayout rl_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipper_set_activity);
//        position = getIntent().getExtras().getInt("position");
//        position = 23;
        activity = Shipper_MainActivity.this;
        Display display = getWindowManager().getDefaultDisplay(); // pixels // 1280 720
        findViews();
        Point size = new Point();
        display.getSize(size);
        device_width = size.x;
        device_height = size.y;
        temp.clear();

        rl_main.getLayoutParams().width = (int) (device_width / 1.15);
        rl_main.getLayoutParams().height = (int) (device_height / 2.5);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: =======>" + temp.size() + "//" + Share.itemnum);
                if (temp.size() == Share.itemnum) {
                    rl_main.setDrawingCacheEnabled(true);
//                rl_main.setDrawingCacheEnabled(true);

                    Bitmap bitmap = Bitmap.createBitmap(rl_main.getDrawingCache());
//                bitmap=Bitmap.createScaledBitmap(bitmap,1400*2,2489*2,true);
                    Share.bitmap_testing = bitmap;
                    rl_main.setDrawingCacheEnabled(false);


                    if (Share.bitmap_testing != null) {

                        Intent intent = new Intent(Shipper_MainActivity.this, Dynamic_EditActivity.class);
                        intent.putExtra("model_name", Share.model_name);
                        intent.putExtra("model_id", "" + Share.model_id);
                        intent.putExtra("user_id", SharedPrefs.getString(Shipper_MainActivity.this, Share.key_ + RegReq.id));
                        intent.putExtra("quantity", "1");
                        intent.putExtra("total_amount", Share.total_amount);
                        intent.putExtra("paid_amount", Share.total_amount);
                        if (!Share.width.equalsIgnoreCase("")) {
                            intent.putExtra("width", Share.width);
                            intent.putExtra("height", Share.height);
                        }
                        intent.putExtra("shipping", "0");
                        startActivity(intent);
//                        } else {
//                            Intent intent = new Intent(Shipper_MainActivity.this, CoffeeMugEditActivity.class);
//                            intent.putExtra("model_name", Share.model_name);
//                            intent.putExtra("model_id", "" + Share.model_id);
//                            intent.putExtra("user_id", SharedPrefs.getString(Shipper_MainActivity.this, Share.key_ + RegReq.id));
//                            intent.putExtra("quantity", "1");
//                            intent.putExtra("total_amount", Share.total_amount);
//                            intent.putExtra("paid_amount", Share.total_amount);
//                            if (!Share.width.equalsIgnoreCase("")) {
//                                intent.putExtra("width", Share.width);
//                                intent.putExtra("height", Share.height);
//                            }
//                            intent.putExtra("shipping", "0");
//                            startActivity(intent);
//                        }
                    }
                } else {
                    Toast.makeText(Shipper_MainActivity.this, getString(R.string.image_processing), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.e(TAG, "onCreate: h = " + device_height + "w = " + device_width);

        startMasking();
    }

    private void findViews() {

        fl_1 = findViewById(R.id.fl_1);

        iv_frame = findViewById(R.id.iv_frame);

        btn_add = findViewById(R.id.btn_add);
        id_back = findViewById(R.id.id_back);
//        id_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Shipper_MainActivity.this, VideoActivity.class);
//                startActivity(intent);
//            }
//        });

        rl_main = findViewById(R.id.rl_main);

        iv_subframe_1 = findViewById(R.id.iv_subframe_1);
        iv_subframe_2 = findViewById(R.id.iv_subframe_2);
        iv_subframe_3 = findViewById(R.id.iv_subframe_3);
        iv_subframe_4 = findViewById(R.id.iv_subframe_4);
        iv_subframe_5 = findViewById(R.id.iv_subframe_5);
        iv_subframe_6 = findViewById(R.id.iv_subframe_6);
        iv_subframe_7 = findViewById(R.id.iv_subframe_7);
        iv_subframe_8 = findViewById(R.id.iv_subframe_8);
        iv_subframe_9 = findViewById(R.id.iv_subframe_9);
        iv_subframe_10 = findViewById(R.id.iv_subframe_10);
        iv_subframe_11 = findViewById(R.id.iv_subframe_11);
        iv_subframe_12 = findViewById(R.id.iv_subframe_12);
        iv_subframe_13 = findViewById(R.id.iv_subframe_13);
        iv_subframe_14 = findViewById(R.id.iv_subframe_14);

        maskable_fl_1 = findViewById(R.id.maskable_fl_1);
        maskable_fl_2 = findViewById(R.id.maskable_fl_2);
        maskable_fl_3 = findViewById(R.id.maskable_fl_3);
        maskable_fl_4 = findViewById(R.id.maskable_fl_4);
        maskable_fl_5 = findViewById(R.id.maskable_fl_5);
        maskable_fl_6 = findViewById(R.id.maskable_fl_6);
        maskable_fl_7 = findViewById(R.id.maskable_fl_7);
        maskable_fl_8 = findViewById(R.id.maskable_fl_8);
        maskable_fl_9 = findViewById(R.id.maskable_fl_9);
        maskable_fl_10 = findViewById(R.id.maskable_fl_10);
        maskable_fl_11 = findViewById(R.id.maskable_fl_11);
        maskable_fl_12 = findViewById(R.id.maskable_fl_12);
        maskable_fl_13 = findViewById(R.id.maskable_fl_13);
        maskable_fl_14 = findViewById(R.id.maskable_fl_14);

    }

    private void startMasking() {

        Log.e(" in start masking", "startMasking: ");
        Log.e(TAG, "setFrameDimen: height" + device_height);
        Log.e(TAG, "setFrameDimen: width" + device_width);

        dimen_frame_1.clear();
        dimen_frame_2.clear();
        dimen_frame_3.clear();
        dimen_frame_4.clear();
        dimen_frame_5.clear();
        dimen_frame_6.clear();
        dimen_frame_7.clear();
        dimen_frame_8.clear();
        dimen_frame_9.clear();
        dimen_frame_10.clear();
        dimen_frame_11.clear();
        dimen_frame_12.clear();
        dimen_frame_13.clear();
        dimen_frame_14.clear();

        iv_frame.setImageBitmap(Share.case_fancy_image);
        Log.e("CASE_IMAGE_ID", "startMasking: " + DataHelperKt.getCaseId(activity));

        switch ( DataHelperKt.getCaseId(activity)) {
            case 16:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 2:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 3:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.35)), (int) (device_height - (device_height / 1.2)), (int) (device_width - (device_width / 1.22)), (int) (device_height - (device_height / 1.1))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.42)), (int) (device_height - (device_height / 1.2)), (int) (device_width - (device_width / 1.66)), (int) (device_height - (device_height / 1.18))));//1

                maskable_fl_1.setMask(R.drawable.shipper_mask_3_1);
                maskable_fl_2.setMask(R.drawable.shipper_mask_3_2);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 4:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 5:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;
            case 6:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;
            case 7:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;
            case 8:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;
            case 9:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;
            case 10:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 11:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 2)), (int) (device_height - (device_height / 1.4)), (int) (device_width - (device_width / 1.05)), (int) (device_height - (device_height / 1.01))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.55)), (int) (device_height - (device_height / 1.25)), (int) (device_width - (device_width / 1.95)), (int) (device_height - (device_height / 1.25))));//1

                maskable_fl_1.setMask(R.drawable.heart_1);
                maskable_fl_2.setMask(R.drawable.heart_2);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 12:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 13:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.45)), (int) (device_height - (device_height / 1.25)), (int) (device_width - (device_width / 1.15)), (int) (device_height - (device_height / 1.01))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.45)), (int) (device_height - (device_height / 1.28)), (int) (device_width - (device_width / 1.85)), (int) (device_height - (device_height / 1.01))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width), (int) (device_height - (device_height / 1.238)), 0, (int) (device_height - (device_height / 1.301))));//1


                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);
                maskable_fl_3.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 14:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 15:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 17:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.65)), (int) (device_height / 2), (int) (device_width - (device_width / 1.075)), (int) (device_height - (device_height / 1.01))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.55)), (int) (device_height / 2), (int) (device_width - (device_width / 1.85)), (int) (device_height - (device_height / 1.01))));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 18:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 19:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 20:
                dimen_frame_1.add(new DimensionModel((int) (device_width), (int) (device_height), 0, 0));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
        }
        setMaskImage();
    }

    private void setMaskImage() {
        iv_frame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("ClickableViewAccessibility")
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                iv_frame.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                iv_frame.getHeight(); //height is ready
                iv_frame.getWidth();
                device_width = iv_frame.getWidth();
                device_height = iv_frame.getHeight();

                switch ( DataHelperKt.getCaseId(activity)) {
                    case 16:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;

                    case 2:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;

                    case 3:
                        Log.e(TAG, "onGlobalLayout: ");
                        initSubFrame_two(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2);
                        setimage();
                        break;

                    case 4:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;

                    case 5:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 6:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 7:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 8:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 9:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 10:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 11:
                        initSubFrame_two(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2);
                        setimage();
                        break;

                    case 12:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;

                    case 13:
                        initSubFrame_three(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3);
                        setimage();
                        break;
                    case 14:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;

                    case 15:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 17:
                        initSubFrame_two(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2);
                        setimage();
                        break;
                    case 18:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 19:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 20:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                }

                iv_frame.getX();
                iv_frame.getY();
            }
        });
        System.gc();
        Runtime.getRuntime().gc();
    }

    private void setimage() {

        for (int i = 0; i < mainapplication.org_selectedImages.size(); i++) {
            if (i == 0) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_1.setImageBitmap(resource);
                    }
                });
            }
            if (i == 1) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_2.setImageBitmap(resource);
                    }
                });
            }
            if (i == 2) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_3.setImageBitmap(resource);
                    }
                });
            }
            if (i == 3) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_4.setImageBitmap(resource);
                    }
                });
            }
            if (i == 4) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_5.setImageBitmap(resource);
                    }
                });
            }
            if (i == 5) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_6.setImageBitmap(resource);
                    }
                });
            }
            if (i == 6) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_7.setImageBitmap(resource);
                    }
                });
            }
            if (i == 7) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_8.setImageBitmap(resource);
                    }
                });
            }
            if (i == 8) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_9.setImageBitmap(resource);
                    }
                });
            }
            if (i == 9) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_10.setImageBitmap(resource);
                    }
                });
            }
            if (i == 10) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_11.setImageBitmap(resource);
                    }
                });
            }
            if (i == 11) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_12.setImageBitmap(resource);
                    }
                });
            }
            if (i == 12) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_13.setImageBitmap(resource);
                    }
                });
            }
            if (i == 13) {
                Glide.with(Shipper_MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_14.setImageBitmap(resource);
                    }
                });
            }
        }


    }


    private void initSubFrame_fourteen(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1, ArrayList<DimensionModel> dimen_frame3_2,
                                       MaskableFrameLayout1 maskable_fl_2, ArrayList<DimensionModel> dimen_frame3_3, MaskableFrameLayout2 maskable_fl_3,
                                       ArrayList<DimensionModel> dimen_frame3_4, MaskableFrameLayout3 maskable_fl_4,
                                       ArrayList<DimensionModel> dimen_frame3_5, MaskableFrameLayout4 maskable_fl_5,
                                       ArrayList<DimensionModel> dimen_frame3_6, MaskableFrameLayout5 maskable_fl_6,
                                       ArrayList<DimensionModel> dimen_frame3_7, MaskableFrameLayout6 maskable_fl_7,
                                       ArrayList<DimensionModel> dimen_frame3_8, MaskableFrameLayout7 maskable_fl_8,
                                       ArrayList<DimensionModel> dimen_frame3_9, MaskableFrameLayout8 maskable_fl_9,
                                       ArrayList<DimensionModel> dimen_frame3_10, MaskableFrameLayout9 maskable_fl_10,
                                       ArrayList<DimensionModel> dimen_frame3_11, MaskableFrameLayout10 maskable_fl_11,
                                       ArrayList<DimensionModel> dimen_frame3_12, MaskableFrameLayout11 maskable_fl_12,
                                       ArrayList<DimensionModel> dimen_frame3_13, MaskableFrameLayout12 maskable_fl_13,
                                       ArrayList<DimensionModel> dimen_frame3_14, MaskableFrameLayout13 maskable_fl_14) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        frame2_x = dimen_frame3_2.get(0).getX();
        frame2_y = dimen_frame3_2.get(0).getY();
        frame2_width = dimen_frame3_2.get(0).getWidth();
        frame2_height = dimen_frame3_2.get(0).getHeight();

        frame3_x = dimen_frame3_3.get(0).getX();
        frame3_y = dimen_frame3_3.get(0).getY();
        frame3_width = dimen_frame3_3.get(0).getWidth();
        frame3_height = dimen_frame3_3.get(0).getHeight();

        frame4_x = dimen_frame3_4.get(0).getX();
        frame4_y = dimen_frame3_4.get(0).getY();
        frame4_width = dimen_frame3_4.get(0).getWidth();
        frame4_height = dimen_frame3_4.get(0).getHeight();

        frame5_x = dimen_frame3_5.get(0).getX();
        frame5_y = dimen_frame3_5.get(0).getY();
        frame5_width = dimen_frame3_5.get(0).getWidth();
        frame5_height = dimen_frame3_5.get(0).getHeight();

        frame6_x = dimen_frame3_6.get(0).getX();
        frame6_y = dimen_frame3_6.get(0).getY();
        frame6_width = dimen_frame3_6.get(0).getWidth();
        frame6_height = dimen_frame3_6.get(0).getHeight();

        frame7_x = dimen_frame3_7.get(0).getX();
        frame7_y = dimen_frame3_7.get(0).getY();
        frame7_width = dimen_frame3_7.get(0).getWidth();
        frame7_height = dimen_frame3_7.get(0).getHeight();

        frame8_x = dimen_frame3_8.get(0).getX();
        frame8_y = dimen_frame3_8.get(0).getY();
        frame8_width = dimen_frame3_8.get(0).getWidth();
        frame8_height = dimen_frame3_8.get(0).getHeight();

        frame9_x = dimen_frame3_9.get(0).getX();
        frame9_y = dimen_frame3_9.get(0).getY();
        frame9_width = dimen_frame3_9.get(0).getWidth();
        frame9_height = dimen_frame3_9.get(0).getHeight();

        frame10_x = dimen_frame3_10.get(0).getX();
        frame10_y = dimen_frame3_10.get(0).getY();
        frame10_width = dimen_frame3_10.get(0).getWidth();
        frame10_height = dimen_frame3_10.get(0).getHeight();

        frame11_x = dimen_frame3_11.get(0).getX();
        frame11_y = dimen_frame3_11.get(0).getY();
        frame11_width = dimen_frame3_11.get(0).getWidth();
        frame11_height = dimen_frame3_11.get(0).getHeight();

        frame12_x = dimen_frame3_12.get(0).getX();
        frame12_y = dimen_frame3_12.get(0).getY();
        frame12_width = dimen_frame3_12.get(0).getWidth();
        frame12_height = dimen_frame3_12.get(0).getHeight();

        frame13_x = dimen_frame3_13.get(0).getX();
        frame13_y = dimen_frame3_13.get(0).getY();
        frame13_width = dimen_frame3_13.get(0).getWidth();
        frame13_height = dimen_frame3_13.get(0).getHeight();

        frame14_x = dimen_frame3_14.get(0).getX();
        frame14_y = dimen_frame3_14.get(0).getY();
        frame14_width = dimen_frame3_14.get(0).getWidth();
        frame14_height = dimen_frame3_14.get(0).getHeight();


        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

        maskable_fl_2.setLayoutParams(new FrameLayout.LayoutParams((int) frame2_width, (int) frame2_height));
        maskable_fl_2.setX(frame2_x);
        maskable_fl_2.setY(frame2_y);

        maskable_fl_3.setLayoutParams(new FrameLayout.LayoutParams((int) frame3_width, (int) frame3_height));
        maskable_fl_3.setX(frame3_x);
        maskable_fl_3.setY(frame3_y);

        maskable_fl_4.setLayoutParams(new FrameLayout.LayoutParams((int) frame4_width, (int) frame4_height));
        maskable_fl_4.setX(frame4_x);
        maskable_fl_4.setY(frame4_y);

        maskable_fl_5.getLayoutParams().width = (int) frame5_width;
        maskable_fl_5.getLayoutParams().height = (int) frame5_height;
        maskable_fl_5.setX(frame5_x);
        maskable_fl_5.setY(frame5_y);

        maskable_fl_6.getLayoutParams().width = (int) frame6_width;
        maskable_fl_6.getLayoutParams().height = (int) frame6_height;
        maskable_fl_6.setX(frame6_x);
        maskable_fl_6.setY(frame6_y);


        maskable_fl_7.getLayoutParams().width = (int) frame7_width;
        maskable_fl_7.getLayoutParams().height = (int) frame7_height;
        maskable_fl_7.setX(frame7_x);
        maskable_fl_7.setY(frame7_y);

        maskable_fl_8.getLayoutParams().width = (int) frame8_width;
        maskable_fl_8.getLayoutParams().height = (int) frame8_height;
        maskable_fl_8.setX(frame8_x);
        maskable_fl_8.setY(frame8_y);

        maskable_fl_9.getLayoutParams().width = (int) frame9_width;
        maskable_fl_9.getLayoutParams().height = (int) frame9_height;
        maskable_fl_9.setX(frame9_x);
        maskable_fl_9.setY(frame9_y);

        maskable_fl_10.getLayoutParams().width = (int) frame10_width;
        maskable_fl_10.getLayoutParams().height = (int) frame10_height;
        maskable_fl_10.setX(frame10_x);
        maskable_fl_10.setY(frame10_y);

        maskable_fl_11.getLayoutParams().width = (int) frame11_width;
        maskable_fl_11.getLayoutParams().height = (int) frame11_height;
        maskable_fl_11.setX(frame11_x);
        maskable_fl_11.setY(frame11_y);

        maskable_fl_12.getLayoutParams().width = (int) frame12_width;
        maskable_fl_12.getLayoutParams().height = (int) frame12_height;
        maskable_fl_12.setX(frame12_x);
        maskable_fl_12.setY(frame12_y);

        maskable_fl_13.getLayoutParams().width = (int) frame13_width;
        maskable_fl_13.getLayoutParams().height = (int) frame13_height;
        maskable_fl_13.setX(frame13_x);
        maskable_fl_13.setY(frame13_y);

        maskable_fl_14.getLayoutParams().width = (int) frame14_width;
        maskable_fl_14.getLayoutParams().height = (int) frame14_height;
        maskable_fl_14.setX(frame14_x);
        maskable_fl_14.setY(frame14_y);


    }

    private void initSubFrame_ten(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1, ArrayList<DimensionModel> dimen_frame3_2,
                                  MaskableFrameLayout1 maskable_fl_2, ArrayList<DimensionModel> dimen_frame3_3, MaskableFrameLayout2 maskable_fl_3,
                                  ArrayList<DimensionModel> dimen_frame3_4, MaskableFrameLayout3 maskable_fl_4,
                                  ArrayList<DimensionModel> dimen_frame3_5, MaskableFrameLayout4 maskable_fl_5,
                                  ArrayList<DimensionModel> dimen_frame3_6, MaskableFrameLayout5 maskable_fl_6,
                                  ArrayList<DimensionModel> dimen_frame3_7, MaskableFrameLayout6 maskable_fl_7,
                                  ArrayList<DimensionModel> dimen_frame3_8, MaskableFrameLayout7 maskable_fl_8,
                                  ArrayList<DimensionModel> dimen_frame3_9, MaskableFrameLayout8 maskable_fl_9,
                                  ArrayList<DimensionModel> dimen_frame3_10, MaskableFrameLayout9 maskable_fl_10) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        frame2_x = dimen_frame3_2.get(0).getX();
        frame2_y = dimen_frame3_2.get(0).getY();
        frame2_width = dimen_frame3_2.get(0).getWidth();
        frame2_height = dimen_frame3_2.get(0).getHeight();

        frame3_x = dimen_frame3_3.get(0).getX();
        frame3_y = dimen_frame3_3.get(0).getY();
        frame3_width = dimen_frame3_3.get(0).getWidth();
        frame3_height = dimen_frame3_3.get(0).getHeight();

        frame4_x = dimen_frame3_4.get(0).getX();
        frame4_y = dimen_frame3_4.get(0).getY();
        frame4_width = dimen_frame3_4.get(0).getWidth();
        frame4_height = dimen_frame3_4.get(0).getHeight();

        frame5_x = dimen_frame3_5.get(0).getX();
        frame5_y = dimen_frame3_5.get(0).getY();
        frame5_width = dimen_frame3_5.get(0).getWidth();
        frame5_height = dimen_frame3_5.get(0).getHeight();

        frame6_x = dimen_frame3_6.get(0).getX();
        frame6_y = dimen_frame3_6.get(0).getY();
        frame6_width = dimen_frame3_6.get(0).getWidth();
        frame6_height = dimen_frame3_6.get(0).getHeight();

        frame7_x = dimen_frame3_7.get(0).getX();
        frame7_y = dimen_frame3_7.get(0).getY();
        frame7_width = dimen_frame3_7.get(0).getWidth();
        frame7_height = dimen_frame3_7.get(0).getHeight();

        frame8_x = dimen_frame3_8.get(0).getX();
        frame8_y = dimen_frame3_8.get(0).getY();
        frame8_width = dimen_frame3_8.get(0).getWidth();
        frame8_height = dimen_frame3_8.get(0).getHeight();

        frame9_x = dimen_frame3_9.get(0).getX();
        frame9_y = dimen_frame3_9.get(0).getY();
        frame9_width = dimen_frame3_9.get(0).getWidth();
        frame9_height = dimen_frame3_9.get(0).getHeight();

        frame10_x = dimen_frame3_10.get(0).getX();
        frame10_y = dimen_frame3_10.get(0).getY();
        frame10_width = dimen_frame3_10.get(0).getWidth();
        frame10_height = dimen_frame3_10.get(0).getHeight();


        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

        maskable_fl_2.setLayoutParams(new FrameLayout.LayoutParams((int) frame2_width, (int) frame2_height));
        maskable_fl_2.setX(frame2_x);
        maskable_fl_2.setY(frame2_y);

        maskable_fl_3.setLayoutParams(new FrameLayout.LayoutParams((int) frame3_width, (int) frame3_height));
        maskable_fl_3.setX(frame3_x);
        maskable_fl_3.setY(frame3_y);

        maskable_fl_4.setLayoutParams(new FrameLayout.LayoutParams((int) frame4_width, (int) frame4_height));
        maskable_fl_4.setX(frame4_x);
        maskable_fl_4.setY(frame4_y);

        maskable_fl_5.getLayoutParams().width = (int) frame5_width;
        maskable_fl_5.getLayoutParams().height = (int) frame5_height;
        maskable_fl_5.setX(frame5_x);
        maskable_fl_5.setY(frame5_y);

        maskable_fl_6.getLayoutParams().width = (int) frame6_width;
        maskable_fl_6.getLayoutParams().height = (int) frame6_height;
        maskable_fl_6.setX(frame6_x);
        maskable_fl_6.setY(frame6_y);


        maskable_fl_7.getLayoutParams().width = (int) frame7_width;
        maskable_fl_7.getLayoutParams().height = (int) frame7_height;
        maskable_fl_7.setX(frame7_x);
        maskable_fl_7.setY(frame7_y);

        maskable_fl_8.getLayoutParams().width = (int) frame8_width;
        maskable_fl_8.getLayoutParams().height = (int) frame8_height;
        maskable_fl_8.setX(frame8_x);
        maskable_fl_8.setY(frame8_y);

        maskable_fl_9.getLayoutParams().width = (int) frame9_width;
        maskable_fl_9.getLayoutParams().height = (int) frame9_height;
        maskable_fl_9.setX(frame9_x);
        maskable_fl_9.setY(frame9_y);

        maskable_fl_10.getLayoutParams().width = (int) frame10_width;
        maskable_fl_10.getLayoutParams().height = (int) frame10_height;
        maskable_fl_10.setX(frame10_x);
        maskable_fl_10.setY(frame10_y);


    }


    private void initSubFrame_seven(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1, ArrayList<DimensionModel> dimen_frame3_2,
                                    MaskableFrameLayout1 maskable_fl_2, ArrayList<DimensionModel> dimen_frame3_3, MaskableFrameLayout2 maskable_fl_3,
                                    ArrayList<DimensionModel> dimen_frame3_4, MaskableFrameLayout3 maskable_fl_4, ArrayList<DimensionModel> dimen_frame3_5, MaskableFrameLayout4 maskable_fl_5, ArrayList<DimensionModel> dimen_frame3_6, MaskableFrameLayout5 maskable_fl_6,
                                    ArrayList<DimensionModel> dimen_frame3_7, MaskableFrameLayout6 maskable_fl_7) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        frame2_x = dimen_frame3_2.get(0).getX();
        frame2_y = dimen_frame3_2.get(0).getY();
        frame2_width = dimen_frame3_2.get(0).getWidth();
        frame2_height = dimen_frame3_2.get(0).getHeight();

        frame3_x = dimen_frame3_3.get(0).getX();
        frame3_y = dimen_frame3_3.get(0).getY();
        frame3_width = dimen_frame3_3.get(0).getWidth();
        frame3_height = dimen_frame3_3.get(0).getHeight();

        frame4_x = dimen_frame3_4.get(0).getX();
        frame4_y = dimen_frame3_4.get(0).getY();
        frame4_width = dimen_frame3_4.get(0).getWidth();
        frame4_height = dimen_frame3_4.get(0).getHeight();

        frame5_x = dimen_frame3_5.get(0).getX();
        frame5_y = dimen_frame3_5.get(0).getY();
        frame5_width = dimen_frame3_5.get(0).getWidth();
        frame5_height = dimen_frame3_5.get(0).getHeight();

        frame6_x = dimen_frame3_6.get(0).getX();
        frame6_y = dimen_frame3_6.get(0).getY();
        frame6_width = dimen_frame3_6.get(0).getWidth();
        frame6_height = dimen_frame3_6.get(0).getHeight();

        frame7_x = dimen_frame3_7.get(0).getX();
        frame7_y = dimen_frame3_7.get(0).getY();
        frame7_width = dimen_frame3_7.get(0).getWidth();
        frame7_height = dimen_frame3_7.get(0).getHeight();


        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

        maskable_fl_2.setLayoutParams(new FrameLayout.LayoutParams((int) frame2_width, (int) frame2_height));
        maskable_fl_2.setX(frame2_x);
        maskable_fl_2.setY(frame2_y);

        maskable_fl_3.setLayoutParams(new FrameLayout.LayoutParams((int) frame3_width, (int) frame3_height));
        maskable_fl_3.setX(frame3_x);
        maskable_fl_3.setY(frame3_y);

        maskable_fl_4.setLayoutParams(new FrameLayout.LayoutParams((int) frame4_width, (int) frame4_height));
        maskable_fl_4.setX(frame4_x);
        maskable_fl_4.setY(frame4_y);

        maskable_fl_5.getLayoutParams().width = (int) frame5_width;
        maskable_fl_5.getLayoutParams().height = (int) frame5_height;
        maskable_fl_5.setX(frame5_x);
        maskable_fl_5.setY(frame5_y);

        maskable_fl_6.getLayoutParams().width = (int) frame6_width;
        maskable_fl_6.getLayoutParams().height = (int) frame6_height;
        maskable_fl_6.setX(frame6_x);
        maskable_fl_6.setY(frame6_y);


        maskable_fl_7.getLayoutParams().width = (int) frame7_width;
        maskable_fl_7.getLayoutParams().height = (int) frame7_height;
        maskable_fl_7.setX(frame7_x);
        maskable_fl_7.setY(frame7_y);


    }

    private void initSubFrame_eight(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1, ArrayList<DimensionModel> dimen_frame3_2,
                                    MaskableFrameLayout1 maskable_fl_2, ArrayList<DimensionModel> dimen_frame3_3, MaskableFrameLayout2 maskable_fl_3,
                                    ArrayList<DimensionModel> dimen_frame3_4, MaskableFrameLayout3 maskable_fl_4, ArrayList<DimensionModel> dimen_frame3_5, MaskableFrameLayout4 maskable_fl_5, ArrayList<DimensionModel> dimen_frame3_6, MaskableFrameLayout5 maskable_fl_6,
                                    ArrayList<DimensionModel> dimen_frame3_7, MaskableFrameLayout6 maskable_fl_7,
                                    ArrayList<DimensionModel> dimen_frame3_8, MaskableFrameLayout7 maskable_fl_8) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        frame2_x = dimen_frame3_2.get(0).getX();
        frame2_y = dimen_frame3_2.get(0).getY();
        frame2_width = dimen_frame3_2.get(0).getWidth();
        frame2_height = dimen_frame3_2.get(0).getHeight();

        frame3_x = dimen_frame3_3.get(0).getX();
        frame3_y = dimen_frame3_3.get(0).getY();
        frame3_width = dimen_frame3_3.get(0).getWidth();
        frame3_height = dimen_frame3_3.get(0).getHeight();

        frame4_x = dimen_frame3_4.get(0).getX();
        frame4_y = dimen_frame3_4.get(0).getY();
        frame4_width = dimen_frame3_4.get(0).getWidth();
        frame4_height = dimen_frame3_4.get(0).getHeight();

        frame5_x = dimen_frame3_5.get(0).getX();
        frame5_y = dimen_frame3_5.get(0).getY();
        frame5_width = dimen_frame3_5.get(0).getWidth();
        frame5_height = dimen_frame3_5.get(0).getHeight();

        frame6_x = dimen_frame3_6.get(0).getX();
        frame6_y = dimen_frame3_6.get(0).getY();
        frame6_width = dimen_frame3_6.get(0).getWidth();
        frame6_height = dimen_frame3_6.get(0).getHeight();

        frame7_x = dimen_frame3_7.get(0).getX();
        frame7_y = dimen_frame3_7.get(0).getY();
        frame7_width = dimen_frame3_7.get(0).getWidth();
        frame7_height = dimen_frame3_7.get(0).getHeight();

        frame8_x = dimen_frame3_8.get(0).getX();
        frame8_y = dimen_frame3_8.get(0).getY();
        frame8_width = dimen_frame3_8.get(0).getWidth();
        frame8_height = dimen_frame3_8.get(0).getHeight();

        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

        maskable_fl_2.setLayoutParams(new FrameLayout.LayoutParams((int) frame2_width, (int) frame2_height));
        maskable_fl_2.setX(frame2_x);
        maskable_fl_2.setY(frame2_y);

        maskable_fl_3.setLayoutParams(new FrameLayout.LayoutParams((int) frame3_width, (int) frame3_height));
        maskable_fl_3.setX(frame3_x);
        maskable_fl_3.setY(frame3_y);

        maskable_fl_4.setLayoutParams(new FrameLayout.LayoutParams((int) frame4_width, (int) frame4_height));
        maskable_fl_4.setX(frame4_x);
        maskable_fl_4.setY(frame4_y);

        maskable_fl_5.getLayoutParams().width = (int) frame5_width;
        maskable_fl_5.getLayoutParams().height = (int) frame5_height;
        maskable_fl_5.setX(frame5_x);
        maskable_fl_5.setY(frame5_y);

        maskable_fl_6.getLayoutParams().width = (int) frame6_width;
        maskable_fl_6.getLayoutParams().height = (int) frame6_height;
        maskable_fl_6.setX(frame6_x);
        maskable_fl_6.setY(frame6_y);


        maskable_fl_7.getLayoutParams().width = (int) frame7_width;
        maskable_fl_7.getLayoutParams().height = (int) frame7_height;
        maskable_fl_7.setX(frame7_x);
        maskable_fl_7.setY(frame7_y);


        maskable_fl_8.getLayoutParams().width = (int) frame8_width;
        maskable_fl_8.getLayoutParams().height = (int) frame8_height;
        maskable_fl_8.setX(frame8_x);
        maskable_fl_8.setY(frame8_y);

    }


    private void initSubFrame_six(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1, ArrayList<DimensionModel> dimen_frame3_2,
                                  MaskableFrameLayout1 maskable_fl_2, ArrayList<DimensionModel> dimen_frame3_3, MaskableFrameLayout2 maskable_fl_3,
                                  ArrayList<DimensionModel> dimen_frame3_4, MaskableFrameLayout3 maskable_fl_4, ArrayList<DimensionModel> dimen_frame3_5, MaskableFrameLayout4 maskable_fl_5, ArrayList<DimensionModel> dimen_frame3_6, MaskableFrameLayout5 maskable_fl_6) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        frame2_x = dimen_frame3_2.get(0).getX();
        frame2_y = dimen_frame3_2.get(0).getY();
        frame2_width = dimen_frame3_2.get(0).getWidth();
        frame2_height = dimen_frame3_2.get(0).getHeight();

        frame3_x = dimen_frame3_3.get(0).getX();
        frame3_y = dimen_frame3_3.get(0).getY();
        frame3_width = dimen_frame3_3.get(0).getWidth();
        frame3_height = dimen_frame3_3.get(0).getHeight();

        frame4_x = dimen_frame3_4.get(0).getX();
        frame4_y = dimen_frame3_4.get(0).getY();
        frame4_width = dimen_frame3_4.get(0).getWidth();
        frame4_height = dimen_frame3_4.get(0).getHeight();

        frame5_x = dimen_frame3_5.get(0).getX();
        frame5_y = dimen_frame3_5.get(0).getY();
        frame5_width = dimen_frame3_5.get(0).getWidth();
        frame5_height = dimen_frame3_5.get(0).getHeight();

        frame6_x = dimen_frame3_6.get(0).getX();
        frame6_y = dimen_frame3_6.get(0).getY();
        frame6_width = dimen_frame3_6.get(0).getWidth();
        frame6_height = dimen_frame3_6.get(0).getHeight();

        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

        maskable_fl_2.setLayoutParams(new FrameLayout.LayoutParams((int) frame2_width, (int) frame2_height));
        maskable_fl_2.setX(frame2_x);
        maskable_fl_2.setY(frame2_y);

        maskable_fl_3.setLayoutParams(new FrameLayout.LayoutParams((int) frame3_width, (int) frame3_height));
        maskable_fl_3.setX(frame3_x);
        maskable_fl_3.setY(frame3_y);

        maskable_fl_4.setLayoutParams(new FrameLayout.LayoutParams((int) frame4_width, (int) frame4_height));
        maskable_fl_4.setX(frame4_x);
        maskable_fl_4.setY(frame4_y);

        maskable_fl_5.getLayoutParams().width = (int) frame5_width;
        maskable_fl_5.getLayoutParams().height = (int) frame5_height;
        maskable_fl_5.setX(frame5_x);
        maskable_fl_5.setY(frame5_y);

        maskable_fl_6.getLayoutParams().width = (int) frame6_width;
        maskable_fl_6.getLayoutParams().height = (int) frame6_height;
        maskable_fl_6.setX(frame6_x);
        maskable_fl_6.setY(frame6_y);

    }


    private void initSubFrame_five(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1, ArrayList<DimensionModel> dimen_frame3_2,
                                   MaskableFrameLayout1 maskable_fl_2, ArrayList<DimensionModel> dimen_frame3_3, MaskableFrameLayout2 maskable_fl_3,
                                   ArrayList<DimensionModel> dimen_frame3_4, MaskableFrameLayout3 maskable_fl_4, ArrayList<DimensionModel> dimen_frame3_5, MaskableFrameLayout4 maskable_fl_5) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        frame2_x = dimen_frame3_2.get(0).getX();
        frame2_y = dimen_frame3_2.get(0).getY();
        frame2_width = dimen_frame3_2.get(0).getWidth();
        frame2_height = dimen_frame3_2.get(0).getHeight();

        frame3_x = dimen_frame3_3.get(0).getX();
        frame3_y = dimen_frame3_3.get(0).getY();
        frame3_width = dimen_frame3_3.get(0).getWidth();
        frame3_height = dimen_frame3_3.get(0).getHeight();

        frame4_x = dimen_frame3_4.get(0).getX();
        frame4_y = dimen_frame3_4.get(0).getY();
        frame4_width = dimen_frame3_4.get(0).getWidth();
        frame4_height = dimen_frame3_4.get(0).getHeight();

        frame5_x = dimen_frame3_5.get(0).getX();
        frame5_y = dimen_frame3_5.get(0).getY();
        frame5_width = dimen_frame3_5.get(0).getWidth();
        frame5_height = dimen_frame3_5.get(0).getHeight();

        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

        maskable_fl_2.setLayoutParams(new FrameLayout.LayoutParams((int) frame2_width, (int) frame2_height));
        maskable_fl_2.setX(frame2_x);
        maskable_fl_2.setY(frame2_y);

        maskable_fl_3.setLayoutParams(new FrameLayout.LayoutParams((int) frame3_width, (int) frame3_height));
        maskable_fl_3.setX(frame3_x);
        maskable_fl_3.setY(frame3_y);

        maskable_fl_4.setLayoutParams(new FrameLayout.LayoutParams((int) frame4_width, (int) frame4_height));
        maskable_fl_4.setX(frame4_x);
        maskable_fl_4.setY(frame4_y);

        maskable_fl_5.getLayoutParams().width = (int) frame5_width;
        maskable_fl_5.getLayoutParams().height = (int) frame5_height;
        maskable_fl_5.setX(frame5_x);
        maskable_fl_5.setY(frame5_y);

    }


    private void initSubFrame_four(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1, ArrayList<DimensionModel> dimen_frame3_2,
                                   MaskableFrameLayout1 maskable_fl_2, ArrayList<DimensionModel> dimen_frame3_3, MaskableFrameLayout2 maskable_fl_3,
                                   ArrayList<DimensionModel> dimen_frame3_4, MaskableFrameLayout3 maskable_fl_4) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        frame2_x = dimen_frame3_2.get(0).getX();
        frame2_y = dimen_frame3_2.get(0).getY();
        frame2_width = dimen_frame3_2.get(0).getWidth();
        frame2_height = dimen_frame3_2.get(0).getHeight();

        frame3_x = dimen_frame3_3.get(0).getX();
        frame3_y = dimen_frame3_3.get(0).getY();
        frame3_width = dimen_frame3_3.get(0).getWidth();
        frame3_height = dimen_frame3_3.get(0).getHeight();

        frame4_x = dimen_frame3_4.get(0).getX();
        frame4_y = dimen_frame3_4.get(0).getY();
        frame4_width = dimen_frame3_4.get(0).getWidth();
        frame4_height = dimen_frame3_4.get(0).getHeight();


        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

        maskable_fl_2.setLayoutParams(new FrameLayout.LayoutParams((int) frame2_width, (int) frame2_height));
        maskable_fl_2.setX(frame2_x);
        maskable_fl_2.setY(frame2_y);

        maskable_fl_3.setLayoutParams(new FrameLayout.LayoutParams((int) frame3_width, (int) frame3_height));
        maskable_fl_3.setX(frame3_x);
        maskable_fl_3.setY(frame3_y);

        maskable_fl_4.setLayoutParams(new FrameLayout.LayoutParams((int) frame4_width, (int) frame4_height));
        maskable_fl_4.setX(frame4_x);
        maskable_fl_4.setY(frame4_y);


    }

    private void initSubFrame_three(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1, ArrayList<DimensionModel> dimen_frame3_2,
                                    MaskableFrameLayout1 maskable_fl_2, ArrayList<DimensionModel> dimen_frame3_3, MaskableFrameLayout2 maskable_fl_3) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        frame2_x = dimen_frame3_2.get(0).getX();
        frame2_y = dimen_frame3_2.get(0).getY();
        frame2_width = dimen_frame3_2.get(0).getWidth();
        frame2_height = dimen_frame3_2.get(0).getHeight();

        frame3_x = dimen_frame3_3.get(0).getX();
        frame3_y = dimen_frame3_3.get(0).getY();
        frame3_width = dimen_frame3_3.get(0).getWidth();
        frame3_height = dimen_frame3_3.get(0).getHeight();


        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

        maskable_fl_2.setLayoutParams(new FrameLayout.LayoutParams((int) frame2_width, (int) frame2_height));
        maskable_fl_2.setX(frame2_x);
        maskable_fl_2.setY(frame2_y);

        maskable_fl_3.setLayoutParams(new FrameLayout.LayoutParams((int) frame3_width, (int) frame3_height));
        maskable_fl_3.setX(frame3_x);
        maskable_fl_3.setY(frame3_y);

    }


    private void initSubFrame_two(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1, ArrayList<DimensionModel> dimen_frame3_2,
                                  MaskableFrameLayout1 maskable_fl_2) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        frame2_x = dimen_frame3_2.get(0).getX();
        frame2_y = dimen_frame3_2.get(0).getY();
        frame2_width = dimen_frame3_2.get(0).getWidth();
        frame2_height = dimen_frame3_2.get(0).getHeight();

        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

        maskable_fl_2.setLayoutParams(new FrameLayout.LayoutParams((int) frame2_width, (int) frame2_height));
        maskable_fl_2.setX(frame2_x);
        maskable_fl_2.setY(frame2_y);
    }

    private void initSubFrame_one(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1) {

        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();

        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

    }


    @Override
    protected void onResume() {
        super.onResume();
        System.gc();
        Runtime.getRuntime().gc();
    }

}
