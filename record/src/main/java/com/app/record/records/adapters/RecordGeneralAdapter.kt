package com.app.record.records.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.network.records.Record
import com.app.record.databinding.RecordPrescriptionViewLayoutBinding

class RecordGeneralAdapter:
    RecyclerView.Adapter<RecordGeneralAdapter.RecordViewHolder>() {

    private var recordList = emptyList<Record>()

    fun setData(record: List<Record>) {
        recordList = record
        notifyDataSetChanged()
    }

    class RecordViewHolder(private var binding: RecordPrescriptionViewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(record: Record) {
            binding.record = record
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordViewHolder {
        return RecordViewHolder(
            RecordPrescriptionViewLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {

        val record = recordList.get(position)
        holder.bind(record)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }
}


