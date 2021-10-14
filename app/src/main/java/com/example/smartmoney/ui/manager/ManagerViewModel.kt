package com.example.smartmoney.ui.manager

import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import com.example.domain.model.SingleTransaction
import com.example.smartmoney.common.DateTimeParser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class ManagerViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {

    private val dateTimeParser = DateTimeParser()

    private val transactionId = DateTime.now().millis.toString()
    var date: Long? = null
    var type: String? = null
    var amount: String? = null
    var description: String? = ""

    var checkedTypeId: Int? = null

    var transaction: SingleTransaction? = null

    fun pushTransactionToFirebase() {
        val userEmail = repository.getCurrentUser().email

        transaction =
            SingleTransaction(transactionId, userEmail!!, date, type!!, amount!!, description)
        repository.pushTransactionToFirebase(transaction!!)
    }

    fun isAllFilled(): Boolean {
        return (date != null) && (amount != null) && (checkedTypeId != null)
    }

     fun isDateValid() : Boolean {
        return dateTimeParser.isDateValid()
    }

    fun validateDate(dayOfMonth: Int, month: Int, year: Int) {
        dateTimeParser.setTimeInMills(dayOfMonth, month, year)
        date = dateTimeParser.getDateInMills()
    }
}