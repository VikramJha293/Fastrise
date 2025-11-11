package com.fastrise.app.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
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
    var mobileno =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = intent
        val dene: LoginResponseModelItem? =
            i.getSerializableExtra("loginData") as LoginResponseModelItem?
        mobileno = dene!!.Mobile_No
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
        binding.buttonCategoryWiseReport.setOnClickListener {
            val intt = Intent(this, SaleReportActivity::class.java)
            intt.putExtra("loginData",dene)
            startActivity(intt)
        }
        TransportManager.getInstance(this)?.getSaleCategoryWiseData(context,mobileno)


    }

    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.SALE_LIST_API -> {
                DialogUtil.stopProgressDisplay()
                val dataList = data.data as SaleListResponseModel
                val bindDat = dataList.item
                invoiceAdapter = SaleListAdapter(bindDat, context)
                binding.recyclerView.adapter = invoiceAdapter
//                TransportManager.getInstance(this)?.getSaleCategoryWiseData(context,mobileno.toString())
            }
            ApiServices.getSaleCategoryWiseData -> {
                val dataGet = data.data as? CategorySaleReport

                // Safely find the item where NAME == "Shutter motors"
                val shutterMotorItem = dataGet?.item?.find { it.CATEGORY.equals(" Shutter motors", ignoreCase = true) == true }

                if (shutterMotorItem != null) {
                    val target = shutterMotorItem.Target ?: 0
                    val achieved = shutterMotorItem.QTY ?: 0

                    binding.progreslayout.visibility = View.VISIBLE
//                    binding.marqueeText.text = "\uD83C\uDFC6 Keep rolling, keep selling! Sell $target and you have achieved $achieved so far."
//                    binding.marqueeText.text = "\uD83C\uDFC6 Keep rolling, keep selling! Sell $target Shutter Motors and win an exciting offer! $achieved down, 497 to go \uD83D\uDD25"
//                    binding.marqueeText.isSelected = true // ✅ enables infinite marquee
                    binding.progressBar.max = target
                    binding.progressBar.progress = achieved
                    binding.tvCurrent.text = "$achieved achieved"
                    binding.tvTarget.text = "$target Target"
                } else {
                    // Optionally clear or hide text when "Shutter motors" is not found
//                    binding.marqueeText.text = ""
                    binding.progreslayout.visibility = View.GONE
                }
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