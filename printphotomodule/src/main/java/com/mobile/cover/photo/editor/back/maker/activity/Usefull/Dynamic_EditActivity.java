package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;
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
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ModelListActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FirebaseEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.sipper.ShipperCategoryActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.customView.MaskableFrameLayout;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.DrawableSticker;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.Sticker;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView;
import com.mobile.cover.photo.editor.back.maker.model.Cart;
import com.mobile.cover.photo.editor.back.maker.model.RegData;
import com.mobile.cover.photo.editor.back.maker.model.RegResponse;
import com.mobile.cover.photo.editor.back.maker.testing_modules.PhotoPickupImageActivity;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Mug_MainActivity;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Shipper_MainActivity;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

public class Dynamic_EditActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 123556;
    private static final long MIN_CLICK_INTERVAL = 1500;
    public static StickerView stickerView;
    public static int width;
    public static int height;
    public static Bitmap print_bitmap = null;
    public static Bitmap new_result = null;
    public static Bitmap final_bitmap = null;
    public static Bitmap cover_bitmap = null;
    ImageView background, up, iv_default_image;
    MaskableFrameLayout maskableFrameLayout;
    Bitmap msk = null, bground = null, up_image = null;
    Bitmap bitmap;
    TextView title;
    RelativeLayout savelayout;
    int selectedColor = Color.parseColor("#ffffff");
    List<Bitmap> disp_bitmap_array = new ArrayList<>();
    ProgressDialog progress;
    LinearLayout id_add_photo;
    FloatingActionButton fab_faceactivity;
    File file_printphoto;
    AlertDialog alertDialog;
    ProgressDialog pd;
    AlertDialog.Builder alertDialog_build;
    private int PICK_IMAGE_REQUEST = 101;
    private FloatingActionMenu menuYellow;
    private List<String> listPermissionsNeeded = new ArrayList<>();
    private int STORAGE_PERMISSION_CODE = 23;
    private String image_name = "";
    private long mLastClickTime;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dynamic_edit);
        mContext = Dynamic_EditActivity.this;
        Log.e("int", "==>" + selectedColor);
        stickerView = findViewById(R.id.id_stickerview);
        StickerView.mStickers.clear();
        Share.bitmap = null;
        Share.imageuri = "";

        // TODO: 03/11/18  Dont you dare touch this process of editing or else you will get punished!!!!!
        if (Share.bitmapHashMap != null) {
            msk = Share.bitmapHashMap.get(Share.key_msk_imge);
            bground = Share.bitmapHashMap.get(Share.key_msk_imge);
            up_image = Share.bitmapHashMap.get(Share.key_normal_image);
        }


        if (Share.bitmap == null) {
            mStickers.clear();
        }

        if (Share.edit_image) {
            Log.e("AATLEAAIVO", "onResume: +=================>3");
            final ProgressDialog pd = ProgressDialog.show(Dynamic_EditActivity.this, "", getString(R.string.loading), true, false);

            Share.edit_image = false;
            Glide.with(Dynamic_EditActivity.this).asBitmap()
                    .load(Share.edited_image)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Log.e("HEIGHT", "onResourceReady: ==========>" + resource.getHeight() + "========>" + resource.getWidth());
                            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(resource));
                            drawableSticker.setTag("maindraw");
                            stickerView.dynamic_edited_addStickerMain(drawableSticker);
                            Share.edited_image = null;
                            pd.dismiss();
                        }
                    });
        }


        setHeader();
        getDisplaySize();
        findViews();
        setDiemns();
        intView();

        if (Share.bitmap_testing != null) {
            //                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Share.bitmap);
            // Log.d(TAG, String.valueOf(bitmap));
            Share.lst_album_image.clear();
            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bitmap_testing));
            drawableSticker.setTag("maindraw");
            stickerView.addSticker(drawableSticker);
            Share.bitmap = null;
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
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return;

        Log.e("COLOR", "addtocart: " + bground + "========>" + selectedColor);
        background.setImageBitmap(bground);
        if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dynamic_EditActivity.this);
            alertDialog.setTitle(getString(R.string.log_in));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.need_login));
            alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    Intent intent = new Intent(Dynamic_EditActivity.this, LogInActivity.class);
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

        Log.e("CHECKCART", "addtocart: 5--" );
        alertDialog_build = new AlertDialog.Builder(Dynamic_EditActivity.this);
        alertDialog_build.setCancelable(false);
        alertDialog_build.setMessage(getString(R.string.add_to_cart));
        alertDialog_build.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                if (StickerView.mStickers.size() == 0) {
                    Toast.makeText(Dynamic_EditActivity.this, getString(R.string.add_image_sticker), Toast.LENGTH_SHORT).show();
                } else {
                    if (ModelListActivity.activity != null) {
                        ModelListActivity.activity.finish();
                    }

                    create_preview_bitmap();
                }

            }
        });
        alertDialog_build.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        alertDialog_build.create().show();

