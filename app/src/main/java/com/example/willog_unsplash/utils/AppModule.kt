package com.example.willog_unsplash.utils

import android.content.Context
import androidx.room.Room
import com.example.willog_unsplash.data.api.UnsplashApi
import com.example.willog_unsplash.data.db.PhotoDatabase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Retrofit
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): UnsplashApi {
        return retrofit.create(UnsplashApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    // Room
    @Singleton
    @Provides
    fun providePhotoDatabases(@ApplicationContext context: Context): PhotoDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            PhotoDatabase::class.java,
            "photos_database"
        ).build()
}