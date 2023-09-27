package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.getcolor_Adapter;
import com.mobile.cover.photo.editor.back.maker.customView.MaskableFrameLayout;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.DrawableSticker;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.Sticker;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.back_StickerView;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mvc.imagepicker.ImagePicker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.mobile.cover.photo.editor.back.maker.Commen.Share.drawables_sticker;
import static com.mobile.cover.photo.editor.back.maker.Commen.Share.upload;
import static com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView.mStickers;

public class Tshirt_Editing_Activity extends AppCompatActivity implements View.OnClickListener {
    public static int width;
    public static int height;
    public static Activity activity;
    public static StickerView stickerView;
    public static back_StickerView id__back_stickerview;
    Bitmap msk = null, bground = null, up_image = null;
    int selectedColor = Color.parseColor("#ffffff");
    LinearLayout id_add_photo;
    Button btn_half, btn_full, btn_color, btn_s_size, btn_m_size, btn_l_size, btn_xl_size, btn_xxl_size, btn_formal, btn_sports;
    ImageView iv_size_chart, id_back, iv_help, save_image;
    LinearLayout ll_select_sleeve_color, ll_tshirt_color, ll_front_back, ll_tshirt_display, ll_edit_button_view, ll_edit_view, id_add_photo_edit;
    RelativeLayout ll_sports, ll_formal;
    RecyclerView rv_color;
    ImageView up, up_back, iv_front, iv_back, iv_back_tshirt, background, background_back, iv_default_image, background_edit, background_edit_back, save;
    List<String> color = new ArrayList<>();
    getcolor_Adapter mAdapter;
    RelativeLayout savelayout, new_savelayout;
    MaskableFrameLayout maskable_fl3_1_1;
    private int PICK_IMAGE_REQUEST = 101;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetRightOut_mask;
    private AnimatorSet mSetLeftIn;
    private AnimatorSet mSetLeftIn_mask;
    private float device_width;
    private float device_height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt__editing_);
        activity = Tshirt_Editing_Activity.this;
        Share.back_print = 0;
        getDisplaySize();
        findviews();
        loadAnimations();
        changeCameraDistance();
        clicklistener();
        setHeader();
        intView();
    }

    private void findviews() {
        maskable_fl3_1_1 = findViewById(R.id.maskable);
        stickerView = findViewById(R.id.id_stickerview);
        id__back_stickerview = findViewById(R.id.id__back_stickerview);
        StickerView.mStickers.clear();
        back_StickerView.mStickers.clear();


        rv_color = findViewById(R.id.rv_color);
        background_edit = findViewById(R.id.background_edit);
        background_edit_back = findViewById(R.id.background_edit_back);
        id_add_photo_edit = findViewById(R.id.id_add_photo_edit);
        ll_edit_view = findViewById(R.id.ll_edit_view);
        ll_edit_button_view = findViewById(R.id.ll_edit_button_view);
        save = findViewById(R.id.save);

        up = findViewById(R.id.up);
        up_back = findViewById(R.id.up_back);
        background = findViewById(R.id.background);
//        background.setX(up.getX() - background.getWidth() / 3);
//        background.setY(up.getY() - background.getHeight() / 3);
        background_back = findViewById(R.id.background_back);
        save_image = findViewById(R.id.save_image);

        up_image = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.tshirt_front_model)).getBitmap();
        bground = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.tshirt_afour)).getBitmap();
        msk = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.tshirt_afour)).getBitmap();


        iv_default_image = findViewById(R.id.iv_default_image);
        savelayout = findViewById(R.id.savelayout);
        savelayout.getLayoutParams().height = (int) (Share.screenHeight / 0.6);
        savelayout.getLayoutParams().width = (int) (Share.screenWidth / 0.9);


        new_savelayout = findViewById(R.id.new_savelayout);
        id_add_photo = findViewById(R.id.id_add_photo);

        iv_back_tshirt = findViewById(R.id.iv_back_tshirt);

        color.add(String.valueOf(Color.WHITE));
        color.add(String.valueOf(Color.BLACK));
        color.add(String.valueOf(Color.BLUE));
        color.add(String.valueOf(Color.RED));
        color.add(String.valueOf(Color.YELLOW));
        color.add(String.valueOf(Color.GREEN));
        color.add(String.valueOf(Color.LTGRAY));
        mAdapter = new getcolor_Adapter(getApplicationContext(), color);
        rv_color.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_color.setItemAnimator(new DefaultItemAnimator());
        rv_color.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickLister(View v, int position) {
                Log.e("CLICKED", "onItemClickLister: " + position);
                Drawable mDrawable;
                if (Share.islogo == 1) {
                    mDrawable = getApplicationContext().getResources().getDrawable(R.drawable.tshirt_front_logo_model);
                } else {
                    mDrawable = getApplicationContext().getResources().getDrawable(R.drawable.tshirt_front_model);
                }
                Drawable mDrawable_back = getApplicationContext().getResources().getDrawable(R.drawable.back_model);
                if (color.get(position).equalsIgnoreCase(String.valueOf(Color.BLACK))) {
                    mDrawable.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY));
                    mDrawable_back.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY));
                } else if (color.get(position).equalsIgnoreCase(String.valueOf(Color.BLUE))) {
                    mDrawable.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY));
                    mDrawable_back.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY));
                } else if (color.get(position).equalsIgnoreCase(String.valueOf(Color.YELLOW))) {
                    mDrawable.setColorFilter(new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY));
                    mDrawable_back.setColorFilter(new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY));
                } else if (color.get(position).equalsIgnoreCase(String.valueOf(Color.RED))) {
                    mDrawable.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY));
                    mDrawable_back.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY));
                } else if (color.get(position).equalsIgnoreCase(String.valueOf(Color.GREEN))) {
                    mDrawable.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY));
                    mDrawable_back.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY));
                } else if (color.get(position).equalsIgnoreCase(String.valueOf(Color.LTGRAY))) {
                    mDrawable.setColorFilter(new PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY));
                    mDrawable_back.setColorFilter(new PorterDuffColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY));
                } else {
                    mDrawable.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY));
                    mDrawable_back.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY));
                }

                up.setImageDrawable(mDrawable);
                up_back.setImageDrawable(mDrawable_back);

                ll_select_sleeve_color.setVisibility(View.VISIBLE);
                ll_tshirt_color.setVisibility(View.GONE);
            }
        });

        btn_half = findViewById(R.id.btn_half);
        btn_full = findViewById(R.id.btn_full);
        btn_color = findViewById(R.id.btn_color);
        btn_s_size = findViewById(R.id.btn_s_size);
        btn_m_size = findViewById(R.id.btn_m_size);
        btn_l_size = findViewById(R.id.btn_l_size);
        btn_xl_size = findViewById(R.id.btn_xl_size);
        btn_xxl_size = findViewById(R.id.btn_xxl_size);
        btn_xxl_size = findViewById(R.id.btn_xxl_size);
        btn_formal = findViewById(R.id.btn_formal);
        btn_sports = findViewById(R.id.btn_sports);

        iv_size_chart = findViewById(R.id.iv_size_chart);
        id_back = findViewById(R.id.id_back);
        iv_help = findViewById(R.id.iv_help);

        ll_sports = findViewById(R.id.ll_sports);
        ll_formal = findViewById(R.id.ll_formal);

        ll_select_sleeve_color = findViewById(R.id.ll_select_sleeve_color);
        ll_tshirt_color = findViewById(R.id.ll_tshirt_color);
        ll_front_back = findViewById(R.id.ll_front_back);
        ll_tshirt_display = findViewById(R.id.ll_tshirt_display);

        iv_front = findViewById(R.id.iv_front);
        iv_back = findViewById(R.id.iv_back);
