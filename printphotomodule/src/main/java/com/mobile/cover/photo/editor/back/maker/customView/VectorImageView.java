package com.mobile.cover.photo.editor.back.maker.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.customView.sharp.OnSvgElementListener;
import com.mobile.cover.photo.editor.back.maker.customView.sharp.Sharp;
import com.mobile.cover.photo.editor.back.maker.customView.sharp.SharpDrawable;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Rius on 29.03.17.
 * VectorImageView class
 */
public abstract class VectorImageView extends AppCompatImageView implements OnSvgElementListener {

    public static ArrayList<Integer> sectorsColors = new ArrayList<>();
    public static ArrayList<Path> sectorsPaths = new ArrayList<>();
    private Context context;
    private LinearLayout ll_main_progress, ll_main_click;
    //    int[] frame_2 = {1, 2, 3, R.drawable.shape1, R.drawable.shape2, R.drawable.shape3, R.drawable.shape4, R.drawable.shape5, R.drawable.shape6};
    private PictureDrawable sharpDrawable;
    private ProgressBar progress;
    private VectorImageView vectorImageView;
    private PhilImageView centerImageView;
    private OnImageCommandsListener onImageCommandsListener;
    private OnImageCallbackListener onImageCallbackListener;
    private ImageView iv_save, iv_undo, iv_redo, iv_reset;
    private Bitmap bitmapMap;
    private int actW;
    private int actH;
    private ArrayList<Boolean> sectorsFlags = new ArrayList<>();
    private ArrayList<String> sectorsTags = new ArrayList<>();
    private ArrayList<Integer> bckgSectorsColors = new ArrayList<>();
    private ArrayList<Path> bckgSectorsPaths = new ArrayList<>();

    private ArrayList<Float> brushSectors = new ArrayList<>();

//    private SectorsDAO sd;
//    SharedPrefs spref;

    private boolean isEmptyDB = false;
    private int sectorId = 0, k = 0;

    public VectorImageView(Context context) {
        super(context);
        vectorImageView = this;
        vectorImageView.context = context;
    }

