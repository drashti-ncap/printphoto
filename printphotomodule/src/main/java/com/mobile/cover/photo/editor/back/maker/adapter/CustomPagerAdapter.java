package com.mobile.cover.photo.editor.back.maker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.ProductImage;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.customView.TouchImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by admin on 11/18/2016.
 */
public class CustomPagerAdapter extends PagerAdapter {

    LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<ProductImage> al_image;
    private DisplayImageOptions options;

    public CustomPagerAdapter(Context context, List<ProductImage> al_image) {
        mContext = context;
        this.al_image = al_image;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return al_image.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        final TouchImageView iv_my_photos = itemView.findViewById(R.id.iv_my_photos);
        final ProgressBar progressBar = itemView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.progress_animation)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
//        try {
//            Picasso.with(mContext)
//                    .load(al_image.get(position).getImage())
//                    .centerCrop()
//                    .fit()
//                    .noFade()
//                    .placeholder(R.drawable.transparent_background)
//                    .into(iv_my_photos, new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            progressBar.setVisibility(View.INVISIBLE);
//                        }
//
//                        @Override
//                        public void onError() {
//
//                        }
//                    });
//        }catch (OutOfMemoryError e){}
//        Glide.with(mContext).load(al_image.get(position).getImage()).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                progressBar.setVisibility(View.GONE);
//                iv_my_photos.setImageBitmap(resource);
//            }
//        });

        ImageLoader.getInstance().displayImage(al_image.get(position).getImage(), iv_my_photos, options);

        container.addView(itemView);

        return itemView;
    }

    public void deletePage(int position, int position_view_pager) {
        al_image.remove(position);
        //  Share.al_my_photos = al_image;
//        notifyDataSetChanged();

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}