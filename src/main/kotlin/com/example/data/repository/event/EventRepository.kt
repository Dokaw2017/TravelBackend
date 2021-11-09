package com.example.data.repository.event

import com.example.data.models.Event

interface EventRepository {

    suspend fun createEvent(sportPlan:Event):Boolean

    suspend fun getEventById(eventId:String):Event?

    suspend fun getEventsByUser(userId: String):List<Event>

    suspend fun getAllEvents():List<Event>

    suspend fun getEvents():List<Event>

    suspend fun deleteEvent(eventId:String)

}