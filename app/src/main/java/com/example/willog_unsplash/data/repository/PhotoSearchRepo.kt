package com.example.willog_unsplash.data.repository

import androidx.paging.PagingData
import com.example.willog_unsplash.data.model.PhotoData
import com.example.willog_unsplash.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PhotoSearchRepo {
    // Paging Api 호출
    fun searchPhotosPaging(query: String): Flow<PagingData<PhotoData>>

    // DB DAO
    suspend fun insertBookMark(photo: PhotoData)

    suspend fun deleteBookMark(photo: PhotoData)

    suspend fun getAllBookmarkData(): List<PhotoData>

    suspend fun getAllBookmarkId(): List<String>

    fun getBookmarkedPhotos(): Flow<PagingData<PhotoData>>

}