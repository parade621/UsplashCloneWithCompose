package com.example.willog_unsplash.data.api

import com.example.willog_unsplash.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {
    @Headers("Authorization: Client-ID kxQd79yOtdXqt67l7nQOjcEpGLTHTeG9e3WMdZSjRB0")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<SearchResponse>
}