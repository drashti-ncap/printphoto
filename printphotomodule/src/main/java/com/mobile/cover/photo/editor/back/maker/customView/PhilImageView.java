package com.mobile.cover.photo.editor.back.maker.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.mobile.cover.photo.editor.back.maker.customView.photoview.PhotoViewAttacher;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PhilImageView extends VectorImageView implements PhotoViewAttacher.OnPhotoTapListener, VectorImageView.OnImageCallbackListener, PhotoViewAttacher.OnMatrixChangedListener {

    public static int curColor = 0;
    ImageView iv_save, iv_undo, iv_redo, iv_reset;
    List<Path> sectorpath = new ArrayList<>();
    private PhotoViewAttacher photoViewAttacher;
    private PhilImageView philImageView;
    private Context mContext;
    private int curSector = -1;
    private Paint paint;
    private int prevColor = -1, selectorcolor = -1;
    private Matrix curMatrix;

    public PhilImageView(Context context) {
        super(context);
        this.mContext = context;
        initThis();
    }

    public PhilImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initThis();
    }

    public PhilImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initThis();
    }

    @Override
    public void load_urlAsset(InputStream string, Bitmap bitmap) {
        super.load_urlAsset(string, bitmap);

        this.iv_save = iv_save;
        this.iv_redo = iv_redo;
        this.iv_undo = iv_undo;
        this.iv_reset = iv_reset;
//        setSectorsDAO(new SectorsDAO(mContext, DataBaseHelper.SECTORS.SECTORS_PHIL));

        curMatrix = new Matrix();
        photoViewAttacher = new PhotoViewAttacher(philImageView);
        photoViewAttacher.getDisplayMatrix(curMatrix);
        photoViewAttacher.setMaximumScale(4);
        photoViewAttacher.setMediumScale(3);
        photoViewAttacher.setZoomable(false);
        photoViewAttacher.setOnPhotoTapListener(philImageView);
        photoViewAttacher.setOnMatrixChangeListener(philImageView);

        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    public void loadAsset(String string) {
        super.loadAsset(string);

        curMatrix = new Matrix();
        photoViewAttacher = new PhotoViewAttacher(philImageView);
        photoViewAttacher.getDisplayMatrix(curMatrix);
        photoViewAttacher.setMaximumScale(4);
        photoViewAttacher.setMediumScale(3);
        photoViewAttacher.setZoomable(false);
        photoViewAttacher.setOnPhotoTapListener(philImageView);
        photoViewAttacher.setOnMatrixChangeListener(philImageView);

        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    public void initThis() {
        philImageView = this;
    }


    @Override
    public void onPhotoTap(View view, float x, float y) {

//        Path curSectorpath = getSectorpath(x, y);
//        Log.e("path", "onPhotoTap: =====>" + curSectorpath);
//        RectF bounds = new RectF();
//        curSectorpath.computeBounds(bounds, false); // fills rect with bounds
//        PointF center = new PointF((bounds.left + bounds.right) / 2,
//                (bounds.top + bounds.bottom) / 2);
//        Log.e("path center", "cordinates: =====>" + (center.x - ((bounds.right - bounds.left) / 2)) + "//" + (center.y - ((bounds.bottom - bounds.top) / 2)));
//
//        Log.e("path center", "cordinates: =====>" + (bounds.right - bounds.left) + "//" + (bounds.bottom - bounds.top));
    }

    @Override
    public void onOutsidePhotoTap() {
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (paint != null) {
            paint.setColor(curColor);
            canvas.drawRect(
                    0, 0,
                    philImageView.getMeasuredWidth() - 1,
                    philImageView.getMeasuredHeight() - 1,
                    paint
            );
        }
    }

    @Override
    public void imageCallback() {
        photoViewAttacher.update();
        // curColor = getOnImageCommandsListener().getCurrentColor();
    }

    @Override
    public void onMatrixChanged(RectF rect) {
        photoViewAttacher.getDisplayMatrix(curMatrix);
    }
}