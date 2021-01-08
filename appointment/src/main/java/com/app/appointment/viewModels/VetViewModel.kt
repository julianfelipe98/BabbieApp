package com.app.appointment.viewModels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.appointment.repository.AppointmentRepository
import com.app.base.model.network.store.StoreNetwork
import com.app.base.utils.NetworkApiStatus
import kotlinx.coroutines.launch

class VetViewModel @ViewModelInject constructor(
    private val appointmentRepository: AppointmentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _vets = MutableLiveData<List<StoreNetwork>>()
    val vets: LiveData<List<StoreNetwork>> = _vets

    fun getStores(tagAppointment: String) {

        viewModelScope.launch {
            try {
                _status.value = NetworkApiStatus.LOADING
                Log.i("_status.value", _status.value.toString())
                val data = appointmentRepository.getStores(tagAppointment)
                _status.value = NetworkApiStatus.DONE
                _vets.value = data
                Log.i("_status.value", _status.value.toString())
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
                Log.i("_status.value", _status.value.toString())
                _vets.value = ArrayList()
            }
        }
    }
}