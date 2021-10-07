package com.example.data.repository.sportplan

import com.example.data.models.SportPlan
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.insertOne

class SportPlanRepositoryImpl(
    db:CoroutineDatabase
):SportPlanRepository {
    private val sportPlans = db.getCollection<SportPlan>()

    override suspend fun createSportPlan(sportPlan: SportPlan): Boolean {
         return sportPlans.insertOne(sportPlan).wasAcknowledged()
    }

    override suspend fun getSPlanById(spId: String): SportPlan? {
        return sportPlans.findOneById(spId)
    }

    override suspend fun getSPlans(userId:String): List<SportPlan> {
       return sportPlans.find(SportPlan::userId `in` userId ).toList()
    }

    override suspend fun deleteSPlan(spId: String) {
        sportPlans.deleteOneById(spId)
    }
}