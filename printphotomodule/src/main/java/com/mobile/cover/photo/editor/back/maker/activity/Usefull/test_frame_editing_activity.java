package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import com.larvalabs.svgandroid.SVG;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pagination.MainActivity;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ModelListActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.customView.PhilImageView;
import com.mobile.cover.photo.editor.back.maker.model.Cart;
import com.mobile.cover.photo.editor.back.maker.model.frame_bitmap_model;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.Commen.Share.upload;


public class test_frame_editing_activity extends PrintPhotoBaseActivity {
    private static final long MIN_CLICK_INTERVAL = 1500;
    PhilImageView phill_image_view;
    RecyclerView rv_frame;
    int frame_number = 6;
    Drawable[] frame_2;
    test_frame_image_adapter test_frame_image_adapter;
    ImageView btn_add, id_back;
  //  ProgressDialog progress, progressDialog;
    InputStream inputStream;
    AlertDialog alertDialog;
    private long mLastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_frame_editing_activity);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        GlobalData.screenHeight = display.getHeight();
        GlobalData.screenWidth = display.getWidth();
        SharedPrefs.save(this, SharedPrefs.SCREEN_HEIGHT, GlobalData.screenHeight);
        SharedPrefs.save(this, SharedPrefs.SCREEN_WIDTH, GlobalData.screenWidth);
        getDisplaySize();

//        frame_2 = new Drawable[]{getResources().getDrawable(R.drawable.shape1), getResources().getDrawable(R.drawable.shape2), getResources().getDrawable(R.drawable.shape3), getResources().getDrawable(R.drawable.shape4), getResources().getDrawable(R.drawable.shape5), getResources().getDrawable(R.drawable.shape6)};
        Share.frame_bitmap.clear();
        for (int i = 0; i < Share.frame_alldetails.size(); i++) {
            frame_bitmap_model frame_bitmap_model = new frame_bitmap_model();
            frame_bitmap_model.setId(i);
            frame_bitmap_model.setMask_bitmap(Share.frame_alldetails.get(i).getMaskImage());
            frame_bitmap_model.setHeight(Share.frame_alldetails.get(i).getHeight());
            frame_bitmap_model.setWidth(Share.frame_alldetails.get(i).getWidth());
            frame_bitmap_model.setDisp_height(Share.frame_alldetails.get(i).getDisplayHeight());
            frame_bitmap_model.setDisp_width(Share.frame_alldetails.get(i).getDisplayWidth());
            Share.frame_bitmap.add(frame_bitmap_model);
        }
        if (Share.frame_bitmap.size() == Share.frame_alldetails.size()) {
            initviews();
        }

//        SvgLoader.pluck()
//                .with(this)
//                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
//                .load(Share.model_svg, phill_image_view);

//        Glide.with(test_frame_editing_activity.this).load(Share.model_svg).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//
//
//
//
//                phill_image_view.setImageBitmap(resource);
////                ByteArrayOutputStream bos = new ByteArrayOutputStream();
////                resource.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
////                byte[] bitmapdata = bos.toByteArray();
////                ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
////                phill_image_view.load_urlAsset(bs);
//            }
//        });


