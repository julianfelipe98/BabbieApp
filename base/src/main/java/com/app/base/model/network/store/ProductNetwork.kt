package com.app.base.model.network.store

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductNetwork(
    var id: String = "",
    var name: String = "",
    var product_type: ProductType,
    var animal_category: ProductType,
    var description: String = "",
    var picture_url: String = ""

) : Parcelable