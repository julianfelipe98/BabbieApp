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
import com.app.appointment.adapter.AppointmentAdapter
import com.app.appointment.databinding.FragmentAppointmentPetListBinding
import com.app.appointment.viewModels.AppointmentViewModel
import com.app.base.model.network.appointment.Appointment
import com.app.base.utils.MarginItemDecoration
import com.app.base.utils.NetworkApiStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentPetListFragment : Fragment() {

    private val viewModel: AppointmentViewModel by viewModels()
    private lateinit var binding: FragmentAppointmentPetListBinding
    private var tagAppointment = ""
    private var idPet = ""
    private val myAdapter = AppointmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_appointment_pet_list,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setRecyclerView()
        initObserverRecords()
        initViews()
        initListeners()
        listenerStatus()

        return binding.root
    }

    private fun initViews() {
        idPet = AppointmentPetListFragmentArgs.fromBundle(requireArguments()).idPet
        tagAppointment =
            AppointmentPetListFragmentArgs.fromBundle(requireArguments()).appointmentTag
        viewModel.getRecords(idPet, tagAppointment)
    }

    private fun setRecyclerView() {

        val cancelString = resources.getString(R.string.canceled_label)
        binding.appointmentList.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.margin_m).toInt()
            )
        )
        binding.appointmentList.adapter = myAdapter
        myAdapter.onItemClickListener = object : AppointmentAdapter.OnItemClickListener {
            override fun onAppointmentClicked(appointment: Appointment) {
                viewModel.cancelAppointment(appointment.id, cancelString)
                viewModel.getRecords(idPet, tagAppointment)
                startObserver()
                observerCancel()
            }
        }
    }

    private fun startObserver() {
        viewModel.appointment.observe(requireActivity(), Observer {
            myAdapter.setData(it)
        })
    }

    private fun observerCancel() {
        viewModel.response.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it.response, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initObserverRecords() {
        viewModel.appointment.observe(requireActivity(), Observer {
            if (it.size == 0) {
                binding.statusImage.visibility = View.VISIBLE
            } else {
                binding.statusImage.visibility = View.GONE
            }
        })
    }

    private fun initListeners() {
        binding.buttonSchedule.setOnClickListener {
            findNavController().navigate(
                AppointmentPetListFragmentDirections.actionAppointmentPetListFragmentToVetListFragment(
                    idPet,
                    tagAppointment
                )
            )
        }
    }

    private fun listenerStatus() {
        viewModel.status.observe(requireActivity(), Observer {
            if (it != NetworkApiStatus.LOADING) {
                binding.loadingPanelA.visibility = View.GONE
            }
        })
    }
}