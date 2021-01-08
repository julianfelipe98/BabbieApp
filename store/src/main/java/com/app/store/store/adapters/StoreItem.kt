package com.app.store.store.adapters

data class StoreItem(var name: String) : ListItemStore() {

    override fun getType(): Int {
        return TYPE_STORE
    }
}