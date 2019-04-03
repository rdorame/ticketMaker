package com.example.ticketmaker.Common

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(private val context: Context?)  {
    private val GENERALPREFS = "MyPrefs"

    private fun getPrefs(context: Context?): SharedPreferences {
        return context!!.getSharedPreferences(GENERALPREFS, Context.MODE_PRIVATE)
    }

    /*
    * Specific functions/ info
    * */
    fun getIP (): String? {
        return getPrefs(context!!).getString("IPADDRESS", "")
    }

    fun getToken (): String? {
        return getPrefs(context!!).getString("APITOKEN", "Y2Z3cHJk-103534-1551469047000-R.WILSON")
    }

    fun getDatabase (): String? {
        return getPrefs(context!!).getString("DATABASE", "")
    }

    fun getLang (): String? {
        return getPrefs(context!!).getString("LANG", "EN")
    }

    fun getURL() : String?{
        return "http://${getPrefs(context!!).getString("IPADDRESS", "")}/WebServicesCellFusion/"
    }

    fun resetCredentials(context : Context){
        save(context, "U_ID", "")
        save(context, "U_NAME", "")
        save(context, "U_LAST", "")
        save(context, "U_PASS", "")
        save(context,"SESSION", "Not Logged")
    }

    /**
     * GENERAL FUNCTIONS
     * */

    /**
     * Booleans
     * */
    //Save Booleans
    fun save(context: Context, key: String, value: Boolean) {
        getPrefs(context).edit().putBoolean(key, value).apply()
    }

    //Get Booleans
    fun getBoolean(context: Context, key: String): Boolean {
        return getPrefs(context).getBoolean(key, false)
    }

    //Get Booleans if not found return a predefined default value
    fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
        return getPrefs(context).getBoolean(key, defaultValue)
    }

    /**
     * Strings
     * */
    fun save(context: Context, key: String, value: String) {
        getPrefs(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String): String {
        return getPrefs(context).getString(key, "")
    }

    fun getString(context: Context, key: String, defaultValue: String): String? {
        return getPrefs(context).getString(key, defaultValue)
    }

    /**
     * Integers
     * */
    fun save(context: Context, key: String, value: Int) {
        getPrefs(context).edit().putInt(key, value).apply()
    }

    fun getInt(context: Context, key: String): Int {
        return getPrefs(context).getInt(key, 0)
    }

    fun getInt(context: Context, key: String, defaultValue: Int): Int {
        return getPrefs(context).getInt(key, defaultValue)
    }

    /**
     * Floats
     * */
    fun save(context: Context, key: String, value: Float) {
        getPrefs(context).edit().putFloat(key, value).apply()
    }

    fun getFloat(context: Context, key: String): Float {
        return getPrefs(context).getFloat(key, 0f)
    }

    fun getFloat(context: Context, key: String, defaultValue: Float): Float {
        return getPrefs(context).getFloat(key, defaultValue)
    }

    /**
     * Longs
     * */
    fun save(context: Context, key: String, value: Long) {
        getPrefs(context).edit().putLong(key, value).apply()
    }

    fun getLong(context: Context, key: String): Long {
        return getPrefs(context).getLong(key, 0)
    }

    fun getLong(context: Context, key: String, defaultValue: Long): Long {
        return getPrefs(context).getLong(key, defaultValue)
    }

    //StringSets
    fun save(context: Context, key: String, value: Set<String>) {
        getPrefs(context).edit().putStringSet(key, value).apply()
    }

    fun getStringSet(context: Context, key: String): Set<String>? {
        return getPrefs(context).getStringSet(key, null)
    }

    fun getStringSet(context: Context, key: String, defaultValue: Set<String>): Set<String>? {
        return getPrefs(context).getStringSet(key, defaultValue)
    }
}