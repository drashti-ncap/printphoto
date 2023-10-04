package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

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
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ModelListActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FirebaseEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.customView.MaskableFrameLayout;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.DrawableSticker;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.Sticker;
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView;
import com.mobile.cover.photo.editor.back.maker.model.Cart;
import com.mobile.cover.photo.editor.back.maker.testing_modules.PhotoPickupImageActivity;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Mug_MainActivity;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class CoffeeMugEditActivity extends PrintPhotoBaseActivity {
    private static final long MIN_CLICK_INTERVAL = 1500;
    public static StickerView stickerView;
    public static Bitmap print_bitmap = null;
    public static Bitmap cover_bitmap = null;
    ImageView background, up, iv_default_image;
    MaskableFrameLayout maskableFrameLayout;
    Bitmap msk = null, bground = null, up_image = null;
    int width;
    int height;
    Bitmap bitmap;
    RelativeLayout savelayout;
    int selectedColor = Color.parseColor("#ffffff");
   // ProgressDialog progress, pd;
    AlertDialog alertDialog;
    LinearLayout id_add_photo;
    private int PICK_IMAGE_REQUEST = 101;
    private long mLastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_coffee_mug_edit);
        Share.bitmap = null;
        Share.imageuri = "";
        stickerView = findViewById(R.id.id_stickerview);
        if (Share.bitmapHashMap != null) {
            msk = Share.bitmapHashMap.get(Share.key_msk_imge);
            bground = Share.bitmapHashMap.get(Share.key_msk_imge);
            Log.e("bground", "onCreate: " + Share.bitmapHashMap.get(Share.key_msk_imge));
            Log.e("bground", "onCreate: " + Share.bitmapHashMap.get(Share.key_normal_image));
            up_image = Share.bitmapHashMap.get(Share.key_normal_image);
        }

        mStickers.clear();

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


    private void getDisplaySize() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void findViews() {
        background = findViewById(R.id.background);
        up = findViewById(R.id.up);
        maskableFrameLayout = findViewById(R.id.maskable);
        savelayout = findViewById(R.id.savelayout);
        iv_default_image = findViewById(R.id.iv_default_image);
        iv_default_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoffeeMugEditActivity.this, Background_image_activity.class);
                startActivity(intent);
            }
        });

        id_add_photo = findViewById(R.id.id_add_photo);


    }

    public void addtocart(View v) {

        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return;


        bground = changeBitmapColor(bground, selectedColor);
        background.setImageBitmap(bground);
        stickerView.setBackgroundColor(selectedColor);
        if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CoffeeMugEditActivity.this);
            alertDialog.setTitle(getString(R.string.log_in));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.need_login));
            alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    Intent intent = new Intent(CoffeeMugEditActivity.this, LogInActivity.class);
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
            Toast.makeText(CoffeeMugEditActivity.this, getString(R.string.please_select_image), Toast.LENGTH_SHORT).show();
        } else {
            Log.e("CHECKCART", "addtocart: 7--" );
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(CoffeeMugEditActivity.this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.add_to_cart));
            alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    if (ModelListActivity.activity != null) {
                        ModelListActivity.activity.finish();
                    }
                    sendData();
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

    public Bitmap getResizedBitmapCover(Bitmap bm, float newHeight, float newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
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

    public Bitmap getBitmapPrint(Bitmap bm) {
        final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inTargetDensity = 1;
        bm.setDensity(Bitmap.DENSITY_NONE);

        int fromHere = (int) (bm.getHeight() * 0.2);

        ImageView imageView = new ImageView(this);
        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 13, bm.getWidth(), bm.getHeight() - 26);
        Bitmap bitmap = Bitmap.createBitmap(croppedBitmap.getWidth(), croppedBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        canvas.scale(-1, 1, bm.getWidth() / 2, bm.getHeight() / 2);
        canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());
        return bitmap;
    }

    public Bitmap getBitmapPrint(Bitmap bm, Float width, Float height) {
        final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inTargetDensity = 1;
        bm.setDensity(Bitmap.DENSITY_NONE);

        int fromHere = (int) (bm.getHeight() * 0.2);

        Log.e("withoutcrop", "==>" + bm.getWidth() + "==>" + bm.getHeight());
        ImageView imageView = new ImageView(this);
//        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 13, bm.getWidth(), bm.getHeight() - 26);
        Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight());

        Log.e("withcrop", "==>" + croppedBitmap.getWidth() + "==>" + croppedBitmap.getHeight());

        Bitmap bitmap = Bitmap.createBitmap(croppedBitmap.getWidth(), croppedBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        canvas.scale(-1, 1, croppedBitmap.getWidth() / 2, croppedBitmap.getHeight() / 2);
        canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());

        return getResizedBitmap(bitmap, height * 300, width * 300);
    }


    private void setDiemns() {


        if (background == null) {
            Toast.makeText(CoffeeMugEditActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }


//        width = (int) (Share.screenWidth * 0.9);
//        height = (int) (Share.screenHeight * 0.6);

        if (getResources().getDisplayMetrics().density <= 1.5) {
            width = (int) (bground.getWidth() * 0.8);
            height = (int) (bground.getHeight() * 0.8);
        } else {
            if (getResources().getDisplayMetrics().density >= 3) {
                width = bground.getWidth() * 2;
                height = bground.getHeight() * 2;
            } else {
                width = (int) (bground.getWidth() * 1.5);
                height = (int) (bground.getHeight() * 1.5);
            }

        }

        Log.e("SIZE", "setDiemns: " + width + "=====>" + height);
        Log.e("SIZE", "setDiemns: " + bground.getWidth() + "=====>" + bground.getHeight());
        Log.e("SIZE", "setDiemns: " + up_image.getWidth() + "=====>" + up_image.getHeight());

        maskableFrameLayout.getLayoutParams().width = width;
        maskableFrameLayout.getLayoutParams().height = height;

        stickerView.getLayoutParams().width = width;
        stickerView.getLayoutParams().height = height;

        background.getLayoutParams().width = width;
        background.getLayoutParams().height = height;


        if (getResources().getDisplayMetrics().density <= 1.5) {
            up.getLayoutParams().width = (int) (up_image.getWidth() * 0.8);
            up.getLayoutParams().height = (int) (up_image.getHeight() * 0.8);

        } else {

            if (getResources().getDisplayMetrics().density >= 3) {
                up.getLayoutParams().width = up_image.getWidth() * 2;
                up.getLayoutParams().height = up_image.getHeight() * 2;
            } else {
                up.getLayoutParams().width = (int) (up_image.getWidth() * 1.5);
                up.getLayoutParams().height = (int) (up_image.getHeight() * 1.5);
            }
        }
        maskableFrameLayout.setMask(new BitmapDrawable(msk));
        background.setImageBitmap(bground);


        up.setImageBitmap(up_image);
       /* DrawableSticker drawableSticker = new DrawableSticker(getDrawable(R.drawable.ic_phone_cases));
        drawableSticker.setTag("main");
        stickerView.addSticker(drawableSticker);*/
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
            }

            @Override
            public void onStickerDragFinished(Sticker sticker) {
            }

            @Override
            public void onStickerZoomFinished(Sticker sticker) {
            }

            @Override
            public void onStickerAdd(Sticker sticker) {
                if (mStickers.size() > 0) {
                    id_add_photo.setVisibility(View.GONE);
                }
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
        Log.e("CHECKGALLERY", "openGallery: 2" );
        if (checkAndRequestPermissionsStorage(2)) {
            Intent i = new Intent(CoffeeMugEditActivity.this, FaceActivity.class);
            i.putExtra("activity", "HomeActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        //selectImage();
    }

    private void selectImage() {
        Log.e("CHECKDIALOG", "selectImage: dialog --2--" );
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_gallery), getString(R.string.cancel)};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CoffeeMugEditActivity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    if (checkAndRequestPermissionsCamera(1)) {
                        ImagePicker.pickImage(CoffeeMugEditActivity.this, "Select your image:");
                    }
                } else if (items[item].equals(getString(R.string.choose_from_gallery))) {
                    if (checkAndRequestPermissionsStorage(2)) {
                        Intent i = new Intent(CoffeeMugEditActivity.this, FaceActivity.class);
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

    private boolean checkAndRequestPermissionsCamera(int code) {

        if (ContextCompat.checkSelfPermission(CoffeeMugEditActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CoffeeMugEditActivity.this, new String[]{android.Manifest.permission.CAMERA},
                    code);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkAndRequestPermissionsStorage(int code) {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(CoffeeMugEditActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CoffeeMugEditActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        code);
                return false;
            } else {

                return true;
            }
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            if (ContextCompat.checkSelfPermission(CoffeeMugEditActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CoffeeMugEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }
        }else {
            if (ContextCompat.checkSelfPermission(CoffeeMugEditActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(CoffeeMugEditActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CoffeeMugEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }
        }

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
                        ActivityCompat.requestPermissions(CoffeeMugEditActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                            ActivityCompat.requestPermissions(CoffeeMugEditActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                            ActivityCompat.requestPermissions(CoffeeMugEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        }else {
                            ActivityCompat.requestPermissions(CoffeeMugEditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
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


    public void pickColor(View view) {
        ColorPickerDialogBuilder
                .with(CoffeeMugEditActivity.this)
                .setTitle(getString(R.string.choose_color))
                .initialColor(selectedColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(10)

                .lightnessSliderOnly()

                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton(getString(R.string.ok), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        CoffeeMugEditActivity.this.selectedColor = selectedColor;
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

    public void openFont(View view) {
        Intent intent = new Intent(CoffeeMugEditActivity.this, FontActivity.class);
        startActivity(intent);
    }

    public void openSticker(View view) {
        Intent intent = new Intent(CoffeeMugEditActivity.this, StickerActivity.class);
        startActivity(intent);
    }

    private void sendData() {
        id_add_photo.setVisibility(View.GONE);

        stickerView.setControlItemsHidden();
        savelayout.setDrawingCacheEnabled(true);
        savelayout.buildDrawingCache();


        try {
            new crateReq().execute();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("EXP", "sendData: " + e.getMessage());
        }

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

    @Override
    protected void onResume() {
        super.onResume();
        getDisplaySize();

        Log.e("RESUME", "onResume: ============>RESUME====1");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (Share.bitmap != null) {
            //                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Share.bitmap);
            // Log.d(TAG, String.valueOf(bitmap));
            Share.lst_album_image.clear();

            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bitmap));
            drawableSticker.setTag("maindraw");
            stickerView.addStickerMain_mug(drawableSticker);
            Share.bitmap = null;


        }

        if (Share.bg_drawable != null) {
            DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(Share.bg_drawable));
            drawableSticker.setTag("maindraw");
            stickerView.addStickerMain_bg(drawableSticker, 0);
            Share.bg_drawable = null;
        }


        if (!Share.imageuri.equalsIgnoreCase("")) {
            //pd = ProgressDialog.show(CoffeeMugEditActivity.this, "", getString(R.string.loading), true, false);
            showProgressDialog(CoffeeMugEditActivity.this);
            loadPicture(Share.imageuri);
        }

        if (Share.FONT_FLAG) {
            Share.FONT_FLAG = false;
            Log.e("RESUME", "onResume: ============>RESUME");

            DrawableSticker drawableSticker = Share.TEXT_DRAWABLE;
            drawableSticker.setTag("text");
            stickerView.addSticker(drawableSticker);

            drawables_sticker.add(drawableSticker);

        }

//        getDisplaySize();
//        setDiemns();
    }

    private void loadPicture(final String photoUrl) {
        Glide.with(CoffeeMugEditActivity.this).asBitmap()
                .load(photoUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                        DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(resource));
                        drawableSticker.setTag("cartoon");
                        stickerView.addSticker(drawableSticker);
                        //pd.dismiss();
                        hideProgressDialog();
                        Share.imageuri = "";
                    }

                    @Override
                    public void onLoadFailed(Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        //pd.dismiss();
                        hideProgressDialog();
                        Share.imageuri = "";
                        if (alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        alertDialog = new AlertDialog.Builder(CoffeeMugEditActivity.this).create();
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
                    stickerView.addStickerMain_mug(drawableSticker);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("PAUSED", "onPause:=============> ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        stickerView = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class crateReq extends AsyncTask<Void, Void, Void> {

        MultipartBody.Builder builder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress = ProgressDialog.show(CoffeeMugEditActivity.this, "", getString(R.string.loading), true, false);
            showProgressDialog(CoffeeMugEditActivity.this);
            builder = new MultipartBody.Builder();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           /* if (progress != null && progress.isShowing())
                progress.dismiss();*/


            if (builder != null) {
                MultipartBody multipartBody = builder.build();
                APIService apiService = new MainApiClient(CoffeeMugEditActivity.this).getApiInterface();
                Call<Cart> cartCall = apiService.sendCart(multipartBody);
                cartCall.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {

//                        if (progress != null && progress.isShowing())
//                            progress.dismiss();
                        hideProgressDialog();

                        if (response != null) {
                            if (response.body().getcart_data().getStatus() == 1) {


                                String contentId = "";
                                String contentType = "";
                                String contentAmount = "";

                                contentId = getIntent().getStringExtra("model_id");
                                contentType = getIntent().getStringExtra("model_name");
                                contentAmount = getIntent().getStringExtra("paid_amount");

                                if (contentId != null && contentType != null && contentAmount != null) {
                                    FBEventsKt.logAddedToCartEvent(CoffeeMugEditActivity.this, contentId, contentType, contentAmount);
                                    FirebaseEventsKt.logAddToCartEvent(CoffeeMugEditActivity.this, contentId, contentType, Double.parseDouble(contentAmount));
                                }


                                upload = true;
                                mStickers.clear();
                                if (PhotoPickupImageActivity.activity != null) {
                                    PhotoPickupImageActivity.activity.finish();
                                }
                                if (Mug_MainActivity.activity != null) {
                                    Mug_MainActivity.activity.finish();
                                }
                                if (Mug_images_category_activity.mActivity != null) {
                                    Mug_images_category_activity.mActivity.finish();
                                }
                                finish();
                                Log.e("SELECTED", "onResponse: " + HomeMainActivity.selected);


                            } else {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CoffeeMugEditActivity.this);
                                alertDialog.setTitle(getString(R.string.upload_fail));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(response.body().getcart_data().getMessage());
                                alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });

                                alertDialog.create().show();
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
                            AlertDialog alertDialog = new AlertDialog.Builder(CoffeeMugEditActivity.this).create();
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
                            AlertDialog alertDialog = new AlertDialog.Builder(CoffeeMugEditActivity.this).create();
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
            } else {

            }

        }

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


        @Override
        protected Void doInBackground(Void... voids) {
            // Log.e("width","==>if"+getIntent().getIntExtra("width",0));
            if (getIntent().hasExtra("width")) {
                print_bitmap = getBitmapPrint(stickerView.createBitmap(), Float.parseFloat(getIntent().getStringExtra("width")), Float.parseFloat(getIntent().getStringExtra("height")));
//                print_bitmap=RotateBitmap(getResizedBitmapCover(CropBitmapTransparency(print_bitmap),(int) Float.parseFloat(getIntent().getStringExtra("width"))*300,(int)Float.parseFloat(getIntent().getStringExtra("height"))*300),270);
                print_bitmap = RotateBitmap(print_bitmap, 270);
                Log.e("with_width", "==>if");

            } else {
                print_bitmap = getBitmapPrint(stickerView.createBitmap());
                print_bitmap = RotateBitmap(print_bitmap, 270);
            }
            Log.e("with_width", "==>" + print_bitmap.getWidth());
            Log.e("with_height", "==>" + print_bitmap.getHeight());
            cover_bitmap = RotateBitmap(getResizedBitmapCover(CropBitmapTransparency(savelayout.getDrawingCache()), height, width), 270);
            //  cover_bitmap = savelayout.getDrawingCache();
            savelayout.setAlwaysDrawnWithCacheEnabled(false);
            File file_printphoto = getFile("printphoto_" + System.currentTimeMillis(), print_bitmap, ".jpg");
            File file_cover_bitmap = getFile("cover_bitmap_" + System.currentTimeMillis(), cover_bitmap, ".png");
            Log.d("file_printphoto", "Size==>" + file_printphoto.length() / 1024);
            Log.d("file_cover_bitmap", "Size==>" + file_cover_bitmap.length() / 1024);

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("user_id", SharedPrefs.getString(CoffeeMugEditActivity.this, Share.key_ + RegReq.id));
            builder.addFormDataPart("quantity", "1");
            builder.addFormDataPart("model_id", getIntent().getStringExtra("model_id"));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_printphoto);
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file_cover_bitmap);
            builder.addFormDataPart("print_image", file_printphoto.getName(), requestBody);
            builder.addFormDataPart("case_image", file_cover_bitmap.getName(), requestBody1);
            builder.addFormDataPart("ln", Locale.getDefault().getLanguage());
            return null;
        }
    }
}
