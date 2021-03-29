package com.example.kotlinv10.model

import com.example.kotlinv10.Service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiObject {
    val apiObject = Retrofit.Builder()
        .baseUrl("https://ksta.co/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)

}