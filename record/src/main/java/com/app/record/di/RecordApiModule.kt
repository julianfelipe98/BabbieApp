package com.app.record.di

import com.app.record.network.RecordService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
class RecordApiModule {

    @Provides
    fun provideStoreService(
        retrofit: Retrofit
    ): RecordService {
        return retrofit.create(RecordService::class.java)
    }
}