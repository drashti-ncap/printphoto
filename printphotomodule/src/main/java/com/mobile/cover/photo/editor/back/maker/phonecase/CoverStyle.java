package com.mobile.cover.photo.editor.back.maker.phonecase;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.mobile.cover.photo.editor.back.maker.Commen.Share;
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs;
import com.mobile.cover.photo.editor.back.maker.R;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseFragment;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.FBEventsKt;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.fragments.FragmentHome;
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.DataHelperKt;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Default_image_activity;
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.VideoActivity;
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq;
import com.mobile.cover.photo.editor.back.maker.model.model_details_data;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class CoverStyle extends PrintPhotoBaseFragment {

    TextView id_dummy_price, id_price, id_model, id_size, id_discription, tv_select_case;
    ImageView img_cover;
    LinearLayout layout1, layout2, layout3, layout4, layout5;
//    ProgressDialog progressDialog;
    String total_amount = "100";
    String discount = "10";
    String paid_amount = "100";
//    private ProgressDialog pDialog;
    private String[] str = new String[3];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_cover_style, container, false);
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
        getDisplaySize();
        findViews(v);
        setDimens();
        intView();
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        model_details_data modelData = DataHelperKt.getModelData(requireActivity());

        // ToDo: Changes mae by Jignesh Patel
        assert modelData != null;
        String proId = modelData.getModelId().toString();
        String proName = modelData.getModalName();
        String proPrice = modelData.getPrice();
        FBEventsKt.logViewedContentEvent(getActivity(), proName, proId, proPrice);
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
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void intView() {
        Glide.with(getContext()).load(R.drawable.banner_img).into(img_cover);


        model_details_data modelData = DataHelperKt.getModelData(requireActivity());

        if (modelData == null && modelData.getPrice() ==null ){
            Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
            return;
        }

        if (modelData.getPrice() != null && !modelData.getPrice().equalsIgnoreCase("null")) {

            id_price.setText(Share.symbol + modelData.getPrice());
        }


        if (modelData.getDummyPrice() != null && !modelData.getDummyPrice().equalsIgnoreCase("null") && !modelData.getDummyPrice().equalsIgnoreCase("0")) {

            id_dummy_price.setText(Share.symbol + modelData.getDummyPrice());


        } else {

            id_dummy_price.setVisibility(View.GONE);
        }

        if (modelData.getModalName() != null && !modelData.getModalName().equalsIgnoreCase("null")) {
            id_model.setText(modelData.getModalName());
        }

        if (modelData.getWidth() != null && !modelData.getWidth().equalsIgnoreCase("null")) {

            id_size.setText("" +modelData.getWidth() + " X " + modelData.getHeight() + " CM");

        }


        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                model_details_data modelData = DataHelperKt.getModelData(requireActivity());

                try {
                    if (modelData.getNMaskImage() != null && !modelData.getNMaskImage().equalsIgnoreCase("null")) {
                        str[0] = modelData.getNMaskImage();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.something_went_wrong_try_agaian), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (modelData.getNNewModalImage() != null && !modelData.getNNewModalImage().equalsIgnoreCase("null")) {
                        str[1] = modelData.getNNewModalImage();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.something_went_wrong_try_agaian), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (modelData.getNModalImage() != null && !modelData.getNModalImage().equalsIgnoreCase("null")) {
                        str[2] = modelData.getNModalImage();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.something_went_wrong_try_agaian), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                    return;
                }


                if (SharedPrefs.getString(getActivity(), SharedPrefs.videoplay).equalsIgnoreCase("1")) {
                    Log.e("TAG", "onClick: ========>Clicked");
                    GetXMLTask task = new GetXMLTask();
                    task.execute(str);
                } else {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(getString(R.string.tutorial));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.learn_video));
                    alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(getActivity(), VideoActivity.class);
                            startActivity(intent);
                        }
                    });
                    alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            GetXMLTask task = new GetXMLTask();
                            task.execute(str);
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


    public double setDiscount(String price, String discount) {
        double dis = Double.parseDouble(discount);
        double pri = Double.parseDouble(price);
        if (dis == 0) {
            Log.e("TAG", "setDiscount: if=>" + pri);
            return pri;

        } else {
            Log.e("TAG", "setDiscount: " + (pri / 100) * dis);

            return pri - ((pri / 100) * dis);

        }
    }

    private void setDimens() {
        layout1.getLayoutParams().height = (int) (Share.screenHeight / 3.1);
        int height = Share.screenHeight / 14;
        layout2.getLayoutParams().height = height;
        layout3.getLayoutParams().height = height;
        layout4.getLayoutParams().height = height;
        layout5.getLayoutParams().height = Share.screenHeight / 11;
    }

    private void setHeader() {
        TextView title = getActivity().findViewById(R.id.title);
        title.setText(getString(R.string.cover_style));

        ImageView imageView = getActivity().findViewById(R.id.id_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void getDisplaySize() {
        Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Share.screenWidth = size.x;
        Share.screenHeight = size.y;
    }

    private void findViews(View v) {
        id_dummy_price = v.findViewById(R.id.id_dummy_price);
        id_price = v.findViewById(R.id.tv_total_amount);
        id_model = v.findViewById(R.id.id_model);
        id_size = v.findViewById(R.id.id_size);
        id_discription = v.findViewById(R.id.id_discription);
        img_cover = v.findViewById(R.id.img_cover);
        layout1 = v.findViewById(R.id.layout1);
        layout2 = v.findViewById(R.id.layout2);
        layout3 = v.findViewById(R.id.layout3);
        layout4 = v.findViewById(R.id.layout4);
        layout5 = v.findViewById(R.id.layout5);
    }

    // Creates Bitmap from InputStream and returns it
    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }

    // Makes HttpURLConnection and returns InputStream
    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }

    private class GetXMLTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("TAG", "onPreExecute: =======>excute");
