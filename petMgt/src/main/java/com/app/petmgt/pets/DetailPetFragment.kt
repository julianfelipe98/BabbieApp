package com.app.petmgt.pets

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.base.model.network.petMgt.Pet
import com.app.petmgt.R
import com.app.petmgt.databinding.FragmentDetailPetBinding
import com.app.petmgt.pets.viewModel.CreatePetViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPetFragment : Fragment() {
    private lateinit var binding: FragmentDetailPetBinding
    private val viewModel: CreatePetViewModel by viewModels()
    private var pet = Pet()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_pet, container, false)
        binding.lifecycleOwner = this
        pet = DetailPetFragmentArgs.fromBundle(requireArguments()).pet
        viewModel.getPetData(pet.id)
        binding.viewModel = viewModel
        initListeners()
        startObserver()
        return binding.root
    }

    fun initListeners() {
        binding.editButton.setOnClickListener {
            findNavController().navigate(
                DetailPetFragmentDirections.actionDetailPetFragmentToCreatePetFragment(
                    pet.id
                )
            )
        }
        binding.vaccineButton.setOnClickListener {
            val uri = Uri.parse("myApp://fragmentRecord/${binding.vaccineButton.tag}/${pet.id}")
            findNavController().navigate(uri)
        }

        binding.dewormingButton.setOnClickListener {
            val uri = Uri.parse("myApp://fragmentRecord/${binding.dewormingButton.tag}/${pet.id}")
            findNavController().navigate(uri)
        }

        binding.medicineButtons.setOnClickListener {
            val uri =
                Uri.parse("myApp://fragmentRecordGeneral/${binding.medicineButtons.tag}/${pet.id}")
            findNavController().navigate(uri)
        }

        binding.appointmentButtons.setOnClickListener {
            val uri =
                Uri.parse("myApp://fragmentAppointment/${binding.appointmentButtons.tag}/${pet.id}")
            findNavController().navigate(uri)
        }

        binding.petGroomingButtons.setOnClickListener {
            val uri =
                Uri.parse("myApp://fragmentAppointment/${binding.petGroomingButtons.tag}/${pet.id}")
            findNavController().navigate(uri)
        }

    }

    private fun startObserver() {
        viewModel.pet.observe(requireActivity(), Observer {
            if (it != null) {
                binding.loadingPanel.visibility = View.GONE
            }
        })
    }
}