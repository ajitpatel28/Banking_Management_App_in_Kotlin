package com.example.bankingmanagementapp.util



fun isValidEmail(email: String?): Boolean {
    if (email == null || email.isBlank()) {
        println("Email cannot be empty")
        return false
    }

    if (!email.contains("@")) {
        println("Invalid email format")
        return false
    }

    // Other validation checks can be added here

    return true
}

fun isValidPassword(password: String?): Boolean {
    if (password == null || password.isBlank()) {
        println("Password cannot be empty")
        return false
    }

    if (password.length < 8) {
        println("Password must be at least 8 characters long")
        return false
    }

    if (!password.matches(Regex(".*[A-Z].*"))) {
        println("Password must contain an uppercase letter")
        return false
    }

    if (!password.matches(Regex(".*[!@#\$%^&*()].*"))) {
        println("Password must contain a special symbol")
        return false
    }

    // Other validation checks can be added here

    return true
}

fun isValidMobile(mobile: String?): Boolean {
    if (mobile == null || mobile.isBlank()) {
        println("Mobile number cannot be empty")
        return false
    }

    if (!mobile.matches(Regex("\\d{10}"))) {
        println("Invalid mobile number format")
        return false
    }

    // Other validation checks can be added here

    return true
}