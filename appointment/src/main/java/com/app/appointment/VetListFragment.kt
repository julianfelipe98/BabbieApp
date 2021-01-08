package com.app.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.appointment.adapter.VetAdapter
import com.app.appointment.databinding.FragmentVetListBinding
import com.app.appointment.viewModels.VetViewModel
import com.app.base.model.network.store.StoreNetwork
import com.app.base.utils.NetworkApiStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VetListFragment : Fragment() {

    private val viewModel: VetViewModel by viewModels()
    private lateinit var binding: FragmentVetListBinding
    private val myAdapter = VetAdapter()
    private var tagAppointment = ""
    private var idPet = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vet_list, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setRecyclerView()
        initSearchView()
        initViews()
        listenerStatus()
        return binding.root
    }

    private fun initViews() {
        idPet = VetListFragmentArgs.fromBundle(requireArguments()).idPet
        tagAppointment = VetListFragmentArgs.fromBundle(requireArguments()).appointmentTag
        viewModel.getStores(tagAppointment)
    }

    private fun setRecyclerView() {

        binding.vetList.setItemAnimator(null)
        binding.vetList.adapter = myAdapter
        myAdapter.onItemClickListener = object : VetAdapter.OnItemClickListener {
            override fun onVetClicked(store: StoreNetwork) {
                findNavController().navigate(
                    VetListFragmentDirections.actionVetListFragmentToAppointmentListFragment(
                        idPet, tagAppointment, store.id
                    )
                )
            }
        }
    }

    private fun initSearchView() {
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

    private fun listenerStatus() {
        viewModel.status.observe(requireActivity(), Observer {
            if (it != NetworkApiStatus.LOADING) {
                binding.loadingPanel.visibility = View.GONE
            }
        })
    }
}