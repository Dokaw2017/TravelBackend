package com.example.data.repository.plan

import com.example.data.models.Plan
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.CoroutineDatabase

class PlanRepositoryImpl(
    db:CoroutineDatabase
):PlanRepository {

    private val plans = db.getCollection<Plan>()

    override suspend fun createEvent(sportPlan: Plan): Boolean {
         return plans.insertOne(sportPlan).wasAcknowledged()
    }

    override suspend fun getEventById(eventId: String): Plan? {
        return plans.findOneById(eventId)
    }

    override suspend fun getEventsByUser(userId:String): List<Plan> {
       return plans.find(Plan::userId `in` userId ).toList()
    }

    override suspend fun getAllEvents(): List<Plan> {
        return plans.find().toList()
    }

    override suspend fun getEvents(): List<Plan> {
        return plans.find().toList()
    }

    override suspend fun deleteEvent(eventId: String) {
        plans.deleteOneById(eventId)
    }
}