package com.mobile.cover.photo.editor.back.maker.Pagination.api;


import com.mobile.cover.photo.editor.back.maker.Pagination.model.TopRatedMovies;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MovieService {
    @FormUrlEncoded
    @POST("case_images")
    Call<TopRatedMovies> getTopRatedMovies(
            @Field("category_id") String category_id,
            @Field("orderBy") String orderBy,
            @Field("page") int page,
            @Field("platform") int platform,
            @Field("country_code") String country_code,
            @Field("limit") String limit
    );

    @FormUrlEncoded
    @POST("case_images")
    Call<TopRatedMovies> getTopRatedMovies_without(
            @Field("category_id") String category_id,
            @Field("orderBy") String orderBy,
            @Field("platform") int platform,
            @Field("country_code") String country_code,
            @Field("limit") String limit
    );

}
