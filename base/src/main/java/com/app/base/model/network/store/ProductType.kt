package com.app.base.model.network.store

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductType(
    var id: String,
    var name: String
) : Parcelable