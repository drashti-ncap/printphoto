package com.mobile.cover.photo.editor.back.maker.Pagination;

import android.view.View;
import android.view.ViewTreeObserver;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

/**
 * Pagination
 * Created by Suleiman19 on 10/15/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */
public abstract class PaginationScrollListener implements NestedScrollView.OnScrollChangeListener {

    GridLayoutManager layoutManager;

    /**
     * Supporting only LinearLayoutManager for now.
     *
     * @param layoutManager
     */
    public PaginationScrollListener(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

//    @Override
//    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        super.onScrolled(recyclerView, dx, dy);
//
//
//
//    }

    @Override
    public void onScrollChange(final NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {

        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView
                        .getScrollY()));

                if (diff == 0) {
                    // your pagination code
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading() && !isLastPage()) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            loadMoreItems();
                        }
                    }
                }
            }
        });
    }


    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
