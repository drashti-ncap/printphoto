package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.fragment.InstagramFragment;
import com.mobile.cover.photo.editor.back.maker.fragment.PhotoFragment;


public class FaceActivity extends AppCompatActivity {

    public static final String IMAGE_SOURCE = "image_source";
    public static final String ALLOW_MULTIPLE_IMAGES = "allow_multiple_images";
    private static final String TAG = FaceActivity.class.getSimpleName();
    public static ImageView iv_more_app, iv_blast;
    public static Boolean is_closed = true;
    public static FaceActivity faceActivity;
    Activity activity;
    private ImageView iv_close, iv_ads;
    private FrameLayout simpleFrameLayout;
    private TabLayout tabLayout;
    private TextView tv_title;
    private FirebaseAnalytics mFirebaseAnalytics;

    public static FaceActivity getFaceActivity() {
        return faceActivity;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        activity = FaceActivity.this;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setToolbar();
        initView();
        initViewAction();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);


        tv_title = toolbar.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.choose_photo));

        setSupportActionBar(toolbar);
    }

    private void initView() {
        iv_close = findViewById(R.id.iv_close);

        simpleFrameLayout = findViewById(R.id.simpleFrameLayout);
        tabLayout = findViewById(R.id.simpleTabLayout);

        TabLayout.Tab PhotoTab = tabLayout.newTab();
        PhotoTab.setText(getResources().getString(R.string.Photo));
        PhotoTab.setIcon(R.drawable.tab_photo_selector);
        tabLayout.addTab(PhotoTab);
        PhotoTab.select();

//        TabLayout.Tab FacebookTab = tabLayout.newTab();
//        FacebookTab.setText(getResources().getString(R.string.Facebook));
//        FacebookTab.setIcon(R.drawable.tab_facebook_selector);
//        tabLayout.addTab(FacebookTab);

//        TabLayout.Tab InstagramTab = tabLayout.newTab();
//        InstagramTab.setText(getResources().getString(R.string.Instagram));
//        InstagramTab.setIcon(R.drawable.tab_instagram_selection);
//        tabLayout.addTab(InstagramTab);

        faceActivity = this;
    }

    private void initViewAction() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        tv_title.setText(getResources().getString(R.string.Photo));
                        fragment = PhotoFragment.newInstance();
                        break;
                    case 1:
                        tv_title.setText(getResources().getString(R.string.Instagram));
                        fragment = InstagramFragment.newInstance();
                        break;
                }
                updateFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        updateFragment(PhotoFragment.newInstance());
    }

    private void updateFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public void onCloseFace(View view) {
        GlobalData.isFromHomeAgain = false;
        finish();
    }

    @Override
    public void onBackPressed() {
        GlobalData.isFromHomeAgain = false;
        Share.lst_album_image.clear();
        super.onBackPressed();
        finish();
    }
}

