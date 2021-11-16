package com.example.smartmoney.ui.stats

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

    private val _userSpent = MutableStateFlow(repository.userSpent.value)
    val userSpent = _userSpent.asStateFlow()

    fun getUserTotalAmount(): Double {
        return repository.userTotalAmount
    }

    private fun listenToLimitChange() {
        viewModelScope.launch {
            userLimit.collect {
                repository.setLimitToFirebase(it)
            }
        }
    }

    fun convertLimitToPercents(): String {
        val total = getUserTotalAmount()
        val percentsOfTotal = userLimit.value / total * 100
        val roundPercent = String.format("%.1f", percentsOfTotal)

        return if (total != 0.0) "$roundPercent % of $total" else "Not enough data"
    }

    init {
        listenToLimitChange()
    }
}