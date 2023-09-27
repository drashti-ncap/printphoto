package com.mobile.cover.photo.editor.back.maker.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.isseiaoki.simplecropview.util.Utils;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Un_Usefull_Old.CropImageActivity1;
import com.mobile.cover.photo.editor.back.maker.customView.FontUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainFragment extends Fragment {
    private static final int REQUEST_PICK_IMAGE = 10011;
    private static final int REQUEST_SAF_PICK_IMAGE = 10012;
    private static final String PROGRESS_DIALOG = "ProgressDialog";
    private final int STORAGE_PERMISSION_CODE = 23;
    private final LoadCallback mLoadCallback = new LoadCallback() {
        @Override
        public void onSuccess() {
            dismissProgress();
        }

        @Override
        public void onError(Throwable e) {
            dismissProgress();
        }
    };
    // Callbacks ///////////////////////////////////////////////////////////////////////////////////
    private final CropCallback mCropCallback = new CropCallback() {
        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onSuccess(final Bitmap cropped) {
            Log.e("TAG", "mCropCallback:==>" + cropped);
            Log.e("mCropCallback", "mCropCallback");

            /*if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();*/

            dismissProgress();
            ((CropImageActivity1) getActivity()).startResultActivity(cropped);
        }
    };
    private final SaveCallback mSaveCallback = new SaveCallback() {
        @Override
        public void onSuccess(Uri outputUri) {
            Log.e("mSaveCallback", "mSaveCallback");
            dismissProgress();
            /*if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();*/
            //   ((CropImageActivity1) getActivity()).startResultActivity(outputUri);
        }

        @Override
        public void onError(Throwable e) {
            dismissProgress();
        }
    };
    // Views ///////////////////////////////////////////////////////////////////////////////////////
    private LinearLayout ll_bottom_bar;
    private CropImageView mCropView;
    private LinearLayout mRootLayout;
    private List<String> listPermissionsNeeded = new ArrayList<>();
    private final View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.buttonDone) {
                if (checkAndRequestPermissions()) {
                    cropImage();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), STORAGE_PERMISSION_CODE);
                }
