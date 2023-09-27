package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model.ResponseDashboard;
import com.mobile.cover.photo.editor.back.maker.activity.Testing.testing_model.response_data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface testing_APIService {
    @Headers("Content-Type: application/json")
    @POST("add")
    Call<response_data> sendLocation(@Body ResponseDashboard responseDashboard);
}

