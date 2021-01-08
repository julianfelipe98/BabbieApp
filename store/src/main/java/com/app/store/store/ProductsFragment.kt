package com.app.store.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.base.model.network.store.ItemNetwork
import com.app.base.utils.NetworkApiStatus
import com.app.store.R
import com.app.store.databinding.FragmentProductsBinding
import com.app.store.store.adapters.FilterAdapter
import com.app.store.store.adapters.ListItemStore
import com.app.store.store.adapters.ProductAdapter
import com.app.store.store.adapters.StoreAdapter
import com.app.store.store.viewModel.ProductsViewModel
import com.app.store.utils.PriceSortType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var binding: FragmentProductsBinding
    val myAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)
        binding.lifecycleOwner = this

        binding.productViewModel = viewModel
        getArgs()
        setRecyclerView()
        setRecyclerStores()
        setRecyclerFilter()
        initViews()
        listenerStatus()
        return binding.root
    }

    private fun initViews() {
        binding.lowerButton.setOnClickListener {
            viewModel.getProducts(PriceSortType.LOWER)
        }
        binding.higherButton.setOnClickListener {
            viewModel.getProducts(PriceSortType.HIGHER)
        }
    }

    private fun getArgs() {
        val idStore = arguments?.let { ProductsFragmentArgs.fromBundle(it).selectedStore }

        if (idStore != null) {
            viewModel.filterStore(idStore)
            viewModel.getProducts(PriceSortType.NORMAL)
            initFilterObserver()
            initProductObserver()
        }

        val searchData = arguments?.let { ProductsFragmentArgs.fromBundle(it).searchText }
        if (searchData != null) {
            viewModel.filterSearch(searchData)
            viewModel.getProductsSearch(PriceSortType.NORMAL)
            binding.filterList.visibility = View.GONE
            binding.vetList.visibility = View.GONE
            binding.titleText.visibility = View.GONE
            binding.layoutFilter.visibility = View.GONE
            observeSearch()
        }
    }

    private fun observeSearch() {
        viewModel.products.observe(requireActivity(), Observer {
            if (it.isEmpty()) {
                binding.emptyText.visibility = View.VISIBLE
            }
        })
    }

    private fun setRecyclerView() {
        binding.productList.setItemAnimator(null);
        val layoutManager = GridLayoutManager(context, 2)
        binding.productList.layoutManager = layoutManager
        binding.productList.adapter = myAdapter
        myAdapter.onItemClickListener = object : ProductAdapter.OnItemClickListener {
            override fun onAdd(item: ItemNetwork) {
                viewModel.insertOrder(item)
                Toast.makeText(requireContext(), "Product added to cart", Toast.LENGTH_LONG)
                    .show()
            }
        }
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (myAdapter.getItemViewType(position)) {
                    ListItemStore.TYPE_STORE -> 2
                    else -> 1
                }
            }
        }
        initSearchListener()

    }

    private fun initSearchListener() {
        binding.searchEditText.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                myAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setRecyclerStores() {
        binding.vetList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.vetList.itemAnimator = null;
        binding.vetList.adapter =
            StoreAdapter(StoreAdapter.OnClickListener {
                viewModel.filterStore(it.id)
                viewModel.getProducts(PriceSortType.NORMAL)
            })
    }

    private fun setRecyclerFilter() {
        binding.filterList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.filterList.itemAnimator = null;
        binding.filterList.adapter =
            FilterAdapter(FilterAdapter.OnClickListener {
                viewModel.getProductsFilter(it)
            })

    }

    private fun initProductObserver(){
        viewModel.products.observe(requireActivity(), Observer {
            if (it.isEmpty()) {
                binding.emptyStore.visibility = View.VISIBLE
            } else {
                binding.emptyStore.visibility = View.GONE
            }
        })
    }

    private fun initFilterObserver(){
        viewModel.productsFilter.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    ProductsFragmentDirections.actionProductsFragmentToCategoryFragment(it.toTypedArray())
                )
                viewModel.displayFilterDataComplete()
            }
        })
    }

    private fun listenerStatus() {
        viewModel.status.observe(requireActivity(), Observer {
            if (it == NetworkApiStatus.LOADING) {
               binding.loadingPanelProducts.visibility = View.VISIBLE
            } else {
               binding.loadingPanelProducts.visibility = View.GONE
            }
        })

        viewModel.stores.observe(requireActivity(), Observer {
            if (it.isNotEmpty()) {
                binding.loadingPanelVet.visibility = View.GONE
            }
        })
    }
}
