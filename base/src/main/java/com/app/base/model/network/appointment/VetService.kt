package com.app.base.model.network.appointment

import android.os.Parcelable
import com.app.base.model.network.store.StoreNetwork
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VetService(

    var id: String,
    var service: Service,
    var vet: StoreNetwork

) : Parcelable
