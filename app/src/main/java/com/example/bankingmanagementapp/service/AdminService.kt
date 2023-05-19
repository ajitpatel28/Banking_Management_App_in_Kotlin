package com.example.bankingmanagementapp.service

import com.example.bankingmanagementapp.*
import com.example.bankingmanagementapp.util.*
import com.example.bankingmanagementapp.model.Account
import com.example.bankingmanagementapp.model.*


fun viewAllUsers(user: User) {
    println("All users:")
    users.forEach{ u ->
        println("User ID: ${u.userId}")
        println("First name: ${u.firstName}")
        println("Last name: ${u.lastName}")
        println("Email: ${u.email}")
        println("Phone: ${u.phone}"
        )
        println("-------------")
    }
    adminUserManagementMenu(user)
}

fun viewAllTransactions(user: User) {
    println("All transactions:")
    transactions.forEach { transaction ->
        println("Transaction ID: ${transaction.transactionId}")
        println("Account number: ${transaction.accountNumber}")
        println("Transaction type: ${transaction.transactionType}")
        println("Amount: ${transaction.amount}")
        println("Date: ${transaction.date}")
        println("-------------")
    }
    adminTransactionManagementMenu(user)
}

fun viewAllLoans(user: User) {
    println("All loans:")
    loans.forEach { loan ->
        println("Loan ID: ${loan.loanId}")
        println("Account number: ${loan.accountNumber}")
        println("Amount: ${loan.amount}")
        println("Rate: ${loan.rate}")
        println("Duration: ${loan.duration}")
        println("Start date: ${loan.startDate}")
        println("Status: ${loan.status}")
        println("-------------")
    }
    adminLoanManagementMenu(user)
}

fun viewAllAccounts(user: User) {
    if (!user.isAdmin) {
        println("You do not have permission to perform this action.")
        return adminHome(user)
    }

    println("All accounts:")
    users.forEach { user ->
        user.accounts.forEach { account ->
            println("User ID: ${user.userId}")
            println("Account number: ${account.accountNumber}")
            println(" Account number: ${account.type}")
            println("Balance: ${account.balance}")
        }
    }
    adminAccountManagementMenu(user)
}

fun viewAllFixedDeposit(user: User) {

    fixedDeposits.forEach {fd ->
        println("Fixed Deposit ID: ${fd.fixedDepositId}")
        println("Account number: ${fd.accountNumber}")
        println("Amount: ${fd.depositAmount}")
        println("Rate: ${fd.interestRate}")
        println("Duration: ${fd.tenure}")
        println("-------------")
        adminFixedDepositMenu(user)
    }
}

fun viewUserFixedDeposits(user: User) {
    println("Enter the email of the user whose details you want to view:")
    val emailInput = readLine()!!

    val userToView = users.find { it.email == emailInput }

    if (userToView == null) {
        println("User with email $emailInput not found.")
        adminFixedDepositMenu(user)
    } else {
        val userToViewID = userToView.userId
        val fd = fixedDeposits.filter { it.userId == userToViewID }
        if (fd == null) {
            println(" No Fixed deposit found for ${emailInput}.")
            adminFixedDepositMenu(user)
        } else {
            fd.forEach {fd ->
                println("Fixed Deposit ID: ${fd.fixedDepositId}")
                println("Account number: ${fd.accountNumber}")
                println("Amount: ${fd.depositAmount}")
                println("Rate: ${fd.interestRate}")
                println("Duration: ${fd.tenure}")
                println("-------------")
            }
            adminFixedDepositMenu(user)
        }
    }
}

fun approveLoan(user: User) {

    val pendingLoans = loans.filter { it.status == "pending" }
    if (pendingLoans.isEmpty()) {
        println("There are no pending loans.")
        adminLoanManagementMenu(user)
    } else {
        println("Pending loans:")
        pendingLoans.forEach { loan ->
            println("Loan ID: ${loan.loanId}")
            println("Account number: ${loan.accountNumber}")
            println("Amount: ${loan.amount}")
            println("Rate: ${loan.rate}")
            println("Duration: ${loan.duration}")
            println("Status: ${loan.status}")
            println()
        }
        println("Enter the ID of the loan you want to approve:")
        val loanId = readLine()
        val loan = loans.find { it.loanId.toString() == loanId }
        if (loan != null) {
            loan.status = "approved"
            saveLoans()
            println("Loan approved.")
        } else {
            println("Loan not found. Please try again.")
        }
        adminLoanManagementMenu(user)
    }
}

fun createAccountForUser(user: User) {
    println("Enter the email address of the user:")
    val email = readLine()?.trim()
    val userToModify = users.find { it.email == email }
    if (userToModify == null) {
        println("User with email address $email not found")
        adminUserManagementMenu(user)
    } else {
        println("Enter the account type:")
        val accountType = readLine()?.trim()
        println("Enter the initial balance:")
        var balance = readLine()?.toDoubleOrNull()
        if (balance == null) {
            println("Invalid balance please open account with valid amount")
            adminUserManagementMenu(user)
        }

        val newAccount =
            Account(userId = userToModify.userId, type = accountType!!, balance = balance!!)
        userToModify.accounts.add(newAccount)
        saveUsers()
        println("Account created successfully")
        println("Account: ${newAccount.accountNumber} ")
        println(" Account number: ${newAccount.type}")
        println("balance: ${newAccount.balance}")

        adminUserManagementMenu(user)
    }
}

