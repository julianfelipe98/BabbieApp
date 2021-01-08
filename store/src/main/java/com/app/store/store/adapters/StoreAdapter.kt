package com.app.store.store.adapters

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.database.Store
import com.app.store.databinding.LayoutStoreViewBinding

class StoreAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private var storeList = emptyList<Store>()

    fun setData(stores: List<Store>) {
        storeList = stores
        notifyDataSetChanged()
    }

    class StoreViewHolder(private var binding: LayoutStoreViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(store: Store) {
            binding.store = store
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoreViewHolder {
        return StoreViewHolder(
            LayoutStoreViewBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storeList[position]

        holder.itemView.setOnClickListener {
            onClickListener.onClick(store)
        }
        holder.bind(store)
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    class OnClickListener(val clickListener: (store: Store) -> Unit) {
        fun onClick(store: Store) = clickListener(store)
    }

}


