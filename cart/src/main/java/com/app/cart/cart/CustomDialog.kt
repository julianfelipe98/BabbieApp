package com.app.cart.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.app.base.utils.NetworkApiStatus
import com.app.cart.databinding.LayoutOrderDialogBinding


//Dialog for  data confirmation before de order is make
class CustomDialog : DialogFragment() {

    private lateinit var binding: LayoutOrderDialogBinding
    private lateinit var activity: LifecycleOwner
    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            com.app.cart.R.layout.layout_order_dialog,
            container,
            false
        )
        initViews()
        return binding.root;
    }

    fun initViews() {
        binding.viewModel = viewModel

        binding.confirmButton.setOnClickListener {
            viewModel.makeOrder()
            viewModel.status.observe(requireActivity(), Observer {
                if (it == NetworkApiStatus.LOADING) {
                    binding.loadingPanel.visibility = View.VISIBLE
                } else {
                    binding.loadingPanel.visibility = View.GONE
                    dismiss()
                }
            })
        }
        binding.cancelButton.setOnClickListener {
            dismiss();
        }
    }

    fun addViewModel(viewModel: CartViewModel) {
        this.viewModel = viewModel
    }

    companion object {
        const val TAG = "TAG"
        fun newInstance(activity: LifecycleOwner): CustomDialog {
            val customDialog = CustomDialog()
            customDialog.activity = activity
            return customDialog
        }
    }
}