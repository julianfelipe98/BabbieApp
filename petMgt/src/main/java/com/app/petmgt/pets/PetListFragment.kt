package com.app.petmgt.pets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.base.utils.MarginItemDecoration
import com.app.base.utils.NetworkApiStatus
import com.app.petmgt.R
import com.app.petmgt.databinding.FragmentPetListBinding
import com.app.petmgt.pets.adapters.PetAdapter
import com.app.petmgt.pets.viewModel.PetListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetListFragment : Fragment() {

    private val viewModel: PetListViewModel by viewModels()
    private lateinit var binding: FragmentPetListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_list, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setRecyclerView()
        navigate()
        initObserverRecords()
        listenerStatus()
        return binding.root
    }

    private fun navigate() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(
                PetListFragmentDirections.actionPetListFragmentToCreatePetFragment(
                    null
                )
            )
        }
    }

    private fun setRecyclerView() {
        binding.petList.itemAnimator = null;
        binding.petList.adapter =
            PetAdapter(PetAdapter.OnClickListener {
                findNavController().navigate(
                    PetListFragmentDirections.actionPetListFragmentToDetailPetFragment(
                        it
                    )
                )
            })

        binding.petList.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.margin_m).toInt()
            )
        )
    }

    private fun initObserverRecords() {
        viewModel.pets.observe(requireActivity(), Observer {
            if (it.size == 0) {
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