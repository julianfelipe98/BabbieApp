package com.app.appointment.network

import com.app.base.model.network.appointment.Appointment
import com.app.base.model.network.store.StoreNetwork
import com.app.base.utils.ResponseModel
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AppointmentService {

    @GET("/appointments/pets/{petId}")
    suspend fun getAppointments(
        @Path(value = "petId") petId: String,
        @Query("tag") type: String
    ): List<Appointment>

    @GET("/appointments/vets/{vetId}")
    suspend fun getAppointmentsByVet(
        @Path(value = "vetId") vetId: String,
        @Query("tag") type: String
    ): List<Appointment>

    @GET("filter/services/vets/")
    suspend fun getVetByService(
        @Query("tag") service: String
    ): List<StoreNetwork>

    @PUT("/appointments/assign/")
    suspend fun assignAppointment(
        @Query("pet") petId: String,
        @Query("appointment") appointmentId: String
    ): ResponseModel

    @PUT("appointments/{id}/status/")
    suspend fun cancelAppointment(
        @Path(value = "id") appointmentId: String,
        @Query("status") status: String
    ): ResponseModel

}
