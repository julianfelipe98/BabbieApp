package com.app.base.model.network.store

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemNetwork(
    var id: String,
    var price: Double,
    var product: ProductNetwork,
    var vetstore: StoreNetwork
) : Parcelable