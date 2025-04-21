package com.fastrise.app.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrise.app.R
import com.fastrise.app.databinding.LayoutSaleListActivtyBinding
import com.fastrise.app.ui.login.LoginResponseModelItem
import com.fastrise.app.ui.services.ApiServices
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.DialogUtil
import com.fastrise.app.utill.toast
import com.pixplicity.easyprefs.library.Prefs

class SaleListRecordActivity : AppCompatActivity(), EventListner {
    private lateinit var binding: LayoutSaleListActivtyBinding
    lateinit var context: Context
    private lateinit var invoiceAdapter: SaleListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = intent
        val dene: LoginResponseModelItem? =
            i.getSerializableExtra("loginData") as LoginResponseModelItem?
        binding = DataBindingUtil.setContentView(this, R.layout.layout_sale_list_activty)
        context = this@SaleListRecordActivity
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.backB.setOnClickListener {
            finish()
        }
        binding.fabAddSale.setOnClickListener {
            val intt = Intent(this, SaleProductActivity::class.java)
            intt.putExtra("loginData",dene)
            startActivity(intt)
        }

    }

    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.SALE_LIST_API -> {
                DialogUtil.stopProgressDisplay()
                val dataList = data.data as SaleListResponseModel
                val bindDat = dataList.item
                invoiceAdapter = SaleListAdapter(bindDat, context)
                binding.recyclerView.adapter = invoiceAdapter
            }
        }
    }

    override fun onFailureResponse(reqType: Int, data: ResponseModel<*>) {
        DialogUtil.stopProgressDisplay()
        toast(data.message.toString())
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.displayProgress(this, "Please wait sale record is loading..")
        TransportManager.getInstance(this)
            ?.getSaleListByMobileNo(context, Prefs.getString("username"))
    }
}