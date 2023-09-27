package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.google.android.material.tabs.TabLayout;
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Display_slide_CustomPagerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class mall_description extends AppCompatActivity {
    public static Activity mActivity;
    TextView title, tv_pro_name, tv_product_description;
    ImageView id_back, iv_contact_us, iv_zoom;
    ArrayList<Uri> image_list_uri = new ArrayList<>();
    private ViewPager viewPager;
    private Display_slide_CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_description);
        mActivity = mall_description.this;
        findviews();
    }

    private void findviews() {
        title = findViewById(R.id.title);
        tv_pro_name = findViewById(R.id.tv_pro_name);
        tv_product_description = findViewById(R.id.tv_product_description);
        iv_contact_us = findViewById(R.id.iv_contact_us);
        iv_zoom = findViewById(R.id.iv_zoom);
        iv_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mall_description.this, FinalScreenActivity.class);
                startActivity(intent);
            }
        });
        iv_contact_us.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                int i = 0;
                image_list_uri.clear();
                generate_uri(i);
            }
        });
        tv_product_description.setText(getIntent().getStringExtra("descrip"));
        tv_pro_name.setText(getIntent().getStringExtra("pro_name"));
        title.setText(getIntent().getStringExtra("pro_name"));
        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
        customPagerAdapter = new Display_slide_CustomPagerAdapter(this, Share.product_images_list);
        viewPager.setAdapter(customPagerAdapter);
        viewPager.setCurrentItem(0);
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
                String sAux = "Buy this amazing " + getIntent().getStringExtra("pro_name") + " from" + "getString(R.string.app_name).toLowerCase()" + " app from play store\n\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n\n";
                intent.putExtra(Intent.EXTRA_TEXT, sAux + Share.description);
                startActivity(Intent.createChooser(intent, "Share notes.."));
            } else {
                Glide.with(mActivity).asBitmap().load(Share.product_images_list.get(i).getThumbImage()).into(new SimpleTarget<Bitmap>() {
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
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        code);
                return false;
            } else {

                return true;
            }
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }
        }else {
            if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
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
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        }else {
                            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
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
}
