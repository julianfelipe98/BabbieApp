package com.app.store.network


import com.app.base.model.database.Store
import com.app.base.model.network.store.ItemNetwork
import com.app.base.model.network.store.OrderNetwork
import com.app.base.utils.Search
import retrofit2.http.*

interface StoreService {

    @GET("/orders/user")
    suspend fun getOrder():
            List<OrderNetwork>

    @GET("vetshops")
    suspend fun getStores(): List<Store>

    @GET("vetshops/item/{storeId}")
    suspend fun getProductsByStore(@Path(value = "storeId") storeId: String): List<ItemNetwork>

    @POST("/products/item/search")
    suspend fun searchProduct(@Body search: Search): List<ItemNetwork>

    @GET("/filter/item")
    suspend fun getProductsByCategory(
        @Query("category") categoryId: String,
        @Query("vetstore") storeId: String
    ): List<ItemNetwork>

}


