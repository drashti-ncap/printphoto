package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.newoffers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.GlideUtilKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.THUMB_TYPE;
import com.mobile.cover.photo.editor.back.maker.model.getdata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodata;
import com.mobile.cover.photo.editor.back.maker.model.getpromodetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;


public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    ProgressDialog pd;
    private List<getdata> sqlitemodelList;
    private List<getpromodata> sqlitemodelList1;
    private List<getpromodetail> sqlitemodelList2;
    private Context mContext;
    private List<getdata> sqlitemodelListFiltered;
    private boolean isOfferCode = true;
    private String fsCouponCode = "";
    private String offerId = "";
    private DisplayImageOptions options;

    public OfferAdapter(Context mContext, List<getdata> sqlitemodelList, List<getpromodata> sqlist2, List<getpromodetail> sqlist3) {
        this.sqlitemodelList = sqlitemodelList;
        this.sqlitemodelList1 = sqlist2;
        this.sqlitemodelList2 = sqlist3;
        this.mContext = mContext;
        this.sqlitemodelListFiltered = sqlitemodelList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position >= sqlitemodelList.size()) {
            final int POS = position - sqlitemodelList.size();
            final getpromodata sqlitemodel1 = sqlitemodelList1.get(POS);
            holder.tv_code.setText(sqlitemodel1.getPromo_code());
        } else {
            final getdata sqlitemodel = sqlitemodelList.get(position);
            if (sqlitemodel.getOffer_code().equalsIgnoreCase("-")) {
                holder.tv_code.setVisibility(View.GONE);
            } else {
                holder.tv_code.setVisibility(View.VISIBLE);
            }
            holder.tv_code.setText(sqlitemodel.getDisplay_message());
            holder.tv_description.setText(sqlitemodel.getDescription());
            holder.tv_expiry.setText(sqlitemodel.getExpiry_text());


            GlideUtilKt.loadImage(mContext, sqlitemodel.getOffer_new_image(), holder.id_image, holder.progressBar, THUMB_TYPE.SQUARE);

            // ImageLoader.getInstance().displayImage(sqlitemodel.getOffer_new_image(), holder.id_image, options);

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
                    Toast.makeText(mContext, mContext.getString(R.string.offer_code_copied), Toast.LENGTH_SHORT).show();
                    clipboard.setPrimaryClip(clip);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_frag_offer, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size() + sqlitemodelList1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_code, tv_description, tv_expiry;
        public Button btn_apply;
        public ImageView id_image, tv_terms_condition;
        public ProgressBar progressBar;
        public CardView main_card;


        public MyViewHolder(View view) {
            super(view);
            tv_code = view.findViewById(R.id.tv_code);
            tv_expiry = view.findViewById(R.id.tv_expiry);
            tv_description = view.findViewById(R.id.tv_description);
//            btn_apply = view.findViewById(R.id.btn_apply);
            id_image = view.findViewById(R.id.id_image);
            tv_terms_condition = view.findViewById(R.id.tv_terms_condition);
            progressBar = view.findViewById(R.id.progressBar);
            main_card = view.findViewById(R.id.main_card);
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }
    }

}
