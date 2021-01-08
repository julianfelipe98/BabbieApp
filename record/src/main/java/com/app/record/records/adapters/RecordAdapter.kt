package com.app.record.records.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.network.records.Record
import com.app.record.databinding.RecordViewLayoutBinding

class RecordAdapter(val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    var recordList = emptyList<Record>()

    fun setData(record: List<Record>) {
        recordList = record
        notifyDataSetChanged()
    }

    class RecordViewHolder(private var binding: RecordViewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val edit = binding.edit
        fun bind(record: Record) {
            binding.record = record
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordViewHolder {
        return RecordViewHolder(RecordViewLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {

        val record = recordList.get(position)
        holder.edit.setOnClickListener {
            onClickListener.onClick(record)
        }
        holder.bind(record)
    }

    class OnClickListener(val clickListener: (record: Record) -> Unit) {
        fun onClick(record: Record) = clickListener(record)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }
}


