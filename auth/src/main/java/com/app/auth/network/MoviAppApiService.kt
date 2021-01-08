package com.app.auth.network

import com.app.base.model.network.User
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @GET("/users/{id}")
    suspend fun getUser(@Path(value = "id") storeId: String): User

    @POST("users/")
    suspend fun createUser(@Body user: User): Response<JSONObject>

    @PUT("users/")
    suspend fun updateUser(@Body user: User): Response<JSONObject>

}

