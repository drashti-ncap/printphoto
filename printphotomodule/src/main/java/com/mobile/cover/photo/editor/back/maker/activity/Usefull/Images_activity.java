package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.default_final_image_adapter;
import com.mobile.cover.photo.editor.back.maker.mainapplication;
import com.mobile.cover.photo.editor.back.maker.model.get_images;
import com.mobile.cover.photo.editor.back.maker.model.temp_get_images;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Images_activity extends AppCompatActivity implements View.OnClickListener {

    public static Activity activity;
    RecyclerView rv_default_images;
    ImageView id_back;
    ProgressDialog pd;
    default_final_image_adapter default_image_adapter;
    TextView title;
    int offset = 0, limit = 9;
    int min = 0, max = 8, total;
    Button btn_load_more;
    private List<get_images> sqlist = new ArrayList<>();
    private List<temp_get_images> temp_sqlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_activity);
        activity = Images_activity.this;
        initview();
        Initlistener();
        calldata_message();
    }

    private void initview() {
        rv_default_images = findViewById(R.id.rv_default_images);
        btn_load_more = findViewById(R.id.btn_load_more);
        btn_load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        pd=new ProgressDialog(this,R.string.loading);
        rv_default_images.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int ItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                Log.e("POS", "onScrolled: " + ItemPosition);
                if (ItemPosition == max) {
                    min = max + 1;
                    max += 9;
                    if (total == max) {
                        max = max - 1;
                    }
                    Log.e("VALUE", "onClick: " + min + "====>" + max + "========>" + total);
                    if (total < max + 1) {
                        Toast.makeText(Images_activity.this, getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = min; i <= max; i++) {
                            temp_get_images temp_get_images = new temp_get_images("" + sqlist.get(i).getId()
                                    , "" + sqlist.get(i).getCategory_id()
                                    , "" + sqlist.get(i).getName()
                                    , "" + sqlist.get(i).getImage1()
                                    , "" + sqlist.get(i).getImage2()
                                    , "" + sqlist.get(i).getImage3()
                                    , "" + sqlist.get(i).getSelling());
                            temp_sqlist.add(temp_get_images);
                        }
                        default_image_adapter.notifyItemRangeChanged(min, max);
                    }
                }
            }
        });
        title = findViewById(R.id.title);
        title.setText(Share.categoryname);
        id_back = findViewById(R.id.id_back);
        default_image_adapter = new default_final_image_adapter(Images_activity.this, temp_sqlist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rv_default_images.setLayoutManager(mLayoutManager);
        rv_default_images.setItemAnimator(new DefaultItemAnimator());
        rv_default_images.setAdapter(default_image_adapter);
    }

    private void Initlistener() {
        id_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == id_back) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDisplaySize();
    }

    private void getDisplaySize() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void calldata_message() {
        sqlist.clear();
        temp_sqlist.clear();
        String url = "https://printphoto.in/Photo_case/api/case_images?search=" + Share.case_category_id + "&searchFields=category_id&country_code=" + SharedPrefs.getString(Images_activity.this, SharedPrefs.country_code) +
                "&user_id=" + SharedPrefs.getString(Images_activity.this, SharedPrefs.uid);
        Log.e("TAG", "calldata_notification: " + url);
        pd = ProgressDialog.show(Images_activity.this, "", getString(R.string.loading), true, false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", "onResponse: " + response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getBoolean("success")) {
                                if (json.getJSONArray("data").length() != 0) {
                                    for (int i = 0; i < json.getJSONArray("data").length(); i++) {
                                        JSONObject jsonObject = json.getJSONArray("data").getJSONObject(i);
                                        Log.e("MAINDATA", "onResponse: ======>" + jsonObject.getString("id"));
                                        Log.e("MAINDATA", "onResponse: ======>" + jsonObject.getString("category_id"));
                                        Log.e("MAINDATA", "onResponse: ======>" + jsonObject.getString("name"));
                                        Log.e("MAINDATA", "onResponse: ======>" + jsonObject.getString("image1"));
                                        Log.e("MAINDATA", "onResponse: ======>" + jsonObject.getString("image2"));
                                        Log.e("MAINDATA", "onResponse: ======>" + jsonObject.getString("image3"));
                                        Log.e("MAINDATA", "onResponse: ======>" + jsonObject.getString("selling"));
                                        get_images get_images = new get_images("" + jsonObject.getString("id"), "" + jsonObject.getString("category_id"), "" + jsonObject.getString("name"),
                                                "" + jsonObject.getString("image1"), "" + jsonObject.getString("image2"), "" + jsonObject.getString("image3"), "" + jsonObject.getString("selling"));
                                        sqlist.add(get_images);
                                    }
                                    total = sqlist.size();
                                    Log.e("TOTAL", "onResponse: " + total);
                                    for (int i = min; i <= max; i++) {
                                        temp_get_images temp_get_images = new temp_get_images("" + sqlist.get(i).getId()
                                                , "" + sqlist.get(i).getCategory_id()
                                                , "" + sqlist.get(i).getName()
                                                , "" + sqlist.get(i).getImage1()
                                                , "" + sqlist.get(i).getImage2()
                                                , "" + sqlist.get(i).getImage3()
                                                , "" + sqlist.get(i).getSelling());
                                        temp_sqlist.add(temp_get_images);
                                    }
                                    default_image_adapter.notifyDataSetChanged();


                                    Log.e("SIZE", "onResponse: " + limit + "======>" + offset);

                                    pd.dismiss();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", "Message", e);
                            pd.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            pd.dismiss();
                            Log.e("Error", "Message", error);
                            pd.dismiss();
                        }
                    }
                }
        );
        mainapplication.getsInstance().getRequestQueue().add(stringRequest).setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sqlist.clear();
        temp_sqlist.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
