package com.mobile.cover.photo.editor.back.maker.testing_modules;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.mainapplication;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.MainActivity;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Mug_MainActivity;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Shipper_MainActivity;
import com.mobile.cover.photo.editor.back.maker.testing_modules.view.EmptyRecyclerView;
import com.mobile.cover.photo.editor.back.maker.testing_modules.view.ExpandIconView;
import com.mobile.cover.photo.editor.back.maker.testing_modules.view.VerticalSlidingPanel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PhotoPickupImageActivity extends AppCompatActivity implements VerticalSlidingPanel.PanelSlideListener, OnClickListener {
    public static final String EXTRA_FROM_PREVIEW = "extra_from_preview";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 123;
    public static PhotoPickupImageActivity activity;
    public static ImageView iv_more_app, iv_blast;
    public static Boolean is_closed = true;
    public static Activity mActivity;
    public boolean isFromPreview = false;
    boolean isPause = false;
    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE};
    ImageView img_back;
    private AlbumAdapterById albumAdapter;
    private ImageByAlbumAdapter albumImagesAdapter;
    private mainapplication application;
    private Button btnClear;
    private ImageView ivNext;
    private RelativeLayout iv_camera;
    private TextView tvCountImages;
    private ExpandIconView expandIcon;
    // private VerticalSlidingPanel panel;
    private View parent;
    private LinearLayout llDelete, llEdit;
    private RecyclerView rvAlbum;
    private RecyclerView rvAlbumImages, rvSelected;
    private EmptyRecyclerView rvSelectedImage;
    private SelectedImageAdapter selectedImageAdapter;
    private SelectedImageHorizontalAdapter imageHorizontalAdapter;
    private TextView tvImageCount;
    // private FirebaseAnalytics mFirebaseAnalytics;
    private String imageStoragePath = null;
    private List<String> listPermissionsNeededAudio = new ArrayList<>();

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context,
                                                          AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context,
                                                          AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainapplication.org_selectedImages.clear();
        mainapplication.temp_org_selectedImages.clear();
        mainapplication.selectedImages.clear();
        if (Share.RestartAppForOnlyStorage(PhotoPickupImageActivity.this)) {
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_photo_pickup_image);

            // mFirebaseAnalytics = FirebaseAnalytics.getInstance(PhotoPickupImageActivity.this);
            activity = this;
            mActivity = PhotoPickupImageActivity.this;
            this.application = mainapplication.getsInstance();
            this.isFromPreview = getIntent().hasExtra(EXTRA_FROM_PREVIEW);
            bindView();

            //application.getSelectedImages().clear();


