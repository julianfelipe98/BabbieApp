package com.app.appointment.utils


import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.appointment.R
import com.app.appointment.adapter.AppointmentAdapter
import com.app.appointment.adapter.ListItem
import com.app.appointment.adapter.ScheduleAdapter
import com.app.appointment.adapter.VetAdapter
import com.app.base.model.network.appointment.Appointment
import com.app.base.model.network.store.StoreNetwork
import com.app.base.utils.AppointmentStatus
import com.app.base.utils.NetworkApiStatus
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Appointment>?) {
    val adapter = recyclerView.adapter as AppointmentAdapter
    if (data != null) {
        adapter.setData(data)
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.pet_bottle)
            )
            .into(imgView)
    }
}

@BindingAdapter("listDataStore")
fun bindRecyclerViewStore(recyclerView: RecyclerView, data: List<StoreNetwork>?) {
    val adapter = recyclerView.adapter as VetAdapter

    if (data != null) {
        adapter.setData(data)
    }
}

@BindingAdapter("listDataSchedule")
fun bindRecyclerViewOrder(recyclerView: RecyclerView, data: List<ListItem>?) {
    val adapter = recyclerView.adapter as ScheduleAdapter

    if (data != null) {
        adapter.setData(data)
    }
}

@BindingAdapter("petApiStatus")
fun bindStatus(statusImageView: ImageView, status: NetworkApiStatus?) {
    when (status) {
        NetworkApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NetworkApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.cloud_off_outline)
        }
        NetworkApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("appointmentStatus")
fun bindAppointmentStatus(button: Button, status: AppointmentStatus?) {

    when (status) {
        AppointmentStatus.CANCEL -> {
            button.setText(R.string.canceled_label)
            button.setTextColor(Color.RED)
            button.isEnabled = false
        }
        NetworkApiStatus.ERROR -> {
        }
    }
}


