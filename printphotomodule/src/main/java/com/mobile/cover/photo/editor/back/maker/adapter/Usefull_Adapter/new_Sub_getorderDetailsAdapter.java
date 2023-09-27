package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.orderitem;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.model.SimpleResponse;

import java.util.List;
import java.util.Locale;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class new_Sub_getorderDetailsAdapter extends RecyclerView.Adapter<new_Sub_getorderDetailsAdapter.MyViewHolder> {

    ProgressDialog pd;
    private List<orderitem> sqlitemodelList;
    private Context mContext;

    public new_Sub_getorderDetailsAdapter(Context mContext, List<orderitem> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final orderitem sqlitemodel = sqlitemodelList.get(position);
        holder.pro_name.setText(sqlitemodel.getModelName());
        holder.pro_image.setEnabled(false);

        holder.tv_qty.setText(mContext.getString(R.string.quantity_val) + sqlitemodel.getQuantity());
        holder.tv_rs.setText(mContext.getString(R.string.amount_val) + Share.symbol + sqlitemodel.getSubtotal());


        Glide.with(mContext).asBitmap()
                .load(sqlitemodel.getDisplayImages().get(0).getImage())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        if (sqlitemodel.getDisplay().getRotate() == 1) {
                            holder.pro_image.setImageBitmap(RotateBitmap(resource, 90));
                        } else {
                            holder.pro_image.setImageBitmap(resource);
                        }
                        holder.pro_image.setEnabled(true);
                        holder.progressBar.setVisibility(View.GONE);
                        System.gc();

                    }

                    @Override
                    public void onLoadFailed(Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });

//        if (position == sqlitemodelList.size() - 1) {
//            holder.ll_layout.setBackground(null);
//        }

        Log.e("CANCELABLE", "onBindViewHolder: ======>" + sqlitemodel.getIs_cancellable());

        if (sqlitemodel.getIs_cancellable() == 1) {
            if (sqlitemodelList.size() >= 2) {
                holder.ll_delete.setVisibility(View.VISIBLE);
            } else {
                holder.ll_delete.setVisibility(View.GONE);
            }
        } else {
            holder.ll_delete.setVisibility(View.GONE);
        }

        holder.ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle(mContext.getString(R.string.order_cancelation));
                alertDialog.setMessage(mContext.getString(R.string.sure_to_cancel_order));
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton(mContext.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bulk_order(position);
                        dialog.dismiss();
                    }
                });
                alertDialog.setNegativeButton(mContext.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
//                bulk_order(position);
            }
        });


    }

    private void bulk_order(final int position) {

        PackageManager manager = mContext.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        pd = ProgressDialog.show(mContext, "", mContext.getResources().getString(R.string.loading), true, false);

        APIService apiService = new MainApiClient(mContext).getApiInterface();
        Call<SimpleResponse> call = apiService.cancel_order_item(String.valueOf(sqlitemodelList.get(position).getId()), Locale.getDefault().getLanguage(), "" + version);

        call.enqueue(new Callback<SimpleResponse>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    pd.dismiss();
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(mContext.getString(R.string.order_cancelation));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(mContext.getString(R.string.item_cancelled));
                        alertDialog.setButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                                ((Activity) mContext).finish();
                                Share.order_cancel = true;
                            }
                        });
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                        alertDialog.setTitle(mContext.getString(R.string.order_cancelation));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(response.body().getResponseMessage());
                        alertDialog.setButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(mContext.getResources().getString(R.string.time_out));
                    alertDialog.setMessage(mContext.getResources().getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(mContext.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            bulk_order(position);

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle(mContext.getResources().getString(R.string.internet_connection));
                    alertDialog.setMessage(mContext.getResources().getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(mContext.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            bulk_order(position);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_row_item_details, parent, false);

        return new MyViewHolder(itemView);
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pro_name, tv_status, tv_rs, tv_qty;
        public ImageViewZoom pro_image;
        public ProgressBar progressBar;
        public LinearLayout ll_layout, ll_delete;


        public MyViewHolder(View view) {
            super(view);
            pro_name = view.findViewById(R.id.pro_name);
//            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_rs = view.findViewById(R.id.tv_rs);
            tv_qty = view.findViewById(R.id.tv_qty);
            pro_image = view.findViewById(R.id.pro_image);
            ll_layout = view.findViewById(R.id.ll_layout);
            ll_delete = view.findViewById(R.id.ll_delete);
            progressBar = view.findViewById(R.id.progressBar);


        }
    }

}
