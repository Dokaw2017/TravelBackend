package com.example.route

import com.example.Utils.ApiMessages
import com.example.data.request.CreateEventRequest
import com.example.data.response.ApiResponse
import com.example.service.EventService
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.createEvent(
    eventService: EventService,
) {
    val gson by inject<Gson>()

    authenticate {

        post("/api/event/create") {

            val request = call.receiveOrNull<CreateEventRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }


            if (request.title.isBlank() || request.place.isBlank()) {
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
    eventService: EventService,
){

authenticate {
    get("/api/event/get") {

        val posts = eventService.getEventsByUser(call.userId)
        call.respond(
            HttpStatusCode.OK,
            posts
        )
    }
}
}

fun Routing.getAllEvents(
    eventService: EventService
){

        get("/api/event/all") {
            val posts = eventService.getAllEvents()
            call.respond(
                HttpStatusCode.OK,
                posts
            )
        }

}

fun Routing.getEvents(eventService: EventService){
    authenticate {
        get("/api/events/all") {
            val events = eventService.getEvents(call.userId)
            call.respond(
                HttpStatusCode.OK,
                events
            )
        }
    }
}