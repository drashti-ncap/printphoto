package com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import com.mobile.cover.photo.editor.back.maker.Commen.DynamicUnitUtils;
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.VideoActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.testing_CaseEditActivity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.mainapplication;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
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
    public static Activity mActivity;
    int position = 0;
    ArrayList<DimensionModel> dimen_frame_1 = new ArrayList<>();
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
    Button button;
    ProgressDialog pd;
    boolean load = false;
    ImageView id_back, btn_add;
    List<Boolean> temp = new ArrayList<>();
    ProgressDialog progress;
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
    private float device_width;
    private float device_height;
    private MaskableFrameLayout13 maskable_fl_14;
    private RelativeLayout rl_main;
    private ImageView iv_subframe_1, iv_subframe_2, iv_subframe_3, iv_subframe_4, iv_subframe_5, iv_subframe_6, iv_subframe_7, iv_subframe_8, iv_subframe_9, iv_subframe_10, iv_subframe_11, iv_subframe_12, iv_subframe_13, iv_subframe_14;

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    public static Bitmap createBitmapFromView(View view, int width, int height) {
        if (width > 0 && height > 0) {
            view.measure(View.MeasureSpec.makeMeasureSpec(DynamicUnitUtils
                            .convertDpToPixels(width), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(DynamicUnitUtils
                            .convertDpToPixels(height), View.MeasureSpec.EXACTLY));
        }
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable background = view.getBackground();

        if (background != null) {
            background.draw(canvas);
        }
        view.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_main);
        position = Integer.parseInt(getIntent().getStringExtra("position"));
        mActivity = MainActivity.this;
        Log.e(TAG, "onCreate: " + position);
//        position = 23;
        findViews();
        temp.clear();
        Display display = getWindowManager().getDefaultDisplay(); // pixels // 1280 720
        Point size = new Point();
        display.getSize(size);
        device_width = size.x;
        device_height = size.y;

        rl_main.getLayoutParams().width = (int) (device_width / 1.35);
        rl_main.getLayoutParams().height = (int) (device_height / 1.5);

        Log.e("WIDTH", "onCreate: " + (device_width / 1.35));
        Log.e("HEIGTH", "onCreate: " + (device_height / 1.5));

        Log.e(TAG, "onCreate: h = " + device_height + "w = " + device_width);

        startMasking();
    }

    private void findViews() {

        fl_1 = findViewById(R.id.fl_1);
        btn_add = findViewById(R.id.btn_add);
        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        iv_frame = findViewById(R.id.iv_frame);

        rl_main = findViewById(R.id.rl_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Log.e(TAG, "onClick: =======>" + temp.size() + "//" + Share.itemnum);
                if (temp.size() == Share.itemnum) {
                    rl_main.setDrawingCacheEnabled(true);
                    rl_main.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    new generate_bitmap().execute();

                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.image_processing), Toast.LENGTH_SHORT).show();
                }
            }
        });

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


        switch (  DataHelperKt.getCaseId(MainActivity.this)) {
            case 1527:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.65)), (int) (device_height - (device_height / 1.186)), (int) (device_width - (device_width / 1.15)), (int) (device_height - (device_height / 1.165))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.65)), (int) (device_height - (device_height / 1.2)), (int) (device_width - (device_width / 1.31)), (int) (device_height - (device_height / 1.45))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width), (int) (device_height - (device_height / 1.25)), (int) (device_width - (device_width)), (int) (device_height - (device_height / 1.88))));//1


                maskable_fl_1.setMask(R.drawable.mask1_1);
                maskable_fl_2.setMask(R.drawable.mask1_1);
                maskable_fl_3.setMask(R.drawable.mask1_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1524:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.38)), (int) (device_height - (device_height / 1.17)), (int) (device_width - (device_width / 1.4)), (int) (device_height - (device_height / 1.175))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.38)), (int) (device_height - (device_height / 1.17)), (int) (device_width - (device_width / 1.125)), (int) (device_height - (device_height / 1.445))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.38)), (int) (device_height - (device_height / 1.17)), (int) (device_width - (device_width / 1.483)), (int) (device_height - (device_height / 1.815))));//1

//                iv_frame.setImageResource(R.drawable.model2);
                maskable_fl_1.setMask(R.drawable.mask2_1);
                maskable_fl_2.setMask(R.drawable.mask2_1);
                maskable_fl_3.setMask(R.drawable.mask2_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1525:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.35)), (int) (device_height - (device_height / 1.17)), (int) (device_width - (device_width / 1.2)), (int) (device_height - (device_height / 1.18))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.3)), (int) (device_height - (device_height / 1.15)), (int) (device_width - (device_width / 1.55)), (int) (device_height - (device_height / 1.45))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.26)), (int) (device_height - (device_height / 1.12)), (int) (device_width - (device_width / 1.2)), (int) (device_height - (device_height / 1.887))));//1

