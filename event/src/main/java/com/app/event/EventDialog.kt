package com.app.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import com.app.event.databinding.LayoutImageDialogBinding

class EventDialog : DialogFragment() {

    private lateinit var binding: LayoutImageDialogBinding
    private lateinit var activity: LifecycleOwner
    private var imageUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_image_dialog,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.imageUrl = imageUrl
        return binding.root;
    }

    fun addData(string: String) {
        imageUrl = string
    }

    companion object {
        const val TAG = "TAG"
        fun newInstance(activity: LifecycleOwner): EventDialog {
            val customDialog = EventDialog()
            customDialog.activity = activity
            return customDialog
        }
    }
}