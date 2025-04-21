package com.fastrise.app.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fastrise.app.R
import com.fastrise.app.ui.login.LoginResponseModelItem
import com.fastrise.app.ui.register.RetailerRegistrationActivity.Companion.PICK_IMAGE_REQUEST

class EditProfileActivity : AppCompatActivity() {
    private lateinit var imgPanUpload: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_edit_profile)
        val i = intent
        val dene: LoginResponseModelItem? =
            i.getSerializableExtra("logindata") as LoginResponseModelItem?
        // Get references to UI components
        val etName: EditText = findViewById(R.id.et_name)
        val etEmail: EditText = findViewById(R.id.et_email)
        val etMobile: EditText = findViewById(R.id.et_mobile)
        val btnSave: Button = findViewById(R.id.btn_save)
        val backB: ImageView = findViewById(R.id.backB)
       val btnUploadPan:Button = findViewById(R.id.btnUploadPan)
        imgPanUpload = findViewById(R.id.imgPanUpload)
        // Set initial values (can be fetched from an API or database)
        etName.setText(dene?.Name)
        etEmail.setText(dene?.Gmail)
        etMobile.setText(dene?.Mobile_No)
        backB.setOnClickListener {
            finish()
        }

        btnUploadPan.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
        // Save button click listener
        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
//            val genderId = rgGender.checkedRadioButtonId
//            val gender = when (genderId) {
//                R.id.rb_male -> "Male"
//                R.id.rb_female -> "Female"
//                R.id.rb_other -> "Other"
//                else -> ""
//            }

            // Perform save operation (e.g., API call or database update)

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            val inputStream = contentResolver.openInputStream(imageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imgPanUpload.setImageBitmap(bitmap)
            // Convert to Base64


        }
    }
}