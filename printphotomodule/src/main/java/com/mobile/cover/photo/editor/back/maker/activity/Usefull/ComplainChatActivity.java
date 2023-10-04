package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Chat.models.ChatMessage;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.chatResponse;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.postcomplain;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.chat_adapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplainChatActivity extends PrintPhotoBaseActivity {
    ImageView id_back;
    EditText ed_enter_complain;
    RecyclerView rv_chat;
    LinearLayout ll_send_complain;
  //  ProgressDialog pd;
    String userid;
    String text_chatmessage;
    chat_adapter chat_adapter;
    ChatMessage chatMessage;
    List<ChatMessage> sqlist_complain = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_chat_activity);
        ed_enter_complain = findViewById(R.id.ed_enter_complain);
        rv_chat = findViewById(R.id.rv_chat);
        chat_adapter = new chat_adapter(ComplainChatActivity.this, sqlist_complain);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_chat.setLayoutManager(mLayoutManager);
        rv_chat.setItemAnimator(new DefaultItemAnimator());
        rv_chat.setAdapter(chat_adapter);
        ll_send_complain = findViewById(R.id.ll_send_complain);
        ll_send_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_enter_complain.getText().toString().trim().equalsIgnoreCase("") || ed_enter_complain.getText().toString().trim().equalsIgnoreCase(" ") || ed_enter_complain.getText().toString().startsWith(" ")) {
                    Toast.makeText(ComplainChatActivity.this, "Please enter text", Toast.LENGTH_SHORT).show();
                } else {
                    sendcomplain();
                }
            }
        });


        id_back = findViewById(R.id.id_back);
        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getcomplain();
    }

    private void getcomplain() {


        sqlist_complain.clear();
        //pd = ProgressDialog.show(ComplainChatActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(this);


        APIService api = new MainApiClient(ComplainChatActivity.this).getApiInterface();


        Call<chatResponse> call = api.getcomplainresponse(SharedPrefs.getString(this, SharedPrefs.uid), getIntent().getStringExtra("orderid"), Locale.getDefault().getLanguage());


        Log.e("COMPLAIN", "getcomplain: =====>" + SharedPrefs.getString(this, SharedPrefs.uid));
        Log.e("COMPLAIN", "getcomplain: =====>" + getIntent().getStringExtra("orderid"));
        call.enqueue(new Callback<chatResponse>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<chatResponse> call, Response<chatResponse> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    chatResponse responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseMessage());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        for (int i = 0; i < responseData.getData().size(); i++) {
                            if (responseData.getData().get(i).getResponse().get(0).getReplay().equalsIgnoreCase("")) {
                                chatMessage = new ChatMessage(responseData.getData().get(i).getComplain(), responseData.getData().get(i).getDate(), ChatMessage.Type.SENT, "");
                                sqlist_complain.add(chatMessage);
                            } else {
                                chatMessage = new ChatMessage(responseData.getData().get(i).getComplain(), responseData.getData().get(i).getDate(), ChatMessage.Type.SENT, "");
                                sqlist_complain.add(chatMessage);
                                chatMessage = new ChatMessage(responseData.getData().get(i).getResponse().get(0).getReplay(), responseData.getData().get(i).getResponse().get(0).getDate(), ChatMessage.Type.RECEIVED,
                                        responseData.getData().get(i).getResponse().get(0).getNImage());
                                sqlist_complain.add(chatMessage);
                            }
                        }

                        chat_adapter.notifyDataSetChanged();
                        //pd.dismiss();
                        hideProgressDialog();
//                        Toast.makeText(ComplainChatActivity.this, "Request send Successfully", Toast.LENGTH_SHORT).show();
                    } else if (responseData.getResponseCode().equalsIgnoreCase("0")) {
                        //pd.dismiss();
                        hideProgressDialog();
//                        chatView.clearMessages();
                        chatMessage = new ChatMessage("Provide Your Complain", "", ChatMessage.Type.RECEIVED, "");
                        sqlist_complain.add(chatMessage);
                        chat_adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ComplainChatActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        //pd.dismiss();
                        hideProgressDialog();
                    }

                } else {
//                    pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(ComplainChatActivity.this, "Error==>2", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<chatResponse> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ComplainChatActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getcomplain();
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ComplainChatActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getcomplain();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }


    private void sendcomplain() {


        //pd = ProgressDialog.show(ComplainChatActivity.this, "", getString(R.string.loading), true, false);
        showProgressDialog(this);

        APIService api = new MainApiClient(ComplainChatActivity.this).getApiInterface();
        Log.e("USERID", "onClick:===> " + SharedPrefs.getString(this, SharedPrefs.uid));
        userid = SharedPrefs.getString(this, SharedPrefs.uid);

        Call<postcomplain> call = api.postcomplain(getIntent().getStringExtra("orderid"), userid, ed_enter_complain.getText().toString(), Locale.getDefault().getLanguage());

        call.enqueue(new Callback<postcomplain>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<postcomplain> call, retrofit2.Response<postcomplain> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    postcomplain responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        Log.e("SUCCESS", "onSUCCESS: ");
                        chatMessage = new ChatMessage(ed_enter_complain.getText().toString(), responseData.getData().getDate(), ChatMessage.Type.SENT, "");
                        ed_enter_complain.setText("");
                        //pd.dismiss();
                        hideProgressDialog();
                        Toast.makeText(ComplainChatActivity.this, "Complain send Successfully", Toast.LENGTH_SHORT).show();
                        sqlist_complain.add(chatMessage);
                        chat_adapter.notifyDataSetChanged();
                    } else if (responseData.getResponseCode().equalsIgnoreCase("0")) {
                        Toast.makeText(ComplainChatActivity.this, responseData.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + responseData.getResponseMessage());
                        //pd.dismiss();
                        hideProgressDialog();
                    }

                } else {
//                    pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(ComplainChatActivity.this, "Error==>2", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<postcomplain> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ComplainChatActivity.this).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sendcomplain();
                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ComplainChatActivity.this).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sendcomplain();
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
