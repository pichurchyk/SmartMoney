package com.example.smartmoney.ui.auth

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {

    private val _isEmailValid = MutableStateFlow(false)
    val isEmailValid: StateFlow<Boolean> = _isEmailValid.asStateFlow()

    private val _isPasswordValid = MutableStateFlow(false)
    val isPasswordValid: StateFlow<Boolean> = _isPasswordValid.asStateFlow()

    fun isEmailValid(text: String) {
        _isEmailValid.value = !TextUtils.isEmpty(text) && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }
    fun isPasswordValid(text: String) {
        _isPasswordValid.value = (text.isNotBlank() && text.length > 5)
    }

    fun isReadyToSubmit() : Boolean {
        return isEmailValid.value && isPasswordValid.value
    }

    fun setRememberUser(remember: Boolean) {
        repository.setRememberUser(remember)
    }
}