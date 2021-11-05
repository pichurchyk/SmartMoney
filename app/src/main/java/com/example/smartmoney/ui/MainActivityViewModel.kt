package com.example.smartmoney.ui

import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val repository: RepositoryImpl) : ViewModel() {
    fun getRememberUser() : Boolean {
        return repository.getRememberUser()
    }
}