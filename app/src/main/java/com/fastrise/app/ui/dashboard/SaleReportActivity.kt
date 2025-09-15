package com.fastrise.app.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R
import com.fastrise.app.ui.login.LoginResponseModelItem
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.DialogUtil
import com.fastrise.app.utill.toast
import java.util.ArrayList

class SaleReportActivity : AppCompatActivity(), EventListner {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SaleReportAdapter
    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale_report)
        recyclerView = findViewById(R.id.recyclerViewSaleReport)
        val backB: ImageView = findViewById(R.id.backB)
        context = this@SaleReportActivity
        val i = intent
        val dene: LoginResponseModelItem? =
            i.getSerializableExtra("loginData") as LoginResponseModelItem?
        backB.setOnClickListener {
            finish()
        }
        DialogUtil.displayProgress(this, "Please wait data is loading..")
        TransportManager.getInstance(this)!!
            .getSaleCategoryWiseData(context, dene?.Mobile_No.toString())
    }

    override fun onSuccessResponse(
        reqType: Int,
        data: ResponseModel<*>
    ) {
        DialogUtil.stopProgressDisplay()
        recyclerView.layoutManager = LinearLayoutManager(this)
        val dataGet= data.data as CategorySaleReport
        adapter = SaleReportAdapter(dataGet.item)
        recyclerView.adapter = adapter

    }

    override fun onFailureResponse(
        reqType: Int,
        data: ResponseModel<*>
    ) {
        DialogUtil.stopProgressDisplay()
        toast(data.message.toString())


    }
}