package com.example.data.repository.plan

import com.example.data.models.Plan
import org.litote.kmongo.`in`
import org.litote.kmongo.coroutine.CoroutineDatabase

class PlanRepositoryImpl(
    db:CoroutineDatabase
):PlanRepository {

    private val plans = db.getCollection<Plan>()
    override suspend fun createPlan(plan: Plan):Boolean {
       return plans.insertOne(plan).wasAcknowledged()
    }

    override suspend fun getPlan(postId: String): Plan? {
        return plans.findOneById(postId)
    }

    override suspend fun getPlans(userId: List<String>): List<Plan> {
        return plans.find(Plan::userId `in` userId).toList()
    }


    override suspend fun deletePlan(planId: String) {
       plans.deleteOneById(planId)
    }
}