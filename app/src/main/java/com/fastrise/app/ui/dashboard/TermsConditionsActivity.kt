package com.fastrise.app.ui.dashboard

import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.fastrise.app.R
import com.fastrise.app.ui.login.LoginResponseModelItem

class TermsConditionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_term_condition) // Replace with actual XML filename
        val i = intent
        val dene: LoginResponseModelItem? =
            i.getSerializableExtra("logindata") as LoginResponseModelItem?
        // Find TextView
        val textView = findViewById<TextView>(R.id.textViewfff)
        val backB = findViewById<ImageView>(R.id.backB)

        // HTML formatted text
        val htmlContent = dene?.terms


        // Set HTML content
        textView.text = HtmlCompat.fromHtml(htmlContent.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        backB.setOnClickListener {
            finish()
        }
    }
}
