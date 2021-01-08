package com.app.petmgt.di

import com.app.petmgt.network.PetMgtService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class PetApiModule {

    @Provides
    fun providePetService(
        retrofit: Retrofit
    ): PetMgtService {
        return retrofit.create(PetMgtService::class.java)
    }
}