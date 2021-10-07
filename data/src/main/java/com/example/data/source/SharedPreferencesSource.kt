package com.example.data.source

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.res.Resources
import com.example.data.R
import javax.inject.Inject

class SharedPreferencesSource @Inject constructor(app: Application) {
    val res: Resources = Resources.getSystem()

    private val sharedPref = app.getSharedPreferences("SettingsSharedPref", MODE_PRIVATE)

    fun setRememberUser(remember: Boolean) {
        sharedPref.edit()
            .putBoolean("RememberUser", remember)
            .apply()
    }
    
    fun getRememberUser() : Boolean {
        return sharedPref.getBoolean("RememberUser", false)
    }
}