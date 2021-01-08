package com.app.store.profile.adapters

import com.app.base.model.network.store.OrderNetwork

data class GeneralItem(val order: OrderNetwork) : ListItem() {

    override fun getType(): Int {
        return TYPE_GENERAL
    }


}