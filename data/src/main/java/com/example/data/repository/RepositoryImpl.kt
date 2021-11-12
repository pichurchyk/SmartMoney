package com.example.data.repository

import android.util.Log
import com.example.data.source.SharedPreferencesSource
import com.example.domain.model.SingleTransaction
import com.example.domain.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferencesSource,
    private val firebaseDatabase: FirebaseDatabase,
) : Repository {
    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun setRememberUser(remember: Boolean) = sharedPref.setRememberUser(remember)

    fun getRememberUser(): Boolean = sharedPref.getRememberUser()

    fun pushTransactionToFirebase(transaction: SingleTransaction) {
        firebaseDatabase.reference.child(getCurrentUser()!!.uid).child("transactions")
            .child(transaction.id!!)
            .setValue(transaction)
    }

    val _userLimit = MutableStateFlow(0.0)
    val userLimit = _userLimit.asStateFlow()

    var userTotalAmount = 0.0

    val _transactions = MutableStateFlow<List<SingleTransaction>?>(null)
    val transactionFlow = _transactions.asStateFlow()

    val _isListenerDetached = MutableStateFlow(false)
    val isListenerDetached = _isListenerDetached.asStateFlow()

    private val firebaseListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (FirebaseAuth.getInstance().currentUser != null) {
                val transactionsList = mutableListOf<SingleTransaction>()

                snapshot.children.map { snapshotList ->
                    snapshotList.children.map { snapshotItem ->
                        snapshotItem.children

                            .filter {
                                it.key != "total" && it.key != "limit" &&
                                        it.getValue(SingleTransaction::class.java)?.userEmail.equals(getCurrentUser()?.email) }
                            .sortedBy { it.getValue(SingleTransaction::class.java)!!.date }
                            .forEach {
                                val transaction = it.getValue(SingleTransaction::class.java)
                                transactionsList.add(transaction!!)
                            }

                        userTotalAmount = transactionsList.sumOf { transaction ->
                            val amountToBigDecimal = transaction.total!!.toBigDecimal()
                            if (transaction.type.equals("Expense")) {
                                return@sumOf -(amountToBigDecimal)
                            }
                            return@sumOf amountToBigDecimal
                        }.toDouble()

                        setTotalToFirebase(userTotalAmount)

                        _transactions.value = transactionsList
                        _isListenerDetached.value = false
                    }
                }

                snapshot.children.map { snapshotList ->
                    snapshotList.children.map { snapshotItem ->
                        snapshotItem.children
                            .filter { it.key == "limit" }
                            .map { Log.d("111", "ALALALALA")
                                _userLimit.value = it.value.toString().toDouble() }
                    }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d("111", "Canceled")
        }
    }

    val setListener = GlobalScope.launch(Dispatchers.IO) {
        Log.d("111", "Listener started")
        firebaseDatabase.reference.addValueEventListener(firebaseListener)
        delay(10000)
        if (_transactions.value == null) {
            firebaseDatabase.reference.removeEventListener(firebaseListener)
            Log.d("111", "Listener removed")
            _isListenerDetached.value = true
        }
    }

    fun deleteFromFirebase(childId: String) {
        firebaseDatabase.reference.child(getCurrentUser()!!.uid).child("transactions")
            .child(childId)
            .removeValue()
    }

    fun setLimitToFirebase(limit: Double) {
        firebaseDatabase.reference.child(getCurrentUser()!!.uid).child("transactions")
            .child("limit")
            .setValue(limit)
    }

    fun setTotalToFirebase(total: Double) {
        firebaseDatabase.reference.child(getCurrentUser()!!.uid).child("transactions")
            .child("total")
            .setValue(total)
    }

    fun updatePassword(password: String): String {
        var status = ""
        getCurrentUser()?.updatePassword(password)
            ?.addOnCompleteListener { status = "Success" }
            ?.addOnFailureListener { status = "${it.message}" }
        return status
    }

    fun changeUserName(name: String) {
        val profileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        getCurrentUser()?.updateProfile(profileChangeRequest)?.addOnCompleteListener {
            Log.d(
                "111",
                "User name has changed successfully"
            )
        }
    }
}