package com.app.cart.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.base.model.database.Order
import com.app.base.utils.MarginItemDecoration
import com.app.base.utils.NetworkApiStatus
import com.app.cart.R
import com.app.cart.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment() {


    private val viewModel: CartViewModel by viewModels()
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        binding.lifecycleOwner = this
        binding.cartViewModel = viewModel

        initViews()
        return binding.root
    }

    private fun initViews() {
        setRecyclerView()
        makeOrder()
        cleanCart()
        initObserverCart()
        initObserverMakeOrder()
    }

    private fun initObserverCart() {
        viewModel.getAllOrders().observe(requireActivity(), Observer {
            if (it.isEmpty()) {
                binding.buyLayout.visibility = View.GONE
                binding.cleanButton.visibility = View.GONE
                binding.statusImage.visibility = View.VISIBLE
            } else {
                binding.buyLayout.visibility = View.VISIBLE
                binding.cleanButton.visibility = View.VISIBLE
                binding.statusImage.visibility = View.GONE
            }
            Log.e("Orders", it.toString())
        })
    }

    private fun initObserverMakeOrder() {
        viewModel.status.observe(requireActivity(), Observer {
            if (it == NetworkApiStatus.ERROR) {
                Toast.makeText(
                    requireContext(),
                    "Error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun setRecyclerView() {
        binding.itemList.setItemAnimator(null);
        val myAdapter = CartAdapter()
        binding.itemList.adapter = myAdapter
        myAdapter.onItemClickListener = object : CartAdapter.OnItemClickListener {
            override fun onItemClick(item: Order) {
                TODO("Not yet implemented")
            }

            override fun onAdd(item: Order) {
                viewModel.changeQuantity(item, "+")
            }

            override fun onReduce(item: Order) {
                viewModel.changeQuantity(item, "-")
            }

            override fun onDelete(item: Order) {
                viewModel.deleteOrder(item.id)
            }

        }

        binding.itemList.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.margin_m).toInt()
            )
        )
    }

    private fun makeOrder() {
        binding.orderButton.setOnClickListener {
            initDialog()
        }
    }

    private fun cleanCart() {
        binding.cleanButton.setOnClickListener {
            viewModel.cleanCart()
        }
    }


    private fun initDialog() {
        val transaction = childFragmentManager.beginTransaction()
        val previous = childFragmentManager.findFragmentByTag(CustomDialog.TAG)
        if (previous != null) {
            transaction.remove(previous)
        }
        transaction.addToBackStack(null)
        val dialogFragment = CustomDialog.newInstance(requireActivity())
        dialogFragment.show(transaction, CustomDialog.TAG)
        dialogFragment.addViewModel(viewModel)
    }
}