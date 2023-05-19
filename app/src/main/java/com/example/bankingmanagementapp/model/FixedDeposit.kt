package com.example.bankingmanagementapp.model

import java.util.*

data class FixedDeposit (
    val fixedDepositId : UUID = UUID.randomUUID(),
    val accountNumber: UUID,
    val interestRate : Double,
    val depositAmount : Double,
    val tenure : Int,
    val userId : UUID

)
