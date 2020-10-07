package com.judge.lms.interfaces

import com.judge.lms.constants.AppConstants
import com.judge.lms.model.LoginRequest
import com.judge.lms.model.LoginResponse
import com.judge.lms.model.RegisterRequest
import com.judge.lms.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Rohit on 18-09-2020.
 */
interface ApiCall {

    @POST(AppConstants.login)
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST(AppConstants.login)
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

}