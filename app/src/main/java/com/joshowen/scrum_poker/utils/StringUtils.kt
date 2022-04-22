package com.joshowen.scrum_poker.utils

fun generateRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun String.isValidUserName() : Boolean {
    return this.isNotEmpty()
}

fun String.isValidLobbyCode() : Boolean {
    return this.length == 6
}