//                iv_frame.setImageResource(R.drawable.model3);
                maskable_fl_1.setMask(R.drawable.mask2_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);
                maskable_fl_3.setMask(R.drawable.mask2_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1526:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.35)), (int) (device_height - (device_height / 1.15)), (int) (device_width - (device_width / 1.340)), (int) (device_height - (device_height / 1.7))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.3)), (int) (device_height - (device_height / 1.17)), (int) (device_width - (device_width / 1.68)), (int) (device_height - (device_height / 1.41))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.35)), (int) (device_height - (device_height / 1.15)), (int) (device_width - (device_width / 1.335)), (int) (device_height - (device_height / 1.235))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 1.29)), (int) (device_height - (device_height / 1.16)), (int) (device_width - (device_width / 1.15)), (int) (device_height - (device_height / 1.42))));//1

//                iv_frame.setImageResource(R.drawable.model4);
                maskable_fl_1.setMask(R.drawable.mask4_1);
                maskable_fl_2.setMask(R.drawable.mask4_2);
                maskable_fl_3.setMask(R.drawable.mask4_3);
                maskable_fl_4.setMask(R.drawable.mask4_4);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1254:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.42)), (int) (device_height - (device_height / 1.185)), (int) (device_width - (device_width / 1.175)), (int) (device_height - (device_height / 1.331))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.17)), (int) (device_height - (device_height / 1.08)), (int) (device_width - (device_width / 1.85)), (int) (device_height - (device_height / 1.33))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.17)), (int) (device_height - (device_height / 1.08)), (int) (device_width - (device_width / 1.85)), (int) (device_height - (device_height / 1.49))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 1.17)), (int) (device_height - (device_height / 1.08)), (int) (device_width - (device_width / 1.85)), (int) (device_height - (device_height / 1.7))));//1
                dimen_frame_5.add(new DimensionModel((int) (device_width - (device_width / 1.17)), (int) (device_height - (device_height / 1.08)), (int) (device_width - (device_width / 1.43)), (int) (device_height - (device_height / 1.7))));//1
                dimen_frame_6.add(new DimensionModel((int) (device_width - (device_width / 1.17)), (int) (device_height - (device_height / 1.08)), (int) (device_width - (device_width / 1.17)), (int) (device_height - (device_height / 1.7))));//1


//                iv_frame.setImageResource(R.drawable.model5);
                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);
                maskable_fl_3.setMask(R.drawable.mask24_1);
                maskable_fl_4.setMask(R.drawable.mask24_1);
                maskable_fl_5.setMask(R.drawable.mask24_1);
                maskable_fl_6.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1523:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.32)), (int) (device_height - (device_height / 1.2)), (int) (device_width - (device_width / 1.2)), (int) (device_height - (device_height / 1.178))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.32)), (int) (device_height - (device_height / 1.21)), (int) (device_width - (device_width / 1.48)), (int) (device_height - (device_height / 1.68))));//1


//                iv_frame.setImageResource(R.drawable.model6);
                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 1256:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.22)), (int) (device_height - (device_height / 1.1)), (int) (device_width - (device_width / 1.19)), (int) (device_height - (device_height / 1.51))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.22)), (int) (device_height - (device_height / 1.1)), (int) (device_width - (device_width / 1.62)), (int) (device_height - (device_height / 1.51))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.22)), (int) (device_height - (device_height / 1.1)), (int) (device_width - (device_width / 1.24)), (int) (device_height - (device_height / 1.825))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 1.22)), (int) (device_height - (device_height / 1.1)), (int) (device_width - (device_width / 1.71)), (int) (device_height - (device_height / 1.825))));//1

//                iv_frame.setImageResource(R.drawable.model7);
                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);
                maskable_fl_3.setMask(R.drawable.mask24_1);
                maskable_fl_4.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1522:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.82)), (int) (device_height - (device_height / 1.18)), (int) (device_width - (device_width / 1.175)), (int) (device_height - (device_height / 1.238))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.25)), (int) (device_height - (device_height / 1.13)), (int) (device_width - (device_width / 1.171)), (int) (device_height - (device_height / 1.57))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.25)), (int) (device_height - (device_height / 1.13)), (int) (device_width - (device_width / 1.642)), (int) (device_height - (device_height / 1.57))));//1

