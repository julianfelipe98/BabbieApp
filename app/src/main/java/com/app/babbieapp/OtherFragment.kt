package com.app.babbieapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.babbieapp.databinding.FragmentOtherBinding

class OtherFragment : Fragment() {

    private lateinit var binding: FragmentOtherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_other, container, false)

        startListeners()
        return binding.root
    }

    private fun startListeners() {
        binding.buttonProfile.setOnClickListener {
            val uri = Uri.parse("myApp://fragmentProfile")
            findNavController().navigate(uri)
        }

        binding.buttonEvents.setOnClickListener {
            val uri = Uri.parse("myApp://fragmentEvent")
            findNavController().navigate(uri)
        }
    }
}