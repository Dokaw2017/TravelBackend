package com.example.data.repository.plan

import com.example.data.models.Plan

interface PlanRepository {

    suspend fun createEvent(sportPlan:Plan):Boolean

    suspend fun getEventById(eventId:String):Plan?

    suspend fun getEventsByUser(userId: String):List<Plan>

    suspend fun getAllEvents():List<Plan>

    suspend fun getEvents():List<Plan>

    suspend fun deleteEvent(eventId:String)

}