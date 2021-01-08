package com.app.auth.login

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.auth.repository.LoginRepository
import com.app.base.utils.NetworkApiStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
//import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class LoginActivityViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus>
        get() = _status

    private val _statusUser = MutableLiveData<NetworkApiStatus>()
    val statusUser: LiveData<NetworkApiStatus>
        get() = _statusUser

    fun checkUser() {

        viewModelScope.launch {

            try {
                Log.e("isSuccessful ", "${FirebaseAuth.getInstance().currentUser?.uid}")
                FirebaseAuth.getInstance().currentUser?.uid?.let {
                    loginRepository.getUser(it)
                }
                _statusUser.value = NetworkApiStatus.DONE

            } catch (t: Throwable) {
                _statusUser.value = NetworkApiStatus.ERROR

            }
        }
    }

    fun subscribeMessagingTopic() {
        viewModelScope.launch {

            try {
                FirebaseMessaging.getInstance().subscribeToTopic("events-news")
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.e("Error ", task.toString())
                        }
                        Log.i("messaging", task.toString())
                    }
                _status.value = NetworkApiStatus.DONE
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
            }
        }
    }
}