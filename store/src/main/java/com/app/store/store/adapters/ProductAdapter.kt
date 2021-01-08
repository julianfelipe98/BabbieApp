package com.app.store.store.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.network.store.ItemNetwork
import com.app.store.databinding.LayoutProductViewBinding
import com.app.store.databinding.TextHeaderLayoutProductBinding


class ProductAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var onItemClickListener: OnItemClickListener? = null
    var consolidateList = emptyList<ListItemStore>()
    var filterList = emptyList<ListItemStore>()

    fun setData(items: List<ListItemStore>) {
        consolidateList = items
        filterList = consolidateList.toList()
        notifyDataSetChanged()
    }

    class ProductViewHolder(private var binding: LayoutProductViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val addButton = binding.addButton
        fun bind(product: GeneralProduct) {
            binding.product = product
            binding.executePendingBindings()
        }

    }

    class StoreViewHolder(private var binding: TextHeaderLayoutProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val text = binding.dateText
        fun bind(name: StoreItem) {
            binding.name = name
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            ListItemStore.TYPE_GENERAL -> return ProductViewHolder(
                LayoutProductViewBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            ListItemStore.TYPE_STORE -> return StoreViewHolder(
                TextHeaderLayoutProductBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            else -> return ProductViewHolder(
                LayoutProductViewBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ListItemStore.TYPE_GENERAL -> {

                val generalItem = filterList[position] as GeneralProduct
                val holderProduct = (holder as ProductViewHolder)
                holderProduct.addButton.setOnClickListener {
                    onItemClickListener?.onAdd(generalItem.product)
                }
                holder.bind(generalItem)

            }
            ListItemStore.TYPE_STORE -> {
                val itemStore = filterList[position]
                (holder as StoreViewHolder).bind(itemStore as StoreItem)

            }
        }
    }

    interface OnItemClickListener {
        fun onAdd(item: ItemNetwork)
    }

    override fun getItemViewType(position: Int): Int {
        return filterList[position].getType()
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val charSearch = constraint.toString()
                if (charSearch.isNotEmpty()) {
                    val resultList = ArrayList<ListItemStore>()
                    for (row in consolidateList) {
                        if (row.getType() == ListItemStore.TYPE_GENERAL) {
                            val item2 = row as GeneralProduct
                            if (item2.product.product.name.contains(
                                    charSearch, ignoreCase = true
                                )
                            ) {
                                resultList.add(item2)
                            }
                        } else {
                            val item2 = row as StoreItem
                            resultList.add(item2)
                        }
                    }
                    filterList = resultList
                }

                val results = FilterResults()
                results.values = filterList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<ListItemStore>
                notifyDataSetChanged()
            }
        }
    }


}


