package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.complain_feedback_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.utility.PathUtil;
import com.mvc.imagepicker.ImagePicker;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Feedback_activity extends AppCompatActivity {

    LinearLayout rl_enter_complain, ll_select_image;
    Spinner sp_subject;
    EditText ed_Complain;
    TextView image_name, ll_send_complain;
    Button btn_browse;
    Uri uri;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    ProgressDialog pd;
    File file;
    String path, type = "Phone Case";
    MultipartBody.Part image;
    ImageView id_back, send_feedback;
    private int PICK_IMAGE_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        findviews();
    }

    private void findviews() {
        rl_enter_complain = findViewById(R.id.rl_enter_complain);
        btn_browse = findViewById(R.id.btn_browse);
        ll_send_complain = findViewById(R.id.ll_send_complain);
        ll_select_image = findViewById(R.id.ll_select_image);

        btn_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CHECKGALLERY", "openGallery: 5" );
                if (checkAndRequestPermissionsStorage(2)) {
                    Share.feedbackactivity = true;
                    Intent i = new Intent(Feedback_activity.this, FaceActivity.class);
                    i.putExtra("activity", "HomeActivity");
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                }
                //selectImage();
            }
        });
        ll_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermissionsStorage(2)) {
                    Share.feedbackactivity = true;
                    Intent i = new Intent(Feedback_activity.this, FaceActivity.class);
                    i.putExtra("activity", "HomeActivity");
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                }
              //  selectImage();
            }
        });
        sp_subject = findViewById(R.id.sp_subject);
        id_back = findViewById(R.id.id_back);
        send_feedback = findViewById(R.id.send_feedback);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        spinnerArray.clear();
        spinnerArray.addAll(Share.maincategoryname);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, spinnerArray);
        sp_subject.setAdapter(adapter);
        type = sp_subject.getSelectedItem().toString();
        ed_Complain = findViewById(R.id.ed_Complain);
        image_name = findViewById(R.id.image_name);

        ll_send_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_Complain.getText().toString().trim().equalsIgnoreCase("") || ed_Complain.getText().toString().trim().equalsIgnoreCase(" ")) {
                    Toast.makeText(Feedback_activity.this, getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
                } else {
                    sendcomplain(type + " : " + ed_Complain.getText().toString());
                }
            }
        });

        send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_Complain.getText().toString().equalsIgnoreCase("") || ed_Complain.getText().toString().equalsIgnoreCase(" ")) {
                    Toast.makeText(Feedback_activity.this, getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
                } else {
                    sendcomplain(type + " : " + ed_Complain.getText().toString());
                }
            }
        });


    }

    private void selectImage() {
        Log.e("CHECKDIALOG", "selectImage: dialog --5--" );
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_gallery),
                getString(R.string.cancel)};
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Feedback_activity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    if (checkAndRequestPermissionsCamera(1)) {
                        ImagePicker.pickImage(Feedback_activity.this, "Select your image:");
                    }
                } else if (items[item].equals(getString(R.string.choose_from_gallery))) {
                    if (checkAndRequestPermissionsStorage(2)) {
                        Share.feedbackactivity = true;
                        Intent i = new Intent(Feedback_activity.this, FaceActivity.class);
                        i.putExtra("activity", "HomeActivity");
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            image_name.setText(data.getData().getPath());
//            Glide.with(Feedback_activity.this).load(uri).into(imageView);
        } else {
            Uri captureUri = ImagePicker.getImageFromResult(Feedback_activity.this, requestCode, resultCode, data);
            if (captureUri != null) {
                uri = captureUri;
                image_name.setText(captureUri.toString());
                Log.e("TAG-->", "captureUri  " + captureUri.getPath());
            }
        }
    }


    private boolean checkAndRequestPermissionsCamera(int code) {

        if (ContextCompat.checkSelfPermission(Feedback_activity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Feedback_activity.this, new String[]{android.Manifest.permission.CAMERA},
                    code);
            return false;
        } else {
            return true;
        }
    }


    private boolean checkAndRequestPermissionsStorage(int code) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(Feedback_activity.this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED)
            {ActivityCompat.requestPermissions(Feedback_activity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, code);
                return false;
            } else {
                return true;
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(Feedback_activity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Feedback_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {
                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(Feedback_activity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(Feedback_activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Feedback_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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
        if (!allPermissionsGranted) {
            boolean somePermissionsForeverDenied = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Feedback_activity.this, permission)) {
                    //denied
                    Log.e("denied", permission);
                    if (requestCode == 1) {
                        ActivityCompat.requestPermissions(Feedback_activity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.requestPermissions(Feedback_activity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            ActivityCompat.requestPermissions(Feedback_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        } else {
                            ActivityCompat.requestPermissions(Feedback_activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
                        }
                    }

                } else {

                    if (ActivityCompat.checkSelfPermission(Feedback_activity.this, permission) == PackageManager.PERMISSION_GRANTED) {
                        //allowed
                        Log.e("allowed", permission);

                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                    }
                }
            }
            if (somePermissionsForeverDenied) {

                if (requestCode == 1) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(Feedback_activity.this);
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
                                            Uri.fromParts("package", Feedback_activity.this.getPackageName(), null));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }
                if (requestCode == 2) {
                    final androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(Feedback_activity.this);
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
                                            Uri.fromParts("package", Feedback_activity.this.getPackageName(), null));
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

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalData.imageUrl != null) {
            uri = Uri.parse(GlobalData.imageUrl);
            image_name.setText(uri.getPath());
            GlobalData.imageUrl = null;
        }
    }

    private void sendcomplain(String message) {

        pd = ProgressDialog.show(Feedback_activity.this, "", getString(R.string.loading), true, false);
        APIService api = new MainApiClient(Feedback_activity.this).getApiInterface();
        Log.e("USERID", "onClick:===> " + SharedPrefs.getString(Feedback_activity.this, SharedPrefs.uid));
        String userid1 = SharedPrefs.getString(Feedback_activity.this, SharedPrefs.uid);
        if (uri != null) {
            try {
                if (uri.getPath() != null) {
                    path = uri.getPath();
                } else {
                    path = PathUtil.getPath(Feedback_activity.this, uri);
                }
                file = new File(path);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }


        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), userid1);
        RequestBody msg = RequestBody.create(MediaType.parse("multipart/form-data"), message);
        RequestBody ln = RequestBody.create(MediaType.parse("multipart/form-data"), Locale.getDefault().getLanguage());

        Call<complain_feedback_response> call = api.send_new_complain(userid, msg, ln, image);

        call.enqueue(new Callback<complain_feedback_response>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<complain_feedback_response> call, retrofit2.Response<complain_feedback_response> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    complain_feedback_response responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        Toast.makeText(Feedback_activity.this, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                        pd.dismiss();
                    } else {
                        Toast.makeText(Feedback_activity.this, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                } else {
                    Toast.makeText(Feedback_activity.this, getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }

            }

            @Override
            public void onFailure(Call<complain_feedback_response> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Feedback_activity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sendcomplain(type + " : " + ed_Complain.getText().toString());

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(Feedback_activity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sendcomplain(type + " : " + ed_Complain.getText().toString());

                        }
                    });
                    alertDialog.show();
                }

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
