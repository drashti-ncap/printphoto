package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.mug_image_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mug_image_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.mug_image_adapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnSubItemClickListener;
import com.mobile.cover.photo.editor.back.maker.mainapplication;
import com.mobile.cover.photo.editor.back.maker.model.mall_new_main_model;
import com.mobile.cover.photo.editor.back.maker.testing_modules.ImageData;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Mug_MainActivity;
import com.mobile.cover.photo.editor.back.maker.utility.PathUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class Mug_images_category_activity extends AppCompatActivity {
    public static Activity mActivity;
    AlertDialog alertDialog;
    ProgressDialog pd;
    RecyclerView rv_list;
    mug_image_adapter default_image_adapter;
    LinearLayout ll_test;
    ImageView id_back;
    TextView title;

    private mainapplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mug_images_category_activity);
        mActivity = Mug_images_category_activity.this;

        rv_list = findViewById(R.id.rv_list);
        title = findViewById(R.id.title_name);
        title.setText(getString(R.string.mug_fancy_image));
        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Share.login_back = !SharedPrefs.getString(Mug_images_category_activity.this, SharedPrefs.last_country_code).equalsIgnoreCase(SharedPrefs.getString(Mug_images_category_activity.this, SharedPrefs.country_code));
            }
        });
        rv_list.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(2, 12, true);
        rv_list.addItemDecoration(gridSpacingItemDecoration);

        rv_list.setLayoutManager(mLayoutManager);
        rv_list.setItemAnimator(new DefaultItemAnimator());

        ll_test = findViewById(R.id.ll_test);
        ll_test.setVisibility(View.GONE);
        if (Share.mug_image_array.size() == 0) {
            getMainData();
        } else {
            default_image_adapter = new mug_image_adapter(mActivity, Share.mug_image_array);
            rv_list.setAdapter(default_image_adapter);

            default_image_adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClickLister(View v, int position) {
                    if (checkAndRequestPermissionsStorage(2)) {
                        if (position != 0) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.putExtra("from", "" + 0);
                            Toast.makeText(Mug_images_category_activity.this, "Select " + Share.itemnum + " image/images", Toast.LENGTH_LONG).show();
                            startActivityForResult(intent, 111);
                        }
                    }
                }

            });

        }
    }

    private void getDisplaySize() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }


    @Override
    public void onBackPressed() {
        finish();
        Share.login_back = !SharedPrefs.getString(Mug_images_category_activity.this, SharedPrefs.last_country_code).equalsIgnoreCase(SharedPrefs.getString(Mug_images_category_activity.this, SharedPrefs.country_code));
    }

    private boolean checkAndRequestPermissionsCamera(int code) {

        if (ContextCompat.checkSelfPermission(Mug_images_category_activity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Mug_images_category_activity.this, new String[]{android.Manifest.permission.CAMERA}, code);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkAndRequestPermissionsStorage(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, code);
                return false;
            } else {
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, code);
                return false;
            } else {
                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
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
                        ActivityCompat.requestPermissions(Mug_images_category_activity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.requestPermissions(Mug_images_category_activity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            ActivityCompat.requestPermissions(Mug_images_category_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        } else {
                            ActivityCompat.requestPermissions(Mug_images_category_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
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

    @Override
    protected void onResume() {
        super.onResume();
        getDisplaySize();
    }

    private void getMainData() {

        pd = ProgressDialog.show(mActivity, "", getString(R.string.loading), true, false);
        Share.mall_main_category_data.clear();

        APIService apiService = new MainApiClient(mActivity).getApiInterface();
        Call<mug_image_response> call = apiService.get_mug_images();

        call.enqueue(new Callback<mug_image_response>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 200) {
                    pd.dismiss();
                    mug_image_response mall_new_main_model = (mug_image_response) response.body();
                    if (mall_new_main_model.getSuccess()) {
                        Share.mug_image_array.addAll(mall_new_main_model.getData());

                        Share.mug_image_array.add(0, new mug_image_response_data(0, "", ""));
                        default_image_adapter = new mug_image_adapter(mActivity, Share.mug_image_array);
                        rv_list.setAdapter(default_image_adapter);

                        default_image_adapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClickLister(View v, int position) {
                                if (checkAndRequestPermissionsStorage(2)) {
                                    if (position != 0) {
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.setType("image/*");
                                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                        intent.putExtra("from", "" + 0);
                                        Toast.makeText(Mug_images_category_activity.this, "Select " + Share.itemnum + " image/images", Toast.LENGTH_LONG).show();
                                        startActivityForResult(intent, 111);
                                    }
                                }
                            }

                        });


                    } else {
                        Toast.makeText(mActivity, mall_new_main_model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    pd.dismiss();
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    alertDialog = new AlertDialog.Builder(mActivity).create();
                    alertDialog.setTitle(getString(R.string.server_error));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.server_under_maintenance));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();

                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<mug_image_response> call, Throwable t) {
                error_dialogs(t);
            }
        });

    }

    public void error_dialogs(Throwable t) {
        pd.dismiss();
        Log.e("MESSAGE", "error_dialogs: " + t.getMessage());
        Log.e("MESSAGE", "error_dialogs: " + t.getLocalizedMessage());
        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            alertDialog = new AlertDialog.Builder(mActivity).create();
            alertDialog.setTitle(getString(R.string.time_out));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.connect_time_out));
            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getMainData();

                }
            });
            alertDialog.show();
        } else {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            alertDialog = new AlertDialog.Builder(mActivity).create();
            alertDialog.setTitle(getString(R.string.internet_connection));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.slow_connect));
            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getMainData();

                }
            });
            alertDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> temp = new ArrayList<>();
                this.application = mainapplication.getsInstance();
                application.getSelectedImages().clear();
                application.org_selectedImages.clear();
                application.temp_org_selectedImages.clear();
                application.selectedImages.clear();
                Log.e("SELECTEDDATA", "onActivityResult: share item num==>" + Share.itemnum);
                if (data.getClipData() != null) {
                    Log.e("SELECTEDDATA", "onActivityResult: image size==>" + data.getClipData().getItemCount());
                    for (int i = 0; i < Share.itemnum; i++) {
                        try {
                            String filePath = PathUtil.getPath(this, data.getClipData().getItemAt(i).getUri());
                            Log.e("SELECTEDDATA", "onActivityResult: file path-->" + filePath);
                            temp.add(filePath);
                        } catch (URISyntaxException e) {
                            Log.e("SELECTEDDATA", "onActivityResult: exception-->" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    Log.e("SELECTEDDATA", "onActivityResult: temp size-->" + temp.size());
                    for (int i = 0; i < temp.size(); i++) {
                        final ImageData imageData = new ImageData();
                        imageData.setTemp_imagePath(temp.get(i));
                        imageData.setImagePath(temp.get(i));
                        imageData.setTemp_org_imagePath(temp.get(i));
                        application.addSelectedImage(imageData);
                    }
                } else {
                    Log.e("SELECTEDDATA", "onActivityResult: image ==>" + data.getData());
                    try {
                        String filePath = PathUtil.getPath(this, data.getData());
                        Log.e("SELECTEDDATA", "onActivityResult: file path-->" + filePath);
                        temp.add(filePath);
                    } catch (URISyntaxException e) {
                        Log.e("SELECTEDDATA", "onActivityResult: exception-->" + e.getMessage());
                        e.printStackTrace();
                    }
                    for (int i = 0; i < temp.size(); i++) {
                        final ImageData imageData = new ImageData();
                        imageData.setTemp_imagePath(temp.get(i));
                        imageData.setImagePath(temp.get(i));
                        imageData.setTemp_org_imagePath(temp.get(i));
                        application.addSelectedImage(imageData);
                    }
                }

                Intent intent = new Intent(this, Mug_MainActivity.class);
                startActivity(intent);

            }
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f / spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