//        for (int i = 0; i < Share.frame_bitmap.size(); i++) {
//            if (Share.frame_bitmap.get(i).getMask_bitmap() == null) {
//                Bitmap original = ((BitmapDrawable) frame_2[i]).getBitmap();
//                Bitmap mask = Share.frame_bitmap.get(i).getOriginal_bitmap();
//                Bitmap result = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas mCanvas = new Canvas(result);
//                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//                mCanvas.drawBitmap(original, 0, 0, null);
//                mCanvas.drawBitmap(mask, 0, 0, paint);
//                paint.setXfermode(null);
//                Share.frame_bitmap.get(i).setMask_bitmap(result);
//            }
//        }
//        phill_image_view.loadAsset("img.svg");
    }

    private void getDisplaySize() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void initviews() {
        frame_number = 6;
        rv_frame = findViewById(R.id.rv_frame);
        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.frame_bitmap.clear();
                finish();
            }
        });
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;


                boolean save = true;
                for (int i = 0; i < Share.frame_bitmap.size(); i++) {
                    if (Share.frame_bitmap.get(i).getSave_mask_bitmap() == null) {
                        save = false;
                    }
                }
                Log.e("SAVE", "addtocart: " + save);
                if (!save) {
                    Toast.makeText(test_frame_editing_activity.this, "Please add image", Toast.LENGTH_SHORT).show();
                } else {
                    if (!SharedPrefs.getBoolean(test_frame_editing_activity.this, Share.key_reg_suc)) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(test_frame_editing_activity.this);
                        alertDialog.setTitle(getString(R.string.log_in));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(getString(R.string.need_login));
                        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                Intent intent = new Intent(test_frame_editing_activity.this, LogInActivity.class);
                                startActivity(intent);

                            }
                        });
                        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        });

                        alertDialog.create().show();

                        return;
                    }
                    Log.e("CHECKCART", "addtocart: 4--" );
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(test_frame_editing_activity.this);
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.add_to_cart));
                    alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                            create_preview_bitmap();

                        }
                    });
                    alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

                    alertDialog.create().show();
