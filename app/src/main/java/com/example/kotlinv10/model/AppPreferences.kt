package com.example.kotlinv10.model

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "FingerprintApp"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val IS_FIRST_RUN_PREF = Pair("is_runfirstapp", false)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    private val BRANCH_ID = Pair("branch_id", "")
    var branch_id: String?
        get() = preferences.getString(BRANCH_ID.first, BRANCH_ID.second)
        set(value) = preferences.edit {
            it.putString(BRANCH_ID.first, value)
        }

    private val COMPANY_ID = Pair("company_id", "")
    var company_id:  String?
        get() = preferences.getString(COMPANY_ID.first, COMPANY_ID.second)
        set(value) = preferences.edit{
            it.putString(COMPANY_ID.first, value)
        }

}