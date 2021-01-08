package com.app.appointment.viewModels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.appointment.repository.AppointmentRepository
import com.app.base.model.network.appointment.Appointment
import com.app.base.utils.NetworkApiStatus
import com.app.base.utils.ResponseModel
import kotlinx.coroutines.launch

class AppointmentViewModel @ViewModelInject constructor(
    private val appointmentRepository: AppointmentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _appointment = MutableLiveData<List<Appointment>>()
    val appointment: LiveData<List<Appointment>> = _appointment

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _response = MutableLiveData<ResponseModel>()
    val response: LiveData<ResponseModel> = _response

    fun getRecords(idPet: String, tagAppointment: String) {

        viewModelScope.launch {
            try {
                _status.value = NetworkApiStatus.LOADING
                val data = appointmentRepository.getAppointments(idPet, tagAppointment)
                _appointment.value = data
                _status.value = NetworkApiStatus.DONE
            } catch (t: Throwable) {
                _appointment.value = ArrayList()
            }
        }
    }

    fun cancelAppointment(idAppointment: String, status: String) {

        viewModelScope.launch {
            try {
                val data = appointmentRepository.cancelAppointment(idAppointment, status)
                _response.value = data
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
            }
        }
    }
}