package com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity;

import android.Manifest;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jaredrummler.android.device.DeviceName;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pagination.MainActivity;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Background_image_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Default_image_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FaceActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FontActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.LogInActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.StickerActivity;
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
import static com.mobile.cover.photo.editor.back.maker.Commen.Share.drawables_sticker;
import static com.mobile.cover.photo.editor.back.maker.Commen.Share.upload;
import static com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView.mStickers;

public class Custom_CaseEditActivity extends PrintPhotoBaseActivity {
    public static final int PICK_IMAGE = 123556;
    public static StickerView stickerView;
    public static int width;
    public static int height;
    public static Bitmap print_bitmap = null;
    public static Bitmap final_bitmap = null;
    public static Bitmap cover_bitmap = null;
    ImageView background, up, iv_default_image;
    MaskableFrameLayout maskableFrameLayout;
    Bitmap msk = null, bground = null, up_image = null;
    Bitmap bitmap;
    RelativeLayout savelayout;
    int selectedColor = Color.parseColor("#ffffff");
   // ProgressDialog progress;
    LinearLayout id_add_photo;
    FloatingActionButton fab_faceactivity;
    boolean iscolor = false;
    File file_printphoto;
    private int PICK_IMAGE_REQUEST = 101;
    private FloatingActionMenu menuYellow;
    private List<String> listPermissionsNeeded = new ArrayList<>();
    private int STORAGE_PERMISSION_CODE = 23;
    private String image_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fancy_activity_custom_case_edit);

        Log.e("int", "==>" + selectedColor);
        stickerView = findViewById(R.id.id_stickerview);
        StickerView.mStickers.clear();

        Share.bitmap = null;
        Share.imageuri = "";


        if (Share.bitmapHashMap != null) {
            msk = Share.bitmapHashMap.get(Share.key_msk_imge);
            bground = Share.bitmapHashMap.get(Share.key_msk_imge);
            up_image = Share.bitmapHashMap.get(Share.key_normal_image);
        }


        if (Share.bitmap == null) {
            mStickers.clear();
            mStickers = new ArrayList<>();
        }

        if (Share.bitmap_testing == null) {
            mStickers.clear();
            mStickers = new ArrayList<>();
        }


        setHeader();
        getDisplaySize();
        findViews();
        setDiemns();
        intView();

        if (Share.bitmap_testing != null) {
            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bitmap_testing));
            drawableSticker.setTag("bg_image");
            stickerView.addSticker(drawableSticker);
            Share.bitmap_testing = null;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    // TODO: 03/11/18  Method to upload data and images with applied stickers.
    public void addtocart(View v) {

        if (mStickers.size() <= 0 && selectedColor == Color.parseColor("#ffffff")) {
            Toast.makeText(Custom_CaseEditActivity.this, getString(R.string.please_select_image), Toast.LENGTH_SHORT).show();
        } else {
            Log.e("COLOR", "addtocart: " + bground + "========>" + selectedColor);
//            bground = changeBitmapColor(bground, selectedColor);
            background.setImageBitmap(bground);
//            stickerView.setBackgroundColor(selectedColor);
            if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
                Log.e("CHECKCART", "addtocart: 1--" );
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Custom_CaseEditActivity.this);
                alertDialog.setTitle(getString(R.string.log_in));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.need_login));
                alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent intent = new Intent(Custom_CaseEditActivity.this, LogInActivity.class);
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
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Custom_CaseEditActivity.this);
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

