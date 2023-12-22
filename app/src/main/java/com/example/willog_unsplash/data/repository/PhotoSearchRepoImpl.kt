package com.example.willog_unsplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.willog_unsplash.data.api.UnsplashApi
import com.example.willog_unsplash.data.model.PhotoData
import com.example.willog_unsplash.data.model.SearchResponse
import com.example.willog_unsplash.utils.Constants.PAGING_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoSearchRepoImpl @Inject constructor(
    private val api: UnsplashApi
) : PhotoSearchRepo {

    override suspend fun searchPhotos(
        query: String,
        page: Int,
        perPage: Int
    ): Response<SearchResponse> =
        withContext(Dispatchers.IO) {
            Timber.e("검색 수행")
            api.searchPhotos(query, page, perPage).body()?.let {
                Response.success(it)
            } ?: Response.error(404, null)
        }

    override fun searchPhotosPaging(
        query: String
    ): Flow<PagingData<PhotoData>> {

        Timber.e("와우")

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                prefetchDistance = 3,
                enablePlaceholders = false, // true로 변경
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = {
                UnsplashPaging(api, query)
            }
        ).flow
    }
}