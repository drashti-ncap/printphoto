package com.mobile.cover.photo.editor.back.maker.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mall_main_category_response_click_data;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseFragment;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentCart;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentHome;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentMall;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.mall.mall_category_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.mall_filter_activity;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.mall_sub_category_data;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.mall_AllChild;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.btn_count;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_account;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_cart;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_home;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_offer;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_order;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.selected;

public class Mall_FragmentOne extends PrintPhotoBaseFragment {
   // ProgressDialog pd;
    RecyclerView rv_offer;
    mall_sub_category_data myRecyclerAdapter;
    ImageView id_back;
    TextView title;
    FirebaseAnalytics firebaseAnalytics;
    AppEventsLogger logger;
    AlertDialog alertDialog;
    private List<mall_AllChild> allChildren = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mall_sub_other_dynamic_fragment, container, false);
        HomeMainActivity.id_back.setVisibility(View.VISIBLE);
        HomeMainActivity.toolbar.setVisibility(View.VISIBLE);
        HomeMainActivity.iv_logout.setVisibility(View.VISIBLE);
        Log.e("MEYAHA", "onCreateView: ");
        btn_count.setVisibility(View.VISIBLE);
        if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) == 0) {
            btn_count.setVisibility(View.GONE);
        } else {
            btn_count.setVisibility(View.VISIBLE);
            btn_count.setText("" + SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT));
        }
        HomeMainActivity.iv_logout.setImageDrawable(getResources().getDrawable(R.drawable.ic_shopping_cart));
        HomeMainActivity.iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected != 2) {
                    selected = 2;
                    id_home.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_account.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_cart.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_blue_select), PorterDuff.Mode.SRC_IN);
                    id_order.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    id_offer.setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint_grey_unselect), PorterDuff.Mode.SRC_IN);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, new FragmentCart());
                    fragmentTransaction.commit();
                }
            }
        });

        allChildren = Share.mall_dynamic_sub_category_list;
        getDisplaySize();
        findViews(v);
        setHeader();
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) == 0) {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);
        } else {
            HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
        }
        getDisplaySize();
    }

    private void setHeader() {
        TextView title = getActivity().findViewById(R.id.title);
        title.setText(Share.header_name);

        ImageView imageView = getActivity().findViewById(R.id.id_back);
        // }
        imageView.setOnClickListener(view -> {
            if (Share.mall_dynamic_sub_category_list.size() > 0) {
                int level = Share.mall_dynamic_sub_category_list.get(0).getLevel();
                for (int i = 0; i < Share.click_positions.size(); i++) {
                    Log.e("position", "===>" + Share.click_positions.get(i) + "///" + level);
                }

                List<mall_AllChild> alldata = new ArrayList<>();
                alldata = Share.mall_main_category_data;
                for (int i = 0; i < level - 2; i++) {
                    Share.header_name = alldata.get(Share.click_positions.get(i)).getName();
                    alldata = alldata.get(Share.click_positions.get(i)).getAllChilds();
                }

                Share.mall_dynamic_sub_category_list = alldata;
                Share.click_positions.remove(Share.click_positions.size() - 1);

                Log.e("SIZE", "onClick: ===========>" + Share.click_positions.size() + "//" + level + "//" + Share.mall_dynamic_sub_category_list.size());
                if (level == 2) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
                    fragmentTransaction.commit();
                } else {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, new Mall_FragmentOne());
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void findViews(View v) {
        rv_offer = v.findViewById(R.id.fragOne_rv_offer);
        myRecyclerAdapter = new mall_sub_category_data(getActivity(), Share.mall_dynamic_sub_category_list);
        rv_offer.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_offer.setAdapter(myRecyclerAdapter);

        myRecyclerAdapter.notifyDataSetChanged();
        Log.e("TAG", "findViews: size" + rv_offer.getChildCount());
        myRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickLister(View v, int position) {
                Share.category_id = Share.mall_dynamic_sub_category_list.get(position).getId();
                if (Share.mall_dynamic_sub_category_list.get(position).getAllChilds() != null && Share.mall_dynamic_sub_category_list.get(position).getAllChilds().size() > 0) {
                    Share.click_positions.add(position);
                    Share.header_name = Share.mall_dynamic_sub_category_list.get(position).getName();
                    Log.e("LEVEL", "onItemClickLister: " + Share.mall_dynamic_sub_category_list.get(position).getLevel());
                    Share.mall_dynamic_sub_category_list = Share.mall_dynamic_sub_category_list.get(position).getAllChilds();
                    ((HomeMainActivity) getActivity()).mall_fragment_transact(new Mall_FragmentOne());
                } else {

                    get_other_category(Share.mall_dynamic_sub_category_list.get(position).getId(), position);
                }
            }
        });
    }

    public void get_other_category(final Integer id, final Integer position) {


        //pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);
        showProgressDialog(getActivity());

        APIService apiService = new MainApiClient(getActivity()).getApiInterface();
        int user_id;
        if (!SharedPrefs.getBoolean(getActivity(), Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(getActivity(), Share.key_ + RegReq.id));
        }
        Call<mall_main_category_response_click_data> call = apiService.call_main_categoru_subdata(id, user_id, Share.countryCodeValue, "whats_new", Locale.getDefault().getLanguage());

        call.enqueue(new Callback<mall_main_category_response_click_data>() {
            @Override
            public void onResponse(Call<mall_main_category_response_click_data> call, Response<mall_main_category_response_click_data> response) {
                //pd.dismiss();
                hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                        Share.isinternational = response.body().getIs_international();

                        firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
                        logger = AppEventsLogger.newLogger(getActivity());
                        Bundle params = new Bundle();
                        params.putInt("mall_category_open", 1);
                        firebaseAnalytics.logEvent("mall_category_open", params);

                        Share.symbol = response.body().getCurrency_symbol();
                        Share.category_header_name = Share.mall_dynamic_sub_category_list.get(position).getName();
                        Share.filtered_response.clear();
                        Share.subresponse_data = response.body().getData();
                        Share.checked_arraylist.clear();
                        Share.available_filters = Share.mall_dynamic_sub_category_list.get(position).getAvailableFilter();
                        Log.e("SIZE", "onResponse: " + Share.subresponse_data.size());
                        Log.e("SIZE", "onResponse: " + Share.mall_dynamic_sub_category_list.get(position).getAvailableFilter().size());
                        mall_filter_activity.mactivity = null;
                        Share.base_filter_url = "";
                        Intent intent = new Intent(getActivity(), mall_category_activity.class);
                        intent.putExtra("category_id", "" + id);
                        getActivity().startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<mall_main_category_response_click_data> call, Throwable t) {
                t.printStackTrace();
                //pd.dismiss();
                hideProgressDialog();
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                        alertDialog = null;
                    }
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_other_category(Share.mall_dynamic_sub_category_list.get(position).getId(), position);
                        }
                    });
                    alertDialog.show();
                } else {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                        alertDialog = null;
                    }
                    alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            get_other_category(Share.mall_dynamic_sub_category_list.get(position).getId(), position);
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void getDisplaySize() {
        Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
