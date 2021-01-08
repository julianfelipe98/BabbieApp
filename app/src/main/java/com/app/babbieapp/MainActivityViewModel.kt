package com.app.babbieapp

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.base.FirebaseHandler

class MainActivityViewModel @ViewModelInject constructor(
    private val firebaseHandler: FirebaseHandler,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _finishActivity = MutableLiveData<Boolean>()
    val finishActivity: LiveData<Boolean> = _finishActivity

    fun logOut() {
        _finishActivity.value = firebaseHandler.logOut()
    }
}