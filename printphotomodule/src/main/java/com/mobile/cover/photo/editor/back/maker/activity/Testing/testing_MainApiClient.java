package com.mobile.cover.photo.editor.back.maker.activity.Testing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class testing_MainApiClient {

    private static final String ROOT_URL = "https://printphoto.in/Photo_case/api/";
    private static final String DEVELOPMENT_ROOT_URL = "https://api.cloudprinter.com/cloudcore/1.0/orders/";

    public static final String BASE_URL = DEVELOPMENT_ROOT_URL;
    private static Retrofit retrofit = null;


    public testing_MainApiClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(logging);

        builder.readTimeout(5, TimeUnit.MINUTES);
        builder.connectTimeout(5, TimeUnit.MINUTES);
        builder.writeTimeout(5, TimeUnit.MINUTES);

        Gson gson;
        gson = new GsonBuilder()
                .setLenient()
                .create();


        OkHttpClient httpClient = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();


    }

    public Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(logging);
        builder.readTimeout(5, TimeUnit.MINUTES);
        builder.connectTimeout(5, TimeUnit.MINUTES);
        builder.writeTimeout(5, TimeUnit.MINUTES);
        Gson gson;
        gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient httpClient = builder.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public Retrofit getClientForPincode() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(logging);
        builder.readTimeout(5, TimeUnit.MINUTES);
        builder.connectTimeout(5, TimeUnit.MINUTES);
        builder.writeTimeout(5, TimeUnit.MINUTES);
        Gson gson;
        gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient httpClient = builder.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public Retrofit getClientSend() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(logging);
        builder.readTimeout(5, TimeUnit.MINUTES);
        builder.connectTimeout(5, TimeUnit.MINUTES);
        builder.writeTimeout(5, TimeUnit.MINUTES);
        Gson gson;
        gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient httpClient = builder.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
