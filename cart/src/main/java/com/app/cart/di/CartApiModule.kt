package com.app.cart.di

import com.app.cart.network.CartService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@Module
@InstallIn(ApplicationComponent::class)
class CartApiModule {

    @Provides
    fun provideStoreService(
        retrofit: Retrofit
    ): CartService {
        return retrofit.create(CartService::class.java)
    }
}