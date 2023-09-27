package com.mobile.cover.photo.editor.back.maker.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.NetworkManager;
import com.mobile.cover.photo.editor.back.maker.Instagram.Instagram;
import com.mobile.cover.photo.editor.back.maker.Instagram.InstagramRequest;
import com.mobile.cover.photo.editor.back.maker.Instagram.InstagramSession;
import com.mobile.cover.photo.editor.back.maker.Instagram.InstagramUser;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.InstagramImageAdapter;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harshad Kathiriya on 11/11/2016.
 */
public class InstagramFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {

    private static final String TAG = InstagramFragment.class.getSimpleName();
    private InstagramSession mInstagramSession;
    private Instagram mInstagram;

//    private static final String REDIRECT_URI = "http://admin.vasundharavision.com/art_work/loader.html";

    private InstagramUser instagramUser;
    private RecyclerView rcv_images;
    private GridLayoutManager gridLayoutManager;
    private ImageView btn_logout;
    private Button btn_insta_login;
    private TextView tv_no_pic;

    private InstagramImageAdapter imageAdapter;
    private ArrayList<JSONObject> al_image = new ArrayList<>();
    private SwipyRefreshLayout swipyRefreshLayout;
    private String next_max_id = "";
    private boolean is_refresh = false;

    private RelativeLayout rl_data, rl_instagram, rl_no_data;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Instagram.InstagramAuthListener mAuthListener = new Instagram.InstagramAuthListener() {
        @Override
        public void onSuccess(InstagramUser user) {

            instagramUser = user;
            GlobalData.insta_access_token = mInstagramSession.getAccessToken();
            GlobalData.insta_id = user.id;
            new DownloadTask().execute(GlobalData.insta_access_token, GlobalData.insta_id);
        }

        @Override
        public void onError(String error) {
            showToast("Please, check your internet connection");
        }

        @Override
        public void onCancel() {
            showToast("OK. Maybe later?");
        }
    };

