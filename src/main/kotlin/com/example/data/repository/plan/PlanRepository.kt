package com.example.data.repository.plan

import com.example.data.models.Plan

interface PlanRepository {

    suspend fun createPlan(plan: Plan):Boolean

    suspend fun getPlan(postId:String):Plan?

    suspend fun getPlans(userId: List<String>):List<Plan>

    suspend fun deletePlan(planId:String)
}