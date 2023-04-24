package com.kosign.multimodulehilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}