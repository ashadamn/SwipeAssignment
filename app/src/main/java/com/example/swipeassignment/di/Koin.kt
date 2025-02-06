package com.example.swipeassignment.di


import androidx.room.Room
import androidx.work.WorkManager
import com.example.swipeassignment.data.repository.ProductRepository
import com.example.swipeassignment.data.repository.ProductRepositoryImpl
import com.example.swipeassignment.data.source.local.AppDatabase
import com.example.swipeassignment.data.source.remote.AppApiService
import com.example.swipeassignment.di.NetworkProvider.provideHttpClient
import com.example.swipeassignment.presentation.ui.screens.home_screen.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    single { get<AppDatabase>().pendingProductDao() }

    single { WorkManager.getInstance(androidContext()) }

    single<ProductRepository> { ProductRepositoryImpl(get(), get(), get(), androidContext()) }
    single { AppApiService(get(), androidContext()) }
    single { provideHttpClient() }
    viewModel { HomeViewModel(get()) }
}