package com.app.store.store.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.base.model.database.Order
import com.app.base.model.network.store.ItemNetwork
import com.app.base.utils.Utils
import com.app.store.repository.StoreRepository
import com.app.store.store.adapters.GeneralProduct
import com.app.store.store.adapters.ListItemStore
import com.app.store.store.adapters.StoreItem
import kotlinx.coroutines.launch

class CategoryViewModel @ViewModelInject constructor(
    private val storeRepository: StoreRepository
) : ViewModel() {

    private val _category = MutableLiveData<List<ItemNetwork>>()
    val category: LiveData<List<ItemNetwork>> = _category

    private val _products = MutableLiveData<List<ListItemStore>>()
    val products: LiveData<List<ListItemStore>> = _products


    fun setListCategory(listCategoty: List<ItemNetwork>) {
        _category.value = listCategoty
        loadConsolidateList()
    }

    private fun loadConsolidateList() {

        val map = _category.value?.groupBy { it.vetstore.name }
        val itemListTem: MutableList<ListItemStore> = ArrayList()
        if (map != null) {
            for ((key, value) in map) {
                itemListTem.add(StoreItem(key))
                for (i in map.get(key)!!) {
                    itemListTem.add(GeneralProduct(i))

                }
            }
        }
        _products.value = itemListTem

    }

    fun insertOrder(item: ItemNetwork) {
        val itemCast = Utils.itemNetworkToItem(item)
        val order = Order(0, itemCast, 1, item.price)
        viewModelScope.launch {
            storeRepository.insertOrder(order)
        }
    }

}
