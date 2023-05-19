package com.example.bankingmanagementapp.model

import java.util.*


data class Transaction(
    val transactionId: UUID = UUID.randomUUID(),
    val accountNumber: UUID,
    val transactionType: String,
    val amount: Double,
    val date: Date = Date(),
    val userId : UUID
)
