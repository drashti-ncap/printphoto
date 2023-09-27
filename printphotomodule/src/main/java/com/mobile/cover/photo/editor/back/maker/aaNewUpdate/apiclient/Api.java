package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient;

import com.mobile.cover.photo.editor.back.maker.model.CityState;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "http://ip-api.com/";
    @GET("json")
    Call<CityState> getData();
}