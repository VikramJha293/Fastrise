package com.fastrise.app.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrise.app.R
import com.fastrise.app.databinding.LayoutDashboardBinding
import com.fastrise.app.ui.dashboard.CategoryModel
import com.fastrise.app.ui.dashboard.DashboardItem
import com.fastrise.app.ui.dashboard.DashboardNewResponseModelItem
import com.fastrise.app.ui.dashboard.EpisodeAdapter
import com.fastrise.app.ui.login.LoginResponseModelItem
import com.fastrise.app.ui.services.ApiServices
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.DialogUtil
import com.pixplicity.easyprefs.library.Prefs

class DashboardFragment : Fragment(), EventListner {
    private lateinit var binding: LayoutDashboardBinding
    private lateinit var context: Context
    private lateinit var episodeAdapter: EpisodeAdapter
    private val categories = mutableListOf<CategoryModel>()
    private val productsMap = mutableMapOf<String, List<DashboardItem>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_dashboard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = requireContext()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val dene: LoginResponseModelItem? =
            arguments?.getSerializable("loginData") as? LoginResponseModelItem

        Prefs.putString("name", dene?.Name)
        Prefs.putString("mobileno", dene?.Mobile_No)

        DialogUtil.displayProgress(activity, "Please Wait, loading Product List..")
        TransportManager.getInstance(this)?.getCategoryWithProduct(context, "1")

    }

    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.CATEGORY_WISE_PRODUCT -> {
                DialogUtil.stopProgressDisplay()
                val dataList = data.data as ArrayList<DashboardNewResponseModelItem>
                episodeAdapter = EpisodeAdapter(dataList, requireContext())
                binding.recyclerView.adapter = episodeAdapter
            }
        }
    }

    override fun onFailureResponse(reqType: Int, data: ResponseModel<*>) {
        DialogUtil.stopProgressDisplay()
        Toast.makeText(requireContext(), data.message.toString(), Toast.LENGTH_SHORT).show()
    }
}
