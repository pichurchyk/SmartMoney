package com.example.smartmoney.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RepositoryImpl
import com.example.domain.model.SingleTransaction
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {
    private var currentUser: FirebaseUser = repository.getCurrentUser()

    private val _totalAmount = MutableStateFlow(0.toBigDecimal())
    val totalAmount : StateFlow<BigDecimal> = _totalAmount.asStateFlow()

    @ExperimentalCoroutinesApi
    val getData = repository.observeTransactions()

    fun getCurrentUser(): FirebaseUser {
        return currentUser
    }

    fun getTotalAmount(allTransactions: List<SingleTransaction>) {
        _totalAmount.value = allTransactions.sumOf { transaction ->
            val amountToBigDecimal = transaction.total!!.toBigDecimal()
            if (transaction.type.equals("Expense")) {
                return@sumOf -(amountToBigDecimal)
            }
            return@sumOf amountToBigDecimal
        }
    }
}