package com.example.kotlinv10.model

data class DataUser(
    val company: Company,
    val company_id: Int,
    val department: Any,
    val department_id: Int,
    val fingerprint: Any,
    val fingerprint_id: Int,
    val id: Int,
    val image_path: String,
    val job: Any,
    val job_id: Int,
    val name: String
)