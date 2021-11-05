package com.example.smartmoney.ui.manager

import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import com.example.domain.model.SingleTransaction
import com.example.smartmoney.common.DateTimeParser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagerViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {

    private val dateTimeParser = DateTimeParser()

    val currentDate = dateTimeParser.getDateInMills()

    var transaction: SingleTransaction = SingleTransaction()

    var checkedTypeId: Int? = null

    fun pushTransactionToFirebase() {
        val userEmail = repository.getCurrentUser()?.email
        transaction.userEmail = userEmail

        if (transaction.id == null) {
            transaction.id = currentDate.toString()
        }
        if (transaction.type == null) {
            transaction.type = "Income"
        }

        repository.pushTransactionToFirebase(transaction)
    }

    fun isAllFilled(): Boolean {
        return (transaction.date != null) && (transaction.total != null)
    }

    fun isDateValid(): Boolean {
        return dateTimeParser.isDateValid()
    }

    fun validateDate(dayOfMonth: Int, month: Int, year: Int) {
        dateTimeParser.setTimeInMills(dayOfMonth, month, year)
        transaction.date = dateTimeParser.getDateInMills()
    }

    fun clearFields() {
        transaction.id = dateTimeParser.getCurrentDate().toString()
        transaction.date = currentDate
        transaction.total = null
        transaction.description = null
    }

    fun getDateAsList(): List<Int> {
        return dateTimeParser.getDateAsList(transaction.date!!)
    }

    init {
        if (transaction.date == null) {
            transaction.date = currentDate
        }
    }
}