package com.mobile.cover.photo.editor.back.maker.customView.StickerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.MotionEventCompat;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Sticker view
 * Created by snowbean on 16-8-2.
 */

public class back_StickerView extends FrameLayout {
    private static final String TAG = "StickerView";
    public static List<Sticker> mStickers = new ArrayList<>();
    public static List<Sticker> tshirtStickers = new ArrayList<>();
    private Paint mBorderPaint;
    private RectF mStickerRect;
    private Matrix mSizeMatrix;
    private Matrix mDownMatrix;
    private Matrix mMoveMatrix;
    private BitmapStickerIcon mDeleteIcon;
    // private BitmapStickerIcon mFlipIcon;
    private BitmapStickerIcon mZoomIcon;
    //the first point down position
    private float mDownX;
    private float mDownY;
    private float mOldDistance = 0f;
    private float mOldRotation = 0f;
    private PointF mMidPoint;
    private ActionMode mCurrentMode = ActionMode.NONE;
    private Sticker mHandlingSticker;
    private boolean mLocked;
    private int mTouchSlop = 3;
    private Context context;
    private OnStickerOperationListener mOnStickerOperationListener;

    public back_StickerView(Context context) {
        this(context, null);
        this.context = context;
    }

    public back_StickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public back_StickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.BLACK);
        mBorderPaint.setAlpha(128);

        mSizeMatrix = new Matrix();
        mDownMatrix = new Matrix();
        mMoveMatrix = new Matrix();

        mStickerRect = new RectF();

        mDeleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_cancelnew));
        mZoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_resizenew));
        // mFlipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_flip_white_18dp));
    }

    public static boolean isInStickerArea(Sticker sticker, float downX, float downY) {
        return sticker.contains(downX, downY);
    }

    public boolean isStickerNull() {
        return mStickers.size() == 0;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mStickerRect.left = left;
            mStickerRect.top = top;
            mStickerRect.right = right;
            mStickerRect.bottom = bottom;
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);


        if (canvas != null) {

            drawStickers(canvas);
        }
    }

    private void drawStickers(Canvas canvas) {

        for (int i = 0; i < mStickers.size(); i++) {
            Sticker sticker = mStickers.get(i);

            if (sticker != null) {

                if (sticker.getTag().equalsIgnoreCase("maindraw")) {
                    float[] bitmapPoints = getStickerPoints(sticker);

                    float x1 = bitmapPoints[0];
                    float y1 = bitmapPoints[1];
                    float x2 = bitmapPoints[2];
                    float y2 = bitmapPoints[3];
                    float x3 = bitmapPoints[4];
                    float y3 = bitmapPoints[5];
                    float x4 = bitmapPoints[6];
                    float y4 = bitmapPoints[7];
                    Paint mBorderPaint = new Paint();
                    mBorderPaint.setAntiAlias(true);
                    mBorderPaint.setColor(Color.TRANSPARENT);
                    mBorderPaint.setAlpha(128);
                    mBorderPaint.setStrokeWidth(0.2F);
                    canvas.drawLine(x1, y1, x2, y2, mBorderPaint);
                    canvas.drawLine(x1, y1, x3, y3, mBorderPaint);
                    canvas.drawLine(x2, y2, x4, y4, mBorderPaint);
                    canvas.drawLine(x4, y4, x3, y3, mBorderPaint);
                }


                sticker.draw(canvas);
            }
        }

        if (mHandlingSticker != null && !mLocked) {

            if (!mHandlingSticker.getTag().equalsIgnoreCase("main")) {
                float[] bitmapPoints = getStickerPoints(mHandlingSticker);

                float x1 = bitmapPoints[0];
                float y1 = bitmapPoints[1];
                float x2 = bitmapPoints[2];
                float y2 = bitmapPoints[3];
                float x3 = bitmapPoints[4];
                float y3 = bitmapPoints[5];
                float x4 = bitmapPoints[6];
                float y4 = bitmapPoints[7];

                canvas.drawLine(x1, y1, x2, y2, mBorderPaint);
                canvas.drawLine(x1, y1, x3, y3, mBorderPaint);
                canvas.drawLine(x2, y2, x4, y4, mBorderPaint);
                canvas.drawLine(x4, y4, x3, y3, mBorderPaint);

                float rotation = calculateRotation(x3, y3, x4, y4);
                //draw delete icon
                configIconMatrix(mDeleteIcon, x1, y1, rotation);
                mDeleteIcon.draw(canvas);

                //draw zoom icon
                configIconMatrix(mZoomIcon, x4, y4, rotation);
                mZoomIcon.draw(canvas);
            }

        }

        //draw flip icon
//            configIconMatrix(mFlipIcon, x2, y2, rotation);
//            mFlipIcon.draw(canvas, mBorderPaint);}
    }


    private void configIconMatrix(BitmapStickerIcon icon, float x, float y, float rotation) {
        icon.setX(x);
        icon.setY(y);
        icon.getMatrix().reset();

        icon.getMatrix().postRotate(
                rotation, icon.getWidth() / 2, icon.getHeight() / 2);
        icon.getMatrix().postTranslate(
                x - icon.getWidth() / 2, y - icon.getHeight() / 2);
    }

    //
    public void setControlItemsHidden() {
        mHandlingSticker = null;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLocked) return super.onTouchEvent(event);

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e("StickerView", "ACTION_DOWN");

                if (isLocked()) {
                    setLocked(false);
                }

                mCurrentMode = ActionMode.DRAG;
                mDownX = event.getX();
                mDownY = event.getY();

                if (checkIconTouched(mDeleteIcon)) {
                    mCurrentMode = ActionMode.DELETE;
                } /*else if (checkIconTouched(mFlipIcon)) {
                    mCurrentMode = ActionMode.FLIP_HORIZONTAL;
                }*/ else if (checkIconTouched(mZoomIcon) && mHandlingSticker != null) {
                    mCurrentMode = ActionMode.ZOOM_WITH_ICON;
                    mMidPoint = calculateMidPoint();
                    mOldDistance = calculateDistance(mMidPoint.x, mMidPoint.y, mDownX, mDownY);
                    mOldRotation = calculateRotation(mMidPoint.x, mMidPoint.y, mDownX, mDownY);
                } else {
                    mHandlingSticker = findHandlingSticker();
                }

                if (mHandlingSticker != null) {
                    mDownMatrix.set(mHandlingSticker.getMatrix());

                    /*Log.e("Sticker getTag", mHandlingSticker.getTag());
                    if (mHandlingSticker.getTag().toString().equalsIgnoreCase("frame")) {
                        Share.IsSelectFrame = true;
                    } else {
                        Share.IsSelectFrame = false;
                    }*/
                }
                invalidate();
                break;


            case MotionEvent.ACTION_POINTER_DOWN:
                Log.e("StickerView", "ACTION_POINTER_DOWN");

                if (isLocked()) {
                    setLocked(false);
                }

                mOldDistance = calculateDistance(event);
                mOldRotation = calculateRotation(event);

                mMidPoint = calculateMidPoint(event);

                if (mHandlingSticker != null &&
                        isInStickerArea(mHandlingSticker, event.getX(1), event.getY(1)) &&
                        !checkIconTouched(mDeleteIcon))

                    mCurrentMode = ActionMode.ZOOM_WITH_TWO_FINGER;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.e("StickerView", "ACTION_MOVE");

                if (isLocked()) {
                    setLocked(false);
                }

                handleCurrentMode(event);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                Log.e("StickerView", "ACTION_UP");

                if (mCurrentMode == ActionMode.DELETE && mHandlingSticker != null) {
                    if (mOnStickerOperationListener != null) {
                        mStickers.remove(mHandlingSticker);
                        mOnStickerOperationListener.onStickerDeleted(mHandlingSticker);
                    }


                    mHandlingSticker.release();
                    mHandlingSticker = null;
                    invalidate();
                }

                if (mCurrentMode == ActionMode.FLIP_HORIZONTAL && mHandlingSticker != null) {
                    mHandlingSticker.getMatrix().preScale(-1, 1,
                            mHandlingSticker.getCenterPoint().x, mHandlingSticker.getCenterPoint().y);

                    mHandlingSticker.setFlipped(!mHandlingSticker.isFlipped());
                    if (mOnStickerOperationListener != null) {
                        mOnStickerOperationListener.onStickerFlipped(mHandlingSticker);
                    }
                    invalidate();
                }

                if ((mCurrentMode == ActionMode.ZOOM_WITH_ICON || mCurrentMode == ActionMode.ZOOM_WITH_TWO_FINGER) && mHandlingSticker != null) {
                    if (mOnStickerOperationListener != null) {
                        mOnStickerOperationListener.onStickerZoomFinished(mHandlingSticker);
                    }
                }

                if (mCurrentMode == ActionMode.DRAG
                        && Math.abs(event.getX() - mDownX) < mTouchSlop
                        && Math.abs(event.getY() - mDownY) < mTouchSlop
                        && mHandlingSticker != null) {
                    mCurrentMode = ActionMode.CLICK;
                    if (mOnStickerOperationListener != null) {
                        mOnStickerOperationListener.onStickerClicked(mHandlingSticker);
                    }
                }

                if (mCurrentMode == ActionMode.DRAG && mHandlingSticker != null) {
                    if (mOnStickerOperationListener != null) {
                        mOnStickerOperationListener.onStickerDragFinished(mHandlingSticker);
                    }
                }

                mCurrentMode = ActionMode.NONE;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                Log.e("StickerView", "ACTION_POINTER_UP");

                if (mCurrentMode == ActionMode.ZOOM_WITH_TWO_FINGER && mHandlingSticker != null) {
                    if (mOnStickerOperationListener != null) {
                        mOnStickerOperationListener.onStickerDragFinished(mHandlingSticker);
                    }
                }
                mCurrentMode = ActionMode.NONE;
                break;

        }//end of switch(action)

        return true;
    }

    public void setStickers(float x, float y) {
        mMoveMatrix.set(mDownMatrix);
        mMoveMatrix.postTranslate(x - mDownX, y - mDownY);
        mHandlingSticker.getMatrix().set(mMoveMatrix);
    }

    private void handleCurrentMode(MotionEvent event) {


        switch (mCurrentMode) {
            case NONE:
                break;
            case DRAG:
//                Log.e("TAG","DRAG Stickers");
                if (mHandlingSticker != null) {
                    mMoveMatrix.set(mDownMatrix);
                    mMoveMatrix.postTranslate(event.getX() - mDownX, event.getY() - mDownY);
                    mHandlingSticker.getMatrix().set(mMoveMatrix);
                }
                break;
            case ZOOM_WITH_TWO_FINGER:
                if (mHandlingSticker != null) {
                    float newDistance = calculateDistance(event);
                    float newRotation = calculateRotation(event);

                    Log.e("newDistance", "newDistance==>" + newDistance);

                    mMoveMatrix.set(mDownMatrix);
                    mMoveMatrix.postScale(
                            newDistance / mOldDistance, newDistance / mOldDistance, mMidPoint.x, mMidPoint.y);
                    mMoveMatrix.postRotate(newRotation - mOldRotation, mMidPoint.x, mMidPoint.y);
                    mHandlingSticker.getMatrix().set(mMoveMatrix);
                }

                break;

            case ZOOM_WITH_ICON:
                try {
                    if (mHandlingSticker != null) {
                        float newDistance = calculateDistance(mMidPoint.x, mMidPoint.y, event.getX(), event.getY());
                        float newRotation = calculateRotation(mMidPoint.x, mMidPoint.y, event.getX(), event.getY());

                        mMoveMatrix.set(mDownMatrix);
                        mMoveMatrix.postScale(
                                newDistance / mOldDistance, newDistance / mOldDistance, mMidPoint.x, mMidPoint.y);
                        mMoveMatrix.postRotate(newRotation - mOldRotation, mMidPoint.x, mMidPoint.y);
                        mHandlingSticker.getMatrix().set(mMoveMatrix);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }// end of switch(mCurrentMode)
    }

    private boolean checkIconTouched(BitmapStickerIcon icon) {
        float x = icon.getX() - mDownX;
        float y = icon.getY() - mDownY;
        float distance_pow_2 = x * x + y * y;
        return distance_pow_2 <= Math.pow(icon.getIconRadius() + icon.getIconRadius(), 2);
    }

    /**
     * find the touched Sticker
     **/
    private Sticker findHandlingSticker() {
        Log.e("TAG Module", "findHandlingSticker");
        for (int i = mStickers.size() - 1; i >= 0; i--) {

            if (isInStickerArea(mStickers.get(i), mDownX, mDownY)) {

                setLocked(false);
                return mStickers.get(i);

            } else {
                Log.e("findHandlingSticker", "findHandlingSticker else");
                if (!isLocked()) {
                    setLocked(true);
                }
            }
        }
        return null;
    }

    private Sticker findHandlingSticker2() {
        Log.e("TAG Module", "findHandlingSticker");
        for (int i = mStickers.size() - 1; i >= 0; i--) {
            Log.e("TAG Module", "findHandlingSticker if");
            return mStickers.get(i);
        }
        return null;
    }

    private PointF calculateMidPoint(MotionEvent event) {
        if (event == null || event.getPointerCount() < 2) return new PointF();
        float x = (event.getX(0) + event.getX(1)) / 2;
        float y = (event.getY(0) + event.getY(1)) / 2;
        return new PointF(x, y);
    }

    private PointF calculateMidPoint() {
        if (mHandlingSticker == null) return new PointF();
        return mHandlingSticker.getMappedCenterPoint();
    }

    /**
     * calculate rotation in line with two fingers and x-axis
     **/
    private float calculateRotation(MotionEvent event) {
        if (event == null || event.getPointerCount() < 2) return 0f;
        double x = event.getX(0) - event.getX(1);
        double y = event.getY(0) - event.getY(1);
        double radians = Math.atan2(y, x);
        return (float) Math.toDegrees(radians);
    }

    private float calculateRotation(float x1, float y1, float x2, float y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        double radians = Math.atan2(y, x);
        return (float) Math.toDegrees(radians);
    }

    /**
     * calculate Distance in two fingers
     **/
    private float calculateDistance(MotionEvent event) {
        if (event == null || event.getPointerCount() < 2) return 0f;
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);

        return (float) Math.sqrt(x * x + y * y);
    }

    private float calculateDistance(float x1, float y1, float x2, float y2) {
        double x = x1 - x2;
        double y = y1 - y2;

        return (float) Math.sqrt(x * x);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        for (int i = 0; i < mStickers.size(); i++) {
            Sticker sticker = mStickers.get(i);
            if (sticker != null) {
                transformSticker(sticker);
            }
        }

    }

    /**
     * Sticker's drawable will be too bigger or smaller
     * This method is to transform it to fit
     * step 1：let the center of the sticker image is coincident with the center of the View.
     * step 2：Calculate the zoom and zoom
     **/
    private void transformSticker(Sticker sticker) {
        if (sticker == null) {
            Log.e(TAG, "transformSticker: the bitmapSticker is null or the bitmapSticker bitmap is null");
            return;
        }


        if (mSizeMatrix != null) {
            mSizeMatrix.reset();
        }

        //step 1
        float offsetX = (getWidth() - sticker.getWidth()) / 2;
        float offsetY = (getHeight() - sticker.getHeight()) / 2;

        mSizeMatrix.postTranslate(offsetX, offsetY);

        //step 2
        float scaleFactor;
        if (getWidth() < getHeight()) {
            scaleFactor = (float) getWidth() / sticker.getWidth();
        } else {
            scaleFactor = (float) getHeight() / sticker.getHeight();
        }

        mSizeMatrix.postScale(scaleFactor / 2, scaleFactor / 2,
                getWidth() / 2, getHeight() / 2);

        sticker.getMatrix().reset();
        sticker.getMatrix().set(mSizeMatrix);

        invalidate();
    }

    public void replace(Sticker sticker) {
        replace(sticker, true);
    }

    public void replace(Sticker sticker, boolean needStayState) {
        if (mHandlingSticker != null && sticker != null) {
            if (needStayState) {
                sticker.getMatrix().set(mHandlingSticker.getMatrix());
                sticker.setFlipped(mHandlingSticker.isFlipped());
            } else {
                mHandlingSticker.getMatrix().reset();
                // reset scale, angle, and put it in center
                float offsetX = (getWidth() - mHandlingSticker.getWidth()) / 2;
                float offsetY = (getHeight() - mHandlingSticker.getHeight()) / 2;
                sticker.getMatrix().postTranslate(offsetX, offsetY);

                float scaleFactor;
                if (getWidth() < getHeight()) {
                    scaleFactor = (float) getWidth() / mHandlingSticker.getDrawable().getIntrinsicWidth();
                } else {
                    scaleFactor = (float) getHeight() / mHandlingSticker.getDrawable().getIntrinsicHeight();
                }
                sticker.getMatrix().postScale(scaleFactor / 6, scaleFactor / 6, getWidth() / 2, getHeight() / 2);
            }
            int index = mStickers.indexOf(mHandlingSticker);
            mStickers.set(index, sticker);
            mHandlingSticker = sticker;
        }
        invalidate();
    }

    public void addSticker(Sticker sticker) {

        if (sticker == null) {
            Log.e(TAG, "Sticker to be added is null!");
            return;
        }
        float offsetX = (getWidth() - sticker.getWidth()) / 2;
        float offsetY = (getHeight() - sticker.getHeight()) / 2;
        sticker.getMatrix().postTranslate(offsetX, offsetY);

        float scaleFactor;
        if (getWidth() < getHeight()) {
            scaleFactor = (float) getWidth() / sticker.getDrawable().getIntrinsicWidth();
        } else {
            scaleFactor = (float) getHeight() / sticker.getDrawable().getIntrinsicHeight();
        }

        sticker.getMatrix().postScale(scaleFactor / 2, scaleFactor / 2, getWidth() / 2, getHeight() / 2);

        Log.e("sticker", "scaleFactor==>" + scaleFactor);
        Log.e("sticker", "getWidth()==>" + getWidth());
        Log.e("sticker", "getHeight()()==>" + getHeight());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicWidth()==>" + sticker.getDrawable().getIntrinsicWidth());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicHeight()==>" + sticker.getDrawable().getIntrinsicHeight());
        Log.e("sticker", " sticker.getMatrix()==>" + sticker.getMatrix());
        Log.e("STICKER_SIZE", "addSticker: " + mStickers.size());
        mMidPoint = calculateMidPoint();
        mHandlingSticker = sticker;
        mStickers.add(sticker);

        if (mOnStickerOperationListener != null) {
            mOnStickerOperationListener.onStickerAdd(mHandlingSticker);
        }

        invalidate();


    }

    public void updateSticker(Sticker sticker) {
        if (sticker == null) {
            Log.e(TAG, "Sticker to be added is null!");
            return;
        }
        float offsetX = (getWidth() - sticker.getWidth()) / 2;
        float offsetY = (getHeight() - sticker.getHeight()) / 2;
        sticker.getMatrix().postTranslate(offsetX, offsetY);

        float scaleFactor;
        if (getWidth() < getHeight()) {
            scaleFactor = (float) getWidth() / sticker.getDrawable().getIntrinsicWidth();
        } else {
            scaleFactor = (float) getHeight() / sticker.getDrawable().getIntrinsicHeight();
        }

        sticker.getMatrix().postScale(scaleFactor / 4, scaleFactor / 4, getWidth() / 4, getHeight() / 4);

        Log.e("sticker", "scaleFactor==>" + scaleFactor);
        Log.e("sticker", "getWidth()==>" + getWidth());
        Log.e("sticker", "getHeight()()==>" + getHeight());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicWidth()==>" + sticker.getDrawable().getIntrinsicWidth());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicHeight()==>" + sticker.getDrawable().getIntrinsicHeight());
        Log.e("sticker", " sticker.getMatrix()==>" + sticker.getMatrix());

    }

    public void addStickerMain(Sticker sticker) {
        if (sticker == null) {
            Log.e(TAG, "Sticker to be added is null!");
            return;
        }
        float offsetX = (getWidth() - sticker.getWidth()) / 2;
        float offsetY = (getHeight() - sticker.getHeight()) / 2;
        sticker.getMatrix().postTranslate(offsetX, offsetY);

        float scaleFactor;
        if (getWidth() < getHeight()) {
            scaleFactor = (float) getWidth() / sticker.getDrawable().getIntrinsicWidth();
        } else {
            scaleFactor = (float) getHeight() / sticker.getDrawable().getIntrinsicHeight();
        }


        // scaleFactor= (float) (scaleFactor*1.3);
        Log.e(TAG, "addStickerMain: " + Share.maskheight + "======>" + Share.maskwidth);
        int width, height;
        if (sticker.getWidth() > sticker.getHeight()) {
            width = (int) 1.5;
            height = (int) 1.75;
        } else {
            width = (int) 2.45;
            height = (int) 2.9;
        }
        sticker.getMatrix().postScale(width, height, getWidth() / 2, getHeight() / 2);

        Log.e("sticker", "scaleFactor==>" + scaleFactor);
        Log.e("sticker", "getWidth()==>" + getWidth());
        Log.e("sticker", "getHeight()()==>" + getHeight());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicWidth()==>" + sticker.getDrawable().getIntrinsicWidth());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicHeight()==>" + sticker.getDrawable().getIntrinsicHeight());
        Log.e("sticker", " sticker.getMatrix()==>" + sticker.getMatrix());
        mMidPoint = calculateMidPoint();
        mHandlingSticker = sticker;
        mStickers.add(sticker);
        if (mOnStickerOperationListener != null) {
            mOnStickerOperationListener.onStickerAdd(mHandlingSticker);
        }


        invalidate();

    }

    public void addStickerMain_bg(Sticker sticker, int pos) {
        if (sticker == null) {
            Log.e(TAG, "Sticker to be added is null!");
            return;
        }
        float offsetX = (getWidth() - sticker.getWidth()) / 2;
        float offsetY = (getHeight() - sticker.getHeight()) / 2;
        sticker.getMatrix().postTranslate(offsetX, offsetY);

        float scaleFactor;
        if (getWidth() < getHeight()) {
            scaleFactor = (float) getWidth() / sticker.getDrawable().getIntrinsicWidth();
        } else {
            scaleFactor = (float) getHeight() / sticker.getDrawable().getIntrinsicHeight();
        }


        // scaleFactor= (float) (scaleFactor*1.3);
        Log.e(TAG, "addStickerMain: " + Share.maskheight + "======>" + Share.maskwidth);
        int width, height;
        if (sticker.getWidth() > sticker.getHeight()) {
            width = (int) 1.5;
            height = (int) 1.75;
        } else {
            width = (int) 2.45;
            height = (int) 2.9;
        }
        sticker.getMatrix().postScale(width, height, getWidth() / 2, getHeight() / 2);

        Log.e("sticker", "scaleFactor==>" + scaleFactor);
        Log.e("sticker", "getWidth()==>" + getWidth());
        Log.e("sticker", "getHeight()()==>" + getHeight());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicWidth()==>" + sticker.getDrawable().getIntrinsicWidth());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicHeight()==>" + sticker.getDrawable().getIntrinsicHeight());
        Log.e("sticker", " sticker.getMatrix()==>" + sticker.getMatrix());
        mMidPoint = calculateMidPoint();
        mHandlingSticker = sticker;
        mStickers.add(pos, sticker);
        if (mOnStickerOperationListener != null) {
            mOnStickerOperationListener.onStickerAdd(mHandlingSticker);
        }


        invalidate();

    }

    public void addStickerMain_mug(Sticker sticker) {
        if (sticker == null) {
            Log.e(TAG, "Sticker to be added is null!");
            return;
        }
        float offsetX = (getWidth() - sticker.getWidth()) / 2;
        float offsetY = (getHeight() - sticker.getHeight()) / 2;
        sticker.getMatrix().postTranslate(offsetX, offsetY);

        float scaleFactor;
        if (getWidth() < getHeight()) {
            scaleFactor = (float) getWidth() / sticker.getDrawable().getIntrinsicWidth();
        } else {
            scaleFactor = (float) getHeight() / sticker.getDrawable().getIntrinsicHeight();
        }


        // scaleFactor= (float) (scaleFactor*1.3);
//        sticker.getMatrix().postScale(Share.maskheight*3/2, Share.maskheight*3/2, getWidth()/2 , getHeight() /2);

        Log.e("sticker", "scaleFactor==>" + scaleFactor);
        Log.e("sticker", "getWidth()==>" + getWidth());
        Log.e("sticker", "getHeight()()==>" + getHeight());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicWidth()==>" + sticker.getDrawable().getIntrinsicWidth());
        Log.e("sticker", " sticker.getDrawable().getIntrinsicHeight()==>" + sticker.getDrawable().getIntrinsicHeight());
        Log.e("sticker", " sticker.getMatrix()==>" + sticker.getMatrix());
        mMidPoint = calculateMidPoint();
        mHandlingSticker = sticker;
        mStickers.add(sticker);
        if (mOnStickerOperationListener != null) {
            mOnStickerOperationListener.onStickerAdd(mHandlingSticker);
        }


        invalidate();

    }

    public float[] getStickerPoints(Sticker sticker) {
        if (sticker == null) return new float[8];
        return sticker.getMappedBoundPoints();
    }

    public void save(File file) {
        StickerUtils.saveImageToGallery(file, createBitmap());
        StickerUtils.notifySystemGallery(getContext(), file);
    }

    public Bitmap createBitmap() {

        mHandlingSticker = null;
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);
        return bitmap;
    }

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
        Canvas canvas = new Canvas(rotatedBitmap);
        canvas.drawBitmap(original, 5.0f, 0.0f, null);

        return rotatedBitmap;
    }

    public Bitmap createBitmapCanvas(String width, String height) {

        mHandlingSticker = null;
        Bitmap bitmap = Bitmap.createBitmap(Integer.parseInt(width), Integer.parseInt(height), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        for (int i = 0; i < mStickers.size(); i++) {
            Sticker sticker = mStickers.get(i);

            if (sticker != null) {

                if (sticker.getTag().equalsIgnoreCase("maindraw")) {
                    float[] bitmapPoints = getStickerPoints(sticker);

                    float x1 = bitmapPoints[0];
                    float y1 = bitmapPoints[1];
                    float x2 = bitmapPoints[2];
                    float y2 = bitmapPoints[3];
                    float x3 = bitmapPoints[4];
                    float y3 = bitmapPoints[5];
                    float x4 = bitmapPoints[6];
                    float y4 = bitmapPoints[7];
                    Paint mBorderPaint = new Paint();
                    mBorderPaint.setAntiAlias(true);
                    mBorderPaint.setColor(Color.TRANSPARENT);
                    mBorderPaint.setAlpha(128);
                    mBorderPaint.setStrokeWidth(0.2F);
                    canvas.drawLine(x1, y1, x2, y2, mBorderPaint);
                    canvas.drawLine(x1, y1, x3, y3, mBorderPaint);
                    canvas.drawLine(x2, y2, x4, y4, mBorderPaint);
                    canvas.drawLine(x4, y4, x3, y3, mBorderPaint);
                }

                float offsetX = (float) ((Integer.parseInt(width)) / 3);
                float offsetY = (float) ((Integer.parseInt(height)) / 3);
                sticker.getMatrix().postTranslate(offsetX, offsetY);
                sticker.draw(canvas);
            }
        }


        //  this.draw(canvas);
        return bitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint());

        return resizedBitmap;
    }

    public Bitmap createBitmap(String width, String height) {
        // getLayoutParams().width=Integer.parseInt(width);
        //  getLayoutParams().height=Integer.parseInt(height);
        mHandlingSticker = null;
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        Log.e("width", "s==>" + getWidth());
        Log.e("height", "s==>" + getHeight());
        // Bitmap bitmap = Bitmap.createBitmap(Integer.parseInt(width), Integer.parseInt(height), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        this.draw(canvas);

        // Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, 13, bitmap.getWidth(), bitmap.getHeight() - 26);


        return bitmap;
        // return getResizedBitmap(bitmap,Integer.parseInt(width),Integer.parseInt(height));
    }

    public boolean removeCurrentSticker() {
        return remove(mHandlingSticker);
    }

    public boolean remove(@Nullable Sticker sticker) {
        if (mStickers.contains(sticker)) {
            mStickers.remove(sticker);
            if (mOnStickerOperationListener != null) {
                mOnStickerOperationListener.onStickerDeleted(sticker);
            }
            if (mHandlingSticker == sticker) {
                mHandlingSticker = null;
            }
            invalidate();

            return true;
        } else {
            Log.d(TAG, "remove: the sticker is not in this StickerView");

            return false;
        }
    }

    public void removeAllStickers() {
        mStickers.clear();
        if (mHandlingSticker != null) {
            mHandlingSticker.release();
            mHandlingSticker = null;
        }
        invalidate();
    }

    public boolean isLocked() {
        return mLocked;
    }

    public void setLocked(boolean locked) {
        mLocked = false;
        invalidate();
    }

    public BitmapStickerIcon getZoomIcon() {
        return mZoomIcon;
    }