    public static InstagramFragment newInstance() {
        Bundle args = new Bundle();
        InstagramFragment fragment = new InstagramFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instagram, container, false);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        if (GlobalData.RestartApp(getActivity())) {
            try {
                initView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    private void showToast(String text) {
        try {
            Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(View view) {
        rl_data = view.findViewById(R.id.rl_data);
        rl_instagram = view.findViewById(R.id.rl_instagram);
        rl_no_data = view.findViewById(R.id.rl_no_data);
        tv_no_pic = view.findViewById(R.id.tv_no_pic);

        btn_logout = view.findViewById(R.id.btn_logout);
        btn_insta_login = view.findViewById(R.id.btn_insta_login);

        swipyRefreshLayout = view.findViewById(R.id.swipyrefreshlayout);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_images = view.findViewById(R.id.rcv_images);
        rcv_images.setLayoutManager(gridLayoutManager);

        imageAdapter = new InstagramImageAdapter(getActivity(), al_image);
        rcv_images.setAdapter(imageAdapter);

        swipyRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInstagramSession == null) {
                    mInstagram = new Instagram(getActivity(), getResources().getString(R.string.instagram_client_id), getResources().getString(R.string.instagram_secret), getResources().getString(R.string.instagram_redirect));
                    mInstagramSession = mInstagram.getSession();
                }
                mInstagramSession.reset();

                GlobalData.insta_access_token = null;
                GlobalData.insta_id = null;

                rl_instagram.setVisibility(View.GONE);
                btn_insta_login.setVisibility(View.VISIBLE);
            }
        });

        btn_insta_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInstagram = new Instagram(getActivity(), getResources().getString(R.string.instagram_client_id), getResources().getString(R.string.instagram_secret), getResources().getString(R.string.instagram_redirect));
                mInstagramSession = mInstagram.getSession();
                mInstagram.authorize(mAuthListener);
            }
        });


        Log.e(TAG, "insta_id : " + GlobalData.insta_id);
        Log.e(TAG, "insta_access_token : " + GlobalData.insta_access_token);

        if (GlobalData.insta_id != null || GlobalData.insta_access_token != null) {
           /* mInstagram = new Instagram(getActivity(),getResources().getString(R.string.instagram_client_id),getResources().getString(R.string.instagram_secret),getResources().getString(R.string.instagram_redirect));
            mInstagramSession = mInstagram.getSession();
            mInstagram.authorize(mAuthListener);*/

            if (NetworkManager.hasInternetConnected(getActivity())) {

                new DownloadTask().execute(GlobalData.insta_access_token, GlobalData.insta_id);
                swipyRefreshLayout.setOnRefreshListener(this);

                showImgData();
            } else {
                hideImgData("");
            }

        } else if (mInstagramSession != null && mInstagramSession.isActive()) {

            if (NetworkManager.hasInternetConnected(getActivity())) {

                instagramUser = mInstagramSession.getUser();
                GlobalData.insta_id = instagramUser.id;
                GlobalData.insta_access_token = mInstagramSession.getAccessToken();

                new DownloadTask().execute(GlobalData.insta_access_token, GlobalData.insta_id);
                swipyRefreshLayout.setOnRefreshListener(this);

                showImgData();
            } else {
                hideImgData("");
            }
        }
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (is_refresh == true) {
            new DownloadTask().execute(GlobalData.insta_access_token, GlobalData.insta_id);
        } else {
            swipyRefreshLayout.setRefreshing(false);
        }
    }

    private void showImgData() {
        rl_data.setVisibility(View.VISIBLE);
        rl_instagram.setVisibility(View.VISIBLE);
        btn_insta_login.setVisibility(View.GONE);
        rl_no_data.setVisibility(View.GONE);
        btn_logout.setVisibility(View.VISIBLE);
    }

    private void hideImgData(String text) {
        rl_data.setVisibility(View.VISIBLE);
        rl_instagram.setVisibility(View.GONE);
        btn_insta_login.setVisibility(View.GONE);
        rl_no_data.setVisibility(View.VISIBLE);
        btn_logout.setVisibility(View.GONE);

        if (text.equals("")) {
            tv_no_pic.setText("Please, check your internet connection!");
        } else {
            tv_no_pic.setText(text);
        }
    }

    public class DownloadTask extends AsyncTask<String, Integer, Long> {
        ProgressDialog pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(String... strings) {
            long result = 0;

            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>(1);

                if (!next_max_id.isEmpty()) {
                    params.add(new BasicNameValuePair("max_id", next_max_id));
                }
                /*InstagramRequest request = new InstagramRequest(mInstagramSession.getAccessToken());
                String response = request.createRequest("GET", "/users/" + instagramUser.id + "/media/recent", params);*/

                InstagramRequest request = new InstagramRequest(strings[0]);
                Log.e("REQUEST", "doInBackground: " + request);
                String response = request.createRequest("GET", "/users/" + strings[1] + "/media/recent", params);
                Log.e("RESPONSE", "doInBackground: " + response);

                if (!response.equals("")) {
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                    JSONArray jsonData = jsonObj.getJSONArray("data");
                    JSONObject jsonObject = jsonObj.getJSONObject("pagination");
                    if (jsonObject.has("next_max_id")) {
                        next_max_id = jsonObject.getString("next_max_id");
                        is_refresh = true;
                    } else {
                        is_refresh = false;
                    }
                    int length = jsonData.length();
                    if (length > 0) {

                        for (int i = 0; i < length; i++) {
                            JSONObject jsonPhoto = jsonData.getJSONObject(i).getJSONObject("images");
                            al_image.add(jsonPhoto);
                        }
                    }
                }
            } catch (Exception e) {
                pd.cancel();
                e.printStackTrace();
                hideImgData("");
            }

            return result;
        }

        protected void onPostExecute(Long result) {
            pd.cancel();
            try {
                if (al_image == null) {
                    hideImgData("No Photos Available.");
                } else {

                    showImgData();

                    Log.e(TAG, "al_image :-> " + al_image.size());
                    imageAdapter.notifyDataSetChanged();
                    if (is_refresh == false) {
//                        rcv_images.smoothScrollToPosition(al_image.size() - 1);
                        swipyRefreshLayout.setEnabled(false);
                    }
                    swipyRefreshLayout.setRefreshing(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                hideImgData("");
            }
        }
    }
}
