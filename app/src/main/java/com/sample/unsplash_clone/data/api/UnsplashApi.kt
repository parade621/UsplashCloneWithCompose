package com.sample.unsplash_clone.data.api

import com.sample.unsplash_clone.data.model.SearchResponse
import com.sample.unsplash_clone.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {
    @Headers("Authorization: Client-ID $API_KEY")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<SearchResponse>
}