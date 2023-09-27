package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.R;

import java.util.List;

public class paypal_demo_response extends AppCompatActivity {
    TextView tv_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_demo_response);
        tv_status = findViewById(R.id.tv_status);
        if (getIntent().getData().getPathSegments().size() != 0) {

            List<String> params = getIntent().getData().getPathSegments();
            for (int i = 0; i < params.size(); i++) {
                Log.e("VALUE", "onResume: " + params.get(i));
            }
            tv_status.setText(params.get(0));
        }
    }
}
