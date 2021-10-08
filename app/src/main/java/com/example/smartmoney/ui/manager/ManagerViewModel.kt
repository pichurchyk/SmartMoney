package com.example.smartmoney.ui.manager

import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import com.example.domain.model.SingleTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagerViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {

    var date : Long ?= null
    var type : String ?= null
    var amount : Double ?= null
    var description : String ?= null

    var checkedTypeId : Int ?= null

    var transaction: SingleTransaction? = null

    fun pushTransactionToFirebase() {
        val userEmail = repository.getCurrentUser().email
        transaction = SingleTransaction(date.toString(), userEmail!!, date.toString(), type!!, amount!!, description)
        repository.pushTransactionToFirebase(transaction!!)
    }
}