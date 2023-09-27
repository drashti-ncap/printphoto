package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.variant.ProductDetails1;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.model.variant_value;

import java.util.ArrayList;
import java.util.List;


public class product_variant_adapter extends RecyclerView.Adapter<product_variant_adapter.MyViewHolder> {

    List<variant_value> variant = new ArrayList<>();
    private List<ProductDetails1> sqlitemodelList;
    private Context mContext;
    private boolean isWomen = false;

    public  List<String> variant_list_value = new ArrayList<>();

    public product_variant_adapter(Context mContext, List<ProductDetails1> sqlitemodelList,boolean isWomen ) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
        this.isWomen = isWomen;
        if (isWomen){
            variant_list_value =  Share.variant_list_value_women;
        }else {
            variant_list_value =  Share.variant_list_value;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_variant, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductDetails1 sqlitemodel = sqlitemodelList.get(position);
        holder.tv_variant_name.setText(sqlitemodel.getName());
        variant = new ArrayList<>();
        variant.clear();
        if (variant_list_value.size() != sqlitemodelList.size()) {
            for (int i = 0; i < sqlitemodel.getValue().size(); i++) {
                Log.e("HERE_COUNT", "onBindViewHolder: ========>" + i);
                variant_value variant_value;
                if (i == 0) {
                    variant_value = new variant_value(true, sqlitemodel.getValue().get(i));
                } else {
                    variant_value = new variant_value(false, sqlitemodel.getValue().get(i));
                }
                variant.add(variant_value);
            }
        } else {
            for (int i = 0; i < sqlitemodel.getValue().size(); i++) {
                variant_value variant_value;
                if (i == 0) {
                    variant_value = new variant_value(false, sqlitemodel.getValue().get(i));
                } else {
                    variant_value = new variant_value(false, sqlitemodel.getValue().get(i));
                }
                variant.add(variant_value);
            }

            for (int i = 0; i < variant.size(); i++) {
                for (int j = 0; j < variant_list_value.size(); j++) {
                    if (variant.get(i).getVariant_value().equalsIgnoreCase(variant_list_value.get(j).split("=")[1])) {
                        Log.e("VALUE_DATA_CHECK", "onBindViewHolder: " + variant.get(i).getVariant_value());
                        variant.get(i).setSelect(true);
                    }
                }
            }
        }


        product_variant_value_adapter product_variant_value_adapter = new product_variant_value_adapter(mContext, variant, sqlitemodel.getName(),isWomen);
        holder.rv_value.setLayoutManager(new GridLayoutManager(mContext, 4));
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(4, 12, true);
        holder.rv_value.addItemDecoration(gridSpacingItemDecoration);

        holder.rv_value.setAdapter(product_variant_value_adapter);
        holder.rv_value.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_variant_name;
        public RecyclerView rv_value;

        public MyViewHolder(View view) {
            super(view);
            tv_variant_name = view.findViewById(R.id.tv_variant_name);
            rv_value = view.findViewById(R.id.rv_value);
        }
    }
}
