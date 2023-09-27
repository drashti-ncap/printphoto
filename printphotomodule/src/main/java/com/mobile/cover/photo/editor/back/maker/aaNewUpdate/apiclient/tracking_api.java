package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface tracking_api {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("getOrderShipmentDetails")
    Call<JsonObject> postRawJSON(@Body JsonObject locationPost);

}
