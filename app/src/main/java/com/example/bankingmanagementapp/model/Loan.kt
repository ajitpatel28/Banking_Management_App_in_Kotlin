package com.example.bankingmanagementapp.model

import java.util.*

data class Loan(
    val loanId: UUID = UUID.randomUUID(),
    val accountNumber: UUID,
    val amount: Double,
    val rate: Double,
    val duration: Int,
    val startDate: Date = Date(),
    var status: String = "pending",
    val userId: UUID
)