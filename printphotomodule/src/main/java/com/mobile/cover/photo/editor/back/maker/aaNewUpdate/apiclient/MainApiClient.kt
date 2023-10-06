package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient

import android.content.Context
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.getBaseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class MainApiClient(val mContext: Context) {

    private var retrofit: Retrofit? = null

    var apiInterface: APIService = client!!.create(APIService::class.java)


    val client: Retrofit?
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient().newBuilder()
            builder.addInterceptor(logging)
            builder.callTimeout(2, TimeUnit.MINUTES)
            builder.connectTimeout(1, TimeUnit.SECONDS)
            builder.readTimeout(1, TimeUnit.MINUTES)
            builder.writeTimeout(1, TimeUnit.MINUTES)
            val httpClient = builder.build()



            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(mContext.getBaseUrl())
//                        .baseUrl("https://printstudio.mobilemiracle.io/api/")
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }


}