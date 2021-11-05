package com.example.smartmoney.ui.settings

import androidx.lifecycle.ViewModel
import com.example.data.repository.RepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.sql.Struct
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: RepositoryImpl) : ViewModel() {
    private var currentUser: FirebaseUser? = repository.getCurrentUser()

    val email = currentUser?.email

    var newPassword = ""

    var userName = ""

    private val _isNewPasswordValid = MutableStateFlow(false)
    val isNewPasswordValid = _isNewPasswordValid.asStateFlow()

    fun isNewPasswordValid(password: String) {
        _isNewPasswordValid.value = (password.isNotBlank() && password.length > 5)

        if (_isNewPasswordValid.value) { newPassword = password }
    }

    fun arePasswordsTheSame(secondPassword: String) : Boolean {
        return newPassword == secondPassword
    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
    }

    fun removeRememberUser() {
        repository.setRememberUser(false)
    }

    fun updatePassword(password: String) {
        repository.updatePassword(password)
        repository.setRememberUser(false)
        logOut()
    }

    fun changeUserName() {
        repository.changeUserName(userName)
    }
}