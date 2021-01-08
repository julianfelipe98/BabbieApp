package com.app.store.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.app.base.model.network.store.ItemNetwork
import com.app.store.R
import com.app.store.databinding.FragmentCategoryBinding
import com.app.store.store.adapters.ListItemStore
import com.app.store.store.adapters.ProductAdapter
import com.app.store.store.viewModel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {


    lateinit var binding: FragmentCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()
    val myAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        binding.lifecycleOwner = this
        initViewModel()
        binding.categoryViewModel = viewModel
        setRecyclerView()
        return binding.root
    }

    private fun initViewModel() {
        val filterList: List<ItemNetwork> =
            CategoryFragmentArgs.fromBundle(requireArguments()).selectedFilter.toList()
        viewModel.setListCategory(filterList)
    }

    private fun setRecyclerView() {

        val layoutManager = GridLayoutManager(context, 2)
        binding.productListCategory.layoutManager = layoutManager
        binding.productListCategory.adapter = myAdapter
        myAdapter.onItemClickListener = object : ProductAdapter.OnItemClickListener {
            override fun onAdd(item: ItemNetwork) {
                viewModel.insertOrder(item)
                Toast.makeText(requireContext(), "Item agregado al carrito", Toast.LENGTH_LONG)
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
}