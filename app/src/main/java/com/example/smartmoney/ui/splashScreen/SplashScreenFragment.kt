package com.example.smartmoney.ui.splashScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen){
    override val viewModel by viewModels<SplashScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            delay(2000)
            if (viewModel.getRememberUser()) {
                navigate(R.id.action_splashScreenFragment_to_historyFragment)
            } else {
                navigate(R.id.action_splashScreenFragment_to_signInFragment)
            }
        }
    }
}