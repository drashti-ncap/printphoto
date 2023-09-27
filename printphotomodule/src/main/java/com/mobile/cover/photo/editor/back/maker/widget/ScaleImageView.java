package com.mobile.cover.photo.editor.back.maker.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;


public class ScaleImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final int SPEC = MeasureSpec.EXACTLY;
    public int mAspectRatioHeight = 610;
    public int mAspectRatioWidth = 1080;

    public ScaleImageView(Context context) {
        super(context);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
