package com.app.store.di

import com.app.store.network.StoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
class StoreApiModule {

    @Provides
    fun provideStoreService(
        retrofit: Retrofit
    ): StoreService {
        return retrofit.create(StoreService::class.java)
    }
}