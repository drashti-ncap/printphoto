package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.mall_AllChild;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class mall_sub_category_data extends RecyclerView.Adapter<mall_sub_category_data.MyViewHolder> {

    ProgressDialog pd;
    OnItemClickListener onItemClickListener;
    private List<mall_AllChild> sqlitemodelList;
    private Context mContext;
    private DisplayImageOptions options;

    public mall_sub_category_data(Context mContext, List<mall_AllChild> sqlitemodelList) {
        this.sqlitemodelList = sqlitemodelList;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        ImageLoader.getInstance().displayImage(sqlitemodelList.get(position).getAppImage(), holder.iv_pro_image, options);

        holder.tv_category_name.setText(sqlitemodelList.get(position).getName());
        holder.ll_tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickLister(view, position);
            }

//            @Override
//            public void onSingleClick(View v) {
//                long currentClickTime = SystemClock.uptimeMillis();
//                long elapsedTime = currentClickTime - mLastClickTime;
//                mLastClickTime = currentClickTime;
//                if (elapsedTime <= MIN_CLICK_INTERVAL)
//                    return;
//                get_other_category(sqlitemodelList.get(position).getId(), position, holder);
//            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_row_mall, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return sqlitemodelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_tshirt;
        ImageView iv_pro_image;
        TextView tv_category_name;

        public MyViewHolder(View view) {
            super(view);
            ll_tshirt = view.findViewById(R.id.ll_tshirt);
            iv_pro_image = view.findViewById(R.id.iv_pro_image);
            tv_category_name = view.findViewById(R.id.tv_category_name);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.progress_animation)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

        }
    }
}
