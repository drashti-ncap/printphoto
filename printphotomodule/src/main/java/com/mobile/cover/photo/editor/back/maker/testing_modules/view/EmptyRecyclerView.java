package com.mobile.cover.photo.editor.back.maker.testing_modules.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EmptyRecyclerView extends RecyclerView {
    private AdapterDataObserver dataObserver;
    private View emptyView;

    public EmptyRecyclerView(Context context) {
        this(context, null);
    }

    public EmptyRecyclerView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public EmptyRecyclerView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        this.dataObserver = new C10371();
    }

    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.dataObserver);
        }
        this.dataObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    class C10371 extends AdapterDataObserver {
        C10371() {
        }

        public void onChanged() {
            Adapter<?> adapter = EmptyRecyclerView.this.getAdapter();
            if (adapter != null && EmptyRecyclerView.this.emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    EmptyRecyclerView.this.emptyView.setVisibility(VISIBLE);
                    EmptyRecyclerView.this.setVisibility(GONE);
                    return;
                }
                EmptyRecyclerView.this.emptyView.setVisibility(GONE);
                EmptyRecyclerView.this.setVisibility(VISIBLE);
            }
        }
    }
}
