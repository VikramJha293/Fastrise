package com.fastrise.app.ui.dashboard

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.fastrise.app.R
import com.fastrise.app.utill.AppConstant
import com.pixplicity.easyprefs.library.Prefs
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_product_details)
        val product: DashboardItem? = AppConstant.dashboardItem
        // Bind UI Elements
        val productImage: ImageView = findViewById(R.id.product_image)
        val itemName: TextView = findViewById(R.id.item_name)
        val shortName: TextView = findViewById(R.id.short_name)
        val hsn: TextView = findViewById(R.id.hsn)
        val category: TextView = findViewById(R.id.category)
        val company: TextView = findViewById(R.id.company)
        val itemType: TextView = findViewById(R.id.item_type)
        val walletPrice: TextView = findViewById(R.id.wallet_price)
        val walletPoints: TextView = findViewById(R.id.wallet_points)
        val modelno: TextView = findViewById(R.id.modelno)
        val shareButton: Button = findViewById(R.id.share_button)
        val backB: ImageView = findViewById(R.id.backB)
        shortName.visibility = View.GONE
        backB.setOnClickListener {
            finish()
        }
        // Populate UI with product data
        itemName.text = product?.ITEM_NAME
//        shortName.text = "Item Type: ${product?.ITEM_TYPE}"
        hsn.text = "Discription: ${product?.Discription}"
        category.text = "Category: ${product?.CATEGORY}"
        company.text = "Company: ${product?.COMPANY}"
        itemType.text = "Item Type: ${product?.ITEM_TYPE}"
        walletPrice.text = "Product Price: ₹${product?.Price_Per}"
        walletPoints.text = "Wallet Points: ${product?.Wallet_Point}"
        modelno.text = "Model No: ${product?.MODEL_NO}"
//        showImageFromBase64(product?.PHOTO1.toString(), productImage)
//    /*    Glide.with(this)
//            .load(product?.PHOTO1)
//            .diskCacheStrategy(DiskCacheStrategy.NONE)
//            .signature(ObjectKey(System.currentTimeMillis().toString()))
//            .skipMemoryCache(true)
//            .into(productImage)*/

        Picasso.get()
            .load(product?.PHOTO1)
            .into(productImage)

        val namesdsf = Prefs.getString("Name")
        val mobileno = Prefs.getString("MobileNo")
        shareButton.setOnClickListener {
            val message = """
                Product Details:
                Name: ${product?.ITEM_NAME}
                Category: ${product?.CATEGORY}
                Company: ${product?.COMPANY}
                Item Type: ${product?.ITEM_TYPE}
                Product Price: ${product?.Price_Per}
                Model No: ${product?.MODEL_NO}
                User Name: $namesdsf
                User Mobile No: $mobileno
            """.trimIndent()

            sendWhatsAppMessage("+918503043323", message)
        }
    }

    private fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun showImageFromBase64(base64String: String, imageView: ImageView) {
        try {
            // Decode the Base64 string
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)

            // Convert the decoded bytes into a Bitmap
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

            // Set the Bitmap to the ImageView
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle decoding errors (optional)
        }
    }

    private fun sendWhatsAppMessage(phoneNumber: String, message: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://wa.me/$phoneNumber?text=${Uri.encode(message)}")
            }
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendWhatsAppMessage(phoneNumber: String, message: String, bitmap: Bitmap?) {
        try {
            val file = File(cacheDir, "product_image.jpg")
            val fileOutputStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()

            val uri = Uri.fromFile(file)

            // Create an intent to send the image with the message
            val intent = Intent(Intent.ACTION_VIEW).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_TEXT, message)
                putExtra(Intent.EXTRA_STREAM, uri)
                setPackage("https://wa.me/") // Open WhatsApp directly
            }

            // Start the activity
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            // Show an error message if WhatsApp is not installed
            Toast.makeText(this, "WhatsApp is not installed on your device.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppConstant.dashboardItem = null
    }
}