package com.app.appointment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.app.appointment.databinding.LayoutVetViewBinding
import com.app.base.model.network.store.StoreNetwork

//Adapter for recyclerview of the Vets
class VetAdapter() :
    RecyclerView.Adapter<VetAdapter.VetViewHolder>(), Filterable {
    var onItemClickListener: OnItemClickListener? = null

    var consolidateList = emptyList<StoreNetwork>()
    var filterList = emptyList<StoreNetwork>()


    fun setData(items: List<StoreNetwork>) {
        consolidateList = items
        filterList = consolidateList.toList()

        notifyDataSetChanged()
    }

    class VetViewHolder(private var binding: LayoutVetViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val vet = binding.layoutVet
        fun bind(store: StoreNetwork) {
            binding.store = store
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VetViewHolder {
        return VetViewHolder(
            LayoutVetViewBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: VetViewHolder, position: Int) {
        val store = filterList.get(position)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onVetClicked(store)
        }
        holder.bind(store)
    }

    interface OnItemClickListener {
        fun onVetClicked(vet: StoreNetwork)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                var charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                } else {
                    val resultList = ArrayList<StoreNetwork>()
                    for (row in consolidateList) {

                        if (row.name.contains(
                                charSearch, ignoreCase = true
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }

                var results = FilterResults()
                results.values = filterList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.count != 0) filterList = results?.values as ArrayList<StoreNetwork>
                notifyDataSetChanged()
            }
        }
    }
}


