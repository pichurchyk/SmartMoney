package com.example.smartmoney.ui.manager

import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import com.example.domain.model.SingleTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagerViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {

    private var transaction: SingleTransaction? = null

    fun collectTransactionData(
        id: Long,
        date: Long,
        type: String,
        amount: Double,
        description: String?
    ) {
        val userEmail = repository.getCurrentUser().email

        transaction =
            SingleTransaction(id.toString(), userEmail!!, date.toString(), type, amount, description)

        pushTransactionToFirebase()
    }

    private fun pushTransactionToFirebase() {
        repository.pushTransactionToFirebase(transaction!!)
    }
}