//            progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.loading), true, false);
//            progressDialog.show();
            showProgressDialog(getActivity());
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... strings) {

            ArrayList<Bitmap> map = new ArrayList<Bitmap>();
            try {
                //enhanced for statement, mainly used for arrays
                map.add(downloadImage(strings[0]));// I used as this for you to understand. You can use for each loop
                map.add(downloadImage(strings[1]));
                map.add(downloadImage(strings[2]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;


        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
            hideProgressDialog();


            if (bitmaps.size() == 3 && bitmaps.get(0) != null && bitmaps.get(1) != null && bitmaps.get(2) != null) {

                model_details_data modelData = DataHelperKt.getModelData(requireActivity());

                Share.bitmapHashMap.put(Share.key_msk_imge, bitmaps.get(0));
                Share.bitmapHashMap.put(Share.key_normal_image_new, bitmaps.get(1));
                Share.bitmapHashMap.put(Share.key_normal_image, bitmaps.get(2));
                Share.maskheight = Float.parseFloat(modelData.getHeight());
                Share.maskwidth = Float.parseFloat(modelData.getWidth());

                Log.e("hashmap", "==>" + Share.bitmapHashMap);
                Log.e("TAG", "onPostExecute: " + modelData.getModelId());

                Intent intent = new Intent(getActivity(), Default_image_activity.class);
                intent.putExtra("model_name", modelData.getModalName());
                intent.putExtra("model_id", modelData.getModelId());
                intent.putExtra("user_id", SharedPrefs.getString(getContext(), Share.key_ + RegReq.id));
                intent.putExtra("quantity", "1");
                intent.putExtra("total_amount", modelData.getPrice());
                intent.putExtra("paid_amount", modelData.getPrice());
                Share.mobile_type = modelData.getImageType();
                Log.e("TAG", "onPostExecute: " + Share.mobile_type);
                if (!modelData.getWidth().equalsIgnoreCase("")) {
                    intent.putExtra("width", modelData.getWidth());
                    intent.putExtra("height", modelData.getHeight());
                }
                intent.putExtra("shipping", "0");
                startActivity(intent);
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {

                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle(getString(R.string.download_failed));
                alertDialog.setMessage(getString(R.string.slow_connect));
                alertDialog.setButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        layout5.callOnClick();
                    }
                });
                alertDialog.show();
            }
        }


    }

}
