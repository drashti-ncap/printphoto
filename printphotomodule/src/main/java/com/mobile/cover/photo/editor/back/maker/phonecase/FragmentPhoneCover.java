package com.mobile.cover.photo.editor.back.maker.phonecase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseFragment;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentCart;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentHome;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.CompanyRecyclerAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.AllChild;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;
import com.mobile.cover.photo.editor.back.maker.model.sub_category_model_details;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt.SEARCH_BRAND_NAME;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.btn_count;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_account;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_cart;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_home;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_offer;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_order;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.selected;

public class FragmentPhoneCover extends PrintPhotoBaseFragment {
    private static final long MIN_CLICK_INTERVAL = 1000;
    public static TextView tv_no_fnd;
  //  ProgressDialog pd;
    RecyclerView rv_offer;
    CompanyRecyclerAdapter myRecyclerAdapter;
    ImageView id_back;
    EditText fragOne_ed_search;
    AlertDialog alertDialog;
    private List<AllChild> allChildren = new ArrayList<>();
    private long mLastClickTime;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dynamic_fragment, container, false);
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
                    FragmentCart cart =  new FragmentCart();
                    cart.setContext(getActivity());
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, cart);
                    fragmentTransaction.commit();
                }
            }
        });

        allChildren = Share.dynamic_sub_category_list;
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 1) {
//                    HomeMainActivity.selected = 0;
//                    if (Share.dynamic_sub_category_list.size() != 0) {
//                        Share.dynamic_sub_category_list.remove(Share.dynamic_sub_category_list.size() - 1);
//                    }
//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.frg_main, new Home());
//                    fragmentTransaction.commit();
//                } else {
                if (fragOne_ed_search.getText().toString().equalsIgnoreCase("")) {
                    fragOne_ed_search.setHint(getString(R.string.search_phone_brand));
                    if (Share.dynamic_sub_category_list.size() > 0) {
//                        Share.dynamic_sub_category_list.remove(Share.dynamic_sub_category_list.size() - 1);

                        int level = Share.dynamic_sub_category_list.get(0).getLevel();
                        for (int i = 0; i < Share.click_positions.size(); i++) {
                            Log.e("position", "===>" + Share.click_positions.get(i) + "///" + level);
                        }

                        List<AllChild> alldata = new ArrayList<>();
                        alldata = Share.main_category_data;
                        Log.e("SIZE_MAIN_DATA", "onClick: " + alldata.size() + "//////" + Share.click_positions.size());
                        for (int i = 0; i < level - 2; i++) {
                            Share.header_name = getString(R.string.phone_case);
                            //      Log.e("ERROR", "onClick: "+Share.click_positions.get(i)+"/////"+i);
                            //    Log.e("ERROR", "onClick: "+alldata.get(Share.click_positions.get(i)));
                            Log.e("SIZE", "onClick: ===========>" + (Share.click_positions.get(i) + "//" + level + "//" + alldata.size()));
                            alldata = alldata.get(Share.click_positions.get(i)).getAllChilds();
                        }
                        Share.dynamic_sub_category_list = alldata;
                        Share.search_dynamic_sub_category_list = alldata;
                        Share.click_positions.remove(Share.click_positions.size() - 1);


                        if (level == 2) {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
                            fragmentTransaction.commit();
                        } else {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frg_main, new FragmentPhoneCover());
                            fragmentTransaction.commit();
                        }

                    }
                } else {
                    fragOne_ed_search.setText("");
                    fragOne_ed_search.setHint(getString(R.string.search_phone_brand));
                    tv_no_fnd.setVisibility(View.GONE);
                }
            }
        });
    }

    private void findViews(View v) {
        rv_offer = v.findViewById(R.id.fragOne_rv_offer);
        fragOne_ed_search = v.findViewById(R.id.fragOne_ed_search);
        tv_no_fnd = v.findViewById(R.id.tv_no_fnd);


        myRecyclerAdapter = new CompanyRecyclerAdapter(getActivity(), Share.dynamic_sub_category_list);
        rv_offer.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_offer.setAdapter(myRecyclerAdapter);

        rv_offer.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });
        Log.e("TAG", "findViews: size" + rv_offer.getChildCount());
        myRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickLister(View v, int position) {
                long currentClickTime = SystemClock.uptimeMillis();
                long elapsedTime = currentClickTime - mLastClickTime;
                mLastClickTime = currentClickTime;
                if (elapsedTime <= MIN_CLICK_INTERVAL)
                    return;


//                if (Share.dynamic_sub_category_list.get(position).getLevel()!=1){
//                    fragOne_ed_search.setHint("Search for "+Share.dynamic_sub_category_list.get(position).getName()+" model...");
//                }else {
//                    fragOne_ed_search.setHint("Search for phone brand..");
//                }

                if (Share.dynamic_sub_category_list.get(position).getName().equalsIgnoreCase("Looking For Other Brand")) {
                    Share.click_looking_for_other = true;
                    fragOne_ed_search.setText("");
                    ((HomeMainActivity) getActivity()).request_cover_fragment_transact(new FragmentRequestModelBrand());
                } else if (Share.dynamic_sub_category_list.get(position).getAllChilds() != null && Share.dynamic_sub_category_list.get(position).getAllChilds().size() > 0) {
                    Log.e("POSITION", "onItemClickLister: " + position);
                    if (fragOne_ed_search.getText().toString().equalsIgnoreCase("")) {
                        Share.click_positions.add(position);
                    } else {
                        for (int i = 0; i < Share.search_dynamic_sub_category_list.size(); i++) {
                            if (Share.search_dynamic_sub_category_list.get(i).getId() == Share.dynamic_sub_category_list.get(position).getId()) {
                                Log.e("click pos", "===>" + i);
                                Share.click_positions.add(i);
                                break;
                            }
                        }
                    }
                    Share.category_id = Share.dynamic_sub_category_list.get(position).getId();
                    Share.search_dynamic_sub_category_list = Share.dynamic_sub_category_list.get(position).getAllChilds();
                    Share.dynamic_sub_category_list = Share.dynamic_sub_category_list.get(position).getAllChilds();
//                    AllChild allChild = new AllChild();
//                    allChild.setName("Looking for other brand");
//                    if (!Share.dynamic_sub_category_list.contains("Looking For Other Brand")) {
//                        Share.dynamic_sub_category_list.add(allChild);
//                    }
//                    Share.dynamic_sub_category_list.remove(Share.dynamic_sub_category_list.size()-1);
                    ((HomeMainActivity) getActivity()).phone_cover_fragment_transact(new FragmentPhoneCover());
                } else {

                    Share.brand_name = Share.dynamic_sub_category_list.get(position).getName();
                    Share.category_id = Share.dynamic_sub_category_list.get(position).getId();
                    load_data();
                }
            }
        });

        fragOne_ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Share.dynamic_sub_category_list = myRecyclerAdapter.filter(charSequence.toString());

                // ToDo: Changes mae by Jignesh Patel
                FBEventsKt.logSearchedEvent(getActivity(), SEARCH_BRAND_NAME, charSequence.toString(), true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void load_data() {


        DataHelperKt.saveModelDetailsData(getActivity(), new ArrayList<>());




        //pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);
        showProgressDialog(getActivity());

        APIService apiService = new MainApiClient(getActivity()).getApiInterface();
        Log.e("CATEGORYID", "load_data: " + Share.category_id);

        int user_id;
        if (!SharedPrefs.getBoolean(getActivity(), Share.key_reg_suc)) {
            user_id = 0000;
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(getActivity(), Share.key_ + RegReq.id));
        }

        Call<sub_category_model_details> call = apiService.getProducts("" + Share.category_id, Share.countryCodeValue, String.valueOf(user_id), Locale.getDefault().getLanguage());
        call.enqueue(new Callback<sub_category_model_details>() {
            @Override
            public void onResponse(Call<sub_category_model_details> call, Response<sub_category_model_details> response) {
                //pd.dismiss();
                hideProgressDialog();
                if (response.body().getResponseCode().equalsIgnoreCase("1")) {
                    Share.isinternational = response.body().getIs_international();
                    fragOne_ed_search.setText("");
                    Share.subDataModelDetailsArrayList_search = response.body().getData();

                    DataHelperKt.saveModelDetailsData(getActivity(), response.body().getData());

                    Share.symbol = response.body().getCurrency_symbol();
                    ((HomeMainActivity) getActivity()).select_company_fragment_transact(new SelectCompanyMobel());
                } else {
                    Toast.makeText(getActivity(), response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<sub_category_model_details> call, Throwable t) {
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
                            load_data();
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
                            load_data();
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
