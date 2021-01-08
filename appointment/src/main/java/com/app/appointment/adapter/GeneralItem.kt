package com.app.appointment.adapter

import com.app.base.model.network.appointment.Appointment

data class GeneralItem(val appointment: Appointment) : ListItem() {

    override fun getType(): Int {
        return TYPE_GENERAL
    }
}