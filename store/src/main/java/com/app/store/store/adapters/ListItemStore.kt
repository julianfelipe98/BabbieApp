package com.app.store.store.adapters

abstract class ListItemStore {
    companion object {
        const val TYPE_STORE = 0;
        const val TYPE_GENERAL = 1;
    }

    abstract fun getType(): Int
}