package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.model.get_images;
import com.mobile.cover.photo.editor.back.maker.model.getdefault_images;

import java.util.List;


public class new_default_image_adapter extends RecyclerView.Adapter<new_default_image_adapter.MyViewHolder> {

    private List<getdefault_images> sqlitemodelList;
    private Context mContext;
    private List<get_images> sqlitemodelListFiltered_images;

    public new_default_image_adapter(Context mContext, List<getdefault_images> sqlitemodelList, List<get_images> sqlitemodelList_images) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
        this.sqlitemodelListFiltered_images = sqlitemodelList_images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_default_image_item, parent, false);

        return new MyViewHolder(itemView);
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final getdefault_images sqlitemodel = sqlitemodelList.get(position);

    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView background, up;

        public MyViewHolder(View view) {
            super(view);
//            iv_sticker = view.findViewById(R.id.iv_sticker);
            //up_border_mask = view.findViewById(R.id.up_border_mask);
            background = view.findViewById(R.id.background);
            up = view.findViewById(R.id.up);
        }
    }

//    public  void m6676a(ArrayList<msg_listmodel> list) {
//        this.sqlitemodelList = list;
//    }

}