//                generate_jpg();
                }
            }
        });
        test_frame_image_adapter = new test_frame_image_adapter(test_frame_editing_activity.this, Share.frame_bitmap);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(test_frame_editing_activity.this, LinearLayoutManager.HORIZONTAL, false);
        rv_frame.setLayoutManager(horizontalLayoutManager);
        rv_frame.setAdapter(test_frame_image_adapter);
    }

    private void generate_jpg() {
        int p = 0;
        Log.e("VALUE", "generate_jpg: =======>" + p + "======>" + (p + 1));
        mergeBitmap(addWhiteBorder(Share.frame_bitmap.get(p).getSave_mask_bitmap(), 10), addWhiteBorder(Share.frame_bitmap.get(p + 1).getSave_mask_bitmap(), 10), p + 1);
    }


    public void addtocart(View v) {
        boolean save = true;
        for (int i = 0; i < Share.frame_bitmap.size(); i++) {
            if (Share.frame_bitmap.get(i).getSave_mask_bitmap() == null) {
                save = false;
            }
        }
        Log.e("SAVE", "addtocart: " + save);
        if (!save) {
            Toast.makeText(this, "Please add image", Toast.LENGTH_SHORT).show();
        } else {

            if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(test_frame_editing_activity.this);
                alertDialog.setTitle(getString(R.string.log_in));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.need_login));
                alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent intent = new Intent(test_frame_editing_activity.this, LogInActivity.class);
                        startActivity(intent);

                    }
                });
                alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                alertDialog.create().show();

                return;
            }
            Log.e("CHECKCART", "addtocart: 3--" );
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(test_frame_editing_activity.this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.add_to_cart));
            alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.create().show();
        }
    }

    private void create_preview_bitmap() {
        phill_image_view.setDrawingCacheEnabled(true);
        phill_image_view.buildDrawingCache();

        new createReq().execute();
    }

    Bitmap CropBitmapTransparency(Bitmap sourceBitmap) {
        int minX = sourceBitmap.getWidth();
        int minY = sourceBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < sourceBitmap.getHeight(); y++) {
            for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                int alpha = (sourceBitmap.getPixel(x, y) >> 24) & 255;
                if (alpha > 0)   // pixel is not 100% transparent
                {
                    if (x < minX)
                        minX = x;
                    if (x > maxX)
                        maxX = x;
                    if (y < minY)
                        minY = y;
                    if (y > maxY)
                        maxY = y;
                }
            }
        }
        if ((maxX < minX) || (maxY < minY))
            return null; // Bitmap is entirely transparent

        // crop bitmap to non-transparent area and return:
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }

    private void mergeBitmap(Bitmap bitmap1, Bitmap bitmap2, int i) {

        Log.e("VALUE", "mergeBitmap: ==========>" + i);
        Bitmap mergedBitmap = null;

        int w, h = 0;

        h = bitmap1.getHeight() + bitmap2.getHeight();
        if (bitmap1.getWidth() > bitmap2.getWidth()) {
            w = bitmap1.getWidth();
        } else {
            w = bitmap2.getWidth();
        }

        mergedBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(mergedBitmap);

        canvas.drawBitmap(bitmap1, 0f, 0f, null);
        canvas.drawBitmap(bitmap2, 0f, bitmap1.getHeight(), null);

        if (i == Share.frame_bitmap.size()) {
            saveToInternalStorage(mergedBitmap);
        } else {
            mergeBitmap(mergedBitmap, addWhiteBorder(Share.frame_bitmap.get(i++).getSave_mask_bitmap(), 10), i++);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (Share.frame_bitmap.size() != 0) {
//            if (progress != null) {
//                progress.dismiss();
//            }
            hideProgressDialog();
            new HttpImageRequestTask().execute();
            if (test_frame_image_adapter != null)
                test_frame_image_adapter.notifyDataSetChanged();
            else {
                test_frame_image_adapter = new test_frame_image_adapter(test_frame_editing_activity.this, Share.frame_bitmap);
                if(rv_frame!=null) {
                    rv_frame.setAdapter(test_frame_image_adapter);
                }else {
                    rv_frame = findViewById(R.id.rv_frame);
                    rv_frame.setAdapter(test_frame_image_adapter);
                }
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(android.os.Environment.getExternalStorageDirectory().toString(), "/printphoto_" + System.currentTimeMillis() + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    void generatePDF_Album(MultipartBody.Builder builder, File file_cover_bitmap) {
        Document document = new Document();
        document.setPageSize(new Rectangle(2480, 3508));
        document.setRole(PdfName.IMAGE);

        document.setMargins(20, 20, 20, 20);
        String directoryPath = android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();

        String fileName;

        fileName = "/printphoto_" + System.currentTimeMillis() + ".pdf";

        try {
            PdfWriter.getInstance(document, new FileOutputStream(directoryPath + fileName));
        } catch (Exception e) {
            Log.e("EXCEPTION", "generatePDF_Album: e print-->"+e.getMessage() );
        }
        document.open();
        int p = 0;
        for (p = 0; p < Share.frame_bitmap.size(); p++) {
            Log.e("P", "generatePDF_Album: ========>" + p);
            Bitmap final_bitmap;
            if (Share.frame_bitmap.get(p).getSave_mask_bitmap().getWidth() > 2460) {
                final_bitmap = newRotateBitmap(Share.frame_bitmap.get(p).getSave_mask_bitmap(), 90);
                final_bitmap = addWhiteBorder(final_bitmap, 25);
                if (p + 1 < Share.frame_bitmap.size()) {
                    if (combineImages(Share.frame_bitmap.get(p).getSave_mask_bitmap(), Share.frame_bitmap.get(p + 1).getSave_mask_bitmap()).getWidth() < 2460) {
                        final_bitmap = combineImages(addWhiteBorder(Share.frame_bitmap.get(p).getSave_mask_bitmap(), 25), addWhiteBorder(Share.frame_bitmap.get(p + 1).getSave_mask_bitmap(), 25));
                        p++;
                    }
                }
            } else {
                final_bitmap = Share.frame_bitmap.get(p).getSave_mask_bitmap();
                final_bitmap = addWhiteBorder(final_bitmap, 25);
                if (p + 1 < Share.frame_bitmap.size()) {
                    if (combineImages(Share.frame_bitmap.get(p).getSave_mask_bitmap(), Share.frame_bitmap.get(p + 1).getSave_mask_bitmap()).getWidth() < 2460) {
                        final_bitmap = combineImages(addWhiteBorder(Share.frame_bitmap.get(p).getSave_mask_bitmap(), 25), addWhiteBorder(Share.frame_bitmap.get(p + 1).getSave_mask_bitmap(), 25));
                        p++;
                    }
                }
            }
            File file_printphoto = getFile("printphoto_" +
                    System.currentTimeMillis(), final_bitmap, ".png");
            Image image = null;
            try {
                image = Image.getInstance(String.valueOf(file_printphoto), true);
                image.scaleAbsoluteHeight(final_bitmap.getHeight());
                image.scaleAbsoluteWidth(final_bitmap.getWidth());
//                image.scaleAbsolute(new Rectangle((final_bitmap.getWidth()/300)*85,(final_bitmap.getHeight()/300)*85));
//                image.setSpacingBefore(500);
//                image.setSpacingAfter(500);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadElementException e) {
                e.printStackTrace();
            }

            try {
                document.add(image);
            } catch (DocumentException e) {

            }
//        }
        }
        document.close();


        Log.e("FILECOVER", "generatePDF_Album: " + file_cover_bitmap);
        Log.e("FILECOVER", "generatePDF_Album: " + (directoryPath + fileName));

        File print_file = new File(directoryPath + fileName);
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("model_id", getIntent().getStringExtra("model_id"));
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), print_file);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file_cover_bitmap);
        builder.addFormDataPart("print_image", fileName, requestBody);
        builder.addFormDataPart("user_id", SharedPrefs.getString(test_frame_editing_activity.this, Share.key_ + RegReq.id));
        builder.addFormDataPart("case_image", file_cover_bitmap.getName(), requestBody1);
        builder.addFormDataPart("quantity", "1");
        builder.addFormDataPart("ln", Locale.getDefault().getLanguage());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Share.frame_bitmap.clear();
    }

    private Bitmap addWhiteBorder(Bitmap bmp, int borderSize) {
        Log.e("AREA", "addWhiteBorder: ===>WIDTH" + bmp.getWidth() + "=====>" + borderSize * 2);
        Log.e("AREA", "addWhiteBorder: ===>HEIGTH" + bmp.getHeight() + "=====>" + borderSize * 4);
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 4, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

    public Bitmap combineImages(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
        Bitmap cs = null;

        int width, height = 0;

        if (c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth();
        } else {
            width = s.getWidth() + s.getWidth();
        }

        if (c.getHeight() > s.getHeight()) {
            height = c.getHeight();
        } else {
            height = s.getHeight();
        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth(), 0f, null);

        // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

    OutputStream os = null;
    try {
      os = new FileOutputStream(loc + tmpImg);
      cs.compress(CompressFormat.PNG, 100, os);
    } catch(IOException e) {
      Log.e("combineImages", "problem combining images", e);
    }*/

        return cs;
    }

    public Bitmap newRotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public File getFile(String filename, Bitmap yourbitmap, String formate) {
        File f = new File(getCacheDir(), filename + formate);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        Bitmap bitmap = yourbitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (formate.contains("jpg")) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);

        } else {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);

        }
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public class create_bitmap extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        URL url;
        InputStream inputStream = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(test_frame_editing_activity.this, "", getString(R.string.loading), true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
            Log.e("INPUTSTREAM", "onPostExecute: =========>" + inputStream.toString());
//            phill_image_view.load_urlAsset(inputStream);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                url = new URL(Share.model_svg);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10000);
                inputStream = urlConnection.getInputStream();

            } catch (MalformedURLException e) {
                Log.e("EXCEP1", "doInBackground: " + e.getLocalizedMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("EXCEP2", "doInBackground: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    private class HttpImageRequestTask extends AsyncTask<Void, Void, Drawable> {

        SVG svg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress = ProgressDialog.show(test_frame_editing_activity.this, "", getString(R.string.loading), true, false);
            showProgressDialog(test_frame_editing_activity.this);
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            //progress.dismiss();
            hideProgressDialog();
            if (inputStream != null) {
                phill_image_view = null;
                phill_image_view = findViewById(R.id.phill_image_view);
                phill_image_view.clearColorFilter();

                Drawable d = getResources().getDrawable(R.drawable.appicon);
                Bitmap b = ((BitmapDrawable) d).getBitmap();
                phill_image_view.load_urlAsset(inputStream, b);

//                progress.dismiss();
                hideProgressDialog();
//                phill_image_view.setImageDrawable(drawable);
            }
        }


        @Override
        protected Drawable doInBackground(Void... params) {
            try {
                final URL url = new URL(Share.model_svg);
                //              final URL url = new URL("http://vasundharaapps.com/151161238813418.svg");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(5000);
                inputStream = null;
                inputStream = urlConnection.getInputStream();
                return null;
            } catch (final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("EXCEPTION", "run: " + e.getLocalizedMessage());
//                        if (progress != null) {
//                            progress.dismiss();
//                        }
                        hideProgressDialog();
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                            alertDialog = new AlertDialog.Builder(test_frame_editing_activity.this).create();
                            alertDialog.setTitle(getString(R.string.internet_connection));
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(getString(R.string.slow_connect));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    new HttpImageRequestTask().execute();
                                }
                            });
                            alertDialog.show();
                        } else {
                            alertDialog = new AlertDialog.Builder(test_frame_editing_activity.this).create();
                            alertDialog.setTitle(getString(R.string.internet_connection));
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(getString(R.string.slow_connect));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    new HttpImageRequestTask().execute();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                });
                cancel(true);
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

    }

    public class createReq extends AsyncTask<Void, Void, Void> {

        MultipartBody.Builder builder;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(test_frame_editing_activity.this, "", getString(R.string.loading), true, false);
            showProgressDialog(test_frame_editing_activity.this);
            builder = new MultipartBody.Builder();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (builder != null) {
                MultipartBody multipartBody = builder.build();
                APIService apiService = new MainApiClient(test_frame_editing_activity.this).getApiInterface();
                Call<Cart> cartCall = apiService.sendCart(multipartBody);
                cartCall.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        if (response != null) {
                            if (response.body().getResponseCode().equalsIgnoreCase("1")) {


                                if (response.body().getcart_data().getStatus() == 1) {
                                    upload = true;
                                    Share.resultbitmap = null;
                                    Share.final_result_bitmap = null;
                                    Share.edit_image = false;
                                    //progressDialog.dismiss();
                                    hideProgressDialog();
                                    if (Default_image_activity.Companion.getActivity() != null) {
                                        Default_image_activity.Companion.getActivity().finish();
                                    }
                                    if (MainActivity.activity != null) {
                                        MainActivity.activity.finish();
                                    }
                                    if (ModelListActivity.activity != null) {
                                        ModelListActivity.activity.finish();
                                    }


                                    finish();
                                    Share.frame_bitmap.clear();
                                    overridePendingTransition(R.anim.app_right_in, R.anim.app_left_out);
                                } else {
                                    Log.e("FAILURE", "onResponse: " + response.body().getResponseMessage());
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(test_frame_editing_activity.this);
                                    alertDialog.setTitle(getString(R.string.upload_fail));
                                    alertDialog.setCancelable(false);
                                    alertDialog.setMessage(response.body().getcart_data().getMessage());
                                    alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //progressDialog.dismiss();
                                            hideProgressDialog();
                                            dialog.dismiss();
                                        }
                                    });

                                    alertDialog.create().show();
                                }
                            } else {
                                //progressDialog.dismiss();
                                hideProgressDialog();
                                Log.e("ISSUE", "onResponse: "+response.body().getResponseMessage());
                                Toast.makeText(test_frame_editing_activity.this, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        Log.d("response", "==>" + response.toString());
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Log.e("response", "Failed==>" + t.getLocalizedMessage());
                        Log.e("response", "Failed==>" + t.getMessage());
//                        if (progressDialog != null && progressDialog.isShowing())
//                            progressDialog.dismiss();
                        hideProgressDialog();

                        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(test_frame_editing_activity.this).create();
                            alertDialog.setTitle(getString(R.string.time_out));
                            alertDialog.setMessage(getString(R.string.connect_time_out));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    create_preview_bitmap();
                                }
                            });
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(test_frame_editing_activity.this).create();
                            alertDialog.setTitle(getString(R.string.internet_connection));
                            alertDialog.setMessage(getString(R.string.slow_connect));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    create_preview_bitmap();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                });
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap cover_bitmap = (CropBitmapTransparency(phill_image_view.getDrawingCache()));
            File file_cover_bitmap = getFile("cover_bitmap_" + System.currentTimeMillis(), cover_bitmap, ".png");
            generatePDF_Album(builder, file_cover_bitmap);
            return null;
        }
    }
}

