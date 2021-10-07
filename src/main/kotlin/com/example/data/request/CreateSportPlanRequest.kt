package com.example.data.request

data class CreateSportPlanRequest(
    val name:String,
    val place:String,
    val date:String,
    val startingTime:String,
    val finishingTime:String,
    val participants:Int
)
