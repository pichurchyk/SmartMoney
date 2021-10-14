package com.example.smartmoney.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.SingleTransaction
import com.example.smartmoney.R
import com.example.smartmoney.common.DateTimeParser
import com.example.smartmoney.databinding.FragmentHistoryListItemBinding

class HistoryRecyclerAdapter : ListAdapter<SingleTransaction, HistoryRecyclerAdapter.mViewHolder>(COMPARATOR){
    inner class mViewHolder(private val binding: FragmentHistoryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: SingleTransaction) {
            val dateTimeParser = DateTimeParser()
            val date = dateTimeParser.getDateAsString(transaction.date!!)

            binding.total.text =  "$${transaction.total}"
            binding.date.text = date
            binding.status.text = transaction.type

            binding.description.text = transaction.description ?: "No Description"

            if (transaction.type.equals("Expense")) {
                binding.root.setBackgroundResource(R.drawable.bg_history_list_item_dark)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<SingleTransaction>() {
            override fun areItemsTheSame(oldItem: SingleTransaction, newItem: SingleTransaction) =
                oldItem.date == newItem.date

            override fun areContentsTheSame(oldItem: SingleTransaction, newItem: SingleTransaction) =
                oldItem.equals(newItem)
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