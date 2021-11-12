package com.example.smartmoney.ui.stats

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(val repository: RepositoryImpl) : ViewModel() {

    val _userLimit = MutableStateFlow(repository.userLimit.value)
    val userLimit = _userLimit.asStateFlow()

     fun getUserTotalAmount() : Double{
         Log.d("111", repository.userTotalAmount.toString())
        return repository.userTotalAmount
    }

    private fun listenToLimitChange() {
        viewModelScope.launch {
            userLimit.collect {
                repository.setLimitToFirebase(it)
            }
        }
    }

    init {
        listenToLimitChange()
    }
}