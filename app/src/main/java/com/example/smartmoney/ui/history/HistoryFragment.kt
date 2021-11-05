package com.example.smartmoney.ui.history

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.model.SingleTransaction
import com.example.smartmoney.R
import com.example.smartmoney.common.base.BaseFragment
import com.example.smartmoney.databinding.FragmentHistoryBinding
import com.example.smartmoney.ui.history.adapter.HistoryRecyclerAdapter
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HistoryFragment : BaseFragment(R.layout.fragment_history), HistoryRecyclerAdapter.OpenDetails,
    SwipeToDelete.DeleteFromFirebase {
    private val binding by viewBinding(FragmentHistoryBinding::bind)
    override val viewModel by viewModels<HistoryViewModel>()

    private var transactionsCount = 0

    private var recyclerViewObserver: Job? = null

    private var adapter: HistoryRecyclerAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuVisibility(true)

        binding.header.findViewById<TextView>(R.id.headerPageName)?.text =
            viewModel.getCurrentUser()?.email

        setupRecyclerView()
        setupObservers()
    }


    private fun setupObservers() {
        recyclerViewObserver = lifecycleScope.launch {
            viewModel.setListener.start()
            viewModel.observeTransactions.start()
            viewModel.transactions.collect {
                if (it != null) {
                    adapter!!.submitList(it)
                    viewModel.getTotalAmount(it)
                    transactionsCount = it.size
                    binding.loader.visibility = View.GONE
                } else {
                    Log.d("111", "EMPTY LIST")
                }
            }
        }
        lifecycleScope.launch {
            viewModel.totalAmount.collect {
                binding.totalAmount.text = "$ $it"
            }
        }

        lifecycleScope.launch {
            viewModel.observerListenerDetaching.start()
            viewModel.isListenerDetached.collect {
                if (it) {
                    binding.loader.visibility = View.GONE
                    binding.tryAgainBtn.visibility = View.VISIBLE
                }
            }
        }

        recyclerViewObserver?.start()
    }

    private fun setupRecyclerView() {
        adapter = HistoryRecyclerAdapter(this)
        recyclerView = binding.historyList
        ItemTouchHelper(SwipeToDelete(requireContext(), this)).attachToRecyclerView(recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView?.adapter = adapter
    }

    override fun openDetailsListener(transaction: SingleTransaction) {
        val action = HistoryFragmentDirections.actionHistoryFragmentToManagerFragment(transaction)
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        recyclerViewObserver?.cancel()
    }

    override fun delete(position: Int) {
        val transactionToRemoveId = viewModel.transactions.value?.get(position)?.id
        viewModel.deleteFromFirebase(transactionToRemoveId!!)

        transactionsCount--
        if (transactionsCount == 0) {
            viewModel.clearList()
            binding.totalAmount.text = "$ 0"
        }
    }
}