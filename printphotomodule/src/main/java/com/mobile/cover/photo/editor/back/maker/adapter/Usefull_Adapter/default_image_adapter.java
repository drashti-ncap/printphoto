package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pagination.MainActivity;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.GlideUtilKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.THUMB_TYPE;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.CaseEditActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Default_image_activity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.get_images;
import com.mobile.cover.photo.editor.back.maker.model.getdefault_images;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;

import java.util.ArrayList;
import java.util.List;


public class default_image_adapter extends RecyclerView.Adapter<default_image_adapter.MyViewHolder> {

    private List<getdefault_images> sqlitemodelList;
    private Context mContext;
    private List<get_images> sqlitemodelListFiltered_images;
    private TextView tv_no_fnd;
    private boolean isEditable = false;

    public default_image_adapter(Context mContext, List<getdefault_images> sqlitemodelList, List<get_images> sqlitemodelList_images, TextView textView) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
        this.sqlitemodelListFiltered_images = sqlitemodelList_images;
        this.tv_no_fnd = textView;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_name_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final getdefault_images sqlitemodel = sqlitemodelList.get(position);

        holder.setIsRecyclable(false);

        Log.e("FLAG", "onBindViewHolder: =======>" + sqlitemodel.getFlag());
      /*  if (sqlitemodel.getFlag().equalsIgnoreCase("1")) {
            holder.iv_new.setImageResource(R.drawable.new_gif_final);
        } else {
            holder.iv_new.setImageResource(R.drawable.iphone_mask);
        }*/
        String thumbImage = sqlitemodel.getImg();
        if (thumbImage != null && !thumbImage.isEmpty()) {
            //  Log.i("TAG_2", "thumbImage: " + thumbImage);
            try {
                GlideUtilKt.loadImageCenterCrop(mContext, thumbImage, holder.iv_thumb, holder.progressBar, THUMB_TYPE.PORTRAIT);
            } catch (Exception e) {
                Log.i("TAG_2", "thumbImage: " + e.toString());
            }


        } else {
            holder.progressBar.setVisibility(View.GONE);
        }

        holder.tv_name.setText(sqlitemodel.getName());
        holder.tv_name.setSelected(true);

        if (position == 0) {
            holder.tv_name.setVisibility(View.GONE);
        }


        holder.main_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCaseClick(sqlitemodel);
            }
        });


    }

    private void onCaseClick(getdefault_images sqlitemodel) {

        model_details_data modelData = DataHelperKt.getModelData(mContext);

        if (sqlitemodel.getCategory_id() == null) {
            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        Share.case_category_id = Integer.parseInt(sqlitemodel.getCategory_id());
        Log.e("CASE_ID", "onClick: " + Share.case_category_id);
        if (Share.case_category_id == 0) {


            Intent intent = new Intent(mContext, CaseEditActivity.class);
            intent.putExtra("model_name", modelData.getModalName());
            intent.putExtra("model_id", "" + modelData.getModelId());
            intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
            intent.putExtra("quantity", "1");
            intent.putExtra("total_amount", modelData.getPrice());
            intent.putExtra("paid_amount", modelData.getPrice());
            Share.mobile_type = modelData.getImageType();
            Log.e("TAG", "onPostExecute: " + Share.mobile_type);
            if (!modelData.getWidth().equalsIgnoreCase("")) {
                intent.putExtra("width", modelData.getWidth());
                intent.putExtra("height", modelData.getHeight());
                Share.maskheight = Float.parseFloat(modelData.getHeight());
                Share.maskwidth = Float.parseFloat(modelData.getWidth());
                Log.e("width", "==>" + modelData.getWidth());
                Log.e("height", "==>" + modelData.getHeight());
            }

            intent.putExtra("shipping", "0");
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.right_in, R.anim.left_out);
        } else {


            if (modelData.getModelId() == null) {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                ((Activity) mContext).onBackPressed();
                return;
            }

            Intent intent;
            intent = new Intent(mContext, com.mobile.cover.photo.editor.back.maker.Pagination.MainActivity.class);
            intent.putExtra("isEditable", isEditable);
            intent.putExtra("model_name", Default_image_activity.Companion.getModel_name());
            intent.putExtra("model_id", "" + modelData.getModelId());
            intent.putExtra("user_id", SharedPrefs.getString(mContext, Share.key_ + RegReq.id));
            intent.putExtra("quantity", "1");
            intent.putExtra("total_amount", modelData.getPrice());
            intent.putExtra("paid_amount", modelData.getPrice());
            Share.mobile_type = modelData.getImageType();
            Log.e("TAG", "onPostExecute: " + Share.mobile_type);
            if (!modelData.getWidth().equalsIgnoreCase("")) {
                intent.putExtra("width", modelData.getWidth());
                intent.putExtra("height", modelData.getHeight());
            }

            intent.putExtra("shipping", "0");
            Log.e("CHECKTITLE", "onCaseClick: sqlite name -->" + sqlitemodel.getName());
            Share.categoryname = sqlitemodel.getName();
            Share.position = Integer.parseInt(sqlitemodel.getCategory_id());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }

    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }


    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public ArrayList<getdefault_images> filter(String s) {
        ArrayList<getdefault_images> subData = new ArrayList<>();
//        sqlitemodelList.clear();
        if (s.length() == 0) {
            subData.addAll(Share.subDataArrayList_category);
            Log.d("size", "0==>" + Share.subDataArrayList.size());
        } else {
            for (getdefault_images wp : Share.subDataArrayList_category) {
                Log.d("size", "1==>" + Share.subDataArrayList.size());

                if (wp.getName().toLowerCase().contains(s.toLowerCase())) {

                    subData.add(wp);
                    Log.d("size", "==>" + wp.getName());
                    tv_no_fnd.setVisibility(View.GONE);
                }
                if (subData.size() <= 0) {
                    tv_no_fnd.setVisibility(View.VISIBLE);
                }
            }
        }
        noti(subData);
        return subData;
    }

    public void noti(ArrayList<getdefault_images> subData) {
        this.sqlitemodelList = subData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //   public GifView iv_new;
        public TextView tv_name;
        public CardView main_card;
        public ImageView iv_thumb;
        public ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            //    iv_new = view.findViewById(R.id.iv_new);
            tv_name = view.findViewById(R.id.name);
            main_card = view.findViewById(R.id.main_card);
            iv_thumb = view.findViewById(R.id.iv_thumb);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

//    public  void m6676a(ArrayList<msg_listmodel> list) {
//        this.sqlitemodelList = list;
//    }

}
