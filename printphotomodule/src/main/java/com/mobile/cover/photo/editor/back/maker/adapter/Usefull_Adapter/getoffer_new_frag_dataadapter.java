package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.model.getdata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodetail;

import java.util.List;


public class getoffer_new_frag_dataadapter extends RecyclerView.Adapter<getoffer_new_frag_dataadapter.MyViewHolder> {

    private List<getdata> sqlitemodelList;
    private List<getpromodata> sqlitemodelList1;
    private List<getpromodetail> sqlitemodelList2;
    private Context mContext;
    private List<getdata> sqlitemodelListFiltered;
    private boolean isOfferCode = true;
    private String fsCouponCode = "";
    private String offerId = "";

    public getoffer_new_frag_dataadapter(Context mContext, List<getdata> sqlitemodelList, List<getpromodata> sqlist2, List<getpromodetail> sqlist3) {
        this.sqlitemodelList = sqlitemodelList;
        this.sqlitemodelList1 = sqlist2;
        this.sqlitemodelList2 = sqlist3;
        this.mContext = mContext;
        this.sqlitemodelListFiltered = sqlitemodelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_frag_offer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position >= sqlitemodelList.size()) {
            final int POS = position - sqlitemodelList.size();
            final getpromodata sqlitemodel1 = sqlitemodelList1.get(POS);
            holder.tv_code.setText(mContext.getString(R.string.use_code_val) + sqlitemodel1.getPromo_code());
        } else {
            final getdata sqlitemodel = sqlitemodelList.get(position);

            holder.tv_code.setText(mContext.getString(R.string.use_code_val) + sqlitemodel.getOffer_code());
            holder.tv_expiry.setText(sqlitemodel.getExpiry_text());
            holder.tv_terms_condition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle(mContext.getString(R.string.term_of_use));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(sqlitemodel.getTerms_condition());
                    alertDialog.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.create().show();
                }
            });

            holder.main_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("offercode", sqlitemodel.getOffer_code());
                    Toast.makeText(mContext, mContext.getString(R.string.mall_code_copied), Toast.LENGTH_SHORT).show();
                    clipboard.setPrimaryClip(clip);
                }
            });
            holder.tv_description.setText(sqlitemodel.getDescription());

            Glide.with(mContext).asBitmap()
                    .load(sqlitemodel.getOffer_new_image())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            holder.id_image.setImageBitmap(resource);
                            holder.progressBar.setVisibility(View.GONE);
                            System.gc();

                        }

                        @Override
                        public void onLoadFailed(Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size() + sqlitemodelList1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_code, tv_description, tv_expiry;
        public ImageView id_image, tv_terms_condition;
        public ProgressBar progressBar;
        public CardView main_card;


        public MyViewHolder(View view) {
            super(view);
            tv_code = view.findViewById(R.id.tv_code);
            tv_description = view.findViewById(R.id.tv_description);
            tv_expiry = view.findViewById(R.id.tv_expiry);
            id_image = view.findViewById(R.id.id_image);
            tv_terms_condition = view.findViewById(R.id.tv_terms_condition);
            progressBar = view.findViewById(R.id.progressBar);
            main_card = view.findViewById(R.id.main_card);
        }
    }

//    public  void m6676a(ArrayList<msg_listmodel> list) {
//        this.sqlitemodelList = list;
//    }

}
