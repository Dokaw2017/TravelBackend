package com.example.data.repository.plan

import com.example.data.models.Plan
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.CoroutineDatabase

class PlanRepositoryImpl(
    db:CoroutineDatabase
):PlanRepository {

    private val sportPlans = db.getCollection<Plan>()

    override suspend fun createEvent(sportPlan: Plan): Boolean {
         return sportPlans.insertOne(sportPlan).wasAcknowledged()
    }

    override suspend fun getEventById(eventId: String): Plan? {
        return sportPlans.findOneById(eventId)
    }

    override suspend fun getEventsByUser(userId:String): List<Plan> {
       return sportPlans.find(Plan::userId `in` userId ).toList()
    }

    override suspend fun getAllEvents(): List<Plan> {
        return sportPlans.find().toList()
    }

    override suspend fun getEvents(): List<Plan> {
        return sportPlans.find().toList()
    }

    override suspend fun deleteEvent(eventId: String) {
        sportPlans.deleteOneById(eventId)
    }
}