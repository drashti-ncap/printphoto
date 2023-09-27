package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.library21.custom.SwipeRefreshLayoutBottom;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.FacebookAlbumPhotoAdapter;
import com.mobile.cover.photo.editor.back.maker.customView.recycleview.CustomRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FacebookAlbumPhotoActivity extends AppCompatActivity {

    private static final String TAG = FacebookAlbumPhotoActivity.class.getSimpleName();
    public static Activity activity;
    public static ImageView iv_more_app, iv_blast;
    public static Boolean is_closed = true;

    private RecyclerView rv_album_photos;
    private RecyclerView.LayoutManager gridLayoutManager;
    private CustomRecyclerViewAdapter albumPhotoAdapter;
    private List<JSONObject> albumPhotoList = new ArrayList<JSONObject>();
    private SwipeRefreshLayoutBottom swipeContainer;
    private String pagingString = "";
    private FirebaseAnalytics mFirebaseAnalytics;


    private void setActionBar() {

        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_photo);

        activity = FacebookAlbumPhotoActivity.this;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Log.e("FacebookAlbumPhotoActivity", "onCreate");

        setActionBar();
        initView();
        initViewAction();
    }

    private void initView() {
        rv_album_photos = findViewById(R.id.rv_album_photos);
        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setEnabled(true);
        setGridLayoutManager();
    }

    private void initViewAction() {
        try {
            setToolbar();
            albumPhotoList.clear();
            loadAlbumPhotos("");
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayoutBottom.OnRefreshListener() {
                @Override
                public void onRefresh() {
//                    is_clickable =false;
                    swipeContainer.setRefreshing(false);
                    if (pagingString.length() > 0)
                        loadAlbumPhotos(pagingString);
                    else
                        Log.e(TAG, "else   loadAlbumPhotos ");
                    Log.d("Swipe", "Refreshing Number");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setToolbar() {
        try {
            String albumName = getIntent().getExtras().getString(GlobalData.KEYNAME.ALBUM_NAME);
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
//            toolbar.setTitle(albumName);

            TextView tv_title = toolbar.findViewById(R.id.tv_title);
            tv_title.setText(albumName);

            ImageView iv_back = toolbar.findViewById(R.id.iv_back);
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            setSupportActionBar(toolbar);
           /* toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        GlobalData.isFromHomeAgain = false;
        Log.e("FacebookAlbumPhotoActivity", "onBackPressed");
        super.onBackPressed();
        Log.e("FacebookAlbumPhotoActivity", "onBackPressed");
        finish();
    }

    private void setGridLayoutManager() {
        rv_album_photos.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(this, 3);
        rv_album_photos.setLayoutManager(gridLayoutManager);
        albumPhotoList.clear();
        albumPhotoAdapter = new FacebookAlbumPhotoAdapter(this, albumPhotoList);
        rv_album_photos.setAdapter(albumPhotoAdapter);
    }

    private void setAdapter() {
        albumPhotoAdapter.notifyDataSetChanged();
        rv_album_photos.invalidate();

        if (rv_album_photos.getAdapter().getItemCount() > 14)
            rv_album_photos.scrollToPosition(rv_album_photos.getAdapter().getItemCount() - 14);
    }

    private void loadAlbumPhotos(String paging) {
        try {
            final String albumID = getIntent().getExtras().getString(GlobalData.KEYNAME.ALBUM_ID);
            Log.e(TAG, "albumID : " + "/" + albumID + "/photos?pretty=0&fields=picture&limit=20&after=" + paging);
            new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + albumID + "/photos?pretty=0&fields=picture,images&limit=21&type=uploaded&after=" + paging,
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                if (response.getError() == null) {
                                    JSONObject data_response = response.getJSONObject();
                                    JSONArray data_photos = data_response.getJSONArray("data");
                                    if (data_photos.length() != 0) {
                                        Log.e(TAG, "GraphResponse : " + data_photos.get(0).toString());
                                        for (int index = 0; index < data_photos.length(); index++) {
                                            albumPhotoList.add(data_photos.getJSONObject(index));
                                        }
                                        albumPhotoAdapter.notifyDataSetChanged();
                                        JSONObject paging = data_response.getJSONObject("paging");
                                        if (paging.has("next")) {
                                            pagingString = paging.getJSONObject("cursors").getString("after");
                                        } else {
                                            pagingString = " ";
                                            swipeContainer.setEnabled(false);
                                        }
                                        setAdapter();
                                    } else {
                                        AlertDialog.Builder alert = new AlertDialog.Builder(FacebookAlbumPhotoActivity.this);
                                        alert.setMessage("No Images");
                                        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                                finish();
                                            }
                                        });
                                        alert.show();
                                    }
                                } else {
                                    Log.d(TAG, "getErrorCode : " + response.getError());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}