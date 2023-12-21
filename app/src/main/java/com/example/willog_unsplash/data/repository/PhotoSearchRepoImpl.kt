package com.example.willog_unsplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.willog_unsplash.data.UnsplashApi
import com.example.willog_unsplash.data.model.PhotoData
import com.example.willog_unsplash.data.model.Photos
import com.example.willog_unsplash.utils.Constants.PAGING_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PhotoSearchRepoImpl @Inject constructor(
    private val api: UnsplashApi
) : PhotoSearchRepo {

    override suspend fun searchPhotos(query: String, page: Int, perPage: Int): Response<Photos> =
        withContext(Dispatchers.IO) {
            api.searchPhotos(query, page, perPage)
        }

    override fun searchPhotosPaging(
        query: String
    ): Flow<PagingData<PhotoData>> {
        val pagingSourceFactory: () -> UnsplashPaging = {
            UnsplashPaging(api, query)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}