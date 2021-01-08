package com.app.auth.profile.viewModel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.auth.repository.LoginRepository
import com.app.base.FirebaseHandler
import com.app.base.di.UserEmail
import com.app.base.di.UserId

import com.app.base.model.network.User
import com.app.base.utils.NetworkApiStatus

import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    @UserId private val userId: String,
    @UserEmail private val userEmail: String,
    private val loginRepository: LoginRepository,
    private val firebaseHandler: FirebaseHandler,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private lateinit var newUser: User

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _finishActivity = MutableLiveData<Boolean>()
    val finishActivity: LiveData<Boolean> = _finishActivity

    fun createUser(user: User) {
        newUser = user
        user.email = userEmail
        viewModelScope.launch {
            loginRepository.createUser(user)
        }
    }

    fun getUserData() {

        viewModelScope.launch {
            try {
                var data = loginRepository.getUser2(userId)
                _user.value = data
            } catch (t: Throwable) {
                Log.e("_status.value", "No se pudo cargar la informacion del usuario ")
            }
        }
    }

    fun updateUser() {

        viewModelScope.launch {
            try {
                _status.value = NetworkApiStatus.LOADING
                _user.value?.let {
                    loginRepository.updateUser(it)
                    _status.value = NetworkApiStatus.DONE
                }
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
                Log.e("_status.value", "No se pudo guardar cambios del usuario ")
            }
        }
    }

    fun logOut() {
        _finishActivity.value = firebaseHandler.logOut()
    }

}



