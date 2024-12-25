package org.kmpcompose.kmp

import android.app.Application
import org.kmpcompose.kmp.di.initKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}