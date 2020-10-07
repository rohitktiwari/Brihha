package com.judge.lms.model

/**
 * Created by Rohit on 21-09-2020.
 */

data class LoginRequest (
        val domain: String,
        val username: String,
        val password: String
)