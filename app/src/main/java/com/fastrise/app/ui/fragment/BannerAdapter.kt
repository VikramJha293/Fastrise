package com.fastrise.app.ui.fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R
import com.squareup.picasso.Picasso

class BannerAdapter(private val bannerList: ArrayList<BannerItem>) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgBanner: ImageView = itemView.findViewById(R.id.imgBannerItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val bannerUrl = bannerList[position]

        Picasso.get()
            .load(bannerUrl.BANNER)
            .into(holder.imgBanner)
    }

    override fun getItemCount(): Int = bannerList.size
}
