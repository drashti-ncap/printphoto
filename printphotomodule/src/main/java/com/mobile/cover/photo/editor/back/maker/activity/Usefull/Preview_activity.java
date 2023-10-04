package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pagination.MainActivity;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.Cart;

import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.Commen.Share.upload;
import static com.mobile.cover.photo.editor.back.maker.activity.Usefull.CaseEditActivity.file_cover_bitmap;
import static com.mobile.cover.photo.editor.back.maker.activity.Usefull.CaseEditActivity.file_printphoto;
import static com.mobile.cover.photo.editor.back.maker.activity.Usefull.CaseEditActivity.preview_bitmap;
import static com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView.mStickers;

public class Preview_activity extends PrintPhotoBaseActivity implements View.OnClickListener {

    Button btn_submit;
    ImageView background, up, id_back;
  //  ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_activity);

        findviews();
        setDimension();
        initlistener();

        if (!getIntent().getStringExtra("model_id").equalsIgnoreCase("69")) {
            up.setImageResource(R.drawable.miytwo);
        } else if (getIntent().getStringExtra("model_id").equalsIgnoreCase("239")) {
            up.setImageResource(R.drawable.notesixpro);
        } else if (getIntent().getStringExtra("model_id").equalsIgnoreCase("228")) {
            up.setImageResource(R.drawable.vivoyninefive);
        } else if (getIntent().getStringExtra("model_id").equalsIgnoreCase("45")) {
            up.setImageResource(R.drawable.oppofone);
        } else if (getIntent().getStringExtra("model_id").equalsIgnoreCase("51")) {
            up.setImageResource(R.drawable.oppofseven);
        } else if (getIntent().getStringExtra("model_id").equalsIgnoreCase("80")) {
            up.setImageResource(R.drawable.vivovseven);
        } else if (getIntent().getStringExtra("model_id").equalsIgnoreCase("83")) {
            up.setImageResource(R.drawable.vivovnine);
        } else if (getIntent().getStringExtra("model_id").equalsIgnoreCase("208")) {
            up.setImageResource(R.drawable.vivoveleven);
        } else {
            up.setImageResource(R.drawable.note_pro);
        }
    }

    private void setDimension() {
        background.getLayoutParams().width = (int) Share.maskwidth * 200;
        background.getLayoutParams().height = (int) Share.maskheight * 200;
        up.getLayoutParams().width = (int) Share.maskwidth * 200;
        up.getLayoutParams().height = (int) Share.maskheight * 200;

        background.setImageBitmap(preview_bitmap);
    }

    private void findviews() {
        btn_submit = findViewById(R.id.btn_submit);
        background = findViewById(R.id.background);
        up = findViewById(R.id.up);
        id_back = findViewById(R.id.id_back);
    }

    private void initlistener() {
        btn_submit.setOnClickListener(this);
        id_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_submit) {
            senddata();
        }
        if (v == id_back) {
            finish();
        }
    }

    private void senddata() {
        //progress = ProgressDialog.show(Preview_activity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(Preview_activity.this);

        try {
            new crateReq().execute();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("EXP", "sendData: " + e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    public class crateReq extends AsyncTask<Void, Void, Void> {

        MultipartBody.Builder builder;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress.show();
            showProgressDialog(Preview_activity.this);
            builder = new MultipartBody.Builder();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (builder != null) {
                MultipartBody multipartBody = builder.build();
                APIService apiService = new MainApiClient(Preview_activity.this).getApiInterface();
                Call<Cart> cartCall = apiService.sendCart(multipartBody);
                cartCall.enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        if (response != null) {
                            if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                                upload = true;
                                mStickers.clear();
                                Share.resultbitmap = null;
                                Share.final_result_bitmap = null;
                                //progress.dismiss();
                                hideProgressDialog();
                                if (Default_image_activity.Companion.getActivity() != null) {
                                    Default_image_activity.Companion.getActivity().finish();
                                }
                                if (MainActivity.activity != null) {
                                    MainActivity.activity.finish();
                                }
                                if (CaseEditActivity.activity != null) {
                                    CaseEditActivity.activity.finish();
                                }
                                finish();
                                overridePendingTransition(R.anim.app_right_in, R.anim.app_left_out);
                            } else {
                                Log.e("FAILURE", "onResponse: " + response.body().getResponseMessage());
                                AlertDialog alertDialog = new AlertDialog.Builder(Preview_activity.this).create();
                                alertDialog.setTitle(getString(R.string.upload_fail));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage("Something went to wrong. Please try again Later");
                                alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //progress.dismiss();
                                        hideProgressDialog();
                                        dialog.dismiss();
                                    }
                                });

                                alertDialog.show();
                            }
                        }
                        Log.d("response", "==>" + response.toString());
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Log.d("response", "Failed==>" + t.toString());
//                        if (progress != null && progress.isShowing())
//                            progress.dismiss();
                        hideProgressDialog();

                        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(Preview_activity.this).create();
                            alertDialog.setTitle(getString(R.string.time_out));
                            alertDialog.setMessage(getString(R.string.connect_time_out));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    senddata();
                                }
                            });
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(Preview_activity.this).create();
                            alertDialog.setTitle(getString(R.string.internet_connection));
                            alertDialog.setMessage(getString(R.string.slow_connect));
                            alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    senddata();
                                }
                            });
                            alertDialog.show();
                        }
                    }
                });
            }

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("file_printphoto", "Size==>" + file_printphoto.length() / 1024);
            Log.d("file_cover_bitmap", "Size==>" + file_cover_bitmap.length() / 1024);

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("model_id", getIntent().getStringExtra("model_id"));
            builder.addFormDataPart("model_name", getIntent().getStringExtra("model_name"));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_printphoto);
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file_cover_bitmap);
            builder.addFormDataPart("print_image", file_printphoto.getName(), requestBody);
            builder.addFormDataPart("user_id", SharedPrefs.getString(Preview_activity.this, Share.key_ + RegReq.id));
            builder.addFormDataPart("case_image", file_cover_bitmap.getName(), requestBody1);
            builder.addFormDataPart("quantity", "1");
            builder.addFormDataPart("total_amount", getIntent().getStringExtra("total_amount"));
            builder.addFormDataPart("discount", "0");
            builder.addFormDataPart("paid_amount", getIntent().getStringExtra("paid_amount"));
            builder.addFormDataPart("type", "1");
            builder.addFormDataPart("product_price", getIntent().getStringExtra("total_amount"));
            builder.addFormDataPart("ln", Locale.getDefault().getLanguage());

            return null;
        }
    }


}