//    private void sendData() {
//        id_add_photo.setVisibility(View.GONE);
//        progress = ProgressDialog.show(Custom_CaseEditActivity.this, "", getString(R.string.loading), true, false);
//        stickerView.setControlItemsHidden();
//        savelayout.setDrawingCacheEnabled(true);
//        savelayout.buildDrawingCache();
//        maskableFrameLayout.setDrawingCacheEnabled(true);
//        maskableFrameLayout.buildDrawingCache();
//        try {
//            new createcover().execute();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("EXP", "sendData: " + e.getMessage());
//        }
//
//    }

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
//        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, (int) (Share.screenHeight * 0.038), bm.getWidth(), bm.getHeight() - (int) (Share.screenHeight * 0.038 * 2));
        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());

        Log.e("withcrop", "==>" + croppedBitmap.getWidth() + "==>" + croppedBitmap.getHeight());

        Bitmap bitmap = Bitmap.createBitmap(croppedBitmap.getWidth(), croppedBitmap.getHeight(), Bitmap.Config.ARGB_8888);


//        if (bm != null && !bm.isRecycled()) {
//            bm.recycle();
//        }
//        if (croppedBitmap != null && !croppedBitmap.isRecycled()) {
//            croppedBitmap.recycle();
//        }
        Canvas canvas = new Canvas(bitmap);

        canvas.scale(-1, 1, croppedBitmap.getWidth() / 2, croppedBitmap.getHeight() / 2);
//        canvas.rotate(90);

//        canvas.translate(getWidth(), 0);
//        canvas.rotate(90, getWidth() / 2, 0);
//        canvas.translate(getHeight() / 2, getWidth() / 2);

//        canvas.translate(croppedBitmap.getWidth(),0);
//        canvas.rotate(90, croppedBitmap.getWidth() / 2, 0);
//        canvas.translate(croppedBitmap.getHeight() / 2, croppedBitmap.getWidth()/ 2);
        canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());

        return getResizedBitmap(bitmap, width * 300, height * 300);