/*

    public BitmapStickerIcon getFlipIcon() {
        return mFlipIcon;
    }

    public void setFlipIcon(BitmapStickerIcon flipIcon) {
        mFlipIcon = flipIcon;
        postInvalidate();
    }
*/

    public void setZoomIcon(BitmapStickerIcon zoomIcon) {
        mZoomIcon = zoomIcon;
        postInvalidate();
    }

    public BitmapStickerIcon getDeleteIcon() {
        return mDeleteIcon;
    }

    public void setDeleteIcon(BitmapStickerIcon deleteIcon) {
        mDeleteIcon = deleteIcon;
        postInvalidate();
    }

    public void setOnStickerOperationListener(OnStickerOperationListener onStickerOperationListener) {
        mOnStickerOperationListener = onStickerOperationListener;
    }

    private enum ActionMode {
        NONE,   //nothing
        DRAG,   //drag the sticker with your finger
        ZOOM_WITH_TWO_FINGER,   //zoom in or zoom out the sticker and rotate the sticker with two finger
        ZOOM_WITH_ICON,    //zoom in or zoom out the sticker and rotate the sticker with icon
        DELETE,  //delete the handling sticker
        FLIP_HORIZONTAL, //horizontal flip the sticker
        CLICK    //Click the Sticker
    }

    public interface OnStickerOperationListener {
        void onStickerClicked(Sticker sticker);

        void onStickerDeleted(Sticker sticker);

        void onStickerDragFinished(Sticker sticker);

        void onStickerZoomFinished(Sticker sticker);

        void onStickerAdd(Sticker sticker);

        void onStickerNull();

        void onStickerFlipped(Sticker sticker);
    }
}
