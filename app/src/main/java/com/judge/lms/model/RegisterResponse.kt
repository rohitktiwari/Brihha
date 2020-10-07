package com.judge.lms.model

/**
 * Created by Rohit on 18-09-2020.
 */

data class RegisterResponse (
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val username: String,
    val role: Int
)
