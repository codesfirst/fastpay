package com.codesfirst.fastpay.data

import com.pixplicity.easyprefs.library.Prefs

object PrefSingleton {

    fun getPrefs(key: String): String = Prefs.getString(key, "")


    fun getPrefsIntValue(key: String): Int = Prefs.getInt(key, -1)


    fun getPrefsBoolValue(key: String): Boolean = Prefs.getBoolean(key, false)

    fun setPrefs(key: String, value: String)
    {
        Prefs.putString(key, value)
    }

    fun setPrefs(key: String, value: Boolean?)
    {
        Prefs.putBoolean(key, value ?: false)
    }

    fun setPrefs(key: String, value: Int)
    {
        Prefs.putInt(key, value)
    }
    
}