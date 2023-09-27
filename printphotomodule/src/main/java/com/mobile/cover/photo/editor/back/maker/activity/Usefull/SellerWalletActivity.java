package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.transactionDetailResponse;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.TransactionWithdrawResponse;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.transactionadapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.model.transaction;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SellerWalletActivity extends AppCompatActivity implements View.OnClickListener {

    public static List<transaction> sqlist = new ArrayList<>();
    ImageView id_back, iv_share_sellercode, id_info_policy, iv_share_now;
    CardView card_share;
    Bitmap share_bitmap;
    ProgressDialog pd;
    RelativeLayout rl_request;
    RecyclerView recyclerview;
    transactionadapter mAdapter;
    TextView tv_rate, tv_underc, tv_request, tv_seller_promo_code, tv_promocode_new, tv_info_share, tv_extra_info, tv_sticker_rate, tv_seller_wallet;
    int sum = 0;
    LinearLayout ll_transaction_history, ll_rate_app, ll_pp_policy;
    private int PICK_IMAGE_REQUEST = 101;

    private List<String> listPermissionsNeeded = new ArrayList<>();
    private int STORAGE_PERMISSION_CODE = 23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_seller_wallet);
        initviews();
        initlistener();
    }

    private void initlistener() {
        id_back.setOnClickListener(this);
        tv_request.setOnClickListener(this);
        tv_seller_wallet.setOnClickListener(this);
        tv_rate.setOnClickListener(this);
        tv_request.setOnClickListener(this);
        rl_request.setOnClickListener(this);
        ll_rate_app.setOnClickListener(this);
        ll_pp_policy.setOnClickListener(this);
        ll_transaction_history.setOnClickListener(this);
        id_info_policy.setOnClickListener(this);
    }

    private void initviews() {
        id_back = findViewById(R.id.id_back);
        rl_request = findViewById(R.id.rl_request);
        tv_seller_wallet = findViewById(R.id.tv_seller_wallet);
        iv_share_now = findViewById(R.id.iv_share_now);
        ll_transaction_history = findViewById(R.id.ll_transaction_history);
        ll_rate_app = findViewById(R.id.ll_rate_app);
        ll_pp_policy = findViewById(R.id.ll_pp_policy);
        card_share = findViewById(R.id.card_share);
        iv_share_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermissionsStorage(2)) {
//                    if (checkAndRequestPermissions()) {
                    card_share.setDrawingCacheEnabled(true);
                    card_share.buildDrawingCache();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    Log.e("BITMAP", "onClick: " + card_share.getHeight() + "=========>" + card_share.getWidth());
                    share_bitmap = getResizedBitmapCover(CropBitmapTransparency(card_share.getDrawingCache()), 300, 600);
                    card_share.setAlwaysDrawnWithCacheEnabled(false);

                    PackageManager pm = getPackageManager();
                    try {
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        share_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(), share_bitmap, "Title", null);
                        Uri imageUri = Uri.parse(path);
                        Intent waIntent = new Intent(Intent.ACTION_SEND);
                        waIntent.setType("image/*");
                        waIntent.putExtra(android.content.Intent.EXTRA_STREAM, imageUri);
                        startActivity(Intent.createChooser(waIntent, "Share with"));
                    } catch (Exception e) {
                        Log.e("Error on sharing", e + " ");
                        Toast.makeText(SellerWalletActivity.this, getString(R.string.app_not_installed), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        tv_promocode_new = findViewById(R.id.tv_promocode_new);
        id_info_policy = findViewById(R.id.id_info_policy);
        tv_request = findViewById(R.id.tv_request);
        tv_rate = findViewById(R.id.tv_rate);
        iv_share_sellercode = findViewById(R.id.iv_share_sellercode);
        tv_seller_promo_code = findViewById(R.id.tv_seller_promo_code);
        tv_info_share = findViewById(R.id.tv_info_share);
        tv_extra_info = findViewById(R.id.tv_extra_info);
        tv_sticker_rate = findViewById(R.id.tv_sticker_rate);

        iv_share_sellercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_app();
            }
        });
        sqlist = new ArrayList<>();
        gettransacthistory();
    }

    public Bitmap getResizedBitmapCover(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
    }


    Bitmap CropBitmapTransparency(Bitmap sourceBitmap) {
        int minX = sourceBitmap.getWidth();
        int minY = sourceBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < sourceBitmap.getHeight(); y++) {
            for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                int alpha = (sourceBitmap.getPixel(x, y) >> 24) & 255;
                if (alpha > 0)   // pixel is not 100% transparent
                {
                    if (x < minX)
                        minX = x;
                    if (x > maxX)
                        maxX = x;
                    if (y < minY)
                        minY = y;
                    if (y > maxY)
                        maxY = y;
                }
            }
        }
        if ((maxX < minX) || (maxY < minY))
            return null; // Bitmap is entirely transparent

        // crop bitmap to non-transparent area and return:
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }


    private void share_app() {
        Log.e("click", "share_app: ");
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "getResources().getString(R.string.app_name)");
            String sAux = "Download this amazing " + "getString(R.string.app_name).toLowerCase()" + " app from play store\n\n\n" + "Also apply this " + tv_seller_promo_code.getText().toString() + " and get amazing discount on your every order!!!";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (v == id_back) {
            finish();
        }
        if (v == rl_request) {
        }

        if (v == tv_seller_wallet) {

        }

        if (v == tv_rate) {

        }

        if (v == tv_request) {

        }

        if (v == ll_transaction_history) {
            Intent intent = new Intent(SellerWalletActivity.this, Seller_transaction_activity.class);
            startActivity(intent);
        }

        if (v == ll_rate_app) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        }

        if (v == ll_pp_policy) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://printphoto.in/Photo_case/public/Seller-Privacy-Poilcy.html"));
            startActivity(browserIntent);
        }

        if (v == id_info_policy) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://printphoto.in/Photo_case/public/Seller-Privacy-Poilcy.html"));
            startActivity(browserIntent);
        }
    }


    private void gettransacthistory() {


        pd = ProgressDialog.show(SellerWalletActivity.this, "", getString(R.string.loading), true, false);

        sqlist.clear();
        APIService api = new MainApiClient(SellerWalletActivity.this).getApiInterface();

        Log.e("SELLER_ID", "getcomplain: " + getIntent().getStringExtra("sellerid"));
        Call<TransactionWithdrawResponse> call = api.gettransaction(getIntent().getStringExtra("sellerid"), Locale.getDefault().getLanguage());

        call.enqueue(new Callback<TransactionWithdrawResponse>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<TransactionWithdrawResponse> call, retrofit2.Response<TransactionWithdrawResponse> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    TransactionWithdrawResponse responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseMessage());
                    tv_seller_promo_code.setText("Promocode : " + responseData.getpromo_code());
                    tv_promocode_new.setText(" " + responseData.getpromo_code());
                    tv_info_share.setText("Share Your Promocode & Get ₹" + responseData.getUserPrice().getMagicMug() + " in Wallet");
                    tv_extra_info.setText("Use This Promocode & Get ₹" + responseData.getUserPrice().getMagicMug() + " INR Instant Discount: " + responseData.getpromo_code());
                    tv_sticker_rate.setText("₹" + responseData.getUserPrice().getMagicMug());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        Log.e(TAG, "onResponse: " + responseData.getpromo_code());
                        List<transactionDetailResponse> datumList = responseData.getDate();
                        Log.e(TAG, "onResponse: " + datumList.size());
                        for (transactionDetailResponse datum : datumList) {
                            transaction transaction = new transaction("" + datum.getId(), "" + datum.getOrderId(), "" + datum.getModelId(), "" + datum.getUserId(),
                                    "" + datum.getSellerId(), "" + datum.getPrice(), "" + datum.getDateTime(), "" + datum.getStatus());
                            sum += Integer.valueOf(datum.getPrice());
                            sqlist.add(transaction);
//                            mAdapter.notifyDataSetChanged();
                        }
                        if (sqlist.size() <= 0) {
                        }
                        Log.e(TAG, "onResponse:==================> " + sum);
                        sum = responseData.getCase() - responseData.getWithdraw();
                        tv_rate.setText(getString(R.string.rs_icon) + " " + sum + ".00");
                        pd.dismiss();
                    } else if (responseData.getResponseCode().equalsIgnoreCase("0")) {
//                        tv_transact.setText(responseData.getResponseMessage());
                        pd.dismiss();
                    } else {
                        Toast.makeText(SellerWalletActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                } else {
                    pd.dismiss();
                    Toast.makeText(SellerWalletActivity.this, "Error==>2", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<TransactionWithdrawResponse> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(SellerWalletActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            gettransacthistory();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SellerWalletActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            gettransacthistory();

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

    private boolean checkAndRequestPermissionsStorage(int code) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(SellerWalletActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SellerWalletActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                        code);
                return false;
            } else {

                return true;
            }
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(SellerWalletActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SellerWalletActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(SellerWalletActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(SellerWalletActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SellerWalletActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                        code);
                return false;
            } else {

                return true;
            }
        }
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
        if (!allPermissionsGranted) {
            boolean somePermissionsForeverDenied = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    //denied
                    Log.e("denied", permission);
                    if (requestCode == 1) {
                        ActivityCompat.requestPermissions(SellerWalletActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                    if (requestCode == 2) {
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                            ActivityCompat.requestPermissions(SellerWalletActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 2);
                        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                            ActivityCompat.requestPermissions(SellerWalletActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                        }else {
                            ActivityCompat.requestPermissions(SellerWalletActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
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

}
