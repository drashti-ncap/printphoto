package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.jaredrummler.android.device.DeviceName;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pagination.MainActivity;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FirebaseEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.customView.MaskableFrameLayout;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.DrawableSticker;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.Sticker;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView;
import com.mobile.cover.photo.editor.back.maker.model.Cart;
import com.mobile.cover.photo.editor.back.maker.testing_modules.PhotoPickupImageActivity;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static androidx.core.content.FileProvider.getUriForFile;
import static com.mobile.cover.photo.editor.back.maker.Commen.Share.drawables_sticker;
import static com.mobile.cover.photo.editor.back.maker.Commen.Share.upload;
import static com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView.mStickers;

public class CaseEditActivity extends PrintPhotoBaseActivity {
    public static final int PICK_IMAGE = 123556;
    private static final long MIN_CLICK_INTERVAL = 1500;
    public static StickerView stickerView;
    public static int width;
    public static int height;
    public static Bitmap print_bitmap = null;
    public static Bitmap preview_bitmap = null;
    public static Bitmap final_bitmap = null;
    public static Bitmap cover_bitmap = null;
    public static Activity activity;
    public static File file_printphoto, file_cover_bitmap;
    ImageView background, up, iv_default_image;
    MaskableFrameLayout maskableFrameLayout;
    Bitmap msk = null, bground = null, up_image = null;
    Bitmap bitmap;
    RelativeLayout savelayout, new_savelayout;
    int selectedColor = Color.parseColor("#ffffff");
    LinearLayout id_add_photo;
    ImageView btn_help, iv_add_to_cart;
   // ProgressDialog pd;
    AlertDialog alertDialog;
    Glide glide;
    private int PICK_IMAGE_REQUEST = 101;
    private List<String> listPermissionsNeeded = new ArrayList<>();
    private int STORAGE_PERMISSION_CODE = 23;
    private String image_name = "";
    private long mLastClickTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_case_edit);

        stickerView = findViewById(R.id.id_stickerview);
        mStickers.clear();
        Log.e("STICKERVIEW", "onCreate: " + stickerView);
        activity = CaseEditActivity.this;
        findViews();
        Log.e("int", "==>" + selectedColor);
        Share.resultbitmap = null;
        Share.bitmap = null;
        Share.imageuri = "";
        Share.final_result_bitmap = null;

//        stickerView = new StickerView(getApplicationContext());

        // TODO: 03/11/18  Dont you dare touch this process of editing or else you will get punished!!!!!
        if (Share.bitmapHashMap != null) {
            msk = Share.bitmapHashMap.get(Share.key_msk_imge);
            bground = Share.bitmapHashMap.get(Share.key_msk_imge);
            Log.e("bground", "onCreate: " + Share.bitmapHashMap.get(Share.key_msk_imge));
            Log.e("bground", "onCreate: " + Share.bitmapHashMap.get(Share.key_normal_image));
            up_image = Share.bitmapHashMap.get(Share.key_normal_image);
        }

