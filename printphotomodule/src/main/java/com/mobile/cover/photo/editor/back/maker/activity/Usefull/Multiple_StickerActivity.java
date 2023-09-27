package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.Sticker;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.sub_StickerAdapter;
import com.mobile.cover.photo.editor.back.maker.model.StickerModel;

import java.util.ArrayList;
import java.util.List;


public class Multiple_StickerActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView gridview_symbol;
    ProgressDialog pd;
    private ImageView iv_more_app, iv_blast, iv_back, iv_more;
    private TextView tv_title;
    private Toolbar toolbar_top;
    private sub_StickerAdapter gridViewAdapter;
    private List<StickerModel> list = new ArrayList<>();
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<Sticker> sqlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mulitple_activity_sticker);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(Multiple_StickerActivity.this);

        initView();
    }

    private void initView() {
        sqlist = Share.sqlist_multiple_sticker;
        gridview_symbol = findViewById(R.id.gridview_symbol);
        gridViewAdapter = new sub_StickerAdapter(Multiple_StickerActivity.this, sqlist);
        gridview_symbol.setLayoutManager(new GridLayoutManager(Multiple_StickerActivity.this, 4));
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(4, 16, true);
        gridview_symbol.addItemDecoration(gridSpacingItemDecoration);
        gridview_symbol.setAdapter(gridViewAdapter);

        Intent i = getIntent();

        int position = Share.SelectStickerPosition;

        iv_back = findViewById(R.id.iv_back1);
        tv_title = findViewById(R.id.tv_title);

        iv_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view == iv_back) {

            finish();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        getMainData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f / spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


}
