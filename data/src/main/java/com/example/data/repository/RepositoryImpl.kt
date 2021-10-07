package com.example.data.repository

import com.example.data.source.SharedPreferencesSource
import com.example.domain.model.SingleTransaction
import com.example.domain.repository.Repository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferencesSource,
    private val firebaseDatabase: FirebaseDatabase,
) : Repository {

    fun getCurrentUser(): FirebaseUser {
        return Firebase.auth.currentUser!!
    }

    fun setRememberUser(remember: Boolean) = sharedPref.setRememberUser(remember)

    fun getRememberUser(): Boolean = sharedPref.getRememberUser()

    fun pushTransactionToFirebase(transaction: SingleTransaction) {
        firebaseDatabase.reference.child("transaction").child(transaction.id).setValue(transaction)
    }
}