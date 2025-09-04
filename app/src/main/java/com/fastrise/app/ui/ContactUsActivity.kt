package com.fastrise.app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fastrise.app.R

// ContactUsActivity.kt
class ContactUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)


        // Phone Click
        findViewById<TextView>(R.id.tvPhone).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+918503043323")
            startActivity(intent)
        }

        // WhatsApp Click
        findViewById<TextView>(R.id.tvWhatsApp).setOnClickListener {
            val url = "https://wa.me/918503043323"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // Email Click
        findViewById<TextView>(R.id.tvEmail).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:fastrisecontact@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "Support Inquiry")
            }
            startActivity(intent)
        }
    }
}

