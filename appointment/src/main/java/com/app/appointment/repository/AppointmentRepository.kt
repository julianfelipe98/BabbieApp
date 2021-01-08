package com.app.appointment.repository

import com.app.appointment.network.AppointmentService
import com.app.base.model.network.appointment.Appointment
import com.app.base.model.network.store.StoreNetwork
import com.app.base.utils.ResponseModel
import javax.inject.Inject

class AppointmentRepository @Inject constructor(
    private val service: AppointmentService
) {

    suspend fun getAppointments(idPet: String, tagRecord: String): List<Appointment>? {
        return service.getAppointments(idPet, tagRecord)
    }

    suspend fun getStores(tagRecord: String): List<StoreNetwork> {
        return service.getVetByService(tagRecord)
    }

    suspend fun getAppointmentsByVet(idVet: String, tagRecord: String): List<Appointment> {
        return service.getAppointmentsByVet(idVet, tagRecord)
    }

    suspend fun assignAppointment(idPet: String, idAppointment: String): ResponseModel {
        return service.assignAppointment(idPet, idAppointment)
    }

    suspend fun cancelAppointment(idAppointment: String, status: String): ResponseModel {
        return service.cancelAppointment(idAppointment, status)
    }
}