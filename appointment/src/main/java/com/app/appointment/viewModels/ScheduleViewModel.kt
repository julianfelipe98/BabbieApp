package com.app.appointment.viewModels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.appointment.adapter.DateItem
import com.app.appointment.adapter.GeneralItem
import com.app.appointment.adapter.ListItem
import com.app.appointment.repository.AppointmentRepository
import com.app.base.model.network.appointment.Appointment
import com.app.base.utils.NetworkApiStatus
import com.app.base.utils.ResponseModel
import kotlinx.coroutines.launch

class ScheduleViewModel @ViewModelInject constructor(
    private val appointmentRepository: AppointmentRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _listItem = MutableLiveData<List<ListItem>>()
    val listItem: LiveData<List<ListItem>> = _listItem

    private val _response = MutableLiveData<ResponseModel>()
    val response: LiveData<ResponseModel> = _response

    private var tagAppointment = ""
    private var idPet = ""
    private var idVet = ""

    fun setBasicData(tagAppointment: String, idPet: String, idVet: String) {
        this.tagAppointment = tagAppointment
        this.idPet = idPet
        this.idVet = idVet
        getSchedule()
    }

    private fun getSchedule() {

        viewModelScope.launch {

            try {
                _status.value = NetworkApiStatus.LOADING
                Log.i("_status.value", _status.value.toString())
                val data = appointmentRepository.getAppointmentsByVet(idVet, tagAppointment)
                _status.value = NetworkApiStatus.DONE
                loadConsolidateList(data)
                Log.i("_status.value", _status.value.toString())
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
                Log.i("_status.value", _status.value.toString())
                _listItem.value = ArrayList()
            }
        }
    }

    private fun loadConsolidateList(list: List<Appointment>) {

        var orderListSort = list.sortedBy { it._initial_date }
        var map = orderListSort.groupBy { it._initial_date }
        val itemListTem: MutableList<ListItem> = ArrayList()
        for ((key, value) in map) {
            itemListTem.add(DateItem(key))
            for (i in map.get(key)!!) {
                itemListTem.add(GeneralItem(i))
            }
        }
        _listItem.value = itemListTem
    }

    fun assignAppointment(idAppointment: String) {

        viewModelScope.launch {
            try {
                val data = appointmentRepository.assignAppointment(idPet, idAppointment)
                _response.value = data
            } catch (t: Throwable) {
                Log.i("_status.value", "error")
            }
        }
    }
}