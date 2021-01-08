package com.app.store.repository

import com.app.base.database.CartDao
import com.app.base.model.database.Order
import com.app.base.model.database.Store
import com.app.base.model.network.store.ItemNetwork
import com.app.base.model.network.store.OrderNetwork
import com.app.base.utils.Search
import com.app.store.network.StoreService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class StoreRepository @Inject constructor(
    private val cartDao: CartDao,
    private val service: StoreService
) {

    suspend fun getOrder(): List<OrderNetwork> {
        return service.getOrder()
    }

    suspend fun getStores(): List<Store> {
        return service.getStores()
    }

    suspend fun getProductsByStore(idStore: String): List<ItemNetwork> {
        return service.getProductsByStore(idStore)
    }

    suspend fun searchProduct(search: String): List<ItemNetwork> {
        return service.searchProduct(Search(search))
    }

    suspend fun getProductsByCateogry(categoryId: String, storeId: String): List<ItemNetwork> {
        return service.getProductsByCategory(categoryId, storeId)
    }

    suspend fun insertOrder(order: Order) {
        withContext(Dispatchers.IO) {
            cartDao.insertOrder(order)
        }
    }
}