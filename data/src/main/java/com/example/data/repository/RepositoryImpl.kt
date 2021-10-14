package com.example.data.repository

import android.util.Log
import com.example.data.source.SharedPreferencesSource
import com.example.domain.model.SingleTransaction
import com.example.domain.repository.Repository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferencesSource,
    private val firebaseDatabase: FirebaseDatabase,
) : Repository {

    private val transactionsList = mutableListOf<SingleTransaction>()

    fun getCurrentUser(): FirebaseUser {
        return Firebase.auth.currentUser!!
    }

    fun setRememberUser(remember: Boolean) = sharedPref.setRememberUser(remember)

    fun getRememberUser(): Boolean = sharedPref.getRememberUser()

    fun pushTransactionToFirebase(transaction: SingleTransaction) {
        firebaseDatabase.reference.child("transactions").child(transaction.id!!)
            .setValue(transaction)
    }

    @ExperimentalCoroutinesApi
    fun observeTransactions(): Flow<List<SingleTransaction>> = callbackFlow {
        firebaseDatabase.reference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    result.children.map { snapShot ->
                        snapShot.getValue(SingleTransaction::class.java)
                        val filteredSnapshot = snapShot.children
                            .filter {
                            it.getValue(SingleTransaction::class.java)?.userEmail.equals(
                                getCurrentUser().email) }
                            .sortedBy { it.getValue(SingleTransaction::class.java)!!.date }

                        filteredSnapshot.forEach {
                            val transaction = it.getValue(SingleTransaction::class.java)
                            transactionsList.add(transaction!!)
                        }
                    }
                }
                trySend(transactionsList)
            } else {
                Log.d("111", "Exception: ${task.exception.toString()}")
            }
        }
        awaitClose { cancel() }
    }
}