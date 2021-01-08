package com.app.record.utils


import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.base.model.network.records.Record
import com.app.record.records.adapters.RecordAdapter
import com.app.record.records.adapters.RecordGeneralAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Record>?) {
    val adapter = recyclerView.adapter as RecordAdapter
    if (data != null) {
        adapter.setData(data)
    }

}

@BindingAdapter("listDataGeneral")
fun bindRecyclerViewGeneral(recyclerView: RecyclerView, data: List<Record>?) {
    val adapter = recyclerView.adapter as RecordGeneralAdapter
    if (data != null) {
        adapter.setData(data)
    }

}

