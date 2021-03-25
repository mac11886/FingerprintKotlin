package com.example.kotlinv10.Service

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("signup")
    fun signUp(@Query("name") name: String,
               @Query("username") username: String,
               @Query("password") password: String,
               @Query("company_name") company: String): Call<String>
}