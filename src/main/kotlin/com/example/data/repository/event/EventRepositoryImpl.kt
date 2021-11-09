package com.example.data.repository.event

import com.example.data.models.Event
import com.example.data.models.User
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.CoroutineDatabase

class EventRepositoryImpl(
    db:CoroutineDatabase
):EventRepository {
    private val sportPlans = db.getCollection<Event>()
    private val users = db.getCollection<User>()

    override suspend fun createEvent(sportPlan: Event): Boolean {
         return sportPlans.insertOne(sportPlan).wasAcknowledged()
    }

    override suspend fun getEventById(eventId: String): Event? {
        return sportPlans.findOneById(eventId)
    }

    override suspend fun getEventsByUser(userId:String): List<Event> {
       return sportPlans.find(Event::userId `in` userId ).toList()
    }

    override suspend fun getAllEvents(): List<Event> {
        return sportPlans.find().toList()
    }

    override suspend fun getEvents(): List<Event> {
        return sportPlans.find().toList()
    }

    override suspend fun deleteEvent(eventId: String) {
        sportPlans.deleteOneById(eventId)
    }
}