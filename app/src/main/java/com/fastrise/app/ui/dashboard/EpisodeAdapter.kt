package com.fastrise.app.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fastrise.app.R

class EpisodeAdapter(
    private var categories: List<DashboardNewResponseModelItem>,
    private val dashboardActivity: Context
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.category)
        val recyclerViewChild: RecyclerView = itemView.findViewById(R.id.recyclervirwchild)
        val no_data_text: TextView = itemView.findViewById(R.id.no_data_text)

        // Store LayoutManager and Adapter for reusability
        private val layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

        init {
            recyclerViewChild.layoutManager = layoutManager

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_dashboard_row, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.CATEGORY

        // Get product list safely
        if (category.Items.isEmpty()) {
            holder.recyclerViewChild.visibility = View.GONE
            holder.no_data_text.visibility = View.VISIBLE  // Show "No products available"
        } else {
            holder.recyclerViewChild.visibility = View.VISIBLE
            holder.no_data_text.visibility = View.GONE
            holder.recyclerViewChild.adapter = ChildAdapter(category.Items,dashboardActivity) // Pass product list
        }

    }

    override fun getItemCount(): Int = categories.size


}
