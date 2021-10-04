package com.example.smartmoney.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.smartmoney.R
import com.example.smartmoney.ui.auth.signIn.SignInFragment
import com.example.smartmoney.ui.auth.signUp.SignUpFragment
import com.google.android.material.internal.ContextUtils.getActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
}