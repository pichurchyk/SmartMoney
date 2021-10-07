package com.example.domain.model

data class SingleTransaction(
    val id: String,
    val userEmail: String,
    val date: String,
    val type: String,
    val total: Double,
    val description: String?
)