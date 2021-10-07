package com.example.smartmoney.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.SingleTransaction
import com.example.smartmoney.databinding.FragmentHistoryListItemBinding

class HistoryRecyclerAdapter : ListAdapter<SingleTransaction, HistoryRecyclerAdapter.mViewHolder>(COMPARATOR){
    inner class mViewHolder(private val binding: FragmentHistoryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: SingleTransaction) {
            binding.total.text = transaction.total.toString()
            binding.date.text = transaction.date
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<SingleTransaction>() {
            override fun areItemsTheSame(oldItem: SingleTransaction, newItem: SingleTransaction) =
                oldItem.date == newItem.date

            override fun areContentsTheSame(oldItem: SingleTransaction, newItem: SingleTransaction) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        return mViewHolder(
            FragmentHistoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}