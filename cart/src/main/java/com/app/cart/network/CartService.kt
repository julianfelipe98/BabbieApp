package com.app.cart.network


import com.app.base.model.database.Order
import com.app.base.model.network.User
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartService {

    @POST("orders/")
    suspend fun createOrder(@Body newOrder: List<Order>): Response<JSONObject>

    @GET("/users/{id}")
    suspend fun getUser(@Path(value = "id") storeId: String): User
}




