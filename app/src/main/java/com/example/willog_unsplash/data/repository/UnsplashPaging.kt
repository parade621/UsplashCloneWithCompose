package com.example.willog_unsplash.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.willog_unsplash.data.UnsplashApi
import com.example.willog_unsplash.data.model.PhotoData
import com.example.willog_unsplash.data.model.Photos
import com.example.willog_unsplash.utils.Constants.PAGING_SIZE
import retrofit2.Response
import timber.log.Timber

class UnsplashPaging(
    private val api: UnsplashApi,
    private val query: String,
) : PagingSource<Int, PhotoData>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoData> {
        return try {
            val pageNumber = params.key ?: START_PAGE_INDEX
            val response: Response<Photos> = api.searchPhotos(query, pageNumber, params.loadSize)
            val data: List<PhotoData> = response.body()?.results!!
            val prevKey = if (pageNumber == START_PAGE_INDEX) null else pageNumber - 1
            val nextKey = if (data.isEmpty()) null else pageNumber + (params.loadSize / PAGING_SIZE)
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    companion object {
        const val START_PAGE_INDEX = 1
    }
}