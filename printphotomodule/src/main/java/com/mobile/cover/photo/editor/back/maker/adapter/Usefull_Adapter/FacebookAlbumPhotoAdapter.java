package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.AlbumImagesActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.CaseEditActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FaceActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FacebookAlbumPhotoActivity;
import com.mobile.cover.photo.editor.back.maker.customView.recycleview.CustomRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by abc on 10/7/2016.
 */
public class FacebookAlbumPhotoAdapter extends CustomRecyclerViewAdapter {
    private static final String TAG = FacebookAlbumPhotoAdapter.class.getSimpleName();
    ProgressDialog pd;
    private Activity activity;
    private List<JSONObject> itemList;
    private int screenWidth;

    public FacebookAlbumPhotoAdapter(Activity activity, List<JSONObject> images) {
        this.activity = activity;
        this.itemList = images;

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    @Override
    public CustomRecyclerViewAdapter.CustomRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_facebook_album_photo, parent, false);
        Holder dataObjectHolder = new Holder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecycleViewHolder holder, final int position) {
        try {
            final Holder myHolder = (Holder) holder;
//            holder.setIsRecyclable(false);
//            {"picture":"","height":388,"width":620,"id":"358478297828578"}
            JSONObject row = itemList.get(position);

            String Urls = row.getString("picture");
//            Log.e(TAG,"JSONObject : "+row.toString());

            JSONArray images = row.getJSONArray("images");
            JSONObject image_row;

            int selpos = 0, imgwidth = 0;
            for (int index = 0; index < images.length(); index++) {
                image_row = images.getJSONObject(index);
                if (Integer.parseInt(image_row.getString("width")) > imgwidth) {
                    imgwidth = Integer.parseInt(image_row.getString("width"));
                    selpos = index;
                }
            }
            image_row = images.getJSONObject(selpos);
            final String SendUrls = image_row.getString("source");

            myHolder.iv_album_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pd = ProgressDialog.show(activity, "", activity.getResources().getString(R.string.loading), true, false);
                    try {

//                        Log.e("hasActivity", "--> " + activity.getIntent().hasExtra("activity"));

                        if (activity.getIntent().hasExtra("activity")) {
                            FaceActivity.getFaceActivity().finish();

                            Intent intent = new Intent(activity, CaseEditActivity.class);
                            GlobalData.modeBackground = GlobalData.MODE_BACKGROUND.CAMETA;

                            //TODO uncomment
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            GlobalData.is_social = true;
                            activity.startActivity(intent);
                            activity.finish();

                        } else {
//                            FaceActivity.getFaceActivity().finish();

                            if (GlobalData.isFromHomeForChange)
                                GlobalData.isFromHomeAgain = true;

                            Glide.with(activity).asBitmap()
                                    .load(SendUrls)
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

//                            Intent selectedPhotoIntent = new Intent(activity, CaseEditActivity.class);
//                            selectedPhotoIntent.putExtra(GlobalData.KEYNAME.SELECTED_IMAGE, "" + SendUrls);
//
//                            GlobalData.imageUrl = SendUrls;
//
//                            //TODO uncomment
////                            selectedPhotoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                            selectedPhotoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                            GlobalData.is_social = false;
//                            activity.startActivity(selectedPhotoIntent);

//                            activity.finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            myHolder.iv_album_img.setImageURI(Uri.parse(Urls));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

    public class Holder extends CustomRecycleViewHolder {
        private SimpleDraweeView iv_album_img;

        public Holder(View itemView) {
            super(itemView);
            iv_album_img = itemView.findViewById(R.id.iv_album_img);
        }
    }
}
