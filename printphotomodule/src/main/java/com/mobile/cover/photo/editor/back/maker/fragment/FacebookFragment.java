package com.mobile.cover.photo.editor.back.maker.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.NetworkManager;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.FacebookAlbumAdapter;
import com.mobile.cover.photo.editor.back.maker.mainapplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Harshad Kathiriya on 11/11/2016.
 */
public class FacebookFragment extends Fragment {
    private static final String TAG = FacebookFragment.class.getSimpleName();

    private LoginButton loginButton;
    private RelativeLayout rl_data, rl_facebook_albumlist, rl_no_data;
    private TextView tv_no_internet;
    private ImageView btn_logout;
    private RecyclerView rv_album;
    private LinearLayoutManager linearLayoutManager;


    private FacebookAlbumAdapter albumAdapter;
    private List<JSONObject> albumList = new ArrayList<JSONObject>();
    private FirebaseAnalytics mFirebaseAnalytics;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            loginButton.setVisibility(View.GONE);
            ((mainapplication) getApplicationContext()).getAccessTokenTracker().startTracking();
            ((mainapplication) getApplicationContext()).getProfileTracker().startTracking();

            Log.e(TAG, "--> " + loginResult.getAccessToken().getUserId());

            if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null) {
                Log.e(TAG, "getCurrentProfile" + com.facebook.Profile.getCurrentProfile().getId());
                if (com.facebook.Profile.getCurrentProfile().getId() != null)
                    SharedPrefs.save(getActivity(), SharedPrefs.ProfileId, com.facebook.Profile.getCurrentProfile().getId());
                loadAlbumList();
            } else {
                Log.e(TAG, "callback not call");

                if (loginResult.getAccessToken().getUserId() != null && loginResult.getAccessToken().getToken() != null) {
                    SharedPrefs.save(getActivity(), SharedPrefs.ProfileId, loginResult.getAccessToken().getUserId());
                    SharedPrefs.save(getActivity(), SharedPrefs.AccessToken, loginResult.getAccessToken().getToken());
                    loadAlbumList();
                } else {
                    hideImgData("Something went wrong while connecting to facebook!");
                }
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Facebook login cancelled!", Toast.LENGTH_SHORT).show();

            if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null) {
                LoginManager.getInstance().logOut();
            }
        }

        @Override
        public void onError(FacebookException e) {

            e.printStackTrace();
            Toast.makeText(getActivity(), "Please, check your internet connection!", Toast.LENGTH_LONG).show();

            if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null) {
                LoginManager.getInstance().logOut();
            }
        }
    };

    public static FacebookFragment newInstance() {
        Bundle args = new Bundle();
        FacebookFragment fragment = new FacebookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        if (GlobalData.RestartApp(getActivity())) {
            initView(view);
            initViewAction();
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((mainapplication) getApplicationContext()).getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (GlobalData.RestartApp(getActivity())) {
            if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null) {
                Log.e(TAG, "getCurrentProfile" + com.facebook.Profile.getCurrentProfile().getId());
                if (com.facebook.Profile.getCurrentProfile().getId() != null)
                    SharedPrefs.save(getActivity(), SharedPrefs.ProfileId, com.facebook.Profile.getCurrentProfile().getId());
                loadAlbumList();
            /*Intent albumIntent = new Intent(getActivity(), AlbumsActivity.class);
            getActivity().startActivity(albumIntent);*/
            }
        }
    }

    private void initView(View view) {
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends,user_photos,publish_actions");
        loginButton.setFragment(this);
        loginButton.registerCallback(((mainapplication) getApplicationContext()).getCallbackManager(), callback);

        rl_facebook_albumlist = view.findViewById(R.id.rl_facebook_albumlist);
        rl_data = view.findViewById(R.id.rl_data);
        rl_no_data = view.findViewById(R.id.rl_no_data);
        tv_no_internet = view.findViewById(R.id.tv_no_internet);
        rv_album = view.findViewById(R.id.rv_album);
        btn_logout = view.findViewById(R.id.btn_logout);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_album.setLayoutManager(linearLayoutManager);

    }

    private void initViewAction() {
        try {
            if (AccessToken.getCurrentAccessToken() != null) {
                if (NetworkManager.hasInternetConnected(getActivity()))
                    showImgData();
                else
                    hideImgData("");
            }

            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null) {
                        FacebookSdk.sdkInitialize(getApplicationContext());

                        if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null) {
                            LoginManager.getInstance().logOut();
                        }

                        SharedPrefs.save(getActivity(), SharedPrefs.ProfileId, "");
                        SharedPrefs.save(getActivity(), SharedPrefs.AccessToken, "");

                        AccessToken.setCurrentAccessToken(null);
                        hideImgData("");
                        rl_data.setVisibility(View.GONE);
                        loginButton.setVisibility(View.VISIBLE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAlbumList() {
        try {
            Log.e(TAG, "ProfileId : " + SharedPrefs.getString(getActivity(), SharedPrefs.ProfileId));

            final ProgressDialog pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);

            new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + SharedPrefs.getString(getActivity(), SharedPrefs.ProfileId) + "/albums/?fields=picture,id,name,type=normal",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            Log.e(TAG, "GraphResponse : " + response.toString());
                            try {
//                                Log.e(TAG,"data_response : "+data_response.toString());
                                Log.e(TAG, "getErrorCode : " + response.getError());

                                if (response.getError() == null) {
                                    JSONObject data_response = response.getJSONObject();
                                    JSONArray data = data_response.getJSONArray("data");
                                    Log.d(TAG, "data : " + data.get(0).toString());
                                    albumList.clear();

                                    for (int index = 0; index < data.length(); index++) {
                                        albumList.add(data.getJSONObject(index));
                                    }

                                    showImgData();
                                    albumAdapter = new FacebookAlbumAdapter(getActivity(), albumList);
                                    rv_album.setAdapter(albumAdapter);

                                } else {
                                    Log.e(TAG, "getErrorCode : " + response.getError());
//                                    Toast.makeText(getActivity(), "Something went wrong! Please, try again.", Toast.LENGTH_LONG).show();
                                    hideImgData("");
                                }
                            } catch (Exception e) {
                                pd.cancel();
                                e.printStackTrace();
//                                Toast.makeText(getActivity(), "Something went wrong! Please, try again.", Toast.LENGTH_LONG).show();
                                hideImgData("");
                            }
                            pd.cancel();
                        }
                    }
            ).executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(getActivity(), "Something went wrong! Please, try again.", Toast.LENGTH_LONG).show();
            hideImgData("");
        }
    }

    private void showImgData() {
        rl_data.setVisibility(View.VISIBLE);
        rl_facebook_albumlist.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
        rl_no_data.setVisibility(View.GONE);
        btn_logout.setVisibility(View.VISIBLE);
    }

    private void hideImgData(String text) {
        rl_facebook_albumlist.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        rl_data.setVisibility(View.VISIBLE);
        rl_no_data.setVisibility(View.VISIBLE);
        btn_logout.setVisibility(View.GONE);

        if (text.equals(""))
            tv_no_internet.setText("Please, check your internet connection!");
        else
            tv_no_internet.setText(text);
    }


}
