package com.mobile.cover.photo.editor.back.maker.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.PhoneAlbumAdapter;
import com.mobile.cover.photo.editor.back.maker.model.PhoneAlbum;
import com.mobile.cover.photo.editor.back.maker.model.PhonePhoto;

import java.util.Vector;

/**
 * Created by Harshad Kathiriya on 11/11/2016.
 */
public class PhotoFragment extends Fragment {
    private static final String TAG = PhotoFragment.class.getSimpleName();

    Vector<PhoneAlbum> phoneAlbums = new Vector<>();
    Vector<String> albumsNames = new Vector<>();
    private GridLayoutManager gridLayoutManager;
    private RecyclerView rcv_album;
    private LinearLayout iv_no_photo;

    private PhoneAlbumAdapter albumAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;

    public static PhotoFragment newInstance() {
        Bundle args = new Bundle();
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        initView(view);
        initViewAction();
        return view;
    }

    private void initView(View view) {
        rcv_album = view.findViewById(R.id.rcv_album);
        iv_no_photo = view.findViewById(R.id.iv_no_photo);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_album.setLayoutManager(gridLayoutManager);
        albumAdapter = new PhoneAlbumAdapter(getActivity(), phoneAlbums);
        rcv_album.setAdapter(albumAdapter);
    }

    private void initViewAction() {
        try {
            // which image properties are we querying
            String BUCKET_ORDER_BY = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
            String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
            // which image properties are we querying
            String[] projection = new String[]{
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID
            };

            // content: style URI for the "primary" external storage volume
            Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            // Make the query.
            Cursor cur = getActivity().getContentResolver().query(images,
                    projection, // Which columns to return
                    null,       // Which rows to return (all rows)
                    null,       // Selection arguments (none)
                    BUCKET_ORDER_BY         // Ordering
            );

            if (cur != null && cur.getCount() > 0) {
                Log.i("DeviceImageManager", " query count=" + cur.getCount());

                iv_no_photo.setVisibility(View.GONE);
                rcv_album.setVisibility(View.VISIBLE);

                if (cur.moveToFirst()) {
                    String bucketName;
                    String data;
                    String imageId;
                    int bucketNameColumn = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                    int imageUriColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);
                    int imageIdColumn = cur.getColumnIndex(MediaStore.Images.Media._ID);

                    do {
                        // Get the field values
                        bucketName = cur.getString(bucketNameColumn);
                        data = cur.getString(imageUriColumn);
                        imageId = cur.getString(imageIdColumn);

                        // Adding a new PhonePhoto object to phonePhotos vector
                        PhonePhoto phonePhoto = new PhonePhoto();
                        phonePhoto.setAlbumName(bucketName);
                        phonePhoto.setPhotoUri(data);
                        phonePhoto.setId(Integer.valueOf(imageId));

                        if (albumsNames.contains(bucketName)) {
                            for (PhoneAlbum album : phoneAlbums) {
                                if (album.getName().equals(bucketName)) {
                                    album.getAlbumPhotos().add(phonePhoto);
                                    Log.i("DeviceImageManager", "A photo was added to album => " + bucketName);
                                    break;
                                }
                            }
                        } else {
                            PhoneAlbum album = new PhoneAlbum();
                            Log.i("DeviceImageManager", "A new album was created => " + bucketName);
                            album.setId(phonePhoto.getId());
                            album.setName(bucketName);
                            album.setCoverUri(phonePhoto.getPhotoUri());
                            album.getAlbumPhotos().add(phonePhoto);
                            Log.i("DeviceImageManager", "A photo was added to album => " + bucketName);
                            phoneAlbums.add(album);
                            albumsNames.add(bucketName);
                        }
                        albumAdapter.notifyDataSetChanged();

                    } while (cur.moveToNext());
                }

                cur.close();
            } else {

                rcv_album.setVisibility(View.GONE);
                iv_no_photo.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
