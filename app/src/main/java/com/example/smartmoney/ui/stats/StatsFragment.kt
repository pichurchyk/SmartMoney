package com.example.smartmoney.ui.stats

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.example.smartmoney.common.util.AmountInputFilter
import com.example.smartmoney.databinding.FragmentStatsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatsFragment : BaseFragment(R.layout.fragment_stats) {
    override val viewModel by viewModels<StatsViewModel>()
    private val binding by viewBinding(FragmentStatsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userLimit.value.let {
            if (it != 0.0) {
                binding.limitInput.setText(it.toString())
            }
        }

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
            lifecycleScope.launch {
                viewModel.userLimit.collect {
                    val total = viewModel.getUserTotalAmount()
                    val spent = viewModel.userSpent.value

                    binding.limitPercentOfTotal.text = viewModel.convertLimitToPercents()
                    binding.chart.setData(total.toFloat(), it.toFloat(), spent.toFloat())
                }
            }

            lifecycleScope.launch {
                viewModel.userSpent.collect {
                    val total = viewModel.getUserTotalAmount()
                    val limit = viewModel.userLimit.value

                    binding.chart.setData(total.toFloat(), limit.toFloat(), it.toFloat())
                }
            }

    }

    private fun setupListeners() {
        binding.root.setOnClickListener {
            clearFocus()
        }

        binding.limitInput.filters = arrayOf(AmountInputFilter())

        binding.limitInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

                try {
                    val text = s.toString()
                    if (text.isNotEmpty() && viewModel.getUserTotalAmount() > text.toDouble()) {
                        viewModel._userLimit.value = text.toDouble()
                    }
                } catch (e : IllegalStateException) {
                    Log.d("111", e.message.toString())
                }
            }
        })
    }
}