package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.Getstatus;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.logout_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseFragment;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ChangePasswordActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.complaints.Complain_ticket_system;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.newoffers.OffersActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Contactus_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Display_address_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.EditRegDataActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.SellerRegistration;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.SellerWalletActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.VideoActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.bulk_order_activity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.bulk_order_dashboard_Response;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_offer;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.selected;

public class UserProfileFragment extends PrintPhotoBaseFragment implements View.OnClickListener {

    LinearLayout ll_offer, ll_order, ll_address, ll_personal_details, ll_Seller, ll_change_password, ll_privacy_policy, ll_terms_condition, ll_refund_policy, ll_video, ll_call_complain, ll_feedback_complain, ll_visit_website, ll_bulk_order;
    TextView tv_seller;
    ImageView iv_help_wa;
  //  ProgressDialog pd;
    AlertDialog alertDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_frag_signout, container, false);
        HomeMainActivity.id_back.setVisibility(View.VISIBLE);
        HomeMainActivity.toolbar.setVisibility(View.VISIBLE);
        HomeMainActivity.btn_count.setVisibility(View.GONE);
        HomeMainActivity.iv_logout.setVisibility(View.VISIBLE);
        HomeMainActivity.iv_logout.setImageDrawable(getResources().getDrawable(R.drawable.logout));
        HomeMainActivity.iv_logout.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle(getString(R.string.logout));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.logout_sentence));
                alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        call_logout(dialog);
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
        setHeader();
        getDisplaySize();
        findViews(v);
        initlistener();

        if (SharedPrefs.getString(getActivity(), SharedPrefs.country_code) == "SA") {
            iv_help_wa.setVisibility(View.GONE);
        }

        return v;

    }

    public void call_logout(final DialogInterface dialog_main) {


        dialog_main.dismiss();
//        if (pd != null) {
//            pd.dismiss();
//        }
        hideProgressDialog();

//        pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);
        showProgressDialog(getActivity());
        APIService apiService = new MainApiClient(getActivity()).getApiInterface();
        Call<logout_response> call = apiService.logout(SharedPrefs.getString(getActivity(), SharedPrefs.TOKEN), Locale.getDefault().getLanguage());

        call.enqueue(new Callback<logout_response>() {
            @Override
            public void onResponse(Call<logout_response> call, Response<logout_response> response) {
                if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                    //pd.dismiss();
                    hideProgressDialog();
                    Share.address_value = "";
                    Share.saved_address_list.clear();
                    SharedPrefs.savePref(getContext(), Share.key_reg_suc, false);
                    SharedPrefs.save(getActivity(), SharedPrefs.uid, "");
                    SharedPrefs.save(getContext(), Share.key_ + RegReq.id, "");
                    SharedPrefs.save(getActivity(), SharedPrefs.CART_COUNT, 0);
                    SharedPrefs.save(getActivity(), SharedPrefs.TOKEN, "");
//                    SharedPrefs.save(getActivity(), SharedPrefs.country_code, "");
                    SharedPrefs.save(getActivity(), SharedPrefs.country_name, "");
                    SharedPrefs.save(getActivity(), SharedPrefs.country_id, "");
                    SharedPrefs.save(getActivity(), SharedPrefs.currency_id, "");
                    SharedPrefs.save(getContext(), SharedPrefs.REVIEW, 0);
                    HomeMainActivity.selected = 3;
                    Share.saved_address_list.clear();
                    Share.search_saved_address_list.clear();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, new FragmentAccount());
//                    fragmentTransaction.commit();
                    fragmentTransaction.commitAllowingStateLoss();
                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Share.address_value = "";
                    Share.saved_address_list.clear();
                    Share.search_saved_address_list.clear();
                    SharedPrefs.savePref(getContext(), Share.key_reg_suc, false);
                    SharedPrefs.save(getActivity(), SharedPrefs.uid, "");
                    SharedPrefs.save(getContext(), Share.key_ + RegReq.id, "");
                    SharedPrefs.save(getActivity(), SharedPrefs.CART_COUNT, 0);
                    SharedPrefs.save(getActivity(), SharedPrefs.TOKEN, "");
                    SharedPrefs.save(getContext(), SharedPrefs.REVIEW, 0);
                    SharedPrefs.save(getActivity(), SharedPrefs.country_code, "");
                    SharedPrefs.save(getActivity(), SharedPrefs.country_name, "");
                    SharedPrefs.save(getActivity(), SharedPrefs.country_id, "");
                    SharedPrefs.save(getActivity(), SharedPrefs.currency_id, "");
                    HomeMainActivity.selected = 3;
                    Share.saved_address_list.clear();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, new FragmentAccount());
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onFailure(Call<logout_response> call, Throwable t) {
                t.printStackTrace();
                //pd.dismiss();
                hideProgressDialog();
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            call_logout(dialog_main);
                        }
                    });
                    alertDialog.show();
                } else {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            call_logout(dialog_main);
                        }
                    });
                    alertDialog.show();
                }
            }
        });


    }


    private void initlistener() {
        ll_order.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_personal_details.setOnClickListener(this);
        ll_change_password.setOnClickListener(this);
        ll_privacy_policy.setOnClickListener(this);
        ll_terms_condition.setOnClickListener(this);
        ll_refund_policy.setOnClickListener(this);
        ll_video.setOnClickListener(this);
        ll_call_complain.setOnClickListener(this);
        ll_feedback_complain.setOnClickListener(this);
        ll_Seller.setOnClickListener(this);
        ll_offer.setOnClickListener(this);
        ll_visit_website.setOnClickListener(this);
        ll_bulk_order.setOnClickListener(this);

        iv_help_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilsKt.sendWhatsappDirectMessage(getActivity());
            }
        });
    }


    private void bulk_order_status_check() {

//        if (pd != null) {
//            pd.dismiss();
//        }
        hideProgressDialog();
//        pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);
        showProgressDialog(getActivity());
        int user_id;
        if (!SharedPrefs.getBoolean(getActivity(), Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(getActivity(), Share.key_ + RegReq.id));
        }

        APIService apiService = new MainApiClient(getActivity()).getApiInterface();
        Call<bulk_order_dashboard_Response> call = apiService.bulk_order_status("" + user_id, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<bulk_order_dashboard_Response>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<bulk_order_dashboard_Response> call, Response<bulk_order_dashboard_Response> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    //pd.dismiss();
                    hideProgressDialog();
                    if (response.body().getCanPlaceOrder() == 1) {
                        Intent intent = new Intent(getActivity(), bulk_order_activity.class);
                        intent.putExtra("fname", SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[0]);
                        try {
                            intent.putExtra("lname", SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[1] == null ? "" : SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[1]);
                        } catch (Exception ex) {
                            intent.putExtra("lname", SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[1] == null ? "" : SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[1]);
                        }

                        intent.putExtra("mobile_1", SharedPrefs.getString(getContext(), Share.key_ + RegReq.mobile_1));
                        intent.putExtra("email", SharedPrefs.getString(getContext(), Share.key_ + RegReq.email));
                        startActivity(intent);
                    } else {
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                        alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(getString(R.string.alert));
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage(response.body().getMessage());
                        alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<bulk_order_dashboard_Response> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                Log.e(TAG, "onFailure: ======>" + t);
                Log.e(TAG, "onFailure: ======>" + t.getMessage());
                Log.e(TAG, "onFailure: ======>" + t.getLocalizedMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            bulk_order_status_check();

                        }
                    });
                    alertDialog.show();
                } else {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            bulk_order_status_check();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v == ll_order) {
            selected = 1;
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frg_main, new FragmentOrder());
            fragmentTransaction.commit();
        }
        if (v == ll_bulk_order) {
            bulk_order_status_check();
        }
        if (v == ll_offer) {
            Intent intent = new Intent(getActivity(), OffersActivity.class);
            startActivity(intent);
        }
        if (v == ll_address) {
            Intent intent = new Intent(getContext(), Display_address_activity.class);
            Share.isfromplaceorder = false;
            Share.radiobutton = false;
            intent.putExtra("addresstype", "Display");
            startActivity(intent);
        }
        if (v == ll_personal_details) {
            Intent intent = new Intent(getActivity(), EditRegDataActivity.class);
            intent.putExtra("userdata", "");
            intent.putExtra("fname", SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[0]);
            try {
                intent.putExtra("lname", SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[1] == null ? "" : SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[1]);
            } catch (Exception ex) {
                intent.putExtra("lname", SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[1] == null ? "" : SharedPrefs.getString(getContext(), Share.key_ + RegReq.name).split(" ")[1]);
            }

            intent.putExtra("mobile_1", SharedPrefs.getString(getContext(), Share.key_ + RegReq.mobile_1));
            intent.putExtra("email", SharedPrefs.getString(getContext(), Share.key_ + RegReq.email));
            startActivity(intent);
        }
        if (v == ll_change_password) {
            startActivity(new Intent(getContext(), ChangePasswordActivity.class));
        }
        if (v == ll_Seller) {
            if (SharedPrefs.getString(getActivity(), SharedPrefs.SELLER).equalsIgnoreCase("1")) {
                Intent intent = new Intent(getActivity(), SellerWalletActivity.class);
                intent.putExtra("sellerid", SharedPrefs.getString(getContext(), SharedPrefs.Sellerid));
                startActivity(intent);
            } else {
                check_seller();

            }
        }
        if (v == ll_privacy_policy) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://printphoto.in/Photo_case/public/Privacy_Policy.html"));
            startActivity(browserIntent);
        }
        if (v == ll_terms_condition) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://printphoto.in/Photo_case/public/Terms_and_Condition.html"));
            startActivity(browserIntent);
        }
        if (v == ll_refund_policy) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://printphoto.in/Photo_case/public/refund.html"));
            startActivity(browserIntent);
        }
        if (v == ll_video) {
            Intent intent = new Intent(getActivity(), VideoActivity.class);
            startActivity(intent);
        }
        if (v == ll_call_complain) {
            Intent intent = new Intent(getActivity(), Contactus_activity.class);
            startActivity(intent);
        }
        if (v == ll_feedback_complain) {
            Intent intent = new Intent(getActivity(), Complain_ticket_system.class);
            intent.putExtra("order_id", "");
            startActivity(intent);
        }
        if (v == ll_visit_website) {
            if (alertDialog != null) {
                alertDialog.dismiss();
            }

            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setCancelable(false);
            alertDialog.setTitle(getString(R.string.alert));
            alertDialog.setMessage(getString(R.string.visit_website_dialog));
            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://printphoto.in"));
//                    startActivity(browserIntent);
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }

    }

    public void check_seller() {


//        if (pd != null) {
//            pd.dismiss();
//        }
        hideProgressDialog();
//        pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);
        showProgressDialog(getActivity());
        Integer userid = null;
        APIService api = new MainApiClient(getActivity()).getApiInterface();

        Log.e("USERID", "onClick:===> " + SharedPrefs.getString(getContext(), SharedPrefs.uid));
        if (SharedPrefs.getString(getContext(), SharedPrefs.uid).matches("")) {
            userid = Integer.valueOf(SharedPrefs.getString(getContext(), Share.key_ + RegReq.id));
        } else {
            userid = Integer.valueOf(SharedPrefs.getString(getContext(), SharedPrefs.uid));
        }
        Call<Getstatus> call = api.getstatus(userid, Locale.getDefault().getLanguage());

        call.enqueue(new Callback<Getstatus>() {
            public static final String TAG = "test";

            @Override
            public void onResponse(Call<Getstatus> call, Response<Getstatus> response) {
                Log.e(TAG, "onResponse: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    Getstatus responseData = response.body();
                    Log.e("RESPONSE", "onResponse: " + responseData.getResponseCode());
                    if (responseData.getResponseCode().equalsIgnoreCase("1")) {
                        //pd.dismiss();
                        hideProgressDialog();
                        SharedPrefs.save(getActivity(), SharedPrefs.SELLER, responseData.getResponseCode());
                        Intent intent = new Intent(getActivity(), SellerWalletActivity.class);
                        Log.e(TAG, "onResponse: " + SharedPrefs.getString(getContext(), SharedPrefs.Sellerid));
                        intent.putExtra("sellerid", SharedPrefs.getString(getContext(), SharedPrefs.Sellerid));
                        startActivity(intent);
                    } else if (responseData.getResponseCode().equalsIgnoreCase("0")) {
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.dialogbrand_layout);
                        dialog.show();
                        //pd.dismiss();
                        hideProgressDialog();
                        Button ButtonCancelPick = dialog.findViewById(R.id.ButtonCancelPick);
                        ButtonCancelPick.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    } else if (responseData.getResponseCode().equalsIgnoreCase("2")) {
                        //pd.dismiss();
                        hideProgressDialog();
                        Intent seller = new Intent(getActivity(), SellerRegistration.class);
                        getActivity().startActivity(seller);
                    } else {
                        //pd.dismiss();
                        hideProgressDialog();
                        Toast.makeText(getActivity(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //pd.dismiss();
                    hideProgressDialog();
                    Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Getstatus> call, Throwable t) {
                //pd.dismiss();
                hideProgressDialog();

                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            check_seller();

                        }
                    });
                    alertDialog.show();
                } else {
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setCancelable(false);
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            check_seller();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("RESUME", "onResume:===============> ");
        if (SharedPrefs.getString(getActivity(), SharedPrefs.SELLER).equalsIgnoreCase("1")) {
            tv_seller.setText(getString(R.string.seller_wallet));
        } else {
            tv_seller.setText(getString(R.string.want_to_become_seller));
        }
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (SharedPrefs.getString(getActivity(), SharedPrefs.country_code).equalsIgnoreCase("")) {
            Share.countryCodeValue = tm.getNetworkCountryIso().toUpperCase();
        } else {
            Share.countryCodeValue = SharedPrefs.getString(getActivity(), SharedPrefs.country_code);
        }
        if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) == 0) {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);
        } else {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
            HomeMainActivity.tv_nudge_cart_count.setText("" + SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT));
        }

    }


    private void findViews(View v) {
        ll_order = v.findViewById(R.id.ll_order);
        ll_offer = v.findViewById(R.id.ll_offer);
        ll_bulk_order = v.findViewById(R.id.ll_bulk_order);
        ll_address = v.findViewById(R.id.ll_address);
        ll_personal_details = v.findViewById(R.id.ll_personal_details);
        ll_Seller = v.findViewById(R.id.ll_Seller);
        ll_change_password = v.findViewById(R.id.ll_change_password);
        ll_privacy_policy = v.findViewById(R.id.ll_privacy_policy);
        ll_terms_condition = v.findViewById(R.id.ll_terms_condition);
        ll_refund_policy = v.findViewById(R.id.ll_refund_policy);
        ll_video = v.findViewById(R.id.ll_video);
        ll_call_complain = v.findViewById(R.id.ll_call_complain);
        ll_feedback_complain = v.findViewById(R.id.ll_feedback_complain);
        ll_visit_website = v.findViewById(R.id.ll_visit_website);
        tv_seller = v.findViewById(R.id.tv_seller);
        iv_help_wa = v.findViewById(R.id.iv_help_wa);

        Share.countryCodeValue = SharedPrefs.getString(getActivity(), SharedPrefs.country_code);

        if (Share.countryCodeValue.equalsIgnoreCase("IN")) {
            ll_offer.setVisibility(View.VISIBLE);
            ll_refund_policy.setVisibility(View.VISIBLE);
            ll_Seller.setVisibility(View.VISIBLE);
        } else {
            ll_offer.setVisibility(View.VISIBLE);
            ll_refund_policy.setVisibility(View.GONE);
            ll_Seller.setVisibility(View.GONE);
        }
    }


    private void getDisplaySize() {
        Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void setHeader() {
        TextView title = getActivity().findViewById(R.id.title);
        title.setText(getString(R.string.my_account));
        ImageView imageView = getActivity().findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 0;
                HomeMainActivity.id_home.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_account.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_cart.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                HomeMainActivity.id_order.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                id_offer.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);9512277648
                fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }


}
