package com.app.cart.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.database.Order
import com.app.cart.databinding.LayoutCartItemViewBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    private var cartList = emptyList<Order>()

    fun setData(stores: List<Order>) {
        cartList = stores
        notifyDataSetChanged()
    }

    class CartViewHolder(private var binding: LayoutCartItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val addButton = binding.plusButton
        val reduceButton = binding.minusButton
        val image = binding.petImage
        val delete = binding.trash
        fun bind(itemStore: Order) {
            binding.item = itemStore
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        return CartViewHolder(LayoutCartItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val order = cartList.get(position)
        holder.addButton.setOnClickListener {
            onItemClickListener?.onAdd(order)
        }

        holder.reduceButton.setOnClickListener {
            onItemClickListener?.onReduce(order)
        }
        holder.image.setOnClickListener {
            onItemClickListener?.onItemClick(order)
        }

        holder.delete.setOnClickListener {
            onItemClickListener?.onDelete(order)
        }
        holder.bind(order)
    }

    interface OnItemClickListener {
        fun onItemClick(item: Order)
        fun onAdd(item: Order)
        fun onReduce(item: Order)
        fun onDelete(item: Order)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}


