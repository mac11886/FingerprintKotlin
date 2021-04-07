package com.example.kotlinv10.model

data class DataUser(
    val branch: Branch,
    val branch_id: Int,
    val company_id: Int,
    val fingerprint: Fingerprint,
    val fingerprint_id: Int,
    val id: Int,
    val name: String
)