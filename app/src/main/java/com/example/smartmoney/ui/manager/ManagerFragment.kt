package com.example.smartmoney.ui.manager

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.example.smartmoney.common.util.AmountInputFilter
import com.example.smartmoney.common.util.textWatcher.SimpleTextWatcher
import com.example.smartmoney.databinding.FragmentManagerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagerFragment : BaseFragment(R.layout.fragment_manager) {
    override val viewModel by viewModels<ManagerViewModel>()
    private val binding by viewBinding(FragmentManagerBinding::bind)

    private val args by navArgs<ManagerFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addEventListeners()

        if (args.transaction != null) {
            viewModel.transaction = args.transaction!!

            val buttonToCheck =
                binding.root.findViewWithTag<RadioButton>("${args.transaction?.type}")
            binding.radioGroup.check(buttonToCheck.id)
            viewModel.checkedTypeId = buttonToCheck.id

            setDateFromTransactionInfo()
        }

        viewModel.checkedTypeId?.let { binding.radioGroup.check(it) }
        viewModel.transaction.total?.let { binding.amount.setText(it) }
        viewModel.transaction.description?.let { binding.description.setText(it) }

       viewModel.checkedTypeId = binding.radioGroup.checkedRadioButtonId

        binding.amount.filters = arrayOf(AmountInputFilter())
    }

    private fun addEventListeners() {
        addMenuListener()

        binding.amount.addTextChangedListener(SimpleTextWatcher {
            if (!it.isNullOrEmpty()) {
                viewModel.transaction.total = it.toString()
            }
        })

        binding.description.addTextChangedListener(SimpleTextWatcher {
            if (!it.isNullOrEmpty()) {
                viewModel.transaction.description = it.toString()
            }
        })

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            viewModel.checkedTypeId = checkedId

            val checkedType = group.findViewById<RadioButton>(checkedId)

            viewModel.transaction.type = checkedType.text.toString()
        }

        binding.calendar.setOnDateChangedListener { _, year, month, dayOfMonth ->
            viewModel.validateDate(dayOfMonth, month, year)
        }

        binding.root.setOnClickListener {
            clearFocus()
        }

        binding.newTransactionBtn?.setOnClickListener {
            viewModel.clearFields()
            clearFields()
        }

        binding.submitBtn.setOnClickListener {
            if (viewModel.isAllFilled()) {
                if (!viewModel.isDateValid()) {
                    snackBar(requireView(), "Check your date please")
                } else {
                    viewModel.pushTransactionToFirebase()
                    snackBar(requireView(), "New transaction added!")
                }
            } else {
                snackBar(requireView(), "You need to fill all fields")
            }
        }
    }

    private fun setDateFromTransactionInfo() {
        val date = viewModel.getDateAsList()
        val year = date[0]
        val month = date[1]
        val day = date[2]

        binding.calendar.updateDate(year, month, day)
    }

    private fun clearFields() {
        binding.amount.text = null
        binding.description.text = null
        setDateFromTransactionInfo()
        binding.radioGroup.check(R.id.income)
    }
}