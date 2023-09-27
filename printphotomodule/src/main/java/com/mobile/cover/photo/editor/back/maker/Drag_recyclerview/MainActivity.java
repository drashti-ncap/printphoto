package com.mobile.cover.photo.editor.back.maker.Drag_recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Drag_recyclerview.adapter.MyAdapterRecyclerView;
import com.mobile.cover.photo.editor.back.maker.Drag_recyclerview.adapter.MyItemTouchHelperCallback;
import com.mobile.cover.photo.editor.back.maker.Drag_recyclerview.interfaces.CallbackItemTouch;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.test_frame_editing_activity;

import java.util.Collections;


public class MainActivity extends AppCompatActivity implements CallbackItemTouch {

    Button btn_continue;
    private RecyclerView mRecyclerView;
    private MyAdapterRecyclerView myAdapterRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        initList();
        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < Share.frame_bitmap.size(); i++) {
                    Share.frame_bitmap.get(i).setId(i);
                }
//                Share.result_frame_bitmap.clear();
//                for (int i = 0; i <Share.frame_bitmap.size() ; i++) {
//                    new_frame_bitmap_model new_frame_bitmap_model=new new_frame_bitmap_model();
//                    new_frame_bitmap_model.setBitmap(Share.frame_bitmap.get(i).getOriginal_bitmap());
//                    new_frame_bitmap_model.setId(Share.frame_bitmap.get(i).getId());
//                    Share.result_frame_bitmap.add(new_frame_bitmap_model);
//                }
                Intent intent = new Intent(MainActivity.this, test_frame_editing_activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        myAdapterRecyclerView.notifyDataSetChanged();
    }

    private void initList() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        myAdapterRecyclerView = new MyAdapterRecyclerView(this, Share.frame_bitmap);
        mRecyclerView.setAdapter(myAdapterRecyclerView);
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(Share.frame_bitmap, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(Share.frame_bitmap, i, i - 1);
            }
        }
        myAdapterRecyclerView.notifyItemMoved(oldPosition, newPosition);
    }
}
