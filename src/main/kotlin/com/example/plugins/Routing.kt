package com.example.plugins

import com.example.route.*
import com.example.service.*
import io.ktor.routing.*
import io.ktor.http.content.*
import io.ktor.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    
    val userService:UserService by inject()
    val sportPlanService:SportPlanService by inject()
    val followService:FollowService by inject()
    val postService: PostService by inject()
    val likeService: LikeService by inject()
    val commentService: CommentService by inject()

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()
    routing {
        //User routes
        authenticate()
        general()
        createUser(userService)
        loginUser(
            userService,
            jwtIssuer,
            jwtAudience,
            jwtSecret
        )

        //Following Routes
        followUser(followService)
        unFollowUser(followService)

        //Post Routes
        cratePostRoute(postService)
        getPostsForFollows(postService)
        deletePost(postService,likeService)

        //Plan Routes
        createSPlan(sportPlanService)
        getSPlans(sportPlanService)

        //Like Route
        likePost(likeService)
        unlikePost(likeService)

        //Post Route
        createComment(commentService)
        deleteComment(commentService,likeService)
        getCommentsForPost(commentService)

        static{
            resources("static")
        }
    }
}
