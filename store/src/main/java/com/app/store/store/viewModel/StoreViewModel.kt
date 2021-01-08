package com.app.store.store.viewModel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.base.model.database.Store
import com.app.base.utils.NetworkApiStatus
import com.app.store.repository.StoreRepository
import kotlinx.coroutines.launch


class StoreViewModel @ViewModelInject constructor(
    private val storeRepository: StoreRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _stores = MutableLiveData<List<Store>>()
    val stores: LiveData<List<Store>> = _stores

    init {
        getStores()
    }

    //get the stores from the web service
    private fun getStores() {

        viewModelScope.launch {

            try {
                _status.value = NetworkApiStatus.LOADING
                Log.i("_status.value", _status.value.toString())
                val data = storeRepository.getStores()

                _status.value = NetworkApiStatus.DONE
                _stores.value = data
                Log.i("_status.value", _status.value.toString())
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
                Log.i("_status.value", _status.value.toString())
                _stores.value = ArrayList()
            }
        }
    }
}
