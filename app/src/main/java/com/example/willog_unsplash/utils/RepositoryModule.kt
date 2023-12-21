package com.example.willog_unsplash.utils

import com.example.willog_unsplash.data.repository.PhotoSearchRepo
import com.example.willog_unsplash.data.repository.PhotoSearchRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindPhotoRepository(
        photoRepositoryImpl: PhotoSearchRepoImpl
    ): PhotoSearchRepo

}