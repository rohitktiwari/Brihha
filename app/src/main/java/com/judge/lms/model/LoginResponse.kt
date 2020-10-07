package com.judge.lms.model

/**
 * Created by Rohit on 19-09-2020.
 */
data class LoginResponse(
        val auth_token: String,
        val detail: String
)