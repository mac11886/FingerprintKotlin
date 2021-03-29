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

    var firstRun: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }

    private val DEVICE_TOKEN = Pair("device_token", null)
    var device_token: String?
        get() = preferences.getString(DEVICE_TOKEN.first, DEVICE_TOKEN.second)
        set(value) = preferences.edit {
            it.putString(DEVICE_TOKEN.first,value)
        }

    var phone: String?
        get() = preferences.getString("phone","")
        set(value) = preferences.edit {
            it.putString("phone",value)
        }

    private val ACCESS_TOKEN = Pair("access_token", "")
    var access_token: String?
        get() = preferences.getString(ACCESS_TOKEN.first, ACCESS_TOKEN.second)
        set(value) = preferences.edit {
            it.putString(ACCESS_TOKEN.first,value)
        }

    private val USER_ID = Pair("user_id", null)
    var user_id: String?
        get() = preferences.getString(USER_ID.first, USER_ID.second)
        set(value) = preferences.edit {
            it.putString(USER_ID.first,value)
        }

    private val YOUR_NAME = Pair("your_name", null)
    var your_name: String?
        get() = preferences.getString(YOUR_NAME.first, YOUR_NAME.second)
        set(value) = preferences.edit {
            it.putString(YOUR_NAME.first,value)
        }

    private val MEETING_ID = Pair("meeting_id", null)
    var meeting_id: String?
        get() = preferences.getString(MEETING_ID.first, MEETING_ID.second)
        set(value) = preferences.edit {
            it.putString(MEETING_ID.first,value)
        }

    private val SELECTED_PROFILE_IMAGE = Pair("image_profileCoach", null)
    var imageProfileSelectedCoach: String?
        get() = preferences.getString(SELECTED_PROFILE_IMAGE.first, SELECTED_PROFILE_IMAGE.second)
        set(value) = preferences.edit {
            it.putString(SELECTED_PROFILE_IMAGE.first,value)
        }

    private val CALLER_ID = Pair("caller_id", null)
    var caller_id: String?
        get() = preferences.getString(CALLER_ID.first, CALLER_ID.second)
        set(value) = preferences.edit {
            it.putString(CALLER_ID.first,value)
        }
}