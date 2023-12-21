package com.example.willog_unsplash

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    @Override
    override fun onCreate() {
        super.onCreate()
        Timber.e("UnsplashCloneApp??????")
        Timber.plant(Timber.DebugTree())
    }
}