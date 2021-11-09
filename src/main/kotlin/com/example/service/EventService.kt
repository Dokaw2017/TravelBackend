package com.example.service

import com.example.data.models.Event
import com.example.data.repository.event.EventRepository
import com.example.data.repository.user.UserRepository
import com.example.data.request.CreateEventRequest
import com.example.data.response.EventResponseItem

class EventService(
    private val sportPlanRepository: EventRepository,
    private val userRepository: UserRepository
) {

    suspend fun createEvent(request:CreateEventRequest,userId:String):Boolean{
        return sportPlanRepository.createEvent(
            Event(
                title = request.title,
                place = request.place,
                userId = userId,
                date = request.date,
                time = request.time,
                category = request.category
            )
        )
    }

    suspend fun getEventById(eventId:String):Event? = sportPlanRepository.getEventById(eventId)

    suspend fun getEventsByUser(userId: String):List<Event>{
        return sportPlanRepository.getEventsByUser(userId)
    }

    suspend fun deleteEvent(eventId: String) = sportPlanRepository.deleteEvent(eventId)

    suspend fun getAllEvents():List<Event>{
        return sportPlanRepository.getAllEvents()
    }

    suspend fun getEvents(userid:String):List<EventResponseItem?>{
        val user = userRepository.getUserById(userid)
        val events = sportPlanRepository.getAllEvents()

        return events.map { event ->
            user?.let {
                EventResponseItem(
                    userId = event.userId,
                    username = it.username,
                    profileImageUrl = user.profileImageUrl,
                    place = event.place,
                    time = event.time,
                    date = event.date
                )
            }
        }

    }
}