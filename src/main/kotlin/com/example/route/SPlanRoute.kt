package com.example.route

import com.example.Utils.ApiMessages
import com.example.data.request.CreateSportPlanRequest
import com.example.data.request.RegistrationRequest
import com.example.data.response.ApiResponse
import com.example.service.SportPlanService
import com.example.service.UserService
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.createSPlan(
    sportPlanService: SportPlanService,
) {
    val gson by inject<Gson>()

    authenticate {

        post("/api/splan/create") {

            val request = call.receiveOrNull<CreateSportPlanRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }


            if (request.name.isBlank() || request.place.isBlank()) {
                call.respond(ApiResponse<Unit>(false, ApiMessages.FIELD_BLANK))
                return@post
            }

            sportPlanService.createSportPlan(request, call.userId)

            call.respond(
                ApiResponse<Unit>(true, "successfully saved")
            )
        }
    }
}

fun Routing.getSPlans(
    sportPlanService: SportPlanService,
){

authenticate {
    get("/api/post/get") {

        val posts = sportPlanService.getSPlans(call.userId)
        call.respond(
            HttpStatusCode.OK,
            posts
        )
    }
}
}