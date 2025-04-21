package com.fastrise.app.ui.login

import java.io.Serializable

data class LoginResponseModelItem(
    val City: String,
    val Eaddress: String,
    val Gender: String,
    val Gmail: String,
    val Mobile_No: String,
    val User_Type: String,
    val WALLET_BALANCE: String,
    val Name: String,
    val PHOTO: String,
    val terms: String,
    val id: Int
) : Serializable {

}