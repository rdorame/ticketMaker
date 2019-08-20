package com.example.ticketmaker.Class

import android.content.Context
import com.example.ticketmaker.Common.SharedPrefs

data class User(var uid: String = "", var firstName: String = "", var lastName: String = "", var password: String = ""){
    lateinit var prefs : SharedPrefs

    fun getLoggedUser(context : Context) : User{
        prefs = SharedPrefs(context)
        return User(
            prefs.getString(context, "U_ID", "")!!,
            prefs.getString(context, "U_NAME", "")!!,
            prefs.getString(context, "U_LAST", "")!!,
            prefs.getString(context, "U_PASS", "")!!
        )
    }

    fun saveUser(context : Context){
        prefs = SharedPrefs(context)
        prefs.save(context, "U_ID", uid)
        prefs.save(context, "U_NAME", firstName)
        prefs.save(context, "U_LAST", lastName)
        prefs.save(context, "U_PASS", password)
    }

    fun cleanUser(context : Context){
        prefs = SharedPrefs(context)
        prefs.save(context, "U_ID", "")
        prefs.save(context, "U_NAME", "")
        prefs.save(context, "U_LAST", "")
        prefs.save(context, "U_PASS", "")
    }
}