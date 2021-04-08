package com.example.kotlinv10.Service

import com.example.kotlinv10.model.AllData
import com.example.kotlinv10.model.Branch
import com.example.kotlinv10.model.DataAdmin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("signup")
    fun signUp(
        @Query("name") name: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("company_name") company: String
    ): Call<String>

    @GET("getAllData/{company_id}")
    fun getAllData(@Path("company_id") company: Int): Call<AllData>


    @POST("login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<DataAdmin>

    @POST("saveBranch")
    fun saveBranch(
        @Query("company_id") company_id: Int?,
        @Query("branch_name") branch_name: String?,
        @Query("name") name: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<String>

    @POST("editBranch")
    fun editBranch(
        @Query("branch_name") branch_name: String?,
        @Query("name") name: String,
        @Query("password") password: String,
        @Query("branch_id") branch_id: Int?,
        @Query("admin_id") admin_id: Int?
    ): Call<String>

    @POST("setLateTime")
    fun setLateTime(
        @Query("company_id") company_id: Int?,
        @Query("time") time: String
    ): Call<String>


    @POST("saveCompany")
    fun saveCompany(@Query("company_name") company: String): Call<String>

    @POST("saveUser")
    fun saveUser(
        @Query("name") name: String,
        @Query("company_id") company_id: Int?,
        @Query("fingerprint_id") fingerprint_id: Int?,
        @Query("branch_id") branch_id: Int?
    ): Call<String>


    @POST("attendance")
    fun saveAttendance(
        @Query("user_id") user_id: Int?,
        @Query("branch_id") branch_id: Int?,
        @Query("status") status: String
    )


    @POST("saveProfile")
    fun saveProfile(
        @Query("user_name") user_name: String,
        @Query("company_id") company_id: Int?,
        @Query("branch_id") branch_id: Int?,
        @Query("first_fingerprint") first_fingerprint: String,
        @Query("second_fingerprint") second_fingerprint: String

    )

    @POST("editProfile")
    fun editProfile(
        @Query("id") id: Int?,
        @Query("user_name") username: String,
        @Query("first_fingerprint") first_fingerprint: String,
        @Query("second_fingerprint") second_fingerprint: String
    )


}