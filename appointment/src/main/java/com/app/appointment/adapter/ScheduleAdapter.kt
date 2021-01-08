package com.app.appointment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.appointment.databinding.DateHeaderLayoutBinding
import com.app.appointment.databinding.LayoutTimeItemViewBinding

import com.app.base.model.network.appointment.Appointment

class ScheduleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: ScheduleAdapter.OnItemClickListener? = null
    private var consolidateList = emptyList<ListItem>()

    fun setData(items: List<ListItem>) {
        consolidateList = items
        notifyDataSetChanged()
    }

    class TimeViewHolder(private var binding: LayoutTimeItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val button = binding.timeButton
        fun bind(time: GeneralItem) {
            binding.time = time
            binding.executePendingBindings()
        }
    }

    class DateViewHolder(private var binding: DateHeaderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val text = binding.dateText
        fun bind(date: DateItem) {
            binding.date = date
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            ListItem.TYPE_GENERAL -> return TimeViewHolder(
                LayoutTimeItemViewBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            ListItem.TYPE_DATE -> return DateViewHolder(
                DateHeaderLayoutBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            else -> return TimeViewHolder(
                LayoutTimeItemViewBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            ListItem.TYPE_GENERAL -> {
                var generalItem = consolidateList.get(position) as GeneralItem
                var holderSchedule = (holder as TimeViewHolder)
                holderSchedule.button.setOnClickListener {
                    onItemClickListener?.onTimeClicked(generalItem.appointment)
                }
                holder.bind(generalItem)
            }
            ListItem.TYPE_DATE -> {
                var dateItem = consolidateList.get(position)
                (holder as DateViewHolder).bind(dateItem as DateItem)
            }
        }
    }

    interface OnItemClickListener {
        fun onTimeClicked(appointment: Appointment)
    }

    override fun getItemViewType(position: Int): Int {
        return consolidateList.get(position).getType()
    }

    override fun getItemCount(): Int {
        return consolidateList.size
    }
}


