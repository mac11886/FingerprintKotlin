package com.example.kotlinv10.model

data class DataAdmin(
    val branch_id: Int,
    val company: Company,
    val branch: Branch,
    val company_id: Int,
    val id: Int,
    val name: String,
    val password: String,
    val role: String,
    val username: String
)