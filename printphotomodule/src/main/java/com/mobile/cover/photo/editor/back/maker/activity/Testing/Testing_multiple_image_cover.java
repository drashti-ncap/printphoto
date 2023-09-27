package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;

public class Testing_multiple_image_cover extends AppCompatActivity {
    RecyclerView rv_image_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_multiple_image_cover);
        rv_image_list = findViewById(R.id.rv_image_list);

    }
}
