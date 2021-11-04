package com.example.data.repository.sportplan

import com.example.data.models.SportPlan

interface SportPlanRepository {

    suspend fun createSportPlan(sportPlan:SportPlan):Boolean

    suspend fun getSPlanById(userId:String):SportPlan?

    suspend fun getSPlans(userId: String):List<SportPlan>

    suspend fun getSPlans():List<SportPlan>

    suspend fun deleteSPlan(spId:String)
}