package com.example.willog_unsplash.data.repository

import androidx.paging.PagingData
import com.example.willog_unsplash.data.model.PhotoData
import com.example.willog_unsplash.data.model.Photos
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PhotoSearchRepo {
    suspend fun searchPhotos(query: String, page: Int, perPage: Int): Response<Photos>

    fun searchPhotosPaging(query: String): Flow<PagingData<PhotoData>>
}