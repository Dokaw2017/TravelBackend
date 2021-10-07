package com.example.plugins

import com.example.route.createSPlan
import com.example.route.createUser
import com.example.route.getSPlans
import com.example.route.loginUser
import com.example.service.SportPlanService
import com.example.service.UserService
import io.ktor.routing.*
import io.ktor.http.content.*
import io.ktor.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    
    val userService:UserService by inject()
    val sportPlanService:SportPlanService by inject()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
    routing {
        //User routes
        createUser(userService)
        loginUser(
            userService,
            jwtIssuer,
            jwtAudience,
            jwtSecret
        )

        //Plan Routes
        createSPlan(sportPlanService)
        getSPlans(sportPlanService)

        static{
            resources("static")
        }
    }
}
