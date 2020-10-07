package com.judge.lms.model

/**
 * Created by Rohit on 18-09-2020.
 */

data class RegisterRequest(
        val first_name: String,
        val last_name: String,
        val role: Int,
        val organization: String,
        val email: String,
        val alt_email: String,
        val phone_number: String,
        val gender: String,
        val username: String,
        val date_of_birth: String,
        val experience: Int,
        val skills: String,
        val education_level: Int,
        val password: String,
        val confirmPass: String
)