class test_frame_image_adapter extends RecyclerView.Adapter<test_frame_image_adapter.MyViewHolder> {

    private static final long MIN_CLICK_INTERVAL = 1500;
    public DisplayImageOptions options;
    private List<frame_bitmap_model> sqlitemodelList;
    private Context mContext;
    private String[] str = new String[2];
    private long mLastClickTime;

    public test_frame_image_adapter(Context mContext, List<frame_bitmap_model> frame) {
        this.sqlitemodelList = frame;
        this.mContext = mContext;
    }

    public static Bitmap RotateBitmap(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onBindViewHolder(final test_frame_image_adapter.MyViewHolder holder, final int position) {

        if (sqlitemodelList.get(position).getSave_mask_bitmap() != null) {
            holder.background.setImageBitmap(RotateBitmap(sqlitemodelList.get(position).getSave_mask_bitmap()));
        } else {
            ImageLoader.getInstance().displayImage(sqlitemodelList.get(position).getMask_bitmap(), holder.background, options);
        }
        if (position > 8) {
            holder.tv_frame_number.setText("" + (position + 1));
        } else {
            holder.tv_frame_number.setText("0" + (position + 1));
        }
        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;
                Share.frame_id = position;
                final ProgressDialog progressDialog = ProgressDialog.show(mContext, "", "Loading", true, false);
                progressDialog.show();
//                Bitmap original = ((BitmapDrawable) holder.background.getDrawable()).getBitmap();
                if (sqlitemodelList.get(position).getSave_mask_bitmap() != null) {
                    Log.e("HERE_AFTER_SAVE", "onClick: ");
                    Share.mask_bitmap = RotateBitmap(sqlitemodelList.get(position).getSave_mask_bitmap());
                    Share.displayheight = Float.valueOf(sqlitemodelList.get(position).getDisp_height());
                    Share.display_width = Float.valueOf(sqlitemodelList.get(position).getDisp_width());

                    progressDialog.dismiss();
                    Intent intent = new Intent(mContext, frame_Dynamic_EditActivity.class);
                    intent.putExtra("position", "" + position);
                    intent.putExtra("model_name", "Frame");
                    intent.putExtra("model_id", "4");
                    intent.putExtra("user_id", "1");
                    intent.putExtra("quantity", "1");
                    intent.putExtra("total_amount", "250");
                    intent.putExtra("model_id", "4");
                    intent.putExtra("paid_amount", "250");
                    Share.maskheight = Float.parseFloat(sqlitemodelList.get(position).getHeight());
                    Share.maskwidth = Float.parseFloat(sqlitemodelList.get(position).getWidth());
                    intent.putExtra("shipping", "0");
                    mContext.startActivity(intent);
                } else {
                    Log.e("HERE_BEFORE_SAVE", "onClick: ");

                    Glide.with(mContext).asBitmap().load(sqlitemodelList.get(position).getMask_bitmap()).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Share.mask_bitmap = changeBitmapColor(resource);

                            Share.displayheight = Float.valueOf(sqlitemodelList.get(position).getDisp_height());
                            Share.display_width = Float.valueOf(sqlitemodelList.get(position).getDisp_width());

                            progressDialog.dismiss();
                            Intent intent = new Intent(mContext, frame_Dynamic_EditActivity.class);
                            intent.putExtra("position", "" + position);
                            intent.putExtra("model_name", "Frame");
                            intent.putExtra("model_id", "4");
                            intent.putExtra("user_id", "1");
                            intent.putExtra("quantity", "1");
                            intent.putExtra("total_amount", "250");
                            intent.putExtra("model_id", "4");
                            intent.putExtra("paid_amount", "250");
                            Share.maskheight = Float.parseFloat(sqlitemodelList.get(position).getHeight());
                            Share.maskwidth = Float.parseFloat(sqlitemodelList.get(position).getWidth());
                            intent.putExtra("shipping", "0");
                            mContext.startActivity(intent);
                        }
                    });

                }


            }
        });
    }

    private Bitmap changeBitmapColor(Bitmap sourceBitmap) {

        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(Color.WHITE, -1);
        p.setColorFilter(filter);


        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }

    @Override
    public test_frame_image_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.frame_row_test, parent, false);

        return new test_frame_image_adapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView background;
        public TextView tv_frame_number;
        public LinearLayout ll_main;

        public MyViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.up);
            tv_frame_number = view.findViewById(R.id.tv_frame_number);
            ll_main = view.findViewById(R.id.ll_main);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }
    }


}
