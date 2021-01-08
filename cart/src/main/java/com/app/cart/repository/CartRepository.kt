package com.app.cart.repository

import androidx.lifecycle.LiveData
import com.app.base.database.CartDao
import com.app.base.model.database.Order
import com.app.base.model.network.User
import com.app.cart.network.CartService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class CartRepository @Inject constructor(
    private val cartDao: CartDao,
    private val service: CartService
) {
    suspend fun getUser(userId: String): User {
        return service.getUser(userId)
    }

    suspend fun createOrder(newOrder: List<Order>): Response<JSONObject> {
        return service.createOrder(newOrder)
    }

    fun getAllOrders(): LiveData<List<Order>> {
        return cartDao.getAllOrder()
    }

    suspend fun updateAddOrder(id: Int) {
        withContext(Dispatchers.IO) {
            cartDao.updateIncreaseQuantity(id)
        }
    }

    suspend fun updateReduceOrder(id: Int) {
        withContext(Dispatchers.IO) {
            cartDao.updateReduceQuantity(id)
        }
    }

    suspend fun deleteOrder(id: Int) {
        withContext(Dispatchers.IO) {
            cartDao.deleteOrder(id)
        }
    }

    suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            cartDao.deleteAll()
        }
    }
}