package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.R;

public class testing_webview extends AppCompatActivity {
    WebView webview;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_webview);
        webview = findViewById(R.id.webview);
        progressDialog = ProgressDialog.show(testing_webview.this, "", getString(R.string.loading), true, false);
        webview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(testing_webview.this, description, Toast.LENGTH_SHORT).show();
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
        webview.loadUrl("http://192.168.25.97/test.html");
        webview.getSettings().setJavaScriptEnabled(true);
    }
}
