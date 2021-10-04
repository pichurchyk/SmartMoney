package com.example.data.source

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.res.Resources
import com.example.data.R
import javax.inject.Inject

class SharedPreferencesSource @Inject constructor(app: Application) {
    val res = Resources.getSystem()

//    private val sharedPref = app.getSharedPreferences(Resources.getSystem().getString(R.string.shared_pref_name), MODE_PRIVATE)

//    fun setRememberUser(remember: Boolean) {
//        sharedPref.edit()
//            .putBoolean(res.getString(R.string.shared_pref_remember_user), remember)
//            .apply()
//    }
}