fun updateUserDetails(user: User) {
    println("Enter the email of the user whose details you want to update:")
    val emailInput = readLine()!!

    val userToUpdate = users.find { it.email == emailInput }
    if (userToUpdate == null) {
        println("User with email $emailInput not found.")
        adminUserManagementMenu(user)
    } else {
        println("Current first name: ${userToUpdate.firstName}")
        print("Enter new first name (leave blank to keep current value): ")
        val newFirstName = readLine()!!
        userToUpdate.firstName = newFirstName.ifEmpty { userToUpdate.firstName }

        println("Current last name: ${userToUpdate.lastName}")
        print("Enter new last name (leave blank to keep current value): ")
        val newLastName = readLine()!!
        userToUpdate.lastName = newLastName.ifEmpty { userToUpdate.lastName }

        println("Current phone number: ${userToUpdate.phone}")
        print("Enter new phone number (leave blank to keep current value): ")
        val newPhone = readLine()!!
        userToUpdate.phone = newPhone.ifEmpty { userToUpdate.phone }

        println("Details updated successfully.")
        saveUsers()
        adminUserManagementMenu(user)
    }
}

fun viewUserTransaction(user: User) {
    println("Enter the email of the user whose details you want to view:")
    val emailInput = readLine()!!

    val userToView = users.find { it.email == emailInput }

    if (userToView == null) {
        println("User with email $emailInput not found.")
        adminTransactionManagementMenu(user)
    } else {
        val userToViewID = userToView.userId
        val transaction = transactions.filter { it.userId.toString() == userToViewID.toString() }
        if (transaction == null) {
            println(" No  transaction found for ${emailInput}.")
            adminTransactionManagementMenu(user)
        } else {
            transaction.forEach {transaction->
                println("Transaction ID: ${transaction.transactionId}")
                println("Account number: ${transaction.accountNumber}")
                println("Amount: ${transaction.amount}")
                println("Transaction type: ${transaction.transactionType}")
                println("Date: ${transaction.date}")
                println("-------------")
            }
            adminTransactionManagementMenu(user)
        }
    }
}

fun viewLoan(user: User) {

    println("Enter the loan id")
    val loanId = readLine()!!

    val loan = loans.find { it.loanId.toString() == loanId }
    if (loan == null) {
        println("Loan with load id  $loanId not found.")
        adminLoanManagementMenu(user)
    } else {
        println("Loan ID: ${loan.loanId}")
        println("Account number: ${loan.accountNumber}")
        println("Amount: ${loan.amount}")
        println("Rate: ${loan.rate}")
        println("Duration: ${loan.duration}")
        println("Start date: ${loan.startDate}")
        println("Status: ${loan.status}")
        println("-------------")
        adminLoanManagementMenu(user)

    }
}

fun viewTransaction(user: User) {

    println("Enter the transaction id")
    val transactionId = readLine()!!

    val transaction = transactions.find { it.transactionId.toString() == transactionId }
    if (transaction == null) {
        println("User with transaction id $transactionId not found.")
        adminTransactionManagementMenu(user)
    } else {
        println("Transaction ID: ${transaction.transactionId}")
        println("Account number: ${transaction.accountNumber}")
        println("Transaction type: ${transaction.transactionType}")
        println("Amount: ${transaction.amount}")
        println("Date: ${transaction.date}")
        println("-------------")

        adminTransactionManagementMenu(user)

    }
}


fun viewFixedDeposit(user: User) {

    println("Enter the fixed deposit id")
    val fixedDepositID = readLine()!!

    val fd = fixedDeposits.find { it.fixedDepositId.toString() == fixedDepositID }
    if (fd == null) {
        println("Loan with load id  $fixedDepositID not found.")
        adminFixedDepositMenu(user)
    } else {
        println("Fixed Deposit ID: ${fd.fixedDepositId}")
        println("Account number: ${fd.accountNumber}")
        println("Amount: ${fd.depositAmount}")
        println("Rate: ${fd.interestRate}")
        println("Duration: ${fd.tenure}")
        println("-------------")
        adminFixedDepositMenu(user)

    }
}

fun viewUserAccount(user: User) {

    println("Enter the email of the user whose accounts you want to view:")
    val emailInput = readLine()!!

    val userToView = users.find { it.email == emailInput }
    if (user == null) {
        println("User with email $emailInput not found.")
        adminAccountManagementMenu(user)
    } else {
        userToView?.accounts?.forEach { account ->
            println(" Account number: ${account.accountNumber}")
            println(" Account Type: ${account.type}")
            println(" Balance: ${account.balance}")
        }
        println("-------------")
        adminAccountManagementMenu(user)


    }
}

fun viewUser(user: User) {

    println("Enter the email of the user whose details you want to view:")
    val emailInput = readLine()!!

    val userToView = users.find { it.email == emailInput }
    if (userToView == null) {
        println("User with email $emailInput not found.")
        adminUserManagementMenu(user)
    } else {
        println("User ID: ${userToView.userId}")
        println("First name: ${userToView.firstName}")
        println("Last name: ${userToView.lastName}")
        println("Email: ${userToView.email}")
        println("Phone: ${userToView.phone}")
        println("Accounts:")
        userToView.accounts.forEach { account ->
            println(" Account number: ${account.accountNumber}")
            println(" Balance: ${account.balance}")
            println(" type: ${account.type}")
        }
        println("-------------")
        adminUserManagementMenu(user)

    }
}

fun viewAccountById(user: User) {
    println("Enter the Account id")
    val accountNumber = readLine()!!

    for (user in users) {
        for (account in user.accounts) {
            if (account.accountNumber.toString() == accountNumber) {
                println("First Name: ${user.firstName}")
                println("Last Name: ${user.lastName}")
                println("Email: ${user.email}")
                println("Phone: ${user.phone}")
                println("Account Number: ${account.accountNumber}")
                println("Account Type: ${account.type}")
                println("Account Balance: ${account.balance}")
                adminAccountManagementMenu(user)
            }
        }
    }
    println("Account not found.")
    adminAccountManagementMenu(user)

}

