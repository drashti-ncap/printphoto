package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.update_quantity;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FirebaseEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentCart;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemDeleteListener;
import com.mobile.cover.photo.editor.back.maker.model.CartItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {
    Context context;
    List<CartItem> cartItems = new ArrayList<>();
    OnItemClickListener onItemClickListener;
    OnItemDeleteListener onItemDeletekLister;
    ProgressDialog pd;
    int quantity = 0;
    Double amount;
    NumberFormat nf;
    private DisplayImageOptions options;

    private boolean isUpdating = false;

    public CartRecyclerAdapter(Context context, List<CartItem> data) {
        this.context = context;
        cartItems = data;
    }


    @Override
    public CartRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_cart_item, parent, false);
        return new CartRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartRecyclerAdapter.MyViewHolder holder, final int position) {

        nf = new DecimalFormat("#.##");


        quantity = cartItems.get(position).getQuantity();
        amount = cartItems.get(position).getSubtotal();
        holder.tv_quantity.setText("" + quantity);
        holder.id_image.setEnabled(false);
        holder.id_model_name.setText(cartItems.get(position).getModelName());
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar")) {
            holder.id_paid_amount.setText(Share.symbol + amount + context.getString(R.string.amount_val));
        } else {
            holder.id_paid_amount.setText(context.getString(R.string.amount_val) + " " + Share.symbol + amount);
        }

        if (cartItems.get(position).getDisplayImages().size() != 0) {
            if (cartItems.get(position).getDisplayImages().size() > 1) {
                Glide.with(context).asBitmap()
                        .load(cartItems.get(position).getDisplayImages().get(0).getThumbImage()).apply(new RequestOptions())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                if (cartItems.get(position).getDisplay().getRotate() == 1) {
                                    holder.id_image.setImageBitmap(RotateBitmap(resource, 90));
                                } else {
                                    holder.id_image.setImageBitmap(resource);
                                }
                                holder.id_image.setEnabled(true);
                                holder.progressBar.setVisibility(View.GONE);
                                System.gc();
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                ImageLoader.getInstance().displayImage(cartItems.get(position).getDisplayImages().get(0).getThumbImage(), holder.id_image, options);

                            }
                        });

            } else {

                Glide.with(context).asBitmap()
                        .load(cartItems.get(position).getDisplayImages().get(0).getImage()).apply(new RequestOptions())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                if (cartItems.get(position).getDisplay().getRotate() == 1) {
                                    holder.id_image.setImageBitmap(RotateBitmap(resource, 90));
                                } else {
                                    holder.id_image.setImageBitmap(resource);
                                }
                                holder.id_image.setEnabled(true);
                                holder.progressBar.setVisibility(View.GONE);
                                System.gc();
                            }

                            @Override
                            public void onLoadFailed(Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                holder.progressBar.setVisibility(View.GONE);
                                ImageLoader.getInstance().displayImage(cartItems.get(position).getDisplayImages().get(0).getImage(), holder.id_image, options);
                            }
                        });
            }

        }

        holder.id_delete_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemDeletekLister.onItemDeletekLister(view, position);

            }
        });

        holder.iv_plus.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (isUpdating) {
                    Toast.makeText(context, context.getString(R.string.please_wait), Toast.LENGTH_SHORT).show();
                } else {
                    quantity = Integer.parseInt(holder.tv_quantity.getText().toString());
                    quantity += 1;
                    holder.tv_quantity.setText("" + quantity);
                    updatequantity(quantity, String.valueOf(cartItems.get(position).getId()), holder, position, v);
                }

            }
        });

        holder.iv_minus.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (isUpdating) {
                    Toast.makeText(context, context.getString(R.string.please_wait), Toast.LENGTH_SHORT).show();
                } else {
                    quantity = Integer.parseInt(holder.tv_quantity.getText().toString());
                    if (quantity > 1) {
                        quantity -= 1;
                        holder.tv_quantity.setText("" + quantity);
                        updatequantity(quantity, String.valueOf(cartItems.get(position).getId()), holder, position, v);
                    } else {
                        Toast.makeText(context, context.getString(R.string.qty_cannot_less), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        holder.id_edit_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Share.edit_position = position;
//                Share.edited_image = cartItems.get(position).getNPrintImage();
//                Share.displayheight=Float.parseFloat("3.5");
//                Share.display_width=Float.parseFloat("3.5");
//                getxml(position);
//            }
//        });


    }

    private void updatequantity(final int qua, final String cartid, final MyViewHolder holder, final int position, final View v) {


        pd = ProgressDialog.show(context, "", context.getResources().getString(R.string.loading), true, false);


        CartItem cartData = cartItems.get(position);

        FBEventsKt.logAddedToCartEvent(context, cartData.getId() + "", cartData.getModelName(), cartData.getProductPrice() + "");
        FirebaseEventsKt.logAddToCartEvent(context, cartData.getId() + "", cartData.getModelName(), cartData.getProductPrice());


        APIService api = new MainApiClient(context).getApiInterface();

        Call<update_quantity> call = api.update_quantity(cartid, String.valueOf(quantity), Locale.getDefault().getLanguage());
        isUpdating = true;
        call.enqueue(new Callback<update_quantity>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<update_quantity> call, Response<update_quantity> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    update_quantity responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        Toast.makeText(context, responseData.getquantity_update_response_data().getMsg(), Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < Share.CartItem_data.size(); i++) {
                            CartItem cartItem = Share.CartItem_data.get(position);
                            if (cartItem.getId().equals(Integer.valueOf(cartid))) {
                                cartItem.setQuantity(qua);
                            }
                        }
//                        onItemClickListener.onItemClickLister(v,position);
                        Log.e(TAG, "onResponse: ///CartID///===>" + cartid);
                        Log.e("RESPONSE", "onResponse: ======>" + responseData.getquantity_update_response_data().getcart_total());
                        Log.e("RESPONSE", "onResponse: ======>SUBTOTAL////===" + responseData.getquantity_update_response_data().getSub_total());
                        if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar")) {
                            holder.id_paid_amount.setText(Share.symbol + (responseData.getquantity_update_response_data().getSub_total()) + context.getString(R.string.amount_val));
                        } else {
                            holder.id_paid_amount.setText(context.getString(R.string.amount_val) + Share.symbol + (responseData.getquantity_update_response_data().getSub_total()));
                        }


                        FragmentCart.id_paid_amount.setText(Share.symbol + (responseData.getquantity_update_response_data().getcart_total()));
                    } else {
                        quantity -= 1;
                        holder.tv_quantity.setText("" + quantity);
                        Toast.makeText(context, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                    pd.dismiss();
                } else {
                    pd.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }
                isUpdating = false;

            }

            @Override
            public void onFailure(Call<update_quantity> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                isUpdating = false;

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle(context.getResources().getString(R.string.time_out));
                    alertDialog.setMessage(context.getResources().getString(R.string.connect_time_out));
                    alertDialog.setButton(context.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            updatequantity(quantity, cartid, holder, position, v);
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle(context.getResources().getString(R.string.internet_connection));
                    alertDialog.setMessage(context.getResources().getString(R.string.slow_connect));
                    alertDialog.setButton(context.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            updatequantity(quantity, cartid, holder, position, v);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }


    // Creates Bitmap from InputStream and returns it
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

    // Makes HttpURLConnection and returns InputStream
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


    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemDeletekLister(OnItemDeleteListener onItemClickListener) {
        this.onItemDeletekLister = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView id_delete_cart, iv_plus, iv_minus, id_edit_cart;
        //        public Button btn_edit_again;
        public ImageViewZoom id_image;
        public CardView cardView;
        public ProgressBar progressBar;
        TextView id_model_name, id_paid_amount, tv_quantity;


        public MyViewHolder(View view) {
            super(view);

            id_image = view.findViewById(R.id.id_image);
            id_edit_cart = view.findViewById(R.id.id_edit_cart);
            cardView = view.findViewById(R.id.main_card);
            iv_minus = view.findViewById(R.id.iv_minus);
            iv_plus = view.findViewById(R.id.iv_plus);
            tv_quantity = view.findViewById(R.id.tv_quantity);
            id_model_name = view.findViewById(R.id.id_model_name);
            id_paid_amount = view.findViewById(R.id.id_paid_amount);
            id_delete_cart = view.findViewById(R.id.id_delete_cart);
            progressBar = view.findViewById(R.id.progressBar);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
    }
}
