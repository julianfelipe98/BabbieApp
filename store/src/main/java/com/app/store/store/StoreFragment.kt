package com.app.store.store

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.base.utils.NetworkApiStatus
import com.app.store.R
import com.app.store.databinding.FragmentStoreBinding
import com.app.store.store.adapters.StoreAdapter
import com.app.store.store.viewModel.StoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreFragment : Fragment() {

    private val viewModel: StoreViewModel by viewModels()
    private lateinit var binding: FragmentStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)
        binding.lifecycleOwner = this
        binding.storeViewModel = viewModel
        setRecyclerView()
        initViews()
        listenerStatus()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.vetList.itemAnimator = null;
        binding.vetList.adapter =
            StoreAdapter(StoreAdapter.OnClickListener {
                this.findNavController().navigate(
                    StoreFragmentDirections.actionStoreFragmentToProductsFragment(
                        it.id,
                        null
                    )
                )
            })
    }

    private fun listenerStatus() {
        viewModel.status.observe(requireActivity(), Observer {
            if (it != NetworkApiStatus.LOADING) {
                binding.loadingPanel.visibility = View.GONE
            }
        })
    }

    private fun initViews() {
        binding.searchEditText.setOnClickListener {
            val text = binding.searchEditText.text.toString()
            binding.searchEditText.text.clear()
            if (text.isNotEmpty()) {
                val imm: InputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            }

            if (text.isNotEmpty()) this.findNavController()
                .navigate(StoreFragmentDirections.actionStoreFragmentToProductsFragment(null, text))
            else Toast.makeText(
                requireContext(),
                "Por favor ingresa un texto de busqueda",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
