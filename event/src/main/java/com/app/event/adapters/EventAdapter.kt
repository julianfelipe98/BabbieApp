package com.app.event.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.network.events.Event
import com.app.event.databinding.DateEventHeaderLayoutBinding
import com.app.event.databinding.LayoutEventItemViewBinding

class EventAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    private var consolidateList = emptyList<ListItem>()
    private var mContext: Context? = null

    fun setContext(context: Context) {
        mContext = context
    }

    fun setData(items: List<ListItem>) {
        consolidateList = items
        notifyDataSetChanged()
    }

    class EventViewHolder(private var binding: LayoutEventItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var data = binding.item
        var image = binding.eventImage
        fun bind(event: GeneralItem) {
            binding.item = event
            binding.executePendingBindings()
        }
    }

    class DateViewHolder(private var binding: DateEventHeaderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: DateItem) {
            binding.date = date
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            ListItem.TYPE_GENERAL -> return EventViewHolder(
                LayoutEventItemViewBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            ListItem.TYPE_DATE -> return DateViewHolder(
                DateEventHeaderLayoutBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
            else -> return EventViewHolder(
                LayoutEventItemViewBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            ListItem.TYPE_GENERAL -> {
                var generalItem = consolidateList.get(position) as GeneralItem
                var holderEvent = (holder as EventViewHolder)
                if (generalItem.event.imageUrl == "") {
                    holderEvent.image.visibility = View.GONE
                    setMargins(holderEvent.image)
                }
                holderEvent.image.setOnClickListener {
                    onItemClickListener?.onClick(generalItem.event, holderEvent.image)
                }
                holder.bind(generalItem)
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

    fun setMargins(imageView: ImageView) {
        val params = imageView.layoutParams as ViewGroup.MarginLayoutParams
        params.rightMargin = 24
        imageView.layoutParams = params
    }

    interface OnItemClickListener {
        fun onClick(event: Event, imageView: ImageView)
    }
}


