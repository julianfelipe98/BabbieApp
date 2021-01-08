package com.app.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.base.model.network.events.Event
import com.app.base.utils.MarginItemDecoration
import com.app.base.utils.NetworkApiStatus
import com.app.event.adapters.EventAdapter
import com.app.event.databinding.FragmentEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels()
    private lateinit var binding: FragmentEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_event, container, false)
        binding.lifecycleOwner = this
        binding.eventViewModel = viewModel
        setRecyclerView()
        initObserverEvents()
        listenerStatus()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.orderList.itemAnimator = null;
        val myAdapter = EventAdapter()
        myAdapter.setContext(requireContext())
        binding.orderList.adapter = myAdapter
        myAdapter.onItemClickListener = object : EventAdapter.OnItemClickListener {
            override fun onClick(event: Event, imageView: ImageView) {
                initDialog(event.imageUrl)
            }
        }
        binding.orderList.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.margin_m).toInt()
            )
        )
    }

    private fun initDialog(imageUrl: String) {
        val transaction = childFragmentManager.beginTransaction()
        val previous = childFragmentManager.findFragmentByTag(EventDialog.TAG)
        if (previous != null) {
            transaction.remove(previous)
        }
        transaction.addToBackStack(null)
        val dialogFragment = EventDialog.newInstance(requireActivity())
        dialogFragment.show(transaction, EventDialog.TAG)
        dialogFragment.addData(imageUrl)
    }

    private fun initObserverEvents() {
        viewModel.listItem.observe(requireActivity(), Observer {
            if (it.isEmpty()) {
                binding.statusImage.visibility = View.VISIBLE
            } else {
                binding.statusImage.visibility = View.GONE
            }
        })
    }

    private fun listenerStatus() {
        viewModel.status.observe(requireActivity(), Observer {
            if (it != NetworkApiStatus.LOADING) {
                binding.loadingPanel.visibility = View.GONE
            }
        })
    }
}