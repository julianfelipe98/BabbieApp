package com.app.event.repository

import com.app.base.model.network.events.Event
import com.app.event.network.EventService
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val service: EventService
) {

    suspend fun getEvents(): List<Event> {
        return service.getEvents()
    }
}