package com.mobile.cover.photo.editor.back.maker.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;


public class ScaleCardView extends CardView {
    private static final int SPEC = MeasureSpec.EXACTLY;
    public int mAspectRatioHeight = 610;
    public int mAspectRatioWidth = 1080;

    public ScaleCardView(Context context) {
        super(context);
    }

    public ScaleCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onMeasure(int size, int size2) {
        if (this.mAspectRatioHeight == this.mAspectRatioWidth) {
            super.onMeasure(size, size);
        }
        size = MeasureSpec.getSize(size);
        size2 = MeasureSpec.getSize(size2);
        int n = (this.mAspectRatioHeight * size) / this.mAspectRatioWidth;
        if (n > size2) {
            size = (this.mAspectRatioWidth * size2) / this.mAspectRatioHeight;
        } else {
            size2 = n;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, SPEC), MeasureSpec.makeMeasureSpec(size2, SPEC));

    }
}
