package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.AlbumImagesActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FaceActivity;
import com.mobile.cover.photo.editor.back.maker.model.PhoneAlbum;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by admin on 11/18/2016.
 */
public class PhoneAlbumAdapter extends RecyclerView.Adapter<PhoneAlbumAdapter.ViewHolder> {
    Context context;
    ArrayList<String> al_image = new ArrayList<>();
    private List<PhoneAlbum> al_album = new ArrayList<>();
    private DisplayImageOptions options;

    public PhoneAlbumAdapter(Context context, Vector<PhoneAlbum> al_album) {
        this.context = context;
        this.al_album = al_album;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.progress_animation)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public PhoneAlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhoneAlbumAdapter.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        ImageLoader.getInstance().displayImage("file:///" + al_album.get(position).getCoverUri(), holder.iv_album_image, options);
        holder.iv_album_image.getLayoutParams().height = SharedPrefs.getInt(context, SharedPrefs.SCREEN_HEIGHT) / 4;
        holder.tv_album_name.setText(al_album.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FaceActivity.is_closed = true;

                al_image.clear();
                Log.e("TAG", "Images====>" + al_album.get(position).getAlbumPhotos().size());
//                for (int i = 0; i < al_album.get(position).getAlbumPhotos().size(); i++) {
//                    al_image.add(al_album.get(position).getAlbumPhotos().get(i).getPhotoUri());
//                }

                Share.lst_album_image.clear();
                Log.e("TAG", "Images====>" + al_album.get(position).getAlbumPhotos().size());

                for (int i = 0; i < al_album.get(position).getAlbumPhotos().size(); i++) {
                    Share.lst_album_image.add(al_album.get(position).getAlbumPhotos().get(i).getPhotoUri());
                }
                Log.e("TAG", "acrtivity from1111:==>" + ((FaceActivity.getFaceActivity()).getIntent().hasExtra("activity")));

                if (GlobalData.isFromHomeForChange)
                    GlobalData.isFromHomeAgain = true;

                Intent intent = new Intent(context, AlbumImagesActivity.class);
//                intent.putStringArrayListExtra("image_list", al_image);
                intent.putExtra(GlobalData.KEYNAME.ALBUM_NAME, al_album.get(position).getName());
                if (((FaceActivity.getFaceActivity()).getIntent().hasExtra("activity"))) {
                    intent.putExtra("activity", "PhotoAlbum");
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return al_album.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_album_name;
        ImageView iv_album_image;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_album_name = itemView.findViewById(R.id.tv_album_name);
            iv_album_image = itemView.findViewById(R.id.iv_album_image);
        }
    }
}
