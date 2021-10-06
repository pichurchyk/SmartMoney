package com.example.smartmoney.ui.manager

import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagerViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {
}