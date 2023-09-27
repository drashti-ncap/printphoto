package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.NetworkManager;
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.responseModelRequest;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.CaseEditActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.LogInActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.fragment.SelectCompany;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.request_final_brand;
import com.mobile.cover.photo.editor.back.maker.phonecase.FragmentRequestModelBrand;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;


public class Request_RecyclerAdapter extends RecyclerView.Adapter<Request_RecyclerAdapter.MyViewHolder> {
    private static final long MIN_CLICK_INTERVAL = 1000;
    Context context;
    ArrayList<request_final_brand> subDataArrayList;
    ProgressDialog pd;
    OnItemClickListener onItemClickListener;
    ProgressDialog progressDialog;
    responseModelRequest responseData;
    private String[] str = new String[2];
    private long mLastClickTime;

    public Request_RecyclerAdapter(Context context, ArrayList<request_final_brand> subDataArrayList) {
        this.context = context;
        this.subDataArrayList = subDataArrayList;
    }

    @Override
    public Request_RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_name_item, parent, false);
        return new Request_RecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Request_RecyclerAdapter.MyViewHolder holder, final int position) {


        holder.cardView.getLayoutParams().height = Share.screenHeight / 8;

        holder.name.setText(subDataArrayList.get(position).getBrand());
        holder.btn_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPrefs.getString(context, SharedPrefs.uid).equalsIgnoreCase("uid") || SharedPrefs.getString(context, SharedPrefs.uid).equalsIgnoreCase("")) {
                    Intent intent = new Intent(context, LogInActivity.class);
                    context.startActivity(intent);
                } else {
                    sendrequest(subDataArrayList.get(position).getModelid(), holder.name.getText().toString(), SharedPrefs.getString(context, SharedPrefs.uid), subDataArrayList.get(position).getId(), v);
                }
            }
        });
    }

    private void sendrequest(final String modelid, final String name, final String userid, final String id, final View v) {

        pd = ProgressDialog.show(context, "", context.getString(R.string.loading), true, false);

        APIService api = new MainApiClient(context).getApiInterface();

        Log.e("DETAILS", "sendrequest: " + modelid);
        Log.e("DETAILS", "sendrequest: " + name);
        Log.e("DETAILS", "sendrequest: " + userid);
        Log.e("DETAILS", "sendrequest: " + id);
        Call<responseModelRequest> call = api.post_new_request_model(modelid, userid, name, id, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<responseModelRequest>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<responseModelRequest> call, retrofit2.Response<responseModelRequest> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("2")) {
                        Log.e("SUCCESS", "onSUCCESS: " + responseData.getResponseCode());
                        pd.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle(context.getString(R.string.model_request));
                        alertDialog.setMessage(context.getString(R.string.request_sent_successfull));
                        alertDialog.setButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                FragmentTransaction fragmentTransaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                                if (Share.requestfragment == 1) {
                                    fragmentTransaction.replace(R.id.frg_main, new SelectCompany());
                                } else {
//                                    fragmentTransaction.replace(R.id.frg_main, new SelectCompanyMobel());
                                }
                                fragmentTransaction.commit();
                            }
                        });
                        alertDialog.show();
                    } else if (responseData.getResponseCode().equalsIgnoreCase("0")) {
                        pd.dismiss();
                        Log.e("SUCCESS", "onSUCCESS: " + responseData.getResponseCode());

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle(context.getString(R.string.model_request));
                        alertDialog.setMessage(responseData.getResponseMessage());
                        alertDialog.setButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    } else if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        pd.dismiss();
                        Log.e("SUCCESS", "onSUCCESS: " + responseData.getResponseCode());

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle(context.getString(R.string.model_request));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(context.getString(R.string.request_model_already_available));
                        alertDialog.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (responseData.getData().getMaskImage() != null && !responseData.getData().getMaskImage().equalsIgnoreCase("null")) {
                                    str[0] = responseData.getData().getMaskImage();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.something_went_wrong_try_agaian), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (responseData.getData().getModalImage() != null && !responseData.getData().getModalImage().equalsIgnoreCase("null")) {
                                    str[1] = responseData.getData().getModalImage();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.something_went_wrong_try_agaian), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (NetworkManager.isInternetConnectedDailog(context)) {
                                    GetXMLTask task = new GetXMLTask();
                                    task.execute(str);
                                }
                            }
                        });
                        alertDialog.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                FragmentTransaction fragmentTransaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                                if (Share.requestfragment == 1) {
                                    fragmentTransaction.replace(R.id.frg_main, new SelectCompany());
                                } else {
//                                    fragmentTransaction.replace(R.id.frg_main, new SelectCompanyMobel());
                                }
                                fragmentTransaction.commit();
                            }
                        });
                        alertDialog.show();


                    } else {
                        Toast.makeText(context, context.getString(R.string.please_sign_in_yourself), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                } else {
                    pd.dismiss();
                    Log.e(TAG, "onResponse: ");
                    Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<responseModelRequest> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle(context.getResources().getString(R.string.time_out));
                    alertDialog.setMessage(context.getResources().getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(context.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sendrequest(modelid, name, userid, id, v);

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle(context.getResources().getString(R.string.internet_connection));
                    alertDialog.setMessage(context.getResources().getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(context.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sendrequest(modelid, name, userid, id, v);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    public ArrayList<request_final_brand> filter(String s) {
        ArrayList<request_final_brand> subData = new ArrayList<>();
//        subDataArrayList.clear();
        if (s.length() == 0) {
            subData.addAll(Share.request_brand);

        } else {
            for (request_final_brand wp : Share.request_brand) {

                if (wp.getBrand().toLowerCase().trim().contains(s.toLowerCase())) {

                    subData.add(wp);
                    Log.d("size", "==>" + wp.getBrand());
                    FragmentRequestModelBrand.tv_no_fnd.setVisibility(View.GONE);
                    FragmentRequestModelBrand.btn_request_written.setVisibility(View.GONE);
                } else if (subData.size() <= 0) {
                    FragmentRequestModelBrand.tv_no_fnd.setVisibility(View.VISIBLE);
                    FragmentRequestModelBrand.btn_request_written.setVisibility(View.VISIBLE);
                    FragmentRequestModelBrand.btn_request_written.setOnClickListener(new OnSingleClickListener() {
                        @Override
                        public void onSingleClick(View v) {
                            send_model_request_dialog();
                        }
                    });
                    Log.e("TAG", "filter: ==========>SIZE============>" + subData.size());
//                    send_model_request_dialog();
                }
            }
        }
        noti(subData);
        return subData;
    }


    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }


    @Override
    public int getItemCount() {
        return subDataArrayList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void noti(ArrayList<request_final_brand> subData) {
        Log.e("TAG", "noti: ================>NOTIFY_SIZE============>" + subData.size());
        if (subData.size() <= 0) {
//            Toast.makeText(context, "Request Brand Dialog=====>"+subData.size(), Toast.LENGTH_SHORT).show();
            this.subDataArrayList = subData;
            notifyDataSetChanged();
//            send_model_request_dialog();
        } else {
            this.subDataArrayList = subData;
            notifyDataSetChanged();
        }
    }

    public void send_model_request_dialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialogcomplain_layout);
        dialog.show();
        final EditText ed_complain = dialog.findViewById(R.id.ed_complain);
        ed_complain.setHint(context.getString(R.string.enter_brand_model_name));
        TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText(context.getString(R.string.request_your_device));
        Button ButtonCancelPick = dialog.findViewById(R.id.ButtonCancelPick);
        Button btnsubmit = dialog.findViewById(R.id.btn_submit);
        ButtonCancelPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String complain = ed_complain.getText().toString();
                if (SharedPrefs.getString(context, SharedPrefs.uid).equalsIgnoreCase("uid") || SharedPrefs.getString(context, SharedPrefs.uid).equalsIgnoreCase("")) {
                    Intent intent = new Intent(context, LogInActivity.class);
                    context.startActivity(intent);
                } else {
                    if (!complain.trim().equalsIgnoreCase("")) {
                        sendrequest_written(complain, dialog, SharedPrefs.getString(context, SharedPrefs.uid));
                    } else {
                        Toast.makeText(context, context.getString(R.string.please_enter_your_brand_name), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
//        }
    }

    private void sendrequest_written(final String complain, final Dialog dialog, final String userid) {


        pd = ProgressDialog.show(context, "", context.getString(R.string.loading), true, false);
        pd.show();

        APIService api = new MainApiClient(context).getApiInterface();



        Call<responseModelRequest> call = api.postrequest(userid, complain, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<responseModelRequest>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<responseModelRequest> call, retrofit2.Response<responseModelRequest> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    responseModelRequest responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        Log.e("SUCCESS", "onSUCCESS: ");
                        dialog.dismiss();
                        pd.dismiss();
                        FragmentRequestModelBrand.tv_no_fnd.setVisibility(View.GONE);
                        FragmentRequestModelBrand.atv_brand_model.setText("");
                        Toast.makeText(context, context.getString(R.string.request_sent_successfull), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        pd.dismiss();
                    }

                } else {
                    pd.dismiss();
                    Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<responseModelRequest> call, Throwable t) {
                pd.dismiss();

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle(context.getResources().getString(R.string.time_out));
                    alertDialog.setMessage(context.getResources().getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(context.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {
                            dialog1.dismiss();
                            sendrequest_written(complain, dialog, userid);

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle(context.getResources().getString(R.string.internet_connection));
                    alertDialog.setMessage(context.getResources().getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(context.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog1, int which) {
                            dialog1.dismiss();
                            sendrequest_written(complain, dialog, userid);

                        }
                    });
                    alertDialog.show();
                }
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());

            }
        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;


        TextView name;
        Button btn_send_request;

        MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            cardView = view.findViewById(R.id.main_card);
            btn_send_request = view.findViewById(R.id.btn_send_request);


        }
    }

    private class GetXMLTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.loading), true, false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (bitmaps.size() != 0) {
                if (bitmaps.get(0) != null && bitmaps.get(1) != null) {

                    Share.bitmapHashMap.put(Share.key_msk_imge, bitmaps.get(0));
                    Share.bitmapHashMap.put(Share.key_normal_image, bitmaps.get(1));
                    Share.maskheight = Float.parseFloat(responseData.getData().getHeight());
                    Share.maskwidth = Float.parseFloat(responseData.getData().getWidth());

                    Log.e("hashmap", "==>" + Share.bitmapHashMap);


                    Intent intent = new Intent(context, CaseEditActivity.class);
                    intent.putExtra("model_name", responseData.getData().getModalName());
                    intent.putExtra("model_id", responseData.getData().getId());
                    intent.putExtra("user_id", SharedPrefs.getString(context, Share.key_ + RegReq.id));
                    intent.putExtra("quantity", "1");
                    intent.putExtra("total_amount", responseData.getData().getPrice());
                    intent.putExtra("model_id", responseData.getData().getId());
                    intent.putExtra("paid_amount", responseData.getData().getPrice());
//                    Share.mobile_type = responseData.getData().getImage_type();
                    Log.e("TAG", "onPostExecute: " + Share.mobile_type);
                    if (!responseData.getData().getWidth().equalsIgnoreCase("")) {
                        intent.putExtra("width", responseData.getData().getWidth());
                        intent.putExtra("height", responseData.getData().getHeight());
                        Share.maskheight = Float.parseFloat(responseData.getData().getHeight());
                        Share.maskwidth = Float.parseFloat(responseData.getData().getWidth());
                        Log.e("width", "==>" + responseData.getData().getWidth());
                        Log.e("height", "==>" + responseData.getData().getHeight());
                    }

                    intent.putExtra("shipping", "0");
                    context.startActivity(intent);

//                    Intent intent = new Intent(getActivity(), Default_image_activity.class);
//                    intent.putExtra("model_name", subDataModelDetails.getModalName());
//                    intent.putExtra("model_id", subDataModelDetails.getId());
//                    intent.putExtra("user_id", SharedPrefs.getString(getContext(), Share.key_ + RegReq.id));
//                    intent.putExtra("quantity", "1");
//                    intent.putExtra("total_amount", subDataModelDetails.getPrice());
//                    intent.putExtra("model_id", subDataModelDetails.getId());
//                    intent.putExtra("paid_amount", subDataModelDetails.getPrice());
//                    Share.mobile_type = subDataModelDetails.getImage_type();
//                    Log.e(TAG, "onPostExecute: " + Share.mobile_type);
//                    if (!subDataModelDetails.getWidth().equalsIgnoreCase("")) {
//                        intent.putExtra("width", subDataModelDetails.getWidth());
//                        intent.putExtra("height", subDataModelDetails.getHeight());
//                    }
//
//                    intent.putExtra("shipping", "0");
//                    startActivity(intent);
                }
            } else {

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle(context.getString(R.string.download_failed));
                alertDialog.setMessage(context.getResources().getString(R.string.slow_connect));
                alertDialog.setButton(context.getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progressDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... strings) {


            ArrayList<Bitmap> map = new ArrayList<Bitmap>();
            try {
                //enhanced for statement, mainly used for arrays
                map.add(downloadImage(strings[0]));// I used as this for you to understand. You can use for each loop
                map.add(downloadImage(strings[1]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;


        }


    }


}
