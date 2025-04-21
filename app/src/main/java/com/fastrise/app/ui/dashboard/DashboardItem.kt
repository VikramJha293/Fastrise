package com.fastrise.app.ui.dashboard

import java.io.Serializable

data class DashboardItem(
    val CATEGORY: String,
    val COMPANY: String,
    val GST_RATE: Int,
    val HSN: String,
    val ID: Int,
    val ITEM_NAME: String,
    val ITEM_TYPE: String,
    val PHOTO1: String,
    val SHORT_NAME: String,
    val Wallet_Point: Double,
    val Wallet_Price: Double,
    val Price_Per: String,
    val price_per: String,
    val MODEL_NO: String,
    val Discription: String
):Serializable{
    override fun toString(): String {
        return ITEM_NAME.toString()
    }
}