package com.example.swipeassignment

import android.app.Application
import com.example.swipeassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SwipeAssignmentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SwipeAssignmentApplication)
            modules(appModule)
        }
    }

}