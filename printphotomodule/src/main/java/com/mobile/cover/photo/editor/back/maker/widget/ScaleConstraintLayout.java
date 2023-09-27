package com.mobile.cover.photo.editor.back.maker.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ScaleConstraintLayout extends ConstraintLayout {
    public ScaleConstraintLayout(Context context) {
        super(context);
    }

    public ScaleConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
