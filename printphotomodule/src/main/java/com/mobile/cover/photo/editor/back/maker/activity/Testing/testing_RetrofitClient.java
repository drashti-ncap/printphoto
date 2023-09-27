package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class testing_RetrofitClient {

    public static final String ROOT_URL = "https://printphoto.in/Photo_case/api/";
    public static final String DEVELOPMENT_ROOT_URL = "https://api.cloudprinter.com/cloudcore/1.0/orders/";

    private static Retrofit getRetrofitInstance() {

        return new Retrofit.Builder()
                .baseUrl(DEVELOPMENT_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static testing_APIService getApiService() {
        return getRetrofitInstance().create(testing_APIService.class);
    }
}
