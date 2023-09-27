package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.AlbumImagesActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FaceActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FacebookAlbumPhotoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 11/18/2016.
 */
public class InstagramImageAdapter extends RecyclerView.Adapter<InstagramImageAdapter.ViewHolder> {
    ProgressDialog pd;
    private Activity activity;
    private ArrayList<JSONObject> al_image = new ArrayList<>();

    public InstagramImageAdapter(Activity activity, ArrayList<JSONObject> al_image) {
        this.activity = activity;
        this.al_image = al_image;
    }

    @Override
    public void onBindViewHolder(InstagramImageAdapter.ViewHolder holder, final int position) {
        try {
            /*Glide.with(activity)
                    .load(al_image.get(position).getJSONObject("low_resolution").getString("url"))
                    .placeholder(R.mipmap.ic_loading)
                    .into(holder.iv_album_image);*/


            Glide.with(activity)
                    .load(al_image.get(position).getJSONObject("low_resolution").getString("url"))
                    .placeholder(R.drawable.progress_animation)
                    .centerCrop()
                    .into(holder.iv_album_image);

            holder.tv_album_name.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.iv_album_image.getLayoutParams().height = SharedPrefs.getInt(activity, SharedPrefs.SCREEN_HEIGHT) / 4;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = ProgressDialog.show(activity, "", activity.getResources().getString(R.string.loading), true, false);
//                FaceActivity.iv_blast.setVisibility(View.GONE);
//                FaceActivity.iv_more_app.setVisibility(View.GONE);
                FaceActivity.is_closed = true;

                try {
//                    if (activity.getIntent().hasExtra("activity")) {
//                        Intent intent = new Intent(activity, CaseEditActivity.class);
//                        intent.putExtra("is_social", "true");
//                        GlobalData.is_social = true;
//                        GlobalData.modeBackground = GlobalData.MODE_BACKGROUND.CAMETA;
//                        activity.startActivity(intent);
////                        activity.finish();
//                    } else {
//                        FaceActivity.getFaceActivity().finish();
                    GlobalData.imageUrl = al_image.get(position).getJSONObject("standard_resolution").getString("url");
                    if (GlobalData.isFromHomeForChange)
                        GlobalData.isFromHomeAgain = true;

//                        Intent intent = new Intent(activity, CropImageActivity1.class);
//                        intent.putExtra(GlobalData.KEYNAME.SELECTED_IMAGE, al_image.get(position).getJSONObject("standard_resolution").getString("url"));
//                        activity.startActivity(intent);

                    Glide.with(activity).asBitmap()
                            .load(al_image.get(position).getJSONObject("standard_resolution").getString("url"))
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                /*int height = (int) (resource.getHeight() * (512.0 / resource.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(resource, 512, height, true);*/


                                    Share.bitmap = resource;
                                    if (AlbumImagesActivity.Companion.getActivity() != null)
                                        AlbumImagesActivity.Companion.getActivity().finish();

                                    if (FacebookAlbumPhotoActivity.activity != null)
                                        FacebookAlbumPhotoActivity.activity.finish();

                                    if (FaceActivity.getFaceActivity() != null)
                                        FaceActivity.getFaceActivity().finish();

                                    pd.dismiss();
                                    activity.finish();
                                    System.gc();

                                }

                            });


//                        GlobalData.is_social = false;
//                        activity.finish();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public InstagramImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return al_image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_album_image;
        TextView tv_album_name;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_album_image = itemView.findViewById(R.id.iv_album_image);
            tv_album_name = itemView.findViewById(R.id.tv_album_name);
        }
    }
}
