package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;

public class fancy_mobile_cover extends AppCompatActivity {
    ProgressDialog pd;
    NestedScrollView nsv;
    RecyclerView rv;
    ImageView id_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_mobile_cover);

        rv = findViewById(R.id.main_recycler);
        nsv = findViewById(R.id.nsv);
    }
}
