package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.complaints;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Paymentintegration.WebViewActivity;
import com.mobile.cover.photo.editor.back.maker.R;

import java.util.Locale;

public class Complain_ticket_system extends WebViewActivity {

    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final String TAG = "WEBVIEW";
    WebView mWebView;
    ImageView id_back;
    ProgressDialog progressDialog;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            Uri[] results = null;
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    if (data.getClipData() == null) {
                        String dataString = data.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    } else {
                        results = new Uri[data.getClipData().getItemCount()];

//                    String dataString = Uri.parse(data.getData().getPath());

                        for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                            results[i] = data.getClipData().getItemAt(i).getUri();
                        }

                    }
                }

            }
            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == this.mUploadMessage) {
                    return;
                }
                Uri result = null;
                try {
                    if (resultCode != RESULT_OK) {
                        result = null;
                    } else {
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
        return;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_webview_system);

        mWebView = findViewById(R.id.track_webview);

        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = ProgressDialog.show(Complain_ticket_system.this, "", getString(R.string.loading), true, false);
        progressDialog.show();

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.getSettings().setAllowFileAccess(true);
        Log.e("COMPLAINURL", "onCreate: complian url =>"+Share.complain_ticket_url);
        if (getIntent().getStringExtra("order_id").equalsIgnoreCase("")) {
            Log.e("COMPLAINURL", "onCreate:=== " + Share.complain_ticket_url + "?token=" + SharedPrefs.getString(Complain_ticket_system.this, SharedPrefs.TOKEN));
            mWebView.loadUrl(Share.complain_ticket_url + "?token=" + SharedPrefs.getString(Complain_ticket_system.this, SharedPrefs.TOKEN) + "&ln=" + Locale.getDefault().getLanguage());
        } else {
            Log.e("COMPLAINURL", "onCreate: " + Share.complain_ticket_url + "?token=" + SharedPrefs.getString(Complain_ticket_system.this, SharedPrefs.TOKEN) + "&order_id=" + getIntent().getStringExtra("order_id"));
            mWebView.loadUrl(Share.complain_ticket_url + "?token=" + SharedPrefs.getString(Complain_ticket_system.this, SharedPrefs.TOKEN) + "&order_id=" + getIntent().getStringExtra("order_id") + "&ln=" + Locale.getDefault().getLanguage());
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePath;
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");
                contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                Intent[] intentArray;
                intentArray = new Intent[0];
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
                return true;
            }


//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//                mUploadMessage = uploadMsg;
//                File imageStorageDir = new File(
//                        Environment.getExternalStoragePublicDirectory(
//                                Environment.DIRECTORY_PICTURES)
//                        , "AndroidExampleFolder");
//                if (!imageStorageDir.exists()) {
//                    imageStorageDir.mkdirs();
//                }
//                File file = new File(
//                        imageStorageDir + File.separator + "IMG_"
//                                + String.valueOf(System.currentTimeMillis())
//                                + ".jpg");
//                mCapturedImageURI = Uri.fromFile(file);
//                final Intent captureIntent = new Intent(
//                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
//                        , new Parcelable[]{captureIntent});
//                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
//            }

//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                openFileChooser(uploadMsg, "");
//            }
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg,
//                                        String acceptType,
//                                        String capture) {
//                openFileChooser(uploadMsg, acceptType);
//            }

        });
    }

}

