package com.example.willog_unsplash.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.willog_unsplash.data.api.UnsplashApi
import com.example.willog_unsplash.data.db.PhotoDatabase
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
    private val api: UnsplashApi,
    private val db: PhotoDatabase
) : PhotoSearchRepo {

    override fun searchPhotosPaging(
        query: String
    ): Flow<PagingData<PhotoData>> {

        Timber.e("와우")

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = true,
                maxSize = PAGING_SIZE * 8
            ),
            pagingSourceFactory = {
                UnsplashPaging(api, query)
            }
        ).flow
    }

    override suspend fun insertBookMark(photo: PhotoData) {
        db.photoDao().insertBookMark(photo)
    }

    override suspend fun deleteBookMark(photo: PhotoData) {
        db.photoDao().deleteBookMark(photo)
    }

    override suspend fun getAllBookmarkData(): List<PhotoData> {
        return db.photoDao().getAllBookmarkData()
    }

    override suspend fun getAllBookmarkId(): List<String> {
        return db.photoDao().getAllBookmarkId()
    }

    override fun getBookmarkedPhotos(): Flow<PagingData<PhotoData>> {
        val pagingSourceFactory: () -> PagingSource<Int, PhotoData> =
            { db.photoDao().getBookMarkPagingPhotos() }

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