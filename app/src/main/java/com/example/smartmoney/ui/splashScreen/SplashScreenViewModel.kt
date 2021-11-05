package com.example.smartmoney.ui.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RepositoryImpl
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(val repository: RepositoryImpl) : ViewModel(){
    fun getRememberUser() : Boolean {
        return repository.getRememberUser()
    }

    fun getCurrentUser() : FirebaseUser? {
        return repository.getCurrentUser()
    }
}