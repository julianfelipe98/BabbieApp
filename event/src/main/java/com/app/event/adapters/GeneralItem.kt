package com.app.event.adapters

import com.app.base.model.network.events.Event

data class GeneralItem(val event: Event) : ListItem() {

    override fun getType(): Int {
        return TYPE_GENERAL
    }
}