package com.app.babbieapp.di

import android.content.Context
import androidx.room.Room
import com.app.base.database.CartDao
import com.app.babbieapp.database.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataBaseModule {
    
    @Provides
    @Singleton
    fun provideCartDB(@ApplicationContext appContext: Context): CartDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            CartDatabase::class.java, "cart_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCartDao(cartDatabase: CartDatabase): CartDao {
        return cartDatabase.cartDao
    }
}
