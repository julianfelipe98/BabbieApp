package com.app.event.network

import com.app.base.model.network.events.Event
import retrofit2.http.GET

interface EventService {

    @GET("/messaging/")
    suspend fun getEvents():
            List<Event>
}
