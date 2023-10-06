package com.mobile.cover.photo.editor.back.maker.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.LogInActivity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.VideoActivity;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.CompanyRecyclerAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentAccount;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentHome;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.AllChild;
import com.mobile.cover.photo.editor.back.maker.model.getbrand_data;
import com.mobile.cover.photo.editor.back.maker.phonecase.FragmentRequestModelBrand;

import java.util.ArrayList;
import java.util.List;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.selected;

public class SelectCompany extends Fragment {
    public static TextView tv_no_fnd;
    EditText ed_search;
    Button tv_request;
    CompanyRecyclerAdapter companyRecyclerAdapter;
    ProgressDialog progressDialog;
    ArrayList<AllChild> subDataArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    private List<getbrand_data> sqlist = new ArrayList<getbrand_data>();

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
        if (Share.sub_new_main_category_data.size() != 0) {
            Share.subDataArrayList.clear();
            subDataArrayList.clear();
            subDataArrayList.addAll(Share.sub_new_main_category_data);
            Share.subDataArrayList.addAll(Share.sub_new_main_category_data);
            companyRecyclerAdapter = new CompanyRecyclerAdapter(getActivity(), Share.sub_new_main_category_data);
            recyclerView.setAdapter(companyRecyclerAdapter);
            companyRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClickLister(View v, int position) {
                    if (Share.disp_subdata_arraylist.get(position).getName().equalsIgnoreCase("Looking for other Brand")) {
                        final String userid = SharedPrefs.getString(getActivity(), SharedPrefs.uid);
                        if (userid.equalsIgnoreCase("uid") || userid.equalsIgnoreCase("")) {
                            Intent intent = new Intent(getContext(), LogInActivity.class);
                            startActivity(intent);
                        } else {
                            Share.requestfragment = 1;
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frg_main, new FragmentRequestModelBrand());
                            fragmentTransaction.commitAllowingStateLoss();
                        }
                    } else {
                        selected = 6;
                    }
                    Share.disp_subdata_arraylist.clear();
                    Share.disp_subdata_arraylist.addAll(Share.subDataArrayList);

                }
            });
        }
        return v;

    }

    private void setHeader() {
        TextView title = getActivity().findViewById(R.id.title);
        title.setText(getString(R.string.select_brand));

        ImageView imageView = getActivity().findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = 0;
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void findViews(View v) {
        ed_search = v.findViewById(R.id.ed_search);
        recyclerView = v.findViewById(R.id.recyclerview);
        tv_request = v.findViewById(R.id.tv_request);
        tv_no_fnd = v.findViewById(R.id.tv_no_fnd);
        tv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userid = SharedPrefs.getString(getActivity(), SharedPrefs.uid);

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
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialogcomplain_layout);
                    dialog.show();
                    final EditText ed_complain = dialog.findViewById(R.id.ed_complain);
                    ed_complain.setHint(getString(R.string.enter_brand_model));
                    TextView tv_title = dialog.findViewById(R.id.tv_title);
                    tv_title.setText(getString(R.string.request_your_device));
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
                            if (!complain.equalsIgnoreCase("")) {
                            } else {
                                Toast.makeText(getActivity(), getString(R.string.please_enter_your_brand_name), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void intView() {


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.scrollToPosition(subDataArrayList.size());
        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });


        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                subDataArrayList = companyRecyclerAdapter.filter(charSequence.toString());

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