//        set_frontside();
        settype(getIntent().getStringExtra("type"));
    }


    private void settype(String type) {
        if (type.equalsIgnoreCase("front")) {
//            setdisplay_dimens();
            Share.islogo = 0;
            ll_front_back.setVisibility(View.GONE);
            id__back_stickerview.setVisibility(View.GONE);
            background_back.setVisibility(View.GONE);
            up_back.setVisibility(View.GONE);
            up.setImageBitmap(up_image);
            up.setVisibility(View.VISIBLE);
            background.setImageBitmap(bground);
            background.getLayoutParams().height = Share.screenHeight - (int) (Share.screenHeight / 1.47);
            background.getLayoutParams().width = Share.screenWidth - (int) (Share.screenWidth / 1.53);
            maskable_fl3_1_1.setMask(new BitmapDrawable(msk));
        } else if (type.equalsIgnoreCase("back")) {
            Share.islogo = 0;
            ll_front_back.setVisibility(View.GONE);
            background_back.setVisibility(View.GONE);
            id__back_stickerview.setVisibility(View.GONE);
            up_back.setVisibility(View.GONE);
            up_image = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.back_model)).getBitmap();
            up.setImageBitmap(up_image);
            background.setImageBitmap(bground);
            maskable_fl3_1_1.setMask(new BitmapDrawable(msk));

            background.getLayoutParams().height = Share.screenHeight - (int) (Share.screenHeight / 1.47);
            background.getLayoutParams().width = Share.screenWidth - (int) (Share.screenWidth / 1.53);
        } else if (type.equalsIgnoreCase("front_back")) {
            Share.islogo = 0;
            ll_front_back.setVisibility(View.VISIBLE);
            background_back.setVisibility(View.VISIBLE);
            up_back.setVisibility(View.VISIBLE);
            background.setVisibility(View.VISIBLE);
            up.setVisibility(View.VISIBLE);
            id__back_stickerview.setVisibility(View.VISIBLE);
            up.setImageBitmap(up_image);
            up_image = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.back_model)).getBitmap();
            up_back.setImageBitmap(up_image);
            background.setImageBitmap(bground);
            background_back.setImageBitmap(bground);
            maskable_fl3_1_1.setMask(new BitmapDrawable(msk));

            background.getLayoutParams().height = Share.screenHeight - (int) (Share.screenHeight / 1.47);
            background.getLayoutParams().width = Share.screenWidth - (int) (Share.screenWidth / 1.53);

            background_back.getLayoutParams().height = Share.screenHeight - (int) (Share.screenHeight / 1.47);
            background_back.getLayoutParams().width = Share.screenWidth - (int) (Share.screenWidth / 1.53);
        } else if (type.equalsIgnoreCase("back_logo")) {
            Share.islogo = 1;
            ll_front_back.setVisibility(View.VISIBLE);
            background_back.setVisibility(View.VISIBLE);
            up_back.setVisibility(View.VISIBLE);
            background.setVisibility(View.VISIBLE);
            id__back_stickerview.setVisibility(View.VISIBLE);
            up.setVisibility(View.VISIBLE);
            up_image = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.tshirt_front_logo_model)).getBitmap();
            up.setImageBitmap(up_image);
            up_image = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.back_model)).getBitmap();
            up_back.setImageBitmap(up_image);
            bground = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.logo_mask)).getBitmap();
            background.setImageBitmap(bground);

            background.setX((float) (Share.screenWidth - Share.screenWidth / 1.1581));
            background.setY((float) (Share.screenHeight - Share.screenHeight / 0.8735));

            Log.e("HEIGTH_WIDTH", "settype: " + (Share.screenHeight - (int) (Share.screenHeight / 1.16)));
            Log.e("HEIGTH_WIDTH", "settype: " + (Share.screenWidth - (int) (Share.screenWidth / 1.26)));
            background.getLayoutParams().height = Share.screenHeight - (int) (Share.screenHeight / 1.16);
            background.getLayoutParams().width = Share.screenWidth - (int) (Share.screenWidth / 1.26);


            background_back.getLayoutParams().height = Share.screenHeight - (int) (Share.screenHeight / 1.47);
            background_back.getLayoutParams().width = Share.screenWidth - (int) (Share.screenWidth / 1.53);
            bground = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.tshirt_afour)).getBitmap();
            background_back.setImageBitmap(bground);
            maskable_fl3_1_1.setMask(new BitmapDrawable(msk));
        } else if (type.equalsIgnoreCase("logo_only")) {
            Share.islogo = 1;
            id_add_photo.setVisibility(View.GONE);
            ll_front_back.setVisibility(View.GONE);
            id__back_stickerview.setVisibility(View.GONE);
            background_back.setVisibility(View.GONE);
            up_back.setVisibility(View.GONE);
            up_image = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.tshirt_front_logo_model)).getBitmap();
            bground = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.logo_mask)).getBitmap();
            up.setImageBitmap(up_image);
            background.setImageResource(R.drawable.logo_mask);
            background.setX((float) (Share.screenWidth - Share.screenWidth / 1.1581));
            background.setY((float) (Share.screenHeight - Share.screenHeight / 0.8735));

            Log.e("HEIGTH_WIDTH", "settype: " + (Share.screenHeight - (int) (Share.screenHeight / 1.16)));
            Log.e("HEIGTH_WIDTH", "settype: " + (Share.screenWidth - (int) (Share.screenWidth / 1.26)));
            background.getLayoutParams().height = Share.screenHeight - (int) (Share.screenHeight / 1.16);
            background.getLayoutParams().width = Share.screenWidth - (int) (Share.screenWidth / 1.26);
            maskable_fl3_1_1.setMask(new BitmapDrawable(msk));
        }
    }

    private void clicklistener() {
        btn_half.setOnClickListener(this);
        btn_full.setOnClickListener(this);
        btn_color.setOnClickListener(this);
        btn_s_size.setOnClickListener(this);
        btn_m_size.setOnClickListener(this);
        btn_l_size.setOnClickListener(this);
        btn_xl_size.setOnClickListener(this);
        btn_xxl_size.setOnClickListener(this);
        btn_formal.setOnClickListener(this);
        btn_sports.setOnClickListener(this);

        iv_size_chart.setOnClickListener(this);
        id_back.setOnClickListener(this);
        iv_help.setOnClickListener(this);
        iv_default_image.setOnClickListener(this);

        ll_sports.setOnClickListener(this);
        ll_formal.setOnClickListener(this);
        id_add_photo_edit.setOnClickListener(this);

        iv_back.setOnClickListener(this);
        iv_front.setOnClickListener(this);
        iv_back_tshirt.setOnClickListener(this);
        id_add_photo.setOnClickListener(this);
        save_image.setOnClickListener(this);
        save.setOnClickListener(this);
        background.setOnClickListener(this);
        background_back.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.background_back) {
            AlertDialog.Builder alertDialog_edit_back = new AlertDialog.Builder(activity);
            alertDialog_edit_back.setTitle("Alert");
            alertDialog_edit_back.setCancelable(false);
            alertDialog_edit_back.setMessage("Want to edit the Image ?");
            alertDialog_edit_back.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    visible_view_editing(v);
                }
            });
            alertDialog_edit_back.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog_edit_back.show();
        } else if (id == R.id.background) {
            AlertDialog.Builder alertDialog_edit = new AlertDialog.Builder(activity);
            alertDialog_edit.setTitle("Alert");
            alertDialog_edit.setCancelable(false);
            alertDialog_edit.setMessage("Want to edit the Image ?");
            alertDialog_edit.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    visible_view_editing(v);
                }
            });
            alertDialog_edit.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog_edit.show();
        } else if (id == R.id.save) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
            alertDialog.setTitle("Alert");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Are you sure you want to save ?");
            alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    view_visible_display(v);
                }
            });
            alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } else if (id == R.id.id_add_photo_edit) {
            openGallery(v);
        } else if (id == R.id.save_image) {
        } else if (id == R.id.id_add_photo) {
            AlertDialog.Builder alertDialog_new = new AlertDialog.Builder(activity);
            alertDialog_new.setTitle("Alert");
            alertDialog_new.setCancelable(false);
            alertDialog_new.setMessage("Want to edit the Image ?");
            alertDialog_new.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    visible_view_editing(v);
                }
            });
            alertDialog_new.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog_new.show();
        } else if (id == R.id.iv_default_image) {
            Intent intent = new Intent(activity, Background_image_activity.class);
            startActivity(intent);
        } else if (id == R.id.iv_back_tshirt) {
            ll_front_back.setVisibility(View.GONE);
        } else if (id == R.id.iv_back) {
            iv_front.setImageResource(R.drawable.ic_front);
            iv_back.setImageResource(R.drawable.ic_back_select);
            mSetRightOut.setTarget(up);
            mSetLeftIn.setTarget(up_back);
            mSetRightOut_mask.setTarget(background);
            mSetLeftIn_mask.setTarget(background_back);
            mSetRightOut.start();
            mSetRightOut_mask.start();
            mSetLeftIn.start();
            mSetLeftIn_mask.start();
            Share.back_print = 1;
        } else if (id == R.id.iv_front) {
            iv_front.setImageResource(R.drawable.ic_front_select);
            iv_back.setImageResource(R.drawable.ic_back);
            mSetRightOut.setTarget(up_back);
            mSetLeftIn.setTarget(up);
            mSetRightOut_mask.setTarget(background_back);
            mSetLeftIn_mask.setTarget(background);
            mSetRightOut.start();
            mSetRightOut_mask.start();
            mSetLeftIn.start();
            mSetLeftIn_mask.start();
            Share.back_print = 0;
        } else if (id == R.id.btn_half) {
            btn_half.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_rect_blue));
            btn_half.setTextColor(getResources().getColor(R.color.white));
            btn_full.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_border_tshirt));
            btn_full.setTextColor(getResources().getColor(R.color.black));
            btn_color.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_border_tshirt));
            btn_color.setTextColor(getResources().getColor(R.color.black));

            ll_tshirt_color.setVisibility(View.GONE);
            ll_select_sleeve_color.setVisibility(View.VISIBLE);
        } else if (id == R.id.btn_full) {
            btn_half.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_border_tshirt));
            btn_half.setTextColor(getResources().getColor(R.color.black));
            btn_full.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_rect_blue));
            btn_full.setTextColor(getResources().getColor(R.color.white));
            btn_color.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_border_tshirt));
            btn_color.setTextColor(getResources().getColor(R.color.black));

            ll_tshirt_color.setVisibility(View.GONE);
            ll_select_sleeve_color.setVisibility(View.VISIBLE);
        } else if (id == R.id.btn_color) {
            btn_half.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_border_tshirt));
            btn_half.setTextColor(getResources().getColor(R.color.black));
            btn_full.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_border_tshirt));
            btn_full.setTextColor(getResources().getColor(R.color.black));
            btn_color.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_rect_blue));
            btn_color.setTextColor(getResources().getColor(R.color.white));

            ll_tshirt_color.setVisibility(View.VISIBLE);
            ll_select_sleeve_color.setVisibility(View.GONE);
        } else if (id == R.id.btn_s_size) {
            btn_s_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.blue_round_size_tshirt));
            btn_l_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_m_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_xl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_xxl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));

            btn_s_size.setTextColor(getResources().getColor(R.color.white));
            btn_l_size.setTextColor(getResources().getColor(R.color.black));
            btn_m_size.setTextColor(getResources().getColor(R.color.black));
            btn_xl_size.setTextColor(getResources().getColor(R.color.black));
            btn_xxl_size.setTextColor(getResources().getColor(R.color.black));
        } else if (id == R.id.btn_l_size) {
            btn_s_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_l_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.blue_round_size_tshirt));
            btn_m_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_xl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_xxl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));

            btn_s_size.setTextColor(getResources().getColor(R.color.black));
            btn_l_size.setTextColor(getResources().getColor(R.color.white));
            btn_m_size.setTextColor(getResources().getColor(R.color.black));
            btn_xl_size.setTextColor(getResources().getColor(R.color.black));
            btn_xxl_size.setTextColor(getResources().getColor(R.color.black));
        } else if (id == R.id.btn_m_size) {
            btn_s_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_l_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_m_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.blue_round_size_tshirt));
            btn_xl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_xxl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));

            btn_s_size.setTextColor(getResources().getColor(R.color.black));
            btn_l_size.setTextColor(getResources().getColor(R.color.black));
            btn_m_size.setTextColor(getResources().getColor(R.color.white));
            btn_xl_size.setTextColor(getResources().getColor(R.color.black));
            btn_xxl_size.setTextColor(getResources().getColor(R.color.black));
        } else if (id == R.id.btn_xl_size) {
            btn_s_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_l_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_m_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_xl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.blue_round_size_tshirt));
            btn_xxl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));

            btn_s_size.setTextColor(getResources().getColor(R.color.black));
            btn_l_size.setTextColor(getResources().getColor(R.color.black));
            btn_m_size.setTextColor(getResources().getColor(R.color.black));
            btn_xl_size.setTextColor(getResources().getColor(R.color.white));
            btn_xxl_size.setTextColor(getResources().getColor(R.color.black));
        } else if (id == R.id.btn_xxl_size) {
            btn_s_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_l_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_m_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_xl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.round_size_tshirt));
            btn_xxl_size.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.blue_round_size_tshirt));

            btn_s_size.setTextColor(getResources().getColor(R.color.black));
            btn_l_size.setTextColor(getResources().getColor(R.color.black));
            btn_m_size.setTextColor(getResources().getColor(R.color.black));
            btn_xl_size.setTextColor(getResources().getColor(R.color.black));
            btn_xxl_size.setTextColor(getResources().getColor(R.color.white));
        } else if (id == R.id.iv_size_chart) {
        } else if (id == R.id.id_back) {
        } else if (id == R.id.iv_help) {
        } else if (id == R.id.ll_sports) {
            btn_sports.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_sports_selecte));
            btn_formal.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_formal));
        } else if (id == R.id.ll_formal) {
            btn_sports.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_sports));
            btn_formal.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_formal_select));
        } else if (id == R.id.btn_sports) {
            btn_sports.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_sports_selecte));
            btn_formal.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_formal));
        } else if (id == R.id.btn_formal) {
            btn_sports.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_sports));
            btn_formal.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.ic_formal_select));
        }

    }

    private void visible_view_editing(View v) {
        ll_edit_button_view.setVisibility(View.VISIBLE);
        ll_edit_view.setVisibility(View.VISIBLE);

        save_image.setVisibility(View.GONE);
        ll_tshirt_display.setVisibility(View.GONE);
        if (Share.back_print == 1) {
            background_edit.setVisibility(View.GONE);
            stickerView.setVisibility(View.GONE);
            background_edit_back.setVisibility(View.VISIBLE);
            id__back_stickerview.setVisibility(View.VISIBLE);
        } else if (Share.back_print == 0) {
            background_edit.setVisibility(View.VISIBLE);
            stickerView.setVisibility(View.VISIBLE);
            background_edit_back.setVisibility(View.GONE);
            id__back_stickerview.setVisibility(View.GONE);
        } else {
            background_edit.setVisibility(View.VISIBLE);
            stickerView.setVisibility(View.VISIBLE);
            background_edit_back.setVisibility(View.GONE);
            id__back_stickerview.setVisibility(View.GONE);
        }
        setDiemns();
    }

    private void view_visible_display(View v) {
        if (Share.back_print == 1) {
            background_back.setImageBitmap(getResizedBitmap(id__back_stickerview.createBitmap(), (float) bground.getWidth(), (float) bground.getHeight()));
        } else {
            background.setImageBitmap(getResizedBitmap(stickerView.createBitmap(), (float) bground.getWidth(), (float) bground.getHeight()));
        }
        ll_edit_button_view.setVisibility(View.GONE);
        ll_edit_view.setVisibility(View.GONE);
        save_image.setVisibility(View.VISIBLE);
        ll_tshirt_display.setVisibility(View.VISIBLE);
    }


    public Bitmap getBitmapPrint(Bitmap bm, Float width, Float height) {
        final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inTargetDensity = 1;
        bm.setDensity(Bitmap.DENSITY_NONE);

        int fromHere = (int) (bm.getHeight() * 0.2);

        Log.e("withoutcrop", "==>" + bm.getWidth() + "==>" + bm.getHeight());
        ImageView imageView = new ImageView(this);
        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());

        Log.e("withcrop", "==>" + croppedBitmap.getWidth() + "==>" + croppedBitmap.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(croppedBitmap.getWidth(), croppedBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        canvas.scale(-1, 1, croppedBitmap.getWidth() / 2, croppedBitmap.getHeight() / 2);
        canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());

        return getResizedBitmap(bitmap, width * 300, height * 300);
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, float newWidth, float newHeight) {
        Bitmap resizedBitmap = Bitmap.createBitmap((int) newWidth, (int) newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);
        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint());

        return resizedBitmap;
    }


    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        up.setCameraDistance(scale);
        background.setCameraDistance(scale);
        up_back.setCameraDistance(scale);
        background_back.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.out_animation);
        mSetRightOut_mask = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.in_animation);
        mSetLeftIn_mask = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.in_animation);
    }

    private void setHeader() {
        ImageView imageView = findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Share.resultbitmap = null;
                Share.final_result_bitmap = null;
            }
        });
    }

    private void getDisplaySize() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void selectImage() {
        Log.e("CHECKDIALOG", "selectImage: dialog --6--");
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_gallery),
                getString(R.string.cancel)};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    if (checkAndRequestPermissionsCamera(1)) {
                        ImagePicker.pickImage(activity, "Select your image:");
                    }
                } else if (items[item].equals(getString(R.string.choose_from_gallery))) {
                    if (checkAndRequestPermissionsStorage(2)) {
                        Intent i = new Intent(activity, FaceActivity.class);
                        i.putExtra("activity", "HomeActivity");
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

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
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
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
                    if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
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
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(getString(R.string.permission_required))
                            .setMessage(getString(R.string.permission_sentence))
                            .setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }
                if (requestCode == 2) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(getString(R.string.permission_required))
                            .setMessage(getString(R.string.permission_sentence_storage))
                            .setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
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

    private boolean checkAndRequestPermissionsCamera(int code) {

        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CAMERA},
                    code);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkAndRequestPermissionsStorage(int code) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        code);
                return false;
            } else {
                return true;
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        code);
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


    private void intView() {
        upload = false;

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerClicked(Sticker sticker) {
            }

            @Override
            public void onStickerDeleted(Sticker sticker) {
                if (mStickers.size() == 0) {
                    id_add_photo.setVisibility(View.VISIBLE);
                }
//                stickerView.remove(sticker);
                Log.e("DELETE", "onStickerDeleted: " + mStickers.size());
            }

            @Override
            public void onStickerDragFinished(Sticker sticker) {
            }

            @Override
            public void onStickerZoomFinished(Sticker sticker) {
            }

            @Override
            public void onStickerAdd(Sticker sticker) {
                id_add_photo.setVisibility(View.GONE);
                Log.e("ADD", "onStickerAdd: " + mStickers.size());
            }

            @Override
            public void onStickerNull() {
            }

            @Override
            public void onStickerFlipped(Sticker sticker) {
            }
        });
        id_add_photo_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(view);
            }
        });
    }

    public void openGallery(View view) {
        if (checkAndRequestPermissionsStorage(2)) {
            Intent i = new Intent(activity, FaceActivity.class);
            i.putExtra("activity", "HomeActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        // selectImage();
    }

    public void pickColor(View view) {
        ColorPickerDialogBuilder
                .with(activity)
                .setTitle(getString(R.string.choose_color))
                .initialColor(selectedColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton(getString(R.string.ok), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        Tshirt_Editing_Activity.this.selectedColor = selectedColor;
                        bground = changeBitmapColor(bground, selectedColor);
                        if (Share.back_print == 1) {
                            background_edit_back.setImageBitmap(bground);
                            id__back_stickerview.setBackgroundColor(selectedColor);
                        } else {
                            background_edit.setImageBitmap(bground);
                            stickerView.setBackgroundColor(selectedColor);
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    public void openFont(View view) {
        Intent intent = new Intent(activity, FontActivity.class);
        startActivity(intent);
    }

    public void openSticker(View view) {
        Intent intent = new Intent(activity, StickerActivity.class);
        startActivity(intent);
    }

    private Bitmap changeBitmapColor(Bitmap sourceBitmap, int color) {

        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);


        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(bitmap));
                drawableSticker.setTag("maindraw");
                if (Share.back_print == 1) {
                    id__back_stickerview.addStickerMain(drawableSticker);
                } else {
                    stickerView.addStickerMain(drawableSticker);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Uri captureUri = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);

            if (captureUri != null)
                Log.e("TAG-->", "captureUri  " + captureUri.getPath());

            if (captureUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), captureUri);
                    // Log.d(TAG, String.valueOf(bitmap));

                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(bitmap));
                    drawableSticker.setTag("maindraw");
                    stickerView.addSticker(drawableSticker);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Share.bitmap != null) {
            //                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Share.bitmap);
            // Log.d(TAG, String.valueOf(bitmap));
            Share.lst_album_image.clear();

            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bitmap));
            drawableSticker.setTag("maindraw");
            if (Share.back_print == 1) {
                id__back_stickerview.addStickerMain(drawableSticker);
            } else {
                stickerView.addStickerMain(drawableSticker);
            }
            Share.bitmap = null;
        }

        if (Share.imageuri.equalsIgnoreCase("")) {

        } else {
            Glide.with(activity).asBitmap().load(Share.imageuri).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(resource));
                    drawableSticker.setTag("cartoon");
                    if (Share.back_print == 1) {
                        id__back_stickerview.addStickerMain(drawableSticker);
                    } else {
                        stickerView.addStickerMain(drawableSticker);
                    }
                    Share.imageuri = "";
                }
            });
        }

        Log.e("STICKER", "onResume: " + Share.FONT_FLAG);
        if (Share.FONT_FLAG) {
            Log.e("STICKER", "onResume: " + Share.FONT_FLAG);
            Share.FONT_FLAG = false;
            DrawableSticker drawableSticker = Share.TEXT_DRAWABLE;
            drawableSticker.setTag("text");
            if (Share.back_print == 1) {
                id__back_stickerview.addStickerMain(drawableSticker);
            } else {
                stickerView.addStickerMain(drawableSticker);
            }
            drawables_sticker.add(drawableSticker);
        }
    }

    private void setDiemns() {
        Log.e("DIMENSION", "setDiemns: " + getResources().getDisplayMetrics().density + "=======>");
        width = 3 * 200;
        height = (int) (4.5 * 200);

        maskable_fl3_1_1.getLayoutParams().width = width;
        maskable_fl3_1_1.getLayoutParams().height = height;

        stickerView.getLayoutParams().width = width;
        stickerView.getLayoutParams().height = height;

        id__back_stickerview.getLayoutParams().width = width;
        id__back_stickerview.getLayoutParams().height = height;

        background_edit.getLayoutParams().width = width;
        background_edit.getLayoutParams().height = height;

        background_edit_back.getLayoutParams().width = width;
        background_edit_back.getLayoutParams().height = height;

//        up.getLayoutParams().width = width;
//        up.getLayoutParams().height = height;
        maskable_fl3_1_1.setMask(new BitmapDrawable(msk));
        if (Share.back_print == 1) {
            bground = ((BitmapDrawable) getApplicationContext().getResources().getDrawable(R.drawable.tshirt_afour)).getBitmap();
            background_edit_back.setImageBitmap(bground);
        } else {
            background_edit.setImageBitmap(bground);
        }
    }

}
