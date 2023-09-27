package com.mobile.cover.photo.editor.back.maker.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class VolleyUtil {



 /*   public static void uploadBitmap(final Context mContext, final Bitmap bitmap,
                                    final onFinished onFinished) {


        String UPLOAD_URL = Constant.ROOT_URL + Constant.uploadPostImage + Constant.output.getSess_usr_id();

        //getting the tag from the edittext
        //    final String tags = VolleyUtil.class.getSimpleName();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest =
                new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));
                                    onFinished.onSuccess(obj.getString("output"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                onFinished.onFailed(error.toString());
                            }
                        }) {

                    *//*
                     * If you want to add more parameters with the image
                     * you can do it here
                     * here we have only one parameter with the image
                     * which is tags
                     * *//*
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("tags", "post_image");
                        return params;
                    }

                    *//*
                     * Here we are passing image by renaming it with a unique name
                     * *//*
                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> params = new HashMap<>();
                        long imagename = System.currentTimeMillis();
                        params.put("post_image", new DataPart(imagename + ".png",
                                getFileDataFromDrawable(bitmap)));
                        return params;
                    }
                };

        //adding the request to volley
        Volley.newRequestQueue(mContext).add(volleyMultipartRequest);
    }*/


    /*
     * The method is taking Bitmap as an argument
     * then it will return the byte[] array for the given bitmap
     * and we will send this array to the server
     * here we are using PNG Compression with 80% quality
     * you can give quality between 0 to 100
     * 0 means worse quality
     * 100 means best quality
     * */
    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    public interface onFinished {
        public void onFailed(String error);

        public void onSuccess(String imageName);
    }



    public static void uploadPost(final Context mContext, final String UPLOAD_URL,
                                    final onFinished onFinished) {



        //getting the tag from the edittext
        //    final String tags = VolleyUtil.class.getSimpleName();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest =
                new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));
                                    onFinished.onSuccess(obj.getString("output"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                onFinished.onFailed(error.toString());
                            }
                        }) {


                };

        //adding the request to volley
        Volley.newRequestQueue(mContext).add(volleyMultipartRequest);
    }


}
