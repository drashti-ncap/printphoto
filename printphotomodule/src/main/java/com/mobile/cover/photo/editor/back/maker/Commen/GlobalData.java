package com.mobile.cover.photo.editor.back.maker.Commen;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.mobile.cover.photo.editor.back.maker.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by Harshad Kathiriya on 11/12/2016.
 */

public class GlobalData {

    public static final int APPID = 22;
    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "Baby Girl Suit";
    // ------- Advertise --------
    public static int AD_index;
    public static boolean is_button_click = false;
    public static String selected_tab = "HOME";
    public static int position = 0;
    public static String ntv_img;
    public static String ntv_inglink;
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    public static ArrayList<File> al_ad_full_image_from_storage = new ArrayList<>();
    //-------------
    public static ArrayList<String> al_ad_full_image_name = new ArrayList<>();
    public static ArrayList<String> al_ad_package_name = new ArrayList<>();
    public static String msg;
    public static String Package = "com.vasundhara.babygirlsuit";
    public static int APP_ID = 22;
    public static String App_name = "Baby Girl Suit";
    public static boolean ChangeAppID = false, is_camera_backgroud;
    public static Matrix matrix = null;
    public static int pager_position = 0;
    public static boolean is_home_back = true;
    public static boolean is_bg_selected = false;
    public static Bitmap bitmapPhoto, faceBitmap;
    public static Uri editedImageUri;

    public static ArrayList<String> saveList = new ArrayList<String>();
    public static int selectGalleryImgPos = 0, selectedColor;

    public static String insta_id, insta_access_token;
    public static int h;
    public static MODE_BACKGROUND modeBackground = GlobalData.MODE_BACKGROUND.NONE;
    public static boolean is_social = false;
    public static String imageUrl;
    public static boolean is_more_apps = false;
    public static boolean isAdShow = false;
    public static boolean is_start = false;
    public static Boolean APD_FLAG = false;
    public static List<Drawable> suitListAsset = new ArrayList<>();
    public static boolean isFromHomeAgain = false;
    public static boolean isFromHomeForChange = false;
    //My Photoes
    public static Bitmap SELECTED_BITMAP;
    public static String image_path;
    public static ArrayList<File> al_my_photos_photo = new ArrayList<>();
    public static int my_photos_position = 0;
    public static ArrayList<File> al_my_photos_favourite = new ArrayList<>();
    public static int my_favourite_position = 0;
    public static String Fragment = "MyPhotosFragmen" +
            "t";
    public static String BG_FILE_NAME = "SuitBG.png";

    public static Dialog showProgress(Context mContext, String text) {
        Dialog mDialog = new Dialog(mContext, R.style.MyTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View layout = mInflater.inflate(R.layout.dialog_progress, null);
        mDialog.setContentView(layout);

        TextView mTextView = layout.findViewById(R.id.text);
        if (text.equals(""))
            mTextView.setVisibility(View.GONE);
        else
            mTextView.setText(text);

        mDialog.setCancelable(false);
        // aiImage.post(new Starter(activityIndicator));
        return mDialog;
    }

    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext, R.style.MyTheme);
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
                createProgressDialog(mContext);
            } else {
                dialog.show();
            }
        } catch (Exception e) {
//			Log.e("dialog crash","crash"+e.getMessage());
            e.printStackTrace();
        }
        //dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setProgressDrawable((new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimary))));
        dialog.setContentView(R.layout.custom_dialog_layout);
        return dialog;
    }

    public static boolean checkAndRequestPermissions(Activity act, int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(act, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static Boolean RestartApp(Activity activity) {
        Log.e("CHECKDATA", "RestartAppForOnlyStorage: 2" );
        if (!checkAndRequestPermissions(activity, 1)) {
            Intent i = activity.getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(i);
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkAndRequestPermissionsHome(Activity act, int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(act, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else
                return ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else
                return ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        } else {
            if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else
                return ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }

    }

    public static Boolean RestartAppHome(Activity activity) {
        Log.e("CHECKDATA", "RestartAppForOnlyStorage: 3" );
        if (!checkAndRequestPermissionsHome(activity, 1)) {
//            resume_flag = true;
            Intent i = activity.getBaseContext().getPackageManager().getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(i);
            return false;
        } else {
            return true;
        }
    }

    public static String saveFaceInternalStorage(Bitmap bitmapImage, boolean is_bg_selected) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        if (bitmapImage != null) {

            File mypath = null;

            if (is_bg_selected) {
                mypath = new File(directory, BG_FILE_NAME);
            } else {
                mypath = new File(directory, "profile.png");
            }

            Log.e("TAG", "" + mypath);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "Not Saved Image------------------------------------------------------->");
        }
        return directory.getAbsolutePath();
    }

    public static String saveToInternalStorage(Bitmap bitmapImage) {
        File directoryIMAGE_PATH = new File(IMAGE_PATH);
        try {
            if (!directoryIMAGE_PATH.exists())
                directoryIMAGE_PATH.mkdir();

            File directory = new File(IMAGE_PATH + "/.tempImage");
            if (!directory.exists())
                directory.mkdir();
            File mypath = new File(directory, "profile.PNG");
            mypath.createNewFile();
            Log.e("TAG", "" + mypath);
            // Create imageDir
            if (mypath.exists()) {
                FileOutputStream fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } else {
                Log.e("TAG", "saveToInternalStorage Not Saved Image------------------------------------------------------->");
            }

            return directory.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String saveCameraBGPhoto(Context context, Bitmap bitmapImage) {

        try {
            Log.e("saveCameraBGPhoto", "" + getTemporalFile(context));
            getTemporalFile(context).createNewFile();

            if (getTemporalFile(context).exists()) {
                FileOutputStream fos = new FileOutputStream(getTemporalFile(context));
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } else {
                Log.e("TAG", "saveToInternalStorage Not Saved Image----------------------------->");
            }

            return getTemporalFile(context).getPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static File getTemporalFile(Context context) {
        return new File(context.getExternalCacheDir(), "tempImageBG");
    }

    public static String saveToTempPhoto(Bitmap bitmapImage) {
        try {
            File directoryIMAGE_PATH = new File(IMAGE_PATH);
            if (!directoryIMAGE_PATH.exists())
                directoryIMAGE_PATH.mkdir();

            File directory = new File(IMAGE_PATH + "/.tempImage");
            if (!directory.exists())
                directory.mkdir();

            // Create imageDir
            if (bitmapImage != null) {
                File mypath = new File(directory, "temp.jpeg");
                if (!mypath.exists())
                    mypath.createNewFile();
                Log.e("TAG", "" + mypath);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.e("TAG", "Not Saved Image------------------------------------------------------->");
            }
            return directory.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public enum MODE_BACKGROUND {
        NONE, COLOR, BGIMAGE, CAMETA
    }

    public class KEYNAME {
        public static final String ALBUM_ID = "album_id";
        public static final String ALBUM_NAME = "album_name";
        public static final String SELECTED_IMAGE = "selected_image";
        public static final String SELECTED_PHONE_IMAGE = "selected_phone_image";
    }

}
