package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.sipper.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.mug_image_response_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Dynamic_EditActivity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.testing_modules.PhotoPickupImageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ShipperAdapter extends RecyclerView.Adapter<ShipperAdapter.MyViewHolder> {

    private static final long MIN_CLICK_INTERVAL = 1500;
    Bitmap bitmap;
    OnItemClickListener onItemClickListner;
    private List<mug_image_response_data> sqlitemodelList;
    private Context mContext;
    private DisplayImageOptions options;
    private long mLastClickTime;
    private String[] str = new String[1];

    public ShipperAdapter(Context mContext, List<mug_image_response_data> sqlitemodelList_images) {
        this.sqlitemodelList = sqlitemodelList_images;
        this.mContext = mContext;
    }

    @Override
    public ShipperAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_main_row_default_image_item, parent, false);

        return new ShipperAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShipperAdapter.MyViewHolder holder, final int position) {
        final mug_image_response_data sqlitemodel;
        sqlitemodel = sqlitemodelList.get(position);
        holder.setIsRecyclable(false);
        holder.background.getLayoutParams().height = (int) (Share.screenHeight / 4.5);
        holder.rl_layout.getLayoutParams().height = (int) (Share.screenHeight / 4.5);
        final String image;
        image = sqlitemodel.getImage();
        if (sqlitemodel.getId() == 0) {
            Log.e("HERE_DRAWABLE", "onBindViewHolder: ");
            holder.rl_layout.setBackgroundColor(mContext.getResources().getColor(R.color.black));
            holder.background.setImageDrawable(mContext.getResources().getDrawable(R.drawable.fancy_add_photo));
            holder.background.setPadding(25, 25, 25, 25);
            holder.background.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.rl_layout.setPadding(4, 4, 4, 4);
            holder.background.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            ImageLoader.getInstance().displayImage(image, holder.background, options);
        }
        holder.itemView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (checkAndRequestPermissionsStorage(2)) {

                    long currentClickTime = SystemClock.uptimeMillis();
                    long elapsedTime = currentClickTime - mLastClickTime;
                    mLastClickTime = currentClickTime;
                    if (elapsedTime <= MIN_CLICK_INTERVAL)
                        return;


                    DataHelperKt.saveCaseId(mContext, sqlitemodel.getId());

                    if (sqlitemodel.getId() == 2) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 0) {
                        Log.e("ID", "onSingleClick: HERE");

                        Intent intent = new Intent(mContext, Dynamic_EditActivity.class);
                        intent.putExtra("model_name", Share.model_name);
                        intent.putExtra("model_id", "" + Share.model_id);
                        intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
                        intent.putExtra("quantity", "1");
                        intent.putExtra("total_amount", Share.total_amount);
                        intent.putExtra("paid_amount", Share.total_amount);
                        if (!Share.width.equalsIgnoreCase("")) {
                            intent.putExtra("width", Share.width);
                            intent.putExtra("height", Share.height);
                        }
                        intent.putExtra("shipping", "0");
                        mContext.startActivity(intent);

                    } else if (sqlitemodel.getId() == 3) {
                        Share.itemnum = 2;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 4) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 5) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 6) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 7) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 8) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 9) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 10) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 11) {
                        Share.itemnum = 2;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 12) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 13) {
                        Share.itemnum = 3;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 14) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 15) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 16) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 17) {
                        Share.itemnum = 2;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 18) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 19) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    } else if (sqlitemodel.getId() == 20) {
                        Share.itemnum = 1;
                        load_image(sqlitemodel.getImage(),position,holder.background);
                    }
                } else {
                    onItemClickListner.onItemClickLister(v, position);
                }

            }
        });
    }

    private void load_image(String image, int position, ImageView background) {

        str[0] = image;
        GetXMLTask task = new GetXMLTask(position,background);
        task.execute(str);

    }

    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }

    private boolean checkAndRequestPermissionsStorage(int code) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, code);
                return false;
            } else {

                return true;
            }
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, code);
                return false;
            } else {

                return true;
            }
        }else {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListner = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }


//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        if (permissions.length == 0) {
//            return;
//        }
//        boolean allPermissionsGranted = true;
//        if (grantResults.length > 0) {
//            for (int grantResult : grantResults) {
//                if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                    allPermissionsGranted = false;
//                    break;
//                }
//            }
//        }
//        if (!allPermissionsGranted) {
//            boolean somePermissionsForeverDenied = false;
//            for (String permission : permissions) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permission)) {
//                    //denied
//                    Log.e("denied", permission);
//                    if (requestCode == 1) {
//                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA}, 1);
//                    }
//                    if (requestCode == 2) {
//                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
//                    }
//
//                } else {
//
//                    if (ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED) {
//                        //allowed
//                        Log.e("allowed", permission);
////                        if (requestCode==2){
////                            Log.e("GRANTED", "checkAndRequestPermissionsStorage:=======> " );
////                        }
//
//                    } else {
//                        //set to never ask again
//                        Log.e("set to never ask again", permission);
//                        somePermissionsForeverDenied = true;
//                    }
//                }
//            }
//            if (somePermissionsForeverDenied) {
//
//                if (requestCode == 1) {
//                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
//                    alertDialogBuilder.setTitle("Permissions Required")
//                            .setMessage(getString(R.string.permission_sentence))
//                            .setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            })
//                            .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                                            Uri.fromParts("package", mContext.getPackageName(), null));
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    mContext.startActivity(intent);
//                                }
//                            })
//                            .setCancelable(false)
//                            .create()
//                            .show();
//                }
//                if (requestCode == 2) {
//                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
//                    alertDialogBuilder.setTitle("Permissions Required")
//                            .setMessage(getString(R.string.permission_sentence_storage))
//                            .setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            })
//                            .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                                            Uri.fromParts("package", mContext.getPackageName(), null));
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    mContext.startActivity(intent);
//                                }
//                            })
//                            .setCancelable(false)
//                            .create()
//                            .show();
//                }
//
//            }
//        } else {
//            switch (requestCode) {
//                case 1:
//                    break;
//                default:
//                    break;
//            }
//        }
//    }

    private class GetXMLTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
        ProgressDialog pd;
        int Pos;
        ImageView imageView;
        public GetXMLTask(int position, ImageView background) {
            Pos=position;
            imageView=background;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("TAG", "onPreExecute: =======>excute");
            pd = ProgressDialog.show(mContext, "", mContext.getResources().getString(R.string.loading), true, false);
            pd.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            if (bitmaps.size() == 1) {
                if (bitmaps.get(0) != null) {
                    Share.case_fancy_image = bitmaps.get(0);
                    onItemClickListner.onItemClickLister(imageView,Pos);
//                    Intent intent = new Intent(mContext, PhotoPickupImageActivity.class);
//                    intent.putExtra("from", "" + 2);
//                    mContext.startActivity(intent);
                }
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle(mContext.getResources().getString(R.string.download_failed));
                alertDialog.setMessage(mContext.getResources().getString(R.string.slow_connect));
                alertDialog.setButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                alertDialog.show();
            }

        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... strings) {

            ArrayList<Bitmap> map = new ArrayList<Bitmap>();
            try {
                //enhanced for statement, mainly used for arrays
                map.add(downloadImage(strings[0]));// I used as this for you to understand. You can use for each loop
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;


        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView background;
        public TextView tv_name;
        public RelativeLayout rl_layout;


        public MyViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.up);
            rl_layout = view.findViewById(R.id.rl_layout);
//            tv_name = view.findViewById(R.id.tv_name);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
    }

}
