package com.app.auth.di

import com.app.base.di.UserEmail
import com.app.base.di.UserId
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object FiruModule {

    @Provides
    @UserEmail
    fun provideUserEmail(): String {
        return FirebaseAuth.getInstance().currentUser?.email.toString()
    }

    @Provides
    @UserId
    fun provideUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid.toString()
    }
}