    public VectorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        vectorImageView = this;
        vectorImageView.context = context;
    }

    public VectorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        vectorImageView = this;
        vectorImageView.context = context;
    }

    public void loadAsset(String string) {

//        sectorsFlags =
        sectorsPaths = new ArrayList<>();
//        bckgSectorsPaths = new ArrayList<>();
//        bckgSectorsColors = new ArrayList<>();
//        brushSectors = new ArrayList<>();
//        spref = new SharedPrefs();
//
        try {
            Sharp mSharp = Sharp.loadAsset(context.getAssets(), string);
            mSharp.setOnElementListener(vectorImageView);

            mSharp.getDrawable(vectorImageView, new Sharp.DrawableCallback() {
                @Override
                public void onDrawableReady(SharpDrawable sd) {
                    sharpDrawable = sd;
                    vectorImageView.setImageDrawable(sharpDrawable);

                    if (onImageCallbackListener != null)
                        onImageCallbackListener.imageCallback();

                    createMap();
                    updatePicture();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void load_urlAsset(InputStream string, Bitmap bitmap) {

        this.iv_save = iv_save;
        this.iv_redo = iv_redo;
        this.iv_undo = iv_undo;
        this.iv_reset = iv_reset;
        this.ll_main_click = ll_main_click;
        this.ll_main_progress = ll_main_progress;
        this.centerImageView = centerImageView;
        this.progress = progress;

        sectorsFlags = new ArrayList();
        sectorsPaths = new ArrayList();
        sectorsTags = new ArrayList<>();
        bckgSectorsPaths = new ArrayList();
        bckgSectorsColors = new ArrayList();
        brushSectors = new ArrayList();

        try {
            Log.e("---------------", "-----" + Sharp.loadInputStream(string));
            Sharp mSharp = Sharp.loadInputStream(string);
            Log.e("---------------", "-----" + mSharp);
            mSharp.setOnElementListener(vectorImageView);
            // mSharp.getDrawable(vectorImageView);
            mSharp.getDrawable(vectorImageView, new Sharp.DrawableCallback() {
                @Override
                public void onDrawableReady(SharpDrawable sd) {
                    sharpDrawable = sd;
                    vectorImageView.setImageDrawable(sharpDrawable);

                    if (onImageCallbackListener != null)
                        onImageCallbackListener.imageCallback();

                    createMap();
                    updatePicture();
                }
            });
//            vectorImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSvgStart(@NonNull Canvas canvas, @Nullable RectF bounds) {
        sectorId = 0;
        k = 0;
        sectorsColors.clear();
        sectorsTags.clear();
        //sectorsColors = sectorsDAO.getSectors();
       /* if (!sectorsColors.isEmpty())
            sectorsColors.clear();*/
//        if (sd == null)
//            sd = new SectorsDAO(context, DataBaseHelper.SECTORS.SECTORS_PHIL);
    }

    @Override
    public void onSvgEnd(@NonNull Canvas canvas, @Nullable RectF bounds) {

    }

    @Override
    public <T> T onSvgElement(@Nullable String id, @NonNull T element, @Nullable RectF
            elementBounds, @NonNull Canvas canvas, @Nullable RectF canvasBounds, @Nullable Paint paint) {

        if (paint != null && (element instanceof Path)) {


            int color = 0;
            Path p = (Path) element;
            Log.e("path", "path" + id);
            sectorsFlags.add(true);
            sectorsPaths.add((Path) element);
            sectorsTags.add(id);

            if (onImageCommandsListener == null) {
                float elB = elementBounds != null ? elementBounds.left : -1;
                float canB = canvasBounds != null ? canvasBounds.width() : -1;
                brushSectors.add(elB / canB);
            }

            k++;
            Log.e("index", "index" + k);


            Log.e("COLORSSS", "onSvgElement: ====>" + paint.getColor());
            sectorsColors.add(paint.getColor());
//            paint.setColor(color);
        }
        return null;
    }

    @Override
    public <T> void onSvgElementDrawn(@Nullable String id, @NonNull T element, @NonNull Canvas
            canvas, @Nullable Paint paint) {
    }

    int getSector(float x, float y) {
        int lX = Math.round(x * actW);
        int lY = Math.round(y * actH);
        int curSector;
        if (lX >= 0 && lY < bitmapMap.getHeight() && lX < bitmapMap.getWidth() && lY >= 0) {
            curSector = ((bitmapMap.getPixel(lX, lY) << 16) >>> 16) - 1;
            return curSector;
        }
        curSector = 0xFFFFFFFF;
        return curSector;
    }

    Path getSectorpath(float x, float y) {
        int lX = Math.round(x * actW);
        int lY = Math.round(y * actH);
        int curSector;
        if (lX >= 0 && lY < bitmapMap.getHeight() && lX < bitmapMap.getWidth() && lY >= 0) {
            curSector = ((bitmapMap.getPixel(lX, lY) << 16) >>> 16) - 1;
            return sectorsPaths.get(curSector);
        }
        curSector = 0xFFFFFFFF;
        return sectorsPaths.get(curSector);
    }


    int getSector(final ImageView imageView, float x, float y) {

        float paddingEventX = x / imageView.getWidth();

        int sectorId = -1;
        for (float fl : brushSectors) {
            if (paddingEventX < fl) break;
            sectorId++;
        }

        return sectorId;
    }


    public void setSectorColor(int i, int c) {
        if (sectorsColors != null && c != sectorsColors.get(i)) {
            sectorsColors.set(i, c);
//            sd.update(i, c);
        }
    }

    int getColorFromSector(int i) {
        if (i == 0xFFFFFFFF) return sectorsColors.get(0);
        return sectorsColors.get(i);
    }

    int getSizeSectors() {
        return sectorsColors.size();
    }

    OnImageCommandsListener getOnImageCommandsListener() {
        return vectorImageView.onImageCommandsListener;
    }

    public void setOnImageCommandsListener(OnImageCommandsListener onImageCommandsListener) {
        vectorImageView.onImageCommandsListener = onImageCommandsListener;
    }

    public void setOnImageCallbackListener(OnImageCallbackListener onImageCallbackListener) {
        this.onImageCallbackListener = onImageCallbackListener;
    }

    private void createMap() {

        actW = sharpDrawable.getPicture().getWidth();

        if (onImageCallbackListener != null) {

            actH = sharpDrawable.getPicture().getHeight();

            Paint paint = new Paint();
            paint.setAntiAlias(false);

            Canvas canvas = sharpDrawable.getPicture().beginRecording(actW, actH);

            for (int i = 0; i < sectorsPaths.size(); i++) {
                paint.setColor(i + 1);
                paint.setAlpha(0xFF);

                Log.e("PAINT", "createMap: " + paint);
                canvas.drawPath(sectorsPaths.get(i), paint);
            }

            Bitmap bitmap = BitmapFactory.decodeResource(
                    getResources(),
                    R.drawable.frame_1
            );

            Drawable d = new BitmapDrawable(getResources(), bitmap);
            d.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

            // Define an offset value between canvas and bitmap
            //1035.05//1024.85
            int offset = 50;

            canvas.drawBitmap(
                    ((BitmapDrawable) d).getBitmap(), // Bitmap
                    offset, // Left
                    offset, // Top
                    null // Paint
            );

            sharpDrawable.getPicture().endRecording();
            Log.e("height", "height" + actH + "///" + actW);
            bitmapMap = Bitmap.createBitmap(actW, actH, Bitmap.Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(bitmapMap);
            bitmapMap.eraseColor(0x00000000);
            sharpDrawable.draw(bitmapCanvas);
        }
    }

    public abstract void initThis();

    public void updatePicture() {

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Canvas canvas = sharpDrawable.getPicture().beginRecording(
                sharpDrawable.getPicture().getWidth(),
                sharpDrawable.getPicture().getHeight()
        );

        int j = 0, k = 0, s = 0;
        for (int i = 0; i < sectorsPaths.size(); i++) {
            //if (sectorsFlags.get(i)) {
            if (sectorsColors.size() > j)
                paint.setColor(sectorsColors.get(j));
            s = j++;
            Log.e("iddddd", "id" + sectorsPaths.get(s).getFillType());

            canvas.drawPath(sectorsPaths.get(s), paint);
           /* } else {
                paint.setColor(bckgSectorsColors.get(k));
                s = k++;
                Log.e("iddddd","id"+bckgSectorsColors.get(s));
                canvas.drawPath(bckgSectorsPaths.get(s), paint);
            }*/

            if (sectorsTags.get(i) != null) {
                RectF bounds = new RectF();
                sectorsPaths.get(s).computeBounds(bounds, false); // fills rect with bounds
                PointF center = new PointF((bounds.left + bounds.right) / 2,
                        (bounds.top + bounds.bottom) / 2);
                Bitmap bitmap = null;
                Log.e("LOG", "updatePicture: " + i + "=========>" + Share.frame_bitmap.size());
                int n;
//                if (Share.frame_bitmap.size() == 0) {
//                    for (n = 0; n < frame_2.length; n++) {
//                        Log.e("LOG", "updatePicture: " + n);
//                        bitmap = BitmapFactory.decodeResource(
//                                getResources(),
//                                frame_2[n]
//                        );
//                    }
//                } else {
                for (int m = 0; m < Share.frame_bitmap.size(); m++) {
                    Log.e("VALUE", "updatePicture: " + sectorsTags.get(i) + "========>" + Share.frame_bitmap.get(m).getId());
                    if (Integer.valueOf(sectorsTags.get(i)) == Share.frame_bitmap.get(m).getId()) {
                        Log.e("EQUALS", "updatePicture: ===================>" + sectorsTags.get(i) + "========>" + Share.frame_bitmap.get(m).getId());
                        if (Share.frame_bitmap.get(m).getSave_mask_bitmap() != null) {
                            Log.e("NULL", "updatePicture: ====>False");
                            bitmap = Share.frame_bitmap.get(m).getSave_mask_bitmap();
                        } else {
                            Log.e("NULL", "updatePicture: ====>true");
//                            try {
//                                URL url=new URL(Share.frame_bitmap.get(m).getMask_bitmap());
//                                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                            } catch(IOException e) {
//                                Log.e("EXCEPTION", "updatePicture: "+e.getLocalizedMessage());
//                            }


                        }
                    }
                }
//                }

//                bitmap = Share.bitmap_frame;
                if (bitmap != null) {
                    Matrix matrix = new Matrix();
                    matrix.preScale(-1, 1);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                d.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) d).getBitmap(), (int) (bounds.right - bounds.left), (int) (bounds.bottom - bounds.top), false);
                    canvas.drawBitmap(
                            bitmap, // Bitmap
                            (center.x - ((bounds.right - bounds.left) / 2)), // Left
                            (center.y - ((bounds.bottom - bounds.top) / 2)), // Top
                            null // Paint
                    );
                }
            }

        }

        sharpDrawable.getPicture().endRecording();
        vectorImageView.invalidate();
    }

    interface OnImageCommandsListener {
        int getCurrentColor();
    }


    interface OnImageCallbackListener {
        void imageCallback();
    }
}