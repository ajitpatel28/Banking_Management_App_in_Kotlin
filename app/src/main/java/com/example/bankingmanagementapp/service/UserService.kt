package com.example.bankingmanagementapp.service

import com.example.bankingmanagementapp.*
import com.example.bankingmanagementapp.util.*
import com.example.bankingmanagementapp.model.*


fun viewAccounts(user: User) {
    println("Your accounts:")
    user.accounts.forEach { account ->
        println("Account number: ${account.accountNumber}")
        println("Balance: ${account.balance}")
        println("-------------")
        println()
    }
    userHome(user)
}

fun openAccount(user: User) {
    println("Please enter the type of account you would like to open (Savings/Current):")
    val accountTypeInput = readLine()?.trim()?.lowercase()

    if (accountTypeInput == "savings" || accountTypeInput == "current") {
        println("Enter the initial balance:")
        val balance = readLine()?.toDoubleOrNull()
        if (balance == null) {
            println("Invalid balance")
            return userHome(user)
        }
        val account = Account(userId = user.userId, type=accountTypeInput, balance = balance)
        user.accounts.add(account)
        saveUsers()
        println("New $accountTypeInput account opened successfully!")
        println("Account number: ${account.accountNumber}")
        userHome(user)
    }
}

fun deposit(user: User) {
    println("Please enter the account number you would like to deposit into:")
    val accountNumberInput = readLine()
    val account = user.accounts.find { it.accountNumber.toString() == accountNumberInput }
    if (account != null) {
        println("Please enter the amount you would like to deposit:")
        val amountInput = readLine()?.toDoubleOrNull()
        if (amountInput != null && amountInput > 0) {
            account.balance += amountInput
            val transaction = Transaction(accountNumber = account.accountNumber, transactionType = "deposit", amount = amountInput, userId = user.userId)
            transactions.add(transaction)
            saveTransactions()
            println("Deposit successful. New balance: ${account.balance}")
        } else {
            println("Invalid amount. Please try again.")
        }
    } else {
        println("Account not found. Please try again.")
    }
    userHome(user)
}

fun withdraw(user: User) {
    println("Please enter the account number you would like to withdraw from:")
    val accountNumberInput = readLine()
    val account = user.accounts.find { it.accountNumber.toString() == accountNumberInput }
    if (account != null) {
        println("Please enter the amount you would like to withdraw:")
        val amountInput = readLine()?.toDoubleOrNull()
        if (amountInput != null && amountInput > 0 && amountInput <= account.balance) {
            account.balance -= amountInput
            val transaction = Transaction(accountNumber = account.accountNumber, transactionType = "withdraw", amount = amountInput, userId = user.userId)
            transactions.add(transaction)
            saveTransactions()
            saveUsers()
            println("Withdrawal successful.")
        } else {
            println("Invalid input or insufficient funds. Please try again.")
        }
    } else {
        println("Account not found. Please try again.")
    }
    userHome(user)
}

fun transfer(user: User) {
    println("Please enter the account number you would like to transfer from:")
    val fromAccountNumberInput = readLine()
    val fromAccount = user.accounts.find { it.accountNumber.toString() == fromAccountNumberInput }
    if (fromAccount != null) {
        println("Please enter the account number you would like to transfer to:")
        val toAccountNumberInput = readLine()
        val toAccount = users.flatMap { it.accounts }.find { it.accountNumber.toString() == toAccountNumberInput }
        if (toAccount != null) {
            println("Please enter the amount you would like to transfer:")
            val amountInput = readLine()?.toDoubleOrNull()
            if (amountInput != null && amountInput > 0 && amountInput <= fromAccount.balance) {
                fromAccount.balance -= amountInput
                toAccount.balance += amountInput
                val transaction = Transaction(accountNumber = fromAccount.accountNumber, transactionType = "transfer", amount = amountInput, userId = user.userId)
                transactions.add(transaction)
                saveTransactions()
                saveUsers()
                println("Transfer successful.")
            } else {
                println("Invalid input or insufficient funds. Please try again.")
            }
        } else {
            println("Destination account not found. Please try again.")
        }
    } else {
        println("Account not found. Please try again.")
    }
    userHome(user)
}

fun viewCurrentUserTransactions(user: User) {

    val userTransactions = transactions.filter { it.userId == user.userId }
    if (userTransactions == null) {
        println("You have no transactions.")
        userHome(user)
    } else {
        println("Your transactions :")
        userTransactions.forEach {
            println("Transaction ID: ${it.transactionId}")
            println("Account Number: ${it.accountNumber}")
            println("Transaction Type: ${it.transactionType}")
            println("Amount: ${it.amount}")
            println("Date: ${it.date}")
            println()
            println("-------------")
        }
        userHome(user)
    }
}