//        }
    }

    private void create_preview_bitmap() {
        up.setVisibility(View.GONE);
        id_add_photo.setVisibility(View.GONE);
        stickerView.setControlItemsHidden();
        savelayout.setDrawingCacheEnabled(true);
        savelayout.buildDrawingCache();

        progress = ProgressDialog.show(Dynamic_EditActivity.this, "", getString(R.string.loading), true, false);
        new create_bitmap().execute();
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

    private void sendData() {
        up.setVisibility(View.VISIBLE);
        savelayout.setDrawingCacheEnabled(true);
        savelayout.buildDrawingCache();
        new crateReq().execute();
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

        Log.e("with_outcrop", "==>" + bm.getWidth() + "==>" + bm.getHeight());
        Log.e("with_outcrop", "==>" + width + "==>" + height);
        ImageView imageView = new ImageView(this);
//        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, (int) (Share.screenHeight * 0.038), bm.getWidth(), bm.getHeight() - (int) (Share.screenHeight * 0.038 * 2));
        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());

        Log.e("with_crop", "==>" + croppedBitmap.getWidth() + "==>" + croppedBitmap.getHeight());

        Bitmap bitmap = Bitmap.createBitmap(croppedBitmap.getWidth(), croppedBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        canvas.scale(-1, 1, croppedBitmap.getWidth() / 2, croppedBitmap.getHeight() / 2);
        canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());

        return Bitmap.createScaledBitmap(bitmap, (int) (width * 300), (int) (height * 300), true);
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

        Log.e("HEIGTH+WIDTH", "getResizedBitmapCover: =====>" + height + "=====>" + newHeight + "====>" + width);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
    }

    public Bitmap combineImages(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
        Bitmap cs = null;

        int width = c.getWidth(), height = s.getHeight();
        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, 0f, 0f, null);

        return cs;
    }

    private void setDiemns() {

        try {
            // return 0.75 if it's LDPI
            // return 1.0 if it's MDPI
            // return 1.5 if it's HDPI
            // return 2.0 if it's XHDPI
            // return 3.0 if it's XXHDPI
            // return 4.0 if it's XXXHDPI

            Log.e("DIMENSION", "setDiemns: " + getResources().getDisplayMetrics().density + "=======>");

            if (Share.category_id == 5) {

                width = (int) (Share.display_width * 250);
                height = (int) (Share.displayheight * 250);


                if (getResources().getDisplayMetrics().density == 0.75 || getResources().getDisplayMetrics().density == 1.0) {
                    width = (int) (Share.display_width * 250);
                    height = (int) (Share.displayheight * 250);
                } else if (getResources().getDisplayMetrics().density == 1.5) {
                    width = (int) (Share.display_width * 250);
                    height = (int) (Share.displayheight * 250);
                } else if (getResources().getDisplayMetrics().density == 1.75 || getResources().getDisplayMetrics().density == 2.0) {
                    if (android.os.Build.MODEL.equalsIgnoreCase("TECNO IN1")) {
                        width = (int) (Share.display_width * 400);
                        height = (int) (Share.displayheight * 350);
                    } else {
                        width = (int) (Share.display_width * 400);
                        height = (int) (Share.displayheight * 400);
                    }

                } else if (getResources().getDisplayMetrics().density == 3.0) {
                    width = (int) (Share.display_width * 500);
                    height = (int) (Share.displayheight * 500);
                } else {
                    width = (int) (Share.display_width * 600);
                    height = (int) (Share.displayheight * 600);
                }

            } else {
                if (getResources().getDisplayMetrics().density == 0.75 || getResources().getDisplayMetrics().density == 1.0) {
                    width = (int) (Share.display_width * 250);
                    height = (int) (Share.displayheight * 250);
                } else if (getResources().getDisplayMetrics().density == 1.5) {
                    width = (int) (Share.display_width * 160);
                    height = (int) (Share.displayheight * 160);
                } else if (getResources().getDisplayMetrics().density == 1.75) {
                    width = (int) (Share.display_width * 200);
                    height = (int) (Share.displayheight * 200);
                } else if (getResources().getDisplayMetrics().density == 2.0) {
                    width = (int) (Share.display_width * 240);
                    height = (int) (Share.displayheight * 240);
                } else {
                    width = (int) (Share.display_width * 350);
                    height = (int) (Share.displayheight * 350);
                }
            }

        } catch (Exception e) {

            Log.e("Error", e.toString());
        }


    }

    private void selectImage() {
        Log.e("CHECKDIALOG", "selectImage: dialog --4--" );
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_gallery),
                getString(R.string.cancel)};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Dynamic_EditActivity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    if (checkAndRequestPermissionsCamera(1)) {
                        ImagePicker.pickImage(Dynamic_EditActivity.this, "Select your image:");
                    }
                } else if (items[item].equals(getString(R.string.choose_from_gallery))) {
                    if (checkAndRequestPermissionsStorage(2)) {

                        Intent i = new Intent(Dynamic_EditActivity.this, FaceActivity.class);
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
                        ActivityCompat.requestPermissions(Dynamic_EditActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.requestPermissions(Dynamic_EditActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            ActivityCompat.requestPermissions(Dynamic_EditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        } else {
                            ActivityCompat.requestPermissions(Dynamic_EditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
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

        if (ContextCompat.checkSelfPermission(Dynamic_EditActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Dynamic_EditActivity.this, new String[]{Manifest.permission.CAMERA},
                    code);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkAndRequestPermissionsStorage(int code) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(Dynamic_EditActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Dynamic_EditActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        code);
                return false;
            } else {

                return true;
            }
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            if (ContextCompat.checkSelfPermission(Dynamic_EditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Dynamic_EditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }
        }else {
            if (ContextCompat.checkSelfPermission(Dynamic_EditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(Dynamic_EditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Dynamic_EditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
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
    }

    public void openGallery(View view) {
        Log.e("CHECKGALLERY", "openGallery: 4" );
        if (checkAndRequestPermissionsStorage(2)) {

            Intent i = new Intent(Dynamic_EditActivity.this, FaceActivity.class);
            i.putExtra("activity", "HomeActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        //selectImage();
    }

    public void pickColor(View view) {
        ColorPickerDialogBuilder
                .with(Dynamic_EditActivity.this)
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
                        Dynamic_EditActivity.this.selectedColor = selectedColor;
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
        Intent intent = new Intent(Dynamic_EditActivity.this, FontActivity.class);
        startActivity(intent);
    }

    public void openSticker(View view) {
        Intent intent = new Intent(Dynamic_EditActivity.this, StickerActivity.class);
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
            Log.e("TAG-->", "captureUri  ");

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
        title = findViewById(R.id.title);
        Log.e("HEADER", "findViews: " + getIntent().getStringExtra("model_name"));
        String titleName = getIntent().getStringExtra("model_name");
        if (titleName != null) {
            titleName = UtilsKt.truncate(titleName, 20);
            title.setText(titleName);
        }
        up = findViewById(R.id.up);
        maskableFrameLayout = findViewById(R.id.maskable);
        savelayout = findViewById(R.id.savelayout);
        id_add_photo = findViewById(R.id.id_add_photo);
        menuYellow = findViewById(R.id.menu_yellow);
        iv_default_image = findViewById(R.id.iv_default_image);
        iv_default_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dynamic_EditActivity.this, Background_image_activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (Share.bitmap != null) {
            //                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Share.bitmap);
            // Log.d(TAG, String.valueOf(bitmap));
            Share.lst_album_image.clear();

            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bitmap));
            drawableSticker.setTag("maindraw");
            stickerView.addStickerMain(drawableSticker);
            Share.bitmap = null;


        }

        if (Share.bg_drawable != null) {
            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bg_drawable));
            drawableSticker.setTag("maindraw");
            stickerView.addStickerMain_bg(drawableSticker, 0);
            Share.bg_drawable = null;
        }

        if (!Share.imageuri.equalsIgnoreCase("")) {
            pd = ProgressDialog.show(Dynamic_EditActivity.this, "", getString(R.string.loading), true, false);
            loadPicture(Share.imageuri, pd);
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

        if (Share.isRegistrationSuccess) {

            Share.isRegistrationSuccess = false;

            String mobile = SharedPrefs.getString(mContext, Share.key_ + RegReq.mobile_1);
            String email = SharedPrefs.getString(mContext, Share.key_ + RegReq.email);

            String cred;
            if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
                cred = mobile;
            } else {
                cred = email;
                for (int i = 0; i < Share.country_mobile_code.size(); i++) {
                    if (Share.country_mobile_code.get(i).getIs_branch() == 1) {
                        if (SharedPrefs.getString(mContext, SharedPrefs.country_code).equalsIgnoreCase(Share.country_mobile_code.get(i).getSortname())) {
                            cred = mobile;
                        }
                    }
                }
            }

            if (Share.tempPassword != null) {
                signin(cred, Share.tempPassword);
                Share.tempPassword = null;
            }

        }
    }

    private void signin(String cred, String password) {
        String androidId = Share.firebaseToken;
        Log.e("androidId", "==>" + androidId);
        pd = ProgressDialog.show(mContext, "", getString(R.string.loading), true, false);
        pd.show();

        APIService apiService = new MainApiClient(mContext).getApiInterface();
        Call<RegResponse> regResponseCall = apiService.getRegResponseLogin(cred, password, androidId, "android", TimeZone.getDefault().getID(), Locale.getDefault().getLanguage());

        regResponseCall.enqueue(new Callback<RegResponse>() {
            @Override
            public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }

                if (response.body() != null) {
                    Log.e("androidId", "==>" + response.body().getResponseCode());


                    pd.dismiss();
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        SharedPrefs.save(mContext, SharedPrefs.CART_COUNT, response.body().getCart_count());
                        HomeMainActivity.tv_nudge_cart_count.setText("" + SharedPrefs.getInt(mContext, SharedPrefs.CART_COUNT));
                        if (response.body().getData() == null) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                            alertDialog.setTitle(getString(R.string.log_in));
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(response.body().getResponseMessage());
                            alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });

                            alertDialog.create().show();
                        } else {
                            SharedPrefs.savePref(mContext, Share.key_reg_suc, true);
                            RegData regData = response.body().getData();
                            Share.isinternational = regData.getIs_international();
                            if (regData != null) {
                                SharedPrefs.save(mContext, Share.key_ + RegReq.name, regData.getName());
                                SharedPrefs.save(mContext, Share.key_ + RegReq.email, regData.getEmail());
                                SharedPrefs.save(mContext, Share.key_ + RegReq.mobile_1, regData.getMobile_1());
                                SharedPrefs.save(mContext, Share.key_ + RegReq.id, regData.getId());
                                SharedPrefs.save(mContext, SharedPrefs.currency_id, "" + regData.getCurrencyId());
                                SharedPrefs.save(mContext, SharedPrefs.country_id, regData.getCountry_id());
                                SharedPrefs.save(mContext, SharedPrefs.country_name, regData.getCountry_name());
//                                SharedPrefs.save(mContext, SharedPrefs.last_country_code, SharedPrefs.getString(mContext, SharedPrefs.country_code));
                                SharedPrefs.save(mContext, SharedPrefs.country_code, regData.getCountry_code());
                                Share.countryCodeValue = regData.getCountry_code();

                                Log.e("UID", "onResponse: " + regData.getId());
                                SharedPrefs.save(mContext, SharedPrefs.uid, regData.getId());
                                SharedPrefs.save(mContext, SharedPrefs.Sellerid, regData.getseller_id());
                                SharedPrefs.save(mContext, SharedPrefs.SELLER, regData.getis_approve());
                                SharedPrefs.save(mContext, SharedPrefs.TOKEN, regData.getToken());

                                Log.e("LOGINDATA", "onResponse: " + regData.getseller_id());
                                Log.e("TOKEN", "onResponse: ========>" + regData.getToken());
                                Log.e("LOGINDATA", "onResponse: " + regData.getis_approve());
                                Log.e("LOGINDATA", "onResponse: " + SharedPrefs.getString(mContext, SharedPrefs.uid));
                                Log.e("LOGINDATA", "onResponse: " + SharedPrefs.getString(mContext, Share.key_ + RegReq.mobile_1));


                                SharedPrefs.save(mContext, SharedPrefs.isapproved, regData.getis_approve());

                                Log.e("ISAPPROVED", "onResponse:==> " + regData.getis_approve());
                            }

                            intView();


                        }


                    } else {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                        alertDialog.setTitle(getString(R.string.log_in));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(response.body().getResponseMessage());
                        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        alertDialog.create().show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.log_in));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.something_went_wrong_try_agaian));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    alertDialog.show();
                }


            }

            @Override
            public void onFailure(Call<RegResponse> call, Throwable t) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {

                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            signin(cred, password);

                        }
                    });
                    alertDialog.show();
                } else {

                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            signin(cred, password);

                        }
                    });
                    alertDialog.show();
                }
            }
        });
        // new login().execute();

    }

    private void loadPicture(final String photoUrl, final ProgressDialog pd) {
        Glide.with(Dynamic_EditActivity.this).asBitmap()
                .load(photoUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(resource));
                        drawableSticker.setTag("cartoon");
                        stickerView.addSticker(drawableSticker);
                        pd.dismiss();
                        Share.imageuri = "";
                    }

                    @Override
                    public void onLoadFailed(Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        pd.dismiss();
                        Share.imageuri = "";
                        if (alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        alertDialog = new AlertDialog.Builder(Dynamic_EditActivity.this).create();
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
        System.gc();
        stickerView = null;
    }

    public class create_bitmap extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
            sendData();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            print_bitmap = getResizedBitmapCover(CropBitmapTransparency(savelayout.getDrawingCache()), height, width);
//            print_bitmap = getResizedBitmap(CropBitmapTransparency(print_bitmap), Share.maskwidth * 300, Share.maskheight * 300);
//            print_bitmap = Bitmap.createScaledBitmap(print_bitmap, (int) (Share.maskwidth * 300), (int) (Share.maskheight * 300), true);
            print_bitmap = getBitmapPrint(CropBitmapTransparency(savelayout.getDrawingCache()), Share.maskwidth, Share.maskheight);
//            print_bitmap = Bitmap.createScaledBitmap(print_bitmap,(int)Share.maskwidth*300,(int)Share.maskheight*300,true);
//            Matrix matrix = new Matrix();
//            matrix.preScale(-1.0f, 1.0f);
//            final_bitmap = Bitmap.createBitmap(CropBitmapTransparency(print_bitmap), 0, 0, print_bitmap.getWidth(), print_bitmap.getHeight(), matrix, true);
            final_bitmap = print_bitmap;
//            savelayout.setAlwaysDrawnWithCacheEnabled(false);
            file_printphoto = getFile("printphoto_" + System.currentTimeMillis(), final_bitmap, ".png");
            return null;

        }
    }

    private class crateReq extends AsyncTask<Void, Void, Void> {

        MultipartBody.Builder builder;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(Dynamic_EditActivity.this, "", getString(R.string.loading), true, false);
//            stickerView.setBackground(new BitmapDrawable(Share.final_result_bitmap));

            builder = new MultipartBody.Builder();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (builder != null) {
                MultipartBody multipartBody = builder.build();
                APIService apiService = new MainApiClient(Dynamic_EditActivity.this).getApiInterface();
                Call<Cart> cartCall = apiService.sendCart(multipartBody);
                cartCall.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        if (response != null) {
                            if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                                if (response.body().getcart_data() != null) {
                                    if (response.body().getcart_data().getStatus() == 1) {


                                        String contentId = "";
                                        String contentType = "";
                                        String contentAmount = "";

                                        contentId = getIntent().getStringExtra("model_id");
                                        contentType = getIntent().getStringExtra("model_name");
                                        contentAmount = getIntent().getStringExtra("paid_amount");

                                        if (contentId != null && contentType != null && contentAmount != null) {
                                            FBEventsKt.logAddedToCartEvent(Dynamic_EditActivity.this, contentId, contentType, contentAmount);
                                            FirebaseEventsKt.logAddToCartEvent(Dynamic_EditActivity.this, contentId, contentType, Double.parseDouble(contentAmount));
                                        }

                                        upload = true;
                                        mStickers.clear();
                                        Share.edit_image = false;
                                        progress.dismiss();
                                        if (Default_image_activity.Companion.getActivity() != null) {
                                            Default_image_activity.Companion.getActivity().finish();
                                        }
                                        if (Images_activity.activity != null) {
                                            Images_activity.activity.finish();
                                        }
                                        if (PhotoPickupImageActivity.activity != null) {
                                            PhotoPickupImageActivity.activity.finish();
                                        }
                                        if (Mug_MainActivity.activity != null) {
                                            Mug_MainActivity.activity.finish();
                                        }
                                        if (Mug_images_category_activity.mActivity != null) {
                                            Mug_images_category_activity.mActivity.finish();
                                        }
                                        if (Shipper_MainActivity.activity != null) {
                                            Shipper_MainActivity.activity.finish();
                                        }
                                        if (ShipperCategoryActivity.Companion.getMActivity() != null) {
                                            ShipperCategoryActivity.Companion.getMActivity().finish();
                                        }
                                        finish();
                                        overridePendingTransition(R.anim.app_right_in, R.anim.app_left_out);
                                    } else {
                                        Log.e("FAILURE", "onResponse: " + response.body().getResponseMessage());
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dynamic_EditActivity.this);
                                        alertDialog.setTitle(getString(R.string.upload_fail));
                                        alertDialog.setCancelable(false);
                                        alertDialog.setMessage(response.body().getcart_data().getMessage());
                                        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                progress.dismiss();
                                                dialog.dismiss();
                                            }
                                        });

                                        alertDialog.create().show();
                                    }
                                }

                            } else {
                                progress.dismiss();
                                Toast.makeText(Dynamic_EditActivity.this, "" + response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        Log.d("response", "==>" + response.toString());
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Log.d("response", "Failed==>" + t.toString());
                        if (progress != null && progress.isShowing())
                            progress.dismiss();

                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }

                        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                            alertDialog = new AlertDialog.Builder(Dynamic_EditActivity.this).create();
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
                            alertDialog = new AlertDialog.Builder(Dynamic_EditActivity.this).create();
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
            Log.e("with_width", "==>" + Share.maskwidth);
            Log.e("with_height", "==>" + Share.maskheight);
//            cover_bitmap = RotateBitmap(getResizedBitmapCover(CropBitmapTransparency(savelayout.getDrawingCache()), (int) height, width), 270);


            if (Share.category_id == 117) {
                cover_bitmap = getResizedBitmapCover(CropBitmapTransparency(savelayout.getDrawingCache()), final_bitmap.getHeight(), final_bitmap.getWidth());
            } else {
                cover_bitmap = getResizedBitmapCover(CropBitmapTransparency(savelayout.getDrawingCache()), CropBitmapTransparency(savelayout.getDrawingCache()).getHeight(), CropBitmapTransparency(savelayout.getDrawingCache()).getWidth());
            }

            Matrix matrix = new Matrix();
            matrix.preScale(1.0f, 1.0f);
            cover_bitmap = Bitmap.createBitmap(cover_bitmap, 0, 0, cover_bitmap.getWidth(), cover_bitmap.getHeight(), matrix, true);

            savelayout.setAlwaysDrawnWithCacheEnabled(false);

            File file_cover_bitmap = getFile("cover_bitmap_" + System.currentTimeMillis(), cover_bitmap, ".png");
            Log.d("file_printphoto", "Size==>" + file_printphoto.length() / 1024);
            Log.d("file_cover_bitmap", "Size==>" + file_cover_bitmap.length() / 1024);


            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("user_id", SharedPrefs.getString(Dynamic_EditActivity.this, Share.key_ + RegReq.id));
            builder.addFormDataPart("quantity", "1");
            builder.addFormDataPart("model_id", getIntent().getStringExtra("model_id"));
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file_cover_bitmap);
            RequestBody requestBody;
            if (Share.category_id == 117) {
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_cover_bitmap);
            } else {
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_printphoto);
            }
            builder.addFormDataPart("print_image", file_printphoto.getName(), requestBody);
            builder.addFormDataPart("case_image", file_cover_bitmap.getName(), requestBody1);
            builder.addFormDataPart("ln", Locale.getDefault().getLanguage());

            return null;
        }
    }
}
