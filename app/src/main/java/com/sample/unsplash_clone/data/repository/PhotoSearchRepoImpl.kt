package com.sample.unsplash_clone.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.sample.unsplash_clone.data.api.UnsplashApi
import com.sample.unsplash_clone.data.db.PhotoDatabase
import com.sample.unsplash_clone.data.model.PhotoData
import com.sample.unsplash_clone.utils.Constants.PAGING_SIZE
import kotlinx.coroutines.flow.Flow
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