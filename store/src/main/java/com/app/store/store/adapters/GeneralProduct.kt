package com.app.store.store.adapters

import com.app.base.model.network.store.ItemNetwork

data class GeneralProduct(val product: ItemNetwork) : ListItemStore() {

    override fun getType(): Int {
        return TYPE_GENERAL
    }
}