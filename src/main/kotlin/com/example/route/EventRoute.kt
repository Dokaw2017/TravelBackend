package com.example.route

import com.example.utils.ApiMessages
import com.example.utils.QueryParams
import com.example.utils.QueryParams.PARAM_SUB_CATEGORY_TYPE
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
) {

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

fun Route.filterPlan(
    eventService: PlanService
) {
    authenticate {
        get("/api/plan/filter") {
            val category = call.parameters[QueryParams.PARAM_CATEGORY_TYPE]
            val subCategory = call.parameters[PARAM_SUB_CATEGORY_TYPE ]
            println("CATEGORG: ${category}")
            val posts = eventService.filterPlan(category,subCategory)
            println("POST: ${posts}")
            call.respond(
                HttpStatusCode.OK,
                posts
            )
        }
    }
}

fun Routing.getAllEvents(
    eventService: PlanService
) {

    get("/api/plan/all") {
        val posts = eventService.getAllEvents()
        call.respond(
            HttpStatusCode.OK,
            posts
        )
    }

}

