package com.mobile.cover.photo.editor.back.maker.aaNewUpdate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.R;

public class PrintPhotoBaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    protected void showProgressDialog(Context activity) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            try {
                progressDialog.show();
            } catch (WindowManager.BadTokenException e) {

            }
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_dialog);
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    protected void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
