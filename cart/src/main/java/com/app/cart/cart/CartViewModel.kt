package com.app.cart.cart

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.base.di.UserId
import com.app.base.model.database.Order
import com.app.base.model.network.User
import com.app.base.utils.NetworkApiStatus
import com.app.cart.repository.CartRepository


import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class CartViewModel @ViewModelInject constructor(
    private val cartRepository: CartRepository,
    @UserId private val userId: String,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private var _items = MutableLiveData<List<Order>>()
    val item: LiveData<List<Order>> = _items

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private var _totalValue = MutableLiveData<Double>()
    val totalValue: LiveData<Double> = _totalValue

    init {
        getAllOrders()
        getTotalValue()
        getUserData()
    }

    fun changeQuantity(order: Order, operation: String) {

        viewModelScope.launch {
            when (operation) {
                "+" -> cartRepository.updateAddOrder(order.id)
                "-" -> cartRepository.updateReduceOrder(order.id)
            }
        }
    }

    private fun getTotalValue() {
        _items.observeForever {
            var total = 0.0
            for (order in it) {
                total += order._totalValue

            }
            _totalValue.value = total
        }
    }

    fun getAllOrders(): LiveData<List<Order>> {
        val items = cartRepository.getAllOrders()
        items.observeForever {
            _items.value = it.toList()
        }
        return cartRepository.getAllOrders()
    }

    private fun getUserData() {
        viewModelScope.launch {
            try {
                val data = cartRepository.getUser(userId)
                Log.e("useriii", userId)
                _user.value = data
            } catch (t: Throwable) {
                Log.e("userData", t.message)
            }
        }
    }

    fun deleteOrder(id: Int) {
        viewModelScope.launch {
            cartRepository.deleteOrder(id)
        }
    }

    fun makeOrder() {
        viewModelScope.launch {
            _status.value = NetworkApiStatus.LOADING
            var map = _items.value?.let { it.groupBy { it.item.vetstore.id } }
            try {
                map?.values?.forEach { value ->
                    handleNetworkResponse(cartRepository.createOrder(value))
                }
                _status.value = NetworkApiStatus.DONE
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
            }

        }
    }

    private fun handleNetworkResponse(response: Response<JSONObject>) {

        if (response.message() == "OK") {
            cleanCart()
        } else {
            _status.value = NetworkApiStatus.ERROR
        }
    }

    fun cleanCart() {
        viewModelScope.launch {
            cartRepository.clearAll()
        }
    }
}