package com.example.service

import com.example.data.models.Plan
import com.example.data.repository.plan.PlanRepository
import com.example.data.repository.user.UserRepository
import com.example.data.request.CreateEventRequest
import com.example.data.response.EventResponseItem

class PlanService(
    private val sportPlanRepository: PlanRepository,
    private val userRepository: UserRepository
) {

    suspend fun createEvent(request:CreateEventRequest,userId:String):Boolean{
        return sportPlanRepository.createEvent(
            Plan(
                title = request.title,
                place = request.place,
                userId = userId,
                date = request.date,
                time = request.time,
                category = request.category,
                subCategory = request.subCategory,
                isBuddy = false
            )
        )
    }

    suspend fun getEventById(eventId:String):Plan? = sportPlanRepository.getEventById(eventId)

    suspend fun getEventsByUser(userId: String):List<Plan>{
        return sportPlanRepository.getEventsByUser(userId)
    }

    suspend fun deleteEvent(eventId: String) = sportPlanRepository.deleteEvent(eventId)

    suspend fun getAllEvents():List<Plan>{
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
                    date = event.date,
                    isBuddy = event.isBuddy
                )
            }
        }

    }
}