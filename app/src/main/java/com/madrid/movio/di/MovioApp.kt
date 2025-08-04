package com.madrid.movio.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovioApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}