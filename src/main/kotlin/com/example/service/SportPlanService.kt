package com.example.service

import com.example.data.models.SportPlan
import com.example.data.repository.sportplan.SportPlanRepository
import com.example.data.request.CreateSportPlanRequest

class SportPlanService(
    private val sportPlanRepository: SportPlanRepository
) {

    suspend fun createSportPlan(request:CreateSportPlanRequest,userId:String):Boolean{
        return sportPlanRepository.createSportPlan(
            SportPlan(
                title = request.name,
                place = request.place,
                userId = userId,
                date = request.date,
                startTime = request.startingTime,
                finishTime = request.finishingTime,
                participants = request.participants
            )
        )
    }

    suspend fun getSPlansById(spId:String):SportPlan? = sportPlanRepository.getSPlanById(spId)

    suspend fun getSPlans(userId: String):List<SportPlan>{
        return sportPlanRepository.getSPlans(userId)
    }

    suspend fun deleteSPlan(spId: String) = sportPlanRepository.deleteSPlan(spId)
}