//                iv_frame.setImageResource(R.drawable.model8);
                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);
                maskable_fl_3.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1521:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.35)), (int) (device_height - (device_height / 1.138)), (int) (device_width - (device_width / 1.54)), (int) (device_height - (device_height / 1.161))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.378)), (int) (device_height - (device_height / 1.138)), (int) (device_width - (device_width / 1.14)), (int) (device_height - (device_height / 1.42))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.378)), (int) (device_height - (device_height / 1.138)), (int) (device_width - (device_width / 1.5)), (int) (device_height - (device_height / 1.741))));//1

//                iv_frame.setImageResource(R.drawable.model9);
                maskable_fl_1.setMask(R.drawable.mask9_1);
                maskable_fl_2.setMask(R.drawable.mask9_2);
                maskable_fl_3.setMask(R.drawable.mask9_3);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1259:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.38)), (int) (device_height - (device_height / 1.14)), (int) (device_width - (device_width / 1.18)), (int) (device_height - (device_height / 1.45))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.18)), (int) (device_height - (device_height / 1.147)), (int) (device_width - (device_width / 1.79)), (int) (device_height - (device_height / 1.46))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.18)), (int) (device_height - (device_height / 1.15)), (int) (device_width - (device_width / 1.18)), (int) (device_height - (device_height / 1.718))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 1.375)), (int) (device_height - (device_height / 1.135)), (int) (device_width - (device_width / 1.48)), (int) (device_height - (device_height / 1.75))));//1

//                iv_frame.setImageResource(R.drawable.model10);
                maskable_fl_1.setMask(R.drawable.mask10_1);
                maskable_fl_2.setMask(R.drawable.mask10_2);
                maskable_fl_3.setMask(R.drawable.mask10_3);
                maskable_fl_4.setMask(R.drawable.mask10_4);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1260:

                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.28)), (int) (device_height - (device_height / 1.35)), (int) (device_width - (device_width / 1)), (int) (device_height - (device_height / 1))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 2.1)), (int) (device_height - (device_height / 1.14)), (int) (device_width - (device_width / 1.28)), (int) (device_height - (device_height / 1))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.28)), (int) (device_height - (device_height / 1.215)), (int) (device_width - (device_width / 1)), (int) (device_height - (device_height / 1.35))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 2.1)), (int) (device_height - (device_height / 1.45)), (int) (device_width - (device_width / 1.28)), (int) (device_height - (device_height / 1.142))));//1
                dimen_frame_5.add(new DimensionModel((int) (device_width - (device_width / 1.92)), (int) (device_height - (device_height / 1.289)), (int) (device_width - (device_width / 1)), (int) (device_height - (device_height / 1.78))));//1
                dimen_frame_6.add(new DimensionModel((int) (device_width - (device_width / 1.35)), (int) (device_height - (device_height / 1.15)), (int) (device_width - (device_width / 1.92)), (int) (device_height - (device_height / 1.78))));//1
                dimen_frame_7.add(new DimensionModel((int) (device_width - (device_width / 1.35)), (int) (device_height - (device_height / 1.1)), (int) (device_width - (device_width / 1.92)), (int) (device_height - (device_height / 2.35))));//1

//                iv_frame.setImageResource(R.drawable.model18);
                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);
                maskable_fl_3.setMask(R.drawable.mask24_1);
                maskable_fl_4.setMask(R.drawable.mask24_1);
                maskable_fl_5.setMask(R.drawable.mask24_1);
                maskable_fl_6.setMask(R.drawable.mask24_1);
                maskable_fl_7.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 1261:

                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.355)), (int) (device_height - (device_height / 1.155)), (int) (device_width - (device_width / 1.325)), (int) (device_height - (device_height / 1.225))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.30)), (int) (device_height - (device_height / 1.138)), (int) (device_width - (device_width / 1.15)), (int) (device_height - (device_height / 1.355))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.30)), (int) (device_height - (device_height / 1.138)), (int) (device_width - (device_width / 1.648)), (int) (device_height - (device_height / 1.348))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 1.355)), (int) (device_height - (device_height / 1.155)), (int) (device_width - (device_width / 1.338)), (int) (device_height - (device_height / 1.480))));//1
                dimen_frame_5.add(new DimensionModel((int) (device_width - (device_width / 1.19)), (int) (device_height - (device_height / 1.095)), (int) (device_width - (device_width / 1.206)), (int) (device_height - (device_height / 1.65))));//1