//            ArrayList<ImageData> list = application.getSelectedImages();
//            ArrayList<String> temp = new ArrayList<>();
//
//            for (int i = 0; i < list.size(); i++) {
//                String path = list.get(i).temp_org_imagePath;
//                File file = new File(path);
//                if (file.exists()) {
//                    temp.add(path);
//                }
//            }
//
//            for (int i = 0; i < temp.size(); i++) {
//                final ImageData data = new ImageData();
//                data.setTemp_imagePath(temp.get(i));
//                data.setImagePath(temp.get(i));
//                data.setTemp_org_imagePath(temp.get(i));
//                application.addSelectedImage(data);
//            }

            Log.e("currentitem", "---total--img--" + Share.itemnum);


            img_back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {


//
//                    MainAppclass.getInstance().trimCache(getApplicationContext());
//                    application.getSelectedImages().clear();

                    onBackPressed();
//                    File dir = new File("/Internal storage/Android/data/com.video.status.music.photo.effects.maker.editing/files/storage/emulated/0/videofile");
//                    dir.delete();


//                    String DNAME = "videofile";
//                    File rootPath = new File(Environment.getExternalStorageDirectory().toString(), DNAME);
//                    Log.e("hideimgpath", "path" + rootPath);

                    // File dir = new File(Environment.getExternalStorageDirectory()+"videofile");

//                    String root = Environment.getDownloadCacheDirectory().toString();
//                    File dir = new File(root + "/videofile");
//
//
//                    Log.e("hideimgpath", "path.............." + dir);
//                    Log.e("hideimgpath", "path.........t....." + dir.exists());
//                    if (dir.isDirectory())
//                    {
//                        Log.e("hideimgpath", "path" + dir);
//                        String[] children = dir.list();
//                        for (int i = 0; i < children.length; i++)
//                        {
//                            new File(dir, children[i]).delete();
//                            Log.e("hideimgpath", "path>>>>>>>>." + dir);
//                        }
//                    }


                }
            });
        }
    }


    private void init() {
        //  Utils.setFont((Activity) this, mTitle);
        albumAdapter = new AlbumAdapterById(this);
        albumImagesAdapter = new ImageByAlbumAdapter(this);
        selectedImageAdapter = new SelectedImageAdapter(this);

        rvAlbum.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//        rvAlbum.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rvAlbum.setItemAnimator(new DefaultItemAnimator());
        rvAlbum.setAdapter(albumAdapter);

        rvAlbumImages.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvAlbumImages.setItemAnimator(new DefaultItemAnimator());
        rvAlbumImages.setAdapter(albumImagesAdapter);

        rvSelectedImage.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        rvSelectedImage.setItemAnimator(new DefaultItemAnimator());
        rvSelectedImage.setAdapter(selectedImageAdapter);

        rvSelectedImage.setEmptyView(findViewById(R.id.list_empty));
        rvSelected.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        imageHorizontalAdapter = new SelectedImageHorizontalAdapter(PhotoPickupImageActivity.this);
        rvSelected.setAdapter(imageHorizontalAdapter);
        tvImageCount.setText(String.valueOf(application.getOrgSelectedImages().size()));
        tvCountImages.setText(application.getOrgSelectedImages().size() + "/" + Share.itemnum);
    }

    private void bindView() {
        img_back = findViewById(R.id.img_back);
        tvImageCount = findViewById(R.id.tvImageCount);
        expandIcon = findViewById(R.id.settings_drag_arrow);
        rvAlbum = findViewById(R.id.rvAlbum);
        rvAlbumImages = findViewById(R.id.rvImageAlbum);
        rvSelectedImage = findViewById(R.id.rvSelectedImagesList);
        rvSelected = findViewById(R.id.rvSelected);
        parent = findViewById(R.id.default_home_screen_panel);
        btnClear = findViewById(R.id.btnClear);
        tvCountImages = findViewById(R.id.tvCountImages);
        iv_camera = findViewById(R.id.iv_camera);

        llDelete = findViewById(R.id.llDelete);
        llEdit = findViewById(R.id.llEdit);

        ivNext = findViewById(R.id.ivNext);

        iv_more_app = findViewById(R.id.iv_more_app);
        iv_blast = findViewById(R.id.iv_blast);

        ivNext.setOnClickListener(this);
        llDelete.setOnClickListener(this);
        llEdit.setOnClickListener(this);
        iv_camera.setOnClickListener(this);

        iv_more_app.setOnClickListener(this);
    }

    private void addListner() {
        btnClear.setOnClickListener(new C05822());
        albumAdapter.setOnItemClickListner(new C10173());
        albumImagesAdapter.setOnItemClickListner(new C10184());
        selectedImageAdapter.setOnItemClickListner(new C10195());
        imageHorizontalAdapter.setOnItemClickListner(new DeleteSelectedImage());
    }

    protected void onResume() {
        super.onResume();

        mainapplication.getsInstance().getFolderList();
        init();
        addListner();

        try {
            if (!Share.resume_flag) {
                if (isPause) {
                    isPause = false;


                    ArrayList<ImageData> list = this.application.getSelectedImages();
                    ArrayList<String> temp = new ArrayList<>();

                    for (int i = 0; i < list.size(); i++) {
                        String path = list.get(i).temp_org_imagePath;
                        File file = new File(path);
                        if (file.exists()) {
                            temp.add(path);
                        }
                    }
                    application.getSelectedImages().clear();
                    application.getOrgSelectedImages().clear();
                    application.getTempOrgSelectedImages().clear();


                    for (int i = 0; i < temp.size(); i++) {
                        final ImageData data = new ImageData();
                        data.setTemp_imagePath(temp.get(i));
                        data.setImagePath(temp.get(i));
                        data.setTemp_org_imagePath(temp.get(i));
                        application.addSelectedImage(data);
                    }


                    tvCountImages.setText(application.getOrgSelectedImages().size() + "/" + Share.itemnum);
                    selectedImageAdapter.notifyDataSetChanged();
                    albumImagesAdapter.notifyDataSetChanged();
                    imageHorizontalAdapter.notifyDataSetChanged();
                }

                // disable delete icon when no image selected
                if (application.getOrgSelectedImages().size() != 0) {
                    llDelete.setEnabled(true);
                    llDelete.setAlpha(1.0f);
                } else {
                    llDelete.setEnabled(false);
                    llDelete.setAlpha(0.5f);
                }


                // disable edit icon when no image selected
                if (application.getOrgSelectedImages().size() >= 3) {
                    llEdit.setEnabled(true);
                    llEdit.setAlpha(1.0f);
                } else {
                    llEdit.setEnabled(false);
                    llEdit.setAlpha(0.5f);
                }

            }
        } catch (Exception e) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Share.resume_flag = false;
    }

    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    private void loadImageEdit() {
        redirectActivity();
    }

    private void redirectActivity() {

        model_details_data modelData = DataHelperKt.getModelData(mActivity);

        Log.e("POSITION", "onClick: =========>" + getIntent().getStringExtra("position"));
        if (getIntent().getStringExtra("from").equalsIgnoreCase("1")) {
            Intent intent = new Intent(PhotoPickupImageActivity.this, MainActivity.class);
            intent.putExtra("position", getIntent().getStringExtra("position"));
            intent.putExtra("model_name", modelData.getModalName());
            intent.putExtra("model_id", "" + modelData.getModelId());
            intent.putExtra("user_id", SharedPrefs.getString(PhotoPickupImageActivity.this, Share.key_ + RegReq.id));
            intent.putExtra("quantity", "1");
            intent.putExtra("total_amount", modelData.getPrice());
            intent.putExtra("paid_amount", modelData.getPrice());
            Share.mobile_type = modelData.getImageType();
            Log.e("TAG", "onPostExecute: " + Share.mobile_type);
            if (!modelData.getWidth().equalsIgnoreCase("")) {
                intent.putExtra("width", modelData.getWidth());
                intent.putExtra("height", modelData.getHeight());
                Share.maskheight = Float.parseFloat(modelData.getHeight());
                Share.maskwidth = Float.parseFloat(modelData.getWidth());
                Log.e("width", "==>" + modelData.getWidth());
                Log.e("height", "==>" + modelData.getHeight());
            }

            intent.putExtra("shipping", "0");
            startActivity(intent);
        } else if (getIntent().getStringExtra("from").equalsIgnoreCase("2")) {
            Intent intent = new Intent(activity, Shipper_MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(activity, Mug_MainActivity.class);
            startActivity(intent);
        }

//        Intent in = new Intent(getApplicationContext(), AudioCutterGallery.class);
//        startActivity(in);
//        finish();

//        if (checkAndRequestPermissionsAudio()) {
//            PhotoPickupImageActivity.this.startActivity(new Intent(PhotoPickupImageActivity.this, AudioCutterGallery.class));
//            finish();
//            //overridePendingTransition(R.anim.right_in, R.anim.left_out);
//        } else if (!checkAndRequestPermissionsAudio()) {
//            ActivityCompat.requestPermissions(activity,
//                    listPermissionsNeededAudio.toArray(new String[listPermissionsNeededAudio.size()]), 5);
//        }


//        if (Share.isPreviewActivity) {
//            Intent intent = new Intent(PhotoPickupImageActivity.this,
//                    SelectedPhotoArrangeActivity.class);
//            startActivity(intent);
//        } else {
//            Share.FirstTimePreviewActivity = true;
//            Intent intent = new Intent(PhotoPickupImageActivity.this,
//                    SelectedPhotoArrangeActivity.class);
//            startActivity(intent);
//        }


    }

    private boolean checkAndRequestPermissionsAudio() {
        listPermissionsNeededAudio.clear();
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS);
        int readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeededAudio.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }
        if (readStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeededAudio.add(Manifest.permission.RECORD_AUDIO);
        }


        return listPermissionsNeededAudio.isEmpty();
    }

    public void onPanelSlide(View panel, float slideOffset) {
        if (expandIcon != null) {
            expandIcon.setFraction(slideOffset, false);
        }
        if (slideOffset >= 0.005f) {
            if (parent != null && parent.getVisibility() != View.VISIBLE) {
                parent.setVisibility(View.VISIBLE);
            }
        } else if (parent != null && parent.getVisibility() == View.VISIBLE) {
            parent.setVisibility(View.GONE);
        }
    }

    public void onPanelCollapsed(View panel) {
        if (parent != null) {
            parent.setVisibility(View.VISIBLE);
        }
        selectedImageAdapter.isExpanded = false;
        selectedImageAdapter.notifyDataSetChanged();
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_selection, menu);
        if (isFromPreview) {
            menu.removeItem(R.id.menu_clear);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                onBackPressed();
                break;
            case R.id.menu_clear:
                clearData();
                break;
            case R.id.menu_done:
                if (application.getSelectedImages().size() > 2) {
                    if (!isFromPreview) {
                        loadImageEdit();
                        break;
                    }
                    setResult(-1);
                    finish();
                    return false;
                }
                Toast.makeText(this, "Select more than 2 Images for create video", Toast
                .LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void onPanelExpanded(View panel) {
        if (parent != null) {
            parent.setVisibility(View.GONE);
        }
        selectedImageAdapter.isExpanded = true;
        selectedImageAdapter.notifyDataSetChanged();
    }

    public void onPanelAnchored(View panel) {
    }

    public void onPanelShown(View panel) {
    }

    public void onBackPressed() {

        application.getSelectedImages().clear();
        application.getOrgSelectedImages().clear();
        application.getTempOrgSelectedImages().clear();


        finish();

//        final Dialog dialog = new Dialog(PhotoPickupImageActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_exit_editing);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        Button rate_app = dialog.findViewById(R.id.rate_app);
//        Button btnNo = dialog.findViewById(R.id.btnNo);
//        Button btnYes = dialog.findViewById(R.id.btnYes);
//
//        btnNo.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        btnYes.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//                if (FinalPreviewActivity.activity != null) {
//                    FinalPreviewActivity.activity.finish();
//                }
//                Share.isPreviewActivity = false;
//                finish();
//            }
//        });
//
//        rate_app.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                dialog.dismiss();
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("market://details?id=" + getPackageName())));
//                } catch (ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google" +
//                            ".com/store/apps/details?id=" + getPackageName())));
//                }
//            }
//        });
//
//        Window window = dialog.getWindow();
//        window.setGravity(Gravity.CENTER);
//        window.setLayout((int) (0.9 * DisplayMetricsHandler.getScreenWidth()),
//                Toolbar.LayoutParams.WRAP_CONTENT);
//
//        if (dialog != null && !dialog.isShowing())
//            dialog.show();

    }

    private void clearData() {

        mainapplication.tempSelection.clear();

        for (int i = application.getSelectedImages().size() - 1; i >= 0; i--) {
            application.removeSelectedImage(i);
        }

        for (int i = 0; i < mainapplication.imageDatas.size(); i++) {
            boolean isSelected = mainapplication.imageDatas.get(i).isSelected;
            if (isSelected) {
                ImageData data1 = mainapplication.imageDatas.get(i);
                data1.setSelected(false);
                mainapplication.imageDatas.set(i, data1);
            }
        }


        tvCountImages.setText("0" + "/" + Share.itemnum);
        llDelete.setEnabled(false);
        llDelete.setAlpha(0.5f);
        selectedImageAdapter.notifyDataSetChanged();
        albumImagesAdapter.notifyDataSetChanged();
        imageHorizontalAdapter.notifyDataSetChanged();


        llEdit.setEnabled(false);
        llEdit.setAlpha(0.5f);

    }

    @Override
    public void onClick(View v) {

        if (v == ivNext) {

            if (application.getSelectedImages().size() > 2) {
                if (!isFromPreview) {
                    loadImageEdit();
                    return;
                }
                setResult(-1);
                finish();
            }
            Toast.makeText(this, getString(R.string.select_atleast) + " " + Share.itemnum + " " + getString(R.string.image_toast), Toast.LENGTH_SHORT).show();


        } else if (v == iv_more_app) {
//            is_closed = false;
//            iv_more_app.setVisibility(View.GONE);
//            iv_blast.setVisibility(View.VISIBLE);
//            ((AnimationDrawable) iv_blast.getBackground()).start();
//
//            if (MainAppclass.getInstance().requestNewInterstitial()) {
//
//                MainAppclass.getInstance().mInterstitialAd.setAdListener(new AdListener() {
//                    @Override
//                    public void onAdClosed() {
//                        super.onAdClosed();
//                        iv_blast.setVisibility(View.GONE);
//                        iv_more_app.setVisibility(View.GONE);
//                        is_closed = true;
//                        /*iv_more_app.setBackgroundResource(R.drawable.animation_list_filling);
//                        ((AnimationDrawable) iv_more_app.getBackground()).start();*/
//                        loadInterstialAd();
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(int i) {
//                        super.onAdFailedToLoad(i);
//                        iv_blast.setVisibility(View.GONE);
//                        iv_more_app.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onAdLoaded() {
//                        super.onAdLoaded();
//                        is_closed = false;
//                        iv_blast.setVisibility(View.GONE);
//                        iv_more_app.setVisibility(View.GONE);
//                    }
//                });
//            }
//        else {
//                iv_blast.setVisibility(View.GONE);
//                iv_more_app.setVisibility(View.GONE);
//            }
        } else if (v == llDelete) {

            // setDeleteAlertDialog();

            if (application.getSelectedImages().size() == Share.itemnum) {
                if (!isFromPreview) {
                    redirectActivity();
//                    loadImageEdit();
                    return;
                }
                setResult(-1);
                finish();
            }
            Toast.makeText(this, getString(R.string.select_atleast) + " " + Share.itemnum + " " + getString(R.string.image_toast), Toast.LENGTH_SHORT).show();


        } else if (v == llEdit) {

            if (application.getSelectedImages().size() > 2) {
                if (!isFromPreview) {
                    loadImageEdit();
                    return;
                }
                setResult(-1);
                finish();
            }
            Toast.makeText(this, getString(R.string.atleast_three_image), Toast.LENGTH_SHORT).show();
        } else if (v == iv_camera) {

// camera button
//            if (application.getSelectedImages().size() < 30) {
//
//                if (CameraUtils.checkPermissions(getApplicationContext())) {
//                    captureImage();
//                } else {
//                    requestCameraPermission();
//                }
//            }else {
//                Toast.makeText(PhotoPickupImageActivity.this, "Maximum 30 images can be select",
//                        Toast.LENGTH_SHORT).show();
//            }

        }
    }

//    private void requestCameraPermission() {
//        Dexter.withActivity(this)
//                .withPermissions(Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if (report.areAllPermissionsGranted()) {
//
//                            captureImage();
//
//                        } else if (report.isAnyPermissionPermanentlyDenied()) {
//                            showAlert();
//                        } else {
//                            Toast.makeText(application, "Permission Failed..",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
//    }

    private void showAlert() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.permission_required))
                .setMessage(getString(R.string.permission_sentence))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog.dismiss();
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", getPackageName(), null));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }


    /**
     * Capturing Camera Image will launch camera app requested image capture
     */
