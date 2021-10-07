package com.example.service

import com.example.data.models.Event
import com.example.data.models.Plan
import com.example.data.repository.plan.PlanRepository
import com.example.data.request.CreatePostRequest

class PlanService(
    private val planRepository: PlanRepository
) {

   /* suspend fun createPlan(request:CreatePostRequest,userId:String):Boolean{
        return planRepository.createPlan(
            Plan(
                userId = userId,
                participants = request.participants,
                location = request.location,

            )
        )

    }*/
}