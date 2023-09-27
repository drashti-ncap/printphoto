package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FaceActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FacebookAlbumPhotoActivity;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by abc on 10/7/2016.
 */
public class FacebookAlbumAdapter extends RecyclerView.Adapter<FacebookAlbumAdapter.ViewHolder> {
    private static final String TAG = FacebookAlbumAdapter.class.getSimpleName();
    private List<JSONObject> itemList;
    private Context context;

    public FacebookAlbumAdapter(Context context, List<JSONObject> objectList) {
        this.context = context;
        this.itemList = objectList;
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facebook_album, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            holder.setIsRecyclable(false);

            final JSONObject row = itemList.get(position);
            /*{"picture":
                {"data":
                    {"is_silhouette":false,"url":""}
                },
                "id":"358478261161915",
                "name":"Foods"
            }*/
            holder.iv_album_img.getLayoutParams().height = SharedPrefs.getInt(context, SharedPrefs.SCREEN_WIDTH) / 3;
            String Urls = row.getJSONObject("picture").getJSONObject("data").getString("url");
            holder.tv_name_album.setText("" + row.getString("name"));
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + row.getString("id") + "/picture?redirect=false",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            try {
                                JSONObject photos = response.getJSONObject();
                                Log.e(TAG, "photos JSONObject : " + response.toString());
                                String Urls = photos.getJSONObject("data").getString("url");
                                holder.iv_album_img.setImageURI(Uri.parse(Urls));
                                holder.iv_album_img.getLayoutParams().height = SharedPrefs.getInt(context, SharedPrefs.SCREEN_WIDTH) / 3;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).executeAsync();

            holder.iv_album_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    FaceActivity.is_closed = true;

                    try {

                        if (GlobalData.isFromHomeForChange)
                            GlobalData.isFromHomeAgain = true;

                        Intent albumPhotoIntent = new Intent(context, FacebookAlbumPhotoActivity.class);
                        albumPhotoIntent.putExtra(GlobalData.KEYNAME.ALBUM_ID, "" + row.getString("id"));
                        albumPhotoIntent.putExtra(GlobalData.KEYNAME.ALBUM_NAME, "" + row.getString("name"));
                        if (((FaceActivity.getFaceActivity()).getIntent().hasExtra("activity"))) {
                            albumPhotoIntent.putExtra("activity", "AlbumPhoto");
                        }
                        context.startActivity(albumPhotoIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            holder.tv_name_album.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.iv_album_img.performClick();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_album_img;
        TextView tv_name_album;

        public ViewHolder(View view) {
            super(view);
            iv_album_img = view.findViewById(R.id.iv_album_img);
            tv_name_album = view.findViewById(R.id.tv_name_album);
        }

    }


}
