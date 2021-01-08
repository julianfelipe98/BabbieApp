package com.app.auth.profile

import android.content.Intent
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
import com.app.auth.R
import com.app.auth.databinding.FragmentProfileBinding
import com.app.auth.profile.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        viewModel.getUserData()
        initUserObserver()
        addBottomListeners()
        return binding.root
    }

    private fun initUserObserver() {
        viewModel.user.observe(requireActivity(), Observer {
            if (it != null) binding.loadingPanel.visibility = View.GONE
            binding.user = it
        })
    }

    private fun addBottomListeners() {

        binding.buttonMyOrders.setOnClickListener {

            val uri = Uri.parse("myApp://fragmentOrder")
            findNavController().navigate(uri)
        }

        binding.buttonSaveChanges.setOnClickListener {
            val textName: String = editTextTextName.text.toString()
            val textPhone: String = editTextPhone.text.toString()
            val textAddress: String = editTextTextPostalAddress.text.toString()
            when {
                textName.trim().isEmpty() -> editTextTextName.error =
                    getString(R.string.mandatory_field)
                textPhone.trim().isEmpty() -> editTextPhone.error =
                    getString(R.string.mandatory_field)
                textAddress.trim().isEmpty() -> editTextTextPostalAddress.error =
                    getString(R.string.mandatory_field)
                else -> {
                    viewModel.updateUser()
                    editTextTextName.clearFocus()
                    editTextPhone.clearFocus()
                    editTextTextPostalAddress.clearFocus()
                }
            }
        }
        binding.buttonLogOut.setOnClickListener {

            logOut()

        }
    }

    private fun logOut() {
        viewModel.logOut()
        viewModel.finishActivity.observe(requireActivity(), Observer { isFinish ->
            if (isFinish) {
                val intent = Intent(Intent.ACTION_MAIN, Uri.parse("com.app.auth://login"))
                startActivity(intent)
                activity?.finish()
            }
        })
    }
}