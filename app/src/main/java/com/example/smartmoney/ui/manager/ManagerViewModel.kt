package com.example.smartmoney.ui.manager

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import com.example.domain.model.SingleTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ManagerViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {

    private val transactionId = DateTime.now().millis.toString()
    var date: String? = null
    var type: String? = null
    var amount: Double? = null
    var description: String? = ""

    var checkedTypeId: Int? = null

    var transaction: SingleTransaction? = null

    fun pushTransactionToFirebase() {
        val userEmail = repository.getCurrentUser().email

        transaction = SingleTransaction(transactionId, userEmail!!, date.toString(), type!!, amount!!, description)
        repository.pushTransactionToFirebase(transaction!!)
    }

    fun isAllFilled() : Boolean {
        return (date != null) && (amount != null) && (checkedTypeId != null)
    }

    fun validateDate(dayOfMonth: Int, month: Int, year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)

        val userDate = DateTime(calendar.timeInMillis)
        val defaultDate = DateTime.now()

        date = when (userDate.millis > defaultDate.millis) {
            true -> {
                val defaultDay = defaultDate.dayOfMonth().asText
                val defaultMonth = defaultDate.monthOfYear().asShortText
                val defaultYear = defaultDate.year().asText
                "$defaultDay $defaultMonth $defaultYear"
            }
            false -> {
                val userDay = userDate.dayOfMonth().asText
                val userMonth = userDate.monthOfYear().asShortText
                val userYear = userDate.year().asText
                "$userDay $userMonth $userYear"
            }
        }
    }
}