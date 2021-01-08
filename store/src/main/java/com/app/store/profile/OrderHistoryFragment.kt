package com.app.store.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.base.utils.MarginItemDecoration
import com.app.base.utils.NetworkApiStatus
import com.app.store.R
import com.app.store.databinding.FragmentOrderHistoryBinding
import com.app.store.profile.adapters.OrderAdapter
import com.app.store.profile.viewModel.OrderHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {

    private val viewModel: OrderHistoryViewModel by viewModels()

    private lateinit var binding: FragmentOrderHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_history, container, false)
        binding.setLifecycleOwner(this)
        binding.historyViewModel = viewModel
        setRecyclerView()
        listenerStatus()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.orderList.setItemAnimator(null);
        val myAdapter = OrderAdapter()
        binding.orderList.adapter = myAdapter

        binding.orderList.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.margin_m).toInt()
            )
        )
    }

    private fun listenerStatus() {
        viewModel.status.observe(requireActivity(), Observer {
            if (it != NetworkApiStatus.LOADING) {
                binding.loadingPanel.visibility = View.GONE
            }
        })
    }
}