package com.example.smartmoney.ui.splashScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {
    override val viewModel by viewModels<SplashScreenViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            if (viewModel.getRememberUser() && viewModel.getCurrentUser() != null) {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_splashScreenFragment_to_historyFragment)
            } else {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_splashScreenFragment_to_signInFragment)
            }
        }
    }
}