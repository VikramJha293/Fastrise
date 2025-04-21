package com.fastrise.app.ui.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fastrise.app.R
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream
import android.util.Base64
import com.fastrise.app.ui.dashboard.CategoryModel
import com.fastrise.app.ui.dashboard.DashboardCategoryModel
import com.fastrise.app.ui.services.ApiServices
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.DialogUtil
import com.fastrise.app.utill.toast


class RetailerRegistrationActivity : AppCompatActivity(), EventListner {
    private var context: Context? = null
    private lateinit var etMobile: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etName: TextInputEditText
    private lateinit var etCompanyName: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var etGST: TextInputEditText
    private lateinit var etBankDetails: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etBankaccountno: TextInputEditText
    private lateinit var etPancard: TextInputEditText
    private lateinit var etbranchname: TextInputEditText
    private lateinit var etifsc: TextInputEditText
    private lateinit var imgPanUpload: ImageView
    private lateinit var btnUploadPan: Button
    private lateinit var btnSubmit: Button

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_register_retailer)
        context = this@RetailerRegistrationActivity
        etMobile = findViewById(R.id.etMobile)
        etEmail = findViewById(R.id.etEmail)
        etName = findViewById(R.id.etName)
        etCompanyName = findViewById(R.id.etCompanyName)
        etAddress = findViewById(R.id.etAddress)
        etGST = findViewById(R.id.etGST)
        etBankDetails = findViewById(R.id.etBankDetails)
        etPassword = findViewById(R.id.etPassword)
        etBankaccountno = findViewById(R.id.etBankaccountno)
        etbranchname = findViewById(R.id.etbranchname)
        etifsc = findViewById(R.id.etifsc)
        etPancard = findViewById(R.id.etPancard)
        imgPanUpload = findViewById(R.id.imgPanUpload)
        btnUploadPan = findViewById(R.id.btnUploadPan)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnUploadPan.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnSubmit.setOnClickListener {
            validateForm()
        }

    }


    private fun validateForm() {
        val mobile = etMobile.text.toString().trim { it <= ' ' }
        val email = etEmail.text.toString().trim { it <= ' ' }
        val name = etName.text.toString().trim { it <= ' ' }
        val companyName = etCompanyName.text.toString().trim { it <= ' ' }
        val bankDetails = etBankDetails.text.toString().trim { it <= ' ' }
        val accountNo = etBankaccountno.text.toString().trim()
        val etifscs = etifsc.text.toString().trim()
        val etbranchnamed = etbranchname.text.toString().trim()
        val etPancarddd = etPancard.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Mobile Number Validation
        if (TextUtils.isEmpty(mobile) || mobile.length != 10 || !mobile.matches("\\d{10}".toRegex())) {
            etMobile.error = "Enter valid 10-digit mobile number"
            etMobile.requestFocus()
            return
        }

        // Email Validation
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter valid email"
            etEmail.requestFocus()
            return
        }

        // Name Validation
        if (TextUtils.isEmpty(name)) {
            etName.error = "Name is required"
            etName.requestFocus()
            return
        }

        // Company Name Validation
        if (TextUtils.isEmpty(companyName)) {
            etCompanyName.error = "Company Name is required"
            etCompanyName.requestFocus()
            return
        }
        if (TextUtils.isEmpty(etPancarddd)) {
            etPancard.error = "Pan Card is required"
            etPancard.requestFocus()
            return
        }

        // PAN Image Validation (Check if user uploaded PAN image)
        if (imgPanUpload.drawable == null) {
            Toast.makeText(this, "Please upload PAN Card", Toast.LENGTH_SHORT).show()
            return
        }

        // Bank Details Validation
        if (TextUtils.isEmpty(bankDetails)) {
            etBankDetails.error = "Bank Name are required"
            etBankDetails.requestFocus()
            return
        }
        if (TextUtils.isEmpty(accountNo)) {
            etBankaccountno.error = "Bank Account no are required"
            etBankaccountno.requestFocus()
            return
        }
        if (TextUtils.isEmpty(etifscs)) {
            etifsc.error = "IFSC code  are required"
            etifsc.requestFocus()
            return
        }
        if (TextUtils.isEmpty(etbranchnamed)) {
            etbranchname.error = "Bank Name  are required"
            etbranchname.requestFocus()
            return
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.error = "Password are required"
            etPassword.requestFocus()
            return
        }

        val userData = RetailerRegistration(
            AccountNo = accountNo,
            BankName = bankDetails,
            Branch = etbranchnamed,
            City = "",
            CompanyName = companyName,
            Eaddress = email,
            Gender = "Male",  // Assume gender (can be a dropdown)
            Gmail = email,
            Gst = etGST.text.toString(),  // Assume GST (Optional)
            Ifsc = etifscs,
            Mobile_No = mobile,
            Name = name,
            Pan = etPancarddd,
            Password = password,
            UserType = "Supplier",
            Photo = panImageBase64!!  // Base64 Encoded Image
        )
        DialogUtil.displayProgress(this, "Please wait uploading data")
        TransportManager.getInstance(this)?.userRegisterRetailer(context, userData)
        // If all fields are valid, show success message

    }

    private var panImageBase64: String? = null  // Store Base64 Image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            val inputStream = contentResolver.openInputStream(imageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imgPanUpload.setImageBitmap(bitmap)
            // Convert to Base64
            panImageBase64 = encodeImageToBase64(bitmap)

            // Show Image

        }
    }

    private fun encodeImageToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.RETAILER_REGISTRATION -> {
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