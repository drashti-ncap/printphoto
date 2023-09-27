package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.transactionadapter;

import static com.mobile.cover.photo.editor.back.maker.activity.Usefull.SellerWalletActivity.sqlist;

public class Seller_transaction_activity extends AppCompatActivity {
    RecyclerView rv_transaction_history;
    ImageView id_back;
    TextView tv_no_transaction;
    transactionadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_transaction_activity);
        findviews();
    }

    private void findviews() {
        rv_transaction_history = findViewById(R.id.rv_transaction_history);
        tv_no_transaction = findViewById(R.id.tv_no_transaction);
        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (sqlist.size() <= 0) {
            tv_no_transaction.setVisibility(View.VISIBLE);
            rv_transaction_history.setVisibility(View.GONE);
        } else {
            rv_transaction_history.setVisibility(View.VISIBLE);
            tv_no_transaction.setVisibility(View.GONE);
            mAdapter = new transactionadapter(Seller_transaction_activity.this, sqlist);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Seller_transaction_activity.this);
            rv_transaction_history.setLayoutManager(mLayoutManager);
            rv_transaction_history.setItemAnimator(new DefaultItemAnimator());
            rv_transaction_history.setAdapter(mAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
