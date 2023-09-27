package com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pagination.api.MovieApi;
import com.mobile.cover.photo.editor.back.maker.Pagination.api.MovieService;
import com.mobile.cover.photo.editor.back.maker.Pagination.model.Result;
import com.mobile.cover.photo.editor.back.maker.Pagination.model.TopRatedMovies;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.adapter.FrameListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    RecyclerView list_frame;
    FrameListAdapter adapter;
    List<Result> list = new ArrayList<com.mobile.cover.photo.editor.back.maker.Pagination.model.Result>();
    ProgressDialog pd;
    String TAG;
    private MovieService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list_frame = findViewById(R.id.list_frame);
        list_frame.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new FrameListAdapter(this, list);
        list_frame.setAdapter(adapter);
        movieService = MovieApi.getClient(Main2Activity.this).create(MovieService.class);
        loadFirstPage();
    }

    private void loadFirstPage() {

        pd = ProgressDialog.show(Main2Activity.this, "", getString(R.string.loading), true, false);


        callTopRatedMoviesApi().enqueue(new Callback<TopRatedMovies>() {
            @Override
            public void onResponse(Call<TopRatedMovies> call, Response<TopRatedMovies> response) {
                if (response.body().getSuccess()) {
                    Log.e(TAG, "onResponse: " + response.body().getMessage());
                    list.addAll(response.body().getResult());
                    adapter.notifyDataSetChanged();
                    pd.dismiss();

                } else {
                    pd.dismiss();

                    Log.e(TAG, "onResponse: " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<TopRatedMovies> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Main2Activity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
//                            progressBar.setVisibility(View.GONE);
                            loadFirstPage();
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Main2Activity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
//                            progressBar.setVisibility(View.GONE);
                            loadFirstPage();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    private Call<TopRatedMovies> callTopRatedMoviesApi() {
        return movieService.getTopRatedMovies_without(
                String.valueOf(Share.case_category_id), "", 1, SharedPrefs.getString(Main2Activity.this, SharedPrefs.country_code), "100"
        );
    }


}
