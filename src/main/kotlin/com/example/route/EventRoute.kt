package com.example.route

import com.example.Utils.ApiMessages
import com.example.data.request.CreatePlanRequest
import com.example.data.response.ApiResponse
import com.example.service.PlanService
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.createEvent(
    eventService: PlanService,
) {
    val gson by inject<Gson>()

    authenticate {

        post("/api/plan/create") {

            val request = call.receiveOrNull<CreatePlanRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }


            if (request.title.isBlank() || request.from.isBlank()) {
                call.respond(ApiResponse<Unit>(false, ApiMessages.FIELD_BLANK))
                return@post
            }

            eventService.createEvent(request, call.userId)

            call.respond(
                ApiResponse<Unit>(true, "successfully saved")
            )
        }
    }
}

fun Routing.getEventsByUser(
    eventService: PlanService,
){

authenticate {
    get("/api/plan/get") {

        val posts = eventService.getEventsByUser(call.userId)
        call.respond(
            HttpStatusCode.OK,
            posts
        )
    }
}
}

fun Routing.getAllEvents(
    eventService: PlanService
){

        get("/api/plan/all") {
            val posts = eventService.getAllEvents()
            call.respond(
                HttpStatusCode.OK,
                posts
            )
        }

}

/*
fun Routing.getEvents(eventService: PlanService){
    authenticate {
        get("/api/plans/all") {
            val events = eventService.getEvents(call.userId)
            call.respond(
                HttpStatusCode.OK,
                events
            )
        }
    }
}*/
