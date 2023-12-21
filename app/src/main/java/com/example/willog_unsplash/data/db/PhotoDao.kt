package com.example.willog_unsplash.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.willog_unsplash.data.model.PhotoData
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookMark(photo: PhotoData)

    @Delete
    suspend fun deleteBookMark(photo: PhotoData)

    @Query("SELECT * FROM photos")
    fun getBookMarkPhotos(): Flow<List<PhotoData>>

    // define paging source
    @Query("SELECT * FROM photos")
    fun getBookMarkPagingPhotos(): PagingSource<Int, PhotoData>

}