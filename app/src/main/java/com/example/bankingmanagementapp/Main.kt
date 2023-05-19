package com.example.bankingmanagementapp


import com.example.bankingmanagementapp.util.*

import com.example.bankingmanagementapp.model.*
import com.example.bankingmanagementapp.service.*
import java.util.*
import kotlin.system.exitProcess



fun main() {
    println("Welcome to the banking app!")
    showMainMenu()
}

fun showMainMenu() {
    println("Please choose an option:")
    println("1. Login")
    println("2. Register")
    println("3. Quit")

    when (readLine()) {
        "1" -> login()
        "2" -> register()
        "3" -> exitProcess(0)
        else -> {
            println("Invalid input. Please try again.")
            showMainMenu()
        }
    }
}


fun login() {
    println("Please enter your email:")
    val emailInput = readLine()
    println("Please enter your password:")
    val passwordInput = readLine()
    val user = users.find { it.email == emailInput && it.password == passwordInput }
    if (user != null) {
        if (user.isAdmin) {
            adminHome(user)
        } else {
            userHome(user)
        }
    } else {
        println("Invalid email or password. Please try again.")
        showMainMenu()
    }
}

fun register() {
    println("Please enter your first name:")
    val firstNameInput = readLine()!!
    println("Please enter your last name:")
    val lastNameInput = readLine()!!
    var emailInput: String?
    do {
        println("Please enter your email:")
        emailInput = readLine()
    } while (!isValidEmail(emailInput))

    // Validate password input
    var passwordInput: String?
    do {
        println("Please enter a password (must be at least 8 characters long, include an uppercase letter, and a special symbol):")
        passwordInput = readLine()
    } while (!isValidPassword(passwordInput))

    // Validate mobile number input
    var mobileInput: String?
    do {
        println("Please enter your mobile number (must be 10 digits):")
        mobileInput = readLine()
    } while (!isValidMobile(mobileInput))
    println("Are you an admin? (y/n):")
    val isAdminInput = readLine()!!.lowercase(Locale.getDefault()) == "y"
    val user = User(firstName = firstNameInput, lastName = lastNameInput, email = emailInput!!, phone = mobileInput!!, password = passwordInput!!, isAdmin = isAdminInput)
    users.add(user)
    saveUsers()
    println("Registration successful.")
    showMainMenu()
}

fun adminHome(user: User) {
    println("Hello, ${user.firstName} ${user.lastName}!")
    println("1. View User Management Menu")
    println("2. View Account Management Menu")
    println("3. View Transaction Management Menu")
    println("4. View Loan Management Menu")
    println("5. View Fixed Deposit Menu")
    println("6. Logout")
    println("7. Exit")



    when (readLine()) {
        "1" -> adminUserManagementMenu(user)
        "2" -> adminAccountManagementMenu(user)
        "3" -> adminTransactionManagementMenu(user)
        "4" -> adminLoanManagementMenu(user)
        "5" -> adminFixedDepositMenu(user)
        "6" -> logout()
        "7" -> exitProcess(0)
        else -> {
            println("Invalid input. Please try again.")
            adminHome(user)
        }
    }
}



fun userHome(user: User) {
    println("Hello, ${user.firstName} ${user.lastName}!")
    println("1. View accounts")
    println("2. Open new account")
    println("3. Deposit")
    println("4. Withdraw")
    println("5. Transfer Money")
    println("6. View your Transactions")
    println("7. Apply for loan")
    println("8. View your loans")
    println("9. Do a Fixed Deposit")
    println("10. View your Fixed Deposits")
    println("11.Update profile")
    println("12. Logout")

    when (readLine()) {
        "1" -> viewAccounts(user)
        "2" -> openAccount(user)
        "3" -> deposit(user)
        "4" -> withdraw(user)
        "5" -> transfer(user)
        "6" -> viewCurrentUserTransactions(user)
        "7" -> applyForLoan(user)
        "8" -> viewCurrentUserLoans(user)
        "9" -> fixedDeposit(user)
        "10" -> viewCurrentUserFixedDeposits(user)
        "11" -> updateProfile(user)
        "12" -> logout()
        else -> {
            println("Invalid input. Please try again.")
            userHome(user)
        }
    }
}



fun adminUserManagementMenu(user: User) {
    println("1. View all users")
    println("2. View Details of a User")
    println("3. Create account for user")
    println("4. Update Details of a User")
    println("5. Back")

    when (readLine()) {
        "1" -> viewAllUsers(user)
        "2" -> viewUser(user)
        "3" -> createAccountForUser(user)
        "4" -> updateUserDetails(user)
        "5" -> adminHome(user)
        else -> {
            println("Invalid input. Please try again.")
            adminUserManagementMenu(user)
        }
    }

}
fun adminAccountManagementMenu(user: User){
    println("1. View All accounts")
    println("2. View Details of an Account")
    println("3. View Accounts of a User")
    println("4. Back")

    when (readLine()) {
        "1" -> viewAllAccounts(user)
        "2" -> viewAccountById(user)
        "3" -> viewUserAccount(user)
        "4" -> adminHome(user)
        else -> {
            println("Invalid input. Please try again.")
            adminAccountManagementMenu(user)
        }
    }

}




fun adminTransactionManagementMenu(user: User){
    println("Transaction Management Menu")
    println("1. View All transactions")
    println("2. View Details of a transaction")
    println("3. View transactions of a user")
    println("4. Back")

    when (readLine()) {
        "1" -> viewAllTransactions(user)
        "2" -> viewTransaction(user)
        "3" -> viewUserTransaction(user)
        "4" -> adminHome(user)
        else -> {
            println("Invalid input. Please try again.")
            adminTransactionManagementMenu(user)
        }
    }
}



fun adminLoanManagementMenu(user: User){
    println("Loan Management Menu")
    println("1. View All Loan")
    println("2. View Details of a loan")
    println("3. Approve loans")
    println("4. Back")

    when (readLine()) {
        "1" -> viewAllLoans(user)
        "2" -> viewLoan(user)
        "3" -> approveLoan(user)
        "4" -> adminHome(user)
        else -> {
            println("Invalid input. Please try again.")
            adminLoanManagementMenu(user)
        }
    }
}

fun adminFixedDepositMenu(user: User){
    println("Fixed Deposit Management Menu")
    println("1. View All Fixed Deposits")
    println("2. View Details of a particular Fixed Deposit")
    println("3. View Fixed Deposits of a particular User")
    println("4. Back")

    when (readLine()) {
        "1" -> viewAllFixedDeposit(user)
        "2" -> viewFixedDeposit(user)
        "3" -> viewUserFixedDeposits(user)
        "4" -> adminHome(user)
        else -> {
            println("Invalid input. Please try again.")
            adminFixedDepositMenu(user)
        }
    }
}




fun logout() {
    println("Goodbye!")
    showMainMenu()
}



