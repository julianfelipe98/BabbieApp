package com.app.store.profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.store.databinding.LayoutOrderItemViewBinding
import com.app.store.databinding.TextHeaderLayoutBinding

class OrderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var consolidateList = emptyList<ListItem>()

    fun setData(items: List<ListItem>) {
        consolidateList = items
        notifyDataSetChanged()
    }

    class OrderViewHolder(private var binding: LayoutOrderItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemStore: GeneralItem) {
            binding.item = itemStore
            binding.executePendingBindings()
        }
    }

    class DateViewHolder(private var binding: TextHeaderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val text = binding.dateText
        fun bind(date: DateItem) {
            binding.date = date
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            ListItem.TYPE_GENERAL -> return OrderViewHolder(
                LayoutOrderItemViewBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            ListItem.TYPE_DATE -> return DateViewHolder(
                TextHeaderLayoutBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            else -> return OrderViewHolder(
                LayoutOrderItemViewBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            ListItem.TYPE_GENERAL -> {
                var generalItem = consolidateList.get(position)
                (holder as OrderViewHolder).bind(generalItem as GeneralItem)
            }
            ListItem.TYPE_DATE -> {
                var dateItem = consolidateList.get(position)
                (holder as DateViewHolder).bind(dateItem as DateItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return consolidateList.get(position).getType()
    }

    override fun getItemCount(): Int {
        return consolidateList.size
    }
}


