package com.fastrise.app.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fastrise.app.R
import com.fastrise.app.databinding.LayoutNewDashboardBinding
import com.fastrise.app.ui.fragment.DashboardFragment
import com.fastrise.app.ui.fragment.EditProfileFragment
import com.fastrise.app.ui.fragment.SaleRecordFragment
import com.fastrise.app.ui.fragment.TransactionFragment
import com.fastrise.app.ui.login.LoginResponseModelItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pixplicity.easyprefs.library.Prefs

class NewDashboardPage : AppCompatActivity() {
    private lateinit var binding: LayoutNewDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.layout_new_dashboard)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val i = intent
        val dene: LoginResponseModelItem? =
            i.getSerializableExtra("loginData") as LoginResponseModelItem?
        binding.textView2.text = dene?.Name
        binding.textView3.text = dene?.Mobile_No
        Prefs.putString("name", dene?.Name)
        Prefs.putString("mobileno", dene?.Mobile_No)
        binding.textView5.text = dene?.Gmail
//        binding.couponList.text = "Wallet Balance${dene?.WALLET_BALANCE}"
        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
        }
        val rewardBadge: TextView = findViewById(R.id.rewardBadge)
        val rewardPoints = dene?.WALLET_BALANCE?.toDoubleOrNull()?.let { Math.round(it).toInt() } ?: 0  // Set your dynamic value

        if (rewardPoints > 0) {
                rewardBadge.text = rewardPoints.toString()
                rewardBadge.visibility = View.VISIBLE  // Show badge if points exist
            } else {
                rewardBadge.visibility = View.GONE  // Hide if no points
            }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(DashboardFragment())
                R.id.nav_tranaction -> loadFragment(TransactionFragment())
                R.id.nav_investments -> loadFragment(SaleRecordFragment())
                R.id.nav_discover -> loadFragment(EditProfileFragment(), dene)
            }
            true
        }
        binding.couponList.setOnClickListener {
            val intentf = Intent(this, TermsConditionsActivity::class.java)
            intentf.putExtra("logindata", dene!!) // Ensure 'dene' is not null
            startActivity(intentf)

        }
    }

    private fun loadFragment(fragment: Fragment, loginData: LoginResponseModelItem? = null) {
        val bundle = Bundle()
        bundle.putSerializable("logindata", loginData)  // Pass the login data
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}