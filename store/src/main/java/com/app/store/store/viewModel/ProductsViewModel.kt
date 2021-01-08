package com.app.store.store.viewModel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.base.model.database.Order
import com.app.base.model.database.Store
import com.app.base.model.network.store.ItemNetwork
import com.app.base.model.network.store.ProductType
import com.app.base.utils.NetworkApiStatus
import com.app.base.utils.Utils
import com.app.store.repository.StoreRepository
import com.app.store.store.adapters.GeneralProduct
import com.app.store.store.adapters.ListItemStore
import com.app.store.store.adapters.StoreItem
import com.app.store.utils.PriceSortType

import kotlinx.coroutines.launch

class ProductsViewModel @ViewModelInject constructor(
    private val storeRepository: StoreRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stores = MutableLiveData<List<Store>>()
    val stores: LiveData<List<Store>> = _stores

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _products = MutableLiveData<List<ListItemStore>>()
    val products: LiveData<List<ListItemStore>> = _products

    private val _productType = MutableLiveData<List<ProductType>>()
    val productType: LiveData<List<ProductType>> = _productType

    private val _storeId = MutableLiveData<String>()
    val storeId: LiveData<String> = _storeId

    private val _productsFilter = MutableLiveData<List<ItemNetwork>>()
    val productsFilter: LiveData<List<ItemNetwork>> = _productsFilter

    private var searchText = ""

    init {
        getStores()
    }

    fun filterStore(id: String) {
        //_storeId.value = id
        _storeId.value = id
    }

    fun filterSearch(string: String) {
        this.searchText = string
    }

    //get the store's products from web server
    fun getProducts(type: PriceSortType) {

        viewModelScope.launch {

            try {
                _status.value = NetworkApiStatus.LOADING
                Log.i("_status.value", _status.value.toString())
                _storeId.value?.let {
                    val data = storeRepository.getProductsByStore(it)
                    Log.e("store",data.toString())
                    _status.value = NetworkApiStatus.DONE
                    loadConsolidateList(data, type)
                    getAnimalCategory(data)
                }

                Log.i("_status.value", _status.value.toString())
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
                Log.i("_status.value", _status.value.toString())
                _products.value = ArrayList()
            }
        }
    }

    //get the  products from web server that matches the search
    fun getProductsSearch(type: PriceSortType) {
        viewModelScope.launch {
            try {
                _status.value = NetworkApiStatus.LOADING
                Log.i("_status.value", _status.value.toString())
                val data = storeRepository.searchProduct(searchText)
                loadConsolidateList(data, type)
                _status.value = NetworkApiStatus.DONE
            } catch (t: Throwable) {
                _status.value = NetworkApiStatus.ERROR
                Log.i("_status.value", _status.value.toString())
                _products.value = ArrayList()
            }
        }
    }

    //insert a product in the local cart database
    fun insertOrder(item: ItemNetwork) {

        var intemParce = Utils.itemNetworkToItem(item)

        val order = Order(0, intemParce, 1, item.price)
        viewModelScope.launch {
            storeRepository.insertOrder(order)
        }
    }

    //group and organize the products by store , so the recycler view can put the headers
    fun loadConsolidateList(productList: List<ItemNetwork>, type: PriceSortType) {

        var productListSort = when (type) {
            PriceSortType.NORMAL -> productList
            PriceSortType.LOWER -> productList.sortedBy { it.price }
            PriceSortType.HIGHER -> productList.sortedByDescending { it.price }
        }

        var map = productListSort.groupBy { it.vetstore.name }

        val itemListTem: MutableList<ListItemStore> = ArrayList()

        for ((key, value) in map) {
            itemListTem.add(StoreItem(key))
            for (i in map.get(key)!!) {
                itemListTem.add(GeneralProduct(i))
            }
        }
        _products.value = itemListTem
    }

    //get the category of products offered by the store
    fun getAnimalCategory(productList: List<ItemNetwork>) {

        var map = productList.groupBy { it.product.animal_category }
        var list: List<ProductType> = map.keys.toList()
        _productType.value = list

    }

    //get the stores from the web service
    fun getStores() {
        viewModelScope.launch {
            try {
                val data = storeRepository.getStores()
                _stores.value = data
            } catch (t: Throwable) {
                Log.i("recycler_store", t.message)
            }
        }
    }

    fun getProductsFilter(productType: ProductType) {

        viewModelScope.launch {

            try {
                val data =
                    storeRepository.getProductsByCateogry(
                        productType.id, _storeId.value.toString()
                    )
                _productsFilter.postValue(data)
            } catch (t: Throwable) {
                _productsFilter.postValue(ArrayList())
            }
        }
    }

    fun displayFilterDataComplete() {
        _productsFilter.value = null
    }
}

