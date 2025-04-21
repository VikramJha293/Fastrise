package com.fastrise.app.ui.dashboard

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R
import com.fastrise.app.utill.AppConstant

class ChildAdapter(
    private var products: List<DashboardItem>,
    private val dashboardActiviyy: Context
) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivThumbnail: ImageView = itemView.findViewById(R.id.ivThumbnail)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvSubtitle: TextView = itemView.findViewById(R.id.tvSubtitle)
        val tvEpisodeCount: TextView = itemView.findViewById(R.id.tvEpisodeCount)
        val modelno: TextView = itemView.findViewById(R.id.modelno)
        val btnEpisodeInfo: Button = itemView.findViewById(R.id.btnEpisodeInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_row_child, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val product = products[position]
        holder.tvTitle.text = "${product.ITEM_NAME}"
        holder.tvSubtitle.text = "Category:${product.CATEGORY}"
        holder.tvEpisodeCount.text = "Price: \u20B9${product.Price_Per}"
        holder.modelno.text = "Model No:${product.MODEL_NO}"
        showImageFromBase64(product.PHOTO1, holder.ivThumbnail)

        holder.btnEpisodeInfo.setOnClickListener {
            // Handle button click
            val int = Intent(dashboardActiviyy, ProductDetailsActivity::class.java)
            AppConstant.dashboardItem = product
            dashboardActiviyy.startActivity(int)
        }

    }

    override fun getItemCount(): Int = products.size

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
    fun updateData(newProducts: List<DashboardItem>) {
        products = newProducts
        notifyDataSetChanged()
    }
}