//        if (Share.bitmap == null) {
//            mStickers.clear();
//            mStickers = new ArrayList<>();
//        }

        if (Share.edit_image) {
            Log.e("AATLEAAIVO", "onResume: +=================>3");
            final ProgressDialog pd = ProgressDialog.show(CaseEditActivity.this, "", getString(R.string.loading), true, false);

            Share.edit_image = false;


            Glide.with(activity).asBitmap()
                    .load(Share.edited_image)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            resource = newRotateBitmap(resource, 90);
                            Log.e("HEIGHT", "onResourceReady: ==========>" + resource.getHeight() + "========>" + resource.getWidth());
                            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(resource));
                            drawableSticker.setTag("maindraw");
                            stickerView.edited_addStickerMain(drawableSticker);
                            Share.edited_image = null;
                            dismissDialog();
                        }
                    });
        }

        setHeader();
        getDisplaySize();
        setDiemns();
        intView();

    }

    private void loadPicture(final String photoUrl) {
        Glide.with(activity).asBitmap()
                .load(photoUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.e("READY", "onResourceReady: ");
                        DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(resource));
                        drawableSticker.setTag("cartoon");
                        stickerView.addSticker(drawableSticker);
                        dismissDialog();
                        Share.imageuri = "";
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        dismissDialog();
                        Share.imageuri = "";
                        if (alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        alertDialog = new AlertDialog.Builder(activity).create();
                        alertDialog.setTitle(getString(R.string.internet_connection));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(getString(R.string.slow_connect));
                        alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

                    }

                });
    }

    @Override
    public void onBackPressed() {
        finish();
        Share.resultbitmap = null;
        Share.final_result_bitmap = null;
        Log.e("Backpressed", "onBackPressed: ");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    // TODO: 03/11/18  Method to upload data and images with applied stickers.
    public void addtocart(View v) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return;


        if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CaseEditActivity.this);
            alertDialog.setTitle(getString(R.string.log_in));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.need_login));
            alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    Intent intent = new Intent(CaseEditActivity.this, LogInActivity.class);
                    startActivity(intent);

                }
            });
            alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });

            alertDialog.create().show();

            return;
        }
        if (mStickers.size() <= 0 && selectedColor == Color.parseColor("#ffffff")) {
            Toast.makeText(CaseEditActivity.this, getString(R.string.please_select_image), Toast.LENGTH_SHORT).show();
        } else {
            bground = changeBitmapColor(bground, selectedColor);
            background.setImageBitmap(bground);
            stickerView.setBackgroundColor(selectedColor);
            Log.e("CHECKCART", "addtocart: 8--" );
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CaseEditActivity.this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.add_to_cart));
            alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    create_preview_bitmap();


                }
            });
            alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });

            alertDialog.create().show();
        }
    }

    private void create_preview_bitmap() {
        up.setVisibility(View.GONE);
        id_add_photo.setVisibility(View.GONE);
        stickerView.setControlItemsHidden();
        new_savelayout.setDrawingCacheEnabled(true);
        new_savelayout.buildDrawingCache();

        Log.e("Modelid", "create_preview_bitmap: " + getIntent().getStringExtra("model_id"));
        // TODO: 26/01/19 Tested for only note 5 pro,redmi y2,Xiaomi Redmi Note 6 Pro,Vivo Y95,Oppo F1,Oppo F7,Vivo V7,Vivo V9,Vivo v11 pro.
//        if (getIntent().getStringExtra("model_id").equalsIgnoreCase("69") || getIntent().getStringExtra("model_id").equalsIgnoreCase("169") ||
//                getIntent().getStringExtra("model_id").equalsIgnoreCase("239") || getIntent().getStringExtra("model_id").equalsIgnoreCase("228") ||
//                getIntent().getStringExtra("model_id").equalsIgnoreCase("45") || getIntent().getStringExtra("model_id").equalsIgnoreCase("51") ||
//                getIntent().getStringExtra("model_id").equalsIgnoreCase("80") || getIntent().getStringExtra("model_id").equalsIgnoreCase("83") ||
//                getIntent().getStringExtra("model_id").equalsIgnoreCase("208")) {
//            new create_bitmap().execute();
//        } else {
        up.setVisibility(View.VISIBLE);
        stickerView.setControlItemsHidden();
        savelayout.setDrawingCacheEnabled(true);
        savelayout.buildDrawingCache();
        new createReq().execute();
    }

    private void setDiemns() {

        Log.e("MASKHEIGTH", "setDiemns: " + Share.maskwidth + "=======>" + Share.maskheight);
        Log.e("MASKHEIGTH", "setDiemns:====>Screen" + Share.screenWidth + "=======>" + Share.screenHeight);

        // return 0.75 if it's LDPI
        // return 1.0 if it's MDPI
        // return 1.5 if it's HDPI
        // return 2.0 if it's XHDPI
        // return 3.0 if it's XXHDPI
        // return 4.0 if it's XXXHDPI

        Log.e("DIMENSION", "setDiemns: " + getResources().getDisplayMetrics().density);

        if (getResources().getDisplayMetrics().density <= 2.0) {
            Log.e("DEVICENAME", "setDiemns: " + DeviceName.getDeviceName());
            if (getResources().getDisplayMetrics().density == 0.75 || getResources().getDisplayMetrics().density == 1.0) {

                Log.e("DENSITY", "setDiemns: ========>LDPI======>MDPI");

                width = (int) (Share.maskwidth * 100);
                height = (int) (Share.maskheight * 100);
            } else if (getResources().getDisplayMetrics().density == 1.5) {
                Log.e("DENSITY", "setDiemns: ========>HDPI");
                width = (int) (Share.maskwidth * 80);
                height = (int) (Share.maskheight * 80);
            } else if (getResources().getDisplayMetrics().density == 2.0) {
                Log.e("DENSITY", "setDiemns: ========>XHDPI");
                width = (int) (Share.maskwidth * 130);
                height = (int) (Share.maskheight * 130);
            } else if (getResources().getDisplayMetrics().density == 1.75) {
                Log.e("DENSITY", "setDiemns: ========>XHDPI2");
                if (DeviceName.getDeviceName().equalsIgnoreCase("Nokia 2.1")) {
                    width = (int) (Share.maskwidth * 120);
                    height = (int) (Share.maskheight * 120);
                } else {
                    width = (int) (Share.maskwidth * 140);
                    height = (int) (Share.maskheight * 140);
                }

            } else {
                if (DeviceName.getDeviceName().equalsIgnoreCase("Nokia 1")) {
                    width = (int) (Share.maskwidth * 100);
                    height = (int) (Share.maskheight * 100);
                } else {
                    width = (int) (Share.maskwidth * 150);
                    height = (int) (Share.maskheight * 150);
                }
            }
        } else {
            width = (int) (Share.maskwidth * 200);
            height = (int) (Share.maskheight * 200);
        }


        Log.e("MASKHEIGTH", "setDiemns: " + width + "=======>111111" + height);

        maskableFrameLayout.getLayoutParams().width = width;
        maskableFrameLayout.getLayoutParams().height = height;

        stickerView.getLayoutParams().width = width;
        stickerView.getLayoutParams().height = height;

        background.getLayoutParams().width = width;
        background.getLayoutParams().height = height;

        up.getLayoutParams().width = width;
        up.getLayoutParams().height = height;
        maskableFrameLayout.setMask(new BitmapDrawable(msk));
        background.setImageBitmap(bground);


        up.setImageBitmap(up_image);

        if (Share.bitmap_testing != null) {
            //                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Share.bitmap);
            // Log.d(TAG, String.valueOf(bitmap));
            Share.lst_album_image.clear();
            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bitmap_testing));
            drawableSticker.setTag("maindraw");
            stickerView.addStickerMain(drawableSticker);
            Share.bitmap = null;
            Share.bitmap_testing = null;
        }

    }

    private void sendData() {
        up.setVisibility(View.VISIBLE);
        stickerView.setControlItemsHidden();
        savelayout.setDrawingCacheEnabled(true);
        savelayout.buildDrawingCache();

        new Preview_request().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        up.setVisibility(View.VISIBLE);
        if (Share.resultbitmap != null) {
            msk = Share.resultbitmap;
            bground = Share.resultbitmap;
            stickerView.setBackground(new BitmapDrawable(Share.resultbitmap));
        }


        if (Share.bg_drawable != null) {
            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bg_drawable));
            drawableSticker.setTag("maindraw");
            stickerView.addStickerMain_bg(drawableSticker, 0);
            Share.bg_drawable = null;
        }

        if (Share.bitmap != null) {
            if (stickerView != null) {
                Log.e("AATLEAAIVO", "onResume: +=================>1");
                DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bitmap));
                drawableSticker.setTag("maindraw");
                stickerView.addStickerMain(drawableSticker);
                Share.lst_album_image.clear();
                Share.bitmap = null;
            }
        }
        if (!Share.imageuri.equalsIgnoreCase("")) {
            Log.e("AATLEAAIVO", "onResume: +=================>2");

//            if (pd != null) {
//                dismissDialog();
//            }
            hideProgressDialog();
//            pd = ProgressDialog.show(CaseEditActivity.this, "", getString(R.string.loading), true, false);
            showProgressDialog(CaseEditActivity.this);
            loadPicture(Share.imageuri);
        }
        Log.e("STICKER", "onResume: " + Share.FONT_FLAG);
        if (Share.FONT_FLAG) {
            Log.e("STICKER", "onResume: " + Share.FONT_FLAG);
            Share.FONT_FLAG = false;


            DrawableSticker drawableSticker = Share.TEXT_DRAWABLE;
            drawableSticker.setTag("text");
            stickerView.addSticker(drawableSticker);

            drawables_sticker.add(drawableSticker);

        }
    }

    // TODO: 03/11/18 Get the image file method
    public File getFile(String filename, Bitmap yourbitmap, String formate) {
        File f = new File(getCacheDir(), filename + formate);
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

    // TODO: 03/11/18 Image rotation method
    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public Bitmap newRotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    // TODO: 03/11/18 Crop image method
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

    // TODO: 03/11/18 PrintBtimap method using the height and width coming in API
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

    public Bitmap getBitmapPrint(Bitmap bm) {
        final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inTargetDensity = 1;
        bm.setDensity(Bitmap.DENSITY_NONE);

        int fromHere = (int) (bm.getHeight() * 0.2);

        Log.e("withoutcrop", "==>" + bm.getWidth() + "==>" + bm.getHeight());
        ImageView imageView = new ImageView(this);
        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, (int) (Share.screenHeight * 0.038), bm.getWidth(), bm.getHeight() - (int) (Share.screenHeight * 0.038 * 2));

        Log.e("withcrop", "==>" + croppedBitmap.getWidth() + "==>" + croppedBitmap.getHeight());

        return croppedBitmap;
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

    // TODO: 03/11/18 Resized cover bitmap method
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

    private void selectImage() {
        Log.e("CHECKDIALOG", "selectImage: dialog --1--");
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_gallery),
                getString(R.string.cancel)};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CaseEditActivity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    if (checkAndRequestPermissionsCamera(1)) {
                        ImagePicker.pickImage(CaseEditActivity.this, "Select your image:");
                    }
                } else if (items[item].equals(getString(R.string.choose_from_gallery))) {
                    if (checkAndRequestPermissionsStorage(2)) {
                        Intent i = new Intent(CaseEditActivity.this, FaceActivity.class);
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
                        ActivityCompat.requestPermissions(CaseEditActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.requestPermissions(CaseEditActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            ActivityCompat.requestPermissions(CaseEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        } else {
                            ActivityCompat.requestPermissions(CaseEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
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

        if (ContextCompat.checkSelfPermission(CaseEditActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CaseEditActivity.this, new String[]{android.Manifest.permission.CAMERA},
                    code);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkAndRequestPermissionsStorage(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(CaseEditActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CaseEditActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, code);
                return false;
            } else {

                return true;
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(CaseEditActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CaseEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, code);
                return false;
            } else {

                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(CaseEditActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(CaseEditActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CaseEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
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
        id_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(view);
            }
        });
    }

    public void openGallery(View view) {
        if (checkAndRequestPermissionsStorage(2)) {
            Intent i = new Intent(CaseEditActivity.this, FaceActivity.class);
            i.putExtra("activity", "HomeActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
//        selectImage();
    }

    public void pickColor(View view) {
        ColorPickerDialogBuilder
                .with(CaseEditActivity.this)
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
                        CaseEditActivity.this.selectedColor = selectedColor;
                        bground = changeBitmapColor(bground, selectedColor);
                        background.setImageBitmap(bground);
                        stickerView.setBackgroundColor(selectedColor);
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
        Intent intent = new Intent(CaseEditActivity.this, FontActivity.class);
        startActivity(intent);
    }

    public void openSticker(View view) {
        Intent intent = new Intent(CaseEditActivity.this, StickerActivity.class);
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
                stickerView.addStickerMain(drawableSticker);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("CHECKDIALOG", "onActivityResult: check data==>"+data );
            Uri captureUri = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);

            if (captureUri != null)
                Log.e("CHECKDIALOG", "captureUri==>" + captureUri.getPath());

            if (captureUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), captureUri);
                    // Log.d(TAG, String.valueOf(bitmap));

                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(bitmap));
                    drawableSticker.setTag("maindraw");
                    stickerView.addSticker(drawableSticker);

                } catch (IOException e) {
                    Log.e("CHECKDIALOG", "onActivityResult: exception-->" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private void findViews() {
        background = findViewById(R.id.background);
        iv_add_to_cart = findViewById(R.id.iv_add_to_cart);
        btn_help = findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, VideoActivity.class);
                startActivity(intent);
            }
        });
        up = findViewById(R.id.up);
        maskableFrameLayout = findViewById(R.id.maskable);
//        stickerView = findViewById(R.id.id_stickerview);
//        StickerView.mStickers.clear();
        savelayout = findViewById(R.id.savelayout);
        new_savelayout = findViewById(R.id.new_savelayout);
        id_add_photo = findViewById(R.id.id_add_photo);
        iv_default_image = findViewById(R.id.iv_default_image);
        iv_default_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaseEditActivity.this, Background_image_activity.class);
                startActivity(intent);
            }
        });


    }

    private void setHeader() {
        ImageView imageView = findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                Share.resultbitmap = null;
                Share.final_result_bitmap = null;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    private void getDisplaySize() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }


    // TODO: 26/01/19 Method for previewing the image in the next activity to the user
    public class Preview_request extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd = ProgressDialog.show(CaseEditActivity.this, "", getString(R.string.loading), true, false);
