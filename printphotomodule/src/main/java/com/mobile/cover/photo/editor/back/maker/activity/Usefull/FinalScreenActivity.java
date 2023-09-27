package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.CustomPagerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Bansi on 24-12-2016.
 */
public class FinalScreenActivity extends AppCompatActivity {

    String m;
    ImageView iv_back, iv_contact_us;
    ArrayList<Uri> image_list_uri = new ArrayList<>();
    private TextView tv_current_page, tv_total_page;
    private int width, height;
    private LinearLayout ll_no_img, ll_my_photo;
    private SharedPreferences sharedpreferences;
    private ViewPager viewPager;
    private File[] allFiles;
    private CustomPagerAdapter customPagerAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);
//        FacebookSdk.sdkInitialize(FinalScreenActivity.this);
        Log.e("HERE", "onCreate: =========>");

        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        } catch (Exception e) {
        }

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

//        if (Share.RestartApp(FinalScreenActivity.this)) {
//
//            sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//            Display display = getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            Share.screenWidth = width = size.x;
//            Share.screenHeight = height = size.y;

        InitView();
        InitViewAction();
//        }
    }

    private void InitView() {
        ll_my_photo = findViewById(R.id.ll_my_photo);
        iv_back = findViewById(R.id.iv_back);
        iv_contact_us = findViewById(R.id.iv_contact_us);
        iv_contact_us.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                int i = 0;
                image_list_uri.clear();
                generate_uri(i);
//                Intent intent_contact = new Intent(FinalScreenActivity.this, Contactus_activity.class);
//                startActivity(intent_contact);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_no_img = findViewById(R.id.ll_no_img);
        viewPager = findViewById(R.id.viewpager);
        tv_current_page = findViewById(R.id.tv_current_page);
        tv_total_page = findViewById(R.id.tv_total_page);
        tv_total_page.setText("0" + Share.product_images_list.size());
    }

    private void generate_uri(final int i) {
        Log.e("POSSSSSS", "generate_uri: " + i);


        if (checkAndRequestPermissionsStorage(2)) {
            if (i == Share.product_images_list.size()) {
//            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
//            shareIntent.setType("text/html");
//            shareIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "sdajfjasdgfjagsdfjgasdjkfgadfsadfsg" });
//            shareIntent.putExtra(Intent.EXTRA_STREAM, image_list_uri);
//            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Print Photo");
//            startActivity(shareIntent);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*"); /* This example is sharing jpeg images. */
                intent.putExtra(Intent.EXTRA_STREAM, image_list_uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, "getApplicationContext().getString(R.string.app_name)");
                String sAux = "Buy this amazing " + Share.product_name + " from" + "getString(R.string.app_name).toLowerCase()" + " app from play store\n\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n\n";
                intent.putExtra(Intent.EXTRA_TEXT, sAux + Share.description);
                startActivity(Intent.createChooser(intent, "Share notes.."));
            } else {
                Glide.with(FinalScreenActivity.this).asBitmap().load(Share.product_images_list.get(i).getThumbImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        image_list_uri.add(Uri.fromFile(getFile("" + System.currentTimeMillis(), resource, ".png")));
                        generate_uri(i + 1);
                    }
                });
            }
        }
    }

    private boolean checkAndRequestPermissionsStorage(int code) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(FinalScreenActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FinalScreenActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        code);
                return false;
            } else {

                return true;
            }
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(FinalScreenActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FinalScreenActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }
        }else {
            if (ContextCompat.checkSelfPermission(FinalScreenActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(FinalScreenActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FinalScreenActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
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
                        ActivityCompat.requestPermissions(FinalScreenActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.requestPermissions(FinalScreenActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R) {
                            ActivityCompat.requestPermissions(FinalScreenActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        }else {
                            ActivityCompat.requestPermissions(FinalScreenActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
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


    private void InitViewAction() {

        customPagerAdapter = new CustomPagerAdapter(this, Share.product_images_list);
        viewPager.setAdapter(customPagerAdapter);
        viewPager.setCurrentItem(Share.slider_url);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("TAG", "onPageScrolled");

                Log.e("view pager", "onPageScrolled: " + viewPager.getCurrentItem());

                if ((viewPager.getCurrentItem() + 1) < 10) {

                    String pos = "0" + (viewPager.getCurrentItem() + 1);
                    Log.e("pos", "onPageScrolled: " + pos);
                    tv_current_page.setText(pos);
                } else {
                    String pos = String.valueOf(viewPager.getCurrentItem() + 1);
                    Log.e("pos", "onPageScrolled: " + pos);
                    tv_current_page.setText(pos);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
