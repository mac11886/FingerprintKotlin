package com.example.kotlinv10.model

class DataHolder {
    companion object {
        @JvmStatic val instance = DataHolder()
    }

    private lateinit var allData: AllData
    fun getData(): AllData {
        return this.allData
    }

    fun setData(allData: AllData) {
        this.allData = allData
    }
}