package com.example.smartmoney.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.example.smartmoney.databinding.FragmentHistoryBinding
import com.example.smartmoney.ui.history.adapter.HistoryRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HistoryFragment : BaseFragment(R.layout.fragment_history) {
    private val binding by viewBinding(FragmentHistoryBinding::bind)
    override val viewModel by viewModels<HistoryViewModel>()

    private var adapter: HistoryRecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuVisibility(true)

        binding.userName.text = viewModel.getCurrentUser().email

        setupRecyclerView()
        setupObservers()
    }


    private fun setupObservers() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.setListener.start()
            viewModel.observeTransactions.start()
            viewModel.transactions.collect {
                adapter!!.submitList(it)
            }
        }
        lifecycleScope.launch {
            viewModel.totalAmount.collect {
                binding.totalAmount.text = "$ $it"
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = HistoryRecyclerAdapter()
        val recyclerView = binding.historyList
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.adapter = adapter
    }
}