package com.mobile.cover.photo.editor.back.maker.activity.Usefull;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.save_address_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.new_AddressRecyclerAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemDeleteListener;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class Display_address_activity extends AppCompatActivity implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL = 1000;
    public static Activity activity;
    ImageView iv_add_address, id_back;
    RecyclerView rv_address;
    LinearLayout ll_address_availablity;
    new_AddressRecyclerAdapter addressRecyclerAdapter;
    ProgressDialog pd;
    private long mLastClickTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address_activity);
        activity = Display_address_activity.this;
        findviews();
        initlistener();

    }

    private void findviews() {
        iv_add_address = findViewById(R.id.iv_add_address);
        id_back = findViewById(R.id.id_back);
        rv_address = findViewById(R.id.rv_address);
        Log.e("ADDRESS_LIST", "findviews: " + Share.saved_address_list.size());
        if (Share.saved_address_list.size() != 0) {
            for (int i = 0; i < Share.saved_address_list.size(); i++) {
                if (Share.saved_address_list.size() == 1) {
                    Share.saved_address_list.get(0).setIsSelect(true);
                } else {
                    if (Share.saved_address_list.get(i).getIsSelect()) {
                        Share.saved_address_list.get(i).setIsSelect(true);
                    } else {
                        Share.saved_address_list.get(i).setIsSelect(false);
                    }
                }
            }
            addressRecyclerAdapter = new new_AddressRecyclerAdapter(activity, Share.saved_address_list);
            rv_address.setLayoutManager(new LinearLayoutManager(activity));
            rv_address.setAdapter(addressRecyclerAdapter);

            addressRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClickLister(View v, int position) {
                    long currentClickTime = SystemClock.uptimeMillis();
                    long elapsedTime = currentClickTime - mLastClickTime;
                    mLastClickTime = currentClickTime;
                    if (elapsedTime <= MIN_CLICK_INTERVAL)
                        return;
                    Intent intent = new Intent(activity, New_address_save_update_activity.class);
                    intent.putExtra("addresstype", "edit");
                    Share.addressposition = position;
                    Share.address_id = Share.saved_address_list.get(position).getId();
                    startActivity(intent);

                }
            });

            addressRecyclerAdapter.setOnItemDeletekLister(new OnItemDeleteListener() {
                @Override
                public void onItemDeletekLister(View v, final int position) {
                    long currentClickTime = SystemClock.uptimeMillis();
                    long elapsedTime = currentClickTime - mLastClickTime;
                    mLastClickTime = currentClickTime;
                    if (elapsedTime <= MIN_CLICK_INTERVAL)
                        return;
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle(getString(R.string.delete));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.are_u_sure_del));
                    alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.e("DELETE", "onClick: =====>" + position);
                            deleteaddress(Share.saved_address_list.get(position).getId());

                        }
                    });
                    alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    alertDialog.create().show();
                }
            });
        } else {
            getaddress_data();
        }
        ll_address_availablity = findViewById(R.id.ll_address_availablity);
    }

    private void initlistener() {
        id_back.setOnClickListener(this);
        iv_add_address.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == id_back) {
            finish();
        }
        if (v == iv_add_address) {
            Intent intent = new Intent(activity, New_address_save_update_activity.class);
            intent.putExtra("addresstype", "new");
            startActivity(intent);
        }

    }

    private void getaddress_data() {


        pd = ProgressDialog.show(Display_address_activity.this, "", getString(R.string.loading), true, false);


        APIService api = new MainApiClient(Display_address_activity.this).getApiInterface();
        Call<save_address_response> call = api.get_saved_address(SharedPrefs.getString(activity, SharedPrefs.uid), Locale.getDefault().getLanguage());


        call.enqueue(new Callback<save_address_response>() {
            @Override
            public void onResponse(Call<save_address_response> call, retrofit2.Response<save_address_response> response) {
                save_address_response address_response = response.body();
                if (address_response.getSuccess()) {
                    pd.dismiss();
                    if (address_response.getData().size() != 0) {
                        Share.saved_address_list = address_response.getData();
                        Share.saved_address_list.get(0).setIsSelect(true);
                        for (int i = 0; i < Share.saved_address_list.size(); i++) {
                            if (Share.saved_address_list.size() == 1) {
                                Share.saved_address_list.get(0).setIsSelect(true);
                            } else {
                                if (Share.saved_address_list.get(i).getIsSelect()) {
                                    Share.saved_address_list.get(i).setIsSelect(true);
                                } else {
                                    Share.saved_address_list.get(i).setIsSelect(false);
                                }
                            }
                        }
                        addressRecyclerAdapter = new new_AddressRecyclerAdapter(activity, Share.saved_address_list);
                        rv_address.setLayoutManager(new LinearLayoutManager(activity));
                        rv_address.setAdapter(addressRecyclerAdapter);


                        addressRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClickLister(View v, int position) {

                                Intent intent = new Intent(activity, New_address_save_update_activity.class);
                                intent.putExtra("addresstype", "edit");
                                Share.addressposition = position;
                                Share.address_id = Share.saved_address_list.get(position).getId();
                                startActivity(intent);

                            }
                        });

                        addressRecyclerAdapter.setOnItemDeletekLister(new OnItemDeleteListener() {
                            @Override
                            public void onItemDeletekLister(View v, final int position) {
                                long currentClickTime = SystemClock.uptimeMillis();
                                long elapsedTime = currentClickTime - mLastClickTime;
                                mLastClickTime = currentClickTime;
                                if (elapsedTime <= MIN_CLICK_INTERVAL)
                                    return;
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                                alertDialog.setTitle(getString(R.string.delete));
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage(getString(R.string.are_u_sure_del));
                                alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Log.e("DELETE", "onClick: =====>" + position);
                                        deleteaddress(Share.saved_address_list.get(position).getId());

                                    }
                                });
                                alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                                alertDialog.create().show();
                            }
                        });

                    } else {
                        ll_address_availablity.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(activity, New_address_save_update_activity.class);
                        intent.putExtra("addresstype", "new");
                        startActivity(intent);
                        Toast.makeText(Display_address_activity.this, getString(R.string.no_address_available), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<save_address_response> call, Throwable t) {
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pd.dismiss();
                            getaddress_data();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pd.dismiss();
                            getaddress_data();
                        }
                    });
                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pd.dismiss();
                        }
                    });
                    alertDialog.show();
                }


            }
        });

    }


    private void deleteaddress(int type) {


        pd = ProgressDialog.show(Display_address_activity.this, "", getString(R.string.loading), true, false);


        APIService api = new MainApiClient(Display_address_activity.this).getApiInterface();

        Call<save_address_response> call = api.delete_saved_address(String.valueOf(type), Locale.getDefault().getLanguage());


        call.enqueue(new Callback<save_address_response>() {
            @Override
            public void onResponse(Call<save_address_response> call, retrofit2.Response<save_address_response> response) {
                save_address_response address_response = response.body();
                if (response.body().getSuccess()) {
                    pd.dismiss();
                    Share.saved_address_list.clear();
                    Share.saved_address_list = address_response.getData();
                    for (int i = 0; i < Share.saved_address_list.size(); i++) {
//                        if (Share.saved_address_list.size() == 1) {
//                            Share.saved_address_list.get(0).setIsSelect(true);
//                        } else {
//                            if (Share.saved_address_list.get(i).getIsSelect()) {
//                                Share.saved_address_list.get(i).setIsSelect(true);
//                            } else {
                        Share.saved_address_list.get(i).setIsSelect(false);
//                            }
//                        }
                    }
                    if (Share.saved_address_list.size() != 0) {
                        Share.saved_address_list.get(0).setIsSelect(true);
                    }
                    addressRecyclerAdapter = new new_AddressRecyclerAdapter(activity, Share.saved_address_list);
                    rv_address.setLayoutManager(new LinearLayoutManager(activity));
                    rv_address.setAdapter(addressRecyclerAdapter);
                    addressRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClickLister(View v, int position) {

                            Intent intent = new Intent(activity, New_address_save_update_activity.class);
                            intent.putExtra("addresstype", "edit");
                            Share.address_id = Share.saved_address_list.get(position).getId();
                            Share.addressposition = position;
                            startActivity(intent);

                        }
                    });

                    addressRecyclerAdapter.setOnItemDeletekLister(new OnItemDeleteListener() {
                        @Override
                        public void onItemDeletekLister(View v, final int position) {
                            long currentClickTime = SystemClock.uptimeMillis();
                            long elapsedTime = currentClickTime - mLastClickTime;
                            mLastClickTime = currentClickTime;
                            if (elapsedTime <= MIN_CLICK_INTERVAL)
                                return;
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                            alertDialog.setTitle(getString(R.string.delete));
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage(getString(R.string.are_u_sure_del));
                            alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Log.e("DELETE", "onClick: =====>" + position);
                                    deleteaddress(Share.saved_address_list.get(position).getId());

                                }
                            });
                            alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                            alertDialog.create().show();
                        }
                    });
                } else if (!response.body().getSuccess()) {
                    Toast.makeText(Display_address_activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Display_address_activity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<save_address_response> call, Throwable t) {
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pd.dismiss();
                            getaddress_data();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pd.dismiss();
                            getaddress_data();
                        }
                    });
                    alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pd.dismiss();
                        }
                    });
                    alertDialog.show();
                }


            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Share.saved_address_list.size() != 0) {
            ll_address_availablity.setVisibility(View.GONE);
            for (int i = 0; i < Share.saved_address_list.size(); i++) {
                if (Share.saved_address_list.size() == 1) {
                    Share.saved_address_list.get(0).setIsSelect(true);
                } else {
                    if (Share.saved_address_list.get(i).getIsSelect()) {
                        Share.saved_address_list.get(i).setIsSelect(true);
                    } else {
                        Share.saved_address_list.get(i).setIsSelect(false);
                    }
                }
            }
            addressRecyclerAdapter = new new_AddressRecyclerAdapter(activity, Share.saved_address_list);
            rv_address.setLayoutManager(new LinearLayoutManager(activity));
            rv_address.setAdapter(addressRecyclerAdapter);


            addressRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClickLister(View v, int position) {

                    Intent intent = new Intent(activity, New_address_save_update_activity.class);
                    intent.putExtra("addresstype", "edit");
                    Share.addressposition = position;
                    Share.address_id = Share.saved_address_list.get(position).getId();
                    startActivity(intent);

                }
            });

            addressRecyclerAdapter.setOnItemDeletekLister(new OnItemDeleteListener() {
                @Override
                public void onItemDeletekLister(View v, final int position) {
                    long currentClickTime = SystemClock.uptimeMillis();
                    long elapsedTime = currentClickTime - mLastClickTime;
                    mLastClickTime = currentClickTime;
                    if (elapsedTime <= MIN_CLICK_INTERVAL)
                        return;
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle(getString(R.string.delete));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.are_u_sure_del));
                    alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.e("DELETE", "onClick: =====>" + position);
                            deleteaddress(Share.saved_address_list.get(position).getId());

                        }
                    });
                    alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    alertDialog.create().show();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
