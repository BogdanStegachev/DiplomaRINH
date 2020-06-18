package com.example.diploma.core.classes

import android.content.Context
import com.example.diploma.core.retrofitClasses.ProfileResponse

class AccountManager {
    companion object{
        lateinit var context:Context
        var User: ProfileResponse? = null
        var Token:String
        get() {
            return context.getSharedPreferences("token", Context.MODE_PRIVATE)
                .getString("accessToken","")!!
        }
        set(value) {
            context.getSharedPreferences("token", Context.MODE_PRIVATE)
                .edit().putString("accessToken", value).apply()
        }
    }
}