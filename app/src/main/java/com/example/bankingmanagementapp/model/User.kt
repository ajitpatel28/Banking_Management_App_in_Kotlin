package com.example.bankingmanagementapp.model

import java.util.*

data class User(
    val userId: UUID = UUID.randomUUID(),
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String,
    var password: String,    // new field for password
    var isAdmin: Boolean = false,
    var accounts: MutableList<Account> = mutableListOf()
)
