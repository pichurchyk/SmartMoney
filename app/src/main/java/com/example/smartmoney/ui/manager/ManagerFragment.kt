package com.example.smartmoney.ui.manager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.example.smartmoney.common.util.textWatcher.SimpleTextWatcher
import com.example.smartmoney.databinding.FragmentManagerBinding
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

@AndroidEntryPoint
class ManagerFragment : BaseFragment(R.layout.fragment_manager) {
    override val viewModel by viewModels<ManagerViewModel>()
    private val binding by viewBinding(FragmentManagerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addEventListeners()

        viewModel.amount?.let { binding.amount.setText(it.toString()) }
        viewModel.description?.let { binding.description.setText(it) }
        viewModel.checkedTypeId?.let { binding.radioGroup.check(it) }
    }

    private fun addEventListeners() {
        addMenuListener()

        binding.amount.addTextChangedListener(SimpleTextWatcher {
            if (!it.isNullOrEmpty()) {
                viewModel.amount = it.toString().toDouble()
            }
        })

        binding.description.addTextChangedListener(SimpleTextWatcher {
            if (!it.isNullOrEmpty()) {
                viewModel.description = it.toString()
            }
        })

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            viewModel.checkedTypeId = checkedId

            val checkedType = group.findViewById<RadioButton>(checkedId)

            viewModel.type = checkedType.text.toString()
        }

        binding.calendar.setOnDateChangedListener { _, year, month, dayOfMonth ->
            val formatter = DateTimeFormat.forPattern("dd/mm/yyyy")
            val date = "$dayOfMonth/${month+1}/$year"
            val jodaDate = formatter.parseDateTime(date)
            val output = DateTimeFormat.forPattern("dd.mm.yyyy")
            Log.d("111", output.print(jodaDate))
        }

        binding.root.setOnClickListener {
            clearFocus()
        }

        binding.submitBtn.setOnClickListener {
            viewModel.pushTransactionToFirebase()
        }
    }

    private fun getDateMill(): String {
        return DateTime.now().toString()
    }
}