//    private void captureImage() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = CameraUtils.getOutputMediaFile(getApplicationContext());
//        if (file != null) {
//            imageStoragePath = file.getAbsolutePath();
//        }
//        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        // start the image capture Intent
//        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//
//
//       // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//      //  startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//    }
    private void setDeleteAlertDialog() {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(PhotoPickupImageActivity.this);
        } else {
            builder = new AlertDialog.Builder(PhotoPickupImageActivity.this);
        }
        builder.setTitle(getString(R.string.delete))
                .setMessage("Are you sure you want to delete all photos?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Share.end_slide_selected_or_not = false;
                        clearData();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.ic_back)
                .show();
    }


    /**
     * Activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery


                // successfully captured the image
                // display it in image view

                // Bitmap bitmap=(Bitmap) data.getExtras().get("data");


                // previewCapturedImage(imageStoragePath);
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage
                , "Title", null);
        return Uri.parse(path);
    }

    /**
     * Display image from gallery
     *
     * @param bitmap
     */
    //710 number
    //Todo : 710 nub line to visible camera button
    private void previewCapturedImage(String bitmap) {
        try {

            //     CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

            // hide video preview

            //    Bitmap bitmap1
            Bitmap myBitmap = BitmapFactory.decodeFile(bitmap);


            String pathhh = FileHelper.getPath(this, getImageUri(this, myBitmap));
            //  String pathhh = bitmap;


            ImageView imageView = new ImageView(application);
            imageView.setImageBitmap(myBitmap);
            Toast toast = new Toast(application);
            toast.setView(imageView);
            //  toast.show();


            final ImageData data = new ImageData();
            data.setTemp_imagePath(pathhh);
            data.setImagePath(pathhh);
            data.setTemp_org_imagePath(pathhh);
            application.addSelectedImage(data);

            PhotoPickupImageActivity.this.tvImageCount.setText(String.valueOf(PhotoPickupImageActivity.this.application.getOrgSelectedImages().size()));
            PhotoPickupImageActivity.this.selectedImageAdapter.notifyDataSetChanged();
            tvCountImages.setText("" + application.getOrgSelectedImages().size() + "/" + Share.itemnum);
            imageHorizontalAdapter.notifyDataSetChanged();
            albumImagesAdapter.notifyDataSetChanged();
            rvSelected.scrollToPosition(application.getOrgSelectedImages().size() - 1);

            if (application.getOrgSelectedImages().size() != 0) {
                llDelete.setEnabled(true);
                llDelete.setAlpha(1.0f);
            } else {
                llDelete.setEnabled(false);
                llDelete.setAlpha(0.5f);
            }

            // disable delete icon when no image selected
            if (application.getOrgSelectedImages().size() >= 3) {
                llEdit.setEnabled(true);
                llEdit.setAlpha(1.0f);
            } else {
                llEdit.setEnabled(false);
                llEdit.setAlpha(0.5f);
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    class C05822 implements OnClickListener {
        C05822() {
        }

        public void onClick(View v) {
            PhotoPickupImageActivity.this.clearData();
        }
    }

    class C10173 implements OnItemClickListner<Object> {
        C10173() {
        }

        public void onItemClick(View view, Object item) {
            PhotoPickupImageActivity.this.albumImagesAdapter.notifyDataSetChanged();
        }
    }

    class C10184 implements OnItemClickListner<Object> {
        C10184() {
        }

        public void onItemClick(View view, Object item) {

            //   application.getFolderList();


            PhotoPickupImageActivity.this.tvImageCount.setText(String.valueOf(PhotoPickupImageActivity.this.application.getOrgSelectedImages().size()));
            PhotoPickupImageActivity.this.selectedImageAdapter.notifyDataSetChanged();
            tvCountImages.setText("" + application.getOrgSelectedImages().size() + "/" + Share.itemnum);
            imageHorizontalAdapter.notifyDataSetChanged();
            albumImagesAdapter.notifyDataSetChanged();
            rvSelected.scrollToPosition(application.getOrgSelectedImages().size() - 1);

            if (application.getOrgSelectedImages().size() != 0) {
                llDelete.setEnabled(true);
                llDelete.setAlpha(1.0f);
            } else {
                llDelete.setEnabled(false);
                llDelete.setAlpha(0.5f);
            }

            // disable delete icon when no image selected
            if (application.getOrgSelectedImages().size() >= 3) {
                llEdit.setEnabled(true);
                llEdit.setAlpha(1.0f);
            } else {
                llEdit.setEnabled(false);
                llEdit.setAlpha(0.5f);
            }
        }
    }

    private class DeleteSelectedImage implements OnItemClickListner<ImageData> {
        @Override
        public void onItemClick(View view, ImageData data) {


            for (int i = 0; i < mainapplication.imageDatas.size(); i++) {

                if (mainapplication.imageDatas.get(i).imagePath.equalsIgnoreCase(data.imagePath)) {
                    ImageData data1 = mainapplication.imageDatas.get(i);
                    data1.setSelected(false);
                    mainapplication.imageDatas.set(i, data1);
                }

            }


            for (int i = 0; i < mainapplication.tempSelection.size(); i++) {

                if (mainapplication.tempSelection.get(i).equalsIgnoreCase(data.imagePath)) {
                    mainapplication.tempSelection.remove(i);
                }
            }


            albumImagesAdapter.notifyDataSetChanged();
            selectedImageAdapter.notifyDataSetChanged();
            tvCountImages.setText("" + application.getOrgSelectedImages().size() + "/" + Share.itemnum);

            if (application.getOrgSelectedImages().size() != 0) {
                llDelete.setEnabled(true);
                llDelete.setAlpha(1.0f);
            } else {
                llDelete.setEnabled(false);
                llDelete.setAlpha(0.5f);
                Share.end_slide_selected_or_not = false;
            }

            // disable delete icon when no image selected
            if (application.getOrgSelectedImages().size() >= 3) {
                llEdit.setEnabled(true);
                llEdit.setAlpha(1.0f);
            } else {
                llEdit.setEnabled(false);
                llEdit.setAlpha(0.5f);
            }
        }
    }

    class C10195 implements OnItemClickListner<Object> {
        C10195() {
        }

        public void onItemClick(View view, Object item) {
            PhotoPickupImageActivity.this.tvImageCount.setText(String.valueOf(PhotoPickupImageActivity.this.application.getOrgSelectedImages().size()));
            PhotoPickupImageActivity.this.albumImagesAdapter.notifyDataSetChanged();
            tvCountImages.setText("" + application.getOrgSelectedImages().size() + "/" + Share.itemnum);

            if (application.getOrgSelectedImages().size() != 0) {
                llDelete.setEnabled(true);
                llDelete.setAlpha(1.0f);
            } else {
                llDelete.setEnabled(false);
                llDelete.setAlpha(0.5f);
            }

            // disable delete icon when no image selected
            if (application.getOrgSelectedImages().size() >= 3) {
                llEdit.setEnabled(true);
                llEdit.setAlpha(1.0f);
            } else {
                llEdit.setEnabled(false);
                llEdit.setAlpha(0.5f);
            }
        }
    }


}
