package com.mobile.cover.photo.editor.back.maker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.ProductImage;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FinalScreenActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 11/18/2016.
 */
public class Display_slide_CustomPagerAdapter extends PagerAdapter {

    LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<ProductImage> al_image;

    public Display_slide_CustomPagerAdapter(Context context, List<ProductImage> al_image) {
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
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.display_pager_item, container, false);

        ImageView iv_my_photos = itemView.findViewById(R.id.iv_my_photos);
        iv_my_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.slider_url = position;
                Intent intent = new Intent(mContext, FinalScreenActivity.class);
                mContext.startActivity(intent);
            }
        });
        final ProgressBar progressBar = itemView.findViewById(R.id.progressBar);
        try {
            Picasso.with(mContext)
                    .load(al_image.get(position).getThumbImage())
                    .centerInside()
                    .fit()
                    .placeholder(R.drawable.transparent_background)
                    .into(iv_my_photos, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } catch (OutOfMemoryError e) {
        }
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