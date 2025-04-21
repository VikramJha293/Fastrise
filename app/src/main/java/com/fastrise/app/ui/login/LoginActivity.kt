package com.fastrise.app.ui.login

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fastrise.app.R
import com.fastrise.app.ui.dashboard.DashboardActiviyy
import com.fastrise.app.ui.dashboard.MobilNoDataResponseModelItem
import com.fastrise.app.ui.dashboard.NewDashboardPage
import com.fastrise.app.ui.dashboard.SaleListRecordActivity
import com.fastrise.app.ui.dashboard.SaleProductActivity
import com.fastrise.app.ui.register.RegisterActivity
import com.fastrise.app.ui.register.RetailerRegistrationActivity
import com.fastrise.app.ui.services.ApiServices
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.Coroutines
import com.fastrise.app.utill.DialogUtil
import com.fastrise.app.utill.toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pixplicity.easyprefs.library.Prefs

class LoginActivity : AppCompatActivity(), EventListner {
    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
        val loginclick = findViewById<AppCompatButton>(R.id.loginclick)
        val signupClivk = findViewById<TextView>(R.id.signupClivk)
        val userid = findViewById<EditText>(R.id.userid)
        val password = findViewById<EditText>(R.id.password)
        val forgotPass = findViewById<TextView>(R.id.forgotPassword)
        context = this@LoginActivity
        val value = Prefs.getString("username", "")
        if (!TextUtils.isEmpty(value)) {
            userid.setText(value.toString())
        }
        loginclick.setOnClickListener {
            if (userid.text.toString() == "") {
                toast(getString(R.string.please_enter_user_id))
            } else if (password.text.toString() == "") {
                toast(getString(R.string.please_enter_user_password))
            } else {
                DialogUtil.displayProgress(
                    this,
                    getString(R.string.please_wait_logging_in_to_app)
                )
                user = userid.text.toString()
                passwordd = password.text.toString()
                TransportManager.getInstance(this)!!
                    .userLogin(context, userid.text.toString(), password.text.toString())

            }
        }
        signupClivk.setOnClickListener {
            /*val bt1 = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val view1: View =
                LayoutInflater.from(this).inflate(R.layout.bottom_sheet_lay_new, null)
            val plinth = view1.findViewById<TextView>(R.id.plinth)
            val lintel = view1.findViewById<TextView>(R.id.lintel)
            plinth.setOnClickListener {
                bt1.dismiss()
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
            lintel.setOnClickListener {
                bt1.dismiss()
                val intent = Intent(this@LoginActivity, RetailerRegistrationActivity::class.java)
                startActivity(intent)

            }
            bt1.setContentView(view1)
            bt1.show()*/
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)

        }

        forgotPass.setOnClickListener {
            showForgotPasswordBottomSheet(this)

        }
    }

    private fun showForgotPasswordBottomSheet(context: Context) {
        // Create a BottomSheetDialog
        val bottomSheetDialog = BottomSheetDialog(context)

        // Inflate the layout
        val view = LayoutInflater.from(context).inflate(R.layout.forgot_password_bottom_sheet, null)

        // Find views
        val etMobileNumber = view.findViewById<EditText>(R.id.etMobileNumber)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        // Set listeners
        btnSubmit.setOnClickListener {
            val mobileNumber = etMobileNumber.text.toString().trim()

            if (mobileNumber.isEmpty()) {
                Toast.makeText(
                    context,
                    "Please enter a valid mobile number",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                bottomSheetDialog.dismiss()
                DialogUtil.displayProgress(this, "Please wait validating mobile no..")
                TransportManager.getInstance(this)!!
                    .getDataByMobileNo(context, mobileNumber.toString())

            }
        }

        btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        // Set the content view for the dialog
        bottomSheetDialog.setContentView(view)

        // Show the dialog
        bottomSheetDialog.show()
    }

    var user = ""
    var passwordd = ""
    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.USER_LOGIN -> {
                DialogUtil.stopProgressDisplay()
                val authResponse = data.data as? ArrayList<*> // Safely cast to the expected type
                if (authResponse != null && authResponse.isNotEmpty()) {
                    val dataList =
                        authResponse[0] as LoginResponseModelItem // Access the first element
                    // Use `dataList` which is now of type `LoginResponseModel`
                    Log.d("AUTH_RESPONSE", "User Name: ${dataList.Name}")
                    Log.d("AUTH_RESPONSE", "User Email: ${dataList.Gmail}")
                    Prefs.putString("username", user)
                    Prefs.putString("userId", dataList.id.toString())
                    Prefs.putString("Name", dataList.Name)
                    Prefs.putString("MobileNo", dataList.Mobile_No.toString())
                    if (dataList.User_Type == "Customer") {
                        val intentfd = Intent(this, NewDashboardPage::class.java)
                        intentfd.putExtra("loginData", dataList)
                        startActivity(intentfd)
                    } else {
                        val intentfd = Intent(this, SaleListRecordActivity::class.java)
                        intentfd.putExtra("loginData", dataList)
                        startActivity(intentfd)
                    }

                } else {
                    Log.e("AUTH_RESPONSE", "Data is null or empty")
                }
                toast(data.message.toString())
            }

            ApiServices.GET_DATA_MOBILE_NO -> {
                DialogUtil.stopProgressDisplay()
                val dataRecive = data.data as ArrayList<MobilNoDataResponseModelItem>
                val binddata = dataRecive[0]
                val customerDatra = binddata
                showResetPasswordBottomSheet(this, customerDatra.Mobile_No)
            }

            ApiServices.PASSWORD_CHANGE -> {
                DialogUtil.stopProgressDisplay()
                bottomSheetDialog?.dismiss()
                toast(data.message.toString())
            }
        }
    }

    private var bottomSheetDialog: BottomSheetDialog? = null

    private fun showResetPasswordBottomSheet(
        context: Context,
        mobileNumber: String

    ) {
        // Create a BottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(context)

        // Inflate the layout
        val view = LayoutInflater.from(context).inflate(R.layout.password_reset_bottom_sheet, null)

        // Find views
        val tvMobileNumber = view.findViewById<TextView>(R.id.tvMobileNumber)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = view.findViewById<EditText>(R.id.etConfirmPassword)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        // Set the non-editable mobile number
        tvMobileNumber.text = "Mobile No : $mobileNumber"

        // Set listeners
        btnSubmit.setOnClickListener {
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(context, "Password fields cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else if (password != confirmPassword) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                DialogUtil.displayProgress(this, "Please wait your password is changing..")
                TransportManager.getInstance(this)!!
                    .userPasswordChange(context, mobileNumber, password)
                bottomSheetDialog?.dismiss()

            }
        }

        btnCancel.setOnClickListener {
            bottomSheetDialog?.dismiss()
        }

        // Set the content view for the dialog
        bottomSheetDialog?.setContentView(view)

        // Show the dialog
        bottomSheetDialog?.show()
    }

    override fun onFailureResponse(reqType: Int, data: ResponseModel<*>) {
        DialogUtil.stopProgressDisplay()
        toast(data.message.toString())
    }
}