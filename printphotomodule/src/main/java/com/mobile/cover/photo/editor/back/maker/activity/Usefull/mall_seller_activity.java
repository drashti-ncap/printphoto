package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;

public class mall_seller_activity extends AppCompatActivity implements View.OnClickListener {
    public static Activity activity;
    TextView tv_seller_name, tv_seller_email, tv_seller_number, tv_seller_address;
    LinearLayout ll_email, ll_call, ll_loc;
    ImageView id_back, iv_wish_list_disp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_seller_activity);
        activity = mall_seller_activity.this;
        findviews();
        initlistener();
    }

    private void findviews() {
        tv_seller_name = findViewById(R.id.tv_seller_name);
        tv_seller_email = findViewById(R.id.tv_seller_email);
        tv_seller_number = findViewById(R.id.tv_seller_number);
        tv_seller_address = findViewById(R.id.tv_seller_address);

        id_back = findViewById(R.id.id_back);
        iv_wish_list_disp = findViewById(R.id.iv_wish_list_disp);

        tv_seller_name.setText(Share.mall_seller_details.getName());
        tv_seller_email.setText(Share.mall_seller_details.getEmail());
        tv_seller_number.setText(Share.mall_seller_details.getMobile());
        tv_seller_address.setText(Share.mall_seller_details.getAddress());

        ll_email = findViewById(R.id.ll_email);
        ll_call = findViewById(R.id.ll_call);
        ll_loc = findViewById(R.id.ll_loc);
    }

    private void initlistener() {
        ll_email.setOnClickListener(this);
        ll_call.setOnClickListener(this);
        ll_loc.setOnClickListener(this);
        id_back.setOnClickListener(this);
        iv_wish_list_disp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_email) {
            sendmail();
        } else if (id == R.id.ll_call) {
            whatsapp(Share.mall_seller_details.getMobile());
        } else if (id == R.id.ll_loc) {
            Geolocation();
        } else if (id == R.id.id_back) {
            finish();
        } else if (id == R.id.iv_wish_list_disp) {
            if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mall_seller_activity.this);
                alertDialog.setTitle(getString(R.string.log_in));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.need_login));
                alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent intent = new Intent(mall_seller_activity.this, LogInActivity.class);
                        startActivity(intent);

                    }
                });
                alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialog.create().show();
            } else {
                if (Mall_wishlist.activity != null) {
                    Mall_wishlist.activity.finish();
                }
                Intent wish_list_intent = new Intent(mall_seller_activity.this, Mall_wishlist.class);
                startActivity(wish_list_intent);
            }
        }
    }

    public void sendmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipients = {Share.mall_seller_details.getEmail()};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }

    public void Geolocation() {
        try {
            String geoUri = "geo:0,0?q=" + Share.mall_seller_details.getAddress();
            Log.e("URI", "Geolocation: " + geoUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            startActivity(intent);
        } catch (Exception e) {
            Log.e("MAP_NOT_FOUND", "Geolocation: " + e.getLocalizedMessage());
            Toast.makeText(activity, getString(R.string.install_google_map), Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("NewApi")
    public void whatsapp(String phone) {
//        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phone + "&text=" + "Provide your complain here");
//
//        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
//
//        startActivity(sendIntent);
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, getString(R.string.no_app_available), Toast.LENGTH_SHORT).show();
        }

    }


}