//                    MainFragmentPermissionsDispatcher.cropImageWithCheck(MainFragment.this);
            } else if (id == R.id.buttonFitImage) {
                mCropView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
            } else if (id == R.id.button1_1) {
                mCropView.setCropMode(CropImageView.CropMode.SQUARE);
            } else if (id == R.id.button3_4) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
            } else if (id == R.id.button4_3) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_4_3);
            } else if (id == R.id.button9_16) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
            } else if (id == R.id.button16_9) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
            } else if (id == R.id.buttonCustom) {
                mCropView.setCustomRatio(7, 5);
            } else if (id == R.id.buttonFree) {
                mCropView.setCropMode(CropImageView.CropMode.FREE);
            } else if (id == R.id.buttonCircle) {
                mCropView.setCropMode(CropImageView.CropMode.CIRCLE);
            } else if (id == R.id.buttonShowCircleButCropAsSquare) {
                mCropView.setCropMode(CropImageView.CropMode.CIRCLE_SQUARE);
            } else if (id == R.id.buttonRotateLeft) {
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
            } else if (id == R.id.buttonRotateRight) {
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
            } else if (id == R.id.tv_back) {
                Log.e("MainFragment", "--> tv_back");
                GlobalData.isFromHomeAgain = false;
                if (GlobalData.is_home_back) {

                    //TODO uncomment
//                        Intent splashMenuIntent = new Intent(getActivity(), Splash_MenuActivity.class);
//                        splashMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(splashMenuIntent);
                    getActivity().finish();
                } else
                    getActivity().finish();
            }
        }

    };

    // Note: only the system can call this constructor by reflection.
    public MainFragment() {
    }

    public static MainFragment getInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    // Bind views //////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.e("MainFragment", "onCreate");
        showProgress();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            showProgress();
            mCropView.startLoad(result.getData(), mLoadCallback);
        } else if (requestCode == REQUEST_SAF_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            showProgress();
            mCropView.startLoad(Utils.ensureUriPermission(getContext(), result), mLoadCallback);
        }
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void pickImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_SAF_PICK_IMAGE);
        }
    }

    public void cropImage() {
        Log.e("cropImage", "cropImage");
        showProgress();
//        progressDialog = GlobalData.createProgressDialog(getActivity());
//        progressDialog.setCancelable(false);
        mCropView.startCrop(createSaveUri(), mCropCallback, mSaveCallback);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void showRationaleForPick(PermissionRequest request) {
        showRationaleDialog(R.string.permission_pick_rationale, request);
    }

    public void showRationaleForCrop(PermissionRequest request) {
        showRationaleDialog(R.string.permission_crop_rationale, request);
    }

    public void showProgress() {
        ProgressDialogFragment f = ProgressDialogFragment.getInstance();
        getFragmentManager()
                .beginTransaction()
                .add(f, PROGRESS_DIALOG)
                .commitAllowingStateLoss();
    }

    private void bindViews(View view) {
        ll_bottom_bar = view.findViewById(R.id.ll_bottom_bar);
        mCropView = view.findViewById(R.id.cropImageView);
        view.findViewById(R.id.buttonDone).setOnClickListener(btnListener);
        view.findViewById(R.id.buttonFitImage).setOnClickListener(btnListener);
        view.findViewById(R.id.button1_1).setOnClickListener(btnListener);
        view.findViewById(R.id.button3_4).setOnClickListener(btnListener);
        view.findViewById(R.id.button4_3).setOnClickListener(btnListener);
        view.findViewById(R.id.button9_16).setOnClickListener(btnListener);
        view.findViewById(R.id.button16_9).setOnClickListener(btnListener);
        view.findViewById(R.id.buttonFree).setOnClickListener(btnListener);
        view.findViewById(R.id.buttonRotateLeft).setOnClickListener(btnListener);
        view.findViewById(R.id.buttonRotateRight).setOnClickListener(btnListener);
        view.findViewById(R.id.buttonCustom).setOnClickListener(btnListener);
        view.findViewById(R.id.buttonCircle).setOnClickListener(btnListener);
        view.findViewById(R.id.buttonShowCircleButCropAsSquare).setOnClickListener(btnListener);
        view.findViewById(R.id.tv_back).setOnClickListener(btnListener);
        mRootLayout = view.findViewById(R.id.layout_root);
    }

    // Handle button event /////////////////////////////////////////////////////////////////////////

    public Uri createSaveUri() {
        return Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    public void dismissProgress() {
        if (!isAdded()) return;
        FragmentManager manager = getFragmentManager();
        if (manager == null) return;
        ProgressDialogFragment f = (ProgressDialogFragment) manager.findFragmentByTag(PROGRESS_DIALOG);
        if (f != null) {
            getFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
        }
    }

    private boolean checkAndRequestPermissions() {
        listPermissionsNeeded.clear();

        int storage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStorage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int readMediaStorage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_MEDIA_IMAGES);
        int camera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.R) {
            if (storage != PackageManager.PERMISSION_GRANTED) {
                GlobalData.msg = "storage";
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        }
        if (readStorage != PackageManager.PERMISSION_GRANTED) {
            GlobalData.msg = "storage";
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU) {
            if (readMediaStorage != PackageManager.PERMISSION_GRANTED) {
                GlobalData.msg = "storage";
                listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
            }
        }

        if (camera != PackageManager.PERMISSION_GRANTED) {
            if (GlobalData.msg == null) {
                GlobalData.msg = "camera";
            } else {
                GlobalData.msg += "";
            }
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        return listPermissionsNeeded.isEmpty();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // bind Views
        bindViews(view);

        if (getActivity().getIntent().hasExtra("activity")) {
            ll_bottom_bar.setVisibility(View.GONE);
            mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
        }

        // apply custom font
        FontUtils.setFont(mRootLayout);
//        mCropView.setDebug(true);
        // set bitmap to CropImageView
        if (mCropView.getImageBitmap() == null) {
            Log.e("TAG", "image uri1111:==>" + GlobalData.imageUrl);
            Glide.with(getActivity()).asBitmap()
                    .load(GlobalData.imageUrl)

                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .override(300, 300)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                            Log.e("MainFragment", "onResourceReady");
                            dismissProgress();
                            mCropView.setImageBitmap(resource);
                        }
                    });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCropView.getImageBitmap() == null) {
            Log.e("TAG", "image uri1111:==>" + GlobalData.imageUrl);

            if (GlobalData.imageUrl != null && !GlobalData.imageUrl.equals("")) {
                Glide.with(getActivity()).asBitmap()
                        .load(GlobalData.imageUrl)

                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .override(300, 300)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                mCropView.setImageBitmap(resource);
                            }
                        });
            } else {
                Log.e("CHECKDATA", "RestartAppForOnlyStorage: 5" );
                if (GlobalData.RestartAppHome(getActivity())) {

                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mCropView.setImageDrawable(null);
    }
}