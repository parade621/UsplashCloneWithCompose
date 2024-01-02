package com.sample.unsplash_clone.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sample.unsplash_clone.data.api.UnsplashApi
import com.sample.unsplash_clone.data.model.PhotoData
import com.sample.unsplash_clone.data.model.SearchResponse
import retrofit2.Response
import timber.log.Timber

class UnsplashPaging(
    private val api: UnsplashApi,
    private val query: String
) : PagingSource<Int, PhotoData>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoData> {
        val pageNumber = params.key ?: START_PAGE_INDEX

        return try {
            val response: Response<SearchResponse> =
                api.searchPhotos(query, pageNumber, params.loadSize)
            val data: List<PhotoData> = response.body()?.results.orEmpty()

            val prevKey = if (pageNumber == START_PAGE_INDEX) null else pageNumber - 1
            val nextKey = if (data.isEmpty()) null else pageNumber + 1

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