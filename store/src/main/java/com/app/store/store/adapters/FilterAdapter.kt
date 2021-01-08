package com.app.store.store.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.network.store.ProductType
import com.app.store.databinding.LayoutFilterItemViewBinding

class FilterAdapter(val onClickListener: OnClickListener) :
    RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var filterType = emptyList<ProductType>()

    fun setData(stores: List<ProductType>) {
        filterType = stores
        notifyDataSetChanged()
    }

    class FilterViewHolder(private var binding: LayoutFilterItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val button = binding.filterButton

        fun bind(filter: ProductType) {
            binding.filterButton.text = filter.name
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterViewHolder {
        return FilterViewHolder(
            LayoutFilterItemViewBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = filterType.get(position)

        holder.button.setOnClickListener {
            onClickListener.onClick(filter)
        }
        holder.bind(filter)
    }

    override fun getItemCount(): Int {
        return filterType.size
    }

    class OnClickListener(val clickListener: (filter: ProductType) -> Unit) {
        fun onClick(filter: ProductType) = clickListener(filter)
    }

}


