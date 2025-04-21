package com.fastrise.app.ui.services


import com.fastrise.app.ui.dashboard.CategoryModel
import com.fastrise.app.ui.dashboard.DashboardCategoryModel
import com.fastrise.app.ui.dashboard.DashboardNewResponseModelItem
import com.fastrise.app.ui.dashboard.DashboardRsponseModel
import com.fastrise.app.ui.dashboard.ItemXX
import com.fastrise.app.ui.dashboard.MobilNoDataResponseModelItem
import com.fastrise.app.ui.dashboard.SaleListResponseModel
import com.fastrise.app.ui.dashboard.SaleRecordBySearilnoResponse
import com.fastrise.app.ui.dashboard.SaleRequestModel
import com.fastrise.app.ui.dashboard.UpdateProfileRequestBody
import com.fastrise.app.ui.fragment.CustomerSaleResponseModel
import com.fastrise.app.ui.fragment.SaleResponseModelItem
import com.fastrise.app.ui.fragment.TransactionResponseModel
import com.fastrise.app.ui.login.LoginResponseModel
import com.fastrise.app.ui.login.LoginResponseModelItem
import com.fastrise.app.ui.register.RetailerRegistration
import com.fastrise.app.ui.register.UserRegisterRequestModel
import com.fastrise.app.utill.AppConstant
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {

    @POST(AppConstant.userRegister)
    fun userRegister(@Body sendOtpRequestModel: UserRegisterRequestModel): Call<ResponseModel<String>>

    @GET(AppConstant.loginMethod)
    fun userLogin(
        @Query("username") sendOtpRequestModel: String,
        @Query("password") password: String
    ): Call<ResponseModel<ArrayList<LoginResponseModelItem>>>

    @GET("GetAllItemsOld")
    fun getProductList(
        @Query("action") action: String,
        @Query("id") category: String
    ): Call<ResponseModel<DashboardRsponseModel>>

    @GET(AppConstant.GET_Cateogry)
    fun getCategories(): Call<ResponseModel<DashboardCategoryModel>>

    @POST(AppConstant.userRegister)
    fun userRegisterRetailer(@Body sendOtpRequestModel: RetailerRegistration): Call<ResponseModel<String>>

    @GET("GetCustomerInfo")
    fun getDataByMobileNo(@Query("Mobile") Mobile: String): Call<ResponseModel<ArrayList<MobilNoDataResponseModelItem>>>

    @POST("CreateSale")
    fun saleUploadApi(@Body sendOtpRequestModel: SaleRequestModel): Call<ResponseModel<String>>

    @GET("GetSaleList")
    fun getSaleListByNo(
        @Query("action") action: String,
        @Query("id") id: String
    ): Call<ResponseModel<SaleListResponseModel>>

    @GET("GetAllItems")
    fun getCategoryWithProduct(
        @Query("action") action: String,
        @Query("id") id: String
    ): Call<ResponseModel<ArrayList<DashboardNewResponseModelItem>>>

    @GET("GetCustomerReports")
    fun getSaleRecordByCustomer(
        @Query("Mobile") Mobile: String,
        @Query("Action") Action: String
    ): Call<ResponseModel<CustomerSaleResponseModel>>

    @GET("GetCustomerReports")
    fun getTranactionDoneByCustomer(
        @Query("Mobile") Mobile: String,
        @Query("Action") Action: String
    ): Call<ResponseModel<TransactionResponseModel>>

    @POST("UpdateCustomerUser")
    fun updateProfile(@Body sendOtpRequestModel: UpdateProfileRequestBody): Call<ResponseModel<String>>

    @POST("UpdateCustomerPassword")
    @Headers("Content-Type: application/json")
    fun passwordChange(
        @Query("mobile") mobile: String,
        @Query("password") password: String
    ): Call<ResponseModel<String>>

    @GET("GetItemsSerialNo")
    fun getSaleDataBySearialNo(
        @Query("SupId") SupId: String,
        @Query("Action") action: String,
        @Query("SerialNo") id: String
    ): Call<ResponseModel<ArrayList<ItemXX>>>

    companion object {
        const val USER_REGISTER = 1
        const val USER_LOGIN = 2
        const val PRODUCT_LIST = 3
        const val CATEGORY_LIST = 4
        const val RETAILER_REGISTRATION = 5
        const val GET_DATA_MOBILE_NO = 6
        const val SALE_UPLOAD_API = 7
        const val SALE_LIST_API = 8
        const val CATEGORY_WISE_PRODUCT = 9
        const val SALE_LIST_API_BY_CUSTOMER = 10
        const val TRANSACTION_DONE_BY_CUSTOMER = 11
        const val UPDATE_PROFILe = 12
        const val PASSWORD_CHANGE = 13
        const val GET_DATA_MSERIAL_NO = 14

    }
}