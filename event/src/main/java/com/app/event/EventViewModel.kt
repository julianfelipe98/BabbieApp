package com.app.event

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.base.model.network.events.Event
import com.app.base.utils.NetworkApiStatus
import com.app.event.adapters.DateItem
import com.app.event.adapters.GeneralItem
import com.app.event.adapters.ListItem
import com.app.event.repository.EventRepository
import kotlinx.coroutines.launch


class EventViewModel @ViewModelInject constructor(
    private val eventRepository: EventRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _listItem = MutableLiveData<List<ListItem>>()
    val listItem: LiveData<List<ListItem>> = _listItem

    init {
        getEvents()
    }

    private fun getEvents() {

        viewModelScope.launch {

            try {
                _status.value = NetworkApiStatus.LOADING
                Log.i("_status.value", _status.value.toString())
                val data = eventRepository.getEvents()
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

    private fun loadConsolidateList(orderList: List<Event>) {

        val orderListSort = orderList.sortedByDescending { it._date }
        val map = orderListSort.groupBy { it._date }
        val itemListTem: MutableList<ListItem> = ArrayList()
        for ((key, value) in map) {
            itemListTem.add(DateItem(key))
            for (i in map[key]!!) {
                itemListTem.add(GeneralItem(i))

            }
        }
        _listItem.value = itemListTem
    }
}