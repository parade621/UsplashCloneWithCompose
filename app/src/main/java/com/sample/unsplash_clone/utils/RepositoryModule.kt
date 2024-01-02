package com.sample.unsplash_clone.utils

import com.sample.unsplash_clone.data.repository.PhotoSearchRepoImpl

import com.sample.unsplash_clone.data.repository.PhotoSearchRepo
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