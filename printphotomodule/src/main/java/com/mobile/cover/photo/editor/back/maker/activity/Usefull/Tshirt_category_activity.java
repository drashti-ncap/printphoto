package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.R;

public class Tshirt_category_activity extends AppCompatActivity implements View.OnClickListener {

    Button btn_only_front, btn_only_back, btn_front_back, btn_back_logo, btn_logo_only;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt_category_activity);
        findviews();
        initlistener();
    }

    private void findviews() {
        btn_only_front = findViewById(R.id.btn_only_front);
        btn_only_back = findViewById(R.id.btn_only_back);
        btn_front_back = findViewById(R.id.btn_front_back);
        btn_back_logo = findViewById(R.id.btn_back_logo);
        btn_logo_only = findViewById(R.id.btn_logo_only);
    }

    private void initlistener() {
        btn_only_front.setOnClickListener(this);
        btn_only_back.setOnClickListener(this);
        btn_front_back.setOnClickListener(this);
        btn_back_logo.setOnClickListener(this);
        btn_logo_only.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_only_front) {
            Intent only_front = new Intent(this, Tshirt_Editing_Activity.class);
            only_front.putExtra("type", "front");
            startActivity(only_front);
        } else if (id == R.id.btn_only_back) {
            Intent only_back = new Intent(this, Tshirt_Editing_Activity.class);
            only_back.putExtra("type", "back");
            startActivity(only_back);
        } else if (id == R.id.btn_front_back) {
            Intent front_back = new Intent(this, Tshirt_Editing_Activity.class);
            front_back.putExtra("type", "front_back");
            startActivity(front_back);
        } else if (id == R.id.btn_back_logo) {
            Intent back_logo = new Intent(this, Tshirt_Editing_Activity.class);
            back_logo.putExtra("type", "back_logo");
            startActivity(back_logo);
        } else if (id == R.id.btn_logo_only) {
            Intent logo_only = new Intent(this, Tshirt_Editing_Activity.class);
            logo_only.putExtra("type", "logo_only");
            startActivity(logo_only);
        }
    }
}
