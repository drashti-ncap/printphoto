package com.mobile.cover.photo.editor.back.maker.phonecase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentAccount;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.LogInActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.VideoActivity;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.CompanyModelRecyclerAdapter;
import com.mobile.cover.photo.editor.back.maker.fragment.SelectCompany;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectCompanyMobel extends Fragment {
    private static final long MIN_CLICK_INTERVAL = 1000;
    public static TextView tv_no_fnd;
    EditText ed_search;
    Button tv_request;
    CompanyModelRecyclerAdapter companyRecyclerAdapter;
    ProgressDialog pd;
    List<model_details_data> subDataModelDetailsArrayList = new ArrayList<>();
    List<model_details_data> subDataModelDetailsArrayList_temp = new ArrayList<>();
    RecyclerView recyclerView;
    String model;
    private long mLastClickTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_select_company, container, false);
        HomeMainActivity.id_back.setVisibility(View.VISIBLE);
        HomeMainActivity.toolbar.setVisibility(View.VISIBLE);
        HomeMainActivity.btn_count.setVisibility(View.GONE);
        HomeMainActivity.iv_logout.setVisibility(View.VISIBLE);
        HomeMainActivity.iv_logout.setImageDrawable(getResources().getDrawable(R.drawable.ic_help));
        HomeMainActivity.iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                startActivity(intent);
            }
        });
        setHeader();
        findViews(v);
        intView();

        return v;

    }

    private void setHeader() {
        TextView title = getActivity().findViewById(R.id.title);
        title.setText(getString(R.string.select_model));

        ImageView imageView = getActivity().findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frg_main, new FragmentPhoneCover());
                fragmentTransaction.commit();
            }
        });
    }

    private void findViews(View v) {
        ed_search = v.findViewById(R.id.ed_search);


        if (Share.brand_name != null) {
            ed_search.setHint(getString(R.string.search_for) + " " + Share.brand_name.toLowerCase() + " " + getString(R.string.model));
        } else {
            ed_search.setHint(getString(R.string.search_for) + getString(R.string.model));

        }


        recyclerView = v.findViewById(R.id.recyclerview);
        tv_request = v.findViewById(R.id.tv_request);
        tv_no_fnd = v.findViewById(R.id.tv_no_fnd);
        tv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("USERID", "onClick:===========> " + SharedPrefs.uid);
                final String userid = SharedPrefs.getString(getActivity(), SharedPrefs.uid);
                Log.e("USERID", "onClick:===========333> " + userid);

                if (userid.equalsIgnoreCase("uid")) {
                    ImageView id_home = getActivity().findViewById(R.id.id_home);
                    ImageView id_order = getActivity().findViewById(R.id.id_order);
                    ImageView id_cart = getActivity().findViewById(R.id.id_cart);
                    ImageView id_account = getActivity().findViewById(R.id.id_account);
                    id_home.setImageResource(R.drawable.ic_home);
                    id_account.setImageResource(R.drawable.ic_select_account);
                    id_cart.setImageResource(R.drawable.ic_cart);
                    id_order.setImageResource(R.drawable.ic_order);
                    HomeMainActivity.selected = 3;
                    FragmentAccount account = new FragmentAccount();
                    account.setContext(getActivity());
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, account);
                    fragmentTransaction.commit();
                } else {
                }
            }
        });

    }

    private void intView() {


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });


        if (!DataHelperKt.getModelDetailsData(requireActivity()).isEmpty()) {
            subDataModelDetailsArrayList_temp.clear();
            subDataModelDetailsArrayList.clear();
            subDataModelDetailsArrayList_temp.addAll(DataHelperKt.getModelDetailsData(requireActivity()));
            subDataModelDetailsArrayList.addAll(DataHelperKt.getModelDetailsData(requireActivity()));
            Collections.sort(subDataModelDetailsArrayList, new Comparator<model_details_data>() {
                @Override
                public int compare(model_details_data p1, model_details_data p2) {
                    return p1.getModalName().compareTo(p2.getModalName());
                }
            });
            companyRecyclerAdapter = new CompanyModelRecyclerAdapter(getActivity(), subDataModelDetailsArrayList);


            recyclerView.setAdapter(companyRecyclerAdapter);
            companyRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClickLister(View v, int position) {
                    long currentClickTime = SystemClock.uptimeMillis();
                    long elapsedTime = currentClickTime - mLastClickTime;
                    mLastClickTime = currentClickTime;
                    if (elapsedTime <= MIN_CLICK_INTERVAL)
                        return;


                    DataHelperKt.saveModelData(requireActivity(), subDataModelDetailsArrayList.get(position));


                    if (subDataModelDetailsArrayList.get(position).getModalName().equalsIgnoreCase("Looking for other Model")) {
                        Log.e("USERID", "onClick:===========> " + SharedPrefs.uid);
                        final String userid = SharedPrefs.getString(getActivity(), SharedPrefs.uid);
                        Log.e("USERID", "onClick:===========333> " + userid);

                        if (userid.equalsIgnoreCase("uid") || userid.equalsIgnoreCase("")) {
                            Intent intent = new Intent(getContext(), LogInActivity.class);
                            startActivity(intent);
                        } else {
                            Share.requestfragment = 0;
                        }
                    } else {
                        if (subDataModelDetailsArrayList.get(position).getAvailable_stock().equalsIgnoreCase("0")) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setCancelable(false);
                            alertDialog.setTitle(getString(R.string.alert));
                            alertDialog.setMessage(getString(R.string.out_of_stock));
                            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        } else {

                            ((HomeMainActivity) getActivity()).cover_style_fragment_transact(new CoverStyle());
                        }
                    }
                }
            });
        } else {
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialogbrand_layout);
            dialog.show();
            Button ButtonCancelPick = dialog.findViewById(R.id.ButtonCancelPick);
            TextView textView = dialog.findViewById(R.id.btn_push);
            textView.setText(getString(R.string.comming_soon));
            ButtonCancelPick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main, new SelectCompany());
                    fragmentTransaction.commit();
                }
            });
        }
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                try {
                    if (!subDataModelDetailsArrayList.isEmpty()) {
                        subDataModelDetailsArrayList = companyRecyclerAdapter.filter(charSequence.toString());
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
