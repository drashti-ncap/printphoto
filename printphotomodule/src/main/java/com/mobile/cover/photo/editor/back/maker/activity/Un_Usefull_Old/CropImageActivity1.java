package com.mobile.cover.photo.editor.back.maker.activity.Un_Usefull_Old;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.AlbumImagesActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FaceActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FacebookAlbumPhotoActivity;
import com.mobile.cover.photo.editor.back.maker.fragment.MainFragment;

public class CropImageActivity1 extends AppCompatActivity {

    private static final String TAG = CropImageActivity1.class.getSimpleName();
    public Activity activity;
    String imageUrl;

    boolean isOpenPermissionDialog = false;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_activity_main);

        Log.e("onCreate", "startResultActivity has activity --> " + getIntent().hasExtra("activity"));

        activity = this;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        /*if (getIntent().hasExtra(GlobalData.KEYNAME.SELECTED_IMAGE)) {
            Log.e("SELECTED_IMAGE", "--> SELECTED_IMAGE");
            imageUrl = getIntent().getExtras().getString(GlobalData.KEYNAME.SELECTED_IMAGE);
        } else {
            Log.e("SELECTED_PHONE_IMAGE", "--> SELECTED_PHONE_IMAGE");
            imageUrl = getIntent().getStringExtra(GlobalData.KEYNAME.SELECTED_PHONE_IMAGE);
        }

        GlobalData.imageUrl = imageUrl;
        Log.e(TAG, "imageUrl11111==>  " + imageUrl);*/

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, MainFragment.getInstance()).commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        GlobalData.isFromHomeAgain = false;
        super.onBackPressed();
        finish();
    }

    public void startResultActivity(Bitmap uri) {
        nextActivity(uri);
    }

    private void nextActivity(Bitmap uri) {

        Log.e("Crop", "startResultActivity has activity --> " + activity.getIntent().hasExtra("activity"));
        String cropfile = GlobalData.saveFaceInternalStorage(uri, activity.getIntent().hasExtra("activity"));

        // if (activity.getIntent().hasExtra("activity")) {
        Log.e("startResultActivity: ", "cropfile --> " + cropfile);

        //            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        // Log.d(TAG, String.valueOf(bitmap));

        /*DrawableSticker drawableSticker = new DrawableSticker(new BitmapDrawable(uri));
        drawableSticker.setTag("maindraw");
        CaseEditActivity.stickerView.addStickerMain(drawableSticker);*/
        Share.bitmap = uri;

//        Intent intent = new Intent(activity, CaseEditActivity.class);
//        GlobalData.is_social = false;
//        GlobalData.modeBackground = GlobalData.MODE_BACKGROUND.CAMETA;
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);

        if (AlbumImagesActivity.Companion.getActivity() != null)
            AlbumImagesActivity.Companion.getActivity().finish();

        if (FacebookAlbumPhotoActivity.activity != null)
            FacebookAlbumPhotoActivity.activity.finish();

        if (FaceActivity.getFaceActivity() != null)
            FaceActivity.getFaceActivity().finish();

        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        finish();


        // }
//        else {
//            GlobalData.suitListAsset.clear();
//
//            if (GlobalData.isFromHomeForChange)
//                GlobalData.isFromHomeAgain = true;
//
//            Intent i = new Intent(this, ImageEditorActivity1.class);
//            i.putExtra("cropfile", cropfile);
//            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(i);
//
//            if (AlbumImagesActivity.activity != null)
//                AlbumImagesActivity.activity.finish();
//
//            if (FacebookAlbumPhotoActivity.activity != null) {
//                FacebookAlbumPhotoActivity.activity.finish();
//            }
//
//            if (FaceActivity.getFaceActivity() != null)
//                FaceActivity.getFaceActivity().finish();
//
//            overridePendingTransition(R.anim.right_in, R.anim.left_out);
//            finish();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
                if (ActivityCompat.shouldShowRequestPermissionRationale(CropImageActivity1.this, permission)) {
                    //denied
                    Log.e("denied", permission);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        ActivityCompat.requestPermissions(CropImageActivity1.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
                    }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        ActivityCompat.requestPermissions(CropImageActivity1.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        ActivityCompat.requestPermissions(CropImageActivity1.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }
                } else {
                    if (ActivityCompat.checkSelfPermission(CropImageActivity1.this, permission) == PackageManager.PERMISSION_GRANTED) {
                        //allowed
                        Log.e("allowed", "permission ====>" + permission);
                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                    }
                }
            }

            if (somePermissionsForeverDenied) {
                if (!isOpenPermissionDialog) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CropImageActivity1.this);
                    alertDialogBuilder.setTitle(getString(R.string.permission_required))
                            .setMessage("Please allow permission for " + GlobalData.msg + ".")
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                    dialog.dismiss();
                                    isOpenPermissionDialog = false;
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    isOpenPermissionDialog = false;
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                    isOpenPermissionDialog = true;
                }
            }
        } else {
            switch (requestCode) {
                case 1:
                    Log.e("TAG", "Permission Allowed");
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
