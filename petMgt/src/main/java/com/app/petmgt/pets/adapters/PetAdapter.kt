package com.app.petmgt.pets.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.network.petMgt.Pet
import com.app.petmgt.databinding.PetListLayoutBinding

class PetAdapter(val onClickListener: OnClickListener) :
    RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    private var petList = emptyList<Pet>()

    fun setData(pet: List<Pet>) {
        petList = pet
        notifyDataSetChanged()
    }

    class PetViewHolder(private var binding: PetListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val cardView = binding.petCardView
        fun bind(pet: Pet) {
            binding.pet = pet
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetViewHolder {
        return PetViewHolder(PetListLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {

        val pet = petList.get(position)
        holder.cardView.setOnClickListener {
            onClickListener.onClick(pet)
        }
        holder.bind(pet)
    }

    class OnClickListener(val clickListener: (pet: Pet) -> Unit) {
        fun onClick(pet: Pet) = clickListener(pet)
    }

    override fun getItemCount(): Int {
        return petList.size
    }
}


