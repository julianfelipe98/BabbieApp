package com.app.appointment.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.appointment.databinding.LayoutAppointmentViewBinding
import com.app.base.model.network.appointment.Appointment

class AppointmentAdapter() :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    private var appointmentList = emptyList<Appointment>()

    fun setData(appointment: List<Appointment>) {
        appointmentList = appointment
        notifyDataSetChanged()
    }

    class AppointmentViewHolder(private var binding: LayoutAppointmentViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val cancel = binding.cancelButton
        fun bind(appointment: Appointment) {
            binding.appointment = appointment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppointmentViewHolder {
        return AppointmentViewHolder(LayoutAppointmentViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {

        val appointment = appointmentList.get(position)
        holder.cancel.setOnClickListener {
            onItemClickListener?.onAppointmentClicked(appointment)
        }
        holder.bind(appointment)
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

    interface OnItemClickListener {
        fun onAppointmentClicked(appointment: Appointment)
    }
}