//                iv_frame.setImageResource(R.drawable.model19);
                maskable_fl_1.setMask(R.drawable.mask19_1);
                maskable_fl_2.setMask(R.drawable.mask19_1);
                maskable_fl_3.setMask(R.drawable.mask19_1);
                maskable_fl_4.setMask(R.drawable.mask19_1);
                maskable_fl_5.setMask(R.drawable.mask19_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1262:

                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.10)), (int) (device_height - (device_height / 1.128)), (int) (device_width - (device_width / 1.185)), (int) (device_height - (device_height / 1.168))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.13)), (int) (device_height - (device_height / 1.07)), (int) (device_width - (device_width / 1.370)), (int) (device_height - (device_height / 1.17))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.22)), (int) (device_height - (device_height / 1.07)), (int) (device_width - (device_width / 1.380)), (int) (device_height - (device_height / 1.283))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 1.13)), (int) (device_height - (device_height / 1.072)), (int) (device_width - (device_width / 1.54)), (int) (device_height - (device_height / 1.425))));//1
                dimen_frame_5.add(new DimensionModel((int) (device_width - (device_width / 1.10)), (int) (device_height - (device_height / 1.128)), (int) (device_width - (device_width / 1.95)), (int) (device_height - (device_height / 1.335))));//1
                dimen_frame_6.add(new DimensionModel((int) (device_width - (device_width / 1.10)), (int) (device_height - (device_height / 1.128)), (int) (device_width - (device_width / 1.185)), (int) (device_height - (device_height / 1.558))));//1
                dimen_frame_7.add(new DimensionModel((int) (device_width - (device_width / 1.13)), (int) (device_height - (device_height / 1.07)), (int) (device_width - (device_width / 1.370)), (int) (device_height - (device_height / 1.580))));//1
                dimen_frame_8.add(new DimensionModel((int) (device_width - (device_width / 1.22)), (int) (device_height - (device_height / 1.07)), (int) (device_width - (device_width / 1.380)), (int) (device_height - (device_height / 1.78))));//1
                dimen_frame_9.add(new DimensionModel((int) (device_width - (device_width / 1.13)), (int) (device_height - (device_height / 1.07)), (int) (device_width - (device_width / 1.54)), (int) (device_height - (device_height / 2.03))));//1
                dimen_frame_10.add(new DimensionModel((int) (device_width - (device_width / 1.10)), (int) (device_height - (device_height / 1.128)), (int) (device_width - (device_width / 1.95)), (int) (device_height - (device_height / 1.876))));//1

//                iv_frame.setImageResource(R.drawable.model21);
                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);
                maskable_fl_3.setMask(R.drawable.mask24_1);
                maskable_fl_4.setMask(R.drawable.mask24_1);
                maskable_fl_5.setMask(R.drawable.mask24_1);
                maskable_fl_6.setMask(R.drawable.mask24_1);
                maskable_fl_7.setMask(R.drawable.mask24_1);
                maskable_fl_8.setMask(R.drawable.mask24_1);
                maskable_fl_9.setMask(R.drawable.mask24_1);
                maskable_fl_10.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);

                break;

            case 1263:

                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.21)), (int) (device_height - (device_height / 1.095)), (int) (device_width - (device_width / 1.1999)), (int) (device_height - (device_height / 1.25))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.20)), (int) (device_height - (device_height / 1.095)), (int) (device_width - (device_width / 1.44)), (int) (device_height - (device_height / 1.24))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.20)), (int) (device_height - (device_height / 1.095)), (int) (device_width - (device_width / 1.758)), (int) (device_height - (device_height / 1.32))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 1.20)), (int) (device_height - (device_height / 1.095)), (int) (device_width - (device_width / 1.178)), (int) (device_height - (device_height / 1.40))));//1
                dimen_frame_5.add(new DimensionModel((int) (device_width - (device_width / 1.20)), (int) (device_height - (device_height / 1.095)), (int) (device_width - (device_width / 1.38)), (int) (device_height - (device_height / 1.396))));//1
                dimen_frame_6.add(new DimensionModel((int) (device_width - (device_width / 1.20)), (int) (device_height - (device_height / 1.095)), (int) (device_width - (device_width / 1.748)), (int) (device_height - (device_height / 1.46))));//1
                dimen_frame_7.add(new DimensionModel((int) (device_width - (device_width / 1.16)), (int) (device_height - (device_height / 1.085)), (int) (device_width - (device_width / 1.278)), (int) (device_height - (device_height / 1.58))));//1
                dimen_frame_8.add(new DimensionModel((int) (device_width - (device_width / 1.16)), (int) (device_height - (device_height / 1.085)), (int) (device_width - (device_width / 1.568)), (int) (device_height - (device_height / 1.62))));//1

