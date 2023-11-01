package com.mobile.cover.photo.editor.back.maker.Pagination;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pagination.api.MovieApi;
import com.mobile.cover.photo.editor.back.maker.Pagination.api.MovieService;
import com.mobile.cover.photo.editor.back.maker.Pagination.model.Result;
import com.mobile.cover.photo.editor.back.maker.Pagination.model.TopRatedMovies;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.mainapplication;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;
import com.mobile.cover.photo.editor.back.maker.testing_modules.ImageData;
import com.mobile.cover.photo.editor.back.maker.testing_modules.PhotoPickupImageActivity;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Mug_MainActivity;
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Shipper_MainActivity;
import com.mobile.cover.photo.editor.back.maker.utility.PathUtil;

import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements PaginationAdapterCallback, PaginationAdapter.setOnSubItemClickListener {

    private static final String TAG = "MainActivity";


    public static Activity activity;
    PaginationAdapter adapter;
    GridLayoutManager linearLayoutManager;
    RecyclerView rv;
    TextView title;
    ImageView id_back;
    androidx.appcompat.widget.Toolbar toolbar;
    ProgressDialog pd;
    NestedScrollView nsv;
    String orderby;
    List<Result> results = new ArrayList<>();
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage;
    private MovieService movieService;
    private androidx.appcompat.app.AlertDialog alertDialogBuilder;
    private boolean isEditable;

    private Intent tempIntent;
    private mainapplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        activity = MainActivity.this;
        alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        isEditable = getIntent().getBooleanExtra("isEditable", false);
        rv = findViewById(R.id.main_recycler);
        nsv = findViewById(R.id.nsv);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        btnRetry = findViewById(R.id.error_btn_retry);
//        txtError = findViewById(R.id.error_txt_cause);
        title = findViewById(R.id.title);
        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.gc();
            }
        });
        title.setText(Share.categoryname);

        Log.e("CHECKLIST", "onCreate: 1");
        initAdapter();
        linearLayoutManager = new GridLayoutManager(this, 3);
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(3, 21, true);
        rv.addItemDecoration(gridSpacingItemDecoration);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        nsv.setOnScrollChangeListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {

                if (TOTAL_PAGES > currentPage) {
                    isLoading = true;
                    Log.e(TAG, "loadMoreItems: " + currentPage);
                    currentPage += 1;

                    loadNextPage();
                }

            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        movieService = MovieApi.getClient(activity).create(MovieService.class);

        loadFirstPage();
    }

    private void initAdapter() {
        rv.getRecycledViewPool().clear();
        adapter = new PaginationAdapter(this, isEditable, this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickLister(View v, int position) {
                //  checkAndRequestPermissionsCamera(2);
            }
        });
        rv.setAdapter(adapter);
    }


    private boolean checkAndRequestPermissionsStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        }

    }

    private boolean checkAndRequestPermissionsCamera(int code) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA}, code);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("CHECKCOUNT", "onBackPressed: check count==>"+ getSupportFragmentManager().getBackStackEntryCount());
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permissions.length == 0) {
            return;
        }
        boolean allPermissionsGranted = true;
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
        }
        Log.e(TAG, "onRequestPermissionsResult: all permission-->" + allPermissionsGranted);
        if (!allPermissionsGranted) {
            boolean somePermissionsForeverDenied = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    //denied
                    Log.e("denied", permission);
                    if (requestCode == 1) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        } else {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
                        }
                    }

                } else {

                    if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                        //allowed
                        Log.e("allowed", permission);
//                        if (requestCode==2){
//                            Log.e("GRANTED", "checkAndRequestPermissionsStorage:=======> " );
//                        }

                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                    }
                }
            }
            if (somePermissionsForeverDenied) {

                if (requestCode == 1) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(getString(R.string.permission_required))
                            .setMessage(getString(R.string.permission_sentence))
                            .setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }
                if (requestCode == 2) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle(getString(R.string.permission_required))
                            .setMessage(getString(R.string.permission_sentence_storage))
                            .setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }

            }
        } else {
            switch (requestCode) {
                case 1:
                    break;
                default:
                    break;
            }
        }
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        Log.e("CHECKLIST", "onResume: list-->" + adapter.getItemCount());
        if (!checkAndRequestPermissionsStorage()) {
            alertDialogBuilder.setTitle(getString(R.string.permission_required));
            alertDialogBuilder.setMessage(getString(R.string.permission_sentence_storage));
            alertDialogBuilder.setButton(-1, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialogBuilder.setButton(-2, getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", getPackageName(), null));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            alertDialogBuilder.setCancelable(false);

            if (!alertDialogBuilder.isShowing()) {
                alertDialogBuilder.show();
            }
        }
    }*/

    private void loadFirstPage() {


        Log.e("CHECKLIST", "loadFirstPage: =====");

        // To ensure list is visible when retry button in error view is clicked
//        hideErrorView();
        pd = ProgressDialog.show(MainActivity.this, "", getString(R.string.loading), true, false);


        callTopRatedMoviesApi().enqueue(new Callback<TopRatedMovies>() {
            @Override
            public void onResponse(@NotNull Call<TopRatedMovies> call, @NotNull Response<TopRatedMovies> response) {
                // Got data. Send it to adapter

//                hideErrorView();
                if (response.isSuccessful()) {

                    if (response.body().getSuccess()) {
                        Log.e(TAG, "onResponse: " + response.body().getMessage());

                        pd.dismiss();
                        TOTAL_PAGES = response.body().getTotalPages();
                        currentPage = response.body().getPage();
                        Log.e(TAG, "onResponse: " + TOTAL_PAGES + "=======>" + currentPage);
                        results = fetchResults(response);
//                    progressBar.setVisibility(View.GONE);
                        adapter.addAll(results);
                        Log.e("CHECKLIST", "onResponse: adapter count-----" + adapter.getItemCount());
                        adapter.notifyDataSetChanged();
                        Log.e(TAG, "onResponse: " + (currentPage <= TOTAL_PAGES));
                        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                        else isLastPage = true;
                    } else {
                        pd.dismiss();

                        Log.e(TAG, "onResponse: " + response.body().getMessage());

//                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<TopRatedMovies> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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

    private List<Result> fetchResults(Response<TopRatedMovies> response) {
        TopRatedMovies topRatedMovies = response.body();
        return topRatedMovies.getResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            Toast.makeText(getApplicationContext(), getString(R.string.sort_by_popularity), Toast.LENGTH_LONG).show();
            orderby = "selling";
            results.clear();
            adapter.clear();
            currentPage = 1;
            isLastPage = false;
            Log.e("CHECKLIST", "onCreate: 2");
            initAdapter();
            loadFirstPage();
            return true;
        } else if (id == R.id.item2) {
            Toast.makeText(getApplicationContext(), getString(R.string.sort_by_newest_first), Toast.LENGTH_LONG).show();
            orderby = "created_at";
            Log.e(TAG, "onOptionsItemSelected: " + TOTAL_PAGES + "======>" + currentPage);
            results.clear();
            adapter.clear();
            isLastPage = false;
            currentPage = 1;
            Log.e("CHECKLIST", "onCreate: 3");
            initAdapter();
            loadFirstPage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNextPage() {

        Log.d(TAG, "loadNextPage: " + currentPage);
        pd = ProgressDialog.show(MainActivity.this, "", getString(R.string.loading), true, false);

        callTopRatedMoviesApi().enqueue(new Callback<TopRatedMovies>() {
            @Override
            public void onResponse(Call<TopRatedMovies> call, Response<TopRatedMovies> response) {
                Log.e(TAG, "onResponse: " + response.body().getMessage());
                if (response.body().getSuccess()) {
                    if (pd != null || pd.isShowing()) {
                        pd.dismiss();
                    }
                    adapter.removeLoadingFooter();
                    isLoading = false;
//                    progressBar.setVisibility(View.GONE);
                    TOTAL_PAGES = response.body().getTotalPages();
                    currentPage = response.body().getPage();
                    results = fetchResults(response);
                    adapter.addAll(results);
                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                } else {
                    pd.dismiss();
                    Log.e(TAG, "onResponse: " + response.body().getMessage());
//                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TopRatedMovies> call, Throwable t) {
                t.printStackTrace();
                pd.dismiss();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
//                            progressBar.setVisibility(View.GONE);
                            loadNextPage();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
//                            progressBar.setVisibility(View.GONE);
                            loadNextPage();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    private Call<TopRatedMovies> callTopRatedMoviesApi() {
        Log.e(TAG, "callTopRatedMoviesApi: ========>" + currentPage + "#####=================>" + Share.case_category_id);
        return movieService.getTopRatedMovies(
                "" + Share.case_category_id,
                orderby,
                currentPage, 1,
                SharedPrefs.getString(activity, SharedPrefs.country_code),
                "100"
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> temp = new ArrayList<>();
                this.application = mainapplication.getsInstance();
                application.getSelectedImages().clear();
                application.org_selectedImages.clear();
                application.temp_org_selectedImages.clear();
                application.selectedImages.clear();
                Log.e("SELECTEDDATA", "onActivityResult: share item num==>" + Share.itemnum);
                if (data.getClipData() != null) {
                    Log.e("SELECTEDDATA", "onActivityResult: image size==>" + data.getClipData().getItemCount());
                    for (int i = 0; i < Share.itemnum; i++) {
                        try {
                            String filePath = PathUtil.getPath(this, data.getClipData().getItemAt(i).getUri());
                            Log.e("SELECTEDDATA", "onActivityResult: file path-->" + filePath);
                            temp.add(filePath);
                        } catch (URISyntaxException e) {
                            Log.e("SELECTEDDATA", "onActivityResult: exception-->" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    Log.e("SELECTEDDATA", "onActivityResult: temp size-->" + temp.size());
                    for (int i = 0; i < temp.size(); i++) {
                        final ImageData imageData = new ImageData();
                        imageData.setTemp_imagePath(temp.get(i));
                        imageData.setImagePath(temp.get(i));
                        imageData.setTemp_org_imagePath(temp.get(i));
                        application.addSelectedImage(imageData);
                    }
                } else {
                    Log.e("SELECTEDDATA", "onActivityResult: image ==>" + data.getData());
                    try {
                        String filePath = PathUtil.getPath(this, data.getData());
                        Log.e("SELECTEDDATA", "onActivityResult: file path-->" + filePath);
                        temp.add(filePath);
                    } catch (URISyntaxException e) {
                        Log.e("SELECTEDDATA", "onActivityResult: exception-->" + e.getMessage());
                        e.printStackTrace();
                    }
                    for (int i = 0; i < temp.size(); i++) {
                        final ImageData imageData = new ImageData();
                        imageData.setTemp_imagePath(temp.get(i));
                        imageData.setImagePath(temp.get(i));
                        imageData.setTemp_org_imagePath(temp.get(i));
                        application.addSelectedImage(imageData);
                    }
                }

                redirectActivity();

            }
        }
    }

    private void redirectActivity() {

        model_details_data modelData = DataHelperKt.getModelData(this);
        if(tempIntent!=null && tempIntent.getStringExtra("from")!=null) {
            Log.e("SELECTEDDATA", "onClick: from=========>" + tempIntent.getStringExtra("from"));
            if (tempIntent.getStringExtra("from").equalsIgnoreCase("1")) {
                Intent intent = new Intent(this, com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.MainActivity.class);
                intent.putExtra("position", tempIntent.getStringExtra("position"));
                intent.putExtra("model_name", modelData.getModalName());
                intent.putExtra("model_id", "" + modelData.getModelId());
                intent.putExtra("user_id", SharedPrefs.getString(this, Share.key_ + RegReq.id));
                intent.putExtra("quantity", "1");
                intent.putExtra("total_amount", modelData.getPrice());
                intent.putExtra("paid_amount", modelData.getPrice());
                Share.mobile_type = modelData.getImageType();
                Log.e("TAG", "onPostExecute: " + Share.mobile_type);
                if (!modelData.getWidth().equalsIgnoreCase("")) {
                    intent.putExtra("width", modelData.getWidth());
                    intent.putExtra("height", modelData.getHeight());
                    Share.maskheight = Float.parseFloat(modelData.getHeight());
                    Share.maskwidth = Float.parseFloat(modelData.getWidth());
                    Log.e("width", "==>" + modelData.getWidth());
                    Log.e("height", "==>" + modelData.getHeight());
                }
                intent.putExtra("shipping", "0");
                startActivity(intent);
            }
        }
    }

    @Override
    public void itemClick(Intent intent, int itemnum) {
        Log.e("SELECTEDDATA", "itemClick: itemnum-->" + itemnum);
        startActivityForResult(intent, 111);
        Log.e("SELECTEDDATA", "onActivityResult: from -->" + intent.getStringExtra("from"));
        tempIntent = intent;
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f / spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
