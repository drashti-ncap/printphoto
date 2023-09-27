package com.mobile.cover.photo.editor.back.maker.Pagination.api;

import android.content.Context;

import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.UtilsKt;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Pagination
 * Created by Suleiman19 on 10/27/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */

public class MovieApi {
    private static Retrofit retrofit = null;


    private static OkHttpClient buildClient() {
        return new OkHttpClient
                .Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static Retrofit getClient(Context mContext) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(buildClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(UtilsKt.getBaseUrl(mContext))
                    .build();
        }
        return retrofit;
    }

}

