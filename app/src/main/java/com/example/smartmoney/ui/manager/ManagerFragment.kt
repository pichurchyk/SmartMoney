package com.example.smartmoney.ui.manager

import androidx.fragment.app.viewModels
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagerFragment : BaseFragment(R.layout.fragment_manager) {
    override val viewModel by viewModels<ManagerViewModel>()


}