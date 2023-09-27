package com.mobile.cover.photo.editor.back.maker.testing_modules;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.mainapplication;

import java.io.File;


public class ImageByAlbumAdapter extends RecyclerView.Adapter<ImageByAlbumAdapter.Holder> {
    private mainapplication application = mainapplication.getsInstance();
    private OnItemClickListner<Object> clickListner;
    private RequestManager glide;
    private LayoutInflater inflater;
    private Context context;

    public ImageByAlbumAdapter(Context activity) {
        this.context = activity;
        this.inflater = LayoutInflater.from(activity);
        this.glide = Glide.with(activity);
    }

    public void setOnItemClickListner(OnItemClickListner<Object> clickListner) {
        this.clickListner = clickListner;
    }

    public int getItemCount() {
        return this.application.getImageByAlbum(this.application.getSelectedFolderId()).size();
    }

    public ImageData getItem(int pos) {
        return this.application.getImageByAlbum(this.application.getSelectedFolderId()).get(pos);
    }

    public void deleteItm(int pos) {
        application.getImageByAlbum(this.application.getSelectedFolderId()).remove(pos);
    }

    public void onBindViewHolder(final Holder holder, final int pos) {
        CharSequence charSequence;
        int i;
        final ImageData data = getItem(pos);

        final File file = new File(data.temp_imagePath);
        if (!file.exists()) {
//            deleteItm(pos);
//            notifyDataSetChanged();
        }


        holder.ivDone.setSelected(true);
        if (data.imageCount == 0) {
            charSequence = "";
        } else {
            charSequence = String.format("%02d", Integer.valueOf(data.imageCount));
        }
        this.glide.load(data.temp_imagePath).into(holder.imageView);

        boolean isSelectde = true;

        for (int j = 0; j < application.getSelectedImages().size(); j++) {
            Log.i("8992", "onBindViewHolder: " + application.getSelectedImages().get(j).imagePath);
        }


//        if (data.isSelected()) {
//            holder.ivDone.setVisibility(View.VISIBLE);
//        } else {
//            holder.ivDone.setVisibility(View.GONE);
//        }
        holder.ivDone.setVisibility(View.GONE);
        for (int j = 0; j < application.getSelectedImages().size(); j++) {
            if (application.getSelectedImages().get(j).imagePath.equalsIgnoreCase(data.imagePath)) {
                holder.ivDone.setVisibility(View.VISIBLE);
            }
        }


        holder.clickableView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {


                Log.e("tempselection", "------size----" + application.getSelectedImages().size());

                if (application.getSelectedImages().size() < Share.itemnum) {

                    if (file.exists()) {
                        if (holder.imageView.getDrawable() == null) {
                            Toast.makeText(ImageByAlbumAdapter.this.application, application.getString(R.string.image_corrupt_or_not_support), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        final ImageData data2 = getItem(pos);
                        data2.setSelected(true);

                        mainapplication.tempSelection.add(data.temp_imagePath);

                        application.addSelectedImage(data2);
                        notifyItemChanged(pos);


                        if (ImageByAlbumAdapter.this.clickListner != null) {
                            ImageByAlbumAdapter.this.clickListner.onItemClick(v, data2);
                        }
                    } else {
                        Toast.makeText(application, application.getString(R.string.image_not_exist),
                                Toast.LENGTH_SHORT).show();
                    }


//                    if (data.imageCount == 0) {
//
//                        application.addSelectedImage(data);
//                        notifyItemChanged(pos);
//
//                        if (ImageByAlbumAdapter.this.clickListner != null) {
//                            ImageByAlbumAdapter.this.clickListner.onItemClick(v, data);
//                        }
//
//                    } else {
//                        Toast.makeText(context, "Already selected", Toast.LENGTH_SHORT).show();
//                    }

                } else {
                    Toast.makeText(context, application.getString(R.string.max) + " " + Share.itemnum + " " + application.getString(R.string.images_can_be_select),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

//        try {
//            File file = new File(data.temp_imagePath);
//            if (!file.exists()) {
//                application.getImageByAlbum(application.getSelectedFolderId()).remove(pos);
//                notifyDataSetChanged();
//            }
//        }catch (Exception e){}
    }

    public Holder onCreateViewHolder(ViewGroup parent, int pos) {
        return new Holder(this.inflater.inflate(R.layout.items_by_folder, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class Holder extends RecyclerView.ViewHolder {
        View clickableView;
        ImageView imageView, ivDone;
        View parent;
//        TextView textView;

        public Holder(View v) {
            super(v);
            this.parent = v;
            this.imageView = v.findViewById(R.id.imageView1);
            ivDone = v.findViewById(R.id.ivDone);
//            this.textView = (TextView) v.findViewById(R.id.textView1);
            this.clickableView = v.findViewById(R.id.clickableView);
        }

        public void onItemClick(View view, Object item) {
            if (ImageByAlbumAdapter.this.clickListner != null) {
                ImageByAlbumAdapter.this.clickListner.onItemClick(view, item);
            }
        }
    }


}
