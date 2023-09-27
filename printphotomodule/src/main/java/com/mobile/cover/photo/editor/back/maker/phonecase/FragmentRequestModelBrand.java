package com.mobile.cover.photo.editor.back.maker.phonecase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.brand_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.model_response_data;
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.main_brand_model_data_response;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.Request_RecyclerAdapter;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient;
import com.mobile.cover.photo.editor.back.maker.model.request_final_brand;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt.SEARCH_BRAND_MODEL;


public class FragmentRequestModelBrand extends Fragment {
    public static EditText atv_brand_model;
    public static TextView tv_no_fnd;
    public static Button btn_request, btn_request_written;
    public ArrayList<request_final_brand> new_model;
    ProgressDialog pd;
    String model;
    RecyclerView rv_brandlist;
    Request_RecyclerAdapter request_recyclerAdapter;
    ArrayList<request_final_brand> spinnermodelArray = new ArrayList<request_final_brand>();
    AutoCompleteTextView sp_brand, sp_model;
    List<brand_response_data> datumList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_request_model, container, false);
        HomeMainActivity.id_back.setVisibility(View.VISIBLE);
        HomeMainActivity.toolbar.setVisibility(View.VISIBLE);
        HomeMainActivity.btn_count.setVisibility(View.GONE);
        HomeMainActivity.iv_logout.setVisibility(View.GONE);
        setHeader();
        findViews(v);
        initview();
        return v;

    }

    private void getMainData() {

        spinnermodelArray.clear();
        Share.request_brand.clear();
        pd = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);

        APIService apiService = new MainApiClient(getContext()).getApiInterface();

        Call<main_brand_model_data_response> call = apiService.get_brand_model_data();


        call.enqueue(new Callback<main_brand_model_data_response>() {
            @Override
            public void onResponse(Call<main_brand_model_data_response> call, retrofit2.Response<main_brand_model_data_response> response) {
                main_brand_model_data_response customimage_response = response.body();
                if (customimage_response.getResponseCode().equalsIgnoreCase("1")) {
                    pd.dismiss();

                    datumList = customimage_response.getData();
                    for (brand_response_data datum : datumList) {
                        List<model_response_data> model_response_data = datum.getModal();
                        for (model_response_data model_datum : model_response_data) {
                            request_final_brand request_final_brand = new request_final_brand("" + model_datum.getManufacturer() + " " + model_datum.getModelName(), "" + model_datum.getId(),
                                    "" + model_datum.getmodel_id());
                            spinnermodelArray.add(request_final_brand);
                        }
                    }

                    request_recyclerAdapter = new Request_RecyclerAdapter(getActivity(), spinnermodelArray);
                    rv_brandlist.setAdapter(request_recyclerAdapter);

                    Share.request_brand.addAll(spinnermodelArray);
                }
            }

            @Override
            public void onFailure(Call<main_brand_model_data_response> call, Throwable t) {
                Log.e("TIMEOUT", "onFailure: " + t.getLocalizedMessage());
                Log.e("TIMEOUT", "onFailure: " + t.getMessage());
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.time_out));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.connect_time_out));
                    alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pd.dismiss();
                            getMainData();

                        }
                    });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle(getString(R.string.internet_connection));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.slow_connect));
                    alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            pd.dismiss();
                            getMainData();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }


    private void initview() {
        rv_brandlist.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_brandlist.scrollToPosition(spinnermodelArray.size());
        rv_brandlist.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });


        atv_brand_model.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                spinnermodelArray = request_recyclerAdapter.filter(charSequence.toString());


                // ToDo: Changes mae by Jignesh Patel
                FBEventsKt.logSearchedEvent(getActivity(), SEARCH_BRAND_MODEL, charSequence.toString(), true);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void findViews(View v) {
        atv_brand_model = v.findViewById(R.id.atv_brand_model);
        btn_request = v.findViewById(R.id.btn_request);
        btn_request_written = v.findViewById(R.id.btn_request_written);
        tv_no_fnd = v.findViewById(R.id.tv_no_fnd);
        rv_brandlist = v.findViewById(R.id.rv_brandlist);


        if (Share.request_brand.size() == 0) {
            getMainData();
        } else {
            spinnermodelArray.addAll(Share.request_brand);
            request_recyclerAdapter = new Request_RecyclerAdapter(getActivity(), spinnermodelArray);
            rv_brandlist.setAdapter(request_recyclerAdapter);
        }
//        btn_request.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendrequest(model, SharedPrefs.getString(getActivity(), SharedPrefs.uid));
//
//            }
//        });
//        ArrayAdapter<String> model_adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, spinnermodelArray);
//        atv_brand_model.setAdapter(model_adapter);
//
//        atv_brand_model.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                model=parent.getItemAtPosition(position).toString();
//            }
//        });

        ImageView imageView = getActivity().findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                selected = 7;
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frg_main, new FragmentPhoneCover());
                fragmentTransaction.commit();
            }
        });
    }

    private void setHeader() {
        TextView title = getActivity().findViewById(R.id.title);
        title.setText(getString(R.string.request_model_brand));
    }


    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}
