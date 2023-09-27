package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.cover.photo.editor.back.maker.Paymentintegration.WebViewActivity;
import com.mobile.cover.photo.editor.back.maker.R;

public class Track_web_view_activity extends WebViewActivity {

    WebView track_webview;
    TextView tv_tracking_id, tv_detail, question;
    RelativeLayout rl_layout_main;
    Button btn_copy;
    ImageView id_back;
    ProgressDialog progressDialog, pd;
    String URL_link;
    int density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_web_view_activity);

        findview();
        density = getResources().getDisplayMetrics().densityDpi;
        Log.e("DENSITY", "onCreate: " + density);
    }

    private void findview() {
        track_webview = findViewById(R.id.track_webview);
        rl_layout_main = findViewById(R.id.rl_layout_main);
        tv_detail = findViewById(R.id.tv_detail);
        question = findViewById(R.id.question);
        tv_tracking_id = findViewById(R.id.tv_tracking_id);
//        tv_tracking_id.setText("Tracking ID: " + getIntent().getStringExtra("trackingid"));
        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_copy = findViewById(R.id.btn_copy);
        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("trackid", getIntent().getStringExtra("trackingid"));
                Toast.makeText(Track_web_view_activity.this, "Copied", Toast.LENGTH_SHORT).show();
                clipboard.setPrimaryClip(clip);
            }
        });


        progressDialog = ProgressDialog.show(Track_web_view_activity.this, "", getString(R.string.loading), true, false);
        progressDialog.show();
        track_webview.getSettings().setJavaScriptEnabled(true);
        track_webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        track_webview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(Track_web_view_activity.this, description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });
        Log.e("TRACKINGURL", "findview: " + getIntent().getStringExtra("trackinglink"));
        track_webview.loadUrl("tel:" + getIntent().getStringExtra("trackinglink"));
//        shouldOverrideUrlLoading(track_webview, getIntent().getStringExtra("trackinglink"));

    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            startActivity(intent);
            view.reload();
            return true;
        }

        view.loadUrl(url);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
