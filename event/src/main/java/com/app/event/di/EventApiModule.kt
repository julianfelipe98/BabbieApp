package com.app.event.di

import com.app.event.network.EventService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
class EventApiModule {

    @Provides
    fun provideStoreService(
        retrofit: Retrofit
    ): EventService {
        return retrofit.create(EventService::class.java)
    }
}