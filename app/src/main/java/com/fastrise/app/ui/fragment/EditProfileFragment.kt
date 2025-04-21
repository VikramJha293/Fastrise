package com.fastrise.app.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fastrise.app.R
import com.fastrise.app.ui.dashboard.UpdateProfileRequestBody
import com.fastrise.app.ui.login.LoginActivity
import com.fastrise.app.ui.login.LoginResponseModelItem
import com.fastrise.app.ui.services.ApiServices
import com.fastrise.app.ui.services.EventListner
import com.fastrise.app.ui.services.ResponseModel
import com.fastrise.app.ui.services.TransportManager
import com.fastrise.app.utill.DialogUtil
import com.fastrise.app.utill.toast
import com.pixplicity.easyprefs.library.Prefs
import java.io.ByteArrayOutputStream

class EditProfileFragment : Fragment(), EventListner {
    private lateinit var imgPanUpload: ImageView
    private var loginData: LoginResponseModelItem? = null
    private lateinit var context: Context

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.layout_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve arguments (Login Data)
        loginData = arguments?.getSerializable("logindata") as? LoginResponseModelItem
        context = requireContext()
        val etName: EditText = view.findViewById(R.id.et_name)
        val layoutLogout: LinearLayout = view.findViewById(R.id.layoutLogout)
        val etEmail: EditText = view.findViewById(R.id.et_email)
        val etMobile: EditText = view.findViewById(R.id.et_mobile)
        val tvLogout: TextView = view.findViewById(R.id.tvLogout)
        val btnSave: Button = view.findViewById(R.id.btn_save)

        imgPanUpload = view.findViewById(R.id.imgPanUpload)

        // Set initial values
        etName.setText(loginData?.Name)
        etEmail.setText(loginData?.Gmail)
        etMobile.setText(loginData?.Mobile_No)
        val bitmap = base64ToBitmap(loginData?.PHOTO.toString())

        if (bitmap != null) {
//            imgPanUpload.setImageBitmap(bitmap) // imgPanUpload is your ImageView
//            imgPanUpload.setBackgroundResource(R.drawable.circle_badge)
            val drawable = ContextCompat.getDrawable(requireActivity(), R.drawable.circle_badge)
            imgPanUpload.setImageDrawable(LayerDrawable(arrayOf(drawable, BitmapDrawable(resources, bitmap))))
        } else {
            Log.e("Base64", "Failed to decode Base64 string")
        }
        layoutLogout.setOnClickListener {
            Prefs.putString("username", "")
            val intrr = Intent(requireActivity(), LoginActivity::class.java)
            intrr.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intrr)
        }
        tvLogout.setOnClickListener {
            Prefs.putString("username", "")
            val intrr = Intent(requireActivity(), LoginActivity::class.java)
            intrr.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intrr)
        }


        imgPanUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            // Perform save operation (e.g., API call or database update)
            if (name.isEmpty()) {
                requireActivity().toast("Please enter name")
            } else if (email.isEmpty()) {
                requireActivity().toast("Please enter email")
            } else {
                val updateProfileRequestBody = UpdateProfileRequestBody(
                    Gmail = email,
                    Mobile_No = loginData?.Mobile_No.toString(),
                    Name = name,
                    Photo = base64Image.toString(),
                    UserType = "Customer"
                )
                DialogUtil.displayProgress(requireActivity(), "Please wait updating profile...")
                TransportManager.getInstance(this)
                    ?.updateProfileApi(context, updateProfileRequestBody)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            val inputStream = requireActivity().contentResolver.openInputStream(imageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imgPanUpload.setImageBitmap(bitmap)
            base64Image = encodeToBase64(bitmap)
        }
    }

    private var base64Image: String? = null  // To store the Base64 string
    override fun onSuccessResponse(reqType: Int, data: ResponseModel<*>) {
        when (reqType) {
            ApiServices.UPDATE_PROFILe -> {
                DialogUtil.stopProgressDisplay()
                requireActivity().toast(data.message.toString())
            }
        }
    }

    private fun encodeToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    override fun onFailureResponse(reqType: Int, data: ResponseModel<*>) {
        DialogUtil.stopProgressDisplay()
        requireActivity().toast(data.message.toString())
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }
}