//                iv_frame.setImageResource(R.drawable.model22);
                maskable_fl_1.setMask(R.drawable.mask22_1);
                maskable_fl_2.setMask(R.drawable.mask22_2);
                maskable_fl_3.setMask(R.drawable.mask22_3);
                maskable_fl_4.setMask(R.drawable.mask22_4);
                maskable_fl_5.setMask(R.drawable.mask22_5);
                maskable_fl_6.setMask(R.drawable.mask22_6);
                maskable_fl_7.setMask(R.drawable.mask22_7);
                maskable_fl_8.setMask(R.drawable.mask22_8);

                fl_1.setVisibility(View.VISIBLE);

                break;


            case 1264:

                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.21)), (int) (device_width - (device_width / 1.01)), (int) (device_height - (device_height / 1.01))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.21)), (int) (device_width - (device_width / 1.01)), (int) (device_height - (device_height / 1.237))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.21)), (int) (device_width - (device_width / 1.01)), (int) (device_height - (device_height / 1.595))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.12)), (int) (device_width - (device_width / 1.01)), (int) (device_height - (device_height / 2.23))));//1
                dimen_frame_5.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.145)), (int) (device_width - (device_width / 1.34)), (int) (device_height - (device_height / 1.008))));//1
                dimen_frame_6.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.145)), (int) (device_width - (device_width / 1.34)), (int) (device_height - (device_height / 1.162))));//1
                dimen_frame_7.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.145)), (int) (device_width - (device_width / 1.34)), (int) (device_height - (device_height / 1.375))));//1
                dimen_frame_8.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.145)), (int) (device_width - (device_width / 1.34)), (int) (device_height - (device_height / 1.67))));//1
                dimen_frame_9.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.145)), (int) (device_width - (device_width / 1.34)), (int) (device_height - (device_height / 2.15))));//1
                dimen_frame_10.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.06)), (int) (device_width - (device_width / 1.98)), (int) (device_height - (device_height / 1.008))));//1
                dimen_frame_11.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.138)), (int) (device_width - (device_width / 1.98)), (int) (device_height - (device_height / 1.075))));//1
                dimen_frame_12.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.21)), (int) (device_width - (device_width / 1.98)), (int) (device_height - (device_height / 1.245))));//1
                dimen_frame_13.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.13)), (int) (device_width - (device_width / 1.98)), (int) (device_height - (device_height / 1.6))));//1
                dimen_frame_14.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.2)), (int) (device_width - (device_width / 1.98)), (int) (device_height - (device_height / 1.98))));//1


//                iv_frame.setImageResource(R.drawable.model24);
                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);
                maskable_fl_3.setMask(R.drawable.mask24_1);
                maskable_fl_4.setMask(R.drawable.mask24_1);
                maskable_fl_5.setMask(R.drawable.mask24_1);
                maskable_fl_6.setMask(R.drawable.mask24_1);
                maskable_fl_7.setMask(R.drawable.mask24_1);
                maskable_fl_8.setMask(R.drawable.mask24_1);
                maskable_fl_9.setMask(R.drawable.mask24_1);
                maskable_fl_10.setMask(R.drawable.mask24_1);
                maskable_fl_11.setMask(R.drawable.mask24_1);
                maskable_fl_12.setMask(R.drawable.mask24_1);
                maskable_fl_13.setMask(R.drawable.mask24_1);
                maskable_fl_14.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1265:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.121)), (int) (device_width - (device_width / 1.14)), (int) (device_height - (device_height / 1.208))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.121)), (int) (device_width - (device_width / 1.59)), (int) (device_height - (device_height / 1.208))));//1
                dimen_frame_3.add(new DimensionModel((int) (device_width - (device_width / 1.58)), (int) (device_height - (device_height / 1.24)), (int) (device_width - (device_width / 1.255)), (int) (device_height - (device_height / 1.343))));//1
                dimen_frame_4.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.121)), (int) (device_width - (device_width / 1.17)), (int) (device_height - (device_height / 1.76))));//1
                dimen_frame_5.add(new DimensionModel((int) (device_width - (device_width / 1.315)), (int) (device_height - (device_height / 1.121)), (int) (device_width - (device_width / 1.59)), (int) (device_height - (device_height / 1.76))));//1

