package com.fastrise.app.ui.services

import android.content.Context
import com.fastrise.app.ui.dashboard.DashboardCategoryModel
import com.fastrise.app.ui.dashboard.DashboardNewResponseModelItem
import com.fastrise.app.ui.dashboard.DashboardRsponseModel
import com.fastrise.app.ui.dashboard.ItemXX
import com.fastrise.app.ui.dashboard.MobilNoDataResponseModel
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
import com.fastrise.app.utill.NetworkUtils
import com.google.gson.GsonBuilder
import com.pixplicity.easyprefs.library.Prefs

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class TransportManager {
    private var listener: EventListner? = null
    fun setListener(listener: EventListner?) {
        this.listener = listener
    }

    fun userRegister(context: Context?, registerUserRequestObject: UserRegisterRequestModel) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.userRegister(registerUserRequestObject)
                ?.enqueue(object : Callback<ResponseModel<String>> {
                    override fun onResponse(
                        call: Call<ResponseModel<String>>, response: Response<ResponseModel<String>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.USER_REGISTER, response.body()!!)
                        } else {
                            onFailure(ApiServices.USER_REGISTER, response.message())
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel<String>>, t: Throwable) {
                        onFailure(ApiServices.USER_REGISTER, t.localizedMessage)
                    }

                })
        } else {
            onFailure(ApiServices.USER_REGISTER, AppConstant.NO_INTERNET)
        }
    }

    fun userRegisterRetailer(context: Context?, registerUserRequestObject: RetailerRegistration) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.userRegisterRetailer(registerUserRequestObject)
                ?.enqueue(object : Callback<ResponseModel<String>> {
                    override fun onResponse(
                        call: Call<ResponseModel<String>>, response: Response<ResponseModel<String>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.RETAILER_REGISTRATION, response.body()!!)
                        } else {
                            onFailure(ApiServices.RETAILER_REGISTRATION, response.message())
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel<String>>, t: Throwable) {
                        onFailure(ApiServices.RETAILER_REGISTRATION, t.localizedMessage)
                    }

                })
        } else {
            onFailure(ApiServices.RETAILER_REGISTRATION, AppConstant.NO_INTERNET)
        }
    }

    fun userLogin(context: Context?, mobileNo: String, password: String) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.userLogin(mobileNo, password)
                ?.enqueue(object : Callback<ResponseModel<ArrayList<LoginResponseModelItem>>> {
                    override fun onResponse(
                        call: Call<ResponseModel<ArrayList<LoginResponseModelItem>>>,
                        response: Response<ResponseModel<ArrayList<LoginResponseModelItem>>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.USER_LOGIN, response.body()!!)
                        } else {
                            onFailure(ApiServices.USER_LOGIN, response.message())
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseModel<ArrayList<LoginResponseModelItem>>>, t: Throwable
                    ) {
                        onFailure(ApiServices.USER_LOGIN, t.localizedMessage)
                    }

                })
        } else {
            onFailure(ApiServices.USER_LOGIN, AppConstant.NO_INTERNET)
        }
    }

    fun getProductList(context: Context?, categoryName: String) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.getProductList("CATWISE", categoryName)
                ?.enqueue(object : Callback<ResponseModel<DashboardRsponseModel>> {
                    override fun onResponse(
                        call: Call<ResponseModel<DashboardRsponseModel>>,
                        response: Response<ResponseModel<DashboardRsponseModel>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.PRODUCT_LIST, response.body()!!)
                        } else {
                            onFailure(ApiServices.PRODUCT_LIST, response.message())
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseModel<DashboardRsponseModel>>, t: Throwable
                    ) {
                        onFailure(ApiServices.PRODUCT_LIST, t.localizedMessage)
                    }

                })
        } else {
            onFailure(ApiServices.PRODUCT_LIST, AppConstant.NO_INTERNET)
        }
    }

    fun getCategoryList(context: Context?) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.getCategories()
                ?.enqueue(object : Callback<ResponseModel<DashboardCategoryModel>> {
                    override fun onResponse(
                        call: Call<ResponseModel<DashboardCategoryModel>>,
                        response: Response<ResponseModel<DashboardCategoryModel>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.CATEGORY_LIST, response.body()!!)
                        } else {
                            onFailure(ApiServices.CATEGORY_LIST, response.message())
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseModel<DashboardCategoryModel>>, t: Throwable
                    ) {
                        onFailure(ApiServices.CATEGORY_LIST, t.localizedMessage)
                    }

                })
        } else {
            onFailure(ApiServices.CATEGORY_LIST, AppConstant.NO_INTERNET)
        }
    }

    fun getDataByMobileNo(context: Context?, mobileNo: String) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.getDataByMobileNo(mobileNo)?.enqueue(object :
                Callback<ResponseModel<ArrayList<MobilNoDataResponseModelItem>>> {
                override fun onResponse(
                    call: Call<ResponseModel<ArrayList<MobilNoDataResponseModelItem>>>,
                    response: Response<ResponseModel<ArrayList<MobilNoDataResponseModelItem>>>
                ) {
                    if (response.isSuccessful) {
                        filterData(ApiServices.GET_DATA_MOBILE_NO, response.body()!!)
                    } else {
                        onFailure(ApiServices.GET_DATA_MOBILE_NO, response.message())
                    }
                }

                override fun onFailure(
                    call: Call<ResponseModel<ArrayList<MobilNoDataResponseModelItem>>>,
                    t: Throwable
                ) {
                    onFailure(ApiServices.GET_DATA_MOBILE_NO, t.localizedMessage)
                }

            })
        } else {
            onFailure(ApiServices.GET_DATA_MOBILE_NO, AppConstant.NO_INTERNET)
        }
    }

    fun uploadSaleData(context: Context?, registerUserRequestObject: SaleRequestModel) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.saleUploadApi(registerUserRequestObject)
                ?.enqueue(object : Callback<ResponseModel<String>> {
                    override fun onResponse(
                        call: Call<ResponseModel<String>>, response: Response<ResponseModel<String>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.SALE_UPLOAD_API, response.body()!!)
                        } else {
                            onFailure(ApiServices.SALE_UPLOAD_API, response.message())
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel<String>>, t: Throwable) {
                        onFailure(ApiServices.SALE_UPLOAD_API, t.localizedMessage)
                    }

                })
        } else {
            onFailure(ApiServices.SALE_UPLOAD_API, AppConstant.NO_INTERNET)
        }
    }

    /*   fun getSaleListByMobileNo(context: Context?, categoryName: String) {
           if (NetworkUtils.isNetworkAvailable(context!!)) {
               getAPIService()?.getSaleListByNo("SaleAll", categoryName)
                   ?.enqueue(object : Callback<ResponseModel<SaleListResponseModel>> {
                       override fun onResponse(
                           call: Call<ResponseModel<SaleListResponseModel>>,
                           response: Response<ResponseModel<SaleListResponseModel>>
                       ) {
                           if (response.isSuccessful) {
                               filterData(ApiServices.SALE_LIST_API, response.body()!!)
                           } else {
                               onFailure(ApiServices.SALE_LIST_API, response.message())
                           }
                       }

                       override fun onFailure(
                           call: Call<ResponseModel<SaleListResponseModel>>, t: Throwable
                       ) {
                           onFailure(ApiServices.SALE_LIST_API, t.localizedMessage)
                       }

                   })
           } else {
               onFailure(ApiServices.SALE_LIST_API, AppConstant.NO_INTERNET)
           }
       }*/
    fun getSaleListByMobileNo(context: Context?, categoryName: String) {
        context?.let {
            if (NetworkUtils.isNetworkAvailable(it)) {
                getAPIService()?.getSaleListByNo("SaleAll", categoryName)
                    ?.enqueue(object : Callback<ResponseModel<SaleListResponseModel>> {
                        override fun onResponse(
                            call: Call<ResponseModel<SaleListResponseModel>>,
                            response: Response<ResponseModel<SaleListResponseModel>>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.let { body ->
                                    if (body.data != null && body.data is SaleListResponseModel) {
                                        // ✅ Handle Success Case
                                        filterData(ApiServices.SALE_LIST_API, body)
                                    } else {
                                        //  Handle Case Where `data` is Empty List `[]`
                                        onFailure(
                                            ApiServices.SALE_LIST_API,
                                            body.message ?: "No Items Found!"
                                        )
                                    }
                                } ?: onFailure(ApiServices.SALE_LIST_API, "Empty Response")
                            } else {
                                // Extract error message from errorBody()
                                val errorMsg = response.errorBody()?.string() ?: response.message()
                                onFailure(ApiServices.SALE_LIST_API, errorMsg)
                            }
                        }

                        override fun onFailure(
                            call: Call<ResponseModel<SaleListResponseModel>>,
                            t: Throwable
                        ) {
                            onFailure(
                                ApiServices.SALE_LIST_API,
                                t.localizedMessage ?: "Unknown error"
                            )
                        }
                    })
            } else {
                onFailure(ApiServices.SALE_LIST_API, AppConstant.NO_INTERNET)
            }
        } ?: run {
            onFailure(ApiServices.SALE_LIST_API, "Invalid Context")
        }
    }


    fun getCategoryWithProduct(context: Context?, categoryName: String) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.getCategoryWithProduct("all", categoryName)?.enqueue(object :
                Callback<ResponseModel<ArrayList<DashboardNewResponseModelItem>>> {
                override fun onResponse(
                    call: Call<ResponseModel<ArrayList<DashboardNewResponseModelItem>>>,
                    response: Response<ResponseModel<ArrayList<DashboardNewResponseModelItem>>>
                ) {
                    if (response.isSuccessful) {
                        filterData(ApiServices.CATEGORY_WISE_PRODUCT, response.body()!!)
                    } else {
                        onFailure(ApiServices.CATEGORY_WISE_PRODUCT, response.message())
                    }
                }

                override fun onFailure(
                    call: Call<ResponseModel<ArrayList<DashboardNewResponseModelItem>>>,
                    t: Throwable
                ) {
                    onFailure(ApiServices.CATEGORY_WISE_PRODUCT, t.localizedMessage)
                }

            })
        } else {
            onFailure(ApiServices.CATEGORY_WISE_PRODUCT, AppConstant.NO_INTERNET)
        }
    }

    fun getSaleRecordByCustomer(context: Context?, username: String) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.getSaleRecordByCustomer(username, "Purchase")
                ?.enqueue(object : Callback<ResponseModel<CustomerSaleResponseModel>> {
                    override fun onResponse(
                        call: Call<ResponseModel<CustomerSaleResponseModel>>,
                        response: Response<ResponseModel<CustomerSaleResponseModel>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.SALE_LIST_API_BY_CUSTOMER, response.body()!!)
                        } else {
                            onFailure(ApiServices.SALE_LIST_API_BY_CUSTOMER, response.message())
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseModel<CustomerSaleResponseModel>>, t: Throwable
                    ) {
                        onFailure(ApiServices.SALE_LIST_API_BY_CUSTOMER, t.localizedMessage)
                    }

                })
        } else {
            onFailure(ApiServices.SALE_LIST_API_BY_CUSTOMER, AppConstant.NO_INTERNET)
        }
    }

    fun getTranactionDoneByCustomer(context: Context?, username: String) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.getTranactionDoneByCustomer(username, "Wallet")
                ?.enqueue(object : Callback<ResponseModel<TransactionResponseModel>> {
                    override fun onResponse(
                        call: Call<ResponseModel<TransactionResponseModel>>,
                        response: Response<ResponseModel<TransactionResponseModel>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.TRANSACTION_DONE_BY_CUSTOMER, response.body()!!)
                        } else {
                            onFailure(ApiServices.TRANSACTION_DONE_BY_CUSTOMER, response.message())
                        }
                    }

                    override fun onFailure(
                        call: Call<ResponseModel<TransactionResponseModel>>, t: Throwable
                    ) {
                        onFailure(ApiServices.TRANSACTION_DONE_BY_CUSTOMER, t.localizedMessage)
                    }

                })
        } else {
            onFailure(ApiServices.TRANSACTION_DONE_BY_CUSTOMER, AppConstant.NO_INTERNET)
        }
    }

    fun updateProfileApi(context: Context?, registerUserRequestObject: UpdateProfileRequestBody) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.updateProfile(registerUserRequestObject)
                ?.enqueue(object : Callback<ResponseModel<String>> {
                    override fun onResponse(
                        call: Call<ResponseModel<String>>, response: Response<ResponseModel<String>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.UPDATE_PROFILe, response.body()!!)
                        } else {
                            onFailure(ApiServices.UPDATE_PROFILe, response.message())
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel<String>>, t: Throwable) {
                        onFailure(ApiServices.UPDATE_PROFILe, t.localizedMessage)
                    }

                })
        } else {
            onFailure(ApiServices.UPDATE_PROFILe, AppConstant.NO_INTERNET)
        }
    }

    fun userPasswordChange(context: Context?, rid: String, password: String) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.passwordChange(rid, password)
                ?.enqueue(object : Callback<ResponseModel<String>> {
                    override fun onResponse(
                        call: Call<ResponseModel<String>>,
                        response: Response<ResponseModel<String>>
                    ) {
                        if (response.isSuccessful) {
                            filterData(ApiServices.PASSWORD_CHANGE, response.body()!!)
                        } else {
                            onFailure(ApiServices.PASSWORD_CHANGE, response.message())
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel<String>>, t: Throwable) {
                        onFailure(ApiServices.PASSWORD_CHANGE, t.localizedMessage.toString())
                    }

                })
        } else {
            onFailure(ApiServices.PASSWORD_CHANGE, AppConstant.NO_INTERNET)
        }
    }

    fun getSaleDataBySearialNo(context: Context?, mobileNo: String, SupId: String) {
        if (NetworkUtils.isNetworkAvailable(context!!)) {
            getAPIService()?.getSaleDataBySearialNo(SupId, "SerialNo", mobileNo)?.enqueue(object :
                Callback<ResponseModel<ArrayList<ItemXX>>> {
                override fun onResponse(
                    call: Call<ResponseModel<ArrayList<ItemXX>>>,
                    response: Response<ResponseModel<ArrayList<ItemXX>>>
                ) {
                    if (response.isSuccessful) {
                        filterData(ApiServices.GET_DATA_MSERIAL_NO, response.body()!!)
                    } else {
                        onFailure(ApiServices.GET_DATA_MSERIAL_NO, response.message())
                    }
                }

                override fun onFailure(
                    call: Call<ResponseModel<ArrayList<ItemXX>>>,
                    t: Throwable
                ) {
                    onFailure(ApiServices.GET_DATA_MSERIAL_NO, t.localizedMessage)
                }

            })
        } else {
            onFailure(ApiServices.GET_DATA_MOBILE_NO, AppConstant.NO_INTERNET)
        }
    }

    private fun filterData(type: Int, result: ResponseModel<*>) {
        if (result.code == "200") {
            listener!!.onSuccessResponse(type, result)
        } else {
            listener!!.onFailureResponse(type, result)
        }
    }


    fun onFailure(request: Int, data: String) {
        val response = ResponseModel<Void>()
        if (!data.equals(AppConstant.NO_INTERNET, true)) response.message = data
        else response.message = data
        response.data = null
        response.code = "201"
        response.status = "false"
        listener?.onFailureResponse(request, response)
    }


    companion object {
        private var apiServices: ApiServices? = null
        private var manager: TransportManager? = null
        private const val CODE_UNAUTHORIZED = 401
        fun getInstance(conlistener: EventListner?): TransportManager? {
            if (manager == null) manager = TransportManager()
            manager!!.setListener(conlistener)
            return manager
        }

        fun getAPIService(): ApiServices? {
            /*val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            //comment in live build and uncomment in uat
            builder.interceptors().add(interceptor)
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(90, TimeUnit.SECONDS)
            val client: OkHttpClient = builder.build()
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(AppConstant.domain)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(client).build()
            val apiServices = retrofit.create<ApiServices>(ApiServices::class.java)
            return apiServices
*/


            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }  // Disable hostname verification

            // Add other configurations
            builder.interceptors().add(interceptor)
            builder.connectTimeout(3, TimeUnit.MINUTES)
            builder.readTimeout(3, TimeUnit.MINUTES)

            val client: OkHttpClient = builder.build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(AppConstant.domain)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            return retrofit.create(ApiServices::class.java)
        }
    }
}