fun applyForLoan(user: User) {
    println("Please enter the account number you would like to apply for a loan from:")
    val accountNumberInput = readLine()
    val account = user.accounts.find { it.accountNumber.toString() == accountNumberInput }
    if (account != null) {
        println("Please enter the amount you would like to borrow:")
        val amountInput = readLine()?.toDoubleOrNull()
        if (amountInput != null && amountInput > 0) {
            val loan = Loan(accountNumber = account.accountNumber, amount = amountInput, rate = 0.05, duration = 12, userId = user.userId)
            loans.add(loan)
            saveLoans()
            println("Loan application submitted successfully.")
            userHome(user)
        } else {
            println("Invalid input. Please try again.")
            applyForLoan(user)
        }
    } else {
        println("Account not found. Please try again.")
        applyForLoan(user)
    }
}

fun viewCurrentUserLoans(user: User){

    val userLoans = loans.filter { it.userId == user.userId }
    if (userLoans == null) {
        println("You have no loans.")
        userHome(user)
    } else {
        println("You loans :")
        userLoans.forEach {
            println("Transaction ID: ${it.loanId}")
            println("Account Number: ${it.accountNumber}")
            println("Interest rate: ${it.rate}")
            println("Tenure: ${it.duration}")
            println("Amount: ${it.amount}")
            println("Start Date: ${it.startDate}")
            println("Start Date: ${it.status}")
            println()
            println("-------------")
        }
        userHome(user)
    }
}

fun updateProfile(user: User) {
    println("Please enter your new details. Press enter to leave a field unchanged.")

    print("First Name [${user.firstName}]: ")
    val newFirstName = readLine()?.takeIf { it.isNotBlank() } ?: user.firstName

    print("Last Name [${user.lastName}]: ")
    val newLastName = readLine()?.takeIf { it.isNotBlank() } ?: user.lastName

    print("Email [${user.email}]: ")
    val newEmail = readLine()?.takeIf { it.isNotBlank() } ?: user.email

    print("Phone [${user.phone}]: ")
    val newPhone = readLine()?.takeIf { it.isNotBlank() } ?: user.phone

    print("Password: ")
    val newPassword = readLine()?.takeIf { it.isNotBlank() } ?: user.password

    val updatedUser = User(
        userId = user.userId,
        firstName = newFirstName,
        lastName = newLastName,
        email = newEmail,
        phone = newPhone,
        password = newPassword,
        isAdmin = user.isAdmin,
        accounts = user.accounts
    )

    users[users.indexOf(user)] = updatedUser
    saveUsers()

    println("User details updated successfully.")
    if (user.isAdmin) {
        adminHome(user)
    } else {
        userHome(user)
    }
}

fun fixedDeposit(user: User) {

    println("Enter the account number in which you want to do fixed deposit ")
    val accountNumber = readLine()!!
    val account = user.accounts.find { it.accountNumber.toString() == accountNumber }
    if (account != null) {
        println("Enter the amount you want to deposit:")
        val depositAmount = readLine()?.toDoubleOrNull()

        if (depositAmount == null || depositAmount <= 0) {
            println("Invalid amount.")
            return userHome(user)
        }

        println("Enter the duration of the fixed deposit (in months):")
        val duration = readLine()?.toIntOrNull()
        if (duration == null || duration <= 0) {
            println("Invalid duration.")
            return userHome(user)
        }

        // Calculate the interest rate based on the duration of the fixed deposit
        val interestRate = when (duration) {
            in 1..6 -> 0.05
            in 7..12 -> 0.06
            in 13..24 -> 0.07
            else -> 0.08
        }

        val interestAmount = depositAmount * interestRate * duration / 12
        val totalAmount = depositAmount + interestAmount
        val fd = FixedDeposit(tenure = duration, depositAmount = depositAmount, interestRate = interestRate, accountNumber =account.accountNumber, userId = user.userId)
        fixedDeposits.add(fd)
        saveFixedDeposits()
        println("Your fixed deposit account has been created with the following details:")
        println("Account number: $accountNumber")
        println("Deposit amount: ${String.format("%.2f", depositAmount)}")
        println("Interest rate: $interestRate")
        println("Duration: $duration months")
        println("Interest amount: ${String.format("%.2f", interestAmount)}")
    }else {
        println("Account not found. Please try again.")
        fixedDeposit(user)
    }

    if (user.isAdmin) {
        adminHome(user)
    } else {
        userHome(user)
    }

}





fun viewCurrentUserFixedDeposits(user: User) {
        val fd = fixedDeposits.filter { it.userId.toString() == user.userId.toString() }
        if (fd == null) {
            println("You have no Fixed deposit.")
            userHome(user)
        } else {
            fd.forEach {fd ->
                println("Fixed Deposit ID: ${fd.fixedDepositId}")
                println("Account number: ${fd.accountNumber}")
                println("Amount: ${fd.depositAmount}")
                println("Rate: ${fd.interestRate}")
                println("Duration: ${fd.tenure}")
                println("-------------")
            }
            userHome(user)
        }
    }
