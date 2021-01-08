package com.app.event.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.event.adapters.EventAdapter
import com.app.event.adapters.ListItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("listDataOrder")
fun bindRecyclerViewOrder(recyclerView: RecyclerView, data: List<ListItem>?) {
    val adapter = recyclerView.adapter as EventAdapter

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
                    .error(null)
            )
            .into(imgView)
    }
}
