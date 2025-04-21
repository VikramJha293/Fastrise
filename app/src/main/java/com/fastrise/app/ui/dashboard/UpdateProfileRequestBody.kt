package com.fastrise.app.ui.dashboard

data class UpdateProfileRequestBody(
    val Gmail: String,
    val Mobile_No: String,
    val Name: String,
    val Photo: String,
    val UserType: String
)