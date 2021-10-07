package com.example.smartmoney.ui.manager

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.example.smartmoney.databinding.FragmentManagerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagerFragment : BaseFragment(R.layout.fragment_manager) {
    override val viewModel by viewModels<ManagerViewModel>()
    private val binding by viewBinding(FragmentManagerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener {
            clearFocus()
        }

        binding.submitBtn.setOnClickListener {
            collectTransactionData()
        }
    }

    private fun collectTransactionData() {
        val date = binding.calendar.date
        val checkedTypeButton = binding.radioGroup.checkedRadioButtonId
        val type = binding.root.findViewById<RadioButton>(checkedTypeButton).text.toString()
        val amount = binding.amount.text.toString().toDouble()
        val description = binding.description.text.toString()

        viewModel.collectTransactionData(date ,date, type, amount, description)
    }
}