//        return croppedBitmap;
    }

    public Bitmap getBitmapPrint(Bitmap bm) {
        final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inTargetDensity = 1;
        bm.setDensity(Bitmap.DENSITY_NONE);

        int fromHere = (int) (bm.getHeight() * 0.2);

        Log.e("withoutcrop", "==>" + bm.getWidth() + "==>" + bm.getHeight());
        ImageView imageView = new ImageView(this);
        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, (int) (Share.screenHeight * 0.038), bm.getWidth(), bm.getHeight() - (int) (Share.screenHeight * 0.038 * 2));
        // Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());

        Log.e("withcrop", "==>" + croppedBitmap.getWidth() + "==>" + croppedBitmap.getHeight());

        // Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);

        //   Canvas canvas = new Canvas(bitmap);

        //canvas.scale(-1, 1, bm.getWidth() / 2, bm.getHeight() / 2);
        //  canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());
        return croppedBitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, float newWidth, float newHeight) {
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

    /* public Bitmap getBitmapPrint(Bitmap bm, String width, String height) {
         final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
         bitmapOptions.inTargetDensity = 1;
         bm.setDensity(Bitmap.DENSITY_NONE);

         int fromHere = (int) (bm.getHeight() * 0.2);

         ImageView imageView = new ImageView(this);
         Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 13, bm.getWidth(), bm.getHeight() - 26);
         Bitmap bitmap = null;
         if (getIntent().hasExtra("width")) {
             bitmap = Bitmap.createBitmap(Integer.parseInt(width), Integer.parseInt(height), Bitmap.Config.ARGB_8888);

         } else {
             bitmap = Bitmap.createBitmap(croppedBitmap.getWidth(), croppedBitmap.getHeight(), Bitmap.Config.ARGB_8888);

         }

         float scaleX = Integer.parseInt(width) / (float) bitmap.getWidth();
         float scaleY = Integer.parseInt(height) / (float) bitmap.getHeight();
         float pivotX = 0;
         float pivotY = 0;

         Matrix scaleMatrix = new Matrix();
         scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

         //   Canvas canvas = new Canvas(resizedBitmap);

         Canvas canvas = new Canvas(bitmap);
         canvas.setMatrix(scaleMatrix);
         canvas.scale(-1, 1, bm.getWidth() / 2, bm.getHeight() / 2);
         // canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());
         canvas.drawBitmap(croppedBitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
         //   canvas.drawBitmap(croppedBitmap, null, new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), null);
         return bitmap;
     }
 */
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

//    public Bitmap getResizedBitmapprint(Bitmap bm, int newHeight, int newWidth) {
//        int width = bm.getWidth();
//        int height = bm.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // create a matrix for the manipulation
//        Matrix matrix = new Matrix();
//        // resize the bit map
//        matrix.postScale(scaleWidth, scaleHeight);
//        // recreate the new Bitmap
//        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
//        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
//    }

//    public static Bitmap loadBitmapFromView(RelativeLayout v) {
//        Bitmap b = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
//        v.draw(c);
//        return b;
//    }

    public Bitmap combineImages(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
        Bitmap cs = null;

        int width = c.getWidth(), height = s.getHeight();

//        if(c.getWidth() > s.getWidth()) {
//            width = c.getWidth() + s.getWidth();
//            height = c.getHeight();
//        } else {
//            width = s.getWidth() + s.getWidth();
//            height = c.getHeight();
//        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, 0f, 0f, null);

        // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

    OutputStream os = null;
    try {
      os = new FileOutputStream(loc + tmpImg);
      cs.compress(CompressFormat.PNG, 100, os);
    } catch(IOException e) {
      Log.e("combineImages", "problem combining images", e);
    }*/

        return cs;
    }

    private void setDiemns() {

        Log.e("DIMENSION", "setDiemns: " + getResources().getDisplayMetrics().density);

//        if (DisplayMetrics.DENSITY_LOW==getResources().getDisplayMetrics().density || DisplayMetrics.DENSITY_MEDIUM==getResources().getDisplayMetrics().density || DisplayMetrics.DENSITY_HIGH==getResources().getDisplayMetrics().density || DisplayMetrics.DENSITY_XHIGH==getResources().getDisplayMetrics().density){
        if (getResources().getDisplayMetrics().density <= 2.0) {
            Log.e("DEVICENAME", "setDiemns: " + DeviceName.getDeviceName());
            if (getResources().getDisplayMetrics().density == 0.75 || getResources().getDisplayMetrics().density == 1.0) {

                Log.e("DENSITY", "setDiemns: ========>LDPI======>MDPI");

                width = (int) (Share.maskwidth * 100);
                height = (int) (Share.maskheight * 100);
            } else if (getResources().getDisplayMetrics().density == 1.5) {
                Log.e("DENSITY", "setDiemns: ========>HDPI");
                width = (int) (Share.maskwidth * 100);
                height = (int) (Share.maskheight * 100);
            } else if (getResources().getDisplayMetrics().density == 2.0) {
                Log.e("DENSITY", "setDiemns: ========>XHDPI");
                if (String.valueOf(Share.maskheight).equalsIgnoreCase("8.48")) {
                    width = (int) (Share.maskwidth * 80);
                    height = (int) (Share.maskheight * 80);
                } else {
                    width = (int) (Share.maskwidth * 130);
                    height = (int) (Share.maskheight * 130);
                }
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
            if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.14")) {
                Log.e("DIMENS", "setDiemns: =============>");
                width = (int) (Share.maskwidth * 80);
                height = (int) (Share.maskheight * 150);
            } else {
                width = (int) (Share.maskwidth * 200);
                height = (int) (Share.maskheight * 200);
            }
        }

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
       /* DrawableSticker drawableSticker = new DrawableSticker(getDrawable(R.drawable.ic_phone_cases));
        drawableSticker.setTag("main");
        stickerView.addSticker(drawableSticker);*/
    }


//    private void setagainDiemns() {
//
//
////        Share.maskwidth = (float) 3.26;
////        Share.maskheight = (float) 5.47;
//
//        Log.e("DIMENSION", "setDiemns: " + getResources().getDisplayMetrics().density);
//
////        if (DisplayMetrics.DENSITY_LOW==getResources().getDisplayMetrics().density || DisplayMetrics.DENSITY_MEDIUM==getResources().getDisplayMetrics().density || DisplayMetrics.DENSITY_HIGH==getResources().getDisplayMetrics().density || DisplayMetrics.DENSITY_XHIGH==getResources().getDisplayMetrics().density){
//        if (getResources().getDisplayMetrics().density <= 2.0) {
//            Log.e("DEVICENAME", "setDiemns: " + DeviceName.getDeviceName());
//            if (getResources().getDisplayMetrics().density == 0.75 || getResources().getDisplayMetrics().density == 1.0) {
//
//                Log.e("DENSITY", "setDiemns: ========>LDPI======>MDPI");
//
//                width = (int) (Share.maskwidth * 100);
//                height = (int) (Share.maskheight * 100);
//            } else if (getResources().getDisplayMetrics().density == 1.5) {
//                Log.e("DENSITY", "setDiemns: ========>HDPI");
//                width = (int) (Share.maskwidth * 100);
//                height = (int) (Share.maskheight * 100);
//            } else if (getResources().getDisplayMetrics().density == 2.0) {
//                Log.e("DENSITY", "setDiemns: ========>XHDPI");
//                if (String.valueOf(Share.maskheight).equalsIgnoreCase("8.48")) {
//                    width = (int) (Share.maskwidth * 80);
//                    height = (int) (Share.maskheight * 80);
//                } else {
//                    width = (int) (Share.maskwidth * 130);
//                    height = (int) (Share.maskheight * 130);
//                }
//            } else if (getResources().getDisplayMetrics().density == 1.75) {
//                Log.e("DENSITY", "setDiemns: ========>XHDPI2");
//                if (DeviceName.getDeviceName().equalsIgnoreCase("Nokia 2.1")) {
//                    width = (int) (Share.maskwidth * 120);
//                    height = (int) (Share.maskheight * 120);
//                } else {
//                    width = (int) (Share.maskwidth * 140);
//                    height = (int) (Share.maskheight * 140);
//                }
//
//            } else {
//                if (DeviceName.getDeviceName().equalsIgnoreCase("Nokia 1")) {
//                    width = (int) (Share.maskwidth * 100);
//                    height = (int) (Share.maskheight * 100);
//                } else {
//                    width = (int) (Share.maskwidth * 150);
//                    height = (int) (Share.maskheight * 150);
//                }
//            }
//        } else {
//            if (String.valueOf(Share.maskheight).equalsIgnoreCase("6.14")) {
//                Log.e("DIMENS", "setDiemns: =============>");
//                width = (int) (Share.maskwidth * 80);
//                height = (int) (Share.maskheight * 150);
//            } else {
//                width = (int) (Share.maskwidth * 200);
//                height = (int) (Share.maskheight * 200);
//            }
//        }
//
//        maskableFrameLayout.getLayoutParams().width = width;
//        maskableFrameLayout.getLayoutParams().height = height;
//
////        stickerView.getLayoutParams().width = width;
////        stickerView.getLayoutParams().height = height;
////
////        background.getLayoutParams().width = width;
////        background.getLayoutParams().height = height;
////
////        up.getLayoutParams().width = width;
////        up.getLayoutParams().height = height;
//        maskableFrameLayout.setMask(R.drawable.mask_patiyu);
////        background.setImageBitmap(bground);
////
////
////        up.setImageBitmap(up_image);
//       /* DrawableSticker drawableSticker = new DrawableSticker(getDrawable(R.drawable.ic_phone_cases));
//        drawableSticker.setTag("main");
//        stickerView.addSticker(drawableSticker);*/
//    }


    private void selectImage() {
        Log.e("CHECKDIALOG", "selectImage: dialog --9--" );
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_gallery),
                getString(R.string.cancel)};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Custom_CaseEditActivity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    if (checkAndRequestPermissionsCamera(1)) {
                        ImagePicker.pickImage(Custom_CaseEditActivity.this, "Select your image:");
                    }
                } else if (items[item].equals(getString(R.string.choose_from_gallery))) {
                    if (checkAndRequestPermissionsStorage(2)) {
                        Intent i = new Intent(Custom_CaseEditActivity.this, FaceActivity.class);
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
                        ActivityCompat.requestPermissions(Custom_CaseEditActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.requestPermissions(Custom_CaseEditActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            ActivityCompat.requestPermissions(Custom_CaseEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        } else {
                            ActivityCompat.requestPermissions(Custom_CaseEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
                        }
                    }

                } else {

                    if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                        //allowed
                        Log.e("allowed", permission);

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

        if (ContextCompat.checkSelfPermission(Custom_CaseEditActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Custom_CaseEditActivity.this, new String[]{Manifest.permission.CAMERA},
                    code);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkAndRequestPermissionsStorage(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(Custom_CaseEditActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Custom_CaseEditActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        code);
                return false;
            } else {

                return true;
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(Custom_CaseEditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Custom_CaseEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(Custom_CaseEditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(Custom_CaseEditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Custom_CaseEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
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
                Log.e("DELETE", "onStickerDeleted:====>1=====> " + mStickers.size());
                if (mStickers.size() == 0) {
                    Log.e("DELETE", "onStickerDeleted:====>2=====>" + mStickers.size());

                    id_add_photo.setVisibility(View.VISIBLE);
                }

                Log.e("DELETE", "onStickerDeleted:====>3=====>" + mStickers.size());

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
//                id_add_photo.setVisibility(View.VISIBLE);
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
        Log.e("CHECKGALLERY", "openGallery: 1" );
        //selectImage();
        if (checkAndRequestPermissionsStorage(2)) {
            Intent i = new Intent(Custom_CaseEditActivity.this, FaceActivity.class);
            i.putExtra("activity", "HomeActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
    }

    public void pickColor(View view) {
        ColorPickerDialogBuilder
                .with(Custom_CaseEditActivity.this)
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
                        iscolor = true;
                        Custom_CaseEditActivity.this.selectedColor = selectedColor;
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
        Intent intent = new Intent(Custom_CaseEditActivity.this, FontActivity.class);
        startActivity(intent);
    }

    public void openSticker(View view) {
        Intent intent = new Intent(Custom_CaseEditActivity.this, StickerActivity.class);
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

    private void findViews() {
        background = findViewById(R.id.background);
        up = findViewById(R.id.up);
        maskableFrameLayout = findViewById(R.id.maskable);
        savelayout = findViewById(R.id.savelayout);
        id_add_photo = findViewById(R.id.id_add_photo);
        menuYellow = findViewById(R.id.menu_yellow);
        iv_default_image = findViewById(R.id.iv_default_image);
        iv_default_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Custom_CaseEditActivity.this, Background_image_activity.class);
                startActivity(intent);
            }
        });
        fab_faceactivity = findViewById(R.id.fab_faceactivity);
        fab_faceactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkAndRequestPermissions()) {
                    if (menuYellow.isOpened()) {
                        closeContextMenu();
                        Intent i = new Intent(Custom_CaseEditActivity.this, FaceActivity.class);
                        i.putExtra("activity", "HomeActivity");
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }

                } else {
//                    image_name = "gallery_bg";
                    ActivityCompat.requestPermissions(Custom_CaseEditActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), STORAGE_PERMISSION_CODE);
                }

//                Intent intent=new Intent(CaseEditActivity.this,FaceActivity.class);
//                startActivity(intent);
            }
        });

//        fabMenu=findViewById(R.id.fabMenu);

    }

    private boolean checkAndRequestPermissions() {
        listPermissionsNeeded.clear();

        int writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int readMediaStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES);
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (writeStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
        if (readStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU) {
            if (readMediaStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
            }
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        Log.e("TAG", "listPermissionsNeeded===>" + listPermissionsNeeded);
        return listPermissionsNeeded.isEmpty();
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
            stickerView.addStickerMain(drawableSticker);
            Share.bitmap = null;


        }

        Log.e("BD_DRAWABLE", "onResume: " + Share.bg_drawable);
        if (Share.bg_drawable != null) {
            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bg_drawable));
            drawableSticker.setTag("maindraw");
            stickerView.addStickerMain_bg(drawableSticker, 0);
            Share.bg_drawable = null;
        }

        if (Share.imageuri.equalsIgnoreCase("")) {

        } else {

            final ProgressDialog pd = ProgressDialog.show(Custom_CaseEditActivity.this, "", getString(R.string.loading), true, false);
            Glide.with(Custom_CaseEditActivity.this).asBitmap().load(Share.imageuri).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(resource));
                    drawableSticker.setTag("cartoon");
                    stickerView.addSticker(drawableSticker);
                    Share.imageuri = "";
                    pd.dismiss();
                }
            });
        }

        if (Share.FONT_FLAG) {
            Share.FONT_FLAG = false;
            DrawableSticker drawableSticker = Share.TEXT_DRAWABLE;
            drawableSticker.setTag("text");
            stickerView.addSticker(drawableSticker);
            drawables_sticker.add(drawableSticker);
        }

    }

    private void setHeader() {
        ImageView imageView = findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stickerView = null;
        System.gc();
    }

    private void create_preview_bitmap() {
//        up.setVisibility(View.GONE);
        id_add_photo.setVisibility(View.GONE);
        stickerView.setControlItemsHidden();
        savelayout.setDrawingCacheEnabled(true);
        savelayout.buildDrawingCache();

        //progress = ProgressDialog.show(Custom_CaseEditActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(Custom_CaseEditActivity.this);
        new create_bitmap().execute();
    }

    private void sendData() {
        up.setVisibility(View.VISIBLE);
        savelayout.setDrawingCacheEnabled(true);
        savelayout.buildDrawingCache();
//        setDiemns();
        new crateReq().execute();
    }

    private class createcover extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress.show();
            showProgressDialog(Custom_CaseEditActivity.this);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //progress.dismiss();
            hideProgressDialog();
            maskableFrameLayout.setMask(R.drawable.mask_patiyu);
            new crateReq().execute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    private class crateReq extends AsyncTask<Void, Void, Void> {

        MultipartBody.Builder builder;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress.show();
            showProgressDialog(Custom_CaseEditActivity.this);
            builder = new MultipartBody.Builder();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           /* if (progress != null && progress.isShowing())
                progress.dismiss();*/


            if (builder != null) {
                MultipartBody multipartBody = builder.build();
                APIService apiService = new MainApiClient(Custom_CaseEditActivity.this).getApiInterface();
                Call<Cart> cartCall = apiService.sendCart(multipartBody);
                cartCall.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        if (response != null) {
                            if (response.body().getcart_data().getStatus() == 1) {
                                upload = true;
                                mStickers.clear();
                                Share.resultbitmap = null;
                                Share.final_result_bitmap = null;
                                Share.edit_image = false;
                                //progress.dismiss();
                                hideProgressDialog();
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
                                AlertDialog alertDialog = new AlertDialog.Builder(Custom_CaseEditActivity.this).create();
                                alertDialog.setTitle(getString(R.string.upload_fail));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(response.body().getcart_data().getMessage());
                                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //progress.dismiss();
                                        hideProgressDialog();
                                        dialog.dismiss();
                                    }
                                });

                                alertDialog.show();
                            }
                        }
                        Log.d("response", "==>" + response.toString());
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Log.d("response", "Failed==>" + t.toString());
//                        if (progress != null && progress.isShowing())
//                            progress.dismiss();
                        hideProgressDialog();

                        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(Custom_CaseEditActivity.this).create();
                            alertDialog.setTitle(getString(R.string.time_out));
                            alertDialog.setMessage(getString(R.string.connect_time_out));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    sendData();
                                }
                            });
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(Custom_CaseEditActivity.this).create();
                            alertDialog.setTitle(getString(R.string.internet_connection));
                            alertDialog.setMessage(getString(R.string.slow_connect));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    sendData();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                });
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Log.e("width","==>if"+getIntent().getIntExtra("width",0));
//            if (getIntent().hasExtra("width")) {
//
//                print_bitmap = getBitmapPrint(savelayout.getDrawingCache(), Float.parseFloat(getIntent().getStringExtra("width")), Float.parseFloat(getIntent().getStringExtra("height")));
////                print_bitmap=RotateBitmap(getResizedBitmapCover(CropBitmapTransparency(print_bitmap),(int) Float.parseFloat(getIntent().getStringExtra("width"))*300,(int)Float.parseFloat(getIntent().getStringExtra("height"))*300),270);
////                print_bitmap=newRotateBitmap(getResizedBitmapCover(print_bitmap,(int) Float.parseFloat(getIntent().getStringExtra("height"))*280,(int) Float.parseFloat(getIntent().getStringExtra("width"))*280),270);
//                print_bitmap = newRotateBitmap(print_bitmap, 270);
//                Log.e("with_width", "==>if");
//
//            } else {
//                print_bitmap = getBitmapPrint(stickerView.createBitmap());
//            }
            cover_bitmap = RotateBitmap(getResizedBitmapCover(CropBitmapTransparency(savelayout.getDrawingCache()), height, width), 270);
            maskableFrameLayout.setAlwaysDrawnWithCacheEnabled(false);
            savelayout.setAlwaysDrawnWithCacheEnabled(false);
