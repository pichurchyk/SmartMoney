package com.example.smartmoney.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.example.smartmoney.databinding.FragmentHistoryBinding
import com.example.smartmoney.ui.history.adapter.HistoryRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment(R.layout.fragment_history) {
    private val binding by viewBinding(FragmentHistoryBinding::bind)
    override val viewModel by viewModels<HistoryViewModel>()

    private var adapter: HistoryRecyclerAdapter ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        adapter = HistoryRecyclerAdapter()
        val recyclerView = binding.historyList
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.adapter = adapter
    }
}