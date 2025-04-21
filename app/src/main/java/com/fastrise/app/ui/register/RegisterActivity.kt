package com.fastrise.app.ui.register

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fastrise.app.R
import com.fastrise.app.ui.services.ApiServices
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.DialogUtil
import com.fastrise.app.utill.toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity(), EventListner {
    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_register)
        context = this@RegisterActivity
        // Initialize views
        val etFullName: TextInputEditText = findViewById(R.id.etFullName)
        val etPassword: TextInputEditText = findViewById(R.id.etPassword)
        val etMobileNumber: TextInputEditText = findViewById(R.id.etMobileNumber)
        val etGmail: TextInputEditText = findViewById(R.id.etGmail)
        val etAddress: TextInputEditText = findViewById(R.id.etAddress)
        val etCity: TextInputEditText = findViewById(R.id.etCity)
        val rgGender: RadioGroup = findViewById(R.id.rgGender)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        val tilFullName: TextInputLayout = findViewById(R.id.tilFullName)
        val loginback: TextView = findViewById(R.id.loginback)
        val tilMobileNumber: TextInputLayout = findViewById(R.id.tilMobileNumber)
        val tilGmail: TextInputLayout = findViewById(R.id.tilGmail)
        val tilPassword: TextInputLayout = findViewById(R.id.tilPassword)
        val tilAddress: TextInputLayout = findViewById(R.id.tilAddress)
        val tilCity: TextInputLayout = findViewById(R.id.tilCity)

        // Handle submit button click
        loginback.setOnClickListener {
            finish()
        }
        btnSubmit.setOnClickListener {
            var isValid = true

            // Validate fields
            isValid = validateField(tilFullName, "Full name is required") && isValid
            isValid = validateField(tilMobileNumber, "Mobile number is required") && isValid
            isValid = validateField(tilGmail, "Gmail is required") && isValid
            isValid = validateField(tilPassword, "Password is required") && isValid
            isValid = validateField(tilAddress, "Address is required") && isValid
            isValid = validateField(tilCity, "City is required") && isValid

            val gender = when (rgGender.checkedRadioButtonId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                R.id.rbOther -> "Other"
                else -> {
                    Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
                    isValid = false
                    ""
                }
            }

            if (isValid) {
                val fullName = tilFullName.editText?.text.toString().trim()
                val mobileNumber = tilMobileNumber.editText?.text.toString().trim()
                val gmail = tilGmail.editText?.text.toString().trim()
                val password = tilPassword.editText?.text.toString().trim()
                val address = tilAddress.editText?.text.toString().trim()
                val city = tilCity.editText?.text.toString().trim()

                // Display the collected data in a Toast

                DialogUtil.displayProgress(this, "Please wait uploading data")
                val userRegisterRequestModel = UserRegisterRequestModel(
                    City = city,
                    Eaddress = address,
                    Gender = gender,
                    Gmail = gmail,
                    Mobile_No = mobileNumber,
                    Name = fullName,
                    Password = password,
                    UserType = "Customer"
                )
                TransportManager.getInstance(this)?.userRegister(context, userRegisterRequestModel)

            }
        }
    }

    private fun validateField(til: TextInputLayout, errorMessage: String): Boolean {
        val input = til.editText?.text.toString().trim()
        return if (input.isEmpty()) {
            til.error = errorMessage
            false
        } else {
            til.error = null
            true
        }
    }


    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.USER_REGISTER -> {
                DialogUtil.stopProgressDisplay()
                toast(data.message.toString())
                finish()
            }
        }
    }

    override fun onFailureResponse(reqType: Int, data: ResponseModel<*>) {
        DialogUtil.stopProgressDisplay()
        toast(data.message.toString())
    }
}