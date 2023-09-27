package com.mobile.cover.photo.editor.back.maker.volley;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyHelper {

    public static String TAG = VolleyHelper.class.getSimpleName();


    /**
     * TODO: Method to make json object request where json response starts wtih {
     */
    public static void requestString(String url, final StringReqListener listener) {


        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(strReq, url);


    }

    /**
     * TODO: Method to make json object request where json response starts wtih {
     */
    public static void requestGetObject(String url, final ObjectReqListener listener) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            Log.d(TAG, response.toString());
            listener.onSuccess(response);
        }, listener::onError);

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * TODO: Method to make json object request where json response starts wtih {
     */
    public static void requestPostObject(String url, final ObjectReqListener listener) {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);

            }
        });

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * TODO:  Method to make json array request where response starts with [
     */
    public static void requestArray(String url, final ArrayReqListener listener) {
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(req);
    }


    /*
     * TODO: Interface for handling response @calling place
     *
     * */

    public interface StringReqListener {
        void onSuccess(String response);

        void onError(VolleyError error);
    }

    public interface ObjectReqListener {
        void onSuccess(JSONObject response);

        void onError(VolleyError error);
    }

    public interface ArrayReqListener {
        void onSuccess(JSONArray response);

        void onError(VolleyError error);
    }


}
