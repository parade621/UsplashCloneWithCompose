package com.example.willog_unsplash

import android.app.Application
import timber.log.Timber

class UnsplashClone:Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}