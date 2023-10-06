package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter.HomeSubAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ModelListActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener;
import com.mobile.cover.photo.editor.back.maker.model.AllChild;

import java.util.ArrayList;
import java.util.List;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.btn_count;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_account;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_cart;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_home;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_offer;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.id_order;
import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity.selected;

public class FragmentHomeSub extends Fragment {
    private static final long MIN_CLICK_INTERVAL = 1000;
    ProgressDialog pd;
    RecyclerView rv_offer;
    HomeSubAdapter myRecyclerAdapter;
    ImageView id_back;
    EditText fragOne_ed_search;
    TextView title;
    private List<AllChild> allChildren = new ArrayList<>();
    private long mLastClickTime;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.other_dynamic_fragment, container, false);
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
                    FragmentCart cart = new FragmentCart();
                    cart.setContext(getActivity());
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frg_main,cart);
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
        if (Share.login_back) {
            Share.login_back = false;
            HomeMainActivity.selected = 0;

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
            fragmentTransaction.commit();
        } else {
            if (SharedPrefs.getInt(getActivity(), SharedPrefs.CART_COUNT) == 0) {
                HomeMainActivity.tv_nudge_cart_count.setVisibility(View.GONE);
            } else {
                HomeMainActivity.tv_nudge_cart_count.setVisibility(View.VISIBLE);
            }
            getDisplaySize();
        }
    }

    private void setHeader() {
        TextView title = getActivity().findViewById(R.id.title);
        title.setText(Share.header_name);

        ImageView imageView = getActivity().findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Share.dynamic_sub_category_list.size() > 0) {
                    int level = Share.dynamic_sub_category_list.get(0).getLevel();
                    for (int i = 0; i < Share.click_positions.size(); i++) {
                        Log.e("position", "===>" + Share.click_positions.get(i) + "///" + level);
                    }

                    List<AllChild> alldata = new ArrayList<>();
                    alldata = Share.main_category_data;
                    for (int i = 0; i < level - 2; i++) {
                        Share.header_name = alldata.get(Share.click_positions.get(i)).getName();
                        alldata = alldata.get(Share.click_positions.get(i)).getAllChilds();
                    }
                    Share.dynamic_sub_category_list = alldata;
                    Share.click_positions.remove(Share.click_positions.size() - 1);

                    Log.e("SIZE", "onClick: ===========>" + Share.click_positions.size() + "//" + level + "//" + Share.dynamic_sub_category_list.size());
                    if (level == 2) {
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frg_main, new FragmentHome());
                        fragmentTransaction.commit();
                    } else {
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frg_main, new FragmentHomeSub());
                        fragmentTransaction.commit();
                    }
                }
            }
            // }
        });
    }

    private void findViews(View v) {
        rv_offer = v.findViewById(R.id.fragOne_rv_offer);
        myRecyclerAdapter = new HomeSubAdapter(getActivity(), Share.dynamic_sub_category_list);
        rv_offer.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_offer.setAdapter(myRecyclerAdapter);

        Log.e("DYNAMIC_SIZE", "findViews: " + Share.dynamic_sub_category_list.size());
        myRecyclerAdapter.notifyDataSetChanged();
        Log.e("TAG", "findViews: size" + rv_offer.getChildCount());
        myRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickLister(View v, int position) {
//                long currentClickTime = SystemClock.uptimeMillis();
//                long elapsedTime = currentClickTime - mLastClickTime;
//                mLastClickTime = currentClickTime;
//                if (elapsedTime <= MIN_CLICK_INTERVAL)
//                    return;
                Share.main_category_id = Share.dynamic_sub_category_list.get(position).getParentId();
                Share.category_id = Share.dynamic_sub_category_list.get(position).getId();
                if (Share.dynamic_sub_category_list.get(position).getAllChilds() != null && Share.dynamic_sub_category_list.get(position).getAllChilds().size() > 0) {
                    Share.click_positions.add(position);
                    Share.header_name = Share.dynamic_sub_category_list.get(position).getName();
                    Share.dynamic_sub_category_list = Share.dynamic_sub_category_list.get(position).getAllChilds();
                    ((HomeMainActivity) getActivity()).fragment_transact(new FragmentHomeSub());
                } else {
                    Share.brand_name = Share.dynamic_sub_category_list.get(position).getName();
                    Intent intent = new Intent(getContext(), ModelListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);


                    DataHelperKt.saveProductId(requireActivity(),Share.dynamic_sub_category_list.get(position).getId());


                    intent.putExtra("name", Share.dynamic_sub_category_list.get(position).getName());
                    startActivity(intent);
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
