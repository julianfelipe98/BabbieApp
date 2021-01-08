package com.app.store.profile.viewModel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.base.model.network.store.OrderNetwork
import com.app.base.utils.NetworkApiStatus
import com.app.store.profile.adapters.DateItem
import com.app.store.profile.adapters.GeneralItem
import com.app.store.profile.adapters.ListItem
import com.app.store.repository.StoreRepository

import kotlinx.coroutines.launch

class OrderHistoryViewModel @ViewModelInject constructor(
    private val storeRepository: StoreRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _listItem = MutableLiveData<List<ListItem>>()
    val listItem: LiveData<List<ListItem>> = _listItem

    init {
        getOrders()
    }

    private fun getOrders() {

        viewModelScope.launch {

            try {
                _status.value = NetworkApiStatus.LOADING
                Log.i("_status.value", _status.value.toString())
                val data = storeRepository.getOrder()
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

    private fun loadConsolidateList(orderList: List<OrderNetwork>) {

        val orderListSort = orderList.sortedByDescending { it._date }
        val map = orderListSort.groupBy { it._date }

        val itemListTem: MutableList<ListItem> = ArrayList()

        for ((key, value) in map) {
            itemListTem.add(DateItem(key))
            for (i in map.get(key)!!) {
                itemListTem.add(GeneralItem(i))
            }
        }
        _listItem.value = itemListTem
    }
}
