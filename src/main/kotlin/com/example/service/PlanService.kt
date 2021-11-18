package com.example.service

import com.example.data.models.Plan
import com.example.data.repository.plan.PlanRepository
import com.example.data.repository.user.UserRepository
import com.example.data.request.CreatePlanRequest
import com.example.data.response.PlanResponseItem

class PlanService(
    private val planRepository: PlanRepository,
    private val userRepository: UserRepository
) {

    suspend fun createEvent(request:CreatePlanRequest, userId:String):Boolean{
        return planRepository.createEvent(
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

    suspend fun getEventById(eventId:String):Plan? = planRepository.getEventById(eventId)

    suspend fun getEventsByUser(userId: String):List<Plan>{
        return planRepository.getEventsByUser(userId)
    }

    suspend fun deleteEvent(eventId: String) = planRepository.deleteEvent(eventId)

    suspend fun getAllEvents():List<Plan>{
        return planRepository.getAllEvents()
    }

    suspend fun getEvents(userid:String):List<PlanResponseItem?>{
        val user = userRepository.getUserById(userid)
        val events = planRepository.getAllEvents()

        return events.map { event ->
            user?.let {
                PlanResponseItem(
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