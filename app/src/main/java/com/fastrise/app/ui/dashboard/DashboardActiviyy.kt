package com.fastrise.app.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R
import com.fastrise.app.databinding.LayoutDashboardBinding
import com.fastrise.app.ui.login.LoginActivity
import com.fastrise.app.ui.login.LoginResponseModelItem
import com.fastrise.app.ui.services.ApiServices
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.DialogUtil
import com.fastrise.app.utill.toast
import com.pixplicity.easyprefs.library.Prefs


class DashboardActiviyy : AppCompatActivity(), EventListner {
    private lateinit var binding: LayoutDashboardBinding
    lateinit var context: Context
    lateinit var episodeAdapter: EpisodeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.layout_dashboard)
        binding = DataBindingUtil.setContentView(this, R.layout.layout_dashboard)
        context = this@DashboardActiviyy
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val i = intent
        val dene: LoginResponseModelItem? =
            i.getSerializableExtra("loginData") as LoginResponseModelItem?
//        binding.textView2.text = dene?.Name
//        binding.textView3.text = dene?.Mobile_No
        Prefs.putString("name", dene?.Name)
        Prefs.putString("mobileno", dene?.Mobile_No)
//        binding.textView5.text = dene?.Gmail
//        binding.couponList.text = "Wallet Balance${dene?.WALLET_BALANCE}"
        DialogUtil.displayProgress(this, "Please Wait loading,Product List..")
        TransportManager.getInstance(this)?.getCategoryWithProduct(context,"1")
//        binding.logOutB.setOnClickListener {
//            Prefs.putString("username", "")
//            val intrr = Intent(this, LoginActivity::class.java)
//            intrr.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intrr)
//        }
//        binding.editProfileB.setOnClickListener {
//            val intent = Intent(this, EditProfileActivity::class.java)
//            intent.putExtra("logindata", dene)
//            startActivity(intent)
//        }
//        binding.backB.setOnClickListener {
//            finish()
//        }

    }

    private val categories = mutableListOf<CategoryModel>()
    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.CATEGORY_LIST -> {
//                DialogUtil.stopProgressDisplay()

                if (data.data is DashboardCategoryModel) {
                    val datacd = data.data as DashboardCategoryModel
                    categories.clear()
                    categories.addAll(datacd.item)

                    // Reset productsMap before fetching new data
                    productsMap.clear()

                    // Fetch products for each category
                    for (category in categories) {
                        TransportManager.getInstance(this)?.getProductList(this, category.ID.toString())
                    }

                    // Update category adapter initially (before products are fetched)

                } else {
                    Log.e("API_ERROR", "Unexpected response for CATEGORY_LIST")
                }
            }

            ApiServices.PRODUCT_LIST -> {
                DialogUtil.stopProgressDisplay()

                if (data.data is DashboardRsponseModel) {
                    val dataBind = data.data as DashboardRsponseModel
                    val gridData = dataBind.item as ArrayList<DashboardItem>

                    // Ensure product list is correctly categorized
                    val categoryName =
                        gridData.firstOrNull()?.CATEGORY  // Assuming each product has a category field
                    if (!categoryName.isNullOrEmpty()) {
                        productsMap[categoryName] = gridData
                    }

                    // Update adapter when each category's products are fetched
//                    episodeAdapter.updateData(categories, productsMap)

                } else {
                    Log.e("API_ERROR", "Unexpected response for PRODUCT_LIST")
                }
            }
            ApiServices.CATEGORY_WISE_PRODUCT ->{
                DialogUtil.stopProgressDisplay()
                val dataList = data.data as ArrayList<DashboardNewResponseModelItem>
                episodeAdapter = EpisodeAdapter(dataList, this)
                binding.recyclerView.adapter = episodeAdapter
            }
        }
    }

    private val productsMap = mutableMapOf<String, List<DashboardItem>>()
    override fun onFailureResponse(reqType: Int, data: ResponseModel<*>) {
        DialogUtil.stopProgressDisplay()
        toast(data.message.toString())
    }
}