//            File file_printphoto = getFile("printphoto_" + System.currentTimeMillis(), final_bitmap, ".jpg");
            File file_cover_bitmap = getFile("cover_bitmap_" + System.currentTimeMillis(), cover_bitmap, ".png");
            Log.d("file_printphoto", "Size==>" + file_printphoto.length() / 1024);
            Log.d("file_cover_bitmap", "Size==>" + file_cover_bitmap.length() / 1024);
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("user_id", SharedPrefs.getString(Custom_CaseEditActivity.this, Share.key_ + RegReq.id));
            builder.addFormDataPart("quantity", "1");
            builder.addFormDataPart("model_id", getIntent().getStringExtra("model_id"));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_printphoto);
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file_cover_bitmap);
            builder.addFormDataPart("print_image", file_printphoto.getName(), requestBody);
            builder.addFormDataPart("case_image", file_cover_bitmap.getName(), requestBody1);
            builder.addFormDataPart("case_image_id", Share.case_id);
            builder.addFormDataPart("ln", Locale.getDefault().getLanguage());

            return null;
        }
    }

    public class create_bitmap extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //progress.dismiss();
            hideProgressDialog();
            sendData();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            print_bitmap = getResizedBitmapCover(CropBitmapTransparency(savelayout.getDrawingCache()), height * 6, width * 6);
            print_bitmap = stickerView.createBitmap();
            print_bitmap = getBitmapPrint(print_bitmap, Float.parseFloat(getIntent().getStringExtra("width")), Float.parseFloat(getIntent().getStringExtra("height")));
//            print_bitmap = Bitmap.createScaledBitmap(print_bitmap,(int)Share.maskwidth*300,(int)Share.maskheight*300,true);
//            Matrix matrix = new Matrix();
//            matrix.preScale(-1.0f, 1.0f);
//            final_bitmap = Bitmap.createBitmap(CropBitmapTransparency(print_bitmap), 0, 0, print_bitmap.getWidth(), print_bitmap.getHeight(), matrix, true);
//            savelayout.setAlwaysDrawnWithCacheEnabled(false);

            final_bitmap = print_bitmap;
            final_bitmap = newRotateBitmap(final_bitmap, 270);
            file_printphoto = getFile("printphoto_" + System.currentTimeMillis(), final_bitmap, ".png");
            return null;

        }
    }

}
