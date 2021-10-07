package com.example.data.repository

import com.example.data.source.SharedPreferencesSource
import com.example.domain.repository.Repository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferencesSource
) : Repository {

    private var currentUser : FirebaseUser ?= null

    fun setCurrentUser(user: FirebaseUser) {
        currentUser = user
    }
    fun getCurrentUser() : FirebaseUser {
        return currentUser!!
    }

    fun setRememberUser(remember: Boolean) = sharedPref.setRememberUser(remember)

    fun getRememberUser() : Boolean = sharedPref.getRememberUser()
}