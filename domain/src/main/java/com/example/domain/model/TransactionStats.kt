package com.example.domain.model

data class TransactionStats(
    val firstDay : Int,
    val lastDay : Int,
    val transactions : List<SingleTransaction>
)