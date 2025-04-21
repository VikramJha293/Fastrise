package com.fastrise.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R
import com.fastrise.app.ui.services.ApiServices
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.DialogUtil
import com.pixplicity.easyprefs.library.Prefs

class SaleRecordFragment : Fragment(), EventListner {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sale_record, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        // Sample Data
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        DialogUtil.displayProgress(activity, "Please Wait, loading Buy Record List..")
        TransportManager.getInstance(this)
            ?.getSaleRecordByCustomer(context, Prefs.getString("username"))

        return view
    }

    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.SALE_LIST_API_BY_CUSTOMER -> {
                DialogUtil.stopProgressDisplay()
                val dataListfewfew = data.data as CustomerSaleResponseModel
                val dataList = dataListfewfew.item as ArrayList<SaleResponseModelItem>
                val episodeAdapter = InvoiceAdapter(dataList, requireContext())
                recyclerView.adapter = episodeAdapter
            }
        }
    }

    override fun onFailureResponse(reqType: Int, data: ResponseModel<*>) {
        DialogUtil.stopProgressDisplay()
        Toast.makeText(requireContext(), data.message.toString(), Toast.LENGTH_SHORT).show()
    }
}