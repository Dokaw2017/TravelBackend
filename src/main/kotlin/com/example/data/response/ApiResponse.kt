package com.example.data.response

data class ApiResponse<T>(
    val success:Boolean,
    val message:String? = null,
    val data: T? = null
)
