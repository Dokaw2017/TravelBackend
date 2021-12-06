# BuddyRadar Api

It is an api build for the buddyRader application. 
It is a REST-api which enables to create,read,update and 
delete Events,Posts and Chats in the application. It Connects 
to MongoDB database and uses KMongo framework for database operations
The Api is build using the MVC and Clean Architecture concepts in 
order to have a clean separation of layers.

## Tech-Stacks

* Kotlin
* Ktor Framework
* Dependency Injection -> Koin
* Authorization -> JWT
* Database -> MongoDB
* ORM Framework -> KMongo
* Build Tool -> Gradle
* Server -> Netty

## Architecture

## Features

### Events
* POST /api/plan/create
* GET /api/plan/get
* GET /api/plan/filter
* GET /api/plan/all
### Posts
* POST /api/post/create
* POST /api/post/get
* DELETE /api/post/delete
* GET /api/post/all
* GET /api/post/detail
### Chats
* GET /api/chat/messages
* GET /api/chats
* WEBSOCKET /api/chat/websocket
### Authentication
* POST /api/user/create
* POST /api/user/login
* GET /api/user/authenticate
### Comment
* POST /api/comment/create
* GET /api/comment/get
* DELETE /api/comment/delete
### Follow
* POST /api/following/follow
* DELETE /api/following/unfollow
### Likes
* POST /api/like
* DELETE /api/unlike
* GET /api/like/parent
### User
* GET /api/user/posts
* GET /api/user/profile
* put /api/user/updatee
* GET /api/user/myprofile



  
  
  
  
 
 