//            pd.show();
            showProgressDialog(CaseEditActivity.this);
            stickerView.setBackgroundColor(selectedColor);
//            stickerView.setBackground(new BitmapDrawable(Share.final_result_bitmap));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dismissDialog();
            Intent intent = new Intent(activity, Preview_activity.class);
            intent.putExtra("model_id", getIntent().getStringExtra("model_id"));
            intent.putExtra("model_name", getIntent().getStringExtra("model_name"));
            intent.putExtra("total_amount", getIntent().getStringExtra("total_amount"));
            intent.putExtra("paid_amount", getIntent().getStringExtra("paid_amount"));
            intent.putExtra("total_amount", getIntent().getStringExtra("total_amount"));
            startActivity(intent);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (getIntent().hasExtra("width")) {
                print_bitmap = getBitmapPrint(stickerView.createBitmap(), Float.parseFloat(getIntent().getStringExtra("width")), Float.parseFloat(getIntent().getStringExtra("height")));
                print_bitmap = newRotateBitmap(print_bitmap, 270);
                Log.e("with_width", "==>if");

            } else {
                print_bitmap = getBitmapPrint(stickerView.createBitmap());
            }
            Log.e("with_width", "==>" + print_bitmap.getWidth());
            Log.e("with_height", "==>" + print_bitmap.getHeight());
            cover_bitmap = RotateBitmap(getResizedBitmapCover(CropBitmapTransparency(savelayout.getDrawingCache()), height, width), 270);
            file_printphoto = getFile("printphoto_" + System.currentTimeMillis(), print_bitmap, ".jpg");
            file_cover_bitmap = getFile("cover_bitmap_" + System.currentTimeMillis(), cover_bitmap, ".png");

            savelayout.setAlwaysDrawnWithCacheEnabled(false);

            return null;
        }
    }

    // TODO: 26/01/19 method for saving the image to cart
    public class createReq extends AsyncTask<Void, Void, Void> {

        MultipartBody.Builder builder;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pd = ProgressDialog.show(CaseEditActivity.this, "", getString(R.string.loading), true, false);
            showProgressDialog(CaseEditActivity.this);
            builder = new MultipartBody.Builder();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            if (getIntent().hasExtra("width")) {
                print_bitmap = stickerView.createBitmap();
                print_bitmap = getBitmapPrint(print_bitmap, Float.parseFloat(getIntent().getStringExtra("width")), Float.parseFloat(getIntent().getStringExtra("height")));
                print_bitmap = newRotateBitmap(print_bitmap, 270);
                Log.e("with_width", "==>if");

            } else {
                Log.e("with_width", "==>else");
                print_bitmap = getBitmapPrint(stickerView.createBitmap());
            }
            Log.e("with_width", "==>" + print_bitmap.getWidth());
            Log.e("with_height", "==>" + print_bitmap.getHeight());
            cover_bitmap = RotateBitmap(getResizedBitmapCover(CropBitmapTransparency(savelayout.getDrawingCache()), height, width), 270);
            file_printphoto = getFile("printphoto_" + System.currentTimeMillis(), print_bitmap, ".jpg");
            file_cover_bitmap = getFile("cover_bitmap_" + System.currentTimeMillis(), cover_bitmap, ".png");

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("model_id", getIntent().getStringExtra("model_id"));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_printphoto);
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file_cover_bitmap);
            builder.addFormDataPart("print_image", file_printphoto.getName(), requestBody);
            builder.addFormDataPart("user_id", SharedPrefs.getString(CaseEditActivity.this, Share.key_ + RegReq.id));
            builder.addFormDataPart("case_image", file_cover_bitmap.getName(), requestBody1);
            builder.addFormDataPart("quantity", "1");
            builder.addFormDataPart("ln", Locale.getDefault().getLanguage());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (builder != null) {
                MultipartBody multipartBody = builder.build();
                APIService apiService = new MainApiClient(CaseEditActivity.this).getApiInterface();

                Call<Cart> cartCall = apiService.sendCart(multipartBody);
                cartCall.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        if (response.isSuccessful()) {
                            dismissDialog();
                            if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                                if (response.body().getcart_data().getStatus() == 1) {


                                    String contentId = "";
                                    String contentType = "";
                                    String contentAmount = "";

                                    contentId = getIntent().getStringExtra("model_id");
                                    contentType = getIntent().getStringExtra("model_name");
                                    contentAmount = getIntent().getStringExtra("paid_amount");

                                    if (contentId != null && contentType != null && contentAmount != null) {
                                        FBEventsKt.logAddedToCartEvent(CaseEditActivity.this, contentId, contentType, contentAmount);
                                        FirebaseEventsKt.logAddToCartEvent(CaseEditActivity.this, contentId, contentType, Double.parseDouble(contentAmount));
                                    }

                                    upload = true;
                                    mStickers.clear();
                                    Share.resultbitmap = null;
                                    Share.final_result_bitmap = null;
                                    Share.edit_image = false;
                                    if (Default_image_activity.Companion.getActivity() != null) {
                                        Default_image_activity.Companion.getActivity().finish();
                                    }
                                    if (MainActivity.activity != null) {
                                        MainActivity.activity.finish();
                                    }
                                    if (com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.MainActivity.mActivity != null) {
                                        com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.MainActivity.mActivity.finish();
                                    }
                                    if (PhotoPickupImageActivity.mActivity != null) {
                                        PhotoPickupImageActivity.mActivity.finish();
                                    }
                                    finish();
                                    overridePendingTransition(R.anim.app_right_in, R.anim.app_left_out);
                                } else {
                                    Log.e("FAILURE", "onResponse: " + response.body().getResponseMessage());
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(CaseEditActivity.this);
                                    alertDialog.setTitle(getString(R.string.upload_fail));
                                    alertDialog.setCancelable(false);
                                    alertDialog.setMessage(response.body().getcart_data().getMessage());
                                    alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dismissDialog();
                                            dialog.dismiss();
                                        }
                                    });

                                    alertDialog.create().show();
                                }
                            } else {
                                Toast.makeText(CaseEditActivity.this, "" + response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            dismissDialog();
                            Toast.makeText(CaseEditActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        }
                        Log.e("response", "==>" + response.toString());
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Log.e("response", "Failed==>" + t.getLocalizedMessage());
                        Log.e("response", "Failed==>" + t.getMessage());
                        dismissDialog();

                        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(CaseEditActivity.this).create();
                            alertDialog.setTitle(getString(R.string.time_out));
                            alertDialog.setMessage(getString(R.string.connect_time_out));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    create_preview_bitmap();
                                }
                            });
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(CaseEditActivity.this).create();
                            alertDialog.setTitle(getString(R.string.internet_connection));
                            alertDialog.setMessage(getString(R.string.slow_connect));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    create_preview_bitmap();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                });
            }

        }
    }


    public void dismissDialog() {

        try {
            if (isDestroyed()) {
                return;
            }
//            if (pd != null && pd.isShowing()) {
//                pd.dismiss();
//            }
            hideProgressDialog();
        } catch (Exception e) {
            Log.e("Dismiss Dialog", e.toString());
        }

    }

}
