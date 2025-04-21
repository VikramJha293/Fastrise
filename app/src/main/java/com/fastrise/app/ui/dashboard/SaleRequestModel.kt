package com.fastrise.app.ui.dashboard

data class SaleRequestModel(
    val ConsId: String,
    val EntBy: String,
    val ItemId: String,
    val PayMode: String,
    val Price: String,
    val PriceWallet: String,
    val Qty: String,
    val ShopId: String,
    val TotalPrice: String,
    val TotalPriceWallet: String,
    val didYouSell: String,
    val SerialNo: String
)