//                iv_frame.setImageResource(R.drawable.model25);
                maskable_fl_1.setMask(R.drawable.mask25_1);
                maskable_fl_2.setMask(R.drawable.mask25_2);
                maskable_fl_3.setMask(R.drawable.mask25_3);
                maskable_fl_4.setMask(R.drawable.mask25_4);
                maskable_fl_5.setMask(R.drawable.mask25_5);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1528:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1
                maskable_fl_1.setMask(R.drawable.mask9_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1529:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1530:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1520:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 3.5)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width)), (int) (device_height - (device_height / 1.25))));//1
                maskable_fl_1.setMask(R.drawable.mask19_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1519:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 2.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width)), (int) (device_height - (device_height / 1.25))));//1
                maskable_fl_1.setMask(R.drawable.mask2_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1531:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.4)), (int) (device_height - (device_height / 1.32)), (int) (device_width - (device_width / 1.459)), (int) (device_height - (device_height / 1.27))));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1532:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1533:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 2)), (int) (device_height - (device_height / 1.25)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 1.55)), (int) (device_height - (device_height / 1.25)), (int) (device_width - (device_width / 1.2)), (int) (device_height - (device_height / 1.7))));//1

                maskable_fl_1.setMask(R.drawable.mask2_2);
                maskable_fl_2.setMask(R.drawable.mask2_2);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1534:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1

                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1535:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1536:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1

                maskable_fl_1.setMask(R.drawable.mask13_1);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1537:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1
                maskable_fl_1.setMask(R.drawable.mask4_3);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1538:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1

                maskable_fl_1.setMask(R.drawable.mask4_3);

                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1539:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 1.7)), (int) (device_height - (device_height / 1.805)), (int) (device_width - (device_width / 1.205)), (int) (device_height - (device_height / 1.25))));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);

                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1540:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 2.1)), (int) (device_height - (device_height / 1.905)), (int) (device_width - (device_width / 1.105)), (int) (device_height - (device_height / 1.15))));//1
                maskable_fl_1.setMask(R.drawable.mask2_1);
                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1541:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 2.1)), (int) (device_height - (device_height / 1.905)), (int) (device_width - (device_width / 1.105)), (int) (device_height - (device_height / 1.15))));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);
                fl_1.setVisibility(View.VISIBLE);
                break;
            case 1542:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 2.1)), (int) (device_height - (device_height / 1.205)), (int) (device_width - (device_width / 1.105)), (int) (device_height - (device_height / 1.33))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 2.1)), (int) (device_height - (device_height / 1.205)), (int) (device_width - (device_width / 1.105)), (int) (device_height - (device_height / 1.75))));//1
                maskable_fl_1.setMask(R.drawable.mask24_1);
                maskable_fl_2.setMask(R.drawable.mask24_1);
                fl_1.setVisibility(View.VISIBLE);
                break;

            case 1543:
                dimen_frame_1.add(new DimensionModel((int) (device_width - (device_width / 2.1)), (int) (device_height - (device_height / 1.205)), (int) (device_width - (device_width / 1.105)), (int) (device_height - (device_height / 1.29))));//1
                dimen_frame_2.add(new DimensionModel((int) (device_width - (device_width / 2.1)), (int) (device_height - (device_height / 1.305)), (int) (device_width - (device_width / 1.105)), (int) (device_height - (device_height / 1.65))));//1
                maskable_fl_1.setMask(R.drawable.mask_fancy);
                maskable_fl_2.setMask(R.drawable.mask_fancy);
                fl_1.setVisibility(View.VISIBLE);
                break;
        }
        setMaskImage();
    }

    public Bitmap getResizedBitmapCover(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
    }

    Bitmap CropBitmapTransparency(Bitmap sourceBitmap) {
        int minX = sourceBitmap.getWidth();
        int minY = sourceBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < sourceBitmap.getHeight(); y++) {
            for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                int alpha = (sourceBitmap.getPixel(x, y) >> 24) & 255;
                if (alpha > 0)   // pixel is not 100% transparent
                {
                    if (x < minX)
                        minX = x;
                    if (x > maxX)
                        maxX = x;
                    if (y < minY)
                        minY = y;
                    if (y > maxY)
                        maxY = y;
                }
            }
        }
        if ((maxX < minX) || (maxY < minY))
            return null; // Bitmap is entirely transparent

        // crop bitmap to non-transparent area and return:
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, float newWidth, float newHeight) {
        System.gc();
        Bitmap resizedBitmap = Bitmap.createBitmap((int) newWidth, (int) newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
//        scaleMatrix.setRotate(90);
//        scaleMatrix.postRotate(270);
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

//        scaleMatrix.postRotate(90,newWidth/2,newHeight/2);
        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(scaleMatrix);
//        canvas.rotate(90);
        canvas.drawBitmap(bitmap, 0, 0, new Paint());

        return resizedBitmap;
    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap new_returnedBitmap = Bitmap.createBitmap(view.getWidth() * 2, view.getHeight() * 2, Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(returnedBitmap, 0, 0, null);
//        Drawable bgDrawable =view.getBackground();
        canvas.drawBitmap(view.getDrawingCache(), 0, 0, paint);
        paint.setXfermode(null);
        //Get the view's background
//        if (bgDrawable!=null) {
//            //has background drawable, then draw it on the canvas
//            bgDrawable.draw(canvas);
//        }   else{
//            //does not have background drawable, then draw white background on the canvas
//            canvas.drawColor(Color.WHITE);
//        }
//        // draw the view on the canvas
//        view.draw(canvas);
//        //return the bitmap
        return returnedBitmap;
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
                Log.e(TAG, "onGlobalLayout:=================> " +  DataHelperKt.getCaseId(MainActivity.this));
                switch ( DataHelperKt.getCaseId(MainActivity.this)) {
                    case 1527:
                        initSubFrame_three(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3);
//                        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
//                        iv_subframe_1.setImageBitmap(bmp1);
//                        iv_subframe_2.setImageBitmap(bmp1);
//                        iv_subframe_3.setImageBitmap(bmp1);

                        setimage();
                        break;

                    case 1524:
                        initSubFrame_three(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3);
                        setimage();
                        break;

                    case 1525:
                        initSubFrame_three(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3);
                        setimage();
                        break;

                    case 1526:
                        initSubFrame_four(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4);
                        setimage();
                        break;

                    case 1254:
                        initSubFrame_six(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4, dimen_frame_5, maskable_fl_5, dimen_frame_6, maskable_fl_6);
                        setimage();
                        break;

                    case 1523:
                        initSubFrame_two(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2);
                        setimage();
                        break;

                    case 1256:
                        initSubFrame_four(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4);
                        setimage();
                        break;

                    case 1522:
                        initSubFrame_three(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3);
                        setimage();
                        break;

                    case 1521:
                        initSubFrame_three(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3);
                        setimage();
                        break;

                    case 1259:
                        initSubFrame_four(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4);
                        setimage();
                        break;

                    case 1260:

                        initSubFrame_seven(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4, dimen_frame_5, maskable_fl_5, dimen_frame_6, maskable_fl_6,
                                dimen_frame_7, maskable_fl_7);
                        setimage();

                        break;
                    case 1261:
                        initSubFrame_five(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4, dimen_frame_5, maskable_fl_5);
                        setimage();

                        break;


                    case 1262:
                        initSubFrame_ten(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4, dimen_frame_5, maskable_fl_5, dimen_frame_6, maskable_fl_6,
                                dimen_frame_7, maskable_fl_7,
                                dimen_frame_8, maskable_fl_8,
                                dimen_frame_9, maskable_fl_9,
                                dimen_frame_10, maskable_fl_10);
                        setimage();

                        break;

                    case 1263:

                        initSubFrame_eight(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4, dimen_frame_5, maskable_fl_5, dimen_frame_6, maskable_fl_6,
                                dimen_frame_7, maskable_fl_7,
                                dimen_frame_8, maskable_fl_8);
                        setimage();

                        break;

                    case 1264:

                        initSubFrame_fourteen(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4, dimen_frame_5, maskable_fl_5, dimen_frame_6, maskable_fl_6,
                                dimen_frame_7, maskable_fl_7,
                                dimen_frame_8, maskable_fl_8,
                                dimen_frame_9, maskable_fl_9,
                                dimen_frame_10, maskable_fl_10,
                                dimen_frame_11, maskable_fl_11,
                                dimen_frame_12, maskable_fl_12,
                                dimen_frame_13, maskable_fl_13,
                                dimen_frame_14, maskable_fl_14);
                        setimage();

                        break;

                    case 1265:

                        initSubFrame_five(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2, dimen_frame_3, maskable_fl_3, dimen_frame_4, maskable_fl_4, dimen_frame_5, maskable_fl_5);
                        setimage();
                        break;


                    //////New
                    case 1528:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1529:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1530:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1520:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1519:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1531:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1532:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1533:

                        initSubFrame_two(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2);
                        setimage();
                        break;
                    case 1534:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1535:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1536:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1537:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1538:

                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1539:
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1540:
                        Log.e(TAG, "onGlobalLayout: 1");
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1541:
                        Log.e(TAG, "onGlobalLayout: 2");
                        initSubFrame_one(dimen_frame_1, maskable_fl_1);
                        setimage();
                        break;
                    case 1542:
                        Log.e(TAG, "onGlobalLayout: 3");
                        initSubFrame_two(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2);
                        setimage();
                        break;
                    case 1543:
                        Log.e(TAG, "onGlobalLayout: 4");
                        initSubFrame_two(dimen_frame_1, maskable_fl_1, dimen_frame_2, maskable_fl_2);
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

    private void initSubFrame_one(ArrayList<DimensionModel> dimen_frame3_1, MaskableFrameLayout maskable_fl_1) {


        frame1_x = dimen_frame3_1.get(0).getX();
        frame1_y = dimen_frame3_1.get(0).getY();
        frame1_width = dimen_frame3_1.get(0).getWidth();
        frame1_height = dimen_frame3_1.get(0).getHeight();
        Log.e(TAG, "initSubFrame_one: " + frame1_x + "====>" + frame1_y + "====>" + frame1_width + "=====>" + frame1_height);

        maskable_fl_1.setLayoutParams(new FrameLayout.LayoutParams((int) frame1_width, (int) frame1_height));
        maskable_fl_1.setX(frame1_x);
        maskable_fl_1.setY(frame1_y);

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

    @Override
    protected void onResume() {
        super.onResume();
        System.gc();
        Runtime.getRuntime().gc();
    }

    private void setimage() {

        Log.e(TAG, "setimage: " + mainapplication.org_selectedImages.size());
        for (int i = 0; i < mainapplication.org_selectedImages.size(); i++) {
            if (i == 0) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        Log.e(TAG, "onResourceReady: ====>imageset");
                        iv_subframe_1.setImageBitmap(resource);
                    }
                });
            }
            if (i == 1) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_2.setImageBitmap(resource);
                    }
                });
            }
            if (i == 2) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_3.setImageBitmap(resource);
                    }
                });
            }
            if (i == 3) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_4.setImageBitmap(resource);
                    }
                });
            }
            if (i == 4) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_5.setImageBitmap(resource);
                    }
                });
            }
            if (i == 5) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_6.setImageBitmap(resource);
                    }
                });
            }
            if (i == 6) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_7.setImageBitmap(resource);
                    }
                });
            }
            if (i == 7) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_8.setImageBitmap(resource);
                    }
                });
            }
            if (i == 8) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_9.setImageBitmap(resource);
                    }
                });
            }
            if (i == 9) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_10.setImageBitmap(resource);
                    }
                });
            }
            if (i == 10) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_11.setImageBitmap(resource);
                    }
                });
            }
            if (i == 11) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_12.setImageBitmap(resource);
                    }
                });
            }
            if (i == 12) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_13.setImageBitmap(resource);
                    }
                });
            }
            if (i == 13) {
                Glide.with(MainActivity.this).asBitmap().load(mainapplication.org_selectedImages.get(i).getImagePath()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        temp.add(true);
                        iv_subframe_14.setImageBitmap(resource);
                    }
                });
            }
        }


    }

    public class generate_bitmap extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress = ProgressDialog.show(MainActivity.this, "", getString(R.string.loading), true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();

            model_details_data modelData = DataHelperKt.getModelData(MainActivity.this);

            if (Share.bitmap_testing != null) {
                Intent intent = new Intent(MainActivity.this, testing_CaseEditActivity.class);
                intent.putExtra("model_name", modelData.getModalName());
                intent.putExtra("model_id", "" + modelData.getModelId());
                intent.putExtra("user_id", SharedPrefs.getString(MainActivity.this, Share.key_ + RegReq.id));
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
                startActivity(intent);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap bitmap = getBitmapFromView(rl_main);

//            String root = Environment.getExternalStorageDirectory().toString();
//            File myDir = new File(root);
//            myDir.mkdirs();
//            Random generator = new Random();
//            int n = 10000;
//            n = generator.nextInt(n);
//            String fname = "Image-"+ n +".png";
//            File file = new File (myDir, fname);
//            if (file.exists ()) file.delete ();
//            try {
//                FileOutputStream out = new FileOutputStream(file);
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                out.flush();
//                out.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            bitmap = getResizedBitmap(bitmap, Share.maskwidth * 1000, Share.maskheight * 1000);
//                bitmap=Bitmap.createScaledBitmap(bitmap,1400*2,2489*2,true);
            Share.bitmap_testing = bitmap;
            rl_main.setDrawingCacheEnabled(false);
            return null;
        }
    }

}
