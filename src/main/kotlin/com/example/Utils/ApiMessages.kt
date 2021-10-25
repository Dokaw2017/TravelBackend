package com.example.Utils

import com.example.Utils.Constants.MAX_COMMENT_LENGTH

object ApiMessages {
    const val USER_ALREADY_EXISTS = "A user with this email already exists"
    const val FIELD_BLANK = "All filled are required"
    const val INVALID_CREDENTIALS = "That is not correct,Please try again"
    const val COMMENT_TOO_LONG = "The comment length must not exceed ${MAX_COMMENT_LENGTH} characters"
}