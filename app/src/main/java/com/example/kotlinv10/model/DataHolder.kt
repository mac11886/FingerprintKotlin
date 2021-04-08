package com.example.kotlinv10.model

object DataHolder {
    var allData: AllData? = null
        get() = field
        set(value) {
            field = value

        }

    lateinit var name : String



    var allDataUser : List<DataUser>? = null
        get () =field
        set(value) {
            field = value
        }
    var user : DataUser? = null
        get() = field
        set(value) {
            field = value
        }
}