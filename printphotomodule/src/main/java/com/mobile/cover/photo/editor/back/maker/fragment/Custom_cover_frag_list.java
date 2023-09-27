package com.mobile.cover.photo.editor.back.maker.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentHome;
import com.mobile.cover.photo.editor.back.maker.model.SubData;

import java.util.ArrayList;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.selected;

public class Custom_cover_frag_list extends Fragment {
    EditText ed_search;
    ArrayList<SubData> subDataArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_select_company, container, false);
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        HomeMainActivity.id_back.setVisibility(View.VISIBLE);
        HomeMainActivity.toolbar.setVisibility(View.VISIBLE);
        HomeMainActivity.btn_count.setVisibility(View.GONE);
        HomeMainActivity.iv_logout.setVisibility(View.GONE);
        setHeader();
        findViews(v);
        intView();
        return v;

    }

    private void setHeader() {
        TextView title = getActivity().findViewById(R.id.title);
        title.setText("Select");
        TextView title1 = getActivity().findViewById(R.id.title1);
        title1.setText(" Cover");

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


    private void findViews(View v) {
        ed_search = v.findViewById(R.id.ed_search);
        recyclerView = v.findViewById(R.id.recyclerview);
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


//        ed_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                subDataArrayList = companyRecyclerAdapter.filter(charSequence.toString());
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
