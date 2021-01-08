package com.app.store.utils

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.database.Store
import com.app.base.model.network.store.ProductType
import com.app.base.utils.NetworkApiStatus
import com.app.store.R

import com.app.store.profile.adapters.ListItem
import com.app.store.profile.adapters.OrderAdapter
import com.app.store.store.adapters.FilterAdapter
import com.app.store.store.adapters.ListItemStore
import com.app.store.store.adapters.ProductAdapter
import com.app.store.store.adapters.StoreAdapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("listDataOrder")
fun bindRecyclerViewOrder(recyclerView: RecyclerView, data: List<ListItem>?) {
    val adapter = recyclerView.adapter as OrderAdapter
    if (data != null) {
        adapter.setData(data)
    }
}

@BindingAdapter("listDataStore")
fun bindRecyclerViewStore(recyclerView: RecyclerView, data: List<Store>?) {
    val adapter = recyclerView.adapter as StoreAdapter
    if (data != null) {
        adapter.setData(data)
    }
}

@BindingAdapter("listDataProduts")
fun bindRecyclerViewItem(recyclerView: RecyclerView, data: List<ListItemStore>?) {
    val adapter = recyclerView.adapter as ProductAdapter
    if (data != null) {
        adapter.setData(data)
    }
}

@BindingAdapter("listDataFilter")
fun bindRecyclerViewFilter(recyclerView: RecyclerView, data: List<ProductType>?) {
    val adapter = recyclerView.adapter as FilterAdapter
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
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
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


