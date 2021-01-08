package com.app.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.appointment.adapter.ListItem
import com.app.appointment.adapter.ScheduleAdapter
import com.app.appointment.databinding.FragmentAppointmentListBinding
import com.app.appointment.viewModels.ScheduleViewModel
import com.app.base.model.network.appointment.Appointment
import com.app.base.utils.NetworkApiStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentListFragment : Fragment() {

    private val viewModel: ScheduleViewModel by viewModels()
    private lateinit var binding: FragmentAppointmentListBinding
    private var tagAppointment = ""
    private var idPet = ""
    private var idVet = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_appointment_list, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initViews()
        setRecyclerView()
        initObserverAppointment()
        listenerStatus()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.orderList.itemAnimator = null;
        val myAdapter = ScheduleAdapter()
        val layoutManager = GridLayoutManager(context, 3)
        binding.orderList.layoutManager = layoutManager
        binding.orderList.adapter = myAdapter
        myAdapter.onItemClickListener = object : ScheduleAdapter.OnItemClickListener {
            override fun onTimeClicked(appointment: Appointment) {
                viewModel.assignAppointment(appointment.id)
                observerAssigned()
                findNavController().navigate(
                    AppointmentListFragmentDirections.actionAppointmentListFragmentToAppointmentPetListFragment(
                        idPet,
                        tagAppointment
                    )
                )
            }
        }
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (myAdapter.getItemViewType(position)) {
                    ListItem.TYPE_DATE -> 3
                    else -> 1
                }
            }
        }
    }

    private fun observerAssigned() {
        viewModel.response.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it.response, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initViews() {
        idPet = AppointmentListFragmentArgs.fromBundle(requireArguments()).idPet
        tagAppointment = AppointmentListFragmentArgs.fromBundle(requireArguments()).appointmentTag
        idVet = AppointmentListFragmentArgs.fromBundle(requireArguments()).idVet
        viewModel.setBasicData(tagAppointment, idPet, idVet)
    }

    private fun initObserverAppointment() {
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