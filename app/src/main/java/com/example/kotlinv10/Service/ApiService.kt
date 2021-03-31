package com.example.kotlinv10.Service

import com.example.kotlinv10.model.AllData
import com.example.kotlinv10.model.Branch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("signup")
    fun signUp(@Query("name") name: String,
               @Query("username") username: String,
               @Query("password") password: String,
               @Query("company_name") company: String): Call<String>

    @GET("getAllData/{company_id}")
    fun getAllData(@Path("company_id") company: Int): Call<AllData>

    @POST("login")
    fun login(@Query("username") username: String,
              @Query("password") password: String,): Call<Branch>

    @POST("saveBranch")
    fun saveBranch(@Query("company_id") company_id: Int?,
                   @Query("branch_name") branch_name: String?,
                   @Query("name") name: String,
                   @Query("username") username: String,
                   @Query("password") password: String): Call<String>
}