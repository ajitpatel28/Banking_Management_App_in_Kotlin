package com.example.bankingmanagementapp.model

import java.util.*

data class Account(
    val accountNumber: UUID = UUID.randomUUID(),
    val type : String,
    var balance: Double = 0.0,
    val userId: UUID
)
