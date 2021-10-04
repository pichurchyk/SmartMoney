package com.example.smartmoney.ui.history

import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {
    private var currentUser: FirebaseUser = repository.getCurrentUser()

    fun getCurrentUser() : FirebaseUser {
        return currentUser
    }
}