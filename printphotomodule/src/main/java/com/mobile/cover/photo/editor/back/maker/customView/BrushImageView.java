package com.mobile.cover.photo.editor.back.maker.customView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by buz on 17.06.16.
 * Vector Brush class
 */
public class BrushImageView extends VectorImageView implements View.OnTouchListener, VectorImageView.OnImageCommandsListener {

    private int prevSector;

    private boolean onPush = true;

    private Context mContext;

    public BrushImageView(Context context) {
        super(context);
        this.mContext = context;
        initThis();
    }

    public BrushImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initThis();
    }

    public BrushImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initThis();
    }

    @Override
    public void initThis() {
        setOnTouchListener(this);
    }

    @Override
    public void loadAsset(String string) {
        super.loadAsset(string);
//        setSectorsDAO(new SectorsDAO(mContext, DataBaseHelper.SECTORS.SECTORS_BRUSH));
    }

    private void setFirstColorSector(int firstColorSector) {
        setSectorColor(0, firstColorSector);
    }

    private int getFirsSectorColor() {
        return getColorFromSector(0);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int sector = getSector((ImageView) v, event.getX(), event.getY());
        Log.e("ontouch", "onTouch: " + sector);
     /*   int color = getColorFromSector(sector);

        if (sector != 0 && sector != 0xFFFFFFFF && sector != prevSector) {
            int c = getFirsSectorColor();
            prevSector = sector;
            setFirstColorSector(color);
            for (int i = sector; i >= 1; i--) {
                setSectorColor(i, getColorFromSector(i-1));
            }
            setSectorColor(1, c);
            updatePicture();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            prevSector = -1;
        }*/

        Path curSectorpath = VectorImageView.sectorsPaths.get(sector);
        Log.e("path", "onPhotoTap: =====>" + curSectorpath);
        RectF bounds = new RectF();
        curSectorpath.computeBounds(bounds, false); // fills rect with bounds
        PointF center = new PointF((bounds.left + bounds.right) / 2,
                (bounds.top + bounds.bottom) / 2);
        Log.e("path center", "cordinates: =====>" + (center.x - ((bounds.right - bounds.left) / 2)) + "//" + (center.y - ((bounds.bottom - bounds.top) / 2)));

        Log.e("path center", "cordinates: =====>" + (bounds.right - bounds.left) + "//" + (bounds.bottom - bounds.top));

//        Share.path=curSectorpath;
//        Intent intent = new Intent(getContext(), EditActivity.class);
//        intent.putExtra("height", String.valueOf(bounds.bottom - bounds.top));
//        intent.putExtra("width", String.valueOf(bounds.right - bounds.left));
//        getContext().startActivity(intent);

        return true;
    }

    @Override
    public int getCurrentColor() {
        onPush = true;
        return getFirsSectorColor();
    }

    void pushColor(int c) {
        if (onPush) {
            for (int i = getSizeSectors() - 1; i > 0; i--) {
                setSectorColor(i, getColorFromSector(i - 1));
            }
            onPush = false;
        }
        setFirstColorSector(c);
        updatePicture();
    }
}
