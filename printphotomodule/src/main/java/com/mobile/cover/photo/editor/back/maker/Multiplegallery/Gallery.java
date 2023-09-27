package com.mobile.cover.photo.editor.back.maker.Multiplegallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Drag_recyclerview.MainActivity;
import com.mobile.cover.photo.editor.back.maker.Multiplegallery.Fragments.OneFragment;
import com.mobile.cover.photo.editor.back.maker.R;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends AppCompatActivity {
    public static int selectionTitle;
    public static String title;
    public static int maxSelection;
    public static int mode;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        maxSelection = 6;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OpenGallery.imagesSelected.size() != maxSelection) {
                    returnResult();
                } else {
                    //         final ProgressDialog pd = ProgressDialog.show(Gallery.this, "", "Processing...", true, false);
                    //         pd.show();
                    id = 0;
                    //     for (int i = 0; i < OpenGallery.imagesSelected.size(); i++) {

                    setBitmapImages();

                    Log.e("SIZE", "onClick: " + Share.frame_bitmap.size());


                }
            }

        });

        if (maxSelection == 0) maxSelection = Integer.MAX_VALUE;
        mode = 1;
        selectionTitle = 0;

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        OpenGallery.selected.clear();
        OpenGallery.imagesSelected.clear();

    }

    private void setBitmapImages() {
//        final frame_bitmap_model frame_bitmap_model_new = new frame_bitmap_model();
//        Glide.with(Gallery.this).load("file://" + OpenGallery.imagesSelected.get(id)).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                frame_bitmap_model_new.setOriginal_bitmap(bitmap);
//                frame_bitmap_model_new.setId(id);
//                Share.frame_bitmap.add(frame_bitmap_model_new);
//                id++;
//                if (Share.frame_bitmap.size() == 6) {
//                  //  pd.dismiss();
//                    Intent intent = new Intent(Gallery.this, MainActivity.class);
//                    startActivity(intent);
//                }
//                if (id < 6)
//                {
//                    setBitmapImages();
//                }
//            }
//        });

        Intent intent = new Intent(Gallery.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (selectionTitle > 0) {
            setTitle(String.valueOf(selectionTitle));
        }
    }

    //This method set up the tab view for images and videos
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Images");
        viewPager.setAdapter(adapter);
    }

    private void returnResult() {
        Intent returnIntent = new Intent();
        returnIntent.putStringArrayListExtra("result", OpenGallery.imagesSelected);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}