package com.app.appointment.di

import com.app.appointment.network.AppointmentService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
class AppointmentApiModule {

    @Provides
    fun provideAppointmentService(retrofit: Retrofit): AppointmentService {
        return retrofit.create(AppointmentService::class.java)
    }
}