package com.fastrise.app.utill

import com.fastrise.app.ui.dashboard.DashboardItem


class AppConstant {
    companion object {


        //        const val domain = "https://dev-api.gocarin.com/api/v1/"
//      1  const val domain = "http://13.201.228.11/api/v1/" //LIVE
        const val domain = " https://fastrise.co.in/api/Mobile/" //LIVE
//        const val domain = "http://3.6.219.143/api/v1/" //LIVE

        const val loginMethod = "GetCustomerLogin"
        const val productList_api = "GetAllItems?action=ALL&id=0"
        const val GET_Cateogry = "GetAllItemsOld?action=CATEGORY&id=0"
        const val validateOTP = "validateotp/"
        const val validateOTPForgot = "validate_forgot_otp/"
        const val userRegister = "CreatCustomerUser"
        const val CalculatePrescription = "register/"
        const val articalAttrubuteList = "articles/"
        const val articalAttrubuteList1 = "cow_feed_calc/"
        const val blog_data = "blogs/"
        const val contaut_service = "contact_us/"
        const val userProfile = "user_profile/"
        const val NO_INTERNET = "Unable to connect. Please check your Internet Connection."
        const val GETTEXT_FEEDCaluclator = "cow_feed_calc/"
        var token = ""
        const val downloadpdf = "cow_feed_prescription/"
        const val getFarmBoosterDataDashboard = "farm_booster/"
        const val GETCATTLELISTAPI = "cattle_list/"
        var dashboardItem: DashboardItem? = null

    }

}