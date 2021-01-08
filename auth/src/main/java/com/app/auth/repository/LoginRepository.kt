package com.app.auth.repository

import com.app.auth.network.AuthService
import com.app.base.model.network.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoginRepository @Inject constructor(
    private val service: AuthService
) {

    suspend fun getUser(userId: String) {
        service.getUser(userId)
    }

    suspend fun getUser2(userId: String): User {
        return service.getUser(userId)
    }

    suspend fun createUser(user: User) {
        withContext(Dispatchers.IO) {
            service.createUser(user)
        }
    }

    suspend fun updateUser(user: User) {
            service.updateUser(user)
    }
}