package com.fastrise.app.ui.dashboard

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fastrise.app.R
import com.fastrise.app.ui.login.LoginActivity
import com.fastrise.app.ui.login.LoginResponseModelItem
import com.fastrise.app.ui.services.ApiServices
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.DialogUtil
import com.fastrise.app.utill.toast
import com.google.android.material.textfield.TextInputLayout
import com.pixplicity.easyprefs.library.Prefs
import java.util.Calendar


class SaleProductActivity : AppCompatActivity(), EventListner {
    private var etInvoiceDate: EditText? = null
    private var etCustomerMobile: EditText? = null
    private var etCustomerName: EditText? = null
    private var etQuantity: EditText? = null
    private var etPrice: EditText? = null
    private var etTotPrice: EditText? = null
    private var etDidYouSell: EditText? = null
    private var etSerialNo: EditText? = null
    private var etPriceWallet: EditText? = null
    private var etTotalPriceWallet: EditText? = null
    private var userwalletbalance: EditText? = null
    private var etProduct: EditText? = null
    private var inputLayoutPrice: TextInputLayout? = null
    private var inputLayoutToatlPrice: TextInputLayout? = null
    private var inputLayoutWallet: TextInputLayout? = null
    private var inputLayoutToatlWallet: TextInputLayout? = null
    private var inputLayoutDidYouSell: TextInputLayout? = null
    private var spinnerProduct: Spinner? = null
    private var logOutB: ImageView? = null
    private var backB: ImageView? = null
    private var spinnerCategory: Spinner? = null
    private var spinnerPayment: Spinner? = null
    private var btnSubmit: Button? = null
    private var btnFetchDetails: Button? = null
    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sale_activity)
        context = this@SaleProductActivity
        val i = intent
        val dene: LoginResponseModelItem? =
            i.getSerializableExtra("loginData") as LoginResponseModelItem?
        // Initialize Views
//        etInvoiceDate = findViewById<EditText>(R.id.etInvoiceDate)
        etCustomerMobile = findViewById<EditText>(R.id.etCustomerMobile)
        etCustomerName = findViewById<EditText>(R.id.etCustomerName)
        etQuantity = findViewById<EditText>(R.id.etQuantity)
        etPrice = findViewById<EditText>(R.id.etPrice)
        etTotPrice = findViewById<EditText>(R.id.etTotPrice)
        etPriceWallet = findViewById<EditText>(R.id.etPriceWallet)
        etSerialNo = findViewById<EditText>(R.id.etSerialNo)
        etTotalPriceWallet = findViewById<EditText>(R.id.etTotalPriceWallet)
        etDidYouSell = findViewById<EditText>(R.id.etDidYouSell)
        userwalletbalance = findViewById<EditText>(R.id.userwalletbalance)
        etProduct = findViewById<EditText>(R.id.etProduct)
        spinnerProduct = findViewById<Spinner>(R.id.spinnerProduct)
        spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        spinnerPayment = findViewById<Spinner>(R.id.spinnerPayment)
        inputLayoutPrice = findViewById<TextInputLayout>(R.id.inputLayoutPrice)
        inputLayoutToatlPrice = findViewById<TextInputLayout>(R.id.inputLayoutToatlPrice)
        inputLayoutWallet = findViewById<TextInputLayout>(R.id.inputLayoutWallet)
        inputLayoutToatlWallet = findViewById<TextInputLayout>(R.id.inputLayoutToatlWallet)
        inputLayoutDidYouSell = findViewById<TextInputLayout>(R.id.inputLayoutDidYouSell)
        logOutB = findViewById<ImageView>(R.id.logOutB)
        backB = findViewById<ImageView>(R.id.backB)
        btnSubmit = findViewById<Button>(R.id.btn_save)
        btnFetchDetails = findViewById<Button>(R.id.btnFetchDetails)

        // Disable Editing for Non-Editable Fields
        etPrice?.isFocusable = false
        etTotPrice?.isFocusable = false
        etPriceWallet?.isFocusable = false
        etTotalPriceWallet?.isFocusable = false
        etCustomerName?.isFocusable = false
        userwalletbalance?.isFocusable = false
//        DialogUtil.displayProgress(this, "Please wait loading category")
//        TransportManager.getInstance(this)?.getCategoryList(context)
        // Open DatePicker on Click
        etInvoiceDate?.setOnClickListener(View.OnClickListener { v: View? -> showDatePicker() })
        btnSubmit?.isEnabled = false
        btnSubmit?.isClickable = false
        btnSubmit?.alpha = 0.5f
        // Set up Spinners
        setupSpinners()

        // Validate Inputs on Submit
        btnSubmit?.setOnClickListener { validateInputs() }
        spinnerProduct?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                selectedDashboardItem = parent.getItemAtPosition(position) as DashboardItem
                etPrice?.setText(selectedDashboardItem!!.price_per.toString())
                etPriceWallet?.setText(selectedDashboardItem!!.Wallet_Price.toString())
                // You can add logic here to update prices or other fields based on selection

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Optional: Handle case when nothing is selected
            }
        }
        spinnerCategory?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {


                categoryModel = parent.getItemAtPosition(position) as CategoryModel
                // You can add category-specific logic here
                DialogUtil.displayProgress(
                    this@SaleProductActivity,
                    "Please wait getting product based upon category"
                )
                TransportManager.getInstance(this@SaleProductActivity)
                    ?.getProductList(context, categoryModel!!.ID.toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Optional: Handle case when nothing is selected
            }
        }
        spinnerPayment?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position != 0) {  // Skipping the "Select Payment" option
                    saleType = parent.getItemAtPosition(position) as String
                    if (saleType == "Rupees") {
                        inputLayoutPrice?.visibility = View.GONE
                        inputLayoutToatlPrice?.visibility = View.GONE
                        inputLayoutDidYouSell?.visibility = View.GONE
                        inputLayoutWallet?.visibility = View.GONE
                        inputLayoutToatlWallet?.visibility = View.GONE
                    } else {
                        inputLayoutPrice?.visibility = View.GONE
                        inputLayoutToatlPrice?.visibility = View.GONE
                        inputLayoutDidYouSell?.visibility = View.GONE
                        inputLayoutWallet?.visibility = View.VISIBLE
                        inputLayoutToatlWallet?.visibility = View.VISIBLE
                    }
                    // Handle payment method selection
                } else {
                    inputLayoutPrice?.visibility = View.GONE
                    inputLayoutToatlPrice?.visibility = View.GONE
                    inputLayoutWallet?.visibility = View.GONE
                    inputLayoutToatlWallet?.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Optional: Handle case when nothing is selected
            }
        }
        etCustomerMobile?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.length == 10) {  // Check if it's exactly 10 digits
                    val mobileNumber = s.toString()
                    DialogUtil.displayProgress(
                        this@SaleProductActivity,
                        "Please wait getting details based upon mobile no no"
                    )
                    TransportManager.getInstance(this@SaleProductActivity)
                        ?.getDataByMobileNo(context, mobileNumber)
                }
            }
        })
        etQuantity?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                calculateTotalPrice()
            }
        })
        logOutB?.setOnClickListener {
            Prefs.putString("username", "")
            val intrr = Intent(this, LoginActivity::class.java)
            intrr.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intrr)
        }
        backB?.setOnClickListener {
            finish()
        }
        btnFetchDetails?.setOnClickListener {
            val serialNo = etSerialNo!!.text.toString()
            if (serialNo.isNotEmpty()) {
                DialogUtil.displayProgress(
                    this@SaleProductActivity,
                    "Please wait getting details based upon serial no"
                )
                TransportManager.getInstance(this@SaleProductActivity)
                    ?.getSaleDataBySearialNo(context, serialNo,dene?.id.toString())
            } else {
                Toast.makeText(this, "Please enter Serial No", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var categoryModel: CategoryModel? = null
    var selectedDashboardItem: DashboardItem? = null
    var saleType: String = ""

    // Show Date Picker Dialog
    private fun showDatePicker() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view: DatePicker?, year1: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate =
                    dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year1
                etInvoiceDate!!.setText(selectedDate)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    // Populate Spinners
    private fun setupSpinners() {
        val payments = arrayOf("Select Sale Type", "Rupees", "Wallet")
        val paymentAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, payments)
        spinnerPayment!!.adapter = paymentAdapter
    }

    // Validate Inputs on Submit
    private fun validateInputs() {

        if (etSerialNo!!.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter Serial No.", Toast.LENGTH_SHORT).show()
            return
        }
        if (etCustomerMobile!!.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter Customer Mobile", Toast.LENGTH_SHORT).show()
            return
        }
        if (etCustomerName!!.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter Customer Name", Toast.LENGTH_SHORT).show()
            return
        }
//        if (categoryModel == null) {
//            Toast.makeText(this, "Please select a Category", Toast.LENGTH_SHORT).show()
//            return
//        }
        if (customerDatraSerialno == null) {
            Toast.makeText(
                this,
                "Please get a Product from entering serial no",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (etQuantity!!.text.toString().isEmpty()) {
            Toast.makeText(this, "Please enter Quantity", Toast.LENGTH_SHORT).show()
            return
        }
        if (spinnerPayment!!.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select Payment Method", Toast.LENGTH_SHORT).show()
            return
        }
        if (customerDatra == null) {
            Toast.makeText(
                this,
                "Please enter mobile no to get details for customer.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (saleType == "Wallet") {
            if (walletPint.toDouble() >= etTotalPriceWallet?.text.toString().toDouble()) {
                val saleRequestModel = SaleRequestModel(
                    ConsId = customerDatra?.id.toString(),
                    EntBy = Prefs.getString("username"),
                    ItemId = customerDatraSerialno?.ID.toString(),
                    PayMode = saleType,
                    Price = etPrice?.text.toString(),
                    PriceWallet = etPriceWallet?.text.toString(),
                    Qty = etQuantity?.text.toString(),
                    ShopId = Prefs.getString("userId"),
                    TotalPrice = etTotPrice?.text.toString(),
                    TotalPriceWallet = etTotalPriceWallet?.text.toString(),
                    didYouSell = etDidYouSell?.text.toString(),
                    SerialNo = etSerialNo!!.text.toString()
                )
                DialogUtil.displayProgress(this, "Please wait uploading data..")
                TransportManager.getInstance(this)?.uploadSaleData(context, saleRequestModel)
            } else {
                Toast.makeText(
                    this,
                    "You can't sale this product for this user from this option because user wallet point balance is low.",
                    Toast.LENGTH_LONG
                ).show()
            }

        } else {
            val saleRequestModel = SaleRequestModel(
                ConsId = customerDatra?.id.toString(),
                EntBy = Prefs.getString("username"),
                ItemId = customerDatraSerialno?.ID.toString(),
                PayMode = saleType,
                Price = etPrice?.text.toString(),
                PriceWallet = etPriceWallet?.text.toString(),
                Qty = etQuantity?.text.toString(),
                ShopId = Prefs.getString("userId"),
                TotalPrice = etTotPrice?.text.toString(),
                TotalPriceWallet = etTotalPriceWallet?.text.toString(),
                didYouSell = etDidYouSell?.text.toString(),
                SerialNo = etSerialNo!!.text.toString()
            )
            DialogUtil.displayProgress(this, "Please wait uploading data..")
            TransportManager.getInstance(this)?.uploadSaleData(context, saleRequestModel)
        }
    }

    private val categories = mutableListOf<CategoryModel>()
    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.CATEGORY_LIST -> {
                DialogUtil.stopProgressDisplay()
                if (data.data is DashboardCategoryModel) {
                    val datacd = data.data as DashboardCategoryModel
                    categories.clear()
                    categories.addAll(datacd.item)
                    val categoryAdapter =
                        ArrayAdapter(
                            this,
                            android.R.layout.simple_spinner_dropdown_item,
                            categories
                        )
                    spinnerCategory!!.adapter = categoryAdapter
                }
            }

            ApiServices.PRODUCT_LIST -> {
                DialogUtil.stopProgressDisplay()

                if (data.data is DashboardRsponseModel) {
                    val dataBind = data.data as DashboardRsponseModel
                    categoriesProduct.clear()
                    categoriesProduct.addAll(dataBind.item as ArrayList<DashboardItem>)
                    val productAdapter =
                        ArrayAdapter(
                            this,
                            android.R.layout.simple_spinner_dropdown_item,
                            categoriesProduct
                        )
                    spinnerProduct!!.adapter = productAdapter

                    // Ensure product list is correctly categorized


                } else {
                    Log.e("API_ERROR", "Unexpected response for PRODUCT_LIST")
                }
            }

            ApiServices.GET_DATA_MOBILE_NO -> {
                DialogUtil.stopProgressDisplay()
                val dataRecive = data.data as ArrayList<MobilNoDataResponseModelItem>
                val binddata = dataRecive[0]
                customerDatra = binddata
                etCustomerName?.setText(binddata.Name)
                walletPint = binddata.WalletBalance.toString()
                userwalletbalance?.setText(binddata.WalletBalance.toString())
            }

            ApiServices.GET_DATA_MSERIAL_NO -> {
                DialogUtil.stopProgressDisplay()
                val dataRecive = data.data as ArrayList<ItemXX>
                val binddata = dataRecive[0]
                customerDatraSerialno = binddata
                etProduct?.setText(binddata.ITEM_NAME)
                etPrice?.setText(binddata.price_per.toString())
                etPriceWallet?.setText(binddata.Wallet_Price.toString())
                btnSubmit?.isEnabled = true
                btnSubmit?.isClickable = true
                btnSubmit?.alpha = 1f
            }

            ApiServices.SALE_UPLOAD_API -> {
                DialogUtil.stopProgressDisplay()
                toast("Product Sale done")
                finish()

            }
        }
    }

    var customerDatra: MobilNoDataResponseModelItem? = null
    var customerDatraSerialno: ItemXX? = null
    var walletPint = ""
    private val categoriesProduct = mutableListOf<DashboardItem>()
    override fun onFailureResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.GET_DATA_MSERIAL_NO -> {
                DialogUtil.stopProgressDisplay()
                toast(data.message.toString())
                btnSubmit?.isEnabled = false
                btnSubmit?.isClickable = false
                btnSubmit?.alpha = 0.5f
            }

            else -> {
                DialogUtil.stopProgressDisplay()
                toast(data.message.toString())
            }
        }

    }

    private fun calculateTotalPrice() {
        val quantityStr = etQuantity!!.text.toString()
        val priceStr = etPrice!!.text.toString()
        val walletPriceStr = etPriceWallet!!.text.toString()

        if (quantityStr.isNotEmpty() && priceStr.isNotEmpty() && walletPriceStr.isNotEmpty()) {
            val quantity = quantityStr.toInt()
            val price = priceStr.toDouble()
            val walletPrice = walletPriceStr.toDouble()

            val totalPrice = price * quantity
            val totalWalletPrice = walletPrice * quantity

            etTotPrice!!.setText(totalPrice.toString()) // Update Total Price
            etTotalPriceWallet!!.setText(totalWalletPrice.toString()) // Update Total Wallet Price
        }
    }
}