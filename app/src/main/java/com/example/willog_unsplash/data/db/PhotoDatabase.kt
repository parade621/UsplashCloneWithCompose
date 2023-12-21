package com.example.willog_unsplash.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.willog_unsplash.data.model.PhotoData

@Database(
    entities = [PhotoData::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(OrmConverter::class)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}