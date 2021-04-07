package com.example.kotlinv10.model

object DataHolder {
    var allData: AllData? = null
        get() = field
        set(value) {
            field = value
        }
}