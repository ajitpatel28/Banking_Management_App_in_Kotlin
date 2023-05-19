package com.example.bankingmanagementapp.util


import com.example.bankingmanagementapp.model.Loan
import com.example.bankingmanagementapp.model.Transaction
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import com.example.bankingmanagementapp.model.*

val gson: Gson = GsonBuilder().setPrettyPrinting().create()

val usersFile = File("C:\\Users\\158131\\AndroidStudioProjects\\BankingManagementApp\\app\\src\\main\\java\\com\\example\\bankingmanagementapp\\resources\\users.json")
var users: MutableList<User> = if (usersFile.exists()) {
    val type = object : TypeToken<MutableList<User>>() {}.type
    gson.fromJson(usersFile.readText(), type)
} else {
    mutableListOf()
}

val transactionsFile = File("C:\\Users\\158131\\AndroidStudioProjects\\BankingManagementApp\\app\\src\\main\\java\\com\\example\\bankingmanagementapp\\resources\\transactions.json")
var transactions: MutableList<Transaction> = if (transactionsFile.exists()) {
    val type = object : TypeToken<MutableList<Transaction>>() {}.type
    gson.fromJson(transactionsFile.readText(), type)
} else {
    mutableListOf()
}

val loansFile = File("C:\\Users\\158131\\AndroidStudioProjects\\BankingManagementApp\\app\\src\\main\\java\\com\\example\\bankingmanagementapp\\resources\\loans.json")
var loans: MutableList<Loan> = if (loansFile.exists()) {
    val type = object : TypeToken<MutableList<Loan>>() {}.type
    gson.fromJson(loansFile.readText(), type)
} else {
    mutableListOf()
}

val fixedDepositFile = File("C:\\Users\\158131\\AndroidStudioProjects\\BankingManagementApp\\app\\src\\main\\java\\com\\example\\bankingmanagementapp\\resources\\fixedDeposit.json")
var fixedDeposits: MutableList<FixedDeposit> = if (fixedDepositFile.exists()) {
    val type = object : TypeToken<MutableList<FixedDeposit>>() {}.type
    gson.fromJson(fixedDepositFile.readText(), type)
} else {
    mutableListOf()
}


fun saveUsers() {
    usersFile.writeText(gson.toJson(users))
}

fun saveTransactions() {
    val jsonString = gson.toJson(transactions)
    transactionsFile.writeText(jsonString)
}

fun saveLoans() {
    loansFile.writeText(gson.toJson(loans))
}

fun saveFixedDeposits() {
    fixedDepositFile.writeText(gson.toJson(fixedDeposits))
}