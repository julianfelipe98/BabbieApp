package com.app.petmgt.pets.viewModel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.base.di.UserId
import com.app.base.model.network.petMgt.Pet
import com.app.base.utils.NetworkApiStatus
import com.app.petmgt.repository.PetRepository
import kotlinx.coroutines.launch

class PetListViewModel @ViewModelInject constructor(
    @UserId private val userId: String,
    private val petRepository: PetRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _pets = MutableLiveData<List<Pet>>()
    val pets: LiveData<List<Pet>> = _pets

    init {
        getPets()
    }

    private fun getPets() {

        viewModelScope.launch {

            try {
                _status.value = NetworkApiStatus.LOADING
                Log.i("_status.value", _status.value.toString())
                val data = petRepository.getPets(userId)
                _status.value = NetworkApiStatus.DONE
                _pets.value = data
                Log.i("_status.value", _status.value.toString())
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
                Log.i("_status.value", _status.value.toString())
                _pets.value = ArrayList()
